package howtoinvest.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import howtoinvest.model.IPortfolio;
import howtoinvest.model.IPortfolioManager;
import howtoinvest.model.Stock;
import howtoinvest.model.StockPortfolio;
import howtoinvest.view.IHowToInvestView;


/**
 * The following HowToInvestController class is an implementation of the IHowToInvestController
 * interface and provides an operation to open up a portfolio manager wherein a user would be able
 * to make choices provided by a specific implementation of IPortfolio manager type class. A
 * portfolio manager would offer operations such as creation of portfolios, examining the
 * composition of portfolios which would ideally be made up of stocks and shares for a particular
 * company or organization, adding shares/stocks to a portfolio for a particular date and retrieving
 * the cost basis and value of stocks or of a portfolio for a given date. This would aid the user in
 * learning about how the investment cycle would work and how the value would dip and spike
 * depending on the date at which the investments were made or the date at which the value is being
 * retrieved for. The controller would provide a method for opening up the home screen wherein the
 * user can choose to perform operations depending on the specific input. The home screen would
 * provide operations for a user to create a portfolio, retrieve a list of portfolios and enter a
 * particular portfolio. If a user enters a particular portfolio then further operations are made
 * open to the user where a user can observe the composition of a particular portfolio at the time,
 * add a share/stock at a particular date that is input by the user. The valid sequence of inputs
 * for adding a share would be the unique ticker symbol representing a company in string format, the
 * amount for which a user wants to buy shares of that company and the date for which the user is
 * planning to add or buy that share for. The final operation in the stock menu would be to get or
 * retrieve the cost basis or value of a portfolio at a particular date which is input by the user
 * again.
 * <ul>
 * <li>
 * Inputs provided by the user would eventually with the user closing the application by entering
 * 'q' at either the the home screen or the portfolio screen.
 * </li>
 * <li>
 * Any invalid data of sorts would require the user to re-enter the input again. Say, an invalid
 * date format was given for adding a share or retrieving the cost basis/value, the user would be
 * required to enter the option to do the above operations and re-enter the date in the correct
 * format. The same would apply to a an empty or invalid ticker symbol.
 * </li>
 * <li>
 * All date is strictly mandated to be in the yyyy-mm-dd format. Any other format would be deemed an
 * invalid date format and would require the user to re-enter the input again.
 * </li>
 * <li>
 * Cost basis/value for an empty portfolio would always be considered be 0.0 unless and until
 * changes have been made to the portfolio whereby a stock has been added.
 * </li>
 * <li>
 * A user would be able to add stocks/share to a portfolio, however removal of portfolios from the
 * portfolio manager and removal of stocks/shares from a portfolio is not a feature in the program
 * and the user would not be able to do so.
 * </li>
 * <li>
 * A readable and appendable object cannot be null.
 * </li>
 * </ul>
 */
public class HowToInvestController<K> implements IHowToInvestController<K> {

  /**
   * Variables initializing readable and appendable objects.
   */
  private final Readable in;
  private final Appendable out;
  private final IHowToInvestView view;

