package howtoinvest.model;

import java.util.HashMap;
import java.util.TreeMap;

public class StockPortfolioManager implements IPortfolioManager<StockPortfolio> {

  private TreeMap<String, StockPortfolio> portfolios = new TreeMap<>();

  public StockPortfolioManager() {
    this.portfolios.put("Default StockPortfolio", new StockPortfolio());
  }

  @Override
  public String getPortfolios() {
    String s = "";
    for (String key : this.portfolios.keySet()) {
      s += key + "\n";
    }
    return s;
  }

  @Override
  public void createPortfolio(String name) throws IllegalArgumentException {
    for (String key : this.portfolios.keySet()) {
      if (name.equals(key)) {
        throw new IllegalArgumentException("StockPortfolio already exists");
      }
    }
    StockPortfolio newStockPortfolio = new StockPortfolio();
    this.portfolios.put(name, newStockPortfolio);
  }

  @Override
  public StockPortfolio enterPortfolio(int index) {
    int counter = 1;
    for (String key : this.portfolios.keySet()) {
      if (index == counter) {
        return this.portfolios.get(key);
      }
      counter++;
    }
    throw new IllegalArgumentException("StockPortfolio does not exist");
  }
}
