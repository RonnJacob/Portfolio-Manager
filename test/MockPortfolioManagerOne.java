import java.util.LinkedList;
import java.util.List;

import howtoinvest.model.IManager;

/**
 * This class is a mock class for testing the controller implementation and HowToInvestController
 * tests. The mock model has a constructor which would take in a StringBuilder variable which would
 * behave as a log. This log would contain the list of actions that were taken with the model with
 * the provided input for the controller. The log would be used to test and verify the controller
 * implementations. The following model implements the IManager interface and implements all the
 * methods in the interface. This model essentially returns a uniqueCode for each of the operations
 * denoting that a particular operation has been called with the input provided for the controller.
 * The log appends the input for methods which have inputs for the controller and this verifies that
 * the input given by the controller is the expected input given to the model . Note that each
 * method called would log that the method was called and display parameters along within the log
 * message if parameters were provided.
 */
public class MockPortfolioManagerOne implements IManager<MockStockPortfolioOne> {
  protected StringBuilder log;

  /**
   * Constructor that takes in a log that is to be used for logging operations.
   *
   * @param log the log string.
   */
  public MockPortfolioManagerOne(StringBuilder log) {
    this.log = log;
  }

  /**
   * Returns a unique code of 1 and logs that all portfolios have been retrieved.
   *
   * @return a unique code of 1.
   */
  @Override
  public List<String> getAll() {
    this.log.append("Get All Portfolios" + "\n");
    return new LinkedList<>();
  }


  /**
   * Logs the creation of a portfolio and sets the unique code to 2.
   *
   * @param name the name of the portfolio to be created.
   */
  @Override
  public void create(String name) throws IllegalArgumentException {
    this.log.append("Create Portfolio Name " + name + "\n");
  }


  /**
   * Returns a new MockStockPortfolioOne object, logs the getting a portfolio with an index and sets
   * the unique code to 3.
   *
   * @param index the index for which a portfolio has to be opened.
   * @return a new MockStockPortfolioOne object.
   */
  @Override
  public MockStockPortfolioOne getByIndex(int index) throws IllegalArgumentException {
    this.log.append("Get Portfolio " + index + "\n");
    return new MockStockPortfolioOne(this.log);
  }
}
