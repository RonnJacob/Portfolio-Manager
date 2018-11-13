package howtoinvest.controller;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import howtoinvest.model.IPortfolio;
import howtoinvest.model.IPortfolioManager;
import howtoinvest.model.StockPortfolio;

public class HowToInvestController<K> implements IHowToInvestController<K> {

  private final Readable in;
  private final Appendable out;
  private String promptMessage;

  public HowToInvestController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Invalid Readable or Appendable object");
    }
    this.in = in;
    this.out = out;
    this.promptMessage = "";
  }

  @Override
  public void openPortfolioManager(IPortfolioManager<StockPortfolio> portfolioManagerModel) {
    Objects.requireNonNull(portfolioManagerModel, "Portfolio manager cannot be a null.");
    addToAppendable(promptMessage);
    openHomeScreen();
    Scanner scan = new Scanner(this.in);
    while (true) {
      if(!scan.hasNext()){
        addToAppendable("Quitting Manager");
        return;
      }
      switch (scan.next()) {
        case "1":
          addPortfoliosToManager(portfolioManagerModel, scan);
          openHomeScreen();
          break;
        case "2":
          displayPortfolios(portfolioManagerModel, scan);
          openHomeScreen();
          break;
        case "3":
          if (openPortfolio(portfolioManagerModel, scan).equalsIgnoreCase("q")) {
            return;
          }
          openHomeScreen();
          break;
        case "q":
          addToAppendable("Quitting Manager");
          return;
        default:
          addToAppendable("Invalid input. Please enter input again.");
          break;
      }
    }
  }

  private String openPortfolio(IPortfolioManager<StockPortfolio> portfolioManagerModel,
                               Scanner scan) {
    addToAppendable("\nEnter name of Portfolio to open.\n");
    String pfolioName = scan.next();
    String applicationRunning = "r";
    openPortfolioMenu();
    try {
      IPortfolio selectedPFolio = portfolioManagerModel.getPortfolio(Integer.parseInt(pfolioName));
      while (true) {
        if(!scan.hasNext()){
          return "q";
        }
        switch (scan.next().toLowerCase()) {
          case "1":
            addToAppendable(selectedPFolio.getPortfolioData());
            openPortfolioMenu();
            break;
          case "2":
            buyStockShares(selectedPFolio, scan);
            openPortfolioMenu();
            break;
          case "3":
            addToAppendable("Enter date in format yyyy-mm-dd: \n");
            String date = scan.next();
            addToAppendable(selectedPFolio.getStockCostBasisAndStockValue(date));
            openPortfolioMenu();
            break;
          case "r":
            return "r";
          case "q":
            addToAppendable("Quitting Manager");
            return "q";
          default:
            addToAppendable("Invalid input. Please enter input again.");
            break;
        }
      }

    } catch (IllegalArgumentException ex) {
      addToAppendable(ex.getMessage());
    }
    return "r";
  }

  private void buyStockShares(IPortfolio<StockPortfolio> selectedPFolio,
                              Scanner scan) {
    do {
      addToAppendable("Enter stock symbol:\n");
      String stockSymbol = scan.next();
      addToAppendable("Enter amount for which shares are to be bought:\n");
      double amount = Double.parseDouble(scan.next());
      addToAppendable("Enter date in format yyyy-mm-dd: \n");
      String date = scan.next();

      try {
        addToAppendable(selectedPFolio.addStock(stockSymbol, amount, date));
      } catch (IllegalArgumentException ex) {
        addToAppendable(ex.getMessage());
      }
      addToAppendable("Buy more shares? (Y/N)");
      if(scan.hasNext()){
        return;
      }
    }
    while (scan.next().equalsIgnoreCase("y"));
  }


  private void displayPortfolios(IPortfolioManager<StockPortfolio> portfolioManagerModel,
                                 Scanner scan) {
    addToAppendable("\nList of Portfolios\n");
    String[] listOfPortfolios = portfolioManagerModel.getPortfolios().split("\n");
    for (int i = 0; i < listOfPortfolios.length; i++) {
      addToAppendable(String.format("%d. %s\n", i + 1, listOfPortfolios[i]));
    }
  }

  private void addPortfoliosToManager(IPortfolioManager<StockPortfolio> pfolioManager, Scanner in) {
    do {
      addToAppendable("Enter the name of the portfolio to be created.");
      String nameOfPortfolio = in.next();
      try {
        pfolioManager.createPortfolio(nameOfPortfolio);
        addToAppendable("Portfolio " + nameOfPortfolio + " has been created.\n");
      } catch (IllegalArgumentException ex) {
        addToAppendable("Portfolio with that name exists");
      }
      addToAppendable("Add more portfolios? (Y/N)");
    }
    while (in.next().equalsIgnoreCase("y"));
  }

  private void openHomeScreen() {
    addToAppendable("\nWelcome to Portfolio Manager.");
    addToAppendable("1. Create new portfolio.");
    addToAppendable("2. Get existing portfolios.");
    addToAppendable("3. Enter portfolio.");
    addToAppendable("Enter the number for performing operation or q to quit application.");
  }

  private void openPortfolioMenu() {
    addToAppendable("1. Examine composition of portfolio");
    addToAppendable("2. Buy shares of a stock with portfolio.");
    addToAppendable("3. Get Cost Basis/Value of portfolio");
    addToAppendable("Enter R to return to the main menu or q to quit the application.");
  }

  /**
   * Adds a message to the appendable object.
   *
   * @param message the message of String format that is to be added.
   */
  private void addToAppendable(String message) {
    try {
      this.out.append(String.format("%s\n", message));
    } catch (IOException ex) {
      throw new IllegalStateException("IO exception has been encountered.");
    }
  }


}
