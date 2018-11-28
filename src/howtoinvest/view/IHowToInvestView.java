package howtoinvest.view;

/**
 * The following interface represents a view for the 'How-to-invest-for-dummies' application. The
 * following interface would handle receiving specific input if necessary and passing these inputs
 * to the controller to carry out necessary options.
 */
public interface IHowToInvestView {

  /**
   * Displays the home screen of the 'How-to-invest-for-dummies' application. The method would
   * display options for the user to create portfolios, getting the list of portfolios present and
   * entering a portfolio.
   */
  void openHomeScreen();

  /**
   * Displays the menu and options for a portfolio. The options for a portfolio would include buying
   * a share/stock, getting the cost basis/value of the portfolio as of a particular date and
   * getting the composition of the portfolio,
   */
  void openPortfolioMenu();

  void strategyModificationMenu();

  /**
   * Displays an appropriate message for quitting the application/program.
   */
  void quitManager();

  /**
   * Displays the composition of the portfolio.
   * @param shareName the name of the share present in the portfolio.
   * @param numberOfShares the number of the shares of a stock present in a portfolio.
   */
  void displayPortfolioComposition(String shareName, Double numberOfShares);

  /**
   * Displays the value of the selected portfolio.
   * @param date the date for which the portfolio value has to be shown.
   * @param stockValue the value of the portfolio as of the particular date.
   */
  void displayPortfolioValue(String date, double stockValue);

  /**
   * Displays the cost basis of the selected portfolio.
   * @param date the date for which the portfolio cost basis has to be shown.
   * @param stockCostBasis the cost basis of the portfolio as of the particular date.
   */
  void displayPortfolioCostBasis(String date, double stockCostBasis);

  /**
   * Displays the list of portfolios present in the portfolio manager.
   * @param counter the index of the portfolio.
   * @param portfolioName the name of the portfolio present in the portfolio manager.
   */
  void displayList(int counter, String portfolioName, String listName);

  /**
   * Returns an input string which would be a choice input by the user.
   * @return the input choice by the user.
   */
  String getInput(String message);

  /**
   * Displays a message relevant to the user.
   * @param message the message that is to be displayed.
   */
  void promptMessage(String message);

  /**
   * Returns the details of a share that the user wants to be in the form of a string array which
   * is of the order stock name (ticker symbol), the amount for which the shares are to be bought,
   * the date for which the share is to be bought on and the commission for the transaction.
   * @return a string array containing necessary details for a particular purchase of a stock.
   */
  String[] getShareBuyDetails();

  void openInvestmentMenu();

  void openStrategyMenu();
}
