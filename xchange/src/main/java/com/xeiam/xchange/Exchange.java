package com.xeiam.xchange;

/**
 * <p>Interface to provide the following to applications:</p>
 * <ul>
 * <li>Entry point to the XChange APIs</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public interface Exchange {


  /**
   * @return A default ExchangeSpecification to use during the creation process if one is not supplied
   */
  ExchangeSpecification getDefaultExchangeSpecification();

  /**
   * Applies any exchange specific parameters
   *
   * @param exchangeSpecification The exchange specification
   */
  void applySpecification(ExchangeSpecification exchangeSpecification);

  /**
   * <p>A market data service typically consists of a regularly updated list of the
   * available prices for the various symbols</p>
   *
   * @return The exchange's market data service
   */
  MarketDataService getMarketDataService();

}
