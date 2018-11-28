import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import howtoinvest.model.IPortfolio;
import howtoinvest.model.StockPortfolioManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class contains all the tests for the StockPortfolioManager class.
 */
public class StockPortfolioManagerTest {

  private StockPortfolioManager portfolioManager;
  private IPortfolio portfolio;

  @Test
  public void TestPortfolioManagerCreationAndGetPortfolio() {
    portfolioManager = new StockPortfolioManager();

    List<String> portfolios = portfolioManager.getAll();
    assertEquals(1, portfolios.size());
    assertEquals("Default StockPortfolio", portfolios.get(0));

    /**
     * Check the portfolio retrieved performs some basic operations.
     */
    portfolio = portfolioManager.getByIndex(1);

    assertEquals(0, portfolio.getPortfolioData("2018-11-11").size());

    assertEquals(0, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(0, portfolio.getStockValue("2018-11-11"), 0.01);


    double output = this.portfolio.addStock("MSFT", 1000, "2018-11-11", 0);

    assertEquals(9.14, output, 0.01);

    assertEquals(1000, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(1000, portfolio.getStockValue("2018-11-11"), 0.01);


    HashMap<String, Double> portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(9.14, portfolioData.get("MSFT"), 0.01);
  }

  @Test
  public void TestPortfolioManagerCreatePortfolio() {
    portfolioManager = new StockPortfolioManager();

    List<String> portfolios = portfolioManager.getAll();
    assertEquals(1, portfolios.size());
    assertEquals("Default StockPortfolio", portfolios.get(0));

    portfolio = portfolioManager.getByIndex(1);

    assertEquals(0, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(0, portfolio.getStockValue("2018-11-11"), 0.01);


    this.portfolio.addStock("MSFT", 1000, "2018-11-11", 0);

    assertEquals(1000, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(1000, portfolio.getStockValue("2018-11-11"), 0.01);


    portfolioManager.create("A Portfolio");

    portfolios = portfolioManager.getAll();
    assertEquals(2, portfolios.size());
    assertEquals("Default StockPortfolio", portfolios.get(1));
    assertEquals("A Portfolio", portfolios.get(0));

    portfolio = portfolioManager.getByIndex(1);

    assertEquals(0, portfolio.getPortfolioData("2018-11-11").size());

    assertEquals(0, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(0, portfolio.getStockValue("2018-11-11"), 0.01);


    double output = this.portfolio.addStock("GOOGL", 1000, "2018-11-11", 0);

    assertEquals(95.96, output, 0.01);

    assertEquals(1000, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(1000, portfolio.getStockValue("2018-11-11"), 0.01);


    portfolio = portfolioManager.getByIndex(2);

    assertEquals(95.96, output, 0.01);

    assertEquals(1000, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(1000, portfolio.getStockValue("2018-11-11"), 0.01);


    portfolio = portfolioManager.getByIndex(1);

    assertEquals(95.96, output, 0.01);

    assertEquals(1000, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(1000, portfolio.getStockValue("2018-11-11"), 0.01);


  }

  @Test
  public void testCreatePortfolioInvalidName() {
    portfolioManager = new StockPortfolioManager();
    try {
      portfolioManager.create(null);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid Name", ex.getMessage());
    }
    List<String> portfolios = portfolioManager.getAll();
    assertEquals(1, portfolios.size());
    assertEquals("Default StockPortfolio", portfolios.get(0));

    try {
      portfolioManager.create("");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid Name", ex.getMessage());
    }

    try {
      portfolioManager.create("   ");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid Name", ex.getMessage());
    }

    portfolios = portfolioManager.getAll();
    assertEquals(1, portfolios.size());
    assertEquals("Default StockPortfolio", portfolios.get(0));
  }

  @Test
  public void testCreatePortfolioInvalidNameAlreadyExists() {
    portfolioManager = new StockPortfolioManager();
    List<String> portfolios = portfolioManager.getAll();
    assertEquals(1, portfolios.size());
    assertEquals("Default StockPortfolio", portfolios.get(0));

    try {
      portfolioManager.create("Default StockPortfolio");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("StockPortfolio already exists", ex.getMessage());
    }
    portfolios = portfolioManager.getAll();
    assertEquals(1, portfolios.size());
    assertEquals("Default StockPortfolio", portfolios.get(0));
  }

  @Test
  public void testGetPortfolioInvalidIndex() {
    portfolioManager = new StockPortfolioManager();

    try {
      portfolioManager.getByIndex(-1);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid index for the Stock Portfolio", ex.getMessage());
    }

    try {
      portfolioManager.getByIndex(2);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid index for the Stock Portfolio", ex.getMessage());
    }

    portfolioManager.create("Portfolio 1");

    portfolioManager.getByIndex(1);
    portfolioManager.getByIndex(2);

    try {
      portfolioManager.getByIndex(3);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid index for the Stock Portfolio", ex.getMessage());
    }

    try {
      portfolioManager.getByIndex(-1);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid index for the Stock Portfolio", ex.getMessage());
    }
  }
}