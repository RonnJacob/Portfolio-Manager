package howtoinvest;

import java.io.InputStreamReader;

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
 * would accept IManager based models and open the Portfolio manager for a user to perform
 * operations related to investing in stocks of an organization and maintenance of various
 * portfolios.
 */
public class Main {

  /**
   * Entry point of the application.
   *
   * @param args arguments of type string.
   */
  public static void main(String[] args) {

    /**
     * Creation of PortfolioManager object which would behave as model of the program that would
     * manage portfolios and portfolio related operations.
     */
    IManager<StockPortfolio> portfolioManager = new StockPortfolioManager();

    /**
     * Creation of the strategy manager object which would behave as a model which would
     * independently deal with strategy related operations for portfolios.
     */
    IManager<DollarCostAveraging> strategyManager = new DollarCostAveragingStrategyManager();

    /**
     * Creation of IHowToInvestView object which would be the view of the program.
     */
    IHowToInvestView howToInvestView =
            new HowToInvestViewImpl(new InputStreamReader(System.in), System.out);


    /**
     * Controller is given the control with the above model as the argument.
     */
    new HowToInvestController<>(howToInvestView, portfolioManager, strategyManager)
            .openPortfolioManager();


  }
}



