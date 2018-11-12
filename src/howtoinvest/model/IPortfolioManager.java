package howtoinvest.model;

public interface IPortfolioManager<K> {
  String getPortfolios();

  void createPortfolio(String name) throws IllegalArgumentException;

  StockPortfolio enterPortfolio(int index) throws IllegalArgumentException;
}
