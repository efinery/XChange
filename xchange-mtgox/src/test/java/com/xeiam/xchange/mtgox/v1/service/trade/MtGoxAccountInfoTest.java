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
package com.xeiam.xchange.mtgox.v1.service.trade;

import com.xeiam.xchange.mtgox.v1.service.marketdata.MtGoxTickerTest;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Test MtGoxAccountInfo JSON parsing
 */
@Ignore
public class MtGoxAccountInfoTest {

  @Test
  public void testUnmarshal() throws IOException {

    // TODO Fill this in with example private account data

    // Read in the JSON from the example resources
    InputStream is = MtGoxTickerTest.class.getResourceAsStream("/mtgox/marketdata/example-accountinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    MtGoxAccountInfo mtGoxAccountInfo = mapper.readValue(is, MtGoxAccountInfo.class);

    // Verify that the example data was unmarshalled correctly
    //assertThat("Unexpected Return Buy value", mtGoxAccountInfo.getAsks().get(0).getAmount_int(), equalTo(2000000000L));
  }
}
