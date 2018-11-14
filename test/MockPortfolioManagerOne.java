import howtoinvest.model.IPortfolio;
import howtoinvest.model.IPortfolioManager;

/**
 * This class is a mock class for testing the controller implementation and HowToInvestController
 * tests. The mock model has a constructor which would take in a StringBuilder variable which would
 * behave as a log. This log would contain the list of actions that were taken with the model with
 * the provided input for the controller. The log would be used to test and verify the controller
 * implementations. The following model implements the IPortfolioManager interface and implements
 * all the methods in the interface. This model essentially returns a uniqueCode for each of the
 * operations denoting that a particular operation has been called with the input provided for the
 * controller. The log appends the input for methods which have inputs for the controller and this
 * verifies that the input given by the controller is the expected input given to the model . The
 * unique codes for each of the operations are as follows:
 * <ul>
 * <li>
 * A unique code of 1 represents that the getPortfolios method has been called.
 * </li>
 * <li>
 * A unique code of 2 denotes that the createPortfolio operation has been called with a name in
 * string format as the argument.
 * </li>
 * <li>
 * A unique code of 3 denotes that the getPortfolio operation has been called with a valid index as
 * the argument.
 * </li>
 * </ul>
 */
public class MockPortfolioManagerOne implements IPortfolioManager<MockStockPortfolioOne> {
  protected StringBuilder log;
  private int uniqueCode;

  /**
   * Constructor that takes in a log that is to be used for logging operations.
   *
   * @param log the log string.
   */
  public MockPortfolioManagerOne(StringBuilder log) {
    this.log = log;
    this.uniqueCode = 0;
  }

  /**
   * Returns a unique code of 1 and logs that all portfolios have been retrieved.
   *
   * @return a unique code of 1.
   */
  @Override
  public String getPortfolios() {
    this.log.append("All Portfolios" + "\n");
    this.uniqueCode = 1;
    return String.format("%d", uniqueCode);
  }


  /**
   * Logs the creation of a portfolio and sets the unique code to 2.
   *
   * @param name the name of the portfolio to be created.
   */
  @Override
  public void createPortfolio(String name) {
    this.log.append("Create Portfolio Name " + name + "\n");
    this.uniqueCode = 2;
  }

  /**
   * Returns a new MockStockPortfolioOne object, logs the getting a portfolio with an index and sets
   * the unique code to 3.
   *
   * @param index the index for which a portfolio has to be opened.
   * @return a new MockStockPortfolioOne object.
   */
  @Override
  public IPortfolio getPortfolio(int index) {
    this.log.append("Get Portfolio " + index + "\n");
    this.uniqueCode = 3;
    return new MockStockPortfolioOne(this.log);
  }
}
