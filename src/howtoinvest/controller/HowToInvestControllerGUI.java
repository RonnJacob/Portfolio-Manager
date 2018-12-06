package howtoinvest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import howtoinvest.model.DollarCostAveraging;
import howtoinvest.model.IInvestmentStrategy;
import howtoinvest.model.IManager;
import howtoinvest.model.StockPortfolio;
import howtoinvest.view.HowToInvestViewGUI;
import howtoinvest.view.StrategyViewGUI;

/**
 * This class tends to the Graphical User Interface view which would have different user
 * interactions as compared to the console. The following HowToInvestControllerGUI class is an
 * implementation of the IHowToInvestController interface and provides an operation to open up a
 * portfolio manager wherein a user would be able to make choices provided by a specific
 * implementation of IPortfolio manager type class. A portfolio manager would offer operations such
 * as creation of portfolios, examining the composition of portfolios which would ideally be made up
 * of stocks and shares for a particular company or organization, adding shares/stocks to a
 * portfolio for a particular date and retrieving the cost basis and value of stocks or of a
 * portfolio for a given date. The controller would provide a method for opening up the home screen
 * wherein the user can choose to perform operations depending on the specific input. The home
 * screen would provide operations for a user to create a portfolio, retrieve a list of portfolios
 * and enter a particular portfolio. If a user enters a particular portfolio then further operations
 * are made open to the user where a user can observe the composition of a particular portfolio at
 * the time, add a share/stock at a particular date that is input by the user. The valid sequence of
 * inputs for adding a share would be the unique ticker symbol representing a company in string
 * format, the amount for which a user wants to buy shares of that company and the date for which
 * the user is planning to add or buy that share for. The final operation in the stock menu would be
 * to get or retrieve the cost basis or value of a portfolio at a particular date which is input by
 * the user The user would be able to invest a fixed amount into an existing portfolio containing
 * multiple stocks, using a specified weight for each stock in the portfolio. This would aid the
 * user in learning about how the investment cycle would work and how the value would dip and spike
 * depending on the date at which the investments were made or the date at which the value is being
 * retrieved for. The user would be able to seamlessly choose between selection of equal weight
 * investment for stocks in the portfolio or could enter weights of the user's choice. Another
 * feature offered would be the higher investment strategies offered by the program. This would
 * allow for a user to apply a strategy and modify the strategies preferences namely the stock(s) in
 * the strategy, the weights for investment, the frequency of investing, the start and end date for
 * investment and the amount to be invested. Note that each transaction (adding/buying of a stock to
 * a portfolio) would include a commission charge which would be included in the cost basis/value.
 * Additionally, there would be operations to load and save strategies and portfolios.
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
 *
 * <li>
 * A user would be able to add stocks/share to a portfolio, however removal of portfolios from the
 * portfolio manager and removal of stocks/shares from a portfolio is not a feature in the program
 * and the user would not be able to do so.
 * </li>
 * <li>
 * The controller would take a portfolio manager model, strategy model and view as parameters and
 * these parameters cannot be null.
 * </li>
 * <li>
 * The view would be a class implementing IHowToInvestView which would handle inputs by the user and
 * pass the input to this class. Necessary operations would be carried out by calling model
 * functions.
 * </li>
 * <li>
 * The portfolio manager model would be a class implementing the IPortfolioManager which would carry
 * out necessary options relevant to the portfolio as called by the controller depending on the
 * input provided by the user.
 * </li>
 * <li>
 * The strategy manager model would be a class implementing the IPortfolioManager which would carry
 * out necessary options relevant to strategies which would include application of a user-selected
 * strategy and modification of a strategy behavior as called by the controller depending on the
 * input provided by the user. Additionally we would be allowed to query for stocks present in the
 * strategy.
 * </li>
 * </ul>
 */
public class HowToInvestControllerGUI implements IHowToInvestController {

  /**
   * Variables initializing models and view objects.
   */
  private final HowToInvestViewGUI view;
  private final StrategyViewGUI strategyView;
  private final IManager<StockPortfolio> model;
  private final IManager<DollarCostAveraging> strategyModel;
  private StockPortfolio selectedPortfolio;
  private IInvestmentStrategy selectedStrategy;

  public HowToInvestControllerGUI(HowToInvestViewGUI view, StrategyViewGUI strategyView,
                                  IManager<StockPortfolio> model,
                                  IManager<DollarCostAveraging> strategyModel) {
    this.model = model;
    this.view = view;
    this.strategyModel = strategyModel;
    this.strategyView = strategyView;
  }

  @Override
  public void openPortfolioManager() {
    strategyView.openHomeScreen(strategyModel.getAll());
    strategyView.addFeatures(this);
    view.openHomeScreen(model.getAll());
    view.addFeatures(this);
  }

