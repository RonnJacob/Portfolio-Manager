package howtoinvest.view;
import java.util.Scanner;

/**
 * The following class is an implementation of the IHowToInvestView interface which implements
 * all the necessary operations specified in the interface. This class would represent a console
 * based view implementation of the interview whereby the program and the necessary outputs/inputs
 * and messages would be displayed on a terminal/console.
 */
public class HowToInvestViewImpl implements IHowToInvestView {

  private final Scanner scan= new Scanner(System.in);

  @Override
  public void openHomeScreen() {
    String homeScreen = "Welcome to Portfolio Manager.\n";
    homeScreen += "1. Create new portfolio.\n";
    homeScreen += "2. Get existing portfolios.\n";
    homeScreen += "3. Enter portfolio.\n";
    homeScreen += "Enter the number for performing operation or q to quit application.\n";
    System.out.print(homeScreen);
  }

  @Override
  public void openPortfolioMenu() {
    String portfolioScreen = "1. Examine composition of portfolio\n";
    portfolioScreen += "2. Buy shares of a stock with portfolio.\n";
    portfolioScreen += "3. Get Cost Basis of portfolio\n";
    portfolioScreen += "4. Get Value of portfolio\n";
    portfolioScreen += "Enter R to return to the main menu or q to quit the application.\n";
    System.out.println(portfolioScreen);
  }

  @Override
  public void quitManager(){
    System.out.print("Quitting manager\n");
  }

  @Override
  public void addPortfolio(String name) {
    System.out.println("Portfolio " + name + " has been created.\n");
  }

  @Override
  public void addStockToPortfolio(String name, double numberOfShares, String date) {
    System.out.println(numberOfShares + " share(s) of "+name+" bought on "+date+"\n");
  }

  @Override
  public void getPortfolioComposition(String key, Double value) {
    System.out.println(value + " share(s) of "+key+"\n");
  }

  @Override
  public void getValueOfPortfolio(String date, double stockValue) {
    System.out.println("The value of the portfolio as of "+ date + " is " + stockValue+"\n");
  }

  @Override
  public void getCostBasisOfPortfolio(String date, double stockCostBasis) {
    System.out.println("The cost basis of the portfolio as of "+ date + " is " + stockCostBasis
            +"\n");
  }

  @Override
  public void getListOfPortfolios(int counter, String key) {
    if(counter==1){
      System.out.println("\nList of Portfolios\n");
    }
    System.out.println(counter + ": "+key+"\n");
  }

  @Override
  public String getInputString() {
    if(scan.hasNext()){
      return scan.next();
    }
    else{
      return "";
    }
  }

  @Override
  public String addPortfoliosToManager(){
    System.out.println("Enter the name of the portfolio to be created\n");
    return scan.next();
  }


  @Override
  public void promptMessage(String message){
    System.out.println(message);
  }

  @Override
  public String promptDate() {
    System.out.println("Enter date in format yyyy-mm-dd: \n");
    return scan.next();
  }

  @Override
  public String[] buyStockShareDisplay() {
    String[] buyDetails = new String[4];
    System.out.println("Enter stock symbol:\n");
    buyDetails[0] = scan.next();
    System.out.println("Enter amount for which shares are to be bought:\n");
    buyDetails[1] = scan.next();
    System.out.println("Enter date in format yyyy-mm-dd: \n");
    buyDetails[2] = scan.next();
    System.out.println("Enter the commission for the transaction: \n");
    buyDetails[3] = scan.next();
    return buyDetails;
  }

  @Override
  public String openPortfolio() {
    System.out.println("Enter index of Portfolio to open.\n\n");
    if(scan.hasNext()){
      return scan.next();
    }
    else{
      return "";
    }
  }

}
