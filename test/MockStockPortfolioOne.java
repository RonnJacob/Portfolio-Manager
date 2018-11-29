import java.util.HashMap;
import java.util.TreeMap;

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
 * the input given by the controller is the expected input given to the model .Note that each method
 * called would log that the method was called and display parameters along within the log message
 * if parameters were provided.
 */
public class MockStockPortfolioOne implements IPortfolio {

  private StringBuilder log;

  /**
   * Constructor that takes in a log that is to be used for logging operations.
   *
   * @param log the log string.
   */
  public MockStockPortfolioOne(StringBuilder log) {
    this.log = log;
  }


  @Override
  public HashMap<String, Double> getPortfolioData(String date) {
    this.log.append("Get composition for " + date + "\n");
    return new HashMap<>();
  }

  @Override
  public double getStockCostBasis(String date) throws IllegalArgumentException {
    this.log.append("Get cost basis for " + date + "\n");
    return 0;
  }

  @Override
  public double getStockValue(String date) throws IllegalArgumentException {
    this.log.append("Get value for " + date + "\n");
    return 0;
  }

  @Override
  public double addStock(String identifier, double amount, String date, double commission)
          throws IllegalArgumentException {
    this.log.append("Add stock " + identifier + " for " + amount + " on " + date + " with "
            + commission + "\n");
    return 100;
  }

  @Override
  public double getCommission(String commission) throws IllegalArgumentException {
    return 100;
  }

  @Override
  public HashMap<String, Double> invest(double amount, TreeMap weights, boolean equalWeights,
                                        String date, double commission)
          throws IllegalArgumentException {
    this.log.append("Invest " + amount + " " + equalWeights + " " + date + " " + commission);
    for (Object weight : weights.values()) {
      this.log.append(" " + weight.toString() + " ");
    }
    this.log.append("\n");
    return new HashMap<>();
  }
}
