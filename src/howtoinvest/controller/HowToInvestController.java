package howtoinvest.controller;
import java.util.HashMap;
import java.util.Map;

import howtoinvest.model.IManager;
import howtoinvest.model.IPortfolio;
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
 * Inputs provided by the user would eventually end with the closing of the application by the user
 * inputting 'q' at either the the home screen or the portfolio screen.
 * </li>
 * <li>
 * Any invalid data of sorts would require the user to re-enter the input again. Say, an invalid
 * date format was given for adding a share or retrieving the cost basis/value, the user would be
 * required to enter the option to do the above operations and re-enter the date in the correct
 * format. The same would apply to a an empty or invalid ticker symbol. This input would be received
 * by the view and handled by the controller implementation.
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
 * The controller would take a model & view as parameters and these parameters cannot be null.
 * </li>
 * <li>
 * The view would be a class implementing IHowToInvestView which would handle inputs by the user
 * and pass the input to this class. Necessary operations would be carried out by calling model
 * functions.
 * </li>
 * <li>
 * The model would be a class implementing the IManager which would carry out necessary
 * options as called by the controller depending on the input provided by the user.
 * </li>
 * </ul>
 */
public class HowToInvestController<K> implements IHowToInvestController<K> {

  /**
   * Variables initializing model and view objects.
   */
  private final IHowToInvestView view;
  private final IManager<StockPortfolio> model;

