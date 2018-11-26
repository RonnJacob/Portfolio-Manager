package howtoinvest.model;

import java.util.HashMap;

/**
 * This interface represents a financial portfolio. A financial portfolio is used to keep track of
 * the investments you have made. You can invest by buying stocks of a company. Basically a
 * portfolio is the collection of stocks. This interface provides you with means to retrieve the
 * data of your portfolio, the amount of money you have invested in the portfolio and the value of
 * your investment at a given date in Dollars. Also gives you the functionality to add an investment
 * by buying stocks.
 *
 * @param <K> the type of the data of the portfolio.
 */
public interface IPortfolio<K> {

  /**
   * Returns the portfolio data.
   *
   * @return the portfolio data in the form a string.
   */
  HashMap<String, Double> getPortfolioData(String date);

//  /**
//   * Returns the total cost basis of the portfolio.
//   *
//   * @param date for which the cost basis has to be fetched.
//   * @return the total cost basis of the portfolio.
//   * @throws IllegalArgumentException if the stock cost basis and value cannot be fetched.
//   */
//  String getStockCostBasisAndStockValue(String date);

  double getStockCostBasis(String date) throws IllegalArgumentException;

  double getStockValue(String date) throws IllegalArgumentException;
  /**
   * Adds a stock to the portfolio.
   *
   * @param identifier the stock that is to be added to the portfolio
   * @param amount     the amount for which the stock has to be added to the portfolio.
   * @param date when the stock has to be added.
   * @return Returns a string that contains the number of shares of a stock bought for an amount at
   *         a particular date.
   * @throws IllegalArgumentException if the stock ticker symbol, amount or date is invalid.
   */
  double addStock(String identifier, double amount, String date, double commission);

  double getCommission(String commission);
}

