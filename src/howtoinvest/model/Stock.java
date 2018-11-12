package howtoinvest.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Stock implements IStock {
  private final String tickerSymbol;
  private TreeMap<Date, Share> shareList = new TreeMap<>();
  private final AlphaVantageDemo stocksApi = new AlphaVantageDemo();
  private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

  public Stock(String tickerSymbol) {
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
    return this.getNumberOfShares() * getSharePrice(this.tickerSymbol);
  }

  @Override
  public double getNumberOfShares() {
    double numberOfShare = 0;
    for (Share shares : shareList.values()) {
      numberOfShare += shares.getNumberOfShares();
    }
    return numberOfShare;
  }

  @Override
  public void addShare(double amount, String date) throws IllegalArgumentException {
    double sharePrice;
    Date shareDate;
    try {
      shareDate = new SimpleDateFormat("yyyy-MM-dd").
              parse(date);
    } catch (Exception ex) {
      throw new IllegalArgumentException("Invalid date format. Please enter date again.");
    }

    if (date.equals(dateFormatter.format(new Date()))) {
      shareDate = new Date();
      sharePrice = getSharePrice(this.tickerSymbol);
    } else {
      try {
        sharePrice = stocksApi.retrieveSharePrice(date, this.tickerSymbol);
      } catch (Exception ex) {
        throw new IllegalArgumentException("Invalid date. Please enter date again."
                + ex.getMessage());
      }
    }
    double noOfSharesBought = (amount / sharePrice);
    for (Date key : shareList.keySet()) {
      if (shareDate.equals(key)) {
        Share oldShare = shareList.get(key);
        Share shareBought = new Share((sharePrice * noOfSharesBought)
                + oldShare.getShareCostBasis(), noOfSharesBought
                + oldShare.getNumberOfShares());
        shareList.put(key, shareBought);
        return;
      }
    }
    Share shareBought = new Share(sharePrice * noOfSharesBought, noOfSharesBought);
    shareList.put(shareDate, shareBought);
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
    return Double.parseDouble(stocksApi.getCurrentSharePrice(tickerSymbol));
  }
}