  /**
   * Constructor that takes a model & view and sets the instance variables.
   * @param view the class implementing the view of the program.
   * @param model the class implementing the model of the program.
   */
  public HowToInvestController(IHowToInvestView view, IManager<StockPortfolio> model) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Model or view cannot be a null.");
    }
    this.model = model;
    this.view = view;
  }

  /**
   * The following method opens a portfolio manager which offers functionalities and performs
   * operations based on user input.
   */
  @Override
  public void openPortfolioManager(){

    view.openHomeScreen();

    while (true) {
      switch (view.getInput("")) {
        /**
         * If a user inputs 1, then the first option which is creation of portfolios operation is
         * performed.
         */
        case "1":
          addPortfoliosToManager();
          view.openHomeScreen();
          break;
        /**
         * If a user inputs 2, then the second option which is retrieval of a list of portfolios in
         * the portfolio manager is performed.
         */
        case "2":
          displayPortfolios();
          view.openHomeScreen();
          break;
        /**
         * If a user inputs 3, then the third option which is opening a particular portfolio
         * operation is performed.
         */
        case "3":
          String returnCode = openPortfolio();
          /**
           * If the returned code is q then quit from the application.
           */
          if (returnCode.equalsIgnoreCase("q")) {
            return;
          } else if (returnCode.equalsIgnoreCase("i")) {
            view.promptMessage("Invalid portfolio. Cannot retrieve portfolio.\n");
          }
          view.openHomeScreen();
          break;
        /**
         * Quit from the application if the user inputs 'q' or empty.
         */
        case "q":
          view.quitManager();
          return;
        case "":
          view.quitManager();
          return;
        /**
         * Any other input is deemed invalid.
         */
        default:
          view.promptMessage("Invalid input. Please enter input again.");
          break;
      }
    }
  }

  /**
   * The following method opens a portfolio and performs operations pertaining to a particular
   * portfolio depending on user input.
   *
   * @return a return code based on the operation and user input.
   */
  private String openPortfolio() {
    displayPortfolios();
    String message = "Enter index of Portfolio to open.\n\n";
    String pfolioName = view.getInput(message);
    if (pfolioName.equals("")) {
      return "q";
    }
    IPortfolio selectedPFolio;
    try {
      /**
       * Returns 'i' if the portfolio to be opened does not exist.
       */
      selectedPFolio = model.getByIndex(Integer.parseInt(pfolioName));
    } catch (IllegalArgumentException ex) {
      return "i";
    }
    view.openPortfolioMenu();
    try {
      while (true) {
        String choice = view.getInput("");
        if (choice.equals("")) {
          return "q";
        }
        switch (choice.toLowerCase()) {
          /**
           * If the user input is 1 pertaining to operations for a portfolio, the the first option
           * for a portfolio which is retrieving the composition of a portfolio is performed.
           */
          case "1":
            message = "Enter date in format yyyy-mm-dd: ";
            String dateToExamine = view.getInput(message);
            if (dateToExamine.equals("")) {
              return "q";
            }
            HashMap<String, Double> map = selectedPFolio.getPortfolioData(dateToExamine);
            for (Map.Entry<String, Double> entry : map.entrySet()) {
              view.displayPortfolioComposition(entry.getKey(), entry.getValue());
            }
            view.openPortfolioMenu();
            break;
          /**
           * If the user input is 2 pertaining to operations for a portfolio, the the second option
           * for a portfolio which is adding a stock/share for a particular date is performed.
           */
          case "2":
            buyStockShares(selectedPFolio);
            view.openPortfolioMenu();
            break;
          /**
           * If the user input is 3 pertaining to operations for a portfolio, the the third option
           * for a portfolio which is retrieving a cost basis/value for a particular date is
           * performed.
           */
          case "3":
            message = "Enter date in format yyyy-mm-dd: \n";
            String date = view.getInput(message);
            view.displayPortfolioCostBasis(date, selectedPFolio.getStockCostBasis(date));
            view.openPortfolioMenu();
            break;
          case "4":
            message = "Enter date in format yyyy-mm-dd: \n";
            date = view.getInput(message);
            view.displayPortfolioValue(date, selectedPFolio.getStockValue(date));
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
            view.promptMessage("Invalid input. Please enter input again.");
            break;
        }
      }

    } catch (IllegalArgumentException ex) {
      view.promptMessage(ex.getMessage());
    }
    return "r";
  }

  /**
   * The following method adds a particular stock/share based on user input. A valid sequence for
   * the input is the ticker symbol representing the company, the amount for which the user wants to
   * buy the shares, the date at which the user wants to add those shares for in the portfolio.
   *
   * @param selectedPFolio the portfolio manager object.
   */
  private void buyStockShares(IPortfolio<StockPortfolio> selectedPFolio) {
    do {
      String[] buyDetails = view.getShareBuyDetails();
      /**
       * Setting the stock name, the amount and the date for which a share of a stock has to be
       * added to the portfolio.
       */
      String stockName = buyDetails[0];
      Double amount = Double.parseDouble(buyDetails[1]);
      String date = buyDetails[2];
      Double commission = Double.parseDouble(buyDetails[3]);

      try {

        /**
         * Adds share and prompts a message specifying the number of shares bought for that amount.
         */
        view.promptMessage(selectedPFolio.addStock(stockName, amount, date
                , commission) +" share(s) of "+stockName + " bought on "+date);
      } catch (IllegalArgumentException ex) {
        view.promptMessage(ex.getMessage());
      }
      /**
       * Add more shares to the portfolio manager object.
       */
      view.promptMessage("Buy more shares? (Y/N)");
    }
    while (view.getInput("").equalsIgnoreCase("y"));
  }


  /**
   * The following method retrieves the list of portfolios present in the portfolio manager and
   * appends them to the appendable object.
   */
  private void displayPortfolios() {
    int counter = 1;
    for (String portfolioName : model.getAll()) {
      view.displayListOfPortfolios(counter, portfolioName);
      counter++;
    }
  }

  /**
   * The following method adds a portfolio to a particular portfolio manager depending on the user
   * input which would be the portfolio name.
   */
  private void addPortfoliosToManager() {
    do {
      try {
        String message = "Enter the name of the portfolio to be created\n";
        String nameOfPortfolio = view.getInput(message);
        model.create(nameOfPortfolio);
        view.promptMessage("Portfolio " + nameOfPortfolio + " has been created.");
      } catch (IllegalArgumentException ex) {
        view.promptMessage("Portfolio with that name exists");
      }
      /**
       * Add more portfolios if need be.
       */
      view.promptMessage("Add more portfolios? (Y/N)");
    }
    while (view.getInput("").equalsIgnoreCase("y"));
  }


}
