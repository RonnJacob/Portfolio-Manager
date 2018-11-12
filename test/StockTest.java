import org.junit.Before;
import org.junit.Test;

import howtoinvest.model.Stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StockTest {

  private Stock stock;
  private Stock google;

  @Before
  public void setUp() throws Exception {
    google = new Stock("GOOG");
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
    assertEquals("0.00 shares of FB for a total investment of $0.00\n", stock.getStockData());

    assertEquals(0, stock.getNumberOfShares(), 0.01);

    assertEquals(0, stock.getStockCostBasis("2018-11-12"), 0.01);
    assertEquals(0, stock.getStockCostBasis("2016-11-12"), 0.01);

    assertEquals(0, stock.getStockValue("2018-11-12"), 0.01);
    assertEquals(0, stock.getStockValue("2016-11-12"), 0.01);
  }

  @Test
  public void testInvalidAddShareDate1() {
    try {
      String output = google.addShare(1000, "2-1-1");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date. Please enter date again.Cannot fetch share price details"
              + " beyond 01/01/0002", ex.getMessage());
    }
    assertEquals("0.00 shares of GOOG for a total investment of $0.00\n", google.getStockData());

    assertEquals(0, google.getNumberOfShares(), 0.01);

    assertEquals(0, google.getStockCostBasis("2018-11-12"), 0.01);

    assertEquals(0, google.getStockValue("2018-11-12"), 0.01);
  }

  @Test
  public void testInvalidAddShareDate2() {
    try {
      String output = google.addShare(1000, "2000-01-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date. Please enter date again.Cannot fetch share price details"
              + " beyond 01/01/2000", ex.getMessage());
    }
  }

  @Test
  public void testInvalidAddShareDate3() {
    try {
      String output = google.addShare(1000, "2020-01-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date. Please enter date again.Invalid date."
              + " Cannot invest in future date.", ex.getMessage());
    }
  }

  @Test
  public void testInvalidAddShareDateFormat1() {
    try {
      String output = google.addShare(1000, "2018-01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }
  }

  @Test
  public void testInvalidAddShareDateFormat2() {
    try {
      String output = google.addShare(1000, "2018");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }
  }

  @Test
  public void testInvalidAddShareDateFormat3() {
    try {
      String output = google.addShare(1000, "");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }
  }

  @Test
  public void testInvalidAddShareDateFormat4() {
    try {
      String output = google.addShare(1000, "2018/01/01");
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid date format. Please enter date again.", ex.getMessage());
    }
  }


}