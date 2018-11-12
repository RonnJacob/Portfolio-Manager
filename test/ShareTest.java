import org.junit.Test;

import howtoinvest.model.Share;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ShareTest {

  private Share share;
  private String maxDouble = "179769313486231570000000000000000000000000000000000000000000000000000"
          + "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000"
          + "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000"
          + "0000000000000000000000000000000000000000000000000000000000000000000000.00";

  @Test
  public void TestShareCreationZero() {
    share = new Share(0, 0);
    assertEquals(0, share.getNumberOfShares(), 0.01);
    assertEquals(0, share.getShareCostBasis(), 0.01);
    assertEquals("Number of shares: 0.00\nShare Cost Basis: 0.00\n", share.getShareData());
  }

  @Test
  public void TestShareCreationZeroShares() {
    share = new Share(0.11, 0);
    assertEquals(0, share.getNumberOfShares(), 0.01);
    assertEquals(0.11, share.getShareCostBasis(), 0.01);
    assertEquals("Number of shares: 0.00\nShare Cost Basis: 0.11\n", share.getShareData());
  }

  @Test
  public void TestShareCreationZeroCostBasis() {
    share = new Share(0, 0.11);
    assertEquals(0.11, share.getNumberOfShares(), 0.01);
    assertEquals(0, share.getShareCostBasis(), 0.01);
    assertEquals("Number of shares: 0.11\nShare Cost Basis: 0.00\n", share.getShareData());
  }

  @Test
  public void TestShareCreationNegative() {
    try {
      share = new Share(-0.01, -0.1);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid input for stock.", ex.getMessage());
    }
  }

  @Test
  public void TestShareCreationNegativeShares() {
    try {
      share = new Share(-0.001, 0);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid input for stock.", ex.getMessage());
    }
  }

  @Test
  public void TestShareCreationNegativeCostBasis() {
    try {
      share = new Share(0, -0.001);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid input for stock.", ex.getMessage());
    }
  }

  @Test
  public void TestShareCreationMax() {
    share = new Share(Double.MAX_VALUE, Double.MAX_VALUE);
    assertEquals(Double.MAX_VALUE, share.getNumberOfShares(), 0.01);
    assertEquals(Double.MAX_VALUE, share.getShareCostBasis(), 0.01);
    assertEquals("Number of shares: " + maxDouble + "\nShare Cost Basis: "
            + maxDouble + "\n", share.getShareData());
  }

  @Test
  public void TestShareCreation() {
    share = new Share(3.5, 20.01);
    assertEquals(20.01, share.getNumberOfShares(), 0.01);
    assertEquals(3.5, share.getShareCostBasis(), 0.01);
    assertEquals("Number of shares: 20.01\nShare Cost Basis: 3.50\n", share.getShareData());
  }
}