package howtoinvest.model;

public interface IPortfolio<K> {

  /**
   * Returns the portfolio data.
   *
   * @return the portfolio data in the form a string.
   */
  String getPortfolioData();

  /**
   * Returns the total cost basis of the portfolio.
   *
   * @return the total cost basis of the portfolio.
   */
  String getStockCostBasisAndStockValue(String date);

  /**
   * Adds a stock to the portfolio.
   *
   * @param identifier the stock that is to be added to the portfolio
   * @param amount     the amount for which the stock has to be added to the portfolio.
   * @return Returns a string that contains the number of shares of a stock bought for an amount at
   * a particular date.
   */
  String addStock(String identifier, double amount, String date);
}

