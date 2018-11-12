package howtoinvest;

import java.io.IOException;
import java.io.InputStreamReader;

import howtoinvest.controller.HowToInvestController;
import howtoinvest.model.IPortfolioManager;
import howtoinvest.model.StockPortfolio;
import howtoinvest.model.StockPortfolioManager;

public class Main {

  public static void main(String[] args) {
      IPortfolioManager<StockPortfolio> portfolioManager = new StockPortfolioManager();
      new HowToInvestController<>(new InputStreamReader(System.in), System.out)
              .openPortfolioManager(portfolioManager);

  }
}



