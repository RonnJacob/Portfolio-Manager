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
 * would be included in the cost basis/value.
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
   * @param portfolioName the name of the portfolio to be created.
   */
  void createPortfolio(String portfolioName);

  /**
   * Opens the portfolio with the name passed as an argument. Retrieves the portfolio with the name
   * passed as an argument from a list of portfolios and opens the portfolio.
   * @param portfolioToOpen the portfolio that is to be opened.
   * @return a return message saying that the portfolio has been opened.
   */
  String openPortfolios(String portfolioToOpen);

  /**
   * Loads an object of type passed as an argument with a specified name.
   * @param filename the name of the file to be loaded.
   * @param typeOfList the type of the object.
   */
  void loadList(String filename, String typeOfList);

  /**
   * Retrieves the cost basis of the portfolio as of a date which is received as input from the
   * view and passed onto the model.
   * @param date the date for which we wish to retrieve the cost basis of the portfolio.
   * @return the cost basis of portfolio as of a specified date
   */
  Double getPortfolioCostBasis(String date);

  /**
   * Retrieves the value of the portfolio as of a date which is received as input from the
   * view and passed onto the model.
   * @param date the date for which we wish to retrieve the value of the portfolio.
   * @return the cost basis of portfolio as of a specified date
   */
  Double getPortfolioValue(String date);

  /**
   * Retrieves input from the view and passes the input in order to add a stock to a portfolio.
   * @param stockNameEntered the ticker symbol of the stock that is to be added to the portfolio.
   * @param amountEntered the amount for which stocks are to be entered.
   * @param dateEntered the date for which the share(s) are to be bought.
   * @param commissionEntered
   */
  void addStockToPortfolio(String stockNameEntered, double amountEntered, String dateEntered,
                           String commissionEntered);

  HashMap<String, Double> getStocksInPortfolio(String date);

  List<String> getStocksInStrategy();

  String[] showStrategies();


  void applyStrategy(String strategyToApply, String commision);

  void savePortfolio(String fileName);

  void saveStrategy(String fileName);

  void addStrategy(String strategyName);

  String openStrategies(String strategyToOpen);

  void addStockToStrategy(String stockNameEntered);

  void setStrategyAmount(String amount);

  void setStrategyFrequency(String frequency);

  void setStrategyTimerange(String begDate, String endDate);

  void investWithWeights(Double amount, String date, String commision,
                         List<Double> weights);

  void setStrategyWeights(TreeMap<String, Double> weights);

  void investEqually(Double amount, String date, String commision);
}
