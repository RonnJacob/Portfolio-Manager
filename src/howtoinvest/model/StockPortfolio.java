package howtoinvest.model;

import java.util.HashMap;

public class StockPortfolio implements IPortfolio<Stock> {

  private HashMap<String, Stock> portfolio;

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
    return s;
  }

  /**
   * Returns the total cost basis of the portfolio.
   *
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
   * Returns the value of the portfolio.
   *
   * @return the value of the portfolio.
   */
  private double getStockValue(String date) {
    double d = 0;
    for (String key : this.portfolio.keySet()) {
      d += this.portfolio.get(key).getStockValue(date);
    }
    return d;
  }

  @Override
  public String getStockCostBasisAndStockValue(String date) {
    String s = String.format("Total portfolio cost basis = %.2f\n", getStockCostBasis(date));
    s += String.format("Total portfolio value = %.2f\n ", getStockValue(date));
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
