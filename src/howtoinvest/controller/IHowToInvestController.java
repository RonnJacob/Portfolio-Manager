package howtoinvest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Interface for the how-to-invest-for-dummies application. An implementation would work with
 * IPortfolio manager interface which would represent a type of portfolio manager and a strategy
 * manager which would be the model for strategies pertaining to portfolios The interface contains
 * an operation to open a portfolio manager wherein a user would be able to make choices provided by
 * a specific implementation of IPortfolio manager type class. A portfolio manager would offer
 * operations such as creation of portfolios, examining the composition of portfolios which would
 * ideally be made up of stocks and shares for a particular company or organization, adding
 * shares/stocks to a portfolio for a particular date and retrieving the cost basis and value of
 * stocks or of a portfolio for a given date. The user would be able to invest a fixed amount into
 * an existing portfolio containing multiple stocks, using a specified weight for each stock in the
 * portfolio. This would aid the user in learning about how the investment cycle would work and how
 * the value would dip and spike depending on the date at which the investments were made or the
 * date at which the value is being retrieved for. The user would be able to seamlessly choose
 * between selection of equal weight investment for stocks in the portfolio or could enter weights
 * of the user's choice. Another feature offered would be the higher investment strategies offered
 * by the program. This would allow for a user to apply a strategy and modify the strategies
 * preferences namely the stock(s) in the strategy, the weights for investment, the frequency of
 * investing, the start and end date for investment and the amount to be invested. Note that each
 * transaction (adding/buying of a stock to a portfolio) would include a commission charge which
 * would be included in the cost basis/value. The user will also be able to load and save strategies
 * and portfolios.
 */
public interface IHowToInvestController {


  /**
   * Opens a portfolio manager with options for a user to perform investment related operations
   * depending on a specific implementation of the portfolio manager and operations provided by the
   * portfolio manager.
   */
  void openPortfolioManager();

  /**
   * Creates a portfolio with the input received from the view. Passes the portfolio name to the
   * model and creates a new portfolio which will be added to the list of portfolios.
   *
   * @param portfolioName the name of the portfolio to be created.
   */
  void createPortfolio(String portfolioName);

  /**
   * Opens the portfolio with the name passed as an argument. Retrieves the portfolio with the name
   * passed as an argument from a list of portfolios and opens the portfolio.
   *
   * @param portfolioToOpen the portfolio that is to be opened.
   * @return a return message saying that the portfolio has been opened.
   */
  String openPortfolios(String portfolioToOpen);

  /**
   * Loads an object of type passed as an argument with a specified name.
   *
   * @param filename   the name of the file to be loaded.
   * @param typeOfList the type of the object.
   */
  void loadList(String filename, String typeOfList);

  /**
   * Retrieves the cost basis of the portfolio as of a date which is received as input from the view
   * and passed onto the model.
   *
   * @param date the date for which we wish to retrieve the cost basis of the portfolio.
   * @return the cost basis of portfolio as of a specified date
   */
  Double getPortfolioCostBasis(String date);

  /**
   * Retrieves the value of the portfolio as of a date which is received as input from the view and
   * passed onto the model.
   *
   * @param date the date for which we wish to retrieve the value of the portfolio.
   * @return the cost basis of portfolio as of a specified date
   */
  Double getPortfolioValue(String date);

  /**
   * Retrieves input from the view and passes the input in order to add a stock to a portfolio.
   *
   * @param stockNameEntered  the ticker symbol of the stock that is to be added to the portfolio.
   * @param amountEntered     the amount for which stocks are to be entered.
   * @param dateEntered       the date for which the share(s) are to be bought.
   * @param commissionEntered the commission that is to be taken into account while buying the
   *                          share(s).
   */
  void addStockToPortfolio(String stockNameEntered, double amountEntered, String dateEntered,
                           String commissionEntered);

  /**
   * Retrieves stocks in a portfolio as of a date which is received as input from the view and
   * passed on to the model.
   *
   * @param date the date for which stocks in a portfolio are to be retrieved.
   * @return the stocks and the number of stocks in portfolio as of a particular date which is
   *         input.
   */
  HashMap<String, Double> getStocksInPortfolio(String date);

