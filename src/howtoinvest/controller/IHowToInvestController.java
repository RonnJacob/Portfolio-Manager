package howtoinvest.controller;

/**
 * Interface for the how-to-invest-for-dummies application. An implementation would work with
 * IPortfolio manager interface which would represent a type of portfolio manager. The interface
 * contains an operation to open a portfolio manager wherein a user would be able to make choices
 * provided by a specific implementation of IPortfolio manager type class. A portfolio manager would
 * offer operations such as creation of portfolios, examining the composition of portfolios which
 * would ideally be made up of stocks and shares for a particular company or organization, adding
 * shares/stocks to a portfolio for a particular date and retrieving the cost basis and value of
 * stocks or of a portfolio for a given date. This would aid the user in learning about how the
 * investment cycle would work and how the value would dip and spike depending on the date at which
 * the investments were made or the date at which the value is being retrieved for.
 */
public interface IHowToInvestController<K> {


  /**
   * Opens a portfolio manager with options for a user to perform investment related operations
   * depending on a specific implementation of the portfolio manager and operations provided by the
   * portfolio manager.
   */
  void openPortfolioManager();

}
