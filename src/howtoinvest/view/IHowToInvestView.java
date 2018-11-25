package howtoinvest.view;

public interface IHowToInvestView {

  void openHomeScreen();

  void openPortfolioMenu();

  void quitManager();

  void addPortfolio(String name);

  void addStockToPortfolio(String name, double numberOfShares, String date);

  void getPortfolioComposition(String key, Double value);

  void getValueOfPortfolio(String date, double stockValue);

  void getCostBasisOfPortfolio(String date, double stockCostBasis);

  void getListOfPortfolios(int counter, String key);
}
