import org.junit.Before;
import org.junit.Test;

import howtoinvest.model.Stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class contains all the test for the Stock class.
 */
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

    assertEquals(0, stock.getStockCostBasis("2018-11-12"), 0.01);
    assertEquals(0, stock.getStockCostBasis("2016-11-12"), 0.01);

    assertEquals(0, stock.getStockValue("2018-11-12"), 0.01);
  }

  @Test
  public void testInvalidAddShareDate() {
    try {
      double output = microsoft.addShare(1000, null, 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      double output = microsoft.addShare(1000, "2-1-1", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date. Please enter date again.Share prices do not exist "
              + "for given date.", ex.getMessage());
    }

    assertEquals(0, microsoft.getStockCostBasis("2018-11-12"), 0.01);

    assertEquals(0, microsoft.getStockValue("2018-11-12"), 0.01);

    try {
      double output = microsoft.addShare(1000, "2000-01-01", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date. Please enter date again.Share prices do not exist "
              + "for given date.", ex.getMessage());
    }


    assertEquals(0, microsoft.getStockCostBasis("2018-11-12"), 0.01);

    assertEquals(0, microsoft.getStockValue("2018-11-12"), 0.01);

    try {
      double output = microsoft.addShare(1000, "2020-01-01", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date. Please enter date again.Cannot fetch share value "
              + "for a future date.", ex.getMessage());
    }

    assertEquals(0, microsoft.getStockCostBasis("2018-11-12"), 0.01);

    assertEquals(0, microsoft.getStockValue("2018-11-12"), 0.01);

  }

  @Test
  public void testGetStockValueDate() {
    double output = microsoft.addShare(10000, "2018-01-01", 0);
    assertEquals(100, output, 0.01);

    assertEquals(10000.00, microsoft.getStockValue("2018-01-01"), 0.01);

    try {
      assertEquals(0, microsoft.getStockValue(null), 0.01);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    try {
      assertEquals(0, microsoft.getStockValue("2-1-1"), 0.01);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Cannot fetch current share price:"
              + "Share prices do not exist for given date.", ex.getMessage());
    }

    try {
      assertEquals(10000, microsoft.getStockValue("2020-11-11"), 0.01);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Cannot fetch current share price:"
              + "Cannot fetch share value for a future date.", ex.getMessage());
    }

    assertEquals(10942, microsoft.getStockValue("2018-11-12"), 0.01);

    try {
      assertEquals(0, microsoft.getStockValue("2000-01-01"), 0.01);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Cannot fetch current share price:"
              + "Share prices do not exist for given date.", ex.getMessage());
    }
  }

  @Test
  public void testGetStockValueInvalidDateFormat() {
    double output = microsoft.addShare(10000, "2018-01-01", 0);
    assertEquals(100, output, 0.01);

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
    double output = microsoft.addShare(10000, "2018-01-01", 0);
    assertEquals(100, output, 0.01);

    try {
      assertEquals(10000.00, microsoft.getStockCostBasis(null), 0.01);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }
    assertEquals(10000.00, microsoft.getStockCostBasis("2018-01-01"), 0.01);

    assertEquals(0, microsoft.getStockCostBasis("2-1-1"), 0.01);

    assertEquals(10000, microsoft.getStockCostBasis("2018-11-12"), 0.01);

    assertEquals(10000, microsoft.getStockCostBasis("2020-11-11"), 0.01);

    assertEquals(0, microsoft.getStockCostBasis("2000-01-01"), 0.01);
  }

  @Test
  public void testGetStockBasisInvalidDateFormat() {
    try {
      microsoft.getStockCostBasis("2018-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    double output = microsoft.addShare(10000, "2018-01-01", 0);
    assertEquals(100, output, 0.01);

    assertEquals(10000.00, microsoft.getStockCostBasis("2018-01-01"), 0.01);


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
  public void testInvalidAddShareDateFormat() {
    try {
      double output = microsoft.addShare(1000, "2018-01", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    assertEquals(0, microsoft.getStockCostBasis("2018-11-12"), 0.01);

    assertEquals(0, microsoft.getStockValue("2018-11-12"), 0.01);

    try {
      double output = microsoft.addShare(1000, "2018", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    assertEquals(0, microsoft.getStockCostBasis("2018-11-12"), 0.01);

    assertEquals(0, microsoft.getStockValue("2018-11-12"), 0.01);

    try {
      double output = microsoft.addShare(1000, "", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    assertEquals(0, microsoft.getStockCostBasis("2018-11-12"), 0.01);

    assertEquals(0, microsoft.getStockValue("2018-11-12"), 0.01);


    try {
      double output = microsoft.addShare(1000, "2018/01/01", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    assertEquals(0, microsoft.getStockCostBasis("2018-11-12"), 0.01);

    assertEquals(0, microsoft.getStockValue("2018-11-12"), 0.01);


    try {
      double output = microsoft.addShare(1000, "01-2018-01", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    assertEquals(0, microsoft.getStockCostBasis("2018-11-12"), 0.01);

    assertEquals(0, microsoft.getStockValue("2018-11-12"), 0.01);


    try {
      double output = microsoft.addShare(1000, "01-01-2018", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    assertEquals(0, microsoft.getStockCostBasis("2018-11-12"), 0.01);

    assertEquals(0, microsoft.getStockValue("2018-11-12"), 0.01);


    try {
      double output = microsoft.addShare(1000, "2018-13-01", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    assertEquals(0, microsoft.getStockCostBasis("2018-11-12"), 0.01);

    assertEquals(0, microsoft.getStockValue("2018-11-12"), 0.01);


    try {
      double output = microsoft.addShare(1000, "2018-12-33", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    assertEquals(0, microsoft.getStockCostBasis("2018-11-12"), 0.01);

    assertEquals(0, microsoft.getStockValue("2018-11-12"), 0.01);


    try {
      double output = microsoft.addShare(1000, "2018-02-30", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }

    assertEquals(0, microsoft.getStockCostBasis("2018-11-12"), 0.01);

    assertEquals(0, microsoft.getStockValue("2018-11-12"), 0.01);

  }

  @Test
  public void TestAddShareZeroAmount() {

    double output = microsoft.addShare(0, "2018-01-01", 0);

    assertEquals(0, output, 0.01);

    assertEquals(0, microsoft.getStockCostBasis("2018-11-12"), 0.01);

    assertEquals(0, microsoft.getStockValue("2018-11-12"), 0.01);


  }

  @Test
  public void TestAddShareInvalidAmount() {
    try {
      double output = microsoft.addShare(-0.0001, "2018-01-01", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid amount", ex.getMessage());
    }

    assertEquals(0, microsoft.getStockCostBasis("2018-11-12"), 0.01);

    assertEquals(0, microsoft.getStockValue("2018-11-12"), 0.01);


    try {
      double output = microsoft.addShare(-1000, "2018-01-01", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid amount", ex.getMessage());
    }

    assertEquals(0, microsoft.getStockCostBasis("2018-11-12"), 0.01);

    assertEquals(0, microsoft.getStockValue("2018-11-12"), 0.01);

    double output = microsoft.addShare(100.0001, "2018-01-01", 0);

    assertEquals(100, microsoft.getStockCostBasis("2018-11-12"), 0.01);

    assertEquals(109.420, microsoft.getStockValue("2018-11-12"), 0.01);

    try {
      output = microsoft.addShare(-1000, "2018-01-01", 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid amount", ex.getMessage());
    }

    assertEquals(100, microsoft.getStockCostBasis("2018-11-12"), 0.01);

    assertEquals(109.420, microsoft.getStockValue("2018-11-12"), 0.01);
  }

  @Test
  public void TestAddShare() {
    double output = microsoft.addShare(0.0001, "2018-01-01", 0);
    assertEquals(0, output, 0.01);

    assertEquals(0, microsoft.getStockCostBasis("2018-11-12"), 0.01);
    assertEquals(0, microsoft.getStockCostBasis("2016-11-12"), 0.01);

    assertEquals(0, microsoft.getStockValue("2018-11-12"), 0.01);
    assertEquals(0, microsoft.getStockValue("2016-11-12"), 0.01);

    output = microsoft.addShare(10000, "2018-01-01", 0);
    assertEquals(100, output, 0.01);

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
      assertEquals("Cannot fetch current share price:Cannot "
              + "fetch share value for a future date.", ex.getMessage());
    }

    output = microsoft.addShare(10000, "2016-12-01", 0);
    assertEquals(200.00, output, 0.01);

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
      assertEquals("Cannot fetch current share price:Share "
              + "prices do not exist for given date.", ex.getMessage());
    }

    output = microsoft.addShare(23.45, "2018-11-11", 0);
    assertEquals(0.21431182599159201, output, 0.01);

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
      assertEquals("Cannot fetch current share price:"
              + "Share prices do not exist for given date.", ex.getMessage());
    }

    /**
     * Add with same date.
     */
    output = microsoft.addShare(213.45, "2018-11-11", 0.1);
    assertEquals(1.95, output, 0.01);

    assertEquals(20000, microsoft.getStockCostBasis("2018-01-01"), 0.01);
    assertEquals(20237, microsoft.getStockCostBasis("2018-11-11"), 0.01);
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
      assertEquals("Cannot fetch current share price:"
              + "Share prices do not exist for given date.", ex.getMessage());
    }
    try {
      assertEquals(10000, microsoft.getStockValue("2020-01-01"), 0.01);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Cannot fetch current share price:Cannot "
              + "fetch share value for a future date.", ex.getMessage());
    }

    output = microsoft.addShare(5000, "2016-12-01", 10000);
    assertEquals(100.00, output, 0.001);

    assertEquals(35000, microsoft.getStockCostBasis("2018-01-01"), 0.01);
    assertEquals(35237, microsoft.getStockCostBasis("2018-11-11"), 0.01);
    assertEquals(25000, microsoft.getStockCostBasis("2016-12-01"), 0.01);
    assertEquals(0, microsoft.getStockCostBasis("2015-12-01"), 0.01);
    assertEquals(25000, microsoft.getStockCostBasis("2017-12-31"), 0.01);

    assertEquals(40000, microsoft.getStockValue("2018-01-01"), 0.01);
    assertEquals(44004.90010942, microsoft.getStockValue("2018-11-13"), 0.01);
    assertEquals(43768.00, microsoft.getStockValue("2018-11-10"), 0.01);
    assertEquals(0, microsoft.getStockValue("2016-11-12"), 0.01);
    assertEquals(15000, microsoft.getStockValue("2016-12-01"), 0.01);
    assertEquals(22536.0, microsoft.getStockValue("2017-12-31"), 0.01);
  }

  @Test
  public void testInvalidAddShareCommission() {
    try {
      double output = microsoft.addShare(1000, "2018-11-11", -0.01);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid commission", ex.getMessage());
    }
    try {
      double output = microsoft.addShare(1000, "2018-11-11", -1);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid commission", ex.getMessage());
    }
  }
}