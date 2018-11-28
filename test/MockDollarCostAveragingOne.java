import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import howtoinvest.model.IInvestmentStrategy;

public class MockDollarCostAveragingOne implements IInvestmentStrategy {

  private StringBuilder log;
  private int uniqueCode;
  private TreeMap<String, Double> stockWeights;
  private double amount;
  private int frequency;

  /**
   * Constructor that takes in a log that is to be used for logging operations.
   *
   * @param log the log string.
   */
  public MockDollarCostAveragingOne(StringBuilder log) {
    this.stockWeights = new TreeMap<>();
    this.stockWeights.put("FB", 50.0);
    this.stockWeights.put("MSFT", 50.0);
    this.log = log;
    this.uniqueCode = 0;
    this.amount = 2000;
    this.frequency = 30;
  }

  @Override
  public List<String> getStocks() {
    return new LinkedList<>();
  }

  @Override
  public void addStockToStrategy(String tickerSymbol) {
    this.log.append("Add stock to strategy.\n");
    this.uniqueCode = 6211;
  }

  @Override
  public void setAmount(double amount) {
    this.log.append("Change amount to "+ amount+"\n");
    this.uniqueCode = 623;
  }

  @Override
  public void setFrequency(int days) {

    this.log.append("Change frequency to "+ days+" days.\n");
    this.uniqueCode = 624;
  }

  @Override
  public void setTimeRange(String begDate, String endDate) {
    this.log.append("Change date from "+begDate+" to "+ endDate+" \n");
    this.uniqueCode = 625;

  }

  @Override
  public TreeMap<Date, HashMap<String, Double>> applyStrategy(Object portfolio, double commission) {
    this.log.append("Apply strategy with commision "+commission);
    this.uniqueCode = 625;
    return new TreeMap<>();
  }

  @Override
  public void setWeights(TreeMap weights) {

    this.log.append("Setting weights\n");
    this.log.append("\n");
    this.uniqueCode = 622;

  }

  @Override
  public void addMultipleStocksToStrategy(List tickerSymbolList) {
    this.log.append("Add stocks to strategy.\n");
    this.uniqueCode = 6212;
  }
}
