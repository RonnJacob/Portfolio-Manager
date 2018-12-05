package howtoinvest.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;

import howtoinvest.model.DollarCostAveraging;
import howtoinvest.model.IInvestmentStrategy;
import howtoinvest.model.IManager;
import howtoinvest.model.IPortfolio;
import howtoinvest.model.StockPortfolio;
import howtoinvest.view.HowToInvestViewGUI;
import howtoinvest.view.StrategyViewGUI;

public class HowToInvestControllerGUI implements IHowToInvestController {

  /**
   * Variables initializing models and view objects.
   */
  private final HowToInvestViewGUI view;
  private final StrategyViewGUI strategyView;
  private final IManager<StockPortfolio> model;
  private final IManager<DollarCostAveraging> strategyModel;
  private static final String datePattern = "yyyy-MM-dd";
  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
  private StockPortfolio selectedPortfolio;
  private IInvestmentStrategy selectedStrategy;

  public HowToInvestControllerGUI(HowToInvestViewGUI view, StrategyViewGUI strategyView,
                                  IManager<StockPortfolio> model,
                                  IManager<DollarCostAveraging> strategyModel) {
    this.model = model;
    this.view = view;
    this.strategyModel = strategyModel;
    this.strategyView = strategyView;
  }

  @Override
  public void openPortfolioManager() {
    strategyView.openHomeScreen(strategyModel.getAll());
    strategyView.addFeatures(this);
    view.openHomeScreen(model.getAll());
    view.addFeatures(this);
  }

