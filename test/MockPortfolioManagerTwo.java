import howtoinvest.model.IPortfolio;

/**
 * The following mock model extends MockPortfolioManagerOne which is an implementation of
 * HowToInvestController. This mock model would be used to check if the controller transmits a
 * message to the appendable if a portfolio index is entered incorrectly causing it be an invalid
 * input. In this model, the getPortfolio operation is overriden to throw an invalid portfolio
 * exception to test the scenario mentioned above.
 */
public class MockPortfolioManagerTwo extends MockPortfolioManagerOne {

  /**
   * Constructor that takes in a log that is to be used for logging operations.
   *
   * @param log the log string.
   */
  public MockPortfolioManagerTwo(StringBuilder log) {
    super(log);
  }

  /**
   * Throws an exception every time the getPortfolio method is called.
   *
   * @param index the index for which a portfolio has to be opened.
   * @return nothing as a exception is thrown each time the method is called.
   * @throws IllegalArgumentException every time the getPortfolio method is called.
   */
  @Override
  public IPortfolio getPortfolio(int index) throws IllegalArgumentException {
    log.append("Cannot fetch " + index + "th portfolio.\n");
    throw new IllegalArgumentException("Invalid portfolio.");
  }
}
