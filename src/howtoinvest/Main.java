package howtoinvest;

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

/**
 * This is the main class where the control is given to the HowToInvestController controller which
 * would accept IManager based models and open the Portfolio manager and the strategy manager for a
 * user to perform operations related to investing in stocks of an organization and maintenance of
 * various portfolios and strategies. The program accepts command-line options of the form -view
 * "type-of-view". The type of view should be either “console” or “gui” to open the respective
 * applications. By default it opens the gui application.
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
     * Creation of IHowToInvestView object which would be the view of the program for the console.
     */
    IHowToInvestView howToInvestView =
            new HowToInvestViewImpl(new InputStreamReader(System.in), System.out);


    /**
     * Creation of a GUI view object for the portfolio manager.
     */
    HowToInvestViewGUI view = new HowToInvestViewGUI();


    /**
     * Creation of a GUI view object for the strategy manager.
     */
    StrategyViewGUI sview = new StrategyViewGUI();


    /**
     * Creation of GUI controller.
     */
    IHowToInvestController guicontroller = new HowToInvestControllerGUI(view, sview,
            portfolioManager, strategyManager);

    /**
     * Creation of the console controller.
     */
    IHowToInvestController consolecontroller = new HowToInvestController(
            howToInvestView, portfolioManager, strategyManager);


    /**
     * If no argument is provided, the default view would be the gui view.
     */
    if (args.length == 0) {
      guicontroller.openPortfolioManager();
    }

    /**
     * If an argument is given with argument starting with -view, the user would have to enter
     * the type of view 'gui' or 'console'.
     */
    else if (args.length == 2) {
      if (args[0].equals("-view")) {
        switch (args[1]) {
          case "gui":
            guicontroller.openPortfolioManager();
            break;
          case "console":
            consolecontroller.openPortfolioManager();
            break;
          default:
            System.out.println("Cannot open application. Invalid arguments.");
        }
      } else {
        System.out.println("Cannot open application. Invalid arguments.");
      }
    } else {
      System.out.println("Cannot open application. Invalid arguments.");
    }
  }
}



