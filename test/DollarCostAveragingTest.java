import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import howtoinvest.model.DollarCostAveraging;
import howtoinvest.model.IPortfolio;
import howtoinvest.model.StockPortfolio;

import static org.junit.Assert.*;

/**
 * This class contains all the tests for the DollarCostAveraging class.
 */
public class DollarCostAveragingTest {

  private DollarCostAveraging dca;

  @Test
  public void testGetStocksAfterCreation() {
    dca = new DollarCostAveraging();
    List<String> output = dca.getStocks();
    assertEquals(1, output.size());
    assertEquals("MSFT", output.get(0));
  }

  @Test
  public void testAddStockToStrategy() {
    dca = new DollarCostAveraging();

    try {
      dca.addStockToStrategy(null);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid stock", ex.getMessage());
    }

    try {
      dca.addStockToStrategy("");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid stock", ex.getMessage());
    }

    try {
      dca.addStockToStrategy(" ");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid stock", ex.getMessage());
    }

    dca.addStockToStrategy("1");

    List<String> output = dca.getStocks();
    assertEquals(2, output.size());
    assertEquals("1", output.get(0));
    assertEquals("MSFT", output.get(1));

    dca.addStockToStrategy("FB");
    output = dca.getStocks();
    assertEquals(3, output.size());
    assertEquals("1", output.get(0));
    assertEquals("FB", output.get(1));
    assertEquals("MSFT", output.get(2));
  }

  @Test
  public void testAddMultipleStocksToStrategy() {
    dca = new DollarCostAveraging();

    List<String> input = new LinkedList<>();
    input.add("MSFT");

    dca.addMultipleStocksToStrategy(input);
    List<String> output = dca.getStocks();
    assertEquals(1, output.size());
    assertEquals("MSFT", output.get(0));

    input.add("FB");

    dca.addMultipleStocksToStrategy(input);
    output = dca.getStocks();
    assertEquals(2, output.size());
    assertEquals("FB", output.get(0));
    assertEquals("MSFT", output.get(1));

    dca = new DollarCostAveraging();

    input = new LinkedList<>();
    input.add("GOOGL");

    dca.addMultipleStocksToStrategy(input);
    output = dca.getStocks();
    assertEquals(2, output.size());
    assertEquals("GOOGL", output.get(0));
    assertEquals("MSFT", output.get(1));

    dca = new DollarCostAveraging();

    input = new LinkedList<>();
    input.add("MSFT");
    input.add("FB");
    input.add("GOOGL");

    dca.addMultipleStocksToStrategy(input);
    output = dca.getStocks();
    assertEquals(3, output.size());
    assertEquals("FB", output.get(0));
    assertEquals("GOOGL", output.get(1));
    assertEquals("MSFT", output.get(2));

    input.add(null);
    try {
      dca.addMultipleStocksToStrategy(input);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid stock", ex.getMessage());
    }

    input = new LinkedList<>();
    input.add("");
    try {
      dca.addMultipleStocksToStrategy(input);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid stock", ex.getMessage());
    }

    input = new LinkedList<>();
    input.add(" ");
    try {
      dca.addMultipleStocksToStrategy(input);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid stock", ex.getMessage());
    }

    input = new LinkedList<>();
    dca = new DollarCostAveraging();
    dca.addMultipleStocksToStrategy(input);
    output = dca.getStocks();
    assertEquals(1, output.size());
    assertEquals("MSFT", output.get(0));

    try {
      dca.addMultipleStocksToStrategy(null);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid stock list", ex.getMessage());
    }
  }

