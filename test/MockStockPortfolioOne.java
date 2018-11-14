import howtoinvest.model.IPortfolio;

/**
 * Mock model implementing IPortfolio to be used in the HowToInvestController Test.
 */
public class MockStockPortfolioOne implements IPortfolio {

  private StringBuilder log;
  private int uniqueCode;

  public MockStockPortfolioOne(StringBuilder log) {
    this.log = log;
    this.uniqueCode = 0;
  }

  @Override
  public String getPortfolioData() {
    this.log.append("Get portfolio composition" + "\n");
    this.uniqueCode = 31;
    return String.format("%d", uniqueCode);
  }

  @Override
  public String getStockCostBasisAndStockValue(String date) {
    this.log.append("Get cost basis for " + date + "\n");
    this.uniqueCode = 33;
    return String.format("%d", uniqueCode);
  }

  @Override
  public String addStock(String identifier, double amount, String date) {
    this.log.append("Get share for " + identifier + " for " + amount + " on " + date + "\n");
    this.uniqueCode = 32;
    return String.format("%d", uniqueCode);

  }
}
