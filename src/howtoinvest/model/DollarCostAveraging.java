package howtoinvest.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * <p>This class represents Dollar cost averaging investment strategy.A popular long-term passive
 * investment strategy is called dollar-cost averaging. In this strategy, an investor creates a
 * portfolio of stocks and determines their relative proportion (e.g. 60% stocks, 40% bonds, with
 * the stocks equally divided into technology, financial, utility, real estate, consumer staple and
 * consumer discretionary companies and the bonds equally divided into federal, state and municipal
 * bonds). Then the investor invests a fixed amount of money in this portfolio at regular frequency
 * (e.g. invest $1000 at the beginning of each month) over a long period of time, paying no
 * attention to what the stock market is doing on the days of the stock purchases. This may be
 * termed as the investment-equivalent of putting a fixed amount of money in a savings account each
 * month.The weights of the stocks must add up to 100 always. The strategy can be applied on a
 * portfolio and if any of the transactions are invalid among the investments made it continues with
 * all the other valid transactions. The investment occurs from the starting date to the end
 * date(inclusive)</p>
 * <p>The benefit of this strategy is that it is extremely simple: the investor does not have to
 * spend time and effort tweaking a portfolio, or monitoring the stock market. This strategy is
 * based on the well-founded hypothesis that although the stock prices fluctuate every day, the
 * long-term trend of a well-chosen portfolio is upward. The fixed-frequency investing effectively
 * “averages” the unpredictable short-term gains and losses of the portfolio. While this strategy
 * fails to capitalize on the market ups and downs, it has the potential to produce a more stable
 * gain, when the strategy is followed without hesitation over a long period of time. This strategy
 * is very popular: for example, most people manage their retirement accounts this way.</p>
 */
public class DollarCostAveraging implements IInvestmentStrategy<IPortfolio> {

