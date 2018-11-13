package howtoinvest.model;

public interface IStock {


  /**
   * Returns the total cost basis of the stock(s) of a company.
   *
   * @return the total cst basis of the stock(s) of a company.
   */
  double getStockCostBasis(String date);

  /**
   * Returns the value of the stock(s) of a company.
   *
   * @return the value of the stock(s) of a company.
   */
  double getStockValue(String date);

//  /**
//   * Returns the number of shares for a particular stock of a company.
//   *
//   * @return the number of shares for a particular stock of a company.
//   */
//  double getNumberOfShares();

  /**
   * Adding shares to a particular stock of a company worth an input amount.
   *
   * @param amount the amount for which shares of a company are to be bought.
   * @param date   the date for which we want to add the share that was bought.
   * @return a string that mentions the number of shares bought for the input amount.
   * @throws IllegalArgumentException if shares cannot be bought successfully for a particular
   *                                  company.
   */
  String addShare(double amount, String date) throws IllegalArgumentException;

  /**
   * Returns the stock data in the form of a string.
   *
   * @return the stock data in the form of a string.
   */
  String getStockData();
}
