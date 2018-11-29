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

public class DollarCostAveraging implements IInvestmentStrategy<IPortfolio> {

  private final String datePattern = "yyyy-MM-dd";
  private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);

  private TreeMap<String, Double> stockWeights;
  private double amount;
  private int frequency;
  private Date startDate;
  private Date endDate;

  public DollarCostAveraging() throws IllegalArgumentException {
    this.stockWeights = new TreeMap<>();
    this.stockWeights.put("MSFT", 100.0);
    this.amount = 2000;
    this.frequency = 30;
    this.startDate = parseDate("2018-01-01");
    this.endDate = parseDate("2018-11-25");
  }

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

  @Override
  public void addStockToStrategy(String tickerSymbol) throws IllegalArgumentException {
    if (!isStockInStrategy(tickerSymbol)) {
      this.stockWeights.put(tickerSymbol, 0.0);
    }
  }

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
    for(String key: weights.keySet()){
      this.stockWeights.put(key, weights.get(key));
    }
  }

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
  public void setAmount(double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Invalid amount");
    }
    this.amount = amount;
  }

  @Override
  public void setFrequency(int days) {
    if (days <= 0) {
      throw new IllegalArgumentException("Invalid frequency");
    }
    this.frequency = days;
  }

  @Override
  public void setTimeRange(String begDate, String endDate) {
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

  @Override
  public TreeMap<Date, HashMap<String, Double>> applyStrategy(IPortfolio portfolio,
                                                              double commission)
          throws IllegalArgumentException {
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