  private final String datePattern = "yyyy-MM-dd";
  private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);

  /**
   * Stores teh stocks and weights in the strategy.
   */
  private TreeMap<String, Double> stockWeights;

  /**
   * Amount to be invested.
   */
  private double amount;

  /**
   * Frequency at which the amount has to be invested in the stocks.
   */
  private int frequency;

  /**
   * Starting date of investment.
   */
  private Date startDate;

  /**
   * Ending date of investment.
   */
  private Date endDate;

  /**
   * Constructs a DollarCostAveraging object with the default parameters.
   */
  public DollarCostAveraging() {
    this.stockWeights = new TreeMap<>();
    this.stockWeights.put("MSFT", 100.0);
    this.amount = 2000;
    this.frequency = 30;
    this.startDate = parseDate("2018-01-01");
    this.endDate = parseDate("2018-11-25");
  }

  /**
   * Parses the given string date to Date type and returns it.
   *
   * @param date given date of type string.
   * @return parsed Date.
   * @throws IllegalArgumentException if the parsing fails or the date is invalid.
   */
  private Date parseDate(String date) throws IllegalArgumentException {
    if (date == null) {
      throw new IllegalArgumentException("Invalid date");
    }
    try {
      return simpleDateFormat.parse(date);
    } catch (ParseException ex) {
      throw new IllegalArgumentException("Date Parse error");
    }
  }

  @Override
  public List<String> getStocks() {
    List<String> stocks = new ArrayList<>();
    for (String key : this.stockWeights.keySet()) {
      stocks.add(key);
    }
    return stocks;
  }

  /**
   * Returns true if the stocks already exists in the strategy else false.
   *
   * @param tickerSymbol symbol of the stock.
   * @return true if the stocks already exists in the strategy else false.
   * @throws IllegalArgumentException if the given string is null or empty.
   */
  private Boolean isStockInStrategy(String tickerSymbol) throws IllegalArgumentException {
    if (tickerSymbol == null || tickerSymbol.trim().isEmpty()) {
      throw new IllegalArgumentException("Invalid stock");
    }
    for (String key : this.stockWeights.keySet()) {
      if (tickerSymbol.equalsIgnoreCase(key)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Adds a stock to the strategy. If the stock already exists it does not add.
   *
   * @param tickerSymbol symbol of the stock.
   * @throws IllegalArgumentException if the symbol is null.
   */
  @Override
  public void addStockToStrategy(String tickerSymbol) throws IllegalArgumentException {
    if (!isStockInStrategy(tickerSymbol)) {
      this.stockWeights.put(tickerSymbol, 0.0);
    }
  }

  /**
   * Adds the list of stocks to the strategy. If any of the stocks already exists it does not add.
   *
   * @param tickerSymbolList stocks to be added.
   * @throws IllegalArgumentException if the list is null or any of the symbol is null.
   */
  @Override
  public void addMultipleStocksToStrategy(List<String> tickerSymbolList)
          throws IllegalArgumentException {
    if (tickerSymbolList == null) {
      throw new IllegalArgumentException("Invalid stock list");
    }
    for (String tickerSymbol : tickerSymbolList) {
      if (!isStockInStrategy(tickerSymbol)) {
        this.stockWeights.put(tickerSymbol, 0.0);
      }
    }
  }

  @Override
  public void setWeights(TreeMap<String, Double> weights) throws IllegalArgumentException {
    validWeights(weights);
    this.stockWeights = new TreeMap<>();
    for (String key : weights.keySet()) {
      this.stockWeights.put(key, weights.get(key));
    }
  }

  /**
   * Checks if the given stock weights contains all the weights for all the stocks in strategy.
   *
   * @param weights stock and theier weights as a Treemap.
   * @throws IllegalArgumentException if the weights dont add up to 100 or if it contains any stocks
   *                                  which are not in the startegy or does not contain all the
   *                                  stocks int he strategy.
   */
  private void validWeights(TreeMap<String, Double> weights) throws IllegalArgumentException {
    if (weights == null) {
      throw new IllegalArgumentException("Invalid weights");
    }
    double total = 0;
    if (weights.size() != this.stockWeights.size()) {
      throw new IllegalArgumentException("Invalid weights");
    }
    for (String key : weights.keySet()) {
      if (!this.stockWeights.containsKey(key)) {
        throw new IllegalArgumentException("Invalid weights");
      }
      total += weights.get(key);
    }
    if (total != 100) {
      throw new IllegalArgumentException("Invalid weights");
    }
  }

  @Override
  public void setAmount(double amount) throws IllegalArgumentException {
    if (amount <= 0) {
      throw new IllegalArgumentException("Invalid amount");
    }
    this.amount = amount;
  }

  @Override
  public void setFrequency(int days) throws IllegalArgumentException {
    if (days <= 0) {
      throw new IllegalArgumentException("Invalid frequency");
    }
    this.frequency = days;
  }

  @Override
  public void setTimeRange(String begDate, String endDate) throws IllegalArgumentException {
    Date tempStartDate = parseDate(begDate);
    Date tempEndDate;
    if (endDate == null) {
      try {
        tempEndDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
      } catch (ParseException ex) {
        throw new IllegalArgumentException("Date Parse error");
      }
    } else {
      tempEndDate = parseDate(endDate);
    }
    if (tempStartDate.after(tempEndDate)) {
      throw new IllegalArgumentException("Invalid dates: Start date after end date");
    }
    this.startDate = tempStartDate;
    this.endDate = tempEndDate;
  }

  /**
   * Applies the strategy to a portfolio and returns the details of the investments by date and by
   * stock. If any of the transactions are invalid among the investments made it continues with all
   * the other valid transactions. The investment occurs from the starting date to the end
   * date(inclusive).
   *
   * @param portfolio  portfolio on which the strategy has to be applied.
   * @param commission commission for every transaction.
   * @return Treemap of key date and value Hashmap of stock and the shares bought.
   * @throws IllegalArgumentException if the portfolio is null.
   */
  @Override
  public TreeMap<Date, HashMap<String, Double>> applyStrategy(IPortfolio portfolio,
                                                              double commission)
          throws IllegalArgumentException {
    if (portfolio == null) {
      throw new IllegalArgumentException("Invalid portfolio");
    }
    TreeMap<Date, HashMap<String, Double>> investmentsByDate = new TreeMap<>();
    LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(frequency)) {
      Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
      Date currentDate = parseDate(simpleDateFormat.format(Date.from(instant)));
      investmentsByDate.put(currentDate,
              portfolio.invest(amount, stockWeights, false,
                      simpleDateFormat.format(Date.from(instant)),
                      commission));
    }
    return investmentsByDate;
  }
}