  /**
   * Constructor that sets the readable and appendable objects.
   *
   * @param in  the readable object argument.
   * @param out the appendable object argument.
   */
  public HowToInvestController(Readable in, Appendable out, IHowToInvestView view) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Invalid Readable or Appendable object");
    }
    this.in = in;
    this.out = out;
    this.view = view;
  }

  /**
   * The following method opens a portfolio manager which offers functionalities and performs
   * operations based on user input.
   *
   * @param portfolioManagerModel the portfolio manager object.
   */
  @Override
  public void openPortfolioManager(IPortfolioManager<StockPortfolio> portfolioManagerModel) {
    if (portfolioManagerModel == null) {
      throw new IllegalArgumentException("Portfolio manager cannot be a null.");
    }
    /**
     * The home screen is appended to the appendable object.
     */
    view.openHomeScreen();
    Scanner scan = new Scanner(this.in);
    while (true) {
      /**
       * Quitting the manager if no more input is available.
       */
      if (!scan.hasNext()) {
        view.quitManager();
        return;
      }
      switch (scan.next()) {
        /**
         * If a user inputs 1, then the first option which is creation of portfolios operation is
         * performed.
         */
        case "1":
          addPortfoliosToManager(portfolioManagerModel, scan);
          view.openHomeScreen();
          break;
        /**
         * If a user inputs 2, then the second option which is retrieval of a list of portfolios in
         * the portfolio manager is performed.
         */
        case "2":
          displayPortfolios(portfolioManagerModel, scan);
          view.openHomeScreen();
          break;
        /**
         * If a user inputs 3, then the third option which is opening a particular portfolio
         * operation is performed.
         */
        case "3":
          String returnCode = openPortfolio(portfolioManagerModel, scan);
          /**
           * If the returned code is q then quit from the application.
           */
          if (returnCode.equalsIgnoreCase("q")) {
            return;
          } else if (returnCode.equalsIgnoreCase("i")) {
            addToAppendable("Invalid portfolio. Cannot retrieve portfolio.\n");
          }
          view.openHomeScreen();
          break;
        /**
         * Quit from the application if the user inputs 'q'.
         */
        case "q":
          view.quitManager();
          return;
        /**
         * Any other input is deemed invalid.
         */
        default:
          addToAppendable("Invalid input. Please enter input again.");
          break;
      }
    }
  }

  /**
   * The following method opens a portfolio and performs operations pertaining to a particular
   * portfolio depending on user input.
   *
   * @param portfolioManagerModel the portfolio manager model object.
   * @param scan                  the scanner object to read input.
   * @return a return code based on the operation and user input.
   */
  private String openPortfolio(IPortfolioManager<StockPortfolio> portfolioManagerModel,
                               Scanner scan) {
    addToAppendable("\nEnter index of Portfolio to open.\n");
    /**
     * Quit if there is no more input available.
     */
    if (!scan.hasNext()) {
      return "q";
    }
    String pfolioName = scan.next();
    IPortfolio selectedPFolio;
    try {
      /**
       * Returns 'i' if the portfolio to be opened does not exist.
       */
      selectedPFolio = portfolioManagerModel.getPortfolio(Integer.parseInt(pfolioName));
    } catch (IllegalArgumentException ex) {
      return "i";
    }
    view.openPortfolioMenu();
    try {
      while (true) {
        if (!scan.hasNext()) {
          return "q";
        }
        switch (scan.next().toLowerCase()) {
          /**
           * If the user input is 1 pertaining to operations for a portfolio, the the first option
           * for a portfolio which is retrieving the composition of a portfolio is performed.
           */
          case "1":
            addToAppendable("Enter date in format yyyy-mm-dd: \n");
            if(!scan.hasNext()){
              return "q";
            }
            String dateToExamine = scan.next();
            HashMap<String, Double> map = selectedPFolio.getPortfolioData(dateToExamine);
            for (Map.Entry<String, Double> entry :  map.entrySet()) {
              view.getPortfolioComposition(entry.getKey(), entry.getValue());
            }
            view.openPortfolioMenu();
            break;
          /**
           * If the user input is 2 pertaining to operations for a portfolio, the the second option
           * for a portfolio which is adding a stock/share for a particular date is performed.
           */
          case "2":
            buyStockShares(selectedPFolio, scan);
            view.openPortfolioMenu();
            break;
          /**
           * If the user input is 3 pertaining to operations for a portfolio, the the third option
           * for a portfolio which is retrieving a cost basis/value for a particular date is
           * performed.
           */
          case "3":
            addToAppendable("Enter date in format yyyy-mm-dd: \n");
            String date = scan.next();
            view.getCostBasisOfPortfolio(date, selectedPFolio.getStockCostBasis(date));
            view.openPortfolioMenu();
            break;
          case "4":
            addToAppendable("Enter date in format yyyy-mm-dd: \n");
            date = scan.next();
            view.getValueOfPortfolio(date, selectedPFolio.getStockValue(date));
            view.openPortfolioMenu();
            break;
          /**
           * If a user inputs 'r', the program returns to the portfolio manager menu.
           */
          case "r":
            return "r";
          /**
           * If the user inputs 'q', the user quits from the portfolio manager program.
           */
          case "q":
            view.quitManager();
            return "q";
          default:
            addToAppendable("Invalid input. Please enter input again.");
            break;
        }
      }

    } catch (IllegalArgumentException ex) {
      addToAppendable(ex.getMessage());
    }
    return "r";
  }

  /**
   * The following method adds a particular stock/share based on user input. A valid sequence for
   * the input is the ticker symbol representing the company, the amount for which the user wants to
   * buy the shares, the date at which the user wants to add those shares for in the portfolio.
   *
   * @param selectedPFolio the portfolio manager object.
   * @param scan           the scanner object to read user input.
   */
  private void buyStockShares(IPortfolio<StockPortfolio> selectedPFolio,
                              Scanner scan) {
    do {
      addToAppendable("Enter stock symbol:\n");
      if (!scan.hasNext()) {
        return;
      }
      String stockSymbol = scan.next();
      addToAppendable("Enter amount for which shares are to be bought:\n");
      if (!scan.hasNext()) {
        return;
      }
      double amount = Double.parseDouble(scan.next());
      addToAppendable("Enter date in format yyyy-mm-dd: \n");
      if (!scan.hasNext()) {
        return;
      }
      String date = scan.next();

      try {
        view.addStockToPortfolio(stockSymbol,selectedPFolio.addStock(stockSymbol,amount,date)
                , date);
      } catch (IllegalArgumentException ex) {
        addToAppendable(ex.getMessage());
      }
      /**
       * Add more shares to the portfolio manager object.
       */
      addToAppendable("Buy more shares? (Y/N)");
      if (!scan.hasNext()) {
        return;
      }
    }
    while (scan.next().equalsIgnoreCase("y"));
  }


  /**
   * The following method retrieves the list of portfolios present in the portfolio manager and
   * appends them to the appendable object.
   *
   * @param portfolioManagerModel the portfolio manager object.
   * @param scan                  the scanner object for reading  input.
   */
  private void displayPortfolios(IPortfolioManager<StockPortfolio> portfolioManagerModel,
                                 Scanner scan) {
    addToAppendable("\nList of Portfolios\n");
    int counter = 1;
    for(String portfolioName: portfolioManagerModel.getPortfolios()){
      view.getListOfPortfolios(counter, portfolioName);
      counter++;
    }
  }

  /**
   * The following method adds a portfolio to a particular portfolio manager depending on the user
   * input which would be the portfolio name.
   *
   * @param pfolioManager th portfolio manager.
   * @param in            the scanner object for reading input.
   */
  private void addPortfoliosToManager(IPortfolioManager<StockPortfolio> pfolioManager, Scanner in) {
    do {
      addToAppendable("Enter the name of the portfolio to be created.");
      if (!in.hasNext()) {
        return;
      }
      String nameOfPortfolio = in.next();
      try {
        pfolioManager.createPortfolio(nameOfPortfolio);

        view.addPortfolio(nameOfPortfolio);
      } catch (IllegalArgumentException ex) {
        addToAppendable("Portfolio with that name exists");
      }
      /**
       * Add more portfolios if need be.
       */
      addToAppendable("Add more portfolios? (Y/N)");
      if (!in.hasNext()) {
        return;
      }
    }
    while (in.next().equalsIgnoreCase("y"));
  }

  /**
   * Adds a message to the appendable object.
   *
   * @param message the message of String format that is to be added.
   */
  private void addToAppendable(String message) {
    try {
      this.out.append(String.format("%s\n", message));
    } catch (IOException ex) {
      throw new IllegalStateException("IO exception has been encountered.");
    }
  }


}
