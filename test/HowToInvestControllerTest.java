import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import howtoinvest.controller.HowToInvestController;
import howtoinvest.controller.IHowToInvestController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class HowToInvestControllerTest {

  String welcome = "\n\n" +
          "Welcome to Portfolio Manager.\n" +
          "1. Create new portfolio.\n" +
          "2. Get existing portfolios.\n" +
          "3. Enter portfolio.\n" +
          "Enter the number for performing operation or q to quit application.\n";
  @Test
  public void checkControllerWithInvalidReadableAppendable() {
    /**
     * Null Readable.
     */
    Reader stringReader;
    String expected = "Invalid Readable or Appendable object";
    StringWriter out = new StringWriter();
    stringReader = null;
    try {
      IHowToInvestController controller = new HowToInvestController(stringReader, out);
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals(expected, ex.getMessage());
    }

    /**
     * Null Appendable.
     */
    stringReader = new StringReader("");
    out = null;
    try {
      IHowToInvestController controller = new HowToInvestController(stringReader, out);
      fail();
    } catch (IllegalArgumentException ex) {
      expected = "Invalid Readable or Appendable object";
      assertEquals(expected, ex.getMessage());
    }

    /**
     * Null Appendable and Readable.
     */
    stringReader = null;
    out = null;
    try {
      IHowToInvestController controller = new HowToInvestController(stringReader, out);
      fail();
    } catch (IllegalArgumentException ex) {
      expected = "Invalid Readable or Appendable object";
      assertEquals(expected, ex.getMessage());
    }
  }

  @Test
  public void TestPortfolioManagerQuit(){
    Reader stringReader = new StringReader("q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockModelOne model = new MockModelOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expected = welcome +"Quitting Manager\n" ;
    assertEquals("", log.toString());
    assertEquals(expected, out.toString());
  }

  @Test
  public void TestPortfolioManagerQuit1(){
    Reader stringReader = new StringReader("");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockModelOne model = new MockModelOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expected = welcome +"Quitting Manager\n" ;
    assertEquals("", log.toString());
    assertEquals(expected, out.toString());
  }
}