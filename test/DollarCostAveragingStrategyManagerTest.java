import com.sun.javafx.tools.packager.PackagerException;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import howtoinvest.model.DollarCostAveraging;
import howtoinvest.model.DollarCostAveragingStrategyManager;
import howtoinvest.model.IPortfolio;
import howtoinvest.model.StockPortfolio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class contains all the tests for the DollarCostAveragingStrategyManager class.
 */
public class DollarCostAveragingStrategyManagerTest {
  private DollarCostAveragingStrategyManager manager;
  private DollarCostAveraging dollarCostAveraging;

  @Test
  public void TestPortfolioManagerCreationAndGetPortfolio() {
    manager = new DollarCostAveragingStrategyManager();

    List<String> dollarCostAveragings = manager.getAll();
    assertEquals(1, dollarCostAveragings.size());
    assertEquals("Default DollarCostAveraging", dollarCostAveragings.get(0));

    /**
     * Check the dollarCostAveraging retrieved performs some basic operations.
     */
    dollarCostAveraging = manager.getByIndex(1);

    assertEquals(1, dollarCostAveraging.getStocks().size());

    this.dollarCostAveraging.addStockToStrategy("GOOGL");

    assertEquals(2, dollarCostAveraging.getStocks().size());
  }

  @Test
  public void TestPortfolioManagerCreatePortfolio() throws ParseException {
    manager = new DollarCostAveragingStrategyManager();

    List<String> dollarCostAveragings = manager.getAll();
    assertEquals(1, dollarCostAveragings.size());
    assertEquals("Default DollarCostAveraging", dollarCostAveragings.get(0));

    dollarCostAveraging = manager.getByIndex(1);

    assertEquals(1, dollarCostAveraging.getStocks().size());

    /**
     * Adding stock thats already in it.
     */
    this.dollarCostAveraging.addStockToStrategy("MSFT");

    assertEquals(1, dollarCostAveraging.getStocks().size());


    manager.create("A Strategy");

    dollarCostAveragings = manager.getAll();
    assertEquals(2, dollarCostAveragings.size());
    assertEquals("Default DollarCostAveraging", dollarCostAveragings.get(1));
    assertEquals("A Strategy", dollarCostAveragings.get(0));

    dollarCostAveraging = manager.getByIndex(1);

    assertEquals(1, dollarCostAveraging.getStocks().size());

    this.dollarCostAveraging.addStockToStrategy("GOOGL");

    assertEquals(2, dollarCostAveraging.getStocks().size());

    dollarCostAveraging = manager.getByIndex(2);

    assertEquals(1, dollarCostAveraging.getStocks().size());

    dollarCostAveraging = manager.getByIndex(1);

    assertEquals(2, dollarCostAveraging.getStocks().size());

    try {
      manager.retrieve("s2");
      fail();
    } catch (IllegalStateException ex) {
      assertEquals("File not found: ./Strategies/s2.json (No such file or directory)",
              ex.getMessage());
    }

    dollarCostAveraging.setTimeRange("2018-10-10", "2018-11-11");
    dollarCostAveraging.saveStrategy("saved strategy");

    try {
      manager.retrieve("");
      fail();
    } catch (IllegalStateException ex) {
      assertEquals("File not found: ./Strategies/.json (No such file or directory)",
              ex.getMessage());
    }

    try {
      manager.retrieve(null);
      fail();
    } catch (IllegalStateException ex) {
      assertEquals("File not found: ./Strategies/null.json (No such file or directory)",
              ex.getMessage());
    }

    /**
     * retrieve the strategy thats already in the system.
     */
    manager.retrieve("s1");
    dollarCostAveragings = manager.getAll();
    assertEquals(3, dollarCostAveragings.size());
    assertEquals("Default DollarCostAveraging", dollarCostAveragings.get(1));
    assertEquals("A Strategy", dollarCostAveragings.get(0));
    assertEquals("s1", dollarCostAveragings.get(2));

    IPortfolio portfolio = new StockPortfolio();

    dollarCostAveraging = manager.getByIndex(3);
    TreeMap<Date, HashMap<String, Double>> output = new TreeMap<>();

    output = dollarCostAveraging.applyStrategy(portfolio, 0.0);

    assertEquals(3, output.size());
    Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2016-10-25");
    assertEquals(1, output.get(date).size());
    assertEquals(5, output.get(date).get("GOOGL"), 0.01);

    date = new SimpleDateFormat("yyyy-MM-dd").parse("2016-11-04");
    assertEquals(1, output.get(date).size());
    assertEquals(5, output.get(date).get("GOOGL"), 0.01);

    date = new SimpleDateFormat("yyyy-MM-dd").parse("2016-11-14");
    assertEquals(2, output.get(date).size());
    assertEquals(1.6, output.get(date).get("MSFT"), 0.01);
    assertEquals(4.0, output.get(date).get("GOOGL"), 0.01);

    HashMap<String, Double> portfolioData = portfolio.getPortfolioData("2018-06-01");
    assertEquals(2, portfolioData.size());
    assertEquals(1.6, portfolioData.get("MSFT"), 0.01);
    assertEquals(14.0, portfolioData.get("GOOGL"), 0.01);

    portfolioData = portfolio.getPortfolioData("2016-11-01");
    assertEquals(2, portfolioData.size());
    assertEquals(0, portfolioData.get("MSFT"), 0.01);
    assertEquals(5.0, portfolioData.get("GOOGL"), 0.01);

    portfolioData = portfolio.getPortfolioData("2016-11-11");
    assertEquals(2, portfolioData.size());
    assertEquals(0, portfolioData.get("MSFT"), 0.01);
    assertEquals(10.0, portfolioData.get("GOOGL"), 0.01);

    portfolioData = portfolio.getPortfolioData("2016-10-03");
    assertEquals(2, portfolioData.size());
    assertEquals(0, portfolioData.get("MSFT"), 0.01);
    assertEquals(0, portfolioData.get("GOOGL"), 0.01);

    assertEquals(140, portfolio.getStockCostBasis("2018-06-01"), 0.01);
    assertEquals(20, portfolio.getStockCostBasis("2016-11-01"), 0.01);
    assertEquals(40, portfolio.getStockCostBasis("2016-11-11"), 0.01);
    assertEquals(0.00, portfolio.getStockCostBasis("2016-10-03"), 0.01);

    assertEquals(301.6, portfolio.getStockValue("2018-06-01"), 0.01);

    /**
     * Retrieve the strategy we saved now.
     */
    manager.retrieve("saved strategy");
    dollarCostAveragings = manager.getAll();
    assertEquals(4, dollarCostAveragings.size());
    assertEquals("Default DollarCostAveraging", dollarCostAveragings.get(1));
    assertEquals("A Strategy", dollarCostAveragings.get(0));
    assertEquals("s1", dollarCostAveragings.get(2));
    assertEquals("saved strategy", dollarCostAveragings.get(3));

    portfolio = new StockPortfolio();

    dollarCostAveraging = manager.getByIndex(4);
    output = new TreeMap<>();

    output = dollarCostAveraging.applyStrategy(portfolio, 0.0);

    assertEquals(2, output.size());
    date = new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-10");
    assertEquals(2, output.get(date).size());
    assertEquals(0, output.get(date).get("GOOGL"), 0.01);
    assertEquals(19.41, output.get(date).get("MSFT"), 0.01);

    date = new SimpleDateFormat("yyyy-MM-dd").parse("2018-11-09");
    assertEquals(2, output.get(date).size());
    assertEquals(0, output.get(date).get("GOOGL"), 0.01);
    assertEquals(18.27, output.get(date).get("MSFT"), 0.01);

    portfolioData = portfolio.getPortfolioData("2018-11-25");
    assertEquals(2, portfolioData.size());
    assertEquals(37.69, portfolioData.get("MSFT"), 0.01);
    assertEquals(0, portfolioData.get("GOOGL"), 0.01);

    portfolioData = portfolio.getPortfolioData("2018-11-01");
    assertEquals(2, portfolioData.size());
    assertEquals(19.41, portfolioData.get("MSFT"), 0.01);
    assertEquals(0.0, portfolioData.get("GOOGL"), 0.01);

    portfolioData = portfolio.getPortfolioData("2018-10-11");
    assertEquals(2, portfolioData.size());
    assertEquals(19.41, portfolioData.get("MSFT"), 0.01);
    assertEquals(0.0, portfolioData.get("GOOGL"), 0.01);

    portfolioData = portfolio.getPortfolioData("2016-10-11");
    assertEquals(2, portfolioData.size());
    assertEquals(0, portfolioData.get("MSFT"), 0.01);
    assertEquals(00.0, portfolioData.get("GOOGL"), 0.01);

    assertEquals(2000, portfolio.getStockCostBasis("2018-11-01"), 0.01);
    assertEquals(4000, portfolio.getStockCostBasis("2018-11-11"), 0.01);

    assertEquals(2067.57, portfolio.getStockValue("2018-11-01"), 0.01);
    assertEquals(4124.66, portfolio.getStockValue("2018-11-11"), 0.01);

    dollarCostAveraging.saveStrategy("saved strategy new");
    portfolio.savePortfolio("p3");

    assertEquals(2, dollarCostAveraging.getStocks().size());

    manager.retrieve("saved strategy new");
    dollarCostAveragings = manager.getAll();
    assertEquals(5, dollarCostAveragings.size());
    assertEquals("Default DollarCostAveraging", dollarCostAveragings.get(1));
    assertEquals("A Strategy", dollarCostAveragings.get(0));
    assertEquals("s1", dollarCostAveragings.get(2));
    assertEquals("saved strategy", dollarCostAveragings.get(3));
    assertEquals("saved strategy new", dollarCostAveragings.get(4));

    dollarCostAveraging = manager.getByIndex(5);
    dollarCostAveraging.addStockToStrategy("FB");
    assertEquals(3, dollarCostAveraging.getStocks().size());
    /**
     * Check retrieving a strategy when the strategy with the same name already exists throws an
     * exception.
     */
    dollarCostAveraging.saveStrategy("saved strategy");

    try {
      manager.retrieve("saved strategy");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Strategy already exists", ex.getMessage());
    }

    dollarCostAveraging = manager.getByIndex(4);
    assertEquals(2, dollarCostAveraging.getStocks().size());

    dollarCostAveraging = manager.getByIndex(5);
    assertEquals(3, dollarCostAveraging.getStocks().size());
  }

