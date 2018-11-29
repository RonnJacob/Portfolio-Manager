import java.util.LinkedList;
import java.util.List;

import howtoinvest.model.IManager;

/**
 * This class is a mock class for testing the strategy model implementation and DollarCostAveraging
 * tests. The mock model has a constructor which would take in a StringBuilder variable which would
 * behave as a log. This log would contain the list of actions that were taken with the model with
 * the provided input for the controller. The log would be used to test and verify the controller
 * implementations. The following model implements the IManager interface and implements all the
 * methods in the interface. The log appends the input for methods which have inputs for the
 * controller and this verifies that the input given by the controller is the expected input given
 * to the model. Note that each method called would log that the method was called and display
 * parameters along within the log message if parameters were provided.
 */
public class MockStrategyManagerOne implements IManager<MockDollarCostAveragingOne> {
  protected StringBuilder log;

  /**
   * Constructor that takes in a log that is to be used for logging operations.
   *
   * @param log the log string.
   */
  public MockStrategyManagerOne(StringBuilder log) {
    this.log = log;
  }

  @Override
  public List<String> getAll() {
    this.log.append("All Portfolios" + "\n");
    return new LinkedList<>();
  }

  @Override
  public void create(String name) throws IllegalArgumentException {
    return;
  }

  @Override
  public MockDollarCostAveragingOne getByIndex(int index) throws IllegalArgumentException {
    this.log.append("Get Portfolio " + index + "\n");
    return new MockDollarCostAveragingOne(this.log);
  }
}
