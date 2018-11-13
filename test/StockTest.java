import org.junit.Before;
import org.junit.Test;

import howtoinvest.model.Stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StockTest {

  private Stock stock;
  private Stock microsoft;

  @Before
  public void setUp() throws Exception {
    microsoft = new Stock("MSFT");
  }

  @Test
  public void testStockCreationInvalidEmpty() {
    try {
      stock = new Stock("");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid stock name or ticker symbol", ex.getMessage());
    }
  }

  @Test
  public void testStockCreationInvalidEmptySpaces() {
    try {
      stock = new Stock(" ");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid stock name or ticker symbol", ex.getMessage());
    }
  }

  @Test
  public void testStockCreationInvalidNull() {
    try {
      stock = new Stock(null);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid stock name or ticker symbol", ex.getMessage());
    }
  }

  @Test
  public void testStockCreationInvalidTicker() {
    try {
      stock = new Stock("This ticker does not exit ");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid stock name or ticker symbol", ex.getMessage());
    }
  }

  @Test
  public void testStockCreationValidTicker() {
    stock = new Stock("FB");
    assertEquals("0.00 shares of FB for a total investment of $0.00", stock.getStockData());

    assertEquals(0, stock.getStockCostBasis("2018-11-12"), 0.01);
    assertEquals(0, stock.getStockCostBasis("2016-11-12"), 0.01);

    assertEquals(0, stock.getStockValue("2018-11-12"), 0.01);
  }

  @Test
  public void testInvalidAddShareDate() {
    try {
      String output = microsoft.addShare(1000, "2-1-1");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date. Please enter date again.Share prices do not exist "
              + "for given date.", ex.getMessage());
    }
    assertEquals("0.00 shares of MSFT for a total investment of $0.00", microsoft.getStockData());

    assertEquals(0, microsoft.getStockCostBasis("2018-11-12"), 0.01);

    assertEquals(0, microsoft.getStockValue("2018-11-12"), 0.01);

    try {
      String output = microsoft.addShare(1000, "2000-01-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date. Please enter date again.Share prices do not exist "
              + "for given date.", ex.getMessage());
    }

    try {
      String output = microsoft.addShare(1000, "2020-01-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date. Please enter date again.Cannot fetch share value "
              + "for a future date.", ex.getMessage());
    }
  }

  @Test
  public void testGetStockValueDate() {
    String output = microsoft.addShare(10000, "2018-01-01");
    assertEquals("100.00 shares of MSFT bought on 2018-01-01 for $10000.00", output);

    assertEquals("100.00 shares of MSFT for a total investment of $10000.00", microsoft.getStockData());

    assertEquals(10000.00, microsoft.getStockValue("2018-01-01"), 0.01);

    try {
      assertEquals(0, microsoft.getStockValue("2-1-1"), 0.01);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Share prices do not exist for given date.", ex.getMessage());
    }

    try {
      assertEquals(10000, microsoft.getStockValue("2020-11-11"), 0.01);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Cannot fetch share value for a future date.", ex.getMessage());
    }

    assertEquals(10942, microsoft.getStockValue("2018-11-12"), 0.01);

    try {
      assertEquals(0, microsoft.getStockValue("2000-01-01"), 0.01);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Share prices do not exist for given date.", ex.getMessage());
    }

  }

  @Test
  public void testGetStockValueInvalidDateFormat() {
    String output = microsoft.addShare(10000, "2018-01-01");
    assertEquals("100.00 shares of MSFT bought on 2018-01-01 for $10000.00", output);

    assertEquals("100.00 shares of MSFT for a total investment of $10000.00", microsoft.getStockData());

    assertEquals(10000.00, microsoft.getStockCostBasis("2018-01-01"), 0.01);

    try {
      microsoft.getStockValue("2018-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      microsoft.getStockValue("2018");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      microsoft.getStockValue("");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      microsoft.getStockValue("2018/01/01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      microsoft.getStockValue("01-2018-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      microsoft.getStockValue("01-01-2018");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      microsoft.getStockValue("2018-13-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      microsoft.getStockValue("2018-12-33");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      microsoft.getStockValue("2018-02-30");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }
  }

  @Test
  public void testGetStockBasisDate() {
    String output = microsoft.addShare(10000, "2018-01-01");
    assertEquals("100.00 shares of MSFT bought on 2018-01-01 for $10000.00", output);

    assertEquals("100.00 shares of MSFT for a total investment of $10000.00", microsoft.getStockData());

    assertEquals(10000.00, microsoft.getStockCostBasis("2018-01-01"), 0.01);

    assertEquals(0, microsoft.getStockCostBasis("2-1-1"), 0.01);

    assertEquals(10000, microsoft.getStockCostBasis("2018-11-12"), 0.01);

    assertEquals(10000, microsoft.getStockCostBasis("2020-11-11"), 0.01);

    assertEquals(0, microsoft.getStockCostBasis("2000-01-01"), 0.01);
  }

  @Test
  public void testGetStockBasisInvalidDateFormat() {
    String output = microsoft.addShare(10000, "2018-01-01");
    assertEquals("100.00 shares of MSFT bought on 2018-01-01 for $10000.00", output);

    assertEquals("100.00 shares of MSFT for a total investment of $10000.00", microsoft.getStockData());

    assertEquals(10000.00, microsoft.getStockCostBasis("2018-01-01"), 0.01);

    try {
      microsoft.getStockCostBasis("2018-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      microsoft.getStockCostBasis("2018");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      microsoft.getStockCostBasis("");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      microsoft.getStockCostBasis("2018/01/01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      microsoft.getStockCostBasis("01-2018-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      microsoft.getStockCostBasis("01-01-2018");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      microsoft.getStockCostBasis("2018-13-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      microsoft.getStockCostBasis("2018-12-33");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      microsoft.getStockCostBasis("2018-02-30");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }
  }

  @Test
  public void testInvalidAddShareDateFormat1() {
    try {
      String output = microsoft.addShare(1000, "2018-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      String output = microsoft.addShare(1000, "2018");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      String output = microsoft.addShare(1000, "");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      String output = microsoft.addShare(1000, "2018/01/01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      String output = microsoft.addShare(1000, "01-2018-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      String output = microsoft.addShare(1000, "01-01-2018");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      String output = microsoft.addShare(1000, "2018-13-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      String output = microsoft.addShare(1000, "2018-12-33");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      String output = microsoft.addShare(1000, "2018-02-30");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }
  }

  @Test
  public void TestAddShareInvalidAmount() {
    try {
      String output = microsoft.addShare(0, "2018-01-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid amount", ex.getMessage());
    }

    try {
      String output = microsoft.addShare(-0.0001, "2018-01-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid amount", ex.getMessage());
    }

    try {
      String output = microsoft.addShare(-1000, "2018-01-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid amount", ex.getMessage());
    }
  }

  @Test
  public void TestAddShare() {
    String output = microsoft.addShare(0.0001, "2018-01-01");
    assertEquals("0.00 shares of MSFT bought on 2018-01-01 for $0.00", output);

    assertEquals("0.00 shares of MSFT for a total investment of $0.00", microsoft.getStockData());

    assertEquals(0, microsoft.getStockCostBasis("2018-11-12"), 0.01);
    assertEquals(0, microsoft.getStockCostBasis("2016-11-12"), 0.01);

    assertEquals(0, microsoft.getStockValue("2018-11-12"), 0.01);
    assertEquals(0, microsoft.getStockValue("2016-11-12"), 0.01);

    output = microsoft.addShare(10000, "2018-01-01");
    assertEquals("100.00 shares of MSFT bought on 2018-01-01 for $10000.00", output);

    assertEquals("100.00 shares of MSFT for a total investment of $10000.00", microsoft.getStockData());

    assertEquals(10000, microsoft.getStockCostBasis("2018-01-01"), 0.01);
    assertEquals(10000, microsoft.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(0, microsoft.getStockCostBasis("2016-11-12"), 0.01);
    assertEquals(10000, microsoft.getStockCostBasis("2019-01-01"), 0.01);

    assertEquals(10942.0, microsoft.getStockValue("2018-11-13"), 0.01);
    assertEquals(0, microsoft.getStockValue("2016-11-12"), 0.01);
    try {
      assertEquals(10000, microsoft.getStockValue("2019-01-01"), 0.01);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Cannot fetch share value for a future date.", ex.getMessage());
    }

    output = microsoft.addShare(10000, "2016-12-01");
    assertEquals("200.00 shares of MSFT bought on 2016-12-01 for $10000.00", output);

    assertEquals("300.00 shares of MSFT for a total investment of $20000.00", microsoft.getStockData());

    assertEquals(20000, microsoft.getStockCostBasis("2018-01-01"), 0.01);
    assertEquals(20000, microsoft.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(10000, microsoft.getStockCostBasis("2016-12-01"), 0.01);
    assertEquals(0, microsoft.getStockCostBasis("2015-12-01"), 0.01);
    assertEquals(10000, microsoft.getStockCostBasis("2017-12-31"), 0.01);

    assertEquals(32826.0, microsoft.getStockValue("2018-11-13"), 0.01);
    assertEquals(0, microsoft.getStockValue("2016-11-12"), 0.01);
    assertEquals(10000, microsoft.getStockValue("2016-12-01"), 0.01);
    assertEquals(15024.0, microsoft.getStockValue("2017-12-31"), 0.01);
    try {
      assertEquals(10000, microsoft.getStockValue("2015-01-01"), 0.01);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Share prices do not exist for given date.", ex.getMessage());
    }

    output = microsoft.addShare(23.45, "2018-11-11");
    assertEquals("0.21 shares of MSFT bought on 2018-11-11 for $23.45", output);

    assertEquals("300.21 shares of MSFT for a total investment of $20023.45", microsoft.getStockData());

    assertEquals(20000, microsoft.getStockCostBasis("2018-01-01"), 0.01);
    assertEquals(20023.45, microsoft.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(10000, microsoft.getStockCostBasis("2016-12-01"), 0.01);
    assertEquals(0, microsoft.getStockCostBasis("2015-12-01"), 0.01);
    assertEquals(10000, microsoft.getStockCostBasis("2017-12-31"), 0.01);

    assertEquals(32849.45, microsoft.getStockValue("2018-11-13"), 0.01);
    assertEquals(32826.00, microsoft.getStockValue("2018-11-10"), 0.01);
    assertEquals(0, microsoft.getStockValue("2016-11-12"), 0.01);
    assertEquals(10000, microsoft.getStockValue("2016-12-01"), 0.01);
    assertEquals(15024.0, microsoft.getStockValue("2017-12-31"), 0.01);
    try {
      assertEquals(10000, microsoft.getStockValue("2015-01-01"), 0.01);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Share prices do not exist for given date.", ex.getMessage());
    }

    /**
     * Add with same date.
     */
    output = microsoft.addShare(213.45, "2018-11-11");
    assertEquals("1.95 shares of MSFT bought on 2018-11-11 for $213.45", output);

    assertEquals("302.17 shares of MSFT for a total investment of $20236.90", microsoft.getStockData());

    assertEquals(20000, microsoft.getStockCostBasis("2018-01-01"), 0.01);
    assertEquals(20236.9001, microsoft.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(10000, microsoft.getStockCostBasis("2016-12-01"), 0.01);
    assertEquals(0, microsoft.getStockCostBasis("2015-12-01"), 0.01);
    assertEquals(10000, microsoft.getStockCostBasis("2017-12-31"), 0.01);

    assertEquals(33062.90010942, microsoft.getStockValue("2018-11-13"), 0.01);
    assertEquals(32826.00, microsoft.getStockValue("2018-11-10"), 0.01);
    assertEquals(0, microsoft.getStockValue("2016-11-12"), 0.01);
    assertEquals(10000, microsoft.getStockValue("2016-12-01"), 0.01);
    assertEquals(15024.0, microsoft.getStockValue("2017-12-31"), 0.01);
    try {
      assertEquals(10000, microsoft.getStockValue("2015-01-01"), 0.01);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Share prices do not exist for given date.", ex.getMessage());
    }
    try {
      assertEquals(10000, microsoft.getStockValue("2020-01-01"), 0.01);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Cannot fetch share value for a future date.", ex.getMessage());
    }

    output = microsoft.addShare(5000, "2016-12-01");
    assertEquals("100.00 shares of MSFT bought on 2016-12-01 for $5000.00", output);

    assertEquals("402.17 shares of MSFT for a total investment of $25236.90", microsoft.getStockData());

    assertEquals(25000, microsoft.getStockCostBasis("2018-01-01"), 0.01);
    assertEquals(25236.9001, microsoft.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(15000, microsoft.getStockCostBasis("2016-12-01"), 0.01);
    assertEquals(0, microsoft.getStockCostBasis("2015-12-01"), 0.01);
    assertEquals(15000, microsoft.getStockCostBasis("2017-12-31"), 0.01);

    assertEquals(44004.90010942, microsoft.getStockValue("2018-11-13"), 0.01);
    assertEquals(43768.00, microsoft.getStockValue("2018-11-10"), 0.01);
    assertEquals(0, microsoft.getStockValue("2016-11-12"), 0.01);
    assertEquals(15000, microsoft.getStockValue("2016-12-01"), 0.01);
    assertEquals(22536.0, microsoft.getStockValue("2017-12-31"), 0.01);
  }
}