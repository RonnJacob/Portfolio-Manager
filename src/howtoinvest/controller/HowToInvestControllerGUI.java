package howtoinvest.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;

import howtoinvest.model.DollarCostAveraging;
import howtoinvest.model.IInvestmentStrategy;
import howtoinvest.model.IManager;
import howtoinvest.model.IPortfolio;
import howtoinvest.model.StockPortfolio;
import howtoinvest.view.HowToInvestViewGUI;

public class HowToInvestControllerGUI implements IHowToInvestController {

  /**
   * Variables initializing models and view objects.
   */
  private final HowToInvestViewGUI view;
  private final IManager<StockPortfolio> model;
  private final IManager<DollarCostAveraging> strategyModel;
  private static final String datePattern = "yyyy-MM-dd";
  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
  private IPortfolio selectedPortfolio;
  private IInvestmentStrategy selectedStrategy;

  public HowToInvestControllerGUI(HowToInvestViewGUI view, IManager<StockPortfolio> model,
                                  IManager<DollarCostAveraging> strategyModel) {
    this.model = model;
    this.view = view;
    this.strategyModel = strategyModel;
  }

  @Override
  public void openPortfolioManager()
  {
    view.openHomeScreen(model.getAll());
    view.addFeatures(this);
  }

  @Override
  public void addPortfolio(String portfolioName) {
    try{
      model.create(portfolioName);
    }catch(IllegalArgumentException ex){
      throw new IllegalArgumentException(ex);
    }
  }

  @Override
  public void openPortfolios(String portfolioToOpen) {
      int counter = 1;
      for(String pfolio: model.getAll()){
        if(pfolio.equalsIgnoreCase(portfolioToOpen)){
          selectedPortfolio = model.getByIndex(counter);
          break;
        }
        counter++;
      }
    view.openPortfolioScreen(this);
  }

  @Override
  public void loadPortfolio(String fileName) {
    try{
      model.retrieve(fileName);
    }catch(IllegalStateException ex){
      throw new IllegalArgumentException(ex);
    }
  }

  public String getPortfolioCostBasis(String date) {
//    selectedPortfolio.addStock("FB",2000,"2018-11-06",2.0);
    return String.format("%.2f",selectedPortfolio.getStockCostBasis("2018-11-14"));
  }

  public String getPortfolioValue(String date) {
    return String.format("%.2f",selectedPortfolio.getStockValue("2018-11-14"));
  }

  public void addStockToPortfolio(String stockNameEntered, double amountEntered, String dateEntered,
                                  String commissionEntered) {
    try{
      Double commission;
      if(commissionEntered.equalsIgnoreCase("")){
        commission = 0.0;
      }
      else{

        commission = selectedPortfolio.getCommission(commissionEntered);
      }

      double shares = selectedPortfolio.addStock(stockNameEntered, amountEntered, dateEntered,
              commission);
      view.logMessage(shares +" share(s) of "+stockNameEntered +" bought for $"+amountEntered
              +" on "+dateEntered +" with a commission charge of "
              + commission);
    } catch(IllegalArgumentException ex){
      throw new IllegalArgumentException(ex);
    }
  }

  public HashMap<String, Double> getStocksInPortfolio(String date) {
    HashMap<String, Double> stocks;
    try{
      stocks = selectedPortfolio.getPortfolioData(simpleDateFormat.format(new Date()));
    } catch (IllegalArgumentException ex){
      throw new IllegalArgumentException(ex);
    }
    return stocks;
  }

  public String[] getStrategies() {
    return strategyModel.getAll().stream().toArray(String[]::new);
  }

  public void applyStrategy(String strategyToApply, String commision) {
    int counter = 1;
    for(String strategy: strategyModel.getAll()){
      if(strategyToApply.equalsIgnoreCase(strategy)){
        selectedStrategy = strategyModel.getByIndex(counter);
        break;
      }
      counter++;
    }
    selectedStrategy.applyStrategy(selectedPortfolio,selectedPortfolio.getCommission(commision));
  }

  public void savePortfolio(String fileName) {
    try{
      selectedPortfolio.savePortfolio(fileName);
    } catch(IllegalStateException ex){
      throw new IllegalArgumentException(ex);
    }
  }
}
