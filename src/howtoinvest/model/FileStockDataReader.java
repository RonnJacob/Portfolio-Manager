package howtoinvest.model;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * The following class implements IStockDataRetrieval interface and all the methods in the
 * interface. This class would retrieve stock data from a .csv file based on the ticker symbol which
 * would be the name of the .csv file. The class offers operations to check the presence of a stock
 * data file and for retrieving the share price for a given date in string format. An exception
 * would be thrown if the input date is not in the yyyy-mm-dd format. Stock data retrieval would be
 * handled depending on the following scenarios:
 * <ul>
 * <li>
 * If the input date is too old for there to be any stock data pertaining to that date, an exception
 * would be thrown saying that the stock data cannot be fetched.
 * </li>
 * <li>
 * If the input date matches with a date in the retrieved stock data, then the price at closing time
 * is retrieved.
 * </li>
 * <li>
 * If the input date is not a future date and is not present in the retrieved stock data, then the
 * price for the last stock exchange working date would be retrieved.
 * </li>
 * <li>
 * If the input date is after the current date, then a share price cannot be retrieved as the share
 * price for a future date cannot be fetched and an exception would be thrown.
 * </li>
 * </ul>
 */
public class FileStockDataReader implements IStockDataRetrieval {
  private final String extension = ".csv";
  private final String datePattern = "yyyy-MM-dd";
  private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
  private StringBuilder output = new StringBuilder();

  /**
   * Checks whether a file for a particular ticker name representing a stock exists.
   *
   * @param tickerName the unique symbol representing a particular company/organization.
   * @return the share price of a company/organization based on the ticker symbol at that date or
   * the date that is closest to that date.
   */
  @Override
  public boolean checkValidityOfTickerName(String tickerName) {
    File f = new File(tickerName + extension);
    return (f.exists() && !f.isDirectory());
  }

  /**
   * Retrieves the share price for a particular date from stock data in a .csv file.
   *
   * @param date       the date for which we wish to look up the stock/share price.
   * @param tickerName a unique symbol representing a company/organization.
   * @return the share price for a particular date from the file.
   * @throws ParseException if the input date is of invalid format or if the retrieved content is
   *                        not in the right format to retrieve the share price for that date.
   */
  @Override
  public double retrieveSharePrice(String date, String tickerName) throws ParseException {
    /**
     * Initializing a scanner object for reading the .csv file.
     */
    Scanner scanner;
    try {
      scanner = new Scanner(new File(tickerName + extension));
    } catch (IOException ex) {
      throw new IllegalArgumentException("Cannot read from file.");
    }

    /**
     * Storing the output of the csv file to an appendable object i.e StringBuilder in this case.
     */
    scanner.useDelimiter(",");
    while (scanner.hasNext()) {
      this.output.append(scanner.next() + ",");
    }

    /**
     * Converting the date to retrieve data to date format.
     */
    String[] dailySharePrices = output.toString().split("\n");

    if (dailySharePrices.length == 0) {
      throw new IllegalArgumentException("No share prices provided.");
    }

    Date dateToFind = simpleDateFormat.parse(date);
    Date currentDate;
    double closestShareValue = Double.parseDouble(dailySharePrices[0].split(",")[1]);


    /**
     * If the date to find is today, then return today or the latest share value as the
     * share value.
     */
    if (dateToFind.equals(simpleDateFormat.parse(simpleDateFormat.format(new Date())))) {
      return closestShareValue;
    }
    /**
     * Cannot fetch share value for a future date that is input.
     */
    else if (dateToFind.after(simpleDateFormat.parse(simpleDateFormat.format(new Date())))) {
      throw new IllegalArgumentException("Cannot fetch share value for a future date.");
    }


    /**
     * Cannot fetch further history.
     */
    if (dateToFind.before(simpleDateFormat.parse(dailySharePrices[dailySharePrices.length - 1]
            .split(",")[0]))) {
      throw new IllegalArgumentException("Share prices do not exist for given date.");
    }

    for (int day = dailySharePrices.length - 1; day >= 0; day--) {
      currentDate = simpleDateFormat.parse(dailySharePrices[day].split(",")[0]);
      if (dateToFind.equals(currentDate)) {
        return Double.parseDouble(dailySharePrices[day].split(",")[1]);
      }
      /**
       * If the date to be found is after the current day's share value then we update
       * the closest share value.
       */
      else if (dateToFind.after(currentDate)) {
        closestShareValue = Double.parseDouble(dailySharePrices[day].split(",")[1]);
      }
    }
    return closestShareValue;
  }
}
