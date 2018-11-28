import java.util.Scanner;

import howtoinvest.view.IHowToInvestView;

public class MockViewOne implements IHowToInvestView {

  private StringBuilder log;
  private int uniqueCode;
  private final Readable in;
  private final Appendable out;
  private Scanner scan;

  /**
   * Constructor that takes in a log that is to be used for logging operations.
   *
   * @param log the log string.
   */
  public MockViewOne(StringBuilder log, Readable in , Appendable out) {
    this.log = log;
    this.uniqueCode = 0;
    this.in = in;
    scan = new Scanner(this.in);
    this.out = out;
  }


  @Override
  public void openHomeScreen() {
    this.log.append("Home Screen Opened.\n");
    this.uniqueCode = 100;
  }

  @Override
  public void openPortfolioMenu() {
    this.log.append("Portfolio Screen Opened.\n");
    this.uniqueCode = 200;
  }

  @Override
  public void strategyModificationMenu() {
    this.log.append("Strategy modification screen Opened.\n");
    this.uniqueCode = 300;
  }

  @Override
  public void quitManager() {
    this.log.append("\nQuitting Manager.\n");
    this.uniqueCode = 400;
  }

  @Override
  public void displayPortfolioComposition(String shareName, Double numberOfShares) {
    this.log.append("Composition "+shareName+" "+numberOfShares+"\n");
    this.uniqueCode = 500;
  }

  @Override
  public void displayPortfolioValue(String date, double stockValue) {
    this.log.append("Value displayed for "+stockValue+" on "+date+"\n");
    this.uniqueCode = 600;
  }

  @Override
  public void displayPortfolioCostBasis(String date, double stockCostBasis) {
    this.log.append("Cost Basis displayed for "+stockCostBasis+" on "+date+"\n");
    this.uniqueCode = 700;
  }

  @Override
  public void displayList(int counter, String portfolioName, String listName) {
    this.log.append("List of  "+listName+" with item #"+counter + " " +portfolioName+" \n");
    this.uniqueCode = 800;
  }

  @Override
  public String getInput(String message) {
    if(!message.equals("")){
      this.log.append("Input received for: "+message+"\n");
    }
    this.uniqueCode = 900;
    if(!message.equals("")){
//      promptMessage(message);
    }
    if(scan.hasNext()){
      String input = scan.next();
      if(input == null){
        return "";
      }
      this.log.append("Input received : "+input+"\n");
      return input;
    }
    else{
      return "";
    }
  }

  @Override
  public void promptMessage(String message) {
    this.log.append("Prompt: "+message);
    this.uniqueCode = 1000;
  }

  @Override
  public String[] getShareBuyDetails() {
    this.uniqueCode = 1100;
    String[] buyDetails = new String[4];
//    promptMessage("Enter stock symbol:\n");
    buyDetails[0] = scan.next();
//    promptMessage("Enter amount for which shares are to be bought:\n");
    buyDetails[1] = scan.next();
//    promptMessage("Enter date in format yyyy-mm-dd: \n");
    buyDetails[2] = scan.next();
//    promptMessage("Enter the commission option for the transaction [l, m, h] \n");
    buyDetails[3] = scan.next();
    this.log.append("Buy details queried.");
    for(int i=0; i<buyDetails.length; i++){
      this.log.append(" "+buyDetails[i]);
    }
    this.log.append("\n");
    return buyDetails;
  }

  @Override
  public void openInvestmentMenu() {
    this.log.append("Investment Menu Screen Opened. \n");
    this.uniqueCode = 1100;
  }

  @Override
  public void openStrategyMenu() {
    this.log.append("Strategy Menu Screen opened. \n");
    this.uniqueCode = 1100;

  }
}
