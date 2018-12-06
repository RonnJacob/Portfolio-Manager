import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

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


    double output = this.portfolio.addStock("MSFT", 1000, "2018-11-11",
            0);

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


    double output = this.portfolio.addStock("GOOGL", 1000, "2018-11-11",
            0);

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

    try {
      portfolioManager.retrieve("p1");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("File not found: ./Stock Portfolios/p1.json (No such file or directory)"
              ,
              ex.getMessage());
    }

    portfolio.savePortfolio("saved portfolio");

    try {
      portfolioManager.retrieve("");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("File not found: ./Stock Portfolios/.json (No such file or directory)",
              ex.getMessage());
    }

    try {
      portfolioManager.retrieve(null);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("File not found: ./Stock Portfolios/null.json (No such file or "
                      + "directory)", ex.getMessage());
    }

    /**
     * retrieve the portfolio thats already in the system.
     */
    portfolioManager.retrieve("p2");
    portfolios = portfolioManager.getAll();
    assertEquals(3, portfolios.size());
    assertEquals("Default StockPortfolio", portfolios.get(1));
    assertEquals("A Portfolio", portfolios.get(0));
    assertEquals("p2", portfolios.get(2));

    portfolio = portfolioManager.getByIndex(3);

    HashMap<String, Double> portfolioData;
    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(3, portfolioData.size());
    assertEquals(203.15, portfolioData.get("GOOGL"), 0.01);
    assertEquals(14.66, portfolioData.get("MSFT"), 0.01);
    assertEquals(13.57, portfolioData.get("FB"), 0.01);

    assertEquals(4166.66, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(5206.67, portfolio.getStockValue("2018-11-11"), 0.01);

    portfolio.addStock("GOOGL", 3000, "2018-11-11", 0.0);
    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(3, portfolioData.size());
    assertEquals(491.066, portfolioData.get("GOOGL"), 0.01);
    assertEquals(14.66, portfolioData.get("MSFT"), 0.01);
    assertEquals(13.57, portfolioData.get("FB"), 0.01);

    assertEquals(7166.66, portfolio.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(8206.67, portfolio.getStockValue("2018-11-11"), 0.01);

    portfolioData = portfolio.getPortfolioData("2018-08-11");
    assertEquals(3, portfolioData.size());
    assertEquals(145.83, portfolioData.get("GOOGL"), 0.01);
    assertEquals(0, portfolioData.get("MSFT"), 0.01);
    assertEquals(5.83, portfolioData.get("FB"), 0.01);

    assertEquals(1166.66, portfolio.getStockCostBasis("2018-08-11"), 0.01);
    assertEquals(2070.83, portfolio.getStockValue("2018-08-11"), 0.01);

    /**
     * Retrieve the portfolio we saved now.
     */
    portfolioManager.retrieve("saved portfolio");
    portfolios = portfolioManager.getAll();
    assertEquals(4, portfolios.size());
    assertEquals("Default StockPortfolio", portfolios.get(1));
    assertEquals("A Portfolio", portfolios.get(0));
    assertEquals("p2", portfolios.get(2));
    assertEquals("saved portfolio", portfolios.get(3));

    portfolio = portfolioManager.getByIndex(4);

    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(1, portfolioData.size());
    assertEquals(95.96, portfolioData.get("GOOGL"), 0.01);

    portfolio.addStock("MSFT", 2000, "2018-11-11", 100);
    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(2, portfolioData.size());
    assertEquals(95.96, portfolioData.get("GOOGL"), 0.01);
    assertEquals(18.27, portfolioData.get("MSFT"), 0.01);

    portfolio.savePortfolio("saved portfolio new");

    portfolioManager.retrieve("saved portfolio new");
    portfolios = portfolioManager.getAll();
    assertEquals(5, portfolios.size());
    assertEquals("Default StockPortfolio", portfolios.get(1));
    assertEquals("A Portfolio", portfolios.get(0));
    assertEquals("p2", portfolios.get(2));
    assertEquals("saved portfolio", portfolios.get(3));
    assertEquals("saved portfolio new", portfolios.get(4));

    portfolio = portfolioManager.getByIndex(5);

    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(2, portfolioData.size());
    assertEquals(95.96, portfolioData.get("GOOGL"), 0.01);
    assertEquals(18.27, portfolioData.get("MSFT"), 0.01);

    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(2, portfolioData.size());
    assertEquals(95.96, portfolioData.get("GOOGL"), 0.01);
    assertEquals(18.27, portfolioData.get("MSFT"), 0.01);

    TreeMap<String, Double> weights = new TreeMap<>();

    weights.put("GOOGL", 25.0);
    weights.put("MSFT", 75.0);

    HashMap<String, Double> outputInvest = portfolio.invest(1000, weights,
            false, "2018-11-11", 0);
    assertEquals(2, outputInvest.size());
    assertEquals(6.85, outputInvest.get("MSFT"), 0.01);
    assertEquals(23.99, outputInvest.get("GOOGL"), 0.01);

    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(2, portfolioData.size());
    assertEquals(119.96, portfolioData.get("GOOGL"), 0.01);
    assertEquals(25.13, portfolioData.get("MSFT"), 0.01);

    /**
     * Check retrieving a portfolio when the portfolio with the same name already exists throws an
     * exception.
     */
    portfolio.savePortfolio("saved portfolio");

    try {
      portfolioManager.retrieve("saved portfolio");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("StockPortfolio already exists", ex.getMessage());
    }

    portfolio = portfolioManager.getByIndex(4);

    portfolioData = portfolio.getPortfolioData("2018-11-11");
    assertEquals(2, portfolioData.size());
    assertEquals(95.96, portfolioData.get("GOOGL"), 0.01);
    assertEquals(18.27, portfolioData.get("MSFT"), 0.01);
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