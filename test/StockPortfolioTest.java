import org.junit.Before;
import org.junit.Test;

import howtoinvest.model.StockPortfolio;

import static org.junit.Assert.*;

public class StockPortfolioTest {

  StockPortfolio portfolio;

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void TestValidPortfolioCreation() {
    portfolio = new StockPortfolio();

    assertEquals("Total portfolio cost basis = 0.00\n"
                    + "Total portfolio value = 0.00",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    assertEquals("", portfolio.getPortfolioData());

    /**
     * Add a stock to the portfolio.
     */
    String output = this.portfolio.addStock("MSFT", 1000, "2018-11-11");

    assertEquals("9.14 shares of MSFT bought on 2018-11-11 for $1000.00", output);

    assertEquals("Total portfolio cost basis = 1000.00\n"
                    + "Total portfolio value = 1000.00",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    assertEquals("9.14 shares of MSFT for a total investment of $1000.00", portfolio.getPortfolioData());
  }

  @Test
  public void TestGetStockCostBasisAndStockValueInvalidDate() {
    portfolio = new StockPortfolio();
    String output = this.portfolio.addStock("MSFT", 1000, "2018-11-11");

    assertEquals("9.14 shares of MSFT bought on 2018-11-11 for $1000.00", output);

    assertEquals("Total portfolio cost basis = 1000.00\n"
                    + "Total portfolio value = 1000.00",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    assertEquals("9.14 shares of MSFT for a total investment of $1000.00",
            portfolio.getPortfolioData());

    try {
      assertEquals("Total portfolio cost basis = 1000.00\n"
                      + "Total portfolio value = 1000.00",
              portfolio.getStockCostBasisAndStockValue("2-1-1"));
      fail();
    }
    catch (IllegalArgumentException ex) {
      assertEquals("Share prices do not exist for given date.", ex.getMessage());
    }

    try {
      assertEquals("Total portfolio cost basis = 1000.00\n"
                      + "Total portfolio value = 1000.00",
              portfolio.getStockCostBasisAndStockValue("2000-10-10"));
      fail();
    }
    catch (IllegalArgumentException ex) {
      assertEquals("Share prices do not exist for given date.", ex.getMessage());
    }

    try {
      assertEquals("Total portfolio cost basis = 1000.00\n"
                      + "Total portfolio value = 1000.00",
              portfolio.getStockCostBasisAndStockValue("2020-10-10"));
      fail();
    }
    catch (IllegalArgumentException ex) {
      assertEquals("Cannot fetch share value for a future date.", ex.getMessage());
    }
  }
}