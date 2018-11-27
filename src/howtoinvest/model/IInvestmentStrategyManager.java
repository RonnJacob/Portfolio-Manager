package howtoinvest.model;

import java.util.List;

public interface IInvestmentStrategyManager<K> {

  List<String> getStrategies();

  /**
   * Creates a portfolio with given name.
   *
   * @param name name of the portfolio.
   * @throws IllegalArgumentException if the name is null or empty or the portfolio for the given
   *                                  name already exists.
   */
  void createStrategy(String name) throws IllegalArgumentException;

  /**
   * Returns the portfolio of type K corresponding to the given index.
   *
   * @param index of the portfolio.
   * @return the portfolio of type K
   * @throws IllegalArgumentException if the portfolio does not exist with the given index.
   */
  K getStrategy(int index) throws IllegalArgumentException;
}
