package howtoinvest.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public interface IInvestmentStrategy<K> {

  List<String> getStocks();

  void addStockToStrategy(String tickerSymbol);

  void addMultipleStocksToStrategy(List<String> tickerSymbolList);

  void setWeights(TreeMap<String, Double> weights);

  void setAmount(double amount);

  void setFrequency(int days);

  void setTimeRange(String begDate, String endDate);

  TreeMap<Date, HashMap<String, Double>> applyStrategy(K portfolio, double commission);
}
