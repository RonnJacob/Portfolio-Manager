import howtoinvest.model.IPortfolio;

public class MockPortfolioManagerTwo extends MockPortfolioManagerOne {


  public MockPortfolioManagerTwo(StringBuilder log) {
    super(log);
  }

  @Override
  public IPortfolio getPortfolio(int index) throws IllegalArgumentException {
    log.append("Cannot fetch "+index+"th portfolio.\n");
    throw new IllegalArgumentException("Invalid portfolio.");
  }
}
