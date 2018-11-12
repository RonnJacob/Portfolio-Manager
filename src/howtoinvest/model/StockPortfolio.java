package howtoinvest.model;

import java.util.HashMap;

public class StockPortfolio implements IPortfolio<Stock> {

  private HashMap<String, IStock> portfolio = new HashMap<>();

  public StockPortfolio() {
    this.portfolio = new HashMap<>();
  }

  /**
   * Returns the portfolio data.
   *
   * @return the portfolio data in the form a string.
   */
  public String getPortfolioData() {
    String s = "";
    for (String key : this.portfolio.keySet()) {
      s += this.portfolio.get(key).getStockData() + "\n";
    }
    return s;
  }

  /**
   * Returns the total cost basis of the portfolio.
   *
   * @return the total cost basis of the portfolio.
   */
  public double getStockCostBasis() {
    double d = 0;
    for (String key : this.portfolio.keySet()) {
      d += this.portfolio.get(key).getStockCostBasis();
    }
    return d;
  }

  /**
   * Returns the value of the portfolio.
   *
   * @return the value of the portfolio.
   */
  public double getStockValue() {
    double d = 0;
    for (String key : this.portfolio.keySet()) {
      d += this.portfolio.get(key).getStockValue();
    }
    return d;
  }

  /**
   * Adds a stock to the portfolio.
   *
   * @param stock  the stock that is to be added to the portfolio
   * @param amount the amount for which the stock has to be added to the portfolio.
   */
  public void addStock(String stock, double amount) {
    for (String key : this.portfolio.keySet()) {
      if (stock.equalsIgnoreCase(key)) {
        this.portfolio.get(key).addShare(amount, "");
        return;
      }
    }
    Stock newStock = new Stock(stock);
    newStock.addShare(amount, "");
    this.portfolio.put(stock, newStock);
  }
}
