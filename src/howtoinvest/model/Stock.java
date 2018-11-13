package howtoinvest.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class Stock implements IStock {
  private final String tickerSymbol;
  private TreeMap<Date, Share> shareList = new TreeMap<>();
  private final IStockDataRetrieval stocksApi;

  public Stock(String tickerSymbol) {
    if(loadPropertiesFromConfig()){
      stocksApi = new AlphaVantageDemo();
    }
    else{
      stocksApi = new FileStockDataReader();
    }
    if (!checkStringValidity(tickerSymbol) ||
            !stocksApi.checkValidityOfTickerName(tickerSymbol)) {
      throw new IllegalArgumentException("Invalid stock name or ticker symbol");
    }
    this.tickerSymbol = tickerSymbol;
  }

  @Override
  public double getStockCostBasis(String date) {
    double costBasis = 0.0;
    Date costBasisDate;
    try {
      costBasisDate = new SimpleDateFormat("yyyy-MM-dd").
              parse(date);
    } catch (Exception ex) {
      throw new IllegalArgumentException("Invalid date format. Please enter date again.");
    }
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
  public double getStockValue(String date) {

    return this.getNumberOfShares("") * getSharePrice(this.tickerSymbol);
  }

  private double getNumberOfShares(String date) {
    double numberOfShare = 0;
    Date costBasisDate;
    try {
      costBasisDate = new SimpleDateFormat("yyyy-MM-dd").
              parse(date);
    } catch (Exception ex) {
      throw new IllegalArgumentException("Invalid date format. Please enter date again.");
    }
    for (Map.Entry<Date, Share> entry : shareList.entrySet()) {
      /**
       * Calculating cost basis up until a particular date.
       */
      if (entry.getKey().after(costBasisDate)) {
        break;
      } else {
        numberOfShare += entry.getValue().getNumberOfShares();
      }
    }
    return numberOfShare;
  }

  @Override
  public String addShare(double amount, String date) throws IllegalArgumentException {
    if(amount <= 0){
      throw new IllegalArgumentException("Invalid amount");
    }
    double sharePrice;
    Date shareDate;
    try {
      shareDate = new SimpleDateFormat("yyyy-MM-dd").
              parse(date);
    } catch (Exception ex) {
      throw new IllegalArgumentException("Invalid date format. Please enter date again.");
    }


    try {
      sharePrice = stocksApi.retrieveSharePrice(date, this.tickerSymbol);
    } catch (Exception ex) {
      throw new IllegalArgumentException("Invalid date. Please enter date again."
              + ex.getMessage());
    }

    double noOfSharesBought = (amount / sharePrice);
    for (Date key : shareList.keySet()) {
      if (shareDate.equals(key)) {
        Share oldShare = shareList.get(key);
        Share shareBought = new Share((sharePrice * noOfSharesBought)
                + oldShare.getShareCostBasis(), noOfSharesBought
                + oldShare.getNumberOfShares());
        shareList.put(key, shareBought);
      }
    }
    Share shareBought = new Share(sharePrice * noOfSharesBought, noOfSharesBought);
    shareList.put(shareDate, shareBought);
    return String.format("%.2f shares of %s bought on %s for $%.2f",noOfSharesBought,
            this.tickerSymbol,date,amount);
  }

  @Override
  public String getStockData() {
    String stockData = "";
    double totalShares = 0;
    double totalCostBasis = 0;
    for (Map.Entry<Date, Share> entry : shareList.entrySet()) {
      Share value = entry.getValue();
      totalShares += value.getNumberOfShares();
      totalCostBasis += value.getShareCostBasis();
    }
    stockData += String.format("%.2f shares of %s for a total investment of $%.2f\n",
            totalShares, this.tickerSymbol, totalCostBasis);
    return stockData;
  }

  /**
   * A private helper method that checks the validity of strings i.e checks whether a string is null
   * or empty.
   *
   * @param string that string that is to be validated.
   * @return true if the string is valid and false otherwise.
   */
  private boolean checkStringValidity(String string) {
    if (string == null || string.trim().isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * Returns the single share price for a particular stock depending on the unique ticker symbol.
   *
   * @param tickerSymbol the ticker symbol that represents a stock of a company.
   * @return the single share price for a stock.
   */
  private double getSharePrice(String tickerSymbol) {
    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    try{
      return stocksApi.retrieveSharePrice(date,tickerSymbol);
    } catch(ParseException ex){
      throw new IllegalArgumentException("Cannot fetch current share price due to parse failure.");
    }
  }

  private boolean loadPropertiesFromConfig(){
    Properties prop = new Properties();
    String fileName = "app.config";
    InputStream is = null;
    try {
      is = new FileInputStream(fileName);
    } catch (FileNotFoundException ex) {
    }
    try {
      prop.load(is);
    } catch (IOException ex) {
    }
    return prop.getProperty("READ_FROM_API").equalsIgnoreCase("1");
  }
}
