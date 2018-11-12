package howtoinvest.controller;

import howtoinvest.model.IPortfolioManager;
import howtoinvest.model.StockPortfolio;

public interface IHowToInvestController<K> {

  void openPortfolioManager(IPortfolioManager<StockPortfolio> model);

}
