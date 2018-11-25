package howtoinvest.model;

import java.util.List;
import java.util.TreeMap;

/**
 * Represents an interface to manage all portfolios of type K. This interface provides the ability
 * to list all the existing portfolios, create a new portfolio and retrieve a portfolio. The
 * portfolios are lexicographically ordered with an index to facilitate retrieval of the portfolio
 * with an index.
 *
 * @param <K> data type
 */
public interface IPortfolioManager<K> {
//  /**
//   * Lists all the existing portfolios.
//   *
//   * @return all existing portfolios.
//   */
//  String getPortfolios();

  List<String> getPortfolios();

  /**
   * Creates a portfolio with given name.
   *
   * @param name name of the portfolio.
   * @throws IllegalArgumentException if the name is null or empty or the portfolio for the given
   *                                  name already exists.
   */
  void createPortfolio(String name) throws IllegalArgumentException;

  /**
   * Returns the portfolio of type K corresponding to the given index.
   *
   * @param index of the portfolio.
   * @return the portfolio of type K
   * @throws IllegalArgumentException if the portfolio does not exist with the given index.
   */
  K getPortfolio(int index) throws IllegalArgumentException;
}
