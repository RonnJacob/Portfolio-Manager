package howtoinvest.view;

public class IHowToInvestViewImpl implements IHowToInvestView {
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
    System.out.println(counter + ": "+key+"\n");
  }

}
