package howtoinvest.model;

import java.io.StringReader;
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
    try{
      costBasisDate  =new SimpleDateFormat("yyyy-MM-dd").
              parse(date);
    } catch(Exception ex){
      throw new IllegalArgumentException("Invalid date format. Please enter date again.");
    }
    for(Map.Entry<Date,Share> entry : shareList.entrySet()) {
      /**
       * Calculating cost basis up until a particular date.
       */
      if(entry.getKey().after(costBasisDate)){
        break;
      }
      else{
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
  public long getNumberOfShares(){
    long numberOfShare = 0;
    for(Share shares: shareList.values()){
      numberOfShare += shares.getNumberOfShares();
    }
    return numberOfShare;
  }


  @Override
  public void addShare(double amount, String date) throws IllegalArgumentException {
    double sharePrice;
    Date shareDate;
    try{
      shareDate  =new SimpleDateFormat("yyyy-MM-dd").
              parse(date);
    } catch(Exception ex){
      throw new IllegalArgumentException("Invalid date format. Please enter date again.");
    }

    if (date.equals(dateFormatter.format(new Date()))) {
      shareDate = new Date();
      sharePrice = getSharePrice(this.tickerSymbol);
    }

    else {
      try {
        sharePrice = stocksApi.retrieveSharePrice(date, this.tickerSymbol);
      } catch (Exception ex) {
        throw new IllegalArgumentException("Invalid date. Please enter date again."
                + ex.getMessage());
      }
    }
    long noOfSharesBought = (long) (amount / sharePrice);
    Share shareBought = new Share(sharePrice * noOfSharesBought, noOfSharesBought);
    shareList.put(shareDate,shareBought);
  }


  @Override
  public String getStockData() {
    String stockTransactions = "";
    for(Map.Entry<Date,Share> entry : shareList.entrySet()) {
      Date key = entry.getKey();
      Share value = entry.getValue();
      stockTransactions += String.format("%d shares bought on %s at %.2f\n",
              value.getNumberOfShares(),dateFormatter.format(key),value.getShareCostBasis());
    }
    return stockTransactions;
  }


  /**
   * A private helper method that checks the validity of strings i.e checks whether a string is null
   * or empty.
   *
   * @param string that string that is to be validated.
   * @return true if the string is valid and false otherwise.
   */
  private boolean checkStringValidity(String string) {
    if (string == null || string.equals("")) {
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
