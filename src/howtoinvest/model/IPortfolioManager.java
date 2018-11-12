package howtoinvest.model;

public interface IPortfolioManager<K> {
  String getPortfolio();

  void createPortfolio(String name)throws IllegalArgumentException;

  StockPortfolio enterPortfolio(String name)throws IllegalArgumentException;
}
