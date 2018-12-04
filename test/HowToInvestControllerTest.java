import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import howtoinvest.controller.HowToInvestController;
import howtoinvest.controller.IHowToInvestController;
import howtoinvest.model.DollarCostAveragingStrategyManager;
import howtoinvest.model.IManager;
import howtoinvest.model.StockPortfolioManager;
import howtoinvest.view.HowToInvestViewImpl;
import howtoinvest.view.IHowToInvestView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * The following class contains tests pertaining to the HowToInvestController operations.
 */
public class HowToInvestControllerTest {

  private String welcome = "\nWelcome to Portfolio Manager.\n"
          + "1. Create new portfolio.\n"
          + "2. Get existing portfolios.\n"
          + "3. Enter portfolio.\n"
          + "4. Load portfolio.\n"
          + "Enter the number for performing operation or q to quit application.\n";

  private String portfolioScreen = "\n1. Examine composition of portfolio\n"
          + "2. Buy shares of a stock with portfolio.\n"
          + "3. Get Cost Basis of portfolio\n"
          + "4. Get Value of portfolio\n"
          + "5. Invest on stocks in portfolio. \n"
          + "6. Open Strategy Manager Menu. \n"
          +"7. Save Portfolio. \n"
          + "Enter the number for performing operation, r to return to the main "
          + "menu or q to quit the application.";

  private String strategyMenu = "\nStrategy Manager Menu\n"
          + "1. Create Strategy\n"
          + "2. Enter A Strategy\n"
          + "3. Display Strategies\n"
          +"4. Load Strategy\n"
          + "Enter the number for performing operation or r to return to the main menu.";


