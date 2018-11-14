import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import howtoinvest.controller.HowToInvestController;
import howtoinvest.controller.IHowToInvestController;
import howtoinvest.model.IPortfolioManager;
import howtoinvest.model.StockPortfolioManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class HowToInvestControllerTest {

  private String welcome = "\nWelcome to Portfolio Manager.\n" +
          "1. Create new portfolio.\n" +
          "2. Get existing portfolios.\n" +
          "3. Enter portfolio.\n" +
          "Enter the number for performing operation or q to quit application.\n";
  private String stockHome = "\n" +
          "1. Examine composition of portfolio\n" +
          "2. Buy shares of a stock with portfolio.\n" +
          "3. Get Cost Basis/Value of portfolio\n" +
          "Enter R to return to the main menu or q to quit the application.\n";
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
  public void TestPortfolioManagerEmptyInput(){
    Reader stringReader = new StringReader("");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expected = welcome +"Quitting Manager\n" ;
    assertEquals("", log.toString());
    assertEquals(expected, out.toString());
  }

  @Test
  public void TestPortfolioManagerWhiteSpaces(){
    Reader stringReader = new StringReader("             ");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expected = welcome +"Quitting Manager\n" ;
    assertEquals("", log.toString());
    assertEquals(expected, out.toString());
  }

  @Test
  public void TestPortfolioManagerNullPortfolioManager(){
    Reader stringReader = new StringReader("");
    String expected = "Portfolio manager cannot be a null.";
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    try{
      controller.openPortfolioManager(null);
    } catch(IllegalArgumentException ex){
      assertEquals(expected,ex.getMessage());
    }
    assertEquals("", log.toString());
    assertEquals("", out.toString());
  }


  @Test
  public void TestPortfolioManagerSelectingOptionToCreate(){
    Reader stringReader = new StringReader("1 Portfolio1");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expected = "\nWelcome to Portfolio Manager.\n" +
            "1. Create new portfolio.\n" +
            "2. Get existing portfolios.\n" +
            "3. Enter portfolio.\n" +
            "Enter the number for performing operation or q to quit application.\n" +
            "Enter the name of the portfolio to be created.\n" +
            "Portfolio Portfolio1 has been created.\n\n" +
            "Add more portfolios? (Y/N)";
    expected = expected+ welcome +"Quitting Manager\n" ;
    assertEquals(expected, out.toString());
    expected = "Create Portfolio Name Portfolio1\n" ;
    assertEquals(expected, log.toString());
  }

  @Test
  public void TestPortfolioManagerSelectingOptionToGetAllPortfolios(){
    Reader stringReader = new StringReader("2  ");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expected = "\nWelcome to Portfolio Manager.\n" +
            "1. Create new portfolio.\n" +
            "2. Get existing portfolios.\n" +
            "3. Enter portfolio.\n" +
            "Enter the number for performing operation or q to quit application.\n" +
            "\n" +
            "List of Portfolios\n" +
            "\n" +
            "1\n";
    expected = expected+ welcome +"Quitting Manager\n" ;
    assertEquals(expected, out.toString());
    expected = "All Portfolios\n" ;
    assertEquals(expected, log.toString());
  }

  @Test
  public void TestPortfolioManagerSelectingOptionToEnterPortfolio(){
    Reader stringReader = new StringReader("3 1 q");
    String expectedView= "";
    String expectedLogs= "";
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expectedView = "\nWelcome to Portfolio Manager.\n" +
            "1. Create new portfolio.\n" +
            "2. Get existing portfolios.\n" +
            "3. Enter portfolio.\n" +
            "Enter the number for performing operation or q to quit application.\n" +
            "\n" +
            "Enter index of Portfolio to open.\n" +
            "\n" +
            "1. Examine composition of portfolio\n" +
            "2. Buy shares of a stock with portfolio.\n" +
            "3. Get Cost Basis/Value of portfolio\n" +
            "Enter R to return to the main menu or q to quit the application.\n";
    expectedView = expectedView+ "Quitting Manager\n" ;
    assertEquals(expectedView, out.toString());
    expectedLogs = "Get Portfolio 1\n" ;
    assertEquals(expectedLogs, log.toString());
  }


  @Test
  public void TestPortfolioManagerSelectingOptionToMultiplePortfolioEnter(){
    String welcome = "Welcome to Portfolio Manager.\n" +
            "1. Create new portfolio.\n" +
            "2. Get existing portfolios.\n" +
            "3. Enter portfolio.\n" +
            "Enter the number for performing operation or q to quit application.\n";
    Reader stringReader = new StringReader("3 1 r 3 2 r 3 4 q");
    String expectedView = "";
    String expectedLogs= "";
    String message = "\nEnter index of Portfolio to open.\n";
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expectedView = "\n"+welcome+message+stockHome+welcome+message+stockHome+welcome+message
            + stockHome +"Quitting Manager\n";
    assertEquals(expectedView, out.toString());
    expectedLogs = "Get Portfolio 1\nGet Portfolio 2\nGet Portfolio 4\n" ;
    assertEquals(expectedLogs, log.toString());
  }



  @Test
  public void TestPortfolioManagerSelectingOptionToMultiplePortfolioExamineComposition(){
    Reader stringReader = new StringReader("3 1 1 q");
    String expectedView = "";
    String message = "\nEnter index of Portfolio to open.\n";
    String expectedLogs= "";
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expectedView = welcome+message+stockHome+"31"+stockHome+"Quitting Manager\n";
    assertEquals(expectedView, out.toString());
    expectedLogs = "Get Portfolio 1\n" +
            "Get portfolio composition\n" ;
    assertEquals(expectedLogs, log.toString());
  }

  @Test
  public void TestPortfolioManagerSelectingOptionToMultiplePortfolioBuyShare(){
    Reader stringReader = new StringReader("3 1 2 AAPL 1000 2018-10-10 n q");
    String expectedLogs= "";
    String expectedView = "";
    String message = "\n" +
            "Enter index of Portfolio to open.\n" + stockHome+
            "Enter stock symbol:\n" +
            "\n" +
            "Enter amount for which shares are to be bought:\n" +
            "\n" +
            "Enter date in format yyyy-mm-dd: \n" +
            "\n" +
            "32\n" +
            "Buy more shares? (Y/N)" + stockHome;
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expectedView = welcome+message+"Quitting Manager\n";
    assertEquals(expectedView, out.toString());
    expectedLogs = "Get Portfolio 1\n" +
            "Get share for AAPL for 1000.0 on 2018-10-10\n" ;
    assertEquals(expectedLogs, log.toString());
  }


  @Test
  public void TestPortfolioManagerSelectingOptionToMultiplePortfolioGetCostBasis(){
    Reader stringReader = new StringReader("3 1 3 2018-10-10 q");
    String expectedLogs= "";
    String expectedView = "";
    String message = "\n" +
            "Enter index of Portfolio to open.\n" + stockHome+
            "Enter date in format yyyy-mm-dd: \n" +
            "\n" +
            "33"  + stockHome;
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expectedView = welcome+message+"Quitting Manager\n";
    assertEquals(expectedView, out.toString());
    expectedLogs = "Get Portfolio 1\n" +
            "Get cost basis for 2018-10-10\n" ;
    assertEquals(expectedLogs, log.toString());
  }

  @Test
  public void TestPortfolioManagerQuit(){
    Reader stringReader = new StringReader("q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expected = welcome +"Quitting Manager\n" ;
    assertEquals("", log.toString());
    assertEquals(expected, out.toString());
  }

  @Test
  public void TestPortfolioManagerNull(){
    Reader stringReader = new StringReader("q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    try{
      controller.openPortfolioManager(null);
    } catch(IllegalArgumentException ex){
      expected = "Portfolio manager cannot be a null.";
      assertEquals(expected,ex.getMessage());
    }
  }

  @Test
  public void TestPortfolioManagerInvalidPortfolio(){
    Reader stringReader = new StringReader("q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    try{
      controller.openPortfolioManager(null);
    } catch(IllegalArgumentException ex){
      expected = "Portfolio manager cannot be a null.";
      assertEquals(expected,ex.getMessage());
    }
  }


  @Test
  public void TestPortfolioManagerInvalidPortfolioIndex(){
    Reader stringReader = new StringReader("3 4 q");
    String expected = "";
    String message = "\nEnter index of Portfolio to open.\n"
            + "\nInvalid portfolio. Cannot retrieve portfolio.\n";
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockPortfolioManagerTwo model = new MockPortfolioManagerTwo(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expected = welcome +message+welcome+"Quitting Manager\n" ;
    assertEquals("Cannot fetch 4th portfolio.\n", log.toString());
    assertEquals(expected, out.toString());
  }

  @Test
  public void TestPortfolioManagerInvalidInputForStockHomeOptions(){
    Reader stringReader = new StringReader("3 1 4 q");
    String expected = "";
    String message = "\nEnter index of Portfolio to open.\n";
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expected = welcome +message+stockHome+"Invalid input. Please enter input again.\nQuitting "
            + "Manager\n" ;
    assertEquals("Get Portfolio 1\n", log.toString());
    assertEquals(expected, out.toString());
  }

  @Test
  public void TestPortfolioManagerInvalidInputForStockHomeNegative(){
    Reader stringReader = new StringReader("3 1 -4 q");
    String expected = "";
    String message = "\nEnter index of Portfolio to open.\n";
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expected = welcome +message+stockHome+"Invalid input. Please enter input again.\nQuitting "
            + "Manager\n" ;
    assertEquals("Get Portfolio 1\n", log.toString());
    assertEquals(expected, out.toString());
  }

  @Test
  public void TestPortfolioManagerInvalidInputForStockHomeAlphabets(){
    Reader stringReader = new StringReader("3 1 asdaaf q");
    String expected = "";
    String message = "\nEnter index of Portfolio to open.\n";
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expected = welcome +message+stockHome+"Invalid input. Please enter input again.\nQuitting "
            + "Manager\n" ;
    assertEquals("Get Portfolio 1\n", log.toString());
    assertEquals(expected, out.toString());
  }

  @Test
  public void TestPortfolioManagerCreatingMoreThanOnePortfolio(){
    Reader stringReader = new StringReader("1 Portfolio1 y Portfolio2 y Portfolio3 q\n");
    String expected = "";
    String message = "" +
            "Enter the name of the portfolio to be created.\n" +
            "Portfolio Portfolio1 has been created.\n" +
            "\n" +
            "Add more portfolios? (Y/N)\n" +
            "Enter the name of the portfolio to be created.\n" +
            "Portfolio Portfolio2 has been created.\n" +
            "\n" +
            "Add more portfolios? (Y/N)\n" +
            "Enter the name of the portfolio to be created.\n" +
            "Portfolio Portfolio3 has been created.\n" +
            "\n" +
            "Add more portfolios? (Y/N)";
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expected = welcome +message+welcome+"Quitting "
            + "Manager\n" ;
    assertEquals("Create Portfolio Name Portfolio1\n"
            + "Create Portfolio Name Portfolio2\n"
            + "Create Portfolio Name Portfolio3\n", log.toString());
    assertEquals(expected, out.toString());
  }

  @Test
  public void TestPortfolioManagerCreatingMoreThanOneShare(){
    Reader stringReader = new StringReader("3 1 2 AAPL 100 2018-10-10 y GOOG 20 2018-10-10 n "
            + "q \n");
    String expected = "";
    String message = "\n" +
            "Enter index of Portfolio to open.\n" + stockHome+
            "Enter stock symbol:\n" +
            "\n" +
            "Enter amount for which shares are to be bought:\n" +
            "\n" +
            "Enter date in format yyyy-mm-dd: \n" +
            "\n" +
            "32\n" +
            "Buy more shares? (Y/N)\n" +
            "Enter stock symbol:\n" +
            "\n" +
            "Enter amount for which shares are to be bought:\n" +
            "\n" +
            "Enter date in format yyyy-mm-dd: \n" +
            "\n" +
            "32\n" +
            "Buy more shares? (Y/N)" + stockHome;
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expected = welcome +message+"Quitting "
            + "Manager\n" ;
    assertEquals("Get Portfolio 1\n" +
            "Get share for AAPL for 100.0 on 2018-10-10\n" +
            "Get share for GOOG for 20.0 on 2018-10-10\n", log.toString());
    assertEquals(expected, out.toString());
  }

  @Test
  public void checkControllerAppendableException() throws IOException {
    String s = "";
    s += "3 1 2 AAPL 100 2018-10-10 y GOOG 20 2018-10-10 n q";
    Reader stringReader = new StringReader(s);
    File file = new File("Hello1.txt");
    FileWriter out = new FileWriter(file);
    StringBuilder log = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    out.close();
    try {
      controller.openPortfolioManager(model);
      fail();
    } catch (IllegalStateException ex) {
      assertEquals("IO exception has been encountered.", ex.getMessage());
    }
  }

  @Test
  public void TestPortfolioManagerOptionOneDoesNotEndWithQNoName(){
    String welcome = "\nWelcome to Portfolio Manager.\n" +
            "1. Create new portfolio.\n" +
            "2. Get existing portfolios.\n" +
            "3. Enter portfolio.\n" +
            "Enter the number for performing operation or q to quit application.";
    Reader stringReader = new StringReader("1");
    String expectedLogs= "";
    String expectedView = "";
    String message = "\n" +
            "Enter the name of the portfolio to be created.";
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expectedView = welcome+message+welcome+"\nQuitting Manager\n";
    assertEquals(expectedView, out.toString());
    expectedLogs = "" ;
    assertEquals(expectedLogs, log.toString());
  }

  @Test
  public void TestPortfolioManagerOptionTwoDoesNotEndWithQNoName(){
    String welcome = "\nWelcome to Portfolio Manager.\n" +
            "1. Create new portfolio.\n" +
            "2. Get existing portfolios.\n" +
            "3. Enter portfolio.\n" +
            "Enter the number for performing operation or q to quit application.";
    Reader stringReader = new StringReader("2");
    String expectedLogs= "";
    String expectedView = "";
    String message = "\n" +
            "\n" +
            "List of Portfolios\n" +
            "\n" +
            "1\n";
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expectedView = welcome+message+welcome+"\nQuitting Manager\n";
    assertEquals(expectedView, out.toString());
    expectedLogs = "All Portfolios\n" ;
    assertEquals(expectedLogs, log.toString());
  }


  @Test
  public void TestPortfolioManagerOptionThreeDoesNotEndWithQNoName(){
    String welcome = "\nWelcome to Portfolio Manager.\n" +
            "1. Create new portfolio.\n" +
            "2. Get existing portfolios.\n" +
            "3. Enter portfolio.\n" +
            "Enter the number for performing operation or q to quit application.";
    Reader stringReader = new StringReader("3");
    String expectedLogs= "";
    String expectedView = "";
    String message = "\n\nEnter index of Portfolio to open.\n\n";
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expectedView = welcome+message;
    assertEquals(expectedView, out.toString());
    expectedLogs = "" ;
    assertEquals(expectedLogs, log.toString());
  }

  @Test
  public void TestPortfolioManagerAddingPortfolioRetrieveAddingStock(){
    String nameOfPortfolio = "MyPortfolio";
    String welcome = "\nWelcome to Portfolio Manager.\n" +
            "1. Create new portfolio.\n" +
            "2. Get existing portfolios.\n" +
            "3. Enter portfolio.\n" +
            "Enter the number for performing operation or q to quit application.";

    Reader stringReader = new StringReader("1 "+nameOfPortfolio+" n 2 3 1 1 3 2018-10-10 "
            + "2 AAPL 1000 2018-10-08 n q");
    String expectedLogs= "";
    String expectedView = "";
    String listOfPortfolios = "\n\nList of Portfolios\n\n1\n";
    String addSharePrompts = "Enter stock symbol:\n" +
            "\n" +
            "Enter amount for which shares are to be bought:\n" +
            "\n" +
            "Enter date in format yyyy-mm-dd: \n\n";
    String indexMessage = "\n\nEnter index of Portfolio to open.\n";
    String message = "\nEnter the name of the portfolio to be created.\n" +
            "Portfolio "+nameOfPortfolio+" has been created.\n\n"
            + "Add more portfolios? (Y/N)"+welcome+listOfPortfolios+welcome+indexMessage+stockHome
            + "31"+stockHome+"Enter date in format yyyy-mm-dd: \n\n33"
            +stockHome+addSharePrompts+"32\n" +
            "Buy more shares? (Y/N)"+stockHome+"Quitting Manager\n";
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expectedView = welcome+message;
    assertEquals(expectedView, out.toString());
    expectedLogs = "Create Portfolio Name MyPortfolio\n"
            + "All Portfolios\n"
            + "Get Portfolio 1\n"
            + "Get portfolio composition\n"
            + "Get cost basis for 2018-10-10\n"
            + "Get share for AAPL for 1000.0 on 2018-10-08\n" ;
    assertEquals(expectedLogs, log.toString());
  }


  @Test
  public void TestPortfolioManagerReturningFromStockMenu(){


    String welcome = "Welcome to Portfolio Manager.\n" +
            "1. Create new portfolio.\n" +
            "2. Get existing portfolios.\n" +
            "3. Enter portfolio.\n" +
            "Enter the number for performing operation or q to quit application.";
    Reader stringReader = new StringReader("3 1 r q");
    String expectedLogs= "";
    String expectedView = "";
    String indexMessage = "\n\nEnter index of Portfolio to open.\n";

    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expectedView = "\n"+welcome+indexMessage+stockHome+welcome+"\nQuitting Manager\n";
    assertEquals(expectedView, out.toString());
    expectedLogs = "Get Portfolio 1\n" ;
    assertEquals(expectedLogs, log.toString());
  }

  @Test
  public void TestPortfolioManagerQuittingFromStockMenu(){


    String welcome = "Welcome to Portfolio Manager.\n" +
            "1. Create new portfolio.\n" +
            "2. Get existing portfolios.\n" +
            "3. Enter portfolio.\n" +
            "Enter the number for performing operation or q to quit application.";
    Reader stringReader = new StringReader("3 1 q");
    String expectedLogs= "";
    String expectedView = "";
    String indexMessage = "\n\nEnter index of Portfolio to open.\n";

    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(log);
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    expectedView = "\n"+welcome+indexMessage+stockHome+"Quitting Manager\n";
    assertEquals(expectedView, out.toString());
    expectedLogs = "Get Portfolio 1\n" ;
    assertEquals(expectedLogs, log.toString());
  }

  @Test
  public void TestControllerModelIntegration(){
    String welcome = "Welcome to Portfolio Manager.\n" +
            "1. Create new portfolio.\n" +
            "2. Get existing portfolios.\n" +
            "3. Enter portfolio.\n" +
            "Enter the number for performing operation or q to quit application.";
    Reader stringReader = new StringReader("3 1 2 FB 1000 2018-10-10 n 1 q");
    String expectedView = "";
    String indexMessage = "\n\nEnter index of Portfolio to open.\n";
    String addSharePrompts = "Enter stock symbol:\n" +
            "\n" +
            "Enter amount for which shares are to be bought:\n" +
            "\n" +
            "Enter date in format yyyy-mm-dd: \n\n";
    StringWriter out = new StringWriter();
    StringBuilder log = new StringBuilder();
    IPortfolioManager model = new StockPortfolioManager();
    IHowToInvestController controller = new HowToInvestController(stringReader, out);
    controller.openPortfolioManager(model);
    String expectedShareBought = "9.52 shares of FB bought on 2018-10-10 for $1000.00\nBuy more "
            + "shares? (Y/N)";
    String composition = "9.52 shares of FB for a total investment of $1000.00";
    expectedView = "\n"+welcome+indexMessage+stockHome+addSharePrompts+expectedShareBought
            +stockHome+composition+stockHome+"Quitting Manager\n";
    assertEquals(expectedView, out.toString());
  }



}