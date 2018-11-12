import org.junit.Test;

import howtoinvest.model.Stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StockTest {

  private Stock stock;

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
      stock = new Stock("This ticker doesnt exit ");
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
  }

  @Test
  public void testRonn() {
    Stock oin = new Stock("GOOG");
    oin.addShare(900000.0, "2018-11-11");
    oin.addShare(90000.0, "2018-11-10");
    System.out.println(oin.getStockData());
  }

}