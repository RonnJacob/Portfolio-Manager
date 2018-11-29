import org.junit.Test;

import java.util.List;

import howtoinvest.model.DollarCostAveraging;
import howtoinvest.model.DollarCostAveragingStrategyManager;

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
  public void TestPortfolioManagerCreatePortfolio() {
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