  @Override
  public void createPortfolio(String portfolioName) throws IllegalArgumentException {
    try {
      model.create(portfolioName);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public String openPortfolios(String portfolioToOpen) throws IllegalArgumentException {
    try {
      int counter = 1;
      for (String pfolio : model.getAll()) {
        if (pfolio.equalsIgnoreCase(portfolioToOpen)) {
          selectedPortfolio = model.getByIndex(counter);
          break;
        }
        counter++;
      }
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
    view.openModificationScreen(this);
    return "opened";
  }

  @Override
  public void loadList(String filename, String typeOfList) throws IllegalArgumentException {
    try {
      if (typeOfList == "Portfolio") {
        model.retrieve(filename);
      } else if (typeOfList == "Strategy") {
        strategyModel.retrieve(filename);
      }
      view.promptMessage(typeOfList + " " + filename + " has been added.");
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public Double getPortfolioCostBasis(String date) throws IllegalArgumentException {
    try {
      return selectedPortfolio.getStockCostBasis(date);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public Double getPortfolioValue(String date) throws IllegalArgumentException {
    try {
      return selectedPortfolio.getStockValue(date);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public void addStockToPortfolio(String stockNameEntered, double amountEntered, String dateEntered,
                                  String commissionEntered) throws IllegalArgumentException {
    try {
      Double commission;
      if (commissionEntered.equalsIgnoreCase("")) {
        commission = 0.0;
      } else {
        commission = selectedPortfolio.getCommission(commissionEntered);
      }

      double shares = selectedPortfolio.addStock(stockNameEntered, amountEntered, dateEntered,
              commission);
      view.logMessage(shares + " share(s) of " + stockNameEntered + " bought for $" + amountEntered
              + " on " + dateEntered + " with a commission charge of "
              + commission);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public HashMap<String, Double> getStocksInPortfolio(String date) throws IllegalArgumentException {
    HashMap<String, Double> stocks;
    try {
      stocks = selectedPortfolio.getPortfolioData(date);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
    return stocks;
  }

  @Override
  public List<String> getStocksInStrategy() throws IllegalArgumentException {
    List<String> stocks;
    try {
      stocks = selectedStrategy.getStocks();
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
    return stocks;
  }

  @Override
  public String[] showStrategies() {
    return strategyModel.getAll().stream().toArray(String[]::new);
  }

  @Override
  public void applyStrategy(String strategyToApply, String commission)
          throws IllegalArgumentException {
    try {
      int counter = 1;
      for (String strategy : strategyModel.getAll()) {
        if (strategyToApply.equalsIgnoreCase(strategy)) {
          strategyModel.getByIndex(counter)
                  .applyStrategy(selectedPortfolio, selectedPortfolio.getCommission(commission));
          return;
        }
        counter++;
      }
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public void savePortfolio(String fileName) throws IllegalArgumentException {
    try {
      selectedPortfolio.savePortfolio(fileName);
    } catch (IllegalStateException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public void saveStrategy(String fileName) throws IllegalArgumentException {
    try {
      selectedStrategy.saveStrategy(fileName);
    } catch (IllegalStateException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public void addStrategy(String strategyName) throws IllegalArgumentException {
    try {
      strategyModel.create(strategyName);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public String openStrategies(String strategyToOpen) throws IllegalArgumentException {
    try {
      int counter = 1;
      for (String pfolio : strategyModel.getAll()) {
        if (pfolio.equalsIgnoreCase(strategyToOpen)) {
          selectedStrategy = strategyModel.getByIndex(counter);
          break;
        }
        counter++;
      }
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
    strategyView.openModificationScreen(this);
    return "Strategy Opened";
  }

  @Override
  public void addStockToStrategy(String stockNameEntered) throws IllegalArgumentException {
    try {
      selectedStrategy.addStockToStrategy(stockNameEntered);
      strategyView.logMessage("Stock added");
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public void setStrategyAmount(String amount) throws IllegalArgumentException {
    try {
      selectedStrategy.setAmount(Double.parseDouble(amount));
      strategyView.logMessage("Amount set");
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public void setStrategyFrequency(String frequency) throws IllegalArgumentException {
    try {
      selectedStrategy.setAmount(Integer.parseInt(frequency));
      strategyView.logMessage("Frequency set");
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public void setStrategyTimerange(String begDate, String endDate) throws IllegalArgumentException {
    try {
      selectedStrategy.setTimeRange(begDate, endDate);
      strategyView.logMessage("Timerange set");
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public void investWithWeights(Double amount, String date, String commision,
                                List<Double> weights) throws IllegalArgumentException {
    try {
      String investmentsLog = "";
      TreeMap<String, Double> investWeights = new TreeMap<>();
      HashMap<String, Double> map = selectedPortfolio.getPortfolioData(date);
      int counter = 0;
      for (Map.Entry<String, Double> m : map.entrySet()) {
        investWeights.put(m.getKey(), weights.get(counter));
        counter++;
      }
      HashMap<String, Double> investments =
              selectedPortfolio.invest(amount, investWeights, false, date,
                      selectedPortfolio.getCommission(commision));
      for (Map.Entry<String, Double> investment : investments.entrySet()) {
        investmentsLog += investment.getValue() + " share(s) of " + investment.getKey()
                + " on " + date + "\n";
      }
      view.logMessage(investmentsLog);
    } catch (IllegalArgumentException | IllegalStateException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public void setStrategyWeights(TreeMap<String, Double> weights) throws IllegalArgumentException {
    try {
      selectedStrategy.setWeights(weights);
    } catch (IllegalArgumentException | IllegalStateException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public void investEqually(Double amount, String date, String commission)
          throws IllegalArgumentException {
    try {
      TreeMap<String, Double> investWeights = new TreeMap<>();
      HashMap<String, Double> investments = selectedPortfolio.invest(amount, investWeights,
              true, date, selectedPortfolio.getCommission(commission));
      String message = "";
      for (Map.Entry<String, Double> investment : investments.entrySet()) {
        message += investment.getValue() + " share(s) of " + investment.getKey()
                + " on " + date + "\n";
      }
      view.logMessage(message);
    } catch (IllegalArgumentException | IllegalStateException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

}