  @Test
  public void TestPortfolioManagerEmptyInput() {
    Reader stringReader = new StringReader("");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioManager = new StringBuilder();
    StringBuilder logStrategyManager = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioManager);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyManager);
    MockViewOne view = new MockViewOne(logView, stringReader, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    assertEquals("", logPortfolioManager.toString());
    assertEquals("", logStrategyManager.toString());
    expected = "Home Screen Opened.\n\nQuitting Manager.\n";
    assertEquals(expected, logView.toString());
    assertEquals("", out.toString());
  }

  @Test
  public void TestPortfolioManagerWhiteSpaces() {
    Reader stringReader = new StringReader("        ");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioManager = new StringBuilder();
    StringBuilder logStrategyManager = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioManager);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyManager);
    MockViewOne view = new MockViewOne(logView, stringReader, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    assertEquals("", logPortfolioManager.toString());
    assertEquals("", logStrategyManager.toString());
    expected = "Home Screen Opened.\n\nQuitting Manager.\n";
    assertEquals(expected, logView.toString());
    assertEquals("", out.toString());
  }

  @Test
  public void TestPortfolioManagerNullPortfolioManager() {
    Reader stringReader = new StringReader("        ");
    String expected = "Model or view cannot be a null.";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioManager = new StringBuilder();
    StringBuilder logStrategyManager = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioManager);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyManager);
    MockViewOne view = new MockViewOne(logView, stringReader, out);
    IHowToInvestController controller;
    try {
      controller = new HowToInvestController(null, model, strategyModel);
      controller.openPortfolioManager();
    } catch (IllegalArgumentException ex) {
      assertEquals(expected, ex.getMessage());
    }
    try {
      controller = new HowToInvestController(view, null, strategyModel);
      controller.openPortfolioManager();
    } catch (IllegalArgumentException ex) {
      assertEquals(expected, ex.getMessage());
    }
    try {
      controller = new HowToInvestController(view, model, null);
      controller.openPortfolioManager();
    } catch (IllegalArgumentException ex) {
      assertEquals(expected, ex.getMessage());
    }
    assertEquals("", logPortfolioManager.toString());
    assertEquals("", logStrategyManager.toString());
    assertEquals("", logView.toString());
    assertEquals("", out.toString());
  }


  @Test
  public void TestPortfolioManagerSelectingOptionToCreateAndTestCreation() {
    Reader in = new StringReader("1 testPortfolio");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioManager = new StringBuilder();
    StringBuilder logStrategyManager = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioManager);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyManager);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();


    expected = "Home Screen Opened.\nInput received : 1\n"
            + "Input received for: Enter the name of the portfolio to be created\n\n"
            + "Input received : testPortfolio\nPrompt: Portfolio testPortfolio has been created.\n"
            + "Prompt: Add more portfolios? (Y/N)\nHome Screen Opened.\n\nQuitting Manager.\n";

    assertEquals(expected, logView.toString());
    assertEquals("Create Portfolio Name testPortfolio\n", logPortfolioManager.toString());
  }


  @Test
  public void TestPortfolioManagerSelectingOptionToCreate() {
    Reader in = new StringReader("1 ");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioManager = new StringBuilder();
    StringBuilder logStrategyManager = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioManager);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyManager);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();


    expected = "Home Screen Opened.\nInput received : 1\n"
            + "Input received for: Enter the name of the portfolio to be created\n\n"
            + "Home Screen Opened.\n\nQuitting Manager.\n";

    assertEquals(expected, logView.toString());
    assertEquals("", logPortfolioManager.toString());
  }

  @Test
  public void TestPortfolioManagerSelectingOptionToCreateMultiple() {
    Reader in = new StringReader("1 P1 y P2 n q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();


    expected = "Home Screen Opened.\nInput received : 1\n"
            + "Input received for: Enter the name of the portfolio to be created\n\n"
            + "Input received : P1\nPrompt: Portfolio P1 has been created.\n"
            + "Prompt: Add more portfolios? (Y/N)\nInput received : y\n"
            + "Input received for: Enter the name of the portfolio to be created\n\n"
            + "Input received : P2\nPrompt: Portfolio P2 has been created.\n"
            + "Prompt: Add more portfolios? (Y/N)\nInput received : n\n"
            + "Home Screen Opened.\nInput received : q\n\nQuitting Manager.\n";

    assertEquals(expected, logView.toString());
    assertEquals("Create Portfolio Name P1\nCreate Portfolio Name P2\n",
            logPortfolioModel.toString());
  }

  @Test
  public void TestPortfolioManagerSelectingOptionToGetAddedPortfolios() {
    Reader in = new StringReader("1 P1 y P2 n 2 q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();

    expected = "Home Screen Opened.\nInput received : 1\nInput received for: Enter the name of the "
            + "portfolio to be created\n\nInput received : P1\n"
            + "Prompt: Portfolio P1 has been created.\nPrompt: Add more portfolios? (Y/N)\n"
            + "Input received : y\nInput received for: Enter the name of the portfolio to "
            + "be created"
            + "\n\nInput received : P2\nPrompt: Portfolio P2 has been created.\nPrompt: Add more "
            + "portfolios? (Y/N)\nInput received : n\nHome Screen Opened.\nInput received : 2\n"
            + "Home Screen Opened.\nInput received : q\n\nQuitting Manager.\n";

    assertEquals(expected, logView.toString());
    assertEquals("Create Portfolio Name P1\nCreate Portfolio Name P2\nGet All Portfolios"
            + "\n", logPortfolioModel.toString());
  }

  @Test
  public void TestPortfolioManagerSelectingOptionToGetAllPortfolios() {
    Reader in = new StringReader("2 q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();

    expected = "Home Screen Opened.\nInput received : 2\nHome Screen Opened.\n"
            + "Input received : q\n\nQuitting Manager.\n";

    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\n", logPortfolioModel.toString());
  }

  @Test
  public void TestPortfolioManagerSelectingOptionToEnterPortfolio() {
    Reader in = new StringReader("3 1 q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();

    expected = "Home Screen Opened.\nInput received : 3\nInput received for: \n"
            + "Enter index of Portfolio to open.\nInput received : 1\nPortfolio Screen Opened.\n"
            + "Input received : q\n\nQuitting Manager.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\nGet Portfolio 1\n",
            logPortfolioModel.toString());
  }


  @Test
  public void TestPortfolioManagerSelectingOptionToMultiplePortfolioEnter() {
    Reader in = new StringReader("3 1 r 3 2 r 3 4 q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : 3\nInput received for: \nEnter index of "
            + "Portfolio to open.\nInput received : 1\nPortfolio Screen Opened.\nInput received "
            + ": r\nHome Screen Opened.\nInput received : 3\nInput received for: \n"
            + "Enter index of Portfolio to open.\nInput received : 2\nPortfolio Screen Opened.\n"
            + "Input received : r\nHome Screen Opened.\nInput received : 3\nInput received for: \n"
            + "Enter index of Portfolio to open.\nInput received : 4\nPortfolio Screen Opened.\n"
            + "Input received : q\n\nQuitting Manager.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\nGet Portfolio 1\nGet All "
                    + "Portfolios\nGet Portfolio 2\nGet Portfolio 2\nGet All Portfolios\n"
                    + "Get Portfolio 4\nGet Portfolio 4\n",
            logPortfolioModel.toString());
  }


  @Test
  public void TestPortfolioManagerQuittingFromPortfolioMenu() {
    Reader in = new StringReader("3 1 q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : 3\nInput received for: \n"
            + "Enter index of Portfolio to open.\nInput received : 1\nPortfolio Screen Opened.\n"
            + "Input received : q\n\nQuitting Manager.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\nGet Portfolio 1\n",
            logPortfolioModel.toString());
  }


  @Test
  public void TestPortfolioManagerSelectingOptionToMultiplePortfolioExamineComposition() {
    Reader in = new StringReader("3 1 1 q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : 3\nInput received for: \n"
            + "Enter index of Portfolio to open.\nInput received : 1\nPortfolio Screen Opened.\n"
            + "Input received : 1\nInput received for: Enter date in format yyyy-mm-dd: \n"
            + "\nInput received : q\nPortfolio Screen Opened.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\nGet Portfolio 1\nGet Portfolio 1\n"
                    + "Get composition for q\n",
            logPortfolioModel.toString());
  }

  @Test
  public void TestPortfolioManagerSelectingOptionToMultiplePortfolioBuyShare() {
    Reader in = new StringReader("3 1 2 AAPL 1000 2018-10-10 l n q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : 3\nInput received for: \n"
            + "Enter index of Portfolio to open.\nInput received : 1\nPortfolio Screen Opened.\n"
            + "Input received : 2\nBuy details queried. AAPL 1000 2018-10-10 l\n"
            + "Prompt: 100.0 share(s) of AAPL bought on 2018-10-10\nPrompt: Buy more shares? (Y/N)"
            + "\nInput received : n\nPortfolio Screen Opened.\nInput received : q\n"
            + "\nQuitting Manager.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\nGet Portfolio 1\nAdd stock AAPL "
            + "for 1000.0 on "
            + "2018-10-10 with 100.0\n", logPortfolioModel.toString());
  }


  @Test
  public void TestPortfolioManagerSelectingOptionToPortfolioGetCostBasis() {
    Reader in = new StringReader("3 1 3 2018-10-10 q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : 3\nInput received for: \n"
            + "Enter index of Portfolio to open.\nInput received : 1\nPortfolio Screen Opened.\n"
            + "Input received : 3\nInput received for: Enter date in format yyyy-mm-dd: \n\n"
            + "Input received : 2018-10-10\nCost Basis displayed for 0.0 on 2018-10-10\n"
            + "Portfolio Screen Opened.\nInput received : q\n\nQuitting Manager.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\nGet Portfolio 1\n"
                    + "Get cost basis for 2018-10-10\n",
            logPortfolioModel.toString());
  }

  @Test
  public void TestPortfolioManagerSelectingOptionToPortfolioGetValue() {
    Reader in = new StringReader("3 1 4 2018-10-10 q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : 3\nInput received for: \n"
            + "Enter index of Portfolio to open.\nInput received : 1\nPortfolio Screen Opened.\n"
            + "Input received : 4\nInput received for: Enter date in format yyyy-mm-dd: \n\n"
            + "Input received : 2018-10-10\nValue displayed for 0.0 on 2018-10-10\n"
            + "Portfolio Screen Opened.\nInput received : q\n\nQuitting Manager.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\nGet Portfolio 1\n"
                    + "Get value for 2018-10-10\n",
            logPortfolioModel.toString());
  }

  @Test
  public void TestPortfolioManagerSelectingOptionToInvestStocksStrategy() {
    Reader in = new StringReader("3 1 5 r q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : 3\nInput received for: \n"
            + "Enter index of Portfolio to open.\nInput received : 1\n"
            + "Portfolio Screen Opened.\nInput received : 5\nInvestment Menu Screen Opened. \n"
            + "Input received : r\nPortfolio Screen Opened.\nInput received : q\n"
            + "\nQuitting Manager.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\nGet Portfolio 1\n",
            logPortfolioModel.toString());
  }


  @Test
  public void TestPortfolioManagerSelectingOptionToInvestStocksStrategyEqualWeights() {
    Reader in = new StringReader("3 1 5 1 2000 2018-10-13 l r q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : 3\nInput received for: \n"
            + "Enter index of Portfolio to open.\nInput received : 1\nPortfolio Screen Opened.\n"
            + "Input received : 5\nInvestment Menu Screen Opened. \nInput received : 1\n"
            + "Input received for: Enter amount to invest: \n\nInput received : 2000\n"
            + "Input received for: Enter date in format yyyy-mm-dd: \n\nInput received : 2018-10-13"
            + "\nInput received for: Enter the commission option for the transaction [l, m, h] or"
            + " enter custom commission value \n"
            + "\nInput received : l\nInvestment Menu "
            + "Screen Opened. \nInput received : r\n"
            + "Portfolio Screen Opened.\nInput received : q\n\nQuitting Manager.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\nGet Portfolio 1\n"
                    + "Invest 2000.0 true 2018-10-13 100.0\n",
            logPortfolioModel.toString());
  }

  @Test
  public void TestPortfolioManagerSelectingOptionToInvestStocksStrategyUnqualWeights() {
    Reader in = new StringReader("3 1 2 FB 200 2018-10-13 l n 5 2 2000 2018-11-13 100 r q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : 3\nInput received for: \n"
            + "Enter index of Portfolio to open.\nInput received : 1\nPortfolio Screen Opened.\n"
            + "Input received : 2\nBuy details queried. FB 200 2018-10-13 l\n"
            + "Prompt: 100.0 share(s) of FB bought on 2018-10-13\nPrompt: Buy more shares? (Y/N)\n"
            + "Input received : n\nPortfolio Screen Opened.\nInput received : 5\n"
            + "Investment Menu Screen Opened. \nInput received : 2\nInput received for: Enter "
            + "amount to invest: \n\nInput received : 2000\n"
            + "Input received for: Enter date in format yyyy-mm-dd: \n\n"
            + "Input received : 2018-11-13\nInput received for: Enter the commission option for the"
            + " transaction [l, m, h] or enter custom commission value \n"
            + "\nInput received : 100\nPrompt: No stocks present in the portfolio.\n"
            + "Investment Menu Screen Opened. \nInput received : r\nPortfolio Screen Opened.\n"
            + "Input received : q\n\nQuitting Manager.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\nGet Portfolio 1\nAdd stock FB "
                    + "for 200.0 on 2018-10-13 with 100.0\n"
                    + "Get composition for 2018-11-13\n",
            logPortfolioModel.toString());
  }

  @Test
  public void TestPortfolioManagerSelectingOptionToApplyStrategy() {
    Reader in = new StringReader("3 1 6 2 r q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : 3\nInput received for: \n"
            + "Enter index of Portfolio to open.\nInput received : 1\n"
            + "Portfolio Screen Opened.\nInput received : 6\nStrategy Manager Menu Opened\n"
            + "Input received : 2\nInput received for: \nEnter index of strategy to apply.\n"
            + "\nInput received : r\nStrategy Manager Menu Opened\nInput received : q\n"
            + "\nQuitting Manager.\nPortfolio Screen Opened.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\nGet Portfolio 1\n",
            logPortfolioModel.toString());
  }

  @Test
  public void TestPortfolioManagerNull() {
    Reader in = new StringReader("q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : q\n\nQuitting Manager.\n";
    assertEquals(expected, logView.toString());
    assertEquals("", logPortfolioModel.toString());
  }

  @Test
  public void TestPortfolioManagerInvalidPortfolio() {
    Reader in = new StringReader("q");
    String expected = "Model or view cannot be a null.";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    try {

      IHowToInvestController controller = new HowToInvestController(null, model, strategyModel);
      controller.openPortfolioManager();
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals(expected, ex.getMessage());
    }
  }


  @Test
  public void TestPortfolioManagerInvalidPortfolioIndex() {
    Reader in = new StringReader("3 8 q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : 3\nInput received for: \n"
            + "Enter index of Portfolio to open.\nInput received : 8\n"
            + "Portfolio Screen Opened.\nInput received : q\n\nQuitting Manager.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 8\nGet Portfolio 8\n",
            logPortfolioModel.toString());
  }

  @Test
  public void TestPortfolioManagerInvalidInputForStockHomeOptions() {
    Reader in = new StringReader("3 1 12 q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : 3\nInput received for: \n"
            + "Enter index of Portfolio to open.\nInput received : 1\nPortfolio Screen Opened.\n"
            + "Input received : 12\nPrompt: Invalid input. Please enter input again.\n"
            + "Input received : q\n\nQuitting Manager.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\nGet Portfolio 1\n",
            logPortfolioModel.toString());
  }

  @Test
  public void TestPortfolioManagerInvalidInputForStockHomeNegative() {
    Reader in = new StringReader("3 1 -4 q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : 3\nInput received for: \n"
            + "Enter index of Portfolio to open.\nInput received : 1\n"
            + "Portfolio Screen Opened.\nInput received : -4\nPrompt: Invalid input. Please enter "
            + "input again.\nInput received : q\n\nQuitting Manager.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\nGet Portfolio 1\n",
            logPortfolioModel.toString());
  }

  @Test
  public void TestPortfolioManagerInvalidInputForStockHomeAlphabets() {
    Reader in = new StringReader("3 1 asdaaf q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\n"
            + "Input received : 3\n"
            + "Input received for: \n"
            + "Enter index of Portfolio to open.\n"
            + "Input received : 1\n"
            + "Portfolio Screen Opened.\n"
            + "Input received : asdaaf\n"
            + "Prompt: Invalid input. Please enter input again.\n"
            + "Input received : q\n"
            + "\nQuitting Manager.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\nGet Portfolio 1\n",
            logPortfolioModel.toString());
  }


  @Test
  public void TestPortfolioManagerCreatingMoreThanOneShare() {
    Reader in = new StringReader("3 1 2 AAPL 100 2018-10-10 l y GOOG 20 2018-10-10 m n "
            + "q \n");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\n"
            + "Input received : 3\n"
            + "Input received for: \n"
            + "Enter index of Portfolio to open.\n"
            + "Input received : 1\n"
            + "Portfolio Screen Opened.\n"
            + "Input received : 2\n"
            + "Buy details queried. AAPL 100 2018-10-10 l\n"
            + "Prompt: 100.0 share(s) of AAPL bought on 2018-10-10\n"
            + "Prompt: Buy more shares? (Y/N)\n"
            + "Input received : y\n"
            + "Buy details queried. GOOG 20 2018-10-10 m\n"
            + "Prompt: 100.0 share(s) of GOOG bought on 2018-10-10\n"
            + "Prompt: Buy more shares? (Y/N)\n"
            + "Input received : n\n"
            + "Portfolio Screen Opened.\n"
            + "Input received : q\n"
            + "\nQuitting Manager.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\nGet Portfolio 1\nAdd stock AAPL for"
                    + " 100.0 on 2018-10-10 with 100.0\nAdd stock GOOG for 20.0 on 2018-10-10 with"
                    + " 100.0\n",
            logPortfolioModel.toString());
  }


  @Test
  public void TestPortfolioManagerOptionOneDoesNotEndWithQNoName() {
    Reader in = new StringReader("1");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : 1\nInput received for: Enter the name of "
            + "the portfolio to be created\n\nHome Screen Opened.\n\nQuitting Manager.\n";
    assertEquals(expected, logView.toString());
    assertEquals("", logPortfolioModel.toString());
  }

  @Test
  public void TestPortfolioManagerOptionTwoDoesNotEndWithQNoName() {
    Reader in = new StringReader("2");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : 2\nHome Screen Opened.\n\nQuitting Manager"
            + ".\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\n", logPortfolioModel.toString());
  }


  @Test
  public void TestPortfolioManagerOptionThreeDoesNotEndWithQNoName() {
    Reader in = new StringReader("3");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : 3\nInput received for: \nEnter index of "
            + "Portfolio to open.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\n", logPortfolioModel.toString());
  }

  @Test
  public void TestPortfolioManagerReturningFromStockMenu() {


    Reader in = new StringReader("3 1 r q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : 3\nInput received for: \n"
            + "Enter index of Portfolio to open.\nInput received : 1\n"
            + "Portfolio Screen Opened.\nInput received : r\nHome Screen Opened.\n"
            + "Input received : q\n\nQuitting Manager.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\nGet Portfolio 1\n",
            logPortfolioModel.toString());
  }

  @Test
  public void TestControllerApplyStrategyOption() {
    Reader in = new StringReader("3 1 6 2 1 r q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : 3\n"
            + "Input received for: \nEnter index of Portfolio to open.\n"
            + "Input received : 1\nPortfolio Screen Opened.\n"
            + "Input received : 6\n"
            + "Strategy Manager Menu Opened\n"
            + "Input received : 2\nInput received for: \n"
            + "Enter index of strategy to apply.\n\n"
            + "Input received : 1\nStrategy Menu Screen opened. \nInput received : r\n"
            + "Strategy Manager Menu Opened\nInput received : q\n\nQuitting Manager.\n"
            + "Portfolio Screen Opened.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\nGet Portfolio 1\n",
            logPortfolioModel.toString());
  }

  @Test
  public void TestControllerApplyStrategy() {
    Reader in = new StringReader("3 1 6 2 1 1 l r q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : 3\nInput received for: \n"
            + "Enter index of Portfolio to open.\nInput received : 1\nPortfolio Screen Opened.\n"
            + "Input received : 6\n"
            + "Strategy Manager Menu Opened\n"
            + "Input received : 2\nInput received for: \nEnter index of strategy to apply.\n"
            + "\nInput received : 1\nStrategy Menu Screen opened. \n"
            + "Input received : 1\nInput received for: Enter the commission option for the "
            + "transaction [l, m, h] or enter custom commission value \n"
            + "\nInput received : l\nPrompt: No stocks in portfolio to apply strategy.\n"
            + "Strategy Menu Screen opened. \nInput received : r\nStrategy Manager Menu Opened\n"
            + "Input received : q\n\nQuitting Manager.\nPortfolio Screen Opened.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\n" + "Get Portfolio 1\n",
            logPortfolioModel.toString());
    assertEquals("All Portfolios\nAll Portfolios\nGet Portfolio 1\nApply strategy "
                    + "with commision 100.0", logStrategyModel.toString());
  }


  @Test
  public void TestControllerStrategyModifyWeights() {
    Reader in = new StringReader("3 1 6 2 1 2 2 r r r q q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\n"
            + "Input received : 3\n"
            + "Input received for: \n"
            + "Enter index of Portfolio to open.\n"
            + "Input received : 1\n"
            + "Portfolio Screen Opened.\n"
            + "Input received : 6\n"
            + "Strategy Manager Menu Opened\n"
            + "Input received : 2\n"
            + "Input received for: \n"
            + "Enter index of strategy to apply.\n"
            + "\n"
            + "Input received : 1\n"
            + "Strategy Menu Screen opened. \n"
            + "Input received : 2\n"
            + "Strategy modification screen Opened.\n"
            + "Input received : 2\n"
            + "Strategy modification screen Opened.\n"
            + "Input received : r\n"
            + "Strategy Menu Screen opened. \n"
            + "Input received : r\n"
            + "Strategy Manager Menu Opened\n"
            + "Input received : r\n"
            + "Portfolio Screen Opened.\n"
            + "Input received : q\n"
            + "\n"
            + "Quitting Manager.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\nGet Portfolio 1\n",
            logPortfolioModel.toString());
    assertEquals("All Portfolios\nAll Portfolios\nGet Portfolio 1\nSetting weights\n\n",
            logStrategyModel.toString());
  }

  @Test
  public void TestControllerStrategyModifyAmount() {
    Reader in = new StringReader("3 1 6 2 1 2 3 2000 r r q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\n"
            + "Input received : 3\n"
            + "Input received for: \n"
            + "Enter index of Portfolio to open.\n"
            + "Input received : 1\n"
            + "Portfolio Screen Opened.\n"
            + "Input received : 6\nStrategy Manager Menu Opened\nInput received : 2\n"
            + "Input received for: \n"
            + "Enter index of strategy to apply.\n"
            + "\nInput received : 1\n"
            + "Strategy Menu Screen opened. \nInput received : 2\n"
            + "Strategy modification screen Opened.\nInput received : 3\n"
            + "Input received for: Enter new amount for investing of strategy.\n"
            + "\nInput received : 2000\nStrategy modification screen Opened.\n"
            + "Input received : r\nStrategy Menu Screen opened. \nInput received : r\n"
            + "Strategy Manager Menu Opened\nInput received : q\n\nQuitting Manager.\nPortfolio "
            + "Screen Opened.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\n"
            + "Get Portfolio 1\n", logPortfolioModel.toString());
    assertEquals("All Portfolios\nAll Portfolios\nGet Portfolio 1\nChange amount to "
                    + "2000.0\n",
            logStrategyModel.toString());
  }

  @Test
  public void TestControllerStrategyModifyFrequency() {
    Reader in = new StringReader("3 1 6 2 1 2 4 100 r r q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : 3\n"
            + "Input received for: \nEnter index of Portfolio to open.\n"
            + "Input received : 1\nPortfolio Screen Opened.\n"
            + "Input received : 6\nStrategy Manager Menu Opened\n"
            + "Input received : 2\nInput received for: \n"
            + "Enter index of strategy to apply.\n\nInput received : 1\n"
            + "Strategy Menu Screen opened. \nInput received : 2\n"
            + "Strategy modification screen Opened.\nInput received : 4\n"
            + "Input received for: Enter frequency in number of days.\n\nInput received : 100\n"
            + "Strategy modification screen Opened.\nInput received : r\n"
            + "Strategy Menu Screen opened. \nInput received : r\n"
            + "Strategy Manager Menu Opened\nInput received : q\n"
            + "\nQuitting Manager.\nPortfolio Screen Opened.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\nGet Portfolio 1\n",
            logPortfolioModel.toString());
    assertEquals("All Portfolios\nAll Portfolios\nGet Portfolio 1\nChange frequency to "
                    + "100 days.\n",
            logStrategyModel.toString());
  }

  @Test
  public void TestControllerStrategyModifyRange() {
    Reader in = new StringReader("3 1 6 2 1 2 5 2018-10-10 2018-12-12 r r q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : 3\nInput received for: \n"
            + "Enter index of Portfolio to open.\nInput received : 1\nPortfolio Screen Opened.\n"
            + "Input received : 6\nStrategy Manager Menu Opened\n"
            + "Input received : 2\nInput received for: \nEnter index of strategy to apply.\n"
            + "\nInput received : 1\nStrategy Menu Screen opened. \nInput received : 2\n"
            + "Strategy modification screen Opened.\n"
            + "Input received : 5\nInput received for: Enter start date for investing of strategy"
            + ".\n\nInput received : 2018-10-10\nInput received for: Enter end date for investing"
            + " of strategy.\n\nInput received : 2018-12-12\nStrategy modification screen Opened.\n"
            + "Input received : r\nStrategy Menu Screen opened. \nInput received : r\nStrategy "
            + "Manager Menu Opened\nInput received : q\n\nQuitting Manager.\nPortfolio Screen"
            + " Opened.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\nGet Portfolio 1\n",
            logPortfolioModel.toString());
    assertEquals("All Portfolios\nAll Portfolios\nGet Portfolio 1\nChange date from "
            + "2018-10-10 to 2018-12-12 \n", logStrategyModel.toString());
  }

  @Test
  public void TestControllerStrategyAddStock() {
    Reader in = new StringReader("3 1 6 2 1 2 1 FB n r r q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : 3\nInput received for: \n"
            + "Enter index of Portfolio to open.\nInput received : 1\nPortfolio Screen Opened.\n"
            + "Input received : 6\nStrategy Manager Menu Opened\nInput received : 2\nInput received"
            + " for: \nEnter index of strategy to apply.\n"
            + "\nInput received : 1\nStrategy Menu Screen opened. \nInput received : 2\n"
            + "Strategy modification screen Opened.\nInput received : 1\n"
            + "Input received for: Enter a stock name (ticker symbol) to add to the strategy: \n"
            + "\nInput received : FB\nPrompt: Add more stocks? (Y/N)\n"
            + "Input received : n\nStrategy modification screen Opened.\n"
            + "Input received : r\nStrategy Menu Screen opened. \nInput received : r\nStrategy"
            + " Manager Menu Opened\nInput received : q\n\nQuitting Manager.\nPortfolio Screen"
            + " Opened.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\nGet Portfolio 1\n",
            logPortfolioModel.toString());
    assertEquals("All Portfolios\nAll Portfolios\nGet Portfolio 1\n"
            + "Add stock to strategy.\n", logStrategyModel.toString());
  }

  @Test
  public void TestControllerStrategyAddStocks() {
    Reader in = new StringReader("3 1 6 2 1 2 1 FB y MSFT n r r q");
    String expected = "";
    StringWriter out = new StringWriter();
    StringBuilder logPortfolioModel = new StringBuilder();
    StringBuilder logStrategyModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    MockPortfolioManagerOne model = new MockPortfolioManagerOne(logPortfolioModel);
    MockStrategyManagerOne strategyModel = new MockStrategyManagerOne(logStrategyModel);
    IHowToInvestView view = new MockViewOne(logView, in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    expected = "Home Screen Opened.\nInput received : 3\nInput received for: \n"
            + "Enter index of Portfolio to open.\nInput received : 1\nPortfolio Screen Opened.\n"
            + "Input received : 6\nStrategy Manager Menu Opened\nInput received : 2\nInput received"
            + " for: \nEnter index of strategy to apply.\n"
            + "\nInput received : 1\nStrategy Menu Screen opened. \nInput received : 2\n"
            + "Strategy modification screen Opened.\nInput received : 1\n"
            + "Input received for: Enter a stock name (ticker symbol) to add to the strategy: \n"
            + "\nInput received : FB\nPrompt: Add more stocks? (Y/N)\n"
            + "Input received : y\nInput received for: Enter a stock name (ticker symbol) to add "
            + "to the strategy: \n\nInput received : MSFT\nPrompt: Add more stocks? (Y/N)\n"
            + "Input received : n\nStrategy modification screen Opened.\n"
            + "Input received : r\nStrategy Menu Screen opened. \nInput received : r\nStrategy "
            + "Manager Menu Opened\nInput received : q\n\nQuitting Manager.\nPortfolio Screen "
            + "Opened.\n";
    assertEquals(expected, logView.toString());
    assertEquals("Get All Portfolios\nGet Portfolio 1\nGet Portfolio 1\n",
            logPortfolioModel.toString());
    assertEquals("All Portfolios\nAll Portfolios\nGet Portfolio 1\n"
            + "Add stocks to strategy.\n", logStrategyModel.toString());
  }

  @Test
  public void TestControllerModelRegressionTest() {
    Reader in = new StringReader("3 1 2 FB 1000 2018-10-10 l n 1 2018-10-10 q");
    StringWriter out = new StringWriter();
    IManager model = new StockPortfolioManager();
    IManager strategyModel = new DollarCostAveragingStrategyManager();
    IHowToInvestView view = new HowToInvestViewImpl(in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    String listOfPortfolios = "\nList of Portfolios\n1: Default StockPortfolio\n";
    String queries = "\nEnter stock symbol:\n\nEnter amount for which shares are to be bought:\n"
            + "\nEnter date in format yyyy-mm-dd: \n\nEnter the commission option for the "
            + "transaction [l, m, h] or enter a custom commission value \n\n"
            + "9.523809523809524 share(s) of FB bought on 2018-10-10\n"
            + "\nBuy more shares? (Y/N)\n\n";
    String stockValue = "\nEnter date in format yyyy-mm-dd: \n\n9.523809523809524 share(s) "
            + "of FB\n";
    String expected = welcome + listOfPortfolios + "\nEnter index of Portfolio to open.\n"
            + portfolioScreen + queries + portfolioScreen + stockValue + portfolioScreen
            + "\nQuitting manager\n";
    assertEquals(expected, out.toString());
  }


  @Test
  public void TestControllerModelIntegration() {
    Reader in = new StringReader("3 1 2 FB 1000 2018-10-10 l n 1 2018-10-10 6 2 1 1 l r q");
    StringWriter out = new StringWriter();
    IManager model = new StockPortfolioManager();
    IManager strategyModel = new DollarCostAveragingStrategyManager();
    IHowToInvestView view = new HowToInvestViewImpl(in, out);
    IHowToInvestController controller = new HowToInvestController(view, model, strategyModel);
    controller.openPortfolioManager();
    String listOfPortfolios = "\nList of Portfolios\n1: Default StockPortfolio\n";
    String queries = "\nEnter stock symbol:\n\nEnter amount for which shares are to be bought:\n"
            + "\nEnter date in format yyyy-mm-dd: \n\nEnter the commission option for the "
            + "transaction [l, m, h] or enter a custom commission value \n\n9.523809523809524 "
            + "share(s) of FB bought on 2018-10-10\n"
            + "\nBuy more shares? (Y/N)\n\n";
    String stockValue = "\nEnter date in format yyyy-mm-dd: \n\n9.523809523809524 share(s) "
            + "of FB\n";
    String strategyMenu = "\n\nList of Strategies\n1: Default DollarCostAveraging\n"
            +this.strategyMenu+"\n\nList of Strategies\n1: Default DollarCostAveraging\n\n"
            + "Enter index of strategy to apply.\n\n\n"
            + "Strategy Menu\n1. Apply Strategy\n2. Modify strategy\n"
            + "3. Display Stocks\n"
            +"4. Save Strategy\n"
            + "Enter the number for performing operation or r to return to the main menu.\n"
            + "Enter the commission option for the transaction [l, m, h] or enter "
            + "custom commission value \n";
    String text = "\n\n2018-01-01\n" + "\n" + "20.00 shares of MSFT bought.\n"
            + "\n" + "\n" + "2018-01-31\n" + "\n" + "20.00 shares of MSFT bought.\n" + "\n"
            + "\n" + "2018-03-02\n" + "\n" + "19.80 shares of MSFT bought.\n" + "\n" + "\n"
            + "2018-04-01\n" + "\n" + "19.80 shares of MSFT bought.\n" + "\n" + "\n"
            + "2018-05-01\n" + "\n" + "19.80 shares of MSFT bought.\n" + "\n" + "\n"
            + "2018-05-31\n" + "\n" + "19.80 shares of MSFT bought.\n" + "\n" + "\n"
            + "2018-06-30\n" + "\n" + "19.80 shares of MSFT bought.\n" + "\n" + "\n"
            + "2018-07-30\n" + "\n" + "19.42 shares of MSFT bought.\n" + "\n" + "\n"
            + "2018-08-29\n" + "\n" + "19.42 shares of MSFT bought.\n" + "\n" + "\n"
            + "2018-09-28\n" + "\n" + "19.42 shares of MSFT bought.\n" + "\n" + "\n"
            + "2018-10-28\n" + "\n" + "19.42 shares of MSFT bought." + "\n\n\n" + "Strategy Menu\n"
            + "1. Apply Strategy\n" + "2. Modify strategy\n"
            + "3. Display Stocks\n4. Save Strategy\n"
            + "Enter the number for performing operation or r to return to the main menu.\n";
    String strategyManagerMenu = "\n" +
            "Strategy Manager Menu\n" +
            "1. Create Strategy\n" +
            "2. Enter A Strategy\n" +
            "3. Display Strategies\n" +
            "4. Load Strategy\n"+
            "Enter the number for performing operation or r to return to the main menu.\n" +
            "Quitting manager\n";
    String expected = welcome + listOfPortfolios + "\nEnter index of Portfolio to open.\n"
            + portfolioScreen + queries + portfolioScreen + stockValue + portfolioScreen
            + strategyMenu + text +strategyManagerMenu+ portfolioScreen+"\n";
    assertEquals(expected, out.toString());
  }


}