  /**
   * Retrieves the stocks in a strategy.
   *
   * @return the list of stocks that are part of a particular strategy.
   */
  List<String> getStocksInStrategy();


  /**
   * Displays the strategy by calling the strategy model.
   *
   * @return the list of strategies available.
   */
  String[] showStrategies();


  /**
   * Applies a strategy that is input by the user with an input commission for the investment as
   * well.
   *
   * @param strategyToApply the strategy that is to be applied to a portfolio.
   * @param commision       the commission that is to be taken into account for the investment.
   */
  void applyStrategy(String strategyToApply, String commision);

  /**
   * Saves a portfolio with the file name which is input by the user. This filename is passed on to
   * the model to carry out the saving operation.
   *
   * @param fileName the filename under which the portfolio is to be saved.
   */
  void savePortfolio(String fileName);

  /**
   * Saves a strategy with the file name which is input by the user. This filename is passed on to
   * the model to carry out the saving operation.
   *
   * @param fileName the filename under which the strategy is to be saved.
   */
  void saveStrategy(String fileName);

  /**
   * Creates a strategy with the input received from the view. Passes the strategy name to the model
   * and creates a new strategy which will be added to the list of strategy.
   *
   * @param strategyName the name of the portfolio to be created.
   */
  void addStrategy(String strategyName);

  /**
   * Opens the strategy with the name passed as an argument. Retrieves the strategy with the name
   * passed as an argument from a list of strategy and opens the strategy.
   *
   * @param strategyToOpen the strategy that is to be opened.
   * @return a return message saying that the strategy has been opened.
   */
  String openStrategies(String strategyToOpen);

  /**
   * Adds a stock which is input by the user through the view to the list of stocks in a strategy.
   * This user input is passed to the model so that the stock can be added to the list of stocks in
   * the strategy.
   *
   * @param stockNameEntered the stock that is to be added to the list of stocks present in the
   *                         strategy
   */
  void addStockToStrategy(String stockNameEntered);

  /**
   * Modifies the amount for investment within a strategy with the amount input by the user via the
   * view. This amount input would passed to the model for modifying the investment amount in the
   * strategy.
   *
   * @param amount the new amount for investment within in the investment strategy.
   */
  void setStrategyAmount(String amount);


  /**
   * Modifies the frequency for investment within a strategy with the frequency input by the user
   * via the view. This frequency input would passed to the model for modifying the frequency in the
   * strategy.
   *
   * @param frequency the new frequency for investment within in the investment strategy.
   */
  void setStrategyFrequency(String frequency);

  /**
   * Modifies the range for which the investment is to be made within a strategy. The user input for
   * the beggining and end dates are received via the input and passed to the model in order to
   * modify the range of investment strategy.
   *
   * @param begDate the start date of the range for the investment period.
   * @param endDate the end date of the range for the investment period.
   */
  void setStrategyTimerange(String begDate, String endDate);

  /**
   * Invest in a strategy with the inputs received from the view. The inputs are passed onto the
   * model in order to carry out the investment operation with custom weights.
   *
   * @param amount    the amount for which the user wishes to invest in the portfolio.
   * @param date      the date on which the user wishes to invest in the portfolio.
   * @param commision the commission for the investment.
   * @param weights   the weights with which investment is to be made for portfolio.
   */
  void investWithWeights(Double amount, String date, String commision,
                         List<Double> weights);

  /**
   * Modifies the weights for investment within a strategy with the weights input by the user via
   * the view. This weights input would be passed to the model for modifying the weights in the
   * strategy.
   *
   * @param weights the new weights for investment within in the investment strategy.
   */
  void setStrategyWeights(TreeMap<String, Double> weights);

  /**
   * Invest in a strategy with the inputs received from the view. The inputs are passed onto the
   * model in order to carry out the investment operation with equal weights.
   *
   * @param amount    the amount for which the user wishes to invest in the portfolio.
   * @param date      the date on which the user wishes to invest in the portfolio.
   * @param commision the commission for the investment.
   */
  void investEqually(Double amount, String date, String commision);
}