  @Test
  public void testSetWeights() {
    dca = new DollarCostAveraging();
    TreeMap<String, Double> weights = new TreeMap<>();
    weights.put("MSFT", 100.0);
    dca.setWeights(weights);

    weights.put("MSFT", -1.0);
    try {
      dca.setWeights(weights);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid weights", ex.getMessage());
    }

    weights.put("MSFT", 0.0);
    try {
      dca.setWeights(weights);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid weights", ex.getMessage());
    }

    weights.put("MSFT", 99.0);
    try {
      dca.setWeights(weights);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid weights", ex.getMessage());
    }

    /**
     * Adding weight for the stock that is not there in the strategy
     */

    weights.put("MSFT", 50.0);
    weights.put("FB", 50.0);
    try {
      dca.setWeights(weights);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid weights", ex.getMessage());
    }

    weights = new TreeMap<>();
    weights.put("FB", 100.0);
    try {
      dca.setWeights(weights);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid weights", ex.getMessage());
    }

    try {
      dca.setWeights(null);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid weights", ex.getMessage());
    }

    try {
      dca.setWeights(new TreeMap<>());
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid weights", ex.getMessage());
    }

    dca.addStockToStrategy("FB");
    dca.addStockToStrategy("GOOGL");

    weights = new TreeMap<>();
    weights.put("FB", 100.0);
    weights.put("GOOGL", 0.0);
    weights.put("MSFT", 0.0);

    dca.setWeights(weights);

    weights = new TreeMap<>();
    weights.put("FB", 50.0);
    weights.put("GOOGL", 50.0);
    weights.put("MSFT", 0.0);

    dca.setWeights(weights);

    weights = new TreeMap<>();
    weights.put("FB", 30.0);
    weights.put("GOOGL", 50.0);
    weights.put("MSFT", 20.0);

    dca.setWeights(weights);

    weights = new TreeMap<>();
    weights.put("FB", 30.0);
    weights.put("GOOGL", 50.0);
    weights.put("MSFT", 10.0);

    try {
      dca.setWeights(new TreeMap<>());
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid weights", ex.getMessage());
    }
  }

