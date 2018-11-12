package howtoinvest.model;

public interface IPortfolio<K> {

  /**
   * Returns the portfolio data.
   * @return the portfolio data in the form a string.
   */
  String getPortfolioData();

  /**
   * Returns the total cost basis of the portfolio.
   *
   * @return the total cost basis of the portfolio.
   */
  double getStockCostBasis();

  /**
   * Returns the value of the portfolio.
   *
   * @return the value of the portfolio.
   */
  double getStockValue();

  /**
   * Adds a stock to the portfolio.
   * @param identifier the stock that is to be added to the portfolio
   * @param amount the amount for which the stock has to be added to the portfolio.
   */
  void addStock(String identifier, double amount);
}

