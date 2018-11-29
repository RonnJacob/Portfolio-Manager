package howtoinvest.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * This interface represents an investment strategy. An investment strategy has the following
 * features and provides the following operations:
 * <ul>
 * <li>
 * The strategy contains list of stocks user wants to invest in. Functionality to retrieve those
 * stocks, add a single stock or multiple items to the strategy.
 * </li>
 * <li>
 * The amount user wants to invest in total for all the stocks on a frequency. Functionality to set
 * the investment amount.
 * </li>
 * <li>
 * The user can invest with different weights or proportions of the total amount for every stock in
 * the strategy. Functionality to set the weights of the investment items.
 * </li>
 * <li>
 * Strategy can be set for a certain time period so a functionality to set the beginning and end
 * dates for the strategy investments are provided.
 * </li>
 * <li>
 * Investments can be made every 30 days or 10 days this frequency(no of days) can be set.
 * </li>
 * <li>
 * Functionality to apply the strategy to a Portfolio(K) and return the details of the investments
 * made by the strategy on the portfolio. Commission amount can be set which will be applied for
 * every transaction.
 * </li>
 * </ul>
 *
 * @param <K> the type of the data for which the strategy can be applied.
 */
public interface IInvestmentStrategy<K> {

  /**
   * Returns the list of all the stocks in the strategy.
   *
   * @return list of all the stocks
   */
  List<String> getStocks();

  /**
   * Adds a stock to the strategy.
   *
   * @param tickerSymbol symbol of the stock.
   * @throws IllegalArgumentException if the symbol is null.
   */
  void addStockToStrategy(String tickerSymbol) throws IllegalArgumentException;

  /**
   * Adds the list of stocks to the strategy.
   *
   * @param tickerSymbolList stocks to be added.
   * @throws IllegalArgumentException if the list is null or any of the symbol is null.
   */
  void addMultipleStocksToStrategy(List<String> tickerSymbolList);

  /**
   * Sets the weight of the stocks according tot he given weights.
   *
   * @param weights Key value pairs of stock and its weights.
   * @throws IllegalArgumentException if the weights are null or invalid.
   */
  void setWeights(TreeMap<String, Double> weights) throws IllegalArgumentException;

  /**
   * Sets the amount of investment in the strategy.
   *
   * @param amount amount to be invested.
   * @throws IllegalArgumentException if the amount is negative or zero.
   */
  void setAmount(double amount) throws IllegalArgumentException;

  /**
   * Sets the frequency of the investments.
   *
   * @param days frequency in days(integers).
   * @throws IllegalArgumentException if the frequency is negative or 0.
   */
  void setFrequency(int days) throws IllegalArgumentException;

  /**
   * Sets the time range for the investments.
   *
   * @param begDate beginning date.
   * @param endDate end date.
   * @throws IllegalArgumentException if the beginning date is after the end date or if any of the
   *                                  dates are invalid.
   */
  void setTimeRange(String begDate, String endDate) throws IllegalArgumentException;

  /**
   * Applies the strategy to a portfolio and returns the details of the investments by date and by
   * stock.
   *
   * @param portfolio  portfolio on which the strategy has to be applied.
   * @param commission commission for every transaction.
   * @return Treemap of key date and value Hashmap of stock and the shares bought.
   * @throws IllegalArgumentException if the portfolio is null.
   */
  TreeMap<Date, HashMap<String, Double>> applyStrategy(K portfolio, double commission)
          throws IllegalArgumentException;
}
