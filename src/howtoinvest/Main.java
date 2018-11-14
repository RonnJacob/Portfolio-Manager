package howtoinvest;

import java.io.InputStreamReader;

import howtoinvest.controller.HowToInvestController;
import howtoinvest.model.IPortfolioManager;
import howtoinvest.model.StockPortfolio;
import howtoinvest.model.StockPortfolioManager;

/**
 * This is the main class where the control is given to the HowToInvestController controller which
 * would accept a IPortfolioManager based model and open the Portfolio manager for a user to perform
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
    IPortfolioManager<StockPortfolio> portfolioManager;
    portfolioManager = new StockPortfolioManager();
    /**
     * Controller is given the control with the above model as the argument.
     */
    new HowToInvestController<>(new InputStreamReader(System.in), System.out)
            .openPortfolioManager(portfolioManager);


  }
}



