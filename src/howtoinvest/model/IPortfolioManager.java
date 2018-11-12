package howtoinvest.model;

public interface IPortfolioManager<K> {
  String getPortfolios();

  void createPortfolio(String name) throws IllegalArgumentException;

  StockPortfolio getPortfolio(int index) throws IllegalArgumentException;
}
