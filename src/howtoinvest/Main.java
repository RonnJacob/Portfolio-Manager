package howtoinvest;

import howtoinvest.controller.HowToInvestController;
import howtoinvest.model.DollarCostAveraging;
import howtoinvest.model.DollarCostAveragingStrategyManager;
import howtoinvest.model.IManager;
import howtoinvest.model.StockPortfolio;
import howtoinvest.model.StockPortfolioManager;
import howtoinvest.view.IHowToInvestView;
import howtoinvest.view.HowToInvestViewImpl;

/**
 * This is the main class where the control is given to the HowToInvestController controller which
 * would accept a IManager based model and open the Portfolio manager for a user to perform
 * operations related to investing in stocks of an organization and maintenance of various
 * portfolios.
 */
public class Main {

  /**
   * Entry point of the application.
   * @param args arguments of type string.
   */
  public static void main(String[] args) {

    /**
     * Creation of PortfolioManager object which would be the model of the program.
     */
    IManager<StockPortfolio> portfolioManager = new StockPortfolioManager();

    IManager<DollarCostAveraging> strategyManager = new DollarCostAveragingStrategyManager();

    /**
     * Creation of IHowToInvestView object which would be the view of the program.
     */
    IHowToInvestView howToInvestView = new HowToInvestViewImpl();


    /**
     * Controller is given the control with the above model as the argument.
     */
    new HowToInvestController<>(howToInvestView, portfolioManager, strategyManager)
            .openPortfolioManager();


  }
}



