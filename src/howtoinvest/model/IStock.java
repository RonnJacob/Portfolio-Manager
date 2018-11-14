package howtoinvest.model;

/**
 * The following interface represents a stock of a company and operations pertaining to the stock of
 * a company.In its simplest form, a stock of a company is simply a part of ownership in that
 * company. Ownership is divided into shares, where a share represents a fraction of the total
 * ownership. For example, Apple has about 5.2B shares. So if you own 100 shares, you own about
 * 1.9𝑥10−6 1.9 x 10 − 6 % of the company (and it would be worth about $20800 today). Anyone who
 * holds a share is considered a shareholder and is an investor. An investor sends money to the
 * company to buy some of its stock, and gets part ownership in return. The total money invested in
 * the stock (money spent buying it) is called the cost basis of the purchase. The value of the
 * stock on a particular day is the money the investor would receive if he/she sold the stock on
 * that day. When the company performs well (e.g. it makes a profit, it expands its business, does
 * business with more money, etc.) the price of the stock goes up because its value in the stock
 * market increases (everybody wants to own that stock). When an investor sells stocks whose prices
 * have risen above their cost bases, they make a profit (they get more money than they had paid to
 * buy the stock earlier). The interface provides methods to retrieve the total cost basis of a
 * stock up until a particular date which would retrieve the cost basis of shares that have been
 * bought up until a particular date. Similarly the stock value can be retrieved for a stock of a
 * company up until a particular date and would be returned. It is mandatory that the date is of
 * string format yyyy-mm-dd. An IllegalArgumentException would be thrown in any case that the date
 * is of invalid format or a date for which stock data cannot be added. Exceptions would also be
 * thrown while adding share if the amount to buy shares is negative.
 */
public interface IStock {


  /**
   * Returns the total cost basis of the stock of a company at a particular input date of format
   * yyyy-mm-dd.
   *
   * @param date the date for which we want to retrieve stock cost basis which is of yyyy-mm-dd
   *             pattern in string format.
   * @return the total cost basis of the stock of a company at a particular input date of format
   *         yyyy-mm-dd.
   * @throws IllegalArgumentException if the cost basis cannot be fetched for the given date.
   */
  double getStockCostBasis(String date);

  /**
   * Returns the value of the stock of a company at a particular input date of format yyyy-mm-dd.
   *
   * @param date the date for which we want to retrieve stock value which is of yyyy-mm-dd pattern
   *             in string format.
   * @return the value of the stock of a company at a particular input date of format yyyy-mm-dd.
   * @throws IllegalArgumentException if the stock value cannot be fetched from its source.
   */
  double getStockValue(String date);

  /**
   * Adding shares to a particular stock of a company worth an input amount. for a particular date
   * of format yyyy-mm-dd and returns a string mentioning the number of shares bought for the amount
   * for that particular date.
   *
   * @param amount the amount for which shares of a company are to be bought.
   * @param date   the date for which we want to add the share that was bought which is of
   *               yyyy-mm-dd pattern in string format.
   * @return a string that mentions the number of shares bought for the input amount.
   * @throws IllegalArgumentException if shares cannot be bought successfully for a particular
   *                                  company.
   */
  String addShare(double amount, String date) throws IllegalArgumentException;

  /**
   * Returns the stock data in the form of a string which would be the total cost basis and the
   * total value of the stock up until the current time.
   *
   * @return the stock data in the form of a string which would be the total cost basis and the
   *         total value of the stock up until the current time.
   */
  String getStockData();
}
