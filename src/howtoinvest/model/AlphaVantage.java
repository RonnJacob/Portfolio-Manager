package howtoinvest.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * The following class implements IStockDataRetrieval interface and all the methods in the
 * interface. This class would retrieve real-time stock data by parsing the web content using the
 * Alpha Vantage API (https://www.alphavantage.co/). Stock data retrieval would be handled depending
 * on the following scenarios:
 * <ul>
 * <li>
 * If the input date is too old for there to be any stock data pertaining to that date, an exception
 * would be thrown saying that the stock data cannot be fetched.
 * </li>
 * <li>
 * If the input date is of the current date, then the opening share price for that minute is
 * returned.
 * </li>
 * <li>
 * If the input date matches with a date in the retrieved stock data, then the price at closing time
 * is retrieved.
 * </li>
 * <li>
 * If the input date is after the current date, then a share price cannot be retrieved as the share
 * price for a future date cannot be fetched and an exception would be thrown.
 * </li>
 * <li>
 * If the input date is not a future date and is not present in the retrieved stock data, then the
 * price for the last stock exchange working date would be retrieved.
 * </li>
 * </ul>
 */
public class AlphaVantage implements IStockDataRetrieval {

  //The API key provided by the service: : https://www.alphavantage.co/
  private final String apiKey = "W0M1JOKC82EZEQA8";
  //Setting update interval for share prices so that share prices by the minute are retrieved.
  private final String updateInterval = "1min";
  //Setting the format for the date.
  private final String extension = "_AlphaVantage.csv";
  private final String datePattern = "yyyy-MM-dd";
  private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
  private URL url;
  private StringBuilder output = new StringBuilder();


  /**
   * Returns the share price of a company/organization based on the ticker symbol.
   *
   * @param tickerName the unique symbol representing a particular company/organization.
   * @return the live share price of a company/organization based on the ticker symbol at that
   * minute.
   */
  private String getCurrentSharePrice(String tickerName) throws IllegalArgumentException {
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
   *
   * @param date       the date for which we wish to look up the stock/share price.
   * @param tickerName a unique symbol representing a company/organization.
   * @return the share price for a particular date from the output of the set URL
   * @throws ParseException           if the input date is of invalid format or if the retrieved
   *                                  content is not in the right format to retrieve the share price
   *                                  for that date.
   * @throws IllegalArgumentException if thr date or tickerName is invalid.
   */
  @Override
  public double retrieveSharePrice(Date date, String tickerName)
          throws ParseException, IllegalArgumentException {

    File stockDataFile = new File(tickerName + extension);
    Scanner scanner;
    setURL(tickerName, false);
    if (!stockDataFile.exists()) {
      writeStockDataToCSVFile(url, stockDataFile);
    } else if (!checkIfLatestStockData(stockDataFile)) {
      writeStockDataToCSVFile(url, stockDataFile);
    }

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


    if (dailySharePrices.length == 0 || dailySharePrices.length == 1) {
      throw new IllegalArgumentException("No share prices provided.");
    }

    Date currentDate;
    Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
    double closestShareValue = Double.parseDouble(dailySharePrices[1].split(",")[1]);

    /**
     * If the date to find is today, then return today or the latest share value as the
     * share value.
     */
    if (date.equals(today)) {
      return Double.parseDouble(this.getCurrentSharePrice(tickerName));
    }
    /**
     * Cannot fetch share value for a future date that is input.
     */
    else if (date.after(today)) {
      throw new IllegalArgumentException("Cannot fetch share value for a future date");
    }


    /**
     * Cannot fetch further history.
     */
    if (date.before(simpleDateFormat.parse(dailySharePrices[dailySharePrices.length - 2]
            .split(",")[0]))) {
      throw new IllegalArgumentException("Share prices do not exist for given date.");
    }


    for (int day = dailySharePrices.length - 2; day >= 1; day--) {
      currentDate = simpleDateFormat.parse(dailySharePrices[day].split(",")[0]);
      if (date.equals(currentDate)) {
        return Double.parseDouble(dailySharePrices[day].split(",")[4]);
      }
      /**
       * If the date to be found is after the current day's share value then we update
       * the closest share value.
       */
      else if (date.after(currentDate)) {
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
   * @throws IllegalArgumentException if the stock data cannot be retrived with the url.
   */
  private void addToOutput(InputStream in, Appendable out, URL url)
          throws IllegalArgumentException {

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
   * Writes the stock data retrieved from the API to .
   *
   * @param url the URL object for retrieving the content by the set URL.
   * @throws IllegalArgumentException if the stock data cannot be retrived with the url.
   */
  private void writeStockDataToCSVFile(URL url, File of)
          throws IllegalArgumentException {

    FileWriter writer;
    try {
      of.createNewFile(); // if file already exists will do nothing
    } catch (IOException ex) {

    }
    try (Scanner scanner = new Scanner(url.openStream(),
            StandardCharsets.UTF_8.toString())) {
      writer = new FileWriter(of, false);
      String input = "";
      scanner.useDelimiter("\\r\\n");
      while (scanner.hasNext()) {
        input = scanner.hasNext() ? scanner.next() : "";
        writer.append(input);
        writer.append(System.lineSeparator());
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  /**
   * Setting the URL based on the boolean flag for whether we require a daily or minute update.
   *
   * @param tickerName the unique symbol representing a particular company/organization.
   * @param intraday   true if a minute interval update is required and false if daily updates are
   *                   required.
   * @throws RuntimeException if the API stops working or errors.
   */
  private void setURL(String tickerName, boolean intraday) throws RuntimeException {
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

  /**
   * Checks whether the stock data file is up-to-date.
   *
   * @param stockDataFile the stock data file that is to be checked.
   * @return true if the file is up-to-date anf false otherwise.
   * @throws ParseException if the date for which the file has to be checked cannot be parsed.
   */
  private boolean checkIfLatestStockData(File stockDataFile) throws ParseException {
    Scanner scanner;
    try {
      scanner = new Scanner(stockDataFile);
    } catch (IOException ex) {
      throw new IllegalArgumentException("Cannot read from file.");
    }
    StringBuilder output = new StringBuilder();
    /**
     * Storing the output of the csv file to an appendable object i.e StringBuilder in this case.
     */
    scanner.useDelimiter("\n");
    int counter = 0;
    while (scanner.hasNext() && counter <= 2) {
      output.append(scanner.next() + "\n");
      counter += 1;
    }
    String outputDates = output.toString().split("\n")[1].split(",")[0];
    Date latestDate = simpleDateFormat.parse(outputDates);
    if (latestDate.before(simpleDateFormat.parse(simpleDateFormat.format(new Date())))) {
      return false;
    }
    return true;
  }
}
