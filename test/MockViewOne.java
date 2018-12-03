import java.util.Scanner;

import howtoinvest.view.IHowToInvestView;

/**
 * This class is a mock class for testing the view implementation and HowToInvestViewImpl tests. The
 * mock view has a constructor which would take in a StringBuilder variable which would behave as a
 * log. This log would contain the list of actions that were taken with the view with the provided
 * input for the controller. The log would be used to test and verify the controller
 * implementations. The following view implements the IHowToInvestView interface and implements all
 * the methods in the interface. The log appends the input for methods which have inputs for the
 * controller and this verifies that the input given by the controller is the expected input given
 * to the view. Note that each method called would log that the method was called and display
 * parameters along within the log message if parameters were provided.
 */
public class MockViewOne implements IHowToInvestView {

  private StringBuilder log;
  private Scanner scan;

  /**
   * Constructor that takes in a log, readable object and appendable object.

   * @param log the log string.
   * @param in the readable object
   * @param out the appendable object
   */
  public MockViewOne(StringBuilder log, Readable in, Appendable out) {
    this.log = log;
    scan = new Scanner(in);
  }


  @Override
  public void openHomeScreen() {
    this.log.append("Home Screen Opened.\n");
  }

  @Override
  public void openPortfolioMenu() {
    this.log.append("Portfolio Screen Opened.\n");
  }

  @Override
  public void strategyModificationMenu() {
    this.log.append("Strategy modification screen Opened.\n");
  }

  @Override
  public void quitManager() {
    this.log.append("\nQuitting Manager.\n");
  }

  @Override
  public void displayPortfolioComposition(String shareName, Double numberOfShares) {
    this.log.append("Composition " + shareName + " " + numberOfShares + "\n");
  }

  @Override
  public void displayPortfolioValue(String date, double stockValue) {
    this.log.append("Value displayed for " + stockValue + " on " + date + "\n");
  }

  @Override
  public void displayPortfolioCostBasis(String date, double stockCostBasis) {
    this.log.append("Cost Basis displayed for " + stockCostBasis + " on " + date + "\n");
  }


  @Override
  public void displayList(int counter, String portfolioName, String listName) {
    this.log.append("List of  " + listName + " with item #" + counter + " " + portfolioName
            + " \n");
  }

  @Override
  public String getInput(String message) {
    if (!message.equals("")) {
      this.log.append("Input received for: " + message + "\n");
    }
    if (scan.hasNext()) {
      String input = scan.next();
      if (input == null) {
        return "";
      }
      this.log.append("Input received : " + input + "\n");
      return input;
    } else {
      return "";
    }
  }

  @Override
  public void promptMessage(String message) {
    this.log.append("Prompt: " + message);
  }

  @Override
  public String[] getShareBuyDetails() {
    String[] buyDetails = new String[4];
    buyDetails[0] = scan.next();
    buyDetails[1] = scan.next();
    buyDetails[2] = scan.next();
    buyDetails[3] = scan.next();
    this.log.append("Buy details queried.");
    for (int i = 0; i < buyDetails.length; i++) {
      this.log.append(" " + buyDetails[i]);
    }
    this.log.append("\n");
    return buyDetails;
  }

  @Override
  public void openInvestmentMenu() {
    this.log.append("Investment Menu Screen Opened. \n");
  }

  @Override
  public void openStrategyMenu() {
    this.log.append("Strategy Menu Screen opened. \n");

  }

  @Override
  public void openStrategyManagerMenu() {
    this.log.append("Strategy Manager Menu Opened\n");
  }
}
