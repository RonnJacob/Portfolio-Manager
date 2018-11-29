package howtoinvest.view;

import java.io.IOException;
import java.util.Scanner;

/**
 * The following class is an implementation of the IHowToInvestView interface which implements all
 * the necessary operations specified in the interface. This class would represent a console based
 * view implementation of the interview whereby the program and the necessary outputs/inputs and
 * messages would be displayed on a terminal/console.
 */
public class HowToInvestViewImpl implements IHowToInvestView {

  private final Appendable out;
  private Scanner scan;

  /**
   * Constructor that takes a readable and appendable object as parameters.s
   *
   * @param in  the readable object.
   * @param out the appendable object.
   */
  public HowToInvestViewImpl(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Invalid Readable or Appendable object");
    }
    this.out = out;
    scan = new Scanner(in);
  }

  @Override
  public void openHomeScreen() {
    String homeScreen = "\nWelcome to Portfolio Manager.\n";
    homeScreen += "1. Create new portfolio.\n";
    homeScreen += "2. Get existing portfolios.\n";
    homeScreen += "3. Enter portfolio.\n";
    homeScreen += "Enter the number for performing operation or q to quit application.";
    promptMessage(homeScreen);
  }

  @Override
  public void openPortfolioMenu() {
    String portfolioScreen = "\n1. Examine composition of portfolio\n";
    portfolioScreen += "2. Buy shares of a stock with portfolio.\n";
    portfolioScreen += "3. Get Cost Basis of portfolio\n";
    portfolioScreen += "4. Get Value of portfolio\n";
    portfolioScreen += "5. Invest on stocks in portfolio. \n";
    portfolioScreen += "6. Apply investment strategy. \n";
    portfolioScreen += "Enter the number for performing operation, r to return to the main "
            + "menu or q to quit the application.";
    promptMessage(portfolioScreen);
  }


  @Override
  public void openInvestmentMenu() {
    String investmentScreen = "\nInvestment Strategies \n";
    investmentScreen += "1. Invest on stocks in portfolio with equal weights.\n";
    investmentScreen += "2. Invest on stocks in portfolio with custom weights.\n";
    investmentScreen += "Enter the number for performing operation or r to return to the main "
            + "menu.";
    promptMessage(investmentScreen);
  }

  @Override
  public void openStrategyMenu() {
    String strategyMenu = "\nStrategy Menu\n";
    strategyMenu += "1. Apply Strategy\n";
    strategyMenu += "2. Modify strategy\n";
    strategyMenu += "Enter the number for performing operation or r to return to the main "
            + "menu.";
    promptMessage(strategyMenu);
  }

  @Override
  public void strategyModificationMenu() {
    String strategyModMenu = "\nStrategy Preferences\n";
    strategyModMenu += "1. Add a Stock to strategy. \n";
    strategyModMenu += "2. Modify weights of stocks for strategy.\n";
    strategyModMenu += "3. Modify amount of investment for strategy.\n";
    strategyModMenu += "4. Change frequency of investment for strategy.\n";
    strategyModMenu += "5. Modify start and end date for investment.\n";
    strategyModMenu += "Enter the number for performing operation or r to return to the main "
            + "menu.";
    promptMessage(strategyModMenu);
  }

  @Override
  public void quitManager() {
    promptMessage("Quitting manager");
  }

  @Override
  public void displayPortfolioComposition(String key, Double value) {
    promptMessage(value + " share(s) of " + key);
  }

  @Override
  public void displayPortfolioValue(String date, double stockValue) {
    promptMessage("The value of the portfolio as of " + date + " is $" + stockValue);
  }

  @Override
  public void displayPortfolioCostBasis(String date, double stockCostBasis) {
    promptMessage("The cost basis of the portfolio as of " + date + " is $" + stockCostBasis);

  }

  @Override
  public void displayList(int counter, String listItem, String listName) {
    if (counter == 1) {
      promptMessage("\nList of " + listName);
    }
    promptMessage(counter + ": " + listItem);
  }

  @Override
  public String getInput(String message) {
    if (!message.equals("")) {
      promptMessage(message);
    }
    if (scan.hasNext()) {
      String input = scan.next();
      if (input == null) {
        return "";
      }
      return input;
    } else {
      return "";
    }
  }

  @Override
  public void promptMessage(String message) {
    try {
      this.out.append(String.format("%s\n", message));
    } catch (IOException ex) {
      throw new IllegalStateException("IO exception has been encountered.");
    }
  }

  @Override
  public String[] getShareBuyDetails() {
    String[] buyDetails = new String[4];
    promptMessage("Enter stock symbol:\n");
    buyDetails[0] = scan.next();
    promptMessage("Enter amount for which shares are to be bought:\n");
    buyDetails[1] = scan.next();
    promptMessage("Enter date in format yyyy-mm-dd: \n");
    buyDetails[2] = scan.next();
    promptMessage("Enter the commission option for the transaction [l, m, h] \n");
    buyDetails[3] = scan.next();
    return buyDetails;
  }

}
