package howtoinvest;

import java.io.Console;
import java.io.InputStreamReader;

import howtoinvest.controller.HowToInvestController;
import howtoinvest.controller.HowToInvestControllerGUI;
import howtoinvest.controller.IHowToInvestController;
import howtoinvest.model.DollarCostAveraging;
import howtoinvest.model.DollarCostAveragingStrategyManager;
import howtoinvest.model.IManager;
import howtoinvest.model.StockPortfolio;
import howtoinvest.model.StockPortfolioManager;
import howtoinvest.view.HowToInvestViewGUI;
import howtoinvest.view.IHowToInvestView;
import howtoinvest.view.HowToInvestViewImpl;
import howtoinvest.view.StrategyViewGUI;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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


    HowToInvestViewGUI view = new HowToInvestViewGUI();

    StrategyViewGUI sview = new StrategyViewGUI();

    /**
     * Controller is given the control with the above model as the argument.
     */
//    HowToInvestControllerGUI gui = new HowToInvestControllerGUI(view,sview,portfolioManager,strategyManager);
//    gui.openPortfolioManager();
//    IHowToInvestController controller = new HowToInvestController(howToInvestView, portfolioManager,
//            strategyManager);
//    controller.openPortfolioManager();

    String arg1 = "console";
    if(arg1 == "gui"){
      HowToInvestControllerGUI gui = new HowToInvestControllerGUI(view,sview,portfolioManager,strategyManager);
      gui.openPortfolioManager();
    }
    else if(arg1 == "console"){

    IHowToInvestController controller = new HowToInvestController(howToInvestView, portfolioManager,
            strategyManager);
    controller.openPortfolioManager();
    }
    else {
      throw new IllegalArgumentException("Cannot open application.");
    }

  }
}



