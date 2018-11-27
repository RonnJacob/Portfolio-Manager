package howtoinvest.model;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class DollarCostAveragingStrategyManager
        implements IManager<DollarCostAveraging> {
  /**
   * tree map of key value pairs where the key is the name of the portfolio and values is the object
   * of that stock portfolio.
   */
  private TreeMap<String, DollarCostAveraging> strategies = new TreeMap<>();

  /**
   * Constructs a StockPortfolioManager object and adds a default stock portfolio.
   */
  public DollarCostAveragingStrategyManager() {
    this.strategies.put("Default DollarCostAveraging", new DollarCostAveraging());
  }

  /**
   * Return the indices and the names of all portfolios. Index starts from 1 and the portfolios are
   * lexicographically ordered based on the names of the stock portfolio.
   */
  @Override
  public List<String> getAll() {
    List<String> strategyNames = new LinkedList<>();
    strategyNames.addAll(this.strategies.keySet());
    return strategyNames;
  }

  /**
   * Creates a new portfolio with the given name.
   *
   * @param name name of the portfolio.
   * @throws IllegalArgumentException if the name is null or empty or if the name is already taken
   *                                  by another portfolio.
   */
  @Override
  public void create(String name) throws IllegalArgumentException {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Invalid Name");
    }
    for (String key : this.strategies.keySet()) {
      if (name.equals(key)) {
        throw new IllegalArgumentException("Strategy already exists");
      }
    }
    DollarCostAveraging newStrategy = new DollarCostAveraging();
    this.strategies.put(name, newStrategy);
  }

  /**
   * Returns the portfolio of the given index in the lexicographically sorted tree map.
   *
   * @param index of the portfolio.
   * @return if the index is invalid.
   * @throws IllegalArgumentException if the portfolio does not exist with the given index.
   */
  @Override
  public DollarCostAveraging getByIndex(int index) {
    int counter = 1;
    for (String key : this.strategies.keySet()) {
      if (index == counter) {
        return this.strategies.get(key);
      }
      counter++;
    }
    throw new IllegalArgumentException("Invalid index for the strategy");
  }
}
