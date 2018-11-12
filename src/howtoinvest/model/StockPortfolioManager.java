package howtoinvest.model;

import java.util.HashMap;

public class StockPortfolioManager implements IPortfolioManager<StockPortfolio> {

  private HashMap<String, StockPortfolio> portfolios = new HashMap<>();

  public StockPortfolioManager(){
    this.portfolios.put("Default StockPortfolio", new StockPortfolio());
  }

  @Override
  public String getPortfolios(){
    String s = "";
    for (String key : this.portfolios.keySet()) {
      s += key + "\n";
    }
    return s;
  }

  @Override
  public void createPortfolio(String name) throws IllegalArgumentException{
    for (String key : this.portfolios.keySet()) {
      if (name.equals(key)) {
        throw new IllegalArgumentException("StockPortfolio already exists");
      }
    }
    StockPortfolio newStockPortfolio = new StockPortfolio();
    this.portfolios.put(name, newStockPortfolio);
  }

  @Override
  public StockPortfolio enterPortfolio(String name){
    for (String key : this.portfolios.keySet()) {
      if (name.equals(key)) {
        return this.portfolios.get(key);
      }
    }
    throw new IllegalArgumentException("StockPortfolio does not exist");
  }
}
