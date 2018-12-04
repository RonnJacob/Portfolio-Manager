package howtoinvest.controller;

import howtoinvest.model.DollarCostAveraging;
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
  private IPortfolio selectedPortfolio;

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
      double shares = selectedPortfolio.addStock(stockNameEntered, amountEntered, dateEntered,
              selectedPortfolio.getCommission(commissionEntered));
      view.logMessage(shares +" share(s) of "+stockNameEntered +" bought for $"+amountEntered
              +" on "+dateEntered +" with a commission charge of "
              + selectedPortfolio.getCommission(commissionEntered));
    } catch(IllegalArgumentException ex){
      throw new IllegalArgumentException(ex);
    }
  }
}
