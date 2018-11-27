package howtoinvest.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * The following class implements the IStock interface and would be the representation of a stock of
 * a company in the real world. This would represent ownership of a part of the company. Ownership
 * is divided into shares which are maintained as part of the Stock class as a map which is a
 * mapping of the share(s) bought to the date on which the shares were bought. This class would
 * retrieve stock data for add shares to the stock depending on the flag set in the app.config file.
 * If the key READ_FROM_API is set to 1, then the IStockDataRetrieval object is initialized inorder
 * to use the API to retrieve stock data from queried results using a set URL. If the key is 0, then
 * the stock data is read from a CSV file provided by the user in a particular format.
 * <ul>
 * <li>
 * Each publicly traded company’s stock is given a unique “ticker symbol” and so each stock object
 * would have an identifier which is the tickerSymbol. The tickerSymbol is considered valid only if
 * data exists for a particular tickerSymbol. This would ensure that invalid tickerSymbol cannot
 * have share associated with them.
 * </li>
 * <li>
 * A TreeMap is maintained inorder to keep track of all the shares bought on a particular date and
 * this is added to the share list which would contain a date mapped to a Share object.
 * </li>
 * </ul>
 */
public class Stock implements IStock {

  /**
   * Initializing the variables pertaining to a stock object which would be a ticker symbol, list of
   * shares and the stock retrieval object.
   */
  private final String tickerSymbol;
  private TreeMap<Date, Share> shareList;
  private final IStockDataRetrieval stocksApi;

  /**
   * Constructs a stock object with the given ticker symbol and selects the source to retrieve stock
   * data based on the config file.
   *
   * @param tickerSymbol ticker symbol of the stock.
   * @throws IllegalArgumentException if the source data cannot be retrieved from the config file.
   */
  public Stock(String tickerSymbol) {
    /**
     * If the key READ_FROM_API is set to 1 in the app.config file, then retrieve stock data using
     * the AlphaVantage API, otherwise retrieve stock data from files.
     */
    if (loadPropertiesFromConfig()) {
      stocksApi = new AlphaVantage();
    } else {
      stocksApi = new FileStockDataReader();
    }

    /**
     * The validity of the stock ticker symbol is checked and throws an exception if the string
     * is empty/null or if stock data does not exist for a particular stock symbol.
     */
    if (!checkStringValidity(tickerSymbol)
            || !stocksApi.checkValidityOfTickerName(tickerSymbol)) {
      throw new IllegalArgumentException("Invalid stock name or ticker symbol");
    }
    this.tickerSymbol = tickerSymbol;
    this.shareList = new TreeMap<>();
  }

  @Override
  public double getStockCostBasis(String date) throws IllegalArgumentException {
    double costBasis = 0.0;
    Date costBasisDate = convertToDate(date);
    for (Map.Entry<Date, Share> entry : shareList.entrySet()) {
      /**
       * Calculating cost basis up until a particular date.
       */
      if (entry.getKey().after(costBasisDate)) {
        break;
      } else {
        costBasis += entry.getValue().getShareCostBasis();
      }
    }
    return costBasis;
  }

  @Override
  public double getStockValue(String date) throws IllegalArgumentException {
    Date stockValueDate = convertToDate(date);
    return this.getNumberOfShares(stockValueDate) * getSharePrice(this.tickerSymbol, stockValueDate);
  }

  /**
   * Returns the number of shares of a stock that were bought up until a particular input date which
   * is of the yyyy-mm-dd pattern in string format.
   *
   * @param date the date for which the number of shares are being queried for which is of the
   *             yyyy-mm-dd pattern in string format
   * @return the number of shares of a stock that were bought up until a particular input date which
   * is of the yyyy-mm-dd pattern in string format
   */
  private double getNumberOfShares(Date date) {
    double numberOfShare = 0;
    for (Map.Entry<Date, Share> entry : shareList.entrySet()) {
      /**
       * Calculating cost basis up until a particular date.
       */
      if (entry.getKey().after(date)) {
        break;
      } else {
        numberOfShare += entry.getValue().getNumberOfShares();
      }
    }
    return numberOfShare;
  }

