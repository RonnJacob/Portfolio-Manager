package howtoinvest.model;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 * This class represents an Dollar cost averaging strategy  Manager. This manages dollar cost
 * averaging strategies. The strategies are uniquely identified by their names and are sorted and
 * stored according to the lexicographical order of their identifier. This strategy manager contains
 * a Default strategy which is automatically created for the user. Users can add , modify and apply
 * the strategy ona portfolio by retrieving a strategy of their choice from the manager and
 * performing the above operations on it.
 */
public class DollarCostAveragingStrategyManager
        implements IManager<DollarCostAveraging> {
  /**
   * tree map of key value pairs where the key is the name of the strategy and values is the object
   * of that strategy.
   */
  private TreeMap<String, DollarCostAveraging> strategies = new TreeMap<>();

  /**
   * Constructs a DollarCostAveraging object and adds a default DollarCostAveraging strategy.
   */
  public DollarCostAveragingStrategyManager() {
    this.strategies.put("Default DollarCostAveraging", new DollarCostAveraging());
  }

  /**
   * Return the indices and the names of all strategies. Index starts from 1 and the strategies are
   * lexicographically ordered based on the names of the strategy.
   */
  @Override
  public List<String> getAll() {
    List<String> strategyNames = new LinkedList<>();
    strategyNames.addAll(this.strategies.keySet());
    return strategyNames;
  }

  /**
   * Creates a new strategy with the given name.
   *
   * @param name name of the strategy.
   * @throws IllegalArgumentException if the name is null or empty or if the name is already taken
   *                                  by another strategy.
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
   * Returns the strategy of the given index in the lexicographically sorted tree map.
   *
   * @param index of the strategy.
   * @return if the index is invalid.
   * @throws IllegalArgumentException if the strategy does not exist with the given index.
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
