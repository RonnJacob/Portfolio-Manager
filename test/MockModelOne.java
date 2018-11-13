import howtoinvest.model.IPortfolio;
import howtoinvest.model.IPortfolioManager;

public class MockModelOne implements IPortfolioManager {
  private StringBuilder log;
  private int uniqueCode;

  public MockModelOne(StringBuilder log) {
    this.log = log;
    this.uniqueCode = 0;
  }

  public String getPortfolios(){
    this.log.append("All Portfolios");
    this.uniqueCode = 1;
    return String.format("%d", uniqueCode);
  }

  public void createPortfolio(String name) throws IllegalArgumentException{
    this.log.append("Create Portfolio" + "name");
    this.uniqueCode = 2;
  }

  public IPortfolio getPortfolio(int index) throws IllegalArgumentException{
    this.log.append("Get Portfolio" + "index");
    this.uniqueCode = 3;
    return null;
  }
}
