package howtoinvest.model;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * This interface represents a financial portfolio. A financial portfolio is used to keep track of
 * the investments you have made. You can invest by buying stocks of a company. Basically a
 * portfolio is the collection of stocks. This interface provides you with means to retrieve the
 * data of your portfolio, the amount of money you have invested in the portfolio and the value of
 * your investment at a given date in Dollars. Also gives you the functionality to add an investment
 * by buying stocks with commission.Commission fees are charged by the brokerage service that an
 * investor typically use to buy and sell stocks. These fees are usually charged per
 * transaction.Also gives the functionality to Invest a fixed amount into an existing portfolio
 * containing multiple stocks, using a specified weight for each stock in the portfolio. The
 * following interface provides operations relevant to portfolios which would include retrieving the
 * stocks and the number of shares present in the portfolio. The cost basis and value of a portfolio
 * can be retrieved as well and the interface provides the functionality to do so. The commission
 * for a transaction is divided in three categories (low, medium and high) and the commission value
 * is retrieved from a .csv file which contains the values corresponding to these settings for
 * commission. Adding or investing in a stock in the portfolio would include this commission charge
 * and this would be reflected in the cost basis and value of the portfolio. The other features
 * would include investing on stocks within a particular portfolio with a set of weights for each
 * stock in the portfolio and buying/adding of stocks within the portfolio.
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


  /**
   * Returns the total cost basis of the portfolio at a given date.
   *
   * @param date date for which the cost basis has to be calculated.
   * @return the total cost basis of the portfolio.
   * @throws IllegalArgumentException if the stock cost basis cannot be fetched.
   */
  double getStockCostBasis(String date) throws IllegalArgumentException;

  /**
   * Returns the total value of the portfolio at a given date.
   *
   * @param date date for which the value has to be calculated.
   * @return the total value of the portfolio.
   * @throws IllegalArgumentException if the stock value cannot be fetched.
   */
  double getStockValue(String date) throws IllegalArgumentException;

  /**
   * Adds a stock to the portfolio.
   *
   * @param identifier the stock that is to be added to the portfolio
   * @param amount     the amount for which the stock has to be added to the portfolio.
   * @param date       when the stock has to be added.
   * @param commission the commission amount.
   * @return Returns a string that contains the number of shares of a stock bought for an amount at
   * a particular date.
   * @throws IllegalArgumentException if the stock ticker symbol, amount, commission or date is
   *                                  invalid.
   */
  double addStock(String identifier, double amount, String date, double commission)
          throws IllegalArgumentException;

  /**
   * Returns the commission fee according the input string.
   *
   * @param commission input commission string.
   * @return commission.
   * @throws IllegalArgumentException if the input is invalid.
   */
  double getCommission(String commission) throws IllegalArgumentException;

  /**
   * Invest a fixed amount into an existing portfolio containing multiple stocks, using a specified
   * weight for each stock in the portfolio. Returns a hashmap with the all the stocks and the
   * number of shares bought for each of them.
   *
   * @param amount       amount to be invested.
   * @param weights      stocks with their corresponding weights.
   * @param equalWeights flag to determine if the weights should be equal.
   * @param date         date to invest.
   * @param commission   commission fees.
   * @return a hashmap with the all the stocks and the number of shares bought for each of them.
   * @throws IllegalArgumentException if the weights are invalid.
   */
  HashMap<String, Double> invest(double amount, TreeMap<String, Double> weights,
                                 boolean equalWeights, String date, double commission)
          throws IllegalArgumentException;
}

