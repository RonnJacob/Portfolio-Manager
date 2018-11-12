package howtoinvest.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The following Stock class implements the IStock interface. The operations that are relevant to a
 * stock include retrieving the stock's current cost basis, retrieving the share price of a current
 * stock,
 */
public class Stock implements IStock {

  /**
   * Initializing final variables for stock name and ticker symbol as these would be two parameters
   * of the stock that shouldn't be changed.
   */
  private final String tickerSymbol;

  private long noOfShares;
  private double stockCostBasis;
  private final AlphaVantageDemo stocksApi = new AlphaVantageDemo();


  /**
   * Constructor that takesin the stock name, ticker symbol and the stock cost basis at that date.
   * The default value for number of shares of the stock would be set to just one share.
   *
   * @param tickerSymbol   the unique ticker symbol which is used for trading.
   */
  public Stock(String tickerSymbol) {

    /**
     * Validity check for the stock's name and ticker symbol.
     */
    if (!checkStringValidity(tickerSymbol) ||
            !stocksApi.checkValidityOfTickerName(tickerSymbol)) {
      throw new IllegalArgumentException("Invalid stock name or ticker symbol");
    }



    this.tickerSymbol = tickerSymbol;
    this.stockCostBasis = 0;
    this.noOfShares = 0;
  }


  /**
   * Constructor that takes in the stock name, ticker symbol,the stock cost basis at that date and
   * the number of shares on that date.
   *
   * @param tickerSymbol   the unique ticker symbol which is used for trading.
   * @param stockCostBasis the stock's cost basis is the money spent buying the stock.
   * @param noOfShares     the number of shares that is being bought of this particular stock.
   */
  public Stock(String tickerSymbol, double stockCostBasis, long noOfShares) {

    /**
     * Validity check for the stock's name and ticker symbol.
     */
    if (!checkStringValidity(tickerSymbol) ||
            !stocksApi.checkValidityOfTickerName(tickerSymbol)) {
      throw new IllegalArgumentException("Invalid stock name or ticker symbol");
    }

    /**
     * A stock's basis or number of shares cannot be negative.
     */
    if (noOfShares < 0 || stockCostBasis < 0) {
      throw new IllegalArgumentException("Invalid input for stock.");
    }
    this.tickerSymbol = tickerSymbol;
    this.stockCostBasis = stockCostBasis;
    this.noOfShares = noOfShares;
  }


  @Override
  public double getStockCostBasis() {
    return this.stockCostBasis;
  }

  /**
   * Returns the stock value which is the money the investor would receive if he/she sold the stock
   * on that day (share price multiplied by the number of shares that the investor possesses of the
   * stock).
   *
   * @return the stock value which is a single share price multiplied by the number of shares that
   * the investor possesses.
   */
  @Override
  public double getStockValue() {
    return this.noOfShares * getSharePrice(this.tickerSymbol);
  }


  @Override
  public long getNumberOfShares() {
    return this.noOfShares;
  }


  @Override
  public void addShare(double amount, String date) throws IllegalArgumentException {
    double sharePrice;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * If the date entered is the current date, get the share price at the current time.
     */
    if(date.equals(dateFormatter.format(new Date()))){
      sharePrice = getSharePrice(this.tickerSymbol);
    }
    /**
     * If the date entered is a previous date then retrieve the closing/low price for that date.
     * An investor cannot invest in a future date and an exception would be thrown in this case.
     */
    else{
      try{
        sharePrice = stocksApi.retrieveSharePrice(date, this.tickerSymbol);
      }catch(Exception ex){
        throw new IllegalArgumentException("Invalid date. Please enter date again."+ex.getMessage());
      }
    }
    long noOfSharesBought = (long) (amount / sharePrice);
    /**
     * The cost basis is updated and the number of shares are updated.
     */
    this.stockCostBasis += sharePrice * noOfSharesBought;
    this.noOfShares += noOfSharesBought;
  }


  @Override
  public String getStockData() {
    return String.format("%s\n%d\n%.2f\n%.2f\n", this.noOfShares,
            this.stockCostBasis, this.getStockValue());
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


