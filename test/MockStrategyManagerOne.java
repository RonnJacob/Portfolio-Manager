import java.util.LinkedList;
import java.util.List;

import howtoinvest.model.DollarCostAveraging;
import howtoinvest.model.DollarCostAveragingStrategyManager;
import howtoinvest.model.IManager;

public class MockStrategyManagerOne implements IManager<MockDollarCostAveragingOne> {
  protected StringBuilder log;
  private int uniqueCode;

  /**
   * Constructor that takes in a log that is to be used for logging operations.
   *
   * @param log the log string.
   */
  public MockStrategyManagerOne(StringBuilder log) {
    this.log = log;
    this.uniqueCode = 0;
  }
  @Override
  public List<String> getAll() {
    this.log.append("All Portfolios" + "\n");
    this.uniqueCode = 6;
    return new LinkedList<>();
  }

  @Override
  public void create(String name) throws IllegalArgumentException {
    return;
  }

  @Override
  public MockDollarCostAveragingOne getByIndex(int index) throws IllegalArgumentException {
    this.log.append("Get Portfolio " + index + "\n");
    this.uniqueCode = 60;
    return new MockDollarCostAveragingOne(this.log);
  }
}
