package howtoinvest.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AlphaVantage implements IStockDataRetrieval {
/**
 * The following class implements IStockDataRetrieval interface and all the methods in the
 * interface. This class would retrieve real-time stock data by parsing the web content using the
 * Alpha Vantage API (https://www.alphavantage.co/).
 */


  //The API key provided by the service: : https://www.alphavantage.co/
  private final String apiKey = "W0M1JOKC82EZEQA8";
  //Setting update interval for share prices so that share prices by the minute are retrieved.
  private final String updateInterval = "1min";
  //Setting the format for the date.
  private final String datePattern = "yyyy-MM-dd";
  private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
  private URL url;


  /**
   * Returns the share price of a company/organization based on the ticker symbol.
   *
   * @param tickerName the unique symbol representing a particular company/organization.
   * @return the live share price of a company/organization based on the ticker symbol at that
   * minute
   */
  private String getCurrentSharePrice(String tickerName) {
    setURL(tickerName, true);
    InputStream in = null;
    StringBuilder output = new StringBuilder();
    try {
      addToOutput(in, output, url);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("No price data found for " + tickerName);
    }
    return output.toString().split("\n")[1].split(",")[1];
  }


  /**
   * Checks the validity of the ticker symbol.
   *
   * @param tickerName a unique symbol representing a company/organization.
   * @return true if a particular symbol is valid symbol and false otherwise.
   */
  @Override
  public boolean checkValidityOfTickerName(String tickerName) {
    setURL(tickerName, true);
    InputStream in = null;
    StringBuilder output = new StringBuilder();
    /**
     * If the retrieved output has an error message, then the ticker name is invalid.
     */
    try {
      addToOutput(in, output, url);
    } catch (IllegalArgumentException e) {
      return false;
    }
    return !output.toString().toLowerCase().contains("Error Message".toLowerCase());
  }


  /**
   * Retrieves the share price for a particular date from the output of the set URL.
   * @param date the date for which we wish to look up the stock/share price.
   * @param tickerName a unique symbol representing a company/organization.
   * @return the share price for a particular date from the output of the set URL
   * @throws ParseException if the input date is of invalid format or if the retrieved content
   *                        is not in the right format to retrieve the share price for that date.
   */
  @Override
  public double retrieveSharePrice(String date, String tickerName) throws ParseException {

    setURL(tickerName, false);

    InputStream in = null;
    StringBuilder output = new StringBuilder();
    try {
      addToOutput(in, output, url);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("No price data found for " + tickerName);
    }


    /**
     * Converting the date to retrieve data to date format.
     */
    String[] dailySharePrices = output.toString().split("\n");

    if (dailySharePrices.length == 0 || dailySharePrices.length == 1) {
      throw new IllegalArgumentException("No share prices provided.");
    }

    Date dateToFind = simpleDateFormat.parse(date);
    Date currentDate;
    double closestShareValue = Double.parseDouble(dailySharePrices[1].split(",")[1]);

    /**
     * If the date to find is today, then return today or the latest share value as the
     * share value.
     */
    if (dateToFind.equals(simpleDateFormat.parse(simpleDateFormat.format(new Date())))) {
      return Double.parseDouble(this.getCurrentSharePrice(tickerName));
    }
    /**
     * Cannot fetch share value for a future date that is input.
     */
    else if (dateToFind.after(simpleDateFormat.parse(simpleDateFormat.format(new Date())))) {
      throw new IllegalArgumentException("Cannot fetch share value for a future date");
    }


    /**
     * Cannot fetch further history.
     */
    if (dateToFind.before(simpleDateFormat.parse(dailySharePrices[dailySharePrices.length - 1]
            .split(",")[0]))) {
      throw new IllegalArgumentException("Share prices do not exist for given date.");
    }


    for (int day = dailySharePrices.length - 1; day >= 1; day--) {
      currentDate = simpleDateFormat.parse(dailySharePrices[day].split(",")[0]);
      if (dateToFind.equals(currentDate)) {
        return Double.parseDouble(dailySharePrices[day].split(",")[4]);
      }
      /**
       * If the date to be found is after the current day's share value then we update
       * the closest share value.
       */
      else if (dateToFind.after(currentDate)) {
        closestShareValue = Double.parseDouble(dailySharePrices[day].split(",")[4]);
      }
    }
    return closestShareValue;
  }


  /**
   * Adding output from the url stream to the appendable object.
   *
   * @param in  the InputStream object for reading the data.
   * @param out the Appendable object for transmitting data.
   * @param url the URL object for retrieving the content by the set URL.
   */
  private void addToOutput(InputStream in, Appendable out, URL url) {
    try {
      in = url.openStream();
      int b;
      while ((b = in.read()) != -1) {
        out.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data.");
    }
  }


  /**
   * Setting the URL based on the boolean flag for whether we require a daily or minute update.
   *
   * @param tickerName the unique symbol representing a particular company/organization.
   * @param intraday   true if a minute interval update is required and false if daily updates are
   *                   required.
   */
  private void setURL(String tickerName, boolean intraday) {
    String urlSuffix = "";
    String function = "TIME_SERIES_DAILY";
    if (intraday) {
      urlSuffix = "&interval=" + updateInterval;
      function = "TIME_SERIES_INTRADAY";
    }
    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=" + function
              + "&outputsize=full"
              + "&symbol"
              + "=" + tickerName + urlSuffix + "&apikey=" + apiKey
              + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }
  }
}
