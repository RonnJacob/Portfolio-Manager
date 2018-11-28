import org.junit.Test;

import java.util.HashMap;
import java.util.TreeMap;

import howtoinvest.model.StockPortfolio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * This class contains all the tests for the StockPortfolio class.
 */
public class StockPortfolioTest {

  private StockPortfolio portfolio;
  private TreeMap<String, Double> weights;

  @Test
  public void TestValidPortfolioCreation() {
    portfolio = new StockPortfolio();

    assertEquals(0, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(0, portfolio.getStockValue("2018-11-11"), 0.01);

    assertTrue(portfolio.getPortfolioData("2018-11-11").isEmpty());

    /**
     * Add a stock to the portfolio.
     */
    double output = this.portfolio.addStock("MSFT", 1000, "2018-11-11", 0);

    assertEquals(9.14, output, 0.01);

    assertEquals(1000, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(1000, portfolio.getStockValue("2018-11-11"), 0.01);

    HashMap<String, Double> portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(9.14, portfolioData.get("MSFT"), 0.01);

    portfolioData = portfolio.getPortfolioData("2018-11-10");
    assertEquals(1, portfolioData.size());
    assertEquals(0, portfolioData.get("MSFT"), 0.01);

    portfolioData = portfolio.getPortfolioData("2018-11-12");
    assertEquals(1, portfolioData.size());
    assertEquals(9.14, portfolioData.get("MSFT"), 0.01);
  }

  @Test
  public void TestGetStockCostBasisAndStockValueInvalidDate() {
    portfolio = new StockPortfolio();

    double output = this.portfolio.addStock("MSFT", 1000, "2018-11-11", 0);

    assertEquals(9.14, output, 0.01);
    assertEquals(1000, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(1000, portfolio.getStockValue("2018-11-11"), 0.01);

    HashMap<String, Double> portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(9.14, portfolioData.get("MSFT"), 0.01);

    assertEquals(0, portfolio.getStockCostBasis("2-1-1"), 0.01);

    try {
      assertEquals(1000, portfolio.getStockValue("2-1-1"), 0.01);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Cannot fetch current share price:Share "
              + "prices do not exist for given date.", ex.getMessage());
    }

    try {
      assertEquals(1000, portfolio.getStockValue(null), 0.01);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      assertEquals(1000, portfolio.getStockCostBasis(null), 0.01);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    assertEquals(0.0, portfolio.getStockCostBasis("2000-10-10"), 0.01);

    try {
      assertEquals(1000, portfolio.getStockValue("2000-10-10"), 0.01);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Cannot fetch current share price:Share "
              + "prices do not exist for given date.", ex.getMessage());
    }

    try {
      assertEquals(1000, portfolio.getStockValue("2020-10-10"), 0.01);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Cannot fetch current share price:Cannot "
              + "fetch share value for a future date.", ex.getMessage());
    }

    assertEquals(1000, portfolio.getStockCostBasis("2020-10-10"), 0.01);
  }

  @Test
  public void testAddStockInvalidDateFormat() {
    portfolio = new StockPortfolio();

    double output = portfolio.addStock("MSFT", 10000, "2018-01-01", 0);
    assertEquals(100, output, 0.01);

    HashMap<String, Double> portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(100, portfolioData.get("MSFT"), 0.01);

    assertEquals(10000, portfolio.getStockCostBasis("2018-01-01"), 0.01);
    assertEquals(10000, portfolio.getStockValue("2018-01-01"), 0.01);


    try {
      portfolio.addStock("FB", 1000, "2018-01", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(100, portfolioData.get("MSFT"), 0.01);

    assertEquals(10000, portfolio.getStockCostBasis("2018-01-01"), 0.01);
    assertEquals(10000, portfolio.getStockValue("2018-01-01"), 0.01);

    assertEquals(10000, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(10942, portfolio.getStockValue("2018-11-11"), 0.01);

    try {
      portfolio.addStock("FB", 1000, "2018", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.addStock("FB", 1000, "", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.addStock("FB", 1000, "2018/01/01", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.addStock("FB", 1000, "01-2018-01", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.addStock("FB", 1000, "01-01-2018", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.addStock("FB", 1000, "2018-13-01", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.addStock("FB", 1000, "2018-12-33", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.addStock("FB", 1000, "2018-02-30", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(100, portfolioData.get("MSFT"), 0.01);

  }

  @Test
  public void TestAddStockInvalidAmount() {
    portfolio = new StockPortfolio();
    double output = portfolio.addStock("MSFT", 0, "2018-01-01", 0);

    assertEquals(0, output, 0.01);

    HashMap<String, Double> portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(0, portfolioData.get("MSFT"), 0.01);

    assertEquals(0, portfolio.getStockCostBasis("2018-11-12"), 0.01);
    assertEquals(0, portfolio.getStockValue("2018-11-12"), 0.01);


    try {
      portfolio.addStock("FB", -0.0001, "2018-01-01", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid amount", ex.getMessage());
    }

    assertEquals(0, portfolio.getStockCostBasis("2018-11-12"), 0.01);
    assertEquals(0, portfolio.getStockValue("2018-11-12"), 0.01);

    try {
      portfolio.addStock("FB", -1000, "2018-01-01", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid amount", ex.getMessage());
    }

    assertEquals(0, portfolio.getStockCostBasis("2018-11-12"), 0.01);
    assertEquals(0, portfolio.getStockValue("2018-11-12"), 0.01);

    output = this.portfolio.addStock("FB", 1000, "2018-11-11", 0);

    assertEquals(9.14, output, 0.01);

    assertEquals(1000, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(1000, portfolio.getStockValue("2018-11-11"), 0.01);


    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(2, portfolioData.size());
    assertEquals(0, portfolioData.get("MSFT"), 0.01);
    assertEquals(9.14, portfolioData.get("FB"), 0.01);

    try {
      portfolio.addStock("FB", -1000, "2018-01-01", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid amount", ex.getMessage());
    }
    assertEquals(1000, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(1000, portfolio.getStockValue("2018-11-11"), 0.01);

    portfolioData = portfolio.getPortfolioData("2018-11-11");

    assertEquals(0, portfolioData.get("MSFT"), 0.01);
    assertEquals(9.14, portfolioData.get("FB"), 0.01);
  }

  @Test
  public void TestAddStockInvalidDate() {
    portfolio = new StockPortfolio();
    try {
      portfolio.addStock("FB", 1000, "2-1-1", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date. Please enter date again.Share prices do not "
                      + "exist for given date.",
              ex.getMessage());
    }
    double output = this.portfolio.addStock("MSFT", 1000, "2018-11-11", 0);

    assertEquals(9.14, output, 0.01);

    assertEquals(1000, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(1000, portfolio.getStockValue("2018-11-11"), 0.01);

    HashMap<String, Double> portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(9.14, portfolioData.get("MSFT"), 0.01);

    try {
      portfolio.addStock("FB", 1500, null, 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.",
              ex.getMessage());
    }

    try {
      portfolio.addStock("FB", 1500, "2000-10-10", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date. Please enter date again.Share prices do not"
                      + " exist for given date.",
              ex.getMessage());
    }

    assertEquals(1000, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(1000, portfolio.getStockValue("2018-11-11"), 0.01);

    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(9.14, portfolioData.get("MSFT"), 0.01);

    try {
      portfolio.addStock("MSFT", 2000, "2020-10-10", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date. Please enter date again.Cannot fetch share value "
                      + "for a future date.",
              ex.getMessage());
    }

    assertEquals(1000, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(1000, portfolio.getStockValue("2018-11-11"), 0.01);

    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(9.14, portfolioData.get("MSFT"), 0.01);
  }

  @Test
  public void testGetStockBasisAndStockValueDateFormat() {
    portfolio = new StockPortfolio();
    double output = portfolio.addStock("MSFT", 10000, "2018-01-01", 0);
    assertEquals(100.00, output, 0.01);

    HashMap<String, Double> portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(100, portfolioData.get("MSFT"), 0.01);

    assertEquals(10000, portfolio.getStockCostBasis("2018-01-01"), 0.01);
    assertEquals(10000, portfolio.getStockValue("2018-01-01"), 0.01);

    try {
      portfolio.getStockCostBasis("2018-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockValue("2018-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockCostBasis("2018");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockValue("2018");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockCostBasis("");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockValue("");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockCostBasis("2018/01/01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockValue("2018/01/01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockCostBasis("01-2018-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockValue("01-2018-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockCostBasis("01-01-2018");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockValue("01-01-2018");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockCostBasis("2018-13-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockValue("2018-13-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockCostBasis("2018-12-33");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockValue("2018-12-33");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockCostBasis("2018-02-30");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockValue("2018-02-30");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }
  }

  @Test
  public void TestAddStockInvalidTickerSymbol() {
    portfolio = new StockPortfolio();
    try {
      portfolio.addStock("", 1000, "2018-01-01", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid stock name or ticker symbol", ex.getMessage());
    }

    assertEquals(0.00, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(0.00, portfolio.getStockValue("2018-11-11"), 0.01);

    assertTrue(portfolio.getPortfolioData("2018-11-11").isEmpty());

    try {
      portfolio.addStock(" ", 1000, "2018-01-01", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid stock name or ticker symbol", ex.getMessage());
    }

    try {
      portfolio.addStock(null, 1000, "2018-01-01", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid stock name or ticker symbol", ex.getMessage());
    }

    try {
      portfolio.addStock("This ticker does not exit ", 1000, "2018-01-01", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid stock name or ticker symbol", ex.getMessage());
    }

    assertTrue(portfolio.getPortfolioData("2018-11-11").isEmpty());
  }

  @Test
  public void TestAddStock() {
    portfolio = new StockPortfolio();

    assertTrue(portfolio.getPortfolioData("2018-11-11").isEmpty());

    assertEquals(0.00, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(0.00, portfolio.getStockValue("2018-11-11"), 0.01);

    assertEquals(0.00, portfolio.getStockCostBasis("2016-11-11"), 0.01);
    assertEquals(0.00, portfolio.getStockValue("2016-11-11"), 0.01);

    assertEquals(0.00, portfolio.getStockCostBasis("2020-11-11"), 0.01);
    assertEquals(0.00, portfolio.getStockValue("2020-11-11"), 0.01);

    double output = portfolio.addStock("MSFT", 10000, "2018-01-01", 0);

    assertEquals(100.00, output, 0.01);

    HashMap<String, Double> portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(100, portfolioData.get("MSFT"), 0.01);

    portfolioData = portfolio.getPortfolioData("2016-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(0, portfolioData.get("MSFT"), 0.01);

    portfolioData = portfolio.getPortfolioData("2020-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(100, portfolioData.get("MSFT"), 0.01);

    assertEquals(10000.00, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(10942.00, portfolio.getStockValue("2018-11-11"), 0.01);

    try {
      portfolio.getStockValue("2016-11-11");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Cannot fetch current share price:Share "
              + "prices do not exist for given date.", ex.getMessage());
    }

    try {
      portfolio.getStockValue("2020-11-11");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Cannot fetch current share price:Cannot "
              + "fetch share value for a future date.", ex.getMessage());
    }

    output = portfolio.addStock("MSFT", 10000, "2016-12-01", 0);

    assertEquals(200.00, output, 0.01);

    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(300, portfolioData.get("MSFT"), 0.01);

    portfolioData = portfolio.getPortfolioData("2016-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(0, portfolioData.get("MSFT"), 0.01);

    portfolioData = portfolio.getPortfolioData("2020-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(300, portfolioData.get("MSFT"), 0.01);

    assertEquals(20000, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(32826, portfolio.getStockValue("2018-11-11"), 0.01);

    assertEquals(10000, portfolio.getStockCostBasis("2017-12-01"), 0.01);
    assertEquals(15024, portfolio.getStockValue("2017-12-01"), 0.01);

    assertEquals(10000, portfolio.getStockCostBasis("2016-12-01"), 0.01);
    assertEquals(10000, portfolio.getStockValue("2016-12-01"), 0.01);

    try {
      portfolio.getStockValue("2016-11-11");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Cannot fetch current share price:Share"
              + " prices do not exist for given date.", ex.getMessage());
    }

    output = portfolio.addStock("MSFT", 23.45, "2018-11-11", 0);

    assertEquals(0.21, output, 0.01);

    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(300.21, portfolioData.get("MSFT"), 0.01);

    portfolioData = portfolio.getPortfolioData("2016-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(0, portfolioData.get("MSFT"), 0.01);

    portfolioData = portfolio.getPortfolioData("2020-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(300.21, portfolioData.get("MSFT"), 0.01);

    assertEquals(20023.45, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(32849.45, portfolio.getStockValue("2018-11-11"), 0.01);

    assertEquals(10000, portfolio.getStockCostBasis("2017-12-01"), 0.01);
    assertEquals(15024, portfolio.getStockValue("2017-12-01"), 0.01);

    assertEquals(10000, portfolio.getStockCostBasis("2016-12-01"), 0.01);
    assertEquals(10000, portfolio.getStockValue("2016-12-01"), 0.01);

    try {
      portfolio.getStockValue("2016-11-11");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Cannot fetch current share price:Share "
              + "prices do not exist for given date.", ex.getMessage());
    }

    /**
     * Add with same date.
     */

    output = portfolio.addStock("MSFT", 213.45, "2018-11-11", 0);

    assertEquals(1.95, output, 0.01);

    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(302.16, portfolioData.get("MSFT"), 0.01);

    portfolioData = portfolio.getPortfolioData("2016-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(0, portfolioData.get("MSFT"), 0.01);

    portfolioData = portfolio.getPortfolioData("2020-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(302.16, portfolioData.get("MSFT"), 0.01);

    assertEquals(20236.90, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(33062.90, portfolio.getStockValue("2018-11-11"), 0.01);

    assertEquals(10000, portfolio.getStockCostBasis("2017-12-01"), 0.01);
    assertEquals(15024, portfolio.getStockValue("2017-12-01"), 0.01);

    assertEquals(10000, portfolio.getStockCostBasis("2016-12-01"), 0.01);
    assertEquals(10000, portfolio.getStockValue("2016-12-01"), 0.01);

    output = portfolio.addStock("MSFT", 5000, "2016-12-01", 0);

    assertEquals(100.00, output, 0.01);

    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(402.16, portfolioData.get("MSFT"), 0.01);

    portfolioData = portfolio.getPortfolioData("2016-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(0, portfolioData.get("MSFT"), 0.01);

    portfolioData = portfolio.getPortfolioData("2020-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(402.16, portfolioData.get("MSFT"), 0.01);

    assertEquals(25236.90, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(44004.90, portfolio.getStockValue("2018-11-11"), 0.01);

    assertEquals(15000.00, portfolio.getStockCostBasis("2017-12-01"), 0.01);
    assertEquals(22536, portfolio.getStockValue("2017-12-01"), 0.01);

    assertEquals(15000, portfolio.getStockCostBasis("2016-12-01"), 0.01);
    assertEquals(15000, portfolio.getStockValue("2016-12-01"), 0.01);

    assertEquals(25000, portfolio.getStockCostBasis("2018-01-01"), 0.01);
    assertEquals(40000, portfolio.getStockValue("2018-01-01"), 0.01);

    /**
     * Add another Stock
     */
    output = portfolio.addStock("FB", 10000, "2018-01-01", 100);

    assertEquals(95.24, output, 0.01);

    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(2, portfolioData.size());
    assertEquals(402.16, portfolioData.get("MSFT"), 0.01);
    assertEquals(95.2380, portfolioData.get("FB"), 0.01);

    portfolioData = portfolio.getPortfolioData("2016-12-01");
    assertEquals(2, portfolioData.size());
    assertEquals(300, portfolioData.get("MSFT"), 0.01);
    assertEquals(0, portfolioData.get("FB"), 0.01);


    portfolioData = portfolio.getPortfolioData("2020-11-11");
    assertEquals(2, portfolioData.size());
    assertEquals(402.16, portfolioData.get("MSFT"), 0.01);
    assertEquals(95.2380, portfolioData.get("FB"), 0.01);

    portfolioData = portfolio.getPortfolioData("2017-12-01");
    assertEquals(2, portfolioData.size());
    assertEquals(300, portfolioData.get("MSFT"), 0.01);
    assertEquals(0, portfolioData.get("FB"), 0.01);

    assertEquals(35336.90, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(54425.85, portfolio.getStockValue("2018-11-11"), 0.01);

    assertEquals(15000.00, portfolio.getStockCostBasis("2017-12-01"), 0.01);
    assertEquals(22536, portfolio.getStockValue("2017-12-01"), 0.01);

    assertEquals(15000, portfolio.getStockCostBasis("2016-12-01"), 0.01);
    assertEquals(15000, portfolio.getStockValue("2016-12-01"), 0.01);

    assertEquals(35100, portfolio.getStockCostBasis("2018-01-01"), 0.01);
    assertEquals(50000, portfolio.getStockValue("2018-01-01"), 0.01);

    /**
     * Add another Stock
     */
    output = portfolio.addStock("GOOGL", 10000, "2017-01-01", 10);

    assertEquals(1404.49, output, 0.01);

    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(3, portfolioData.size());
    assertEquals(402.16, portfolioData.get("MSFT"), 0.01);
    assertEquals(95.2380, portfolioData.get("FB"), 0.01);
    assertEquals(1404.49, portfolioData.get("GOOGL"), 0.01);

    portfolioData = portfolio.getPortfolioData("2016-12-01");
    assertEquals(3, portfolioData.size());
    assertEquals(300, portfolioData.get("MSFT"), 0.01);
    assertEquals(0, portfolioData.get("FB"), 0.01);
    assertEquals(0, portfolioData.get("GOOGL"), 0.01);


    portfolioData = portfolio.getPortfolioData("2020-11-11");
    assertEquals(3, portfolioData.size());
    assertEquals(402.16, portfolioData.get("MSFT"), 0.01);
    assertEquals(95.2380, portfolioData.get("FB"), 0.01);
    assertEquals(1404.49, portfolioData.get("GOOGL"), 0.01);

    portfolioData = portfolio.getPortfolioData("2017-12-01");
    assertEquals(3, portfolioData.size());
    assertEquals(300, portfolioData.get("MSFT"), 0.01);
    assertEquals(0, portfolioData.get("FB"), 0.01);
    assertEquals(1404.49, portfolioData.get("GOOGL"), 0.01);

    assertEquals(45346.90, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(69060.68, portfolio.getStockValue("2018-11-11"), 0.01);

    assertEquals(25010.00, portfolio.getStockCostBasis("2017-12-01"), 0.01);
    assertEquals(32536, portfolio.getStockValue("2017-12-01"), 0.01);

    assertEquals(15000, portfolio.getStockCostBasis("2016-12-01"), 0.01);
    assertEquals(15000, portfolio.getStockValue("2016-12-01"), 0.01);

    assertEquals(45110, portfolio.getStockCostBasis("2018-01-01"), 0.01);
    assertEquals(64044.94, portfolio.getStockValue("2018-01-01"), 0.01);

    output = portfolio.addStock("FB", 100, "2016-12-12", 0);

    assertEquals(0.95, output, 0.01);

    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(3, portfolioData.size());
    assertEquals(402.16, portfolioData.get("MSFT"), 0.01);
    assertEquals(96.19, portfolioData.get("FB"), 0.01);
    assertEquals(1404.49, portfolioData.get("GOOGL"), 0.01);

    portfolioData = portfolio.getPortfolioData("2016-12-01");
    assertEquals(3, portfolioData.size());
    assertEquals(300, portfolioData.get("MSFT"), 0.01);
    assertEquals(0, portfolioData.get("FB"), 0.01);
    assertEquals(0, portfolioData.get("GOOGL"), 0.01);


    portfolioData = portfolio.getPortfolioData("2020-11-11");
    assertEquals(3, portfolioData.size());
    assertEquals(402.16, portfolioData.get("MSFT"), 0.01);
    assertEquals(96.19, portfolioData.get("FB"), 0.01);
    assertEquals(1404.49, portfolioData.get("GOOGL"), 0.01);

    portfolioData = portfolio.getPortfolioData("2017-12-01");
    assertEquals(3, portfolioData.size());
    assertEquals(300, portfolioData.get("MSFT"), 0.01);
    assertEquals(0.95, portfolioData.get("FB"), 0.01);
    assertEquals(1404.49, portfolioData.get("GOOGL"), 0.01);

    assertEquals(45446.90, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(69164.89, portfolio.getStockValue("2018-11-11"), 0.01);

    assertEquals(25110, portfolio.getStockCostBasis("2017-12-01"), 0.01);
    assertEquals(32636, portfolio.getStockValue("2017-12-01"), 0.01);

    assertEquals(15000, portfolio.getStockCostBasis("2016-12-01"), 0.01);
    assertEquals(15000, portfolio.getStockValue("2016-12-01"), 0.01);

    assertEquals(45210, portfolio.getStockCostBasis("2018-01-01"), 0.01);
    assertEquals(64144.95, portfolio.getStockValue("2018-01-01"), 0.01);
  }

  @Test
  public void TestGetCommission() {
    portfolio = new StockPortfolio();

    try {
      portfolio.getCommission("");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid commission", ex.getMessage());
    }

    try {
      portfolio.getCommission(null);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid commission", ex.getMessage());
    }

    try {
      portfolio.getCommission("a");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid commission", ex.getMessage());
    }

    try {
      portfolio.getCommission("g");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid commission", ex.getMessage());
    }

    try {
      portfolio.getCommission("z");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid commission", ex.getMessage());
    }

    try {
      portfolio.getCommission("*");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid commission", ex.getMessage());
    }

    double output = portfolio.getCommission("0");
    assertEquals(0, output, 0.01);

    output = portfolio.getCommission(" 0.0001 ");
    assertEquals(0.0001, output, 0.01);

    output = portfolio.getCommission("1f");
    assertEquals(1, output, 0.01);

    output = portfolio.getCommission("1d");
    assertEquals(1, output, 0.01);

    output = portfolio.getCommission("500.25");
    assertEquals(500.25, output, 0.01);

    output = portfolio.getCommission("l");
    assertEquals(1, output, 0.01);

    output = portfolio.getCommission("m");
    assertEquals(0.0, output, 0.01);

    output = portfolio.getCommission("h");
    assertEquals(2.05, output, 0.01);
  }

  @Test
  public void TestInvestInvalid() {
    portfolio = new StockPortfolio();
    weights = new TreeMap<>();

    weights.put("FB", 25.0);
    weights.put("MSFT", 75.0);

    HashMap<String, Double> output =
            portfolio.invest(-1, weights, false, "2018-11-11", 0);
    assertTrue(output.isEmpty());

    output =
            portfolio.invest(100, weights, false, null, 0);
    assertTrue(output.isEmpty());

    output =
            portfolio.invest(1, weights, false, "2018-11-jadnlk", 0);
    assertTrue(output.isEmpty());

    output =
            portfolio.invest(1, weights, false, " ", 0);
    assertTrue(output.isEmpty());

    output =
            portfolio.invest(1, weights, false, "2018-11-11", -0.01);
    assertTrue(output.isEmpty());

    try {
      portfolio.invest(-1, null, false, "2018-11-11", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid weights", ex.getMessage());
    }

    weights.put("GOOGL", -150d);

    try {
      output = portfolio.invest(100, weights, false, "2018-11-11", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid weights", ex.getMessage());
    }

    weights.put("GOOGL", -50d);

    try {
      output = portfolio.invest(100, weights, false, "2018-11-11", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid weights", ex.getMessage());
    }

    weights.put("GOOGL", 0.01d);

    try {
      output = portfolio.invest(100, weights, false, "2018-11-11", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid weights", ex.getMessage());
    }

    HashMap<String, Double> portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(0, portfolioData.size());

    assertEquals(0.00, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(0.00, portfolio.getStockValue("2018-11-11"), 0.01);


    weights.put("GOOGL", 0.0);
    output = portfolio.invest(1000, weights, false, "2018-11-11", 0);
    assertEquals(3, output.size());
    assertEquals(0.00, output.get("GOOGL"), 0.01);
    assertEquals(6.85, output.get("MSFT"), 0.01);
    assertEquals(2.28, output.get("FB"), 0.01);

    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(3, portfolioData.size());
    assertEquals(0.00, portfolioData.get("GOOGL"), 0.01);
    assertEquals(6.85, portfolioData.get("MSFT"), 0.01);
    assertEquals(2.28, portfolioData.get("FB"), 0.01);

    assertEquals(1000.00, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(1000.00, portfolio.getStockValue("2018-11-11"), 0.01);

    weights.put("GOOGL", 25.0);
    weights.put("MSFT", 50.0);
    output = portfolio.invest(1000, weights, false, "2018-11-11", 0);
    assertEquals(3, output.size());
    assertEquals(23.99, output.get("GOOGL"), 0.01);
    assertEquals(4.56, output.get("MSFT"), 0.01);
    assertEquals(2.28, output.get("FB"), 0.01);

    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(3, portfolioData.size());
    assertEquals(23.99, portfolioData.get("GOOGL"), 0.01);
    assertEquals(11.42, portfolioData.get("MSFT"), 0.01);
    assertEquals(4.56, portfolioData.get("FB"), 0.01);

    assertEquals(2000.00, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(2000.00, portfolio.getStockValue("2018-11-11"), 0.01);

    /**
     * Investing when MSFT will throw an invalid date.
     */
    output = portfolio.invest(1000, weights, false, "2016-10-10", 0);
    assertEquals(2, output.size());
    assertEquals(62.5, output.get("GOOGL"), 0.01);
    assertEquals(2.5, output.get("FB"), 0.01);

    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(3, portfolioData.size());
    assertEquals(86.49, portfolioData.get("GOOGL"), 0.01);
    assertEquals(11.42, portfolioData.get("MSFT"), 0.01);
    assertEquals(7.06, portfolioData.get("FB"), 0.01);

    assertEquals(2500.00, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(2924.8, portfolio.getStockValue("2018-11-11"), 0.01);

    /**
     * investing with equal weights
     */
    output = portfolio.invest(1000, weights, true, "2018-10-10", 0);
    assertEquals(3, output.size());
    assertEquals(33.33, output.get("GOOGL"), 0.01);
    assertEquals(11.42, portfolioData.get("MSFT"), 0.01);
    assertEquals(3.17, output.get("FB"), 0.01);

    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(3, portfolioData.size());
    assertEquals(119.82, portfolioData.get("GOOGL"), 0.01);
    assertEquals(14.75, portfolioData.get("MSFT"), 0.01);
    assertEquals(10.24, portfolioData.get("FB"), 0.01);

    assertEquals(3500.00, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(3984.23, portfolio.getStockValue("2018-11-11"), 0.01);

    output = portfolio.invest(1000, weights, true, "2016-10-10", 0);
    assertEquals(2, output.size());
    assertEquals(83.33, output.get("GOOGL"), 0.01);
    assertEquals(3.33, output.get("FB"), 0.01);

    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(3, portfolioData.size());
    assertEquals(203.15, portfolioData.get("GOOGL"), 0.01);
    assertEquals(14.75, portfolioData.get("MSFT"), 0.01);
    assertEquals(13.57, portfolioData.get("FB"), 0.01);

    assertEquals(4166.66, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(5217.29, portfolio.getStockValue("2018-11-11"), 0.01);

  }
}