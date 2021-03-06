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
package com.xeiam.xchange.intersango.v1.demo;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.service.marketdata.MarketDataService;
import com.xeiam.xchange.service.marketdata.Ticker;
import com.xeiam.xchange.service.trade.AccountInfo;
import com.xeiam.xchange.service.trade.TradeService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connecting to Mt Gox Bitcoin exchange</li>
 * <li>Retrieving market data</li>
 * </ul>
 * 
 * @since 0.0.1  
 */
public class IntersangoDemo {

  public static void main(String[] args) {

    // Demonstrate the public market data service
    demoMarketDataService();

    // Demonstrate the private account data service
    demoAccountService();

  }

  /**
   * Demonstrates how to connect to the MarketDataService for MtGox
   */
  private static void demoMarketDataService() {

    // Use the factory to get the version 1 Intersango exchange API using default settings
    Exchange intersango = ExchangeFactory.INSTANCE.createExchange("com.xeiam.xchange.intersango.v1.IntersangoExchange");

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = intersango.getMarketDataService();

    // Get the latest ticker data showing BTC to USD
    Ticker ticker = marketDataService.getTicker("BTCUSD");

    // Perform a crude conversion from the internal representation
    double btcusd = (double) ticker.getLast() / 100000;

    System.out.printf("Current exchange rate for BTC to USD: %.4f", btcusd);
  }

  /**
   * Demonstrates how to connect to the AccountService for Intersango
   */
  private static void demoAccountService() {
    // Use the factory to get the version 1 MtGox exchange API using default settings
    Exchange imcex = ExchangeFactory.INSTANCE.createExchange("com.xeiam.xchange.intersango.v1.IntersangoExchange");

    // Interested in the public market data feed (no authentication)
    TradeService accountService = imcex.getTradeService();

    // Get the latest ticker data showing BTC to USD
    AccountInfo accountInfo = accountService.getAccountInfo();

    System.out.printf("Account info: %s", accountInfo);
  }

}
