package howtoinvest.model;

import java.util.HashMap;

/**
 * This class represents a portfolio made of collection of stocks. Each stock has an individual
 * ticker symbol so they are represented by a hashmap of ticker symbol and stock objects.
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
   */
  private double getStockCostBasis(String date) {
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
   */
  @Override
  public String addStock(String stock, double amount, String date) {
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
