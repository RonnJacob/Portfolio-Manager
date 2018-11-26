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

  /**
   * Displays an appropriate message for quitting the application/program.
   */
  void quitManager();

  /**
   * Displays message specifying that a portfolio with a particular name has been created.
   *
   * @param name the name of the portfolio that has been successfully been created.
   */
  void addedPortfolio(String name);

  /**
   * Displays a message saying that x number of shares of stock with a particular ticker symbol y
   * worth a particular amount has been bought on z date.
   * @param name the ticker symbol of the stock of the company.
   * @param numberOfShares the number of shares that have been bought.
   * @param date the date on which the share is bought.
   */
  void displayTransaction(String name, double numberOfShares, String date);

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
  void displayListOfPortfolios(int counter, String portfolioName);

  /**
   * Returns an input string which would be a choice input by the user.
   * @return the input choice by the user.
   */
  String getInputChoice();

  /**
   * Returns the name of the portfolio that is to be added to the portfolio manager.
   * @return the name of the portfolio that is to be added to the portfolio manager.
   */
  String getPortfolioName();

  /**
   * Displays a message relevant to the user.
   * @param message the message that is to be displayed.
   */
  void promptMessage(String message);

  /**
   * Returns the date from the user input in format yyyy-mm-dd.
   * @return the date input by the user in string format of pattern yyyy-mm-dd.
   */
  String getDate();

  /**
   * Returns the details of a share that the user wants to be in the form of a string array which
   * is of the order stock name (ticker symbol), the amount for which the shares are to be bought,
   * the date for which the share is to be bought on and the commission for the transaction.
   * @return a string array containing necessary details for a particular purchase of a stock.
   */
  String[] getShareBuyDetails();

  /**
   * Returns the portfolio index of the portfolio for which operations are to be carried out on.
   * @return the portfolio index of the portfolio.
   */
  String enterPortfolio();
}