  @Test
  public void testCreatePortfolioInvalidName() {
    manager = new DollarCostAveragingStrategyManager();
    try {
      manager.create(null);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid Name", ex.getMessage());
    }
    List<String> dollarCostAveragings = manager.getAll();
    assertEquals(1, dollarCostAveragings.size());
    assertEquals("Default DollarCostAveraging", dollarCostAveragings.get(0));

    try {
      manager.create("");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid Name", ex.getMessage());
    }

    try {
      manager.create("   ");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid Name", ex.getMessage());
    }

    dollarCostAveragings = manager.getAll();
    assertEquals(1, dollarCostAveragings.size());
    assertEquals("Default DollarCostAveraging", dollarCostAveragings.get(0));
  }

  @Test
  public void testCreatePortfolioInvalidNameAlreadyExists() {
    manager = new DollarCostAveragingStrategyManager();
    List<String> dollarCostAveragings = manager.getAll();
    assertEquals(1, dollarCostAveragings.size());
    assertEquals("Default DollarCostAveraging", dollarCostAveragings.get(0));

    try {
      manager.create("Default DollarCostAveraging");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Strategy already exists", ex.getMessage());
    }
    dollarCostAveragings = manager.getAll();
    assertEquals(1, dollarCostAveragings.size());
    assertEquals("Default DollarCostAveraging", dollarCostAveragings.get(0));
  }

  @Test
  public void testGetPortfolioInvalidIndex() {
    manager = new DollarCostAveragingStrategyManager();

    try {
      manager.getByIndex(-1);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid index for the strategy", ex.getMessage());
    }

    try {
      manager.getByIndex(2);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid index for the strategy", ex.getMessage());
    }

    manager.create("Portfolio 1");

    manager.getByIndex(1);
    manager.getByIndex(2);

    try {
      manager.getByIndex(3);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid index for the strategy", ex.getMessage());
    }

    try {
      manager.getByIndex(-1);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid index for the strategy", ex.getMessage());
    }
  }
}