  @Test
  public void testSetAmount() {
    dca = new DollarCostAveraging();

    dca.setAmount(100.45);

    try {
      dca.setAmount(-0.01);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid amount", ex.getMessage());
    }

    try {
      dca.setAmount(0.0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid amount", ex.getMessage());
    }
  }

  @Test
  public void testSetFrequency() {
    dca = new DollarCostAveraging();

    dca.setFrequency(1);

    try {
      dca.setFrequency(-1);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid frequency", ex.getMessage());
    }

    try {
      dca.setFrequency(0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid frequency", ex.getMessage());
    }
  }

  @Test
  public void setTimeRange() {
    dca = new DollarCostAveraging();

    dca.setTimeRange("2018-12-12", "2020-12-12");
    dca.setTimeRange("2016-12-12", "2028-12-12");

    try {
      dca.setTimeRange("2018-12-12", null);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid dates: Start date after end date", ex.getMessage());
    }

    dca.setTimeRange("2018-11-25", null);

    try {
      dca.setTimeRange(null, null);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date", ex.getMessage());
    }

    try {
      dca.setTimeRange("2018-11-11", "");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Date Parse error", ex.getMessage());
    }

    try {
      dca.setTimeRange("", "2018-11-11");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Date Parse error", ex.getMessage());
    }

    try {
      dca.setTimeRange("2018-11-12", "2018-11-11");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid dates: Start date after end date", ex.getMessage());
    }
  }

  @Test
  public void applyStrategy() throws ParseException {
    dca = new DollarCostAveraging();
    IPortfolio portfolio = new StockPortfolio();

    dca.setTimeRange("2018-06-01", "2018-08-01");
    TreeMap<Date, HashMap<String, Double>> output = new TreeMap<>();
    output = dca.applyStrategy(portfolio, 0.0);

    assertEquals(3, output.size());
    Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2018-06-01");
    assertEquals(1, output.get(date).size());
    assertEquals(19.8, output.get(date).get("MSFT"), 0.01);

    date = new SimpleDateFormat("yyyy-MM-dd").parse("2018-07-01");
    assertEquals(1, output.get(date).size());
    assertEquals(19.41, output.get(date).get("MSFT"), 0.01);

    date = new SimpleDateFormat("yyyy-MM-dd").parse("2018-07-31");
    assertEquals(1, output.get(date).size());
    assertEquals(19.41, output.get(date).get("MSFT"), 0.01);

    HashMap<String, Double> portfolioData = portfolio.getPortfolioData("2018-06-01");
    assertEquals(1, portfolioData.size());
    assertEquals(19.8, portfolioData.get("MSFT"), 0.01);

    portfolioData = portfolio.getPortfolioData("2018-07-01");
    assertEquals(1, portfolioData.size());
    assertEquals(39.21, portfolioData.get("MSFT"), 0.01);

    portfolioData = portfolio.getPortfolioData("2018-08-01");
    assertEquals(1, portfolioData.size());
    assertEquals(58.63, portfolioData.get("MSFT"), 0.01);

    portfolioData = portfolio.getPortfolioData("2018-05-30");
    assertEquals(1, portfolioData.size());
    assertEquals(0, portfolioData.get("MSFT"), 0.01);

    assertEquals(2000.00, portfolio.getStockCostBasis("2018-06-01"), 0.01);
    assertEquals(4000.00, portfolio.getStockCostBasis("2018-07-01"), 0.01);
    assertEquals(6000.00, portfolio.getStockCostBasis("2018-08-01"), 0.01);
    assertEquals(0.00, portfolio.getStockCostBasis("2018-05-30"), 0.01);

    dca = new DollarCostAveraging();
    portfolio = new StockPortfolio();

    dca.setTimeRange("2016-10-25", "2016-11-15");
    dca.setFrequency(10);
    dca.addStockToStrategy("GOOGL");
    dca.addStockToStrategy("Invalid");
    dca.setAmount(100.0);
    TreeMap<String, Double> weights = new TreeMap<>();
    weights.put("MSFT", 80.0);
    weights.put("GOOGL", 20.0);
    weights.put("Invalid", 0.0);
    dca.setWeights(weights);

    output = dca.applyStrategy(portfolio, 10);

    /**
     * Checks absence of invalid transactions.
     */
    assertEquals(3, output.size());
    date = new SimpleDateFormat("yyyy-MM-dd").parse("2016-10-25");
    assertEquals(1, output.get(date).size());
    assertEquals(5, output.get(date).get("GOOGL"), 0.01);

    date = new SimpleDateFormat("yyyy-MM-dd").parse("2016-11-04");
    assertEquals(1, output.get(date).size());
    assertEquals(5, output.get(date).get("GOOGL"), 0.01);

    date = new SimpleDateFormat("yyyy-MM-dd").parse("2016-11-14");
    assertEquals(2, output.get(date).size());
    assertEquals(1.6, output.get(date).get("MSFT"), 0.01);
    assertEquals(4, output.get(date).get("GOOGL"), 0.01);

    portfolioData = portfolio.getPortfolioData("2016-10-25");
    assertEquals(2, portfolioData.size());
    assertEquals(0.0, portfolioData.get("MSFT"), 0.01);
    assertEquals(5, portfolioData.get("GOOGL"), 0.01);

    portfolioData = portfolio.getPortfolioData("2016-11-04");
    assertEquals(2, portfolioData.size());
    assertEquals(0.0, portfolioData.get("MSFT"), 0.01);
    assertEquals(10, portfolioData.get("GOOGL"), 0.01);

    portfolioData = portfolio.getPortfolioData("2016-11-14");
    assertEquals(2, portfolioData.size());
    assertEquals(1.6, portfolioData.get("MSFT"), 0.01);
    assertEquals(14, portfolioData.get("GOOGL"), 0.01);

    portfolioData = portfolio.getPortfolioData("2016-10-24");
    assertEquals(2, portfolioData.size());
    assertEquals(0, portfolioData.get("MSFT"), 0.01);
    assertEquals(0, portfolioData.get("GOOGL"), 0.01);

    assertEquals(30, portfolio.getStockCostBasis("2016-10-25"), 0.01);
    assertEquals(60, portfolio.getStockCostBasis("2016-11-04"), 0.01);
    assertEquals(180, portfolio.getStockCostBasis("2016-11-14"), 0.01);
    assertEquals(0.00, portfolio.getStockCostBasis("2016-10-24"), 0.01);

    try {
      dca.applyStrategy(null, 10);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid portfolio", ex.getMessage());
    }
  }
}