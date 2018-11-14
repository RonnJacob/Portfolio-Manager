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
    } catch (IllegalArgumentException ex) {
      assertEquals("Share prices do not exist for given date.", ex.getMessage());
    }

    try {
      assertEquals("Total portfolio cost basis = 1000.00\n"
                      + "Total portfolio value = 1000.00",
              portfolio.getStockCostBasisAndStockValue(null));
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      assertEquals("Total portfolio cost basis = 1000.00\n"
                      + "Total portfolio value = 1000.00",
              portfolio.getStockCostBasisAndStockValue("2000-10-10"));
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Share prices do not exist for given date.", ex.getMessage());
    }

    try {
      assertEquals("Total portfolio cost basis = 1000.00\n"
                      + "Total portfolio value = 1000.00",
              portfolio.getStockCostBasisAndStockValue("2020-10-10"));
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Cannot fetch share value for a future date.", ex.getMessage());
    }
  }

  @Test
  public void testAddStockInvalidDateFormat() {
    portfolio = new StockPortfolio();

    String output = portfolio.addStock("MSFT", 10000, "2018-01-01");
    assertEquals("100.00 shares of MSFT bought on 2018-01-01 for $10000.00", output);

    assertEquals("100.00 shares of MSFT for a total investment of $10000.00", portfolio.getPortfolioData());

    assertEquals("Total portfolio cost basis = 10000.00\n"
                    + "Total portfolio value = 10000.00",
            portfolio.getStockCostBasisAndStockValue("2018-01-01"));

    try {
      portfolio.addStock("FB", 1000, "2018-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    assertEquals("100.00 shares of MSFT for a total investment of $10000.00", portfolio.getPortfolioData());

    assertEquals("Total portfolio cost basis = 10000.00\n"
                    + "Total portfolio value = 10000.00",
            portfolio.getStockCostBasisAndStockValue("2018-01-01"));

    assertEquals("Total portfolio cost basis = 10000.00\n"
                    + "Total portfolio value = 10942.00",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    assertEquals("100.00 shares of MSFT for a total investment of $10000.00",
            portfolio.getPortfolioData());

    try {
      portfolio.addStock("FB", 1000, "2018");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.addStock("FB", 1000, "");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.addStock("FB", 1000, "2018/01/01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.addStock("FB", 1000, "01-2018-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.addStock("FB", 1000, "01-01-2018");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.addStock("FB", 1000, "2018-13-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.addStock("FB", 1000, "2018-12-33");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.addStock("FB", 1000, "2018-02-30");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }
  }

  @Test
  public void TestAddStockInvalidAmount() {
    portfolio = new StockPortfolio();
    try {
      String output = portfolio.addStock("MSFT", 0, "2018-01-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid amount", ex.getMessage());
    }

    String expected = "Total portfolio cost basis = 0.00\n"
            + "Total portfolio value = 0.00";
    assertEquals("", portfolio.getPortfolioData());

    assertEquals(expected, portfolio.getStockCostBasisAndStockValue("2018-11-12"));

    try {
      String output = portfolio.addStock("FB", -0.0001, "2018-01-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid amount", ex.getMessage());
    }

    assertEquals("", portfolio.getPortfolioData());

    assertEquals(expected, portfolio.getStockCostBasisAndStockValue("2018-11-12"));

    try {
      String output = portfolio.addStock("FB", -1000, "2018-01-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid amount", ex.getMessage());
    }

    assertEquals("", portfolio.getPortfolioData());

    assertEquals(expected, portfolio.getStockCostBasisAndStockValue("2018-11-12"));

    String output = this.portfolio.addStock("FB", 1000, "2018-11-11");

    assertEquals("9.14 shares of FB bought on 2018-11-11 for $1000.00", output);

    assertEquals("Total portfolio cost basis = 1000.00\n"
                    + "Total portfolio value = 1000.00",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    assertEquals("9.14 shares of FB for a total investment of $1000.00", portfolio.getPortfolioData());


    try {
      output = portfolio.addStock("FB", -1000, "2018-01-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid amount", ex.getMessage());
    }
    assertEquals("Total portfolio cost basis = 1000.00\n"
                    + "Total portfolio value = 1000.00",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    assertEquals("9.14 shares of FB for a total investment of $1000.00", portfolio.getPortfolioData());
  }

  @Test
  public void TestAddStockInvalidDate() {
    portfolio = new StockPortfolio();
    try {
      portfolio.addStock("FB", 1000, "2-1-1");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date. Please enter date again.Share prices do not exist for given date.",
              ex.getMessage());
    }
    String output = this.portfolio.addStock("MSFT", 1000, "2018-11-11");

    assertEquals("9.14 shares of MSFT bought on 2018-11-11 for $1000.00", output);

    assertEquals("Total portfolio cost basis = 1000.00\n"
                    + "Total portfolio value = 1000.00",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    assertEquals("9.14 shares of MSFT for a total investment of $1000.00",
            portfolio.getPortfolioData());

    try {
      portfolio.addStock("FB", 1500, null);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.",
              ex.getMessage());
    }

    try {
      portfolio.addStock("FB", 1500, "2000-10-10");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date. Please enter date again.Share prices do not exist for given date.",
              ex.getMessage());
    }

    assertEquals("Total portfolio cost basis = 1000.00\n"
                    + "Total portfolio value = 1000.00",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    assertEquals("9.14 shares of MSFT for a total investment of $1000.00",
            portfolio.getPortfolioData());

    try {
      portfolio.addStock("MSFT", 2000, "2020-10-10");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date. Please enter date again.Cannot fetch share value for a future date.",
              ex.getMessage());
    }

    assertEquals("Total portfolio cost basis = 1000.00\n"
                    + "Total portfolio value = 1000.00",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    assertEquals("9.14 shares of MSFT for a total investment of $1000.00",
            portfolio.getPortfolioData());
  }

  @Test
  public void testGetStockBasisAndStockValueDateFormat() {
    portfolio = new StockPortfolio();
    String output = portfolio.addStock("MSFT", 10000, "2018-01-01");
    assertEquals("100.00 shares of MSFT bought on 2018-01-01 for $10000.00", output);

    assertEquals("100.00 shares of MSFT for a total investment of $10000.00", portfolio.getPortfolioData());

    assertEquals("Total portfolio cost basis = 10000.00\n"
                    + "Total portfolio value = 10000.00",
            portfolio.getStockCostBasisAndStockValue("2018-01-01"));

    try {
      portfolio.getStockCostBasisAndStockValue("2018-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockCostBasisAndStockValue("2018");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockCostBasisAndStockValue("");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockCostBasisAndStockValue("2018/01/01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockCostBasisAndStockValue("01-2018-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockCostBasisAndStockValue("01-01-2018");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockCostBasisAndStockValue("2018-13-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockCostBasisAndStockValue("2018-12-33");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      portfolio.getStockCostBasisAndStockValue("2018-02-30");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }
  }

  @Test
  public void TestAddStockInvalidTickerSymbol() {
    portfolio = new StockPortfolio();
    try {
      portfolio.addStock("", 1000, "2018-01-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid stock name or ticker symbol", ex.getMessage());
    }

    assertEquals("Total portfolio cost basis = 0.00\n"
                    + "Total portfolio value = 0.00",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    assertEquals("", portfolio.getPortfolioData());

    try {
      portfolio.addStock(" ", 1000, "2018-01-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid stock name or ticker symbol", ex.getMessage());
    }

    try {
      portfolio.addStock(null, 1000, "2018-01-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid stock name or ticker symbol", ex.getMessage());
    }

    try {
      portfolio.addStock("This ticker does not exit ", 1000, "2018-01-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid stock name or ticker symbol", ex.getMessage());
    }
  }

  @Test
  public void TestAddStock() {
    portfolio = new StockPortfolio();

    assertEquals("", portfolio.getPortfolioData());

    assertEquals("Total portfolio cost basis = 0.00\n"
                    + "Total portfolio value = 0.00",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    assertEquals("Total portfolio cost basis = 0.00\n"
                    + "Total portfolio value = 0.00",
            portfolio.getStockCostBasisAndStockValue("2016-11-11"));

    assertEquals("Total portfolio cost basis = 0.00\n"
                    + "Total portfolio value = 0.00",
            portfolio.getStockCostBasisAndStockValue("2020-11-11"));

    String output = portfolio.addStock("MSFT", 10000, "2018-01-01");

    assertEquals("100.00 shares of MSFT bought on 2018-01-01 for $10000.00", output);

    assertEquals("100.00 shares of MSFT for a total investment of $10000.00",
            portfolio.getPortfolioData());

    assertEquals("Total portfolio cost basis = 10000.00\n"
                    + "Total portfolio value = 10942.00",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    try {
      assertEquals("Total portfolio cost basis = 0.00\n"
                      + "Total portfolio value = 0.00",
              portfolio.getStockCostBasisAndStockValue("2016-11-11"));
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Share prices do not exist for given date.", ex.getMessage());
    }

    try {
      assertEquals("Total portfolio cost basis = 0.00\n"
                      + "Total portfolio value = 0.00",
              portfolio.getStockCostBasisAndStockValue("2020-11-11"));
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Cannot fetch share value for a future date.", ex.getMessage());
    }

    output = portfolio.addStock("MSFT", 10000, "2016-12-01");

    assertEquals("200.00 shares of MSFT bought on 2016-12-01 for $10000.00", output);

    assertEquals("300.00 shares of MSFT for a total investment of $20000.00",
            portfolio.getPortfolioData());

    assertEquals("Total portfolio cost basis = 20000.00\n"
                    + "Total portfolio value = 32826.00",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    assertEquals("Total portfolio cost basis = 10000.00\n"
                    + "Total portfolio value = 15024.00",
            portfolio.getStockCostBasisAndStockValue("2017-12-01"));

    assertEquals("Total portfolio cost basis = 10000.00\n"
                    + "Total portfolio value = 10000.00",
            portfolio.getStockCostBasisAndStockValue("2016-12-01"));

    try {
      assertEquals("Total portfolio cost basis = 0.00\n"
                      + "Total portfolio value = 0.00",
              portfolio.getStockCostBasisAndStockValue("2016-11-11"));
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Share prices do not exist for given date.", ex.getMessage());
    }

    output = portfolio.addStock("MSFT", 23.45, "2018-11-11");

    assertEquals("0.21 shares of MSFT bought on 2018-11-11 for $23.45", output);

    assertEquals("300.21 shares of MSFT for a total investment of $20023.45",
            portfolio.getPortfolioData());

    assertEquals("Total portfolio cost basis = 20023.45\n"
                    + "Total portfolio value = 32849.45",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    assertEquals("Total portfolio cost basis = 10000.00\n"
                    + "Total portfolio value = 15024.00",
            portfolio.getStockCostBasisAndStockValue("2017-12-01"));

    assertEquals("Total portfolio cost basis = 10000.00\n"
                    + "Total portfolio value = 10000.00",
            portfolio.getStockCostBasisAndStockValue("2016-12-01"));

    try {
      assertEquals("Total portfolio cost basis = 0.00\n"
                      + "Total portfolio value = 0.00",
              portfolio.getStockCostBasisAndStockValue("2016-11-11"));
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Share prices do not exist for given date.", ex.getMessage());
    }

    /**
     * Add with same date.
     */

    output = portfolio.addStock("MSFT", 213.45, "2018-11-11");

    assertEquals("1.95 shares of MSFT bought on 2018-11-11 for $213.45", output);

    assertEquals("302.17 shares of MSFT for a total investment of $20236.90",
            portfolio.getPortfolioData());

    assertEquals("Total portfolio cost basis = 20236.90\n"
                    + "Total portfolio value = 33062.90",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    assertEquals("Total portfolio cost basis = 10000.00\n"
                    + "Total portfolio value = 15024.00",
            portfolio.getStockCostBasisAndStockValue("2017-12-01"));

    assertEquals("Total portfolio cost basis = 10000.00\n"
                    + "Total portfolio value = 10000.00",
            portfolio.getStockCostBasisAndStockValue("2016-12-01"));

    output = portfolio.addStock("MSFT", 5000, "2016-12-01");

    assertEquals("100.00 shares of MSFT bought on 2016-12-01 for $5000.00", output);

    assertEquals("402.17 shares of MSFT for a total investment of $25236.90",
            portfolio.getPortfolioData());

    assertEquals("Total portfolio cost basis = 25236.90\n"
                    + "Total portfolio value = 44004.90",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    assertEquals("Total portfolio cost basis = 15000.00\n"
                    + "Total portfolio value = 22536.00",
            portfolio.getStockCostBasisAndStockValue("2017-12-01"));

    assertEquals("Total portfolio cost basis = 15000.00\n"
                    + "Total portfolio value = 15000.00",
            portfolio.getStockCostBasisAndStockValue("2016-12-01"));

    assertEquals("Total portfolio cost basis = 25000.00\n"
                    + "Total portfolio value = 40000.00",
            portfolio.getStockCostBasisAndStockValue("2018-01-01"));

    /**
     * Add another Stock
     */
    output = portfolio.addStock("FB", 10000, "2018-01-01");

    assertEquals("95.24 shares of FB bought on 2018-01-01 for $10000.00", output);

    assertEquals("402.17 shares of MSFT for a total investment of $25236.90\n"
                    + "95.24 shares of FB for a total investment of $10000.00",
            portfolio.getPortfolioData());

    assertEquals("Total portfolio cost basis = 35236.90\n"
                    + "Total portfolio value = 54425.85",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    assertEquals("Total portfolio cost basis = 15000.00\n"
                    + "Total portfolio value = 22536.00",
            portfolio.getStockCostBasisAndStockValue("2017-12-01"));

    assertEquals("Total portfolio cost basis = 15000.00\n"
                    + "Total portfolio value = 15000.00",
            portfolio.getStockCostBasisAndStockValue("2016-12-01"));

    assertEquals("Total portfolio cost basis = 35000.00\n"
                    + "Total portfolio value = 50000.00",
            portfolio.getStockCostBasisAndStockValue("2018-01-01"));

    /**
     * Add another Stock
     */
    output = portfolio.addStock("GOOGL", 10000, "2017-01-01");

    assertEquals("1404.49 shares of GOOGL bought on 2017-01-01 for $10000.00", output);

    assertEquals("402.17 shares of MSFT for a total investment of $25236.90\n"
                    + "1404.49 shares of GOOGL for a total investment of $10000.00\n"
                    + "95.24 shares of FB for a total investment of $10000.00",
            portfolio.getPortfolioData());

    assertEquals("Total portfolio cost basis = 45236.90\n"
                    + "Total portfolio value = 69060.68",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    assertEquals("Total portfolio cost basis = 25000.00\n"
                    + "Total portfolio value = 32536.00",
            portfolio.getStockCostBasisAndStockValue("2017-12-01"));

    assertEquals("Total portfolio cost basis = 15000.00\n"
                    + "Total portfolio value = 15000.00",
            portfolio.getStockCostBasisAndStockValue("2016-12-01"));

    assertEquals("Total portfolio cost basis = 45000.00\n"
                    + "Total portfolio value = 64044.94",
            portfolio.getStockCostBasisAndStockValue("2018-01-01"));


    output = portfolio.addStock("FB", 100, "2016-12-12");

    assertEquals("0.95 shares of FB bought on 2016-12-12 for $100.00", output);

    assertEquals("402.17 shares of MSFT for a total investment of $25236.90\n"
                    + "1404.49 shares of GOOGL for a total investment of $10000.00\n"
                    + "96.19 shares of FB for a total investment of $10100.00",
            portfolio.getPortfolioData());

    assertEquals("Total portfolio cost basis = 45336.90\n"
                    + "Total portfolio value = 69164.89",
            portfolio.getStockCostBasisAndStockValue("2018-11-11"));

    assertEquals("Total portfolio cost basis = 25100.00\n"
                    + "Total portfolio value = 32636.00",
            portfolio.getStockCostBasisAndStockValue("2017-12-01"));

    assertEquals("Total portfolio cost basis = 15000.00\n"
                    + "Total portfolio value = 15000.00",
            portfolio.getStockCostBasisAndStockValue("2016-12-01"));

    assertEquals("Total portfolio cost basis = 45100.00\n"
                    + "Total portfolio value = 64144.94",
            portfolio.getStockCostBasisAndStockValue("2018-01-01"));
  }
}