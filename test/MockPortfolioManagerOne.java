import howtoinvest.model.IPortfolio;
import howtoinvest.model.IPortfolioManager;

public class MockPortfolioManagerOne implements IPortfolioManager<MockStockPortfolioOne> {
  protected StringBuilder log;
  private int uniqueCode;

  public MockPortfolioManagerOne(StringBuilder log) {
    this.log = log;
    this.uniqueCode = 0;
  }

  @Override
  public String getPortfolios(){
    this.log.append("All Portfolios"+"\n");
    this.uniqueCode = 1;
    return String.format("%d", uniqueCode);
  }


  @Override
  public void createPortfolio(String name) throws IllegalArgumentException{
    this.log.append("Create Portfolio Name "+name+"\n");
    this.uniqueCode = 2;
  }


  @Override
  public IPortfolio getPortfolio(int index) throws IllegalArgumentException{
    this.log.append("Get Portfolio " + index+"\n");
    this.uniqueCode = 3;
    return new MockStockPortfolioOne(this.log);
  }
}
