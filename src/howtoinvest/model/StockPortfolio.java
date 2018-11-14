package howtoinvest.model;

import java.util.HashMap;

/**
 * <p>This class represents a portfolio made of collection of stocks. Each stock has an individual
 * ticker symbol so they are represented by a hashmap of ticker symbol and stock objects.</p>
 * <p>This class provides three operations:</p>
 * <ul>
 * <li>
 * View the composition of the portfolio - It returns all the stocks in the portfolio and the amount
 * that is invested in those stocks.
 * </li>
 * <li>
 * Adding a stock to the portfolio with a unique ticker symbol, the amount which you are investing
 * that stock and the date of investment. The stock ticker symbol must be valid, the validity is
 * checked based on the app.config properties if it is set to read from the API it uses the Alpha
 * vantage api to check the validity of the ticker symbol or if its not set to the API a check is
 * determined to see if the user has provided a tickersymbol .csv to provide prices for the ticker
 * symbol. The amount of investment cannot be negative and the date is also restricted like the
 * tickersymbol. If we use the API we check stock prices exists for the given date(Holidays are
 * handled by taking the previous working day's closing date). If we don't use the API we check if
 * the user has provided the values for the given date.
 * </li>
 * <li>
 * Retrieves the cost basis and the stock value at a given time. The total money invested in the
 * stock (money spent buying it) is called the cost basis of the purchase. The value of the stock on
 * a particular day is the money the investor would receive if he/she sold the stock on that day.
 * </li>
 * </ul>
 */
public class StockPortfolio implements IPortfolio<Stock> {

  /**
   * Hashmap to store the stocks in the portfolio. Key is the ticker symbol of the stock and value
   * is the corresponding stock object.
   */
  private HashMap<String, Stock> portfolio;

  /**
   * Constructs a stock portfolio object with empty hashmap.
   */
  public StockPortfolio() {
    this.portfolio = new HashMap<>();
  }

  /**
   * Returns the portfolio data.
   *
   * @return the portfolio data in the form a string.
   */
  @Override
  public String getPortfolioData() {
    String s = "";
    for (String key : this.portfolio.keySet()) {
      s += this.portfolio.get(key).getStockData() + "\n";
    }
    return s.trim();
  }

  /**
   * Returns the total cost basis of the portfolio at a given date.
   *
   * @param date date for which the cost basis has to be calculated.
   * @return the total cost basis of the portfolio.
   * @throws IllegalArgumentException if the stock cost basis cannot be fetched.
   */
  private double getStockCostBasis(String date) throws IllegalArgumentException {
    double d = 0;
    for (String key : this.portfolio.keySet()) {
      d += this.portfolio.get(key).getStockCostBasis(date);
    }
    return d;
  }

  /**
   * Returns the total value of the portfolio at a given date.
   *
   * @param date date for which the value has to be calculated.
   * @return the total value of the portfolio.
   * @throws IllegalArgumentException if the stock value cannot be fetched.
   */
  private double getStockValue(String date) {
    double d = 0;
    for (String key : this.portfolio.keySet()) {
      d += this.portfolio.get(key).getStockValue(date);
    }
    return d;
  }

  /**
   * Returns the cost basis and the value of the portfolio for a given date as string in a specific
   * format: Total portfolio cost basis = x \n Total portfolio value = y where x and y are the
   * portfolios cost basis and the value.
   *
   * @param date date for which the cost basis and the value have to be calculated.
   * @return the cost basis and the value of the portfolio for a given date.
   */
  @Override
  public String getStockCostBasisAndStockValue(String date) {
    String s = String.format("Total portfolio cost basis = %.2f\n", getStockCostBasis(date));
    s += String.format("Total portfolio value = %.2f", getStockValue(date));
    return s;
  }

  /**
   * Adds a stock to the portfolio.
   *
   * @param stock  the stock that is to be added to the portfolio
   * @param amount the amount for which the stock has to be added to the portfolio.
   * @throws IllegalArgumentException if the stock ticker symbol, amount or date is invalid.
   */
  @Override
  public String addStock(String stock, double amount, String date) throws IllegalArgumentException {
    String sharesBought = "";
    for (String key : this.portfolio.keySet()) {
      if (stock.equalsIgnoreCase(key)) {
        sharesBought = this.portfolio.get(key).addShare(amount, date);
        return sharesBought;
      }
    }
    Stock newStock = new Stock(stock);
    sharesBought = newStock.addShare(amount, date);
    this.portfolio.put(stock, newStock);

    return sharesBought;
  }
}
