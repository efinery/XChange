package com.xeiam.xchange.intersango.v1;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.MarketDataService;
import com.xeiam.xchange.exchange.BaseExchange;
import com.xeiam.xchange.intersango.v1.service.marketdata.IntersangoPublicHttpMarketDataService;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Exchange implementation to provide the following to applications:</p>
 * <ul>
 * <li>A wrapper for the MtGox Bitcoin exchange API</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class IntersangoExchange extends BaseExchange implements Exchange {

  private MarketDataService marketDataService;

  /**
   * Default constructor for ExchangeFactory
   */
  public IntersangoExchange() {
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    if (exchangeSpecification == null) {
      exchangeSpecification = getDefaultExchangeSpecification();
    }
    this.marketDataService = new IntersangoPublicHttpMarketDataService(exchangeSpecification);
  }

  public ExchangeSpecification getDefaultExchangeSpecification() {

    Map<String, Object> parameters = new HashMap<String, Object>();

    parameters.put(ExchangeSpecification.API_URI, "https://mtgox.com");
    parameters.put(ExchangeSpecification.API_VERSION, "1");

    return new ExchangeSpecification(this.getClass().getCanonicalName(), parameters);
  }

  @Override
  public MarketDataService getMarketDataService() {
    return marketDataService;
  }

  // Package local for testing
  void setMarketDataService(MarketDataService marketDataService) {
    this.marketDataService = marketDataService;
  }


}
