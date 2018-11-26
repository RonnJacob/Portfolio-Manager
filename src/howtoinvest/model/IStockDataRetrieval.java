package howtoinvest.model;

import java.text.ParseException;
import java.util.Date;

/**
 * The following interface represents operations related to handling stock data from a source. This
 * would include checking if a company/organization exists using the unique symbol give. Another
 * functionality provided by the interface would be retrieving share price based on the a given date
 * and the ticker symbol for that stock.
 */
public interface IStockDataRetrieval {

  /**
   * Checks the validity or existence of a particular stock for a company.
   *
   * @param tickerName the unique “ticker symbol" given to a company's stock.
   * @return true if the stock or stock data exists for a given "ticker symbol".
   */
  boolean checkValidityOfTickerName(String tickerName);

  /**
   * Retrieves the share price for a particular date from stock data retrieved from a source.
   *
   * @param date       the date for which we wish to look up the stock/share price.
   * @param tickerName a unique symbol representing a company/organization.
   * @return the share price for a particular date from the source.
   * @throws ParseException           if the input date is of invalid format or if the retrieved
   *                                  content is not in the right format to retrieve the share price
   *                                  for that date.
   * @throws IllegalArgumentException if thr date or tickerName is invalid.
   */
  double retrieveSharePrice(Date date, String tickerName)
          throws ParseException, IllegalArgumentException;
}