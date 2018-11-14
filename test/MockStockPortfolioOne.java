import howtoinvest.model.IPortfolio;

/**
 * This class is a mock class for testing the controller implementation and HowToInvestController
 * tests. The mock model has a constructor which would take in a StringBuilder variable which would
 * behave as a log. This log would contain the list of actions that were taken with the model with
 * the provided input for the controller. The log would be used to test and verify the controller
 * implementations. The following model implements the IPortfolio interface and implements all the
 * methods in the interface. This model essentially returns a uniqueCode for each of the operations
 * denoting that a particular operation has been called with the input provided for the controller.
 * The log appends the input for methods which have inputs for the controller and this verifies that
 * the input given by the controller is the expected input given to the model . The unique codes for
 * each of the operations are as follows:
 * <ul>
 * <li>
 * A unique code of 31 represents that the getPortfolioData method has been called.
 * </li>
 * <li>
 * A unique code of 32 denotes that the addStock operation has been called with a ticker symbol in
 * string format as the argument, an amount as double and a date in string format.
 * </li>
 * <li>
 * A unique code of 33 denotes that the getStockCostBasisAndStockValue operation has been called
 * with a date in string format.
 * </li>
 * </ul>
 */
public class MockStockPortfolioOne implements IPortfolio {

  private StringBuilder log;
  private int uniqueCode;

  /**
   * Constructor that takes in a log that is to be used for logging operations.
   *
   * @param log the log string.
   */
  public MockStockPortfolioOne(StringBuilder log) {
    this.log = log;
    this.uniqueCode = 0;
  }

  /**
   * Returns a unique code of 31 and logs that the composition of portfolio has been retrieved.
   *
   * @return a unique code of 31.
   */
  @Override
  public String getPortfolioData() {
    this.log.append("Get portfolio composition" + "\n");
    this.uniqueCode = 31;
    return String.format("%d", uniqueCode);
  }


  /**
   * Returns a unique code of 33 and logs that cost basis has been retrieved.
   *
   * @return a unique code of 33.
   */
  @Override
  public String getStockCostBasisAndStockValue(String date) {
    this.log.append("Get cost basis for " + date + "\n");
    this.uniqueCode = 33;
    return String.format("%d", uniqueCode);
  }

  /**
   * Returns a unique code of 32 and logs that share of X has been bought for Y amount on Z date.
   *
   * @return a unique code of 32.
   */
  @Override
  public String addStock(String identifier, double amount, String date) {
    this.log.append("Get share for " + identifier + " for " + amount + " on " + date + "\n");
    this.uniqueCode = 32;
    return String.format("%d", uniqueCode);

  }
}