  /**
   * Converts a date from string format of yyyy-mm-dd to a date object.
   *
   * @param date the date in string format of the pattern yyyy-mm-dd.
   * @return the corresponding date object which is the date that is in string format passed as as
   * argument.
   * @throws IllegalArgumentException if the input date in string format is in an incorrect format.
   */
  private Date convertToDate(String date) throws IllegalArgumentException {
    Date shareDate;
    try {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      format.setLenient(false);
      shareDate = format.parse(date);
    } catch (Exception ex) {
      throw new IllegalArgumentException("Invalid date format. Please enter date again.");
    }
    return shareDate;
  }

  @Override
  public double addShare(double amount, String date, double commission) throws IllegalArgumentException {
    /**
     * Amount for which shares are to be added cannot be negative or zero.
     */
    if (amount < 0) {
      throw new IllegalArgumentException("Invalid amount");
    }
    double sharePrice;
    Date shareDate = convertToDate(date);

    /**
     * Retrieve the share price for the stock using the stock retrieval object.
     */
    try {
      sharePrice = stocksApi.retrieveSharePrice(shareDate, this.tickerSymbol);
    } catch (Exception ex) {
      throw new IllegalArgumentException("Invalid date. Please enter date again."
              + ex.getMessage());
    }

    /**
     * Update the mapping of date to share if the share has been bought on the same date.
     */
    double noOfSharesBought = (amount / sharePrice);
    for (Date key : shareList.keySet()) {
      if (shareDate.equals(key)) {
        Share oldShare = shareList.get(key);
        Share shareBought = new Share((sharePrice * noOfSharesBought) + commission
                + oldShare.getShareCostBasis(), noOfSharesBought
                + oldShare.getNumberOfShares());
        shareList.put(key, shareBought);
        return noOfSharesBought;
      }
    }

    /**
     * If a share hasn't been bought on the same day, add the share to the list of shares bought
     * for the stock.
     */
    Share shareBought = new Share((sharePrice * noOfSharesBought) + commission, noOfSharesBought);
    shareList.put(shareDate, shareBought);
    return noOfSharesBought;
  }

  @Override
  public double getShares(String date) {
    double shares = 0;
    Date shareDate = convertToDate(date);
    for (Map.Entry<Date, Share> entry : shareList.entrySet()) {
      /**
       * Calculating cost basis up until a particular date.
       */
      if (entry.getKey().after(shareDate)) {
        break;
      } else {
        shares += entry.getValue().getNumberOfShares();
      }
    }
    return shares;
  }

  /**
   * A private helper method that checks the validity of strings i.e checks whether a string is null
   * or empty.
   *
   * @param string that string that is to be validated.
   * @return true if the string is valid and false otherwise.
   */
  private boolean checkStringValidity(String string) {
    return !(string == null || string.trim().isEmpty());
  }

  /**
   * Returns the single share price for a particular stock depending on the unique ticker symbol for
   * a particular date.
   *
   * @param date         the date for which we wish to look up the stock/share price which is of
   *                     yyyy-mm-dd format as a string.
   * @param tickerSymbol the ticker symbol that represents a stock of a company.
   * @return the single share price for a stock.
   * @throws IllegalArgumentException if the price data cannot be fetched from its source.
   */
  private double getSharePrice(String tickerSymbol, Date date) throws IllegalArgumentException {
    try {
      return stocksApi.retrieveSharePrice(date, tickerSymbol);
    } catch (ParseException | IllegalArgumentException ex) {
      throw new IllegalArgumentException("Cannot fetch current share price:" + ex.getMessage());
    }
  }

  /**
   * Returns true if the READ_FROM_API key in the app.config file is set to 1 and false otherwise.
   *
   * @return true if the READ_FROM_API key in the app.config file is set to 1 and false otherwise.
   * @throws IllegalArgumentException if the properties cannot be loaded from the config file.
   */
  private boolean loadPropertiesFromConfig() throws IllegalArgumentException {
    Properties prop = new Properties();
    String fileName = "app.config";
    InputStream is = null;
    try {
      is = new FileInputStream(fileName);
    } catch (FileNotFoundException ex) {
      throw new IllegalArgumentException("Config file not found");
    }
    try {
      prop.load(is);
    } catch (IOException ex) {
      throw new IllegalArgumentException("Cannot load properties of config file");
    }
    return prop.getProperty("READ_FROM_API").equalsIgnoreCase("1");
  }
}
