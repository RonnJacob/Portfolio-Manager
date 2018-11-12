import org.junit.Test;

import howtoinvest.model.Stock;

public class StockTest {

  @Test
  public void testRonn(){
    Stock oin = new Stock("GOOG");
    oin.addShare(900000.0,"2018-11-11");
    oin.addShare(90000.0,"2018-11-10");
    System.out.println(oin.getStockData());
  }

}