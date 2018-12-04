//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.io.StringReader;
//import java.io.StringWriter;
//
//import howtoinvest.view.HowToInvestViewImpl;
//import howtoinvest.view.IHowToInvestView;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;
//
///**
// * The following class contains tests pertaining to the HowToInvestViewImpl operations.
// */
//public class HowToInvestViewTest {
//  private String expected = "";
//  private IHowToInvestView view;
//  private StringBuffer out;
//
//  @Before
//  public void setUp() {
//
//    out = new StringBuffer();
//    view = new HowToInvestViewImpl(new InputStreamReader(System.in), out);
//  }
//
//  @Test
//  public void testOpenHomeScreen() {
//    expected += "\nWelcome to Portfolio Manager.\n";
//    expected += "1. Create new portfolio.\n";
//    expected += "2. Get existing portfolios.\n";
//    expected += "3. Enter portfolio.\n";
//    expected += "Enter the number for performing operation or q to quit application.\n";
//    view.openHomeScreen();
//    assertEquals(expected, out.toString());
//  }
//
//  @Test
//  public void testOpenPortfolioMenu() {
//    expected += "\n1. Examine composition of portfolio\n";
//    expected += "2. Buy shares of a stock with portfolio.\n";
//    expected += "3. Get Cost Basis of portfolio\n";
//    expected += "4. Get Value of portfolio\n";
//    expected += "5. Invest on stocks in portfolio. \n";
//    expected += "6. Open Strategy Manager Menu. \n";
//    expected += "Enter the number for performing operation, r to return to the main "
//            + "menu or q to quit the application.\n";
//    view.openPortfolioMenu();
//    assertEquals(expected, out.toString());
//  }
//
//  @Test
//  public void testOpenInvestmentMenu() {
//    expected += "\nInvestment Strategies \n";
//    expected += "1. Invest on stocks in portfolio with equal weights.\n";
//    expected += "2. Invest on stocks in portfolio with custom weights.\n";
//    expected += "Enter the number for performing operation or r to return to the main "
//            + "menu.\n";
//    view.openInvestmentMenu();
//    assertEquals(expected, out.toString());
//  }
//
//
//  @Test
//  public void testOpenStrategyManagerMenu() {
//    expected = "\nStrategy Manager Menu\n";
//    expected += "1. Create Strategy\n";
//    expected += "2. Enter A Strategy\n";
//    expected += "3. Display Strategies\n";
//    expected += "Enter the number for performing operation or r to return to the main "
//            + "menu.\n";
//    view.openStrategyManagerMenu();
//    assertEquals(expected, out.toString());
//  }
//
//
//  @Test
//  public void testOpenStrategyMenu() {
//    expected += "\nStrategy Menu\n";
//    expected += "1. Apply Strategy\n";
//    expected += "2. Modify strategy\n";
//    expected += "3. Display Stocks\n";
//    expected += "Enter the number for performing operation or r to return to the main "
//            + "menu.\n";
//    view.openStrategyMenu();
//    assertEquals(expected, out.toString());
//  }
//
//  @Test
//  public void testQuitManager() {
//    expected += "Quitting manager\n";
//    view.quitManager();
//    assertEquals(expected, out.toString());
//  }
//
//  @Test
//  public void testDisplayPortfolioComposition() {
//    Double numberOfShares = 2.3;
//    String stockName = "FB";
//    expected += numberOfShares + " share(s) of " + stockName + "\n";
//    view.displayPortfolioComposition(stockName, numberOfShares);
//    assertEquals(expected, out.toString());
//    numberOfShares = 4000.2;
//    stockName = "MSFT";
//    expected += numberOfShares + " share(s) of " + stockName + "\n";
//    view.displayPortfolioComposition(stockName, numberOfShares);
//    assertEquals(expected, out.toString());
//    numberOfShares = 0.00000;
//    stockName = "AB";
//    expected += numberOfShares + " share(s) of " + stockName + "\n";
//    view.displayPortfolioComposition(stockName, numberOfShares);
//    assertEquals(expected, out.toString());
//    numberOfShares = -11.2;
//    stockName = "FB";
//    expected += numberOfShares + " share(s) of " + stockName + "\n";
//    view.displayPortfolioComposition(stockName, numberOfShares);
//    assertEquals(expected, out.toString());
//    numberOfShares = 1.2;
//    stockName = "AB";
//    expected += numberOfShares + " share(s) of " + stockName + "\n";
//    view.displayPortfolioComposition(stockName, numberOfShares);
//    assertEquals(expected, out.toString());
//    numberOfShares = -1.2;
//    stockName = "AB";
//    expected += numberOfShares + " share(s) of " + stockName + "\n";
//    view.displayPortfolioComposition(stockName, numberOfShares);
//    assertEquals(expected, out.toString());
//    numberOfShares = -1.2;
//    stockName = null;
//    expected += numberOfShares + " share(s) of " + stockName + "\n";
//    view.displayPortfolioComposition(stockName, numberOfShares);
//    assertEquals(expected, out.toString());
//  }
//
//  @Test
//  public void testDisplayPortfolioValue() {
//    Double value = 2200.2;
//    String date = "2018-11-10";
//    expected += "The value of the portfolio as of " + date + " is $" + value + "\n";
//    view.displayPortfolioValue(date, value);
//    assertEquals(expected, out.toString());
//    value = -2200.2;
//    date = "2018-11-10";
//    expected += "The value of the portfolio as of " + date + " is $" + value + "\n";
//    view.displayPortfolioValue(date, value);
//    assertEquals(expected, out.toString());
//    value = 2200.2;
//    date = "201811-10";
//    expected += "The value of the portfolio as of " + date + " is $" + value + "\n";
//    view.displayPortfolioValue(date, value);
//    assertEquals(expected, out.toString());
//    value = -200.2;
//    date = "---2018-11-10";
//    expected += "The value of the portfolio as of " + date + " is $" + value + "\n";
//    view.displayPortfolioValue(date, value);
//    assertEquals(expected, out.toString());
//    value = -200.2;
//    date = null;
//    expected += "The value of the portfolio as of " + date + " is $" + value + "\n";
//    view.displayPortfolioValue(date, value);
//    assertEquals(expected, out.toString());
//  }
//
//  @Test
//  public void testDisplayPortfolioCostBasis() {
//    Double stockCostBasis = 2200.2;
//    String date = "2018-11-10";
//    expected += "The cost basis of the portfolio as of " + date + " is $" + stockCostBasis + "\n";
//    view.displayPortfolioCostBasis(date, stockCostBasis);
//    assertEquals(expected, out.toString());
//    stockCostBasis = -2200.2;
//    date = "2018-11-10";
//    expected += "The cost basis of the portfolio as of " + date + " is $" + stockCostBasis + "\n";
//    view.displayPortfolioCostBasis(date, stockCostBasis);
//    assertEquals(expected, out.toString());
//    stockCostBasis = 2200.2;
//    date = "201811-10";
//    expected += "The cost basis of the portfolio as of " + date + " is $" + stockCostBasis + "\n";
//    view.displayPortfolioCostBasis(date, stockCostBasis);
//    assertEquals(expected, out.toString());
//    stockCostBasis = -200.2;
//    date = "---2018-11-10";
//    expected += "The cost basis of the portfolio as of " + date + " is $" + stockCostBasis + "\n";
//    view.displayPortfolioCostBasis(date, stockCostBasis);
//    assertEquals(expected, out.toString());
//    stockCostBasis = -200.2;
//    date = null;
//    expected += "The cost basis of the portfolio as of " + date + " is $" + stockCostBasis + "\n";
//    view.displayPortfolioCostBasis(date, stockCostBasis);
//    assertEquals(expected, out.toString());
//  }
//
//  @Test
//  public void testDisplayList() {
//    int counter = 1;
//    String listItem = "Ronn";
//    String listName = "Name";
//    expected += "\nList of " + listName + "\n";
//    expected += counter + ": " + listItem + "\n";
//    view.displayList(counter, listItem, listName);
//    assertEquals(expected, out.toString());
//    counter = 2;
//    listItem = "Reno";
//    listName = "Name";
//    expected += counter + ": " + listItem + "\n";
//    view.displayList(counter, listItem, listName);
//    assertEquals(expected, out.toString());
//    counter = 1;
//    listItem = "Reno";
//    listName = "1";
//    expected += "\nList of " + listName + "\n";
//    expected += counter + ": " + listItem + "\n";
//    view.displayList(counter, listItem, listName);
//    assertEquals(expected, out.toString());
//    counter = 5;
//    listItem = "0";
//    listName = "0";
//    expected += counter + ": " + listItem + "\n";
//    view.displayList(counter, listItem, listName);
//    assertEquals(expected, out.toString());
//    counter = 5;
//    listItem = null;
//    listName = null;
//    expected += counter + ": " + listItem + "\n";
//    view.displayList(counter, listItem, listName);
//    assertEquals(expected, out.toString());
//  }
//
//  @Test
//  public void testPromptMessage() {
//    String message = "Ronn";
//    expected += message + "\n";
//    view.promptMessage(message);
//    assertEquals(expected, out.toString());
//    message = "1";
//    expected += message + "\n";
//    view.promptMessage(message);
//    assertEquals(expected, out.toString());
//    message = "Ronn George" + "\n";
//    expected += message + "\n";
//    view.promptMessage(message);
//    assertEquals(expected, out.toString());
//    message = null;
//    expected += message + "\n";
//    view.promptMessage(message);
//    assertEquals(expected, out.toString());
//    message = "-1";
//    expected += message + "\n";
//    view.promptMessage(message);
//    assertEquals(expected, out.toString());
//    message = "1.22";
//    expected += message + "\n";
//    view.promptMessage(message);
//    assertEquals(expected, out.toString());
//    message = "";
//    expected += message + "\n";
//    view.promptMessage(message);
//    assertEquals(expected, out.toString());
//    message = "  ";
//    expected += message + "\n";
//    view.promptMessage(message);
//    assertEquals(expected, out.toString());
//  }
//
//  @Test
//  public void testGetInput() {
//    StringBuffer out = new StringBuffer();
//    Reader in = new StringReader("Ronn");
//    view = new HowToInvestViewImpl(in, out);
//    assertEquals("Ronn", view.getInput(""));
//    in = new StringReader("1");
//    view = new HowToInvestViewImpl(in, out);
//    assertEquals("1", view.getInput(""));
//    in = new StringReader("Ronn");
//    view = new HowToInvestViewImpl(in, out);
//    assertEquals("Ronn", view.getInput("Message"));
//    in = new StringReader("2.3");
//    view = new HowToInvestViewImpl(in, out);
//    assertEquals("2.3", view.getInput("Message"));
//    in = new StringReader("   ");
//    view = new HowToInvestViewImpl(in, out);
//    assertEquals("", view.getInput("Message"));
//    in = new StringReader("");
//    view = new HowToInvestViewImpl(in, out);
//    assertEquals("", view.getInput("Message"));
//  }
//
//  @Test
//  public void testGetBuyDetail() {
//    StringBuffer out = new StringBuffer();
//    String[] buyDetails = new String[]{"FB", "2000", "2018-10-10", "l"};
//    String stockName = buyDetails[0];
//    String amount = buyDetails[1];
//    String date = buyDetails[2];
//    String commision = buyDetails[3];
//    Reader in = new StringReader(stockName + " " + amount + " " + date + " " + commision);
//    view = new HowToInvestViewImpl(in, out);
//    String[] inputs = view.getShareBuyDetails();
//    for (int i = 0; i < inputs.length; i++) {
//      assertEquals(buyDetails[0], inputs[0]);
//    }
//  }
//
//  @Test
//  public void testGetBuyDetailIllegalValues() {
//    StringBuffer out = new StringBuffer();
//    String[] buyDetails = new String[]{"a", "b", "c", "d"};
//    String stockName = buyDetails[0];
//    String amount = buyDetails[1];
//    String date = buyDetails[2];
//    String commision = buyDetails[3];
//    Reader in = new StringReader(stockName + " " + amount + " " + date + " " + commision);
//    view = new HowToInvestViewImpl(in, out);
//    String[] inputs = view.getShareBuyDetails();
//    for (int i = 0; i < inputs.length; i++) {
//      assertEquals(buyDetails[0], inputs[0]);
//    }
//  }
//
//  @Test
//  public void checkViewWithInvalidReadableAppendable() {
//    /**
//     * Null Readable.
//     */
//    Reader stringReader;
//    String expected = "Invalid Readable or Appendable object";
//    StringWriter out = new StringWriter();
//    stringReader = null;
//    try {
//      view = new HowToInvestViewImpl(stringReader, out);
//      fail();
//    } catch (IllegalArgumentException ex) {
//      assertEquals(expected, ex.getMessage());
//    }
//
//    /**
//     * Null Appendable.
//     */
//    stringReader = new StringReader("");
//    out = null;
//    try {
//      view = new HowToInvestViewImpl(stringReader, out);
//      fail();
//    } catch (IllegalArgumentException ex) {
//      expected = "Invalid Readable or Appendable object";
//      assertEquals(expected, ex.getMessage());
//    }
//
//    /**
//     * Null Appendable and Readable.
//     */
//    stringReader = null;
//    out = null;
//    try {
//      view = new HowToInvestViewImpl(stringReader, out);
//      fail();
//    } catch (IllegalArgumentException ex) {
//      expected = "Invalid Readable or Appendable object";
//      assertEquals(expected, ex.getMessage());
//    }
//  }
//
//  @Test
//  public void checkViewAppendableException() throws IOException {
//    String s = "";
//    s += "3 1 2 AAPL 100 2018-10-10 y GOOG 20 2018-10-10 n q";
//    Reader stringReader = new StringReader(s);
//    File file = new File("Hello1.txt");
//    FileWriter out = new FileWriter(file);
//    StringBuilder log = new StringBuilder();
//    MockPortfolioManagerOne model = new MockPortfolioManagerOne(log);
//    IHowToInvestView view = new HowToInvestViewImpl(stringReader, out);
//    out.close();
//    try {
//      view.getInput("Hello");
//      fail();
//    } catch (IllegalStateException ex) {
//      assertEquals("IO exception has been encountered.", ex.getMessage());
//    }
//  }
//
//}
