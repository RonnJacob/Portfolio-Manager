import howtoinvest.model.IPortfolio;

/**
 * Mock model to be used in the HowToInvestController Test.
 */
public class MockPortfolioManagerTwo extends MockPortfolioManagerOne {
  public MockPortfolioManagerTwo(StringBuilder log) {
    super(log);
  }

  @Override
  public IPortfolio getPortfolio(int index) throws IllegalArgumentException {
    log.append("Cannot fetch " + index + "th portfolio.\n");
    throw new IllegalArgumentException("Invalid portfolio.");
  }
}
