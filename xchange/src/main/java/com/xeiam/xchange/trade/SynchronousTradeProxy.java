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
package com.xeiam.xchange.trade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeProxy;
import com.xeiam.xchange.exceptions.NotConnectedException;

/**
 * An Exchange Proxy for getting exchange account info synchronously
 */
public abstract class SynchronousTradeProxy extends ExchangeProxy {

  /**
   * Provides logging for this class
   */
  private static final Logger log = LoggerFactory.getLogger(SynchronousTradeProxy.class);

  public AccountInfo getAccountInfo(String key, String secret) {

    AccountInfo accountInfo = null;

    // check if connected
    if (!isConnected) {
      throw new NotConnectedException("Not Connected to Exchange!");
    }

    accountInfo = getExchangeAccountInfo(key, secret);

    return accountInfo;
  }

  public abstract AccountInfo getExchangeAccountInfo(String key, String secret);

}