  @Override
  public void createPortfolio(String portfolioName) throws IllegalArgumentException {
    try {
      model.create(portfolioName);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public String openPortfolios(String portfolioToOpen) throws IllegalArgumentException {
    try {
      int counter = 1;
      for (String pfolio : model.getAll()) {
        if (pfolio.equalsIgnoreCase(portfolioToOpen)) {
          selectedPortfolio = model.getByIndex(counter);
          break;
        }
        counter++;
      }
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
    view.openPortfolioScreen(this);
    return "opened";
  }

  @Override
  public void loadList(String filename, String typeOfList) throws IllegalArgumentException {
    try {
      if (typeOfList == "Portfolio") {
        model.retrieve(filename);
      } else if (typeOfList == "Strategy") {
        strategyModel.retrieve(filename);
      }
      view.promptMessage(typeOfList + " " + filename + " has been added.");
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public String getPortfolioCostBasis(String date) throws IllegalArgumentException {
    try {
      return String.format("%.2f", selectedPortfolio.getStockCostBasis(date));
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public String getPortfolioValue(String date) throws IllegalArgumentException {
    try {
      return String.format("%.2f", selectedPortfolio.getStockValue(date));
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public void addStockToPortfolio(String stockNameEntered, double amountEntered, String dateEntered,
                                  String commissionEntered) throws IllegalArgumentException {
    try {
      Double commission;
      if (commissionEntered.equalsIgnoreCase("")) {
        commission = 0.0;
      } else {
        commission = selectedPortfolio.getCommission(commissionEntered);
      }

      double shares = selectedPortfolio.addStock(stockNameEntered, amountEntered, dateEntered,
              commission);
      view.logMessage(shares + " share(s) of " + stockNameEntered + " bought for $" + amountEntered
              + " on " + dateEntered + " with a commission charge of "
              + commission);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public HashMap<String, Double> getStocksInPortfolio(String date) throws IllegalArgumentException {
    HashMap<String, Double> stocks;
    try {
      stocks = selectedPortfolio.getPortfolioData(date);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
    return stocks;
  }

  @Override
  public List<String> getStocksInStrategy() throws IllegalArgumentException {
    List<String> stocks;
    try {
      stocks = selectedStrategy.getStocks();
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
    return stocks;
  }

  @Override
  public String[] showStrategies() {
    return strategyModel.getAll().stream().toArray(String[]::new);
  }

  @Override
  public void applyStrategy(String strategyToApply, String commission)
          throws IllegalArgumentException {
    try {
      int counter = 1;
      for (String strategy : strategyModel.getAll()) {
        if (strategyToApply.equalsIgnoreCase(strategy)) {
          strategyModel.getByIndex(counter)
                  .applyStrategy(selectedPortfolio, selectedPortfolio.getCommission(commission));
          return;
        }
        counter++;
      }
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public void savePortfolio(String fileName) throws IllegalArgumentException {
    try {
      selectedPortfolio.savePortfolio(fileName);
    } catch (IllegalStateException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public void saveStrategy(String fileName) throws IllegalArgumentException {
    try {
      selectedStrategy.saveStrategy(fileName);
    } catch (IllegalStateException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public void addStrategy(String strategyName) throws IllegalArgumentException {
    try {
      strategyModel.create(strategyName);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public String openStrategies(String strategyToOpen) throws IllegalArgumentException {
    try {
      int counter = 1;
      for (String pfolio : strategyModel.getAll()) {
        if (pfolio.equalsIgnoreCase(strategyToOpen)) {
          selectedStrategy = strategyModel.getByIndex(counter);
          break;
        }
        counter++;
      }
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
    strategyView.openStrategyScreen(this);
    return "Strategy Opened";
  }

  @Override
  public void addStockToStrategy(String stockNameEntered) throws IllegalArgumentException {
    try {
      selectedStrategy.addStockToStrategy(stockNameEntered);
      strategyView.logMessage("Stock added");
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public void setStrategyAmount(String amount) throws IllegalArgumentException {
    try {
      selectedStrategy.setAmount(Double.parseDouble(amount));
      strategyView.logMessage("Amount set");
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public void setStrategyFrequency(String frequency) throws IllegalArgumentException {
    try {
      selectedStrategy.setAmount(Integer.parseInt(frequency));
      strategyView.logMessage("Frequency set");
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public void setStrategyTimerange(String begDate, String endDate) throws IllegalArgumentException {
    try {
      selectedStrategy.setTimeRange(begDate, endDate);
      strategyView.logMessage("Timerange set");
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public void investWithWeights(Double amount, String date, String commision,
                                List<Double> weights) throws IllegalArgumentException{
    try{
      String investmentsLog = "";
      TreeMap<String, Double> investWeights = new TreeMap<>();
      HashMap<String, Double> map = selectedPortfolio.getPortfolioData(date);
      int counter = 0;
      for(Map.Entry<String, Double> m: map.entrySet()){
        investWeights.put(m.getKey(),weights.get(counter));
        counter++;
      }
      HashMap<String, Double> investments =
              selectedPortfolio.invest(amount, investWeights, false, date,
              selectedPortfolio.getCommission(commision));
      for (Map.Entry<String, Double> investment : investments.entrySet()) {
        investmentsLog += investment.getValue() + " share(s) of " + investment.getKey()
                + " on " + date+"\n";
      }
      view.logMessage(investmentsLog);
    } catch(IllegalArgumentException | IllegalStateException ex){
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public void setStrategyWeights(TreeMap<String, Double> weights) throws IllegalArgumentException {
    try {
      selectedStrategy.setWeights(weights);
    } catch (IllegalArgumentException | IllegalStateException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public void investEqually(Double amount, String date, String commission)
          throws IllegalArgumentException {
    try {
      TreeMap<String, Double> investWeights = new TreeMap<>();
      HashMap<String, Double> investments = selectedPortfolio.invest(amount, investWeights,
              true, date, selectedPortfolio.getCommission(commission));
      String message = "";
      for (Map.Entry<String, Double> investment : investments.entrySet()) {
        message += "[" + investment.getValue() + " share(s) of " + investment.getKey()
                + " on " + date + "]";
      }
      view.logMessage(message);
    } catch (IllegalArgumentException | IllegalStateException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

}
