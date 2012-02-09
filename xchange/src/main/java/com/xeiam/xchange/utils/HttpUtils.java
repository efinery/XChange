/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.HttpException;

/**
 * Various HTTP utility methods
 */
public class HttpUtils {

  public static final String CHARSET_UTF_8 = "UTF-8";

  /**
   * Provides logging for this class
   */
  private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

  /**
   * Default request header fields
   */
  private static final Map<String, String> defaultHeaderKeyValues = new HashMap<String, String>();

  static {
    defaultHeaderKeyValues.put("Accept-Charset", CHARSET_UTF_8);
    defaultHeaderKeyValues.put("Content-Type", "application/x-www-form-urlencoded");
    defaultHeaderKeyValues.put("Accept", "text/plain"); // default Accept
    defaultHeaderKeyValues.put("User-Agent", "XChange/0.0.1 JDK/6 AppleWebKit/535.7 Chrome/16.0.912.36 Safari/535.7"); // custom User-Agent
  }

  /**
   * Requests JSON via an HTTP GET and unmarshals it into an object graph
   * 
   * @param urlString A string representation of a URL
   * @param returnType The required return type
   * @param objectMapper The Jackson ObjectMapper to use
   * @param httpHeaders Any HTTP headers
   * @return The contents of the response body as the given type mapped through Jackson
   */
  public static <T> T getForJsonObject(String urlString, Class<T> returnType, ObjectMapper objectMapper, Map<String, String> httpHeaders) {

    Assert.notNull(urlString, "urlString cannot be null");
    Assert.notNull(urlString, "objectMapper cannot be null");
    try {
      return objectMapper.readValue(getForString(urlString, httpHeaders), returnType);
    } catch (IOException e) {
      // Rethrow as runtime exception
      throw new HttpException(e.getMessage(), e);
    }
  }

  /**
   * Requests a String via an HTTP GET
   * 
   * @param urlString A string representation of a URL
   * @return The contents of the response body as a String containing JSON
   * @deprecated Use the more powerful getForJsonObject instead
   */
  @Deprecated
  public static String httpGET4JSON(String urlString) {
    return httpGET4JSON(urlString, new HashMap<String, String>());
  }

  /**
   * Requests JSON via an HTTP GET
   * 
   * @param urlString A string representation of a URL
   * @param customHeaderKeyValues Any custom header values
   * @return The contents of the response body as a String containing JSON
   * @deprecated Use the more powerful getForJsonObject instead
   */
  @Deprecated
  public static String httpGET4JSON(String urlString, Map<String, String> customHeaderKeyValues) {

    Assert.notNull(customHeaderKeyValues, "customHeaderKeyValues should not be null");

    Map<String, String> headerKeyValueMap = new HashMap<String, String>();
    headerKeyValueMap.put("Accept", "application/json");
    headerKeyValueMap.putAll(customHeaderKeyValues);
    return getForString(urlString, headerKeyValueMap);
  }

  /**
   * Requests JSON via an HTTP POST
   * 
   * @param urlString A string representation of a URL
   * @param postBody The contents of the request body
   * @return The contents of the response body as a String containing JSON
   */
  public static String httpPOST4JSON(String urlString, String postBody) {
    return httpPOST4JSON(urlString, postBody, new HashMap<String, String>());
  }

  /**
   * Requests JSON via an HTTP POST
   * 
   * @param urlString A string representation of a URL
   * @param postBody The contents of the request body
   * @param customHeaderKeyValues Any custom header values
   * @return String - the fetched JSON String
   */
  public static String httpPOST4JSON(String urlString, String postBody, Map<String, String> customHeaderKeyValues) {

    Assert.notNull(customHeaderKeyValues, "customHeaderKeyValues should not be null");

    Map<String, String> headerKeyValueMap = new HashMap<String, String>();
    headerKeyValueMap.put("Accept", "application/json");
    headerKeyValueMap.putAll(customHeaderKeyValues);
    return postForString(urlString, postBody, headerKeyValueMap);
  }

