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

  void createPortfolio(String portfolioName);

  String openPortfolios(String portfolioToOpen);

  void loadList(String filename, String typeOfList);

  String getPortfolioCostBasis(String date);

  String getPortfolioValue(String date);

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
