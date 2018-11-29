import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import howtoinvest.model.AlphaVantage;
import howtoinvest.model.IStockDataRetrieval;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class contains all the tests for the AlphaVantage class.
 */
public class AlphaVantageTest {
  private final IStockDataRetrieval alphavantageApi = new AlphaVantage();

  @Test
  public void testCheckValidityOfAlphaVantageFalse() {
    String invalidTickerName = "a1234a1234";
    assertFalse(alphavantageApi.checkValidityOfTickerName(invalidTickerName));
  }

  @Test
  public void testCheckValidityOfAlphaVantageTrue() {
    String validTickerName = "FB";
    assertTrue(alphavantageApi.checkValidityOfTickerName(validTickerName));
  }

  @Test
  public void testRetrieveSharePriceOfFutureDate() {
    String validTickerName = "FB";
    String message = "Cannot fetch share value for a future date";
    try {
      Date date = new GregorianCalendar(2019, Calendar.FEBRUARY, 10).getTime();
      alphavantageApi.retrieveSharePrice(date, validTickerName);
      fail();
    } catch (ParseException | IllegalArgumentException ex) {
      assertEquals(message, ex.getMessage());
    }
  }

  @Test
  public void testRetrieveSharePriceOfNonexistentHistory() {
    String validTickerName = "FB";
    String message = "Share prices do not exist for given date.";
    try {
      Date date = new GregorianCalendar(1990, Calendar.FEBRUARY, 10).getTime();
      alphavantageApi.retrieveSharePrice(date, validTickerName);
      fail();
    } catch (ParseException | IllegalArgumentException ex) {
      assertEquals(message, ex.getMessage());
    }
  }

  @Test
  public void testRetrieveSharePriceOfDatePresentNonHoliday() {
    String validTickerName = "FB";
    String message = "Share prices do not exist for given date.";
    try {
      Date date = new GregorianCalendar(2018, Calendar.NOVEMBER, 27).getTime();
      Double price = alphavantageApi.retrieveSharePrice(date, validTickerName);
      assertEquals(price, 135.0000, 0.01);
    } catch (ParseException | IllegalArgumentException ex) {
      assertEquals(message, ex.getMessage());
    }
  }

  @Test
  public void testRetrieveSharePriceOfDateNotPresentWeekend() {
    String validTickerName = "FB";
    String message = "Share prices do not exist for given date.";
    try {
      Date date = new GregorianCalendar(2018, Calendar.NOVEMBER, 25).getTime();
      Double price = alphavantageApi.retrieveSharePrice(date, validTickerName);
      assertEquals(price, 131.7300, 0.01);
    } catch (ParseException | IllegalArgumentException ex) {
      assertEquals(message, ex.getMessage());
    }
  }

  @Test
  public void testRetrieveSharePriceOfDateHolidayOrClosedExchange() {
    String validTickerName = "FB";
    String message = "Share prices do not exist for given date.";
    try {
      Date date = new GregorianCalendar(2017, Calendar.DECEMBER, 25).getTime();
      Double price = alphavantageApi.retrieveSharePrice(date, validTickerName);
      assertEquals(price, 177.20, 0.01);
    } catch (ParseException | IllegalArgumentException ex) {
      assertEquals(message, ex.getMessage());
    }
  }

}