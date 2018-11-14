import org.junit.Before;
import org.junit.Test;

import howtoinvest.model.IPortfolio;
import howtoinvest.model.StockPortfolioManager;

import static org.junit.Assert.*;

public class StockPortfolioManagerTest {

  private StockPortfolioManager portfolioManager;
  private IPortfolio portfolio;

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void TestPortfolioManagerCreationAndGetPortfolio() {
    portfolioManager = new StockPortfolioManager();

    assertEquals("1. Default StockPortfolio", portfolioManager.getPortfolios());

    /**
     * Check the portfolio retrieved performs some basic operations.
     */
    portfolio = portfolioManager.getPortfolio(1);

    assertEquals("", portfolio.getPortfolioData());

    assertEquals("Total portfolio cost basis = 0.00\n"
                    + "Total portfolio value = 0.00",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    String output = this.portfolio.addStock("MSFT", 1000, "2018-11-11");

    assertEquals("9.14 shares of MSFT bought on 2018-11-11 for $1000.00", output);

    assertEquals("Total portfolio cost basis = 1000.00\n"
                    + "Total portfolio value = 1000.00",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    assertEquals("9.14 shares of MSFT for a total investment of $1000.00",
            portfolio.getPortfolioData());
  }

  @Test
  public void TestPortfolioManagerCreatePortfolio() {
    portfolioManager = new StockPortfolioManager();

    assertEquals("1. Default StockPortfolio", portfolioManager.getPortfolios());

    portfolio = portfolioManager.getPortfolio(1);

    assertEquals("", portfolio.getPortfolioData());

    assertEquals("Total portfolio cost basis = 0.00\n"
                    + "Total portfolio value = 0.00",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    String output = this.portfolio.addStock("MSFT", 1000, "2018-11-11");

    assertEquals("9.14 shares of MSFT bought on 2018-11-11 for $1000.00", output);

    assertEquals("Total portfolio cost basis = 1000.00\n"
                    + "Total portfolio value = 1000.00",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    assertEquals("9.14 shares of MSFT for a total investment of $1000.00",
            portfolio.getPortfolioData());

    portfolioManager.createPortfolio("New Portfolio");

    assertEquals("1. Default StockPortfolio\n2. New Portfolio",
            portfolioManager.getPortfolios());

    portfolio = portfolioManager.getPortfolio(2);

    assertEquals("", portfolio.getPortfolioData());

    assertEquals("Total portfolio cost basis = 0.00\n"
                    + "Total portfolio value = 0.00",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    output = this.portfolio.addStock("MSFT", 1000, "2018-11-11");

    assertEquals("9.14 shares of MSFT bought on 2018-11-11 for $1000.00", output);

    assertEquals("Total portfolio cost basis = 1000.00\n"
                    + "Total portfolio value = 1000.00",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    assertEquals("9.14 shares of MSFT for a total investment of $1000.00",
            portfolio.getPortfolioData());

    portfolio = portfolioManager.getPortfolio(1);

    assertEquals("9.14 shares of MSFT for a total investment of $1000.00", portfolio.getPortfolioData());

    assertEquals("Total portfolio cost basis = 1000.00\n"
                    + "Total portfolio value = 1000.00",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    portfolio = portfolioManager.getPortfolio(2);

    assertEquals("Total portfolio cost basis = 1000.00\n"
                    + "Total portfolio value = 1000.00",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    assertEquals("9.14 shares of MSFT for a total investment of $1000.00",
            portfolio.getPortfolioData());

  }

  @Test
  public void testCreatePortfolioInvalidName() {
    portfolioManager = new StockPortfolioManager();
    try {
      portfolioManager.createPortfolio(null);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid Name", ex.getMessage());
    }
    assertEquals("1. Default StockPortfolio", portfolioManager.getPortfolios());

    try {
      portfolioManager.createPortfolio("");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid Name", ex.getMessage());
    }

    try {
      portfolioManager.createPortfolio("   ");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid Name", ex.getMessage());
    }
  }

  @Test
  public void testCreatePortfolioInvalidNameAlreadyExists() {
    portfolioManager = new StockPortfolioManager();
    assertEquals("1. Default StockPortfolio", portfolioManager.getPortfolios());

    try {
      portfolioManager.createPortfolio("Default StockPortfolio");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("StockPortfolio already exists", ex.getMessage());
    }
    assertEquals("1. Default StockPortfolio", portfolioManager.getPortfolios());
  }

  @Test
  public void testGetPortfolioInvalidIndex() {
    portfolioManager = new StockPortfolioManager();

    try {
      portfolioManager.getPortfolio(-1);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid index for the Stock Portfolio", ex.getMessage());
    }

    try {
      portfolioManager.getPortfolio(2);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid index for the Stock Portfolio", ex.getMessage());
    }

    portfolioManager.createPortfolio("Portfolio 1");

    portfolioManager.getPortfolio(1);
    portfolioManager.getPortfolio(2);

    try {
      portfolioManager.getPortfolio(3);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid index for the Stock Portfolio", ex.getMessage());
    }

    try {
      portfolioManager.getPortfolio(-1);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid index for the Stock Portfolio", ex.getMessage());
    }
  }
}