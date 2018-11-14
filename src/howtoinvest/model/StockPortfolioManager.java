package howtoinvest.model;

import java.util.TreeMap;

/**
 * This class represents an Stock Portfolio Manager. This manages Stock Portfolios which are
 * essentially collection of stocks owned by the user. The portfolios are uniquely identified by
 * their names and are sorted and stored according to the lexicographical order of their identifier.
 * This portfolio manager contains a Default Portfolio which is automatically created for the user.
 * Users can add stocks, view their composition, get total cost basis and total value of the
 * portfolio by retrieving a portfolio of their choice from the StockPortfolioManager and performing
 * the above operations on it..
 */
public class StockPortfolioManager implements IPortfolioManager<StockPortfolio> {

  /**
   * tree map of key value pairs where the key is the name of the portfolio and values is the object
   * of that stock portfolio.
   */
  private TreeMap<String, StockPortfolio> portfolios = new TreeMap<>();

  /**
   * Constructs a StockPortfolioManager object and adds a default stock portfolio.
   */
  public StockPortfolioManager() {
    this.portfolios.put("Default StockPortfolio", new StockPortfolio());
  }

  /**
   * Return the indices and the names of all portfolios. Index starts from 1 and the portfolios are
   * lexicographically ordered based on the names of the stock portfolio.
   */
  @Override
  public String getPortfolios() {
    String s = "";
    int counter = 1;
    for (String key : this.portfolios.keySet()) {
      s += counter + ". " + key + "\n";
      counter++;
    }
    return s.trim();
  }

  /**
   * Creates a new portfolio with the given name.
   *
   * @param name name of the portfolio.
   * @throws IllegalArgumentException if the name is null or empty or if the name is already taken
   *                                  by another portfolio.
   */
  @Override
  public void createPortfolio(String name) throws IllegalArgumentException {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Invalid Name");
    }
    for (String key : this.portfolios.keySet()) {
      if (name.equals(key)) {
        throw new IllegalArgumentException("StockPortfolio already exists");
      }
    }
    StockPortfolio newStockPortfolio = new StockPortfolio();
    this.portfolios.put(name, newStockPortfolio);
  }

  /**
   * Returns the portfolio of the given index in the lexicographically sorted tree map.
   *
   * @param index of the portfolio.
   * @return if the index is invalid.
   * @throws IllegalArgumentException if the portfolio does not exist with the given index.
   */
  @Override
  public StockPortfolio getPortfolio(int index) {
    int counter = 1;
    for (String key : this.portfolios.keySet()) {
      if (index == counter) {
        return this.portfolios.get(key);
      }
      counter++;
    }
    throw new IllegalArgumentException("Invalid index for the Stock Portfolio");
  }
}
