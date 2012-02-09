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
package com.xeiam.xchange.imcex.v1;

import java.util.Arrays;
import java.util.List;

/**
 * A central place for shared Mt Gox properties
 * TODO Consider an enum for this
 */
public class ImcexProperties {

  public static final int REFRESH_RATE = 10; // [seconds]

  // TODO Move into a symbol service
  public static final List<String> IMCEX_SYMBOLS = Arrays.asList("BTCUSD", "BTCAUD", "BTCCAD", "BTCCHF", "BTCCNY", "BTCDKK", "BTCEUR", "BTCGBP", "BTCHKD", "BTCJPY", "BTCNZD", "BTCPLN", "BTCRUB", "BTCSEK", "BTCSGD","BTCTHB");

  public static final int VOLUME_INT_2_DECIMAL_FACTOR = 100000000;

  public static final int PRICE_INT_2_DECIMAL_FACTOR = 100000;

  public static final int JPY_PRICE_INT_2_DECIMAL_FACTOR = 1000;

}
