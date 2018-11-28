package howtoinvest.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import howtoinvest.model.DollarCostAveraging;
import howtoinvest.model.IPortfolio;
import howtoinvest.model.IManager;
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
 * The model would be a class implementing the IPortfolioManager which would carry out necessary
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
  private final IManager<DollarCostAveraging> strategyModel;
  private String message;

  /**
   * Constructor that takes a model & view and sets the instance variables.
   * @param view the class implementing the view of the program.
   * @param model the class implementing the model of the program.
   */
  public HowToInvestController(IHowToInvestView view, IManager<StockPortfolio> model
          , IManager<DollarCostAveraging> strategyModel) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Model or view cannot be a null.");
    }
    this.model = model;
    this.view = view;
    this.strategyModel = strategyModel;
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
          view.promptMessage("Invalid input. Please enter input again.\n");
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
    String pfolioName = view.getInput("\nEnter index of Portfolio to open.\n");
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
           * Option 1 corresponds to retrieving the composition.
           */
          case "1":
            message = "Enter date in format yyyy-mm-dd: \n";
            retrieveComposition(selectedPFolio);
            view.openPortfolioMenu();
            break;
          /**
           * Option 2 corresponds to buying a stock/share and adding to the portfolio.
           */
          case "2":
            buyStockShares(selectedPFolio);
            view.openPortfolioMenu();
            break;
          /**
           * Option 3 corresponds to getting the cost basis for a particular date.
           */
          case "3":
            message = "Enter date in format yyyy-mm-dd: \n";
            String date = view.getInput(message);
            view.displayPortfolioCostBasis(date, selectedPFolio.getStockCostBasis(date));
            view.openPortfolioMenu();
            break;
          /**
           * Option 4 corresponds to getting the value for a particular date.
           */
          case "4":
            message = "Enter date in format yyyy-mm-dd: \n";
            date = view.getInput(message);
            view.displayPortfolioValue(date, selectedPFolio.getStockValue(date));
            view.openPortfolioMenu();
            break;
          case "5":
            message = "\nInvestment Strategies. \n";
            applyInvestment(selectedPFolio);
            view.openPortfolioMenu();
            break;
          case "6":
            message = applyStrategy(selectedPFolio);
            view.openPortfolioMenu();
            break;
          /**
           * If a user inputs 'r', the program returns to the portfolio manager menu.
           */
          case "r":
            return "r";
          case "q":
            view.quitManager();
            return "q";
          default:
            view.promptMessage("Invalid input. Please enter input again.\n");
            break;
        }
      }

    } catch (IllegalArgumentException ex) {
      view.promptMessage(ex.getMessage());
    }
    return "r";
  }

  private String applyStrategy(IPortfolio portfolio) {
    int counter = 1;
    for (String portfolioName : strategyModel.getAll()) {
      view.displayList(counter, portfolioName, "Strategies");
      counter++;
    }
    String strategyChoice = view.getInput("\nEnter index of strategy to apply.\n");
    if (strategyChoice.equals("")) {
      return "q";
    }
    DollarCostAveraging dcaStrategy;
    try {
      /**
       * Returns 'i' if the portfolio to be opened does not exist.
       */
      dcaStrategy = strategyModel.getByIndex(Integer.parseInt(strategyChoice));
    } catch (IllegalArgumentException ex) {
      return "i";
    }
    view.openStrategyMenu();
    try {
      while (true) {
        String choice = view.getInput("");
        if (choice.equals("")) {
          return "q";
        }
        switch (choice.toLowerCase()) {
          case "1":
            String commisionString = view.getInput("Enter the commission option for the "
                    + "transaction [l, m, h] \n");
            try{
              TreeMap<Date, HashMap<String, Double>> strategyApplied =
                      dcaStrategy.applyStrategy(portfolio,portfolio.getCommission(commisionString));
              displayStrategy(strategyApplied);
            }catch(IllegalArgumentException ex){
              view.promptMessage(ex.getMessage()+"\n");
            }
            view.openStrategyMenu();
            break;
          case "2":
            modificationMenu(portfolio, dcaStrategy);
            view.openStrategyMenu();
            break;
          case "r":
            return "r";
          case "q":
            view.quitManager();
            return "q";
          default:
            view.promptMessage("Invalid input. Please enter input again.\n");
            break;
        }
      }

    } catch (IllegalArgumentException ex) {
      view.promptMessage(ex.getMessage());
    }
    return "r";
  }

  private void displayStrategy(TreeMap<Date, HashMap<String, Double>> strategyApplied) {

    String datePattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
    if(strategyApplied.size()==0){
      view.promptMessage("No stocks in portfolio to apply strategy.\n");
    }
    for (Map.Entry<Date,  HashMap<String, Double>> entry : strategyApplied.entrySet()) {
      view.promptMessage("\n"+simpleDateFormat.format(entry.getKey())+"\n");
      for(HashMap.Entry<String, Double> stock: entry.getValue().entrySet()){
        String message = String.format("%.2f shares of %s bought.\n", stock.getValue()
                , stock.getKey());
        view.promptMessage(message);
      }
    }
  }

  private String modificationMenu(IPortfolio portfolio, DollarCostAveraging strategyModel) {
    view.strategyModificationMenu();
    try {
      while (true) {
        String choice = view.getInput("");
        if (choice.equals("")) {
          return "q";
        }
        switch (choice) {
          case "1":
            addStocksToStrategy(portfolio, strategyModel);
            view.strategyModificationMenu();
            break;
          case "2":
            modifyWeights(strategyModel);
            view.strategyModificationMenu();
            break;
          case "3":
            Double amount = Double.parseDouble(view.getInput("Enter new amount for investing of "
                    + "strategy.\n"));
            strategyModel.setAmount(amount);
            view.strategyModificationMenu();
            break;
          case "4":
            int days = Integer.parseInt(view.getInput("Enter frequency \n"));
            strategyModel.setFrequency(days);
            view.strategyModificationMenu();
            break;
          case "5":
            String startDate = view.getInput("Enter start date for investing of strategy.\n");
            String endDate = view.getInput("Enter end date for investing of strategy.\n");
            strategyModel.setTimeRange(startDate, endDate);
            view.strategyModificationMenu();
            break;
          case "r":
            return "r";
          case "q":
            view.quitManager();
            return "q";
          default:
            view.promptMessage("Invalid input. Please enter input again.\n");
            break;
        }
      }

    } catch (IllegalArgumentException ex) {
      view.promptMessage(ex.getMessage());
    }
    return "r";
  }

  private void addStocksToStrategy(IPortfolio portfolio, DollarCostAveraging strategyModel) {
    List<String> stocks = new LinkedList<>();
    do {
      try {

        String tickerSymbol = view.getInput("Enter a stock name (ticker symbol) to add to "
                + "the strategy: \n");
        stocks.add(tickerSymbol);
      } catch (IllegalArgumentException ex) {
        view.promptMessage(ex.getMessage());
      }
      view.promptMessage("Add more stocks? (Y/N)\n");
    }
    while (view.getInput("").equalsIgnoreCase("y"));
    if(stocks.size()==1){
      strategyModel.addStockToStrategy(stocks.get(0));
    }else{
      strategyModel.addMultipleStocksToStrategy(stocks);
    }
  }

  private void modifyWeights(DollarCostAveraging strategyModel) {
    List<String> stocks = strategyModel.getStocks();
    TreeMap<String, Double> weights = new TreeMap<>();
    for (String stock : stocks) {
      Double weight = Double.parseDouble(view.getInput("Enter weight for "+ stock
              +" : \n"));
      weights.put(stock, weight);
    }
  }


  private void retrieveComposition(IPortfolio selectedPFolio) {
    String dateToExamine = view.getInput(message);
    HashMap<String, Double> map = selectedPFolio.getPortfolioData(dateToExamine);
    for (Map.Entry<String, Double> entry : map.entrySet()) {
      view.displayPortfolioComposition(entry.getKey(), entry.getValue());
    }
  }

  private void applyInvestment(IPortfolio selectedPFolio) {
    view.openInvestmentMenu();
    while(true){
      switch (view.getInput("").toLowerCase()) {
        case "1":

          Double amount = Double.parseDouble(view.getInput("Enter amount to invest: \n"));
          String date = view.getInput("Enter date in format yyyy-mm-dd: \n");
          String commision = view.getInput("Enter the commission option for the transaction "
                  + "[l, m, h]:\n");
          selectedPFolio.invest(amount, new TreeMap<>(),true, date
                  , selectedPFolio.getCommission(commision));
          view.openInvestmentMenu();
          break;
        case "2":
          investWithCustomWeights(selectedPFolio);
          view.openInvestmentMenu();
          break;
        case "r":
          return;
        default:
          view.promptMessage("Invalid input. Please enter input again.\n");
          break;
      }
    }
  }

  private void investWithCustomWeights(IPortfolio selectedPFolio) {
    Double amount = Double.parseDouble(view.getInput("Enter amount to invest: \n"));
    String date = view.getInput("Enter date in format yyyy-mm-dd: \n");
    String commision = view.getInput("Enter the commission option for the transaction "
            + "[l, m, h]:\n");
    HashMap<String, Double> map = selectedPFolio.getPortfolioData(date);
    TreeMap<String, Double> weights = new TreeMap<>();
    for (Map.Entry<String, Double> entry : map.entrySet()) {
      Double weight = Double.parseDouble(view.getInput("Enter weight for "+ entry.getKey()
              +" : \n"));
      weights.put(entry.getKey(), weight);
    }
    try{
      selectedPFolio.invest(amount, weights, false, date
              , selectedPFolio.getCommission(commision));
    } catch(IllegalStateException ex){
      view.promptMessage(ex.getMessage());
    }
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
      String commissionOption = buyDetails[3];

      try {

        Double commission = selectedPFolio.getCommission(commissionOption);
        /**
         * Adds share and prompts a message specifying the number of shares bought for that amount.
         */
        view.promptMessage(selectedPFolio.addStock(stockName, amount, date
                , commission) +" share(s) of "+stockName + " bought on "+date+"\n");
      } catch (IllegalArgumentException ex) {
        view.promptMessage(ex.getMessage());
      }
      /**
       * Add more shares to the portfolio manager object.
       */
      view.promptMessage("Buy more shares? (Y/N)\n");
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
      view.displayList(counter, portfolioName, "Portfolios");
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
        String nameOfPortfolio = view.getInput("Enter the name of the portfolio to be created\n");
        model.create(nameOfPortfolio);
        view.promptMessage("Portfolio " + nameOfPortfolio + " has been created.\n");
      } catch (IllegalArgumentException ex) {
        view.promptMessage("Portfolio with that name exists\n");
      }
      /**
       * Add more portfolios if need be.
       */
      view.promptMessage("Add more portfolios? (Y/N)\n");
    }
    while (view.getInput("").equalsIgnoreCase("y"));
  }


}
