import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import howtoinvest.model.IInvestmentStrategy;

/**
 * This class is a mock DollarCostAveraging class for testing the controller implementation and
 * HowToInvestController tests. The mock class has a constructor which would take in a StringBuilder
 * variable which would behave as a log. This log would contain the list of actions that were taken
 * with the class with the provided input for the controller. The log would be used to test and
 * verify the controller implementations. The following class implements the IManager interface and
 * implements all the methods in the interface. This class essentially returns a uniqueCode for each
 * of the operations denoting that a particular operation has been called with the input provided
 * for the controller. The log appends the input for methods which have inputs for the controller
 * and this verifies that the input given by the controller is the expected input given to the class
 * . Note that each method called would log that the method was called and display parameters along
 * within the log message if parameters were provided.
 */
public class MockDollarCostAveragingOne implements IInvestmentStrategy {

  private StringBuilder log;

  /**
   * Constructor that takes in a log that is to be used for logging operations.
   *
   * @param log the log string.
   */
  public MockDollarCostAveragingOne(StringBuilder log) {
    TreeMap<String, Double> stockWeights;
    stockWeights = new TreeMap<>();
    stockWeights.put("FB", 50.0);
    stockWeights.put("MSFT", 50.0);
    this.log = log;
  }

  @Override
  public List<String> getStocks() {
    return new LinkedList<>();
  }

  @Override
  public void addStockToStrategy(String tickerSymbol) {
    this.log.append("Add stock to strategy.\n");
  }

  @Override
  public void setAmount(double amount) {
    this.log.append("Change amount to " + amount + "\n");
  }

  @Override
  public void setFrequency(int days) {

    this.log.append("Change frequency to " + days + " days.\n");
  }

  @Override
  public void setTimeRange(String begDate, String endDate) {
    this.log.append("Change date from " + begDate + " to " + endDate + " \n");

  }

  @Override
  public TreeMap<Date, HashMap<String, Double>> applyStrategy(Object portfolio, double commission) {
    this.log.append("Apply strategy with commision " + commission);
    return new TreeMap<>();
  }

  @Override
  public void setWeights(TreeMap weights) {

    this.log.append("Setting weights\n");
    this.log.append("\n");

  }

  @Override
  public void addMultipleStocksToStrategy(List tickerSymbolList) {
    this.log.append("Add stocks to strategy.\n");
  }
}