  /**
   * Send an HTTP GET request, and receive server response
   * 
   * @param urlString A string representation of a URL
   * @param customHeaderKeyValues Any custom header values
   * @return String - the fetched Response String
   * @see <a href="http://stackoverflow.com/questions/2793150/how-to-use-java-net-urlconnection-to-fire-and-handle-http-requests">Stack Overflow on URLConnection</a>
   */
  private static String getForString(String urlString, Map<String, String> customHeaderKeyValues) {

    Assert.notNull(customHeaderKeyValues, "customHeaderKeyValues should not be null");

    String responseString = "";
    HttpURLConnection conn = null;

    try {

      URL url = new URL(urlString);
      conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");

      // header key values
      Map<String, String> headerKeyValues = new HashMap<String, String>(defaultHeaderKeyValues);

      // add/override defaultHeaderKeyValues with customHeaderKeyValues
      headerKeyValues.putAll(customHeaderKeyValues);

      // add header key values to request
      for (Map.Entry<String, String> entry : headerKeyValues.entrySet()) {
        conn.setRequestProperty(entry.getKey(), entry.getValue());
        log.trace("header request property: key= " + entry.getKey() + ", value= " + entry.getValue());
      }

      responseString = getResponseString(conn);

    } catch (MalformedURLException e) {
      throw new HttpException("Problem GETing (malformed URL)", e);
    } catch (IOException e) {
      throw new HttpException("Problem GETing (IO)", e);
    } finally {
      if (conn != null) {
        conn.disconnect();
      }
    }
    return responseString;
  }

  /**
   * Send an HTTP POST request, and receive server response
   * 
   * @param urlString A string representation of a URL
   * @param postBody The contents of the request body
   * @param customHeaderKeyValues Any custom header values
   * @return The contents of the response body as a String
   */
  private static String postForString(String urlString, String postBody, Map<String, String> customHeaderKeyValues) {

    String responseString = "";
    HttpURLConnection conn = null;
    try {

      URL url = new URL(urlString);
      conn = (HttpURLConnection) url.openConnection();
      conn.setDoOutput(true);
      conn.setDoInput(true);
      conn.setRequestMethod("POST");

      // header key values
      Map<String, String> headerKeyValues = new HashMap<String, String>(defaultHeaderKeyValues);

      // add/override defaultHeaderKeyValues with customHeaderKeyValues
      headerKeyValues.putAll(customHeaderKeyValues);

      // add header key values to request
      for (Map.Entry<String, String> entry : headerKeyValues.entrySet()) {
        conn.setRequestProperty(entry.getKey(), entry.getValue());
        log.trace("header request property: key= " + entry.getKey() + ", value= " + entry.getValue());
      }

      // add content length to header
      conn.setRequestProperty("Content-Length", Integer.toString(postBody.length()));

      log.trace("postBody= " + postBody);
      conn.getOutputStream().write(postBody.getBytes(CHARSET_UTF_8));

      responseString = getResponseString(conn);

    } catch (MalformedURLException e) {
      throw new HttpException("Problem POSTing (malformed URL)", e);
    } catch (IOException e) {
      throw new HttpException("Problem POSTing (IO)", e);
    } finally {
      if (conn != null) {
        conn.disconnect();
      }
    }

    return responseString;
  }

  /**
   * Gets the response String from an HTTP request
   * 
   * @param conn The HTTP connection
   * @return The response body as a String
   * @throws IOException If something goes wrong
   */
  private static String getResponseString(HttpURLConnection conn) throws IOException {

    String responseString;

    String responseEncoding = getResponseEncoding(conn);

    // if the server specified an encoding, use a Buffered Reader
    if (responseEncoding != null) {

      StringBuilder sb = new StringBuilder();
      BufferedReader reader;
      reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), responseEncoding));
      for (String line; (line = reader.readLine()) != null;) {
        log.trace(line);
        sb.append(line);
      }
      responseString = sb.toString();

      // no encoding specified, use a BufferedInputStream
    } else {

      BufferedInputStream bis;
      bis = new BufferedInputStream(conn.getInputStream());
      byte[] byteContents = new byte[1024];

      int bytesRead;
      String strContents = null;
      while ((bytesRead = bis.read(byteContents)) != -1) {
        strContents = new String(byteContents, 0, bytesRead);
        log.trace(strContents);
      }
      responseString = strContents;

    }
    return responseString;
  }

  /**
   * Determine the response encoding if specified
   * 
   * @param conn The HTTP connection
   * @return The response encoding as a string (taken from "Content-Type")
   */
  private static String getResponseEncoding(HttpURLConnection conn) {

    String contentType = conn.getHeaderField("Content-Type");
    String charset = null;
    for (String param : contentType.replace(" ", "").split(";")) {
      if (param.startsWith("charset=")) {
        charset = param.split("=", 2)[1];
        break;
      }
    }
    return charset;
  }

}
