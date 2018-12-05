package howtoinvest.view;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import howtoinvest.controller.HowToInvestControllerGUI;
import howtoinvest.controller.IHowToInvestController;

public class HowToInvestViewGUI extends JFrame implements ActionListener,
        ListSelectionListener {

  private JList list;
  private JPanel stockPanel = new JPanel();
  private JPanel mainPortfolioPanel = new JPanel();
  private JPanel loggerPanel = new JPanel();
  private JPanel stocksPortfolioPanel = new JPanel();
  private JLabel log = new JLabel();
  private JTextField newPortfolioName;
  private JButton createPortfolio = new JButton("Create Portfolio");
  private JButton openPortfolio = new JButton("Open Portfolio");
  private JButton closePortfolio = new JButton("Close Portfolio");
  private JButton loadPortfolio = new JButton("Load Portfolio");
  private DefaultListModel listModel;
  private JComboBox listOfStrategies;
  private static final Object[][] rowData = {};
  private static final Object[] columnNames = {"Stock", "Number Of Shares"};


  public HowToInvestViewGUI() {
    super();
    setLocation(200, 200);
    Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
    this.setPreferredSize(DimMax);
    loggerPanel.setLayout(new GridLayout(6,6));
    stockPanel.setLayout(new GridLayout(6, 6));
    mainPortfolioPanel.setLayout(new GridLayout(6, 6));
    loggerPanel.add(log);
    this.add(loggerPanel);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    this.setLayout(new FlowLayout());
    this.setLayout(new GridLayout(6, 24));

  }

  public void openHomeScreen(List<String> listItems) {
    mainPortfolioPanel.add(new JLabel("List Of Portfolios"));
    listModel = new DefaultListModel();
    for (String listItem : listItems) {
      listModel.addElement(listItem);
    }
    list = new JList(listModel);
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    list.setSelectedIndex(0);
    list.addListSelectionListener(this);
    JScrollPane listScrollPane = new JScrollPane(list);
    listScrollPane.setPreferredSize(new Dimension(700, 500));
    mainPortfolioPanel.add(listScrollPane);
    mainPortfolioPanel.add(createPortfolio);

    /**
     * Create Portfolio Button.
     */
    JLabel portfolioNameLabel = new JLabel("Enter portfolio name to be created.");
    newPortfolioName = new JTextField(15);
    createPortfolio.addActionListener(this);
    mainPortfolioPanel.add(createPortfolio);
    mainPortfolioPanel.add(newPortfolioName);
    openPortfolio.addActionListener(this);
    mainPortfolioPanel.add(openPortfolio);
    loadPortfolio.addActionListener(this);
    mainPortfolioPanel.add(loadPortfolio);
    this.add(mainPortfolioPanel);
    pack();
    setVisible(true);
  }


  @Override
  public void actionPerformed(ActionEvent arg0) {

  }

  @Override
  public void valueChanged(ListSelectionEvent e) {

  }

  public void addFeatures(IHowToInvestController controller) {
    createPortfolio.addActionListener((ActionEvent e)-> {
        String portfolioName = newPortfolioName.getText();
        if(portfolioName.equals("")){
          promptMessage("Please enter portfolio name.\n");
          return;
        }
        try{
          controller.addPortfolio(portfolioName);
          listModel.insertElementAt(portfolioName, listModel.size()-1);
          newPortfolioName.setText("");
        } catch(IllegalArgumentException ex){
          promptMessage("Portfolio "+portfolioName+" already exists.\n");
        }
      }
    );

    openPortfolio.addActionListener((ActionEvent e)-> {
        try{

          String portfolioToOpen = list.getSelectedValue().toString();
          controller.openPortfolios(portfolioToOpen);
          log.setText("Portfolio "+portfolioToOpen + " has been opened.");
          openPortfolio.setEnabled(false);
          closePortfolio.setEnabled(true);
        } catch(IllegalArgumentException ex){
        }
      }
    );

    loadPortfolio.addActionListener((ActionEvent e)-> {
      JTextField fileName = new JTextField();
      Object[] message = {
              "Load Portfolio :", fileName,
      };

      int option = JOptionPane.showConfirmDialog(null, message,
              "Load Portfolio",
              JOptionPane.OK_CANCEL_OPTION);
      if (option == JOptionPane.OK_OPTION) {
        controller.loadPortfolio(fileName.getText());
        listModel.insertElementAt(fileName.getText(), listModel.size()-1);
        log.setText("Portfolio "+fileName.getText() + " has been loaded.");
      } else {
        promptMessage("Portfolio could not be loaded.");
      }}
    );

  }

  public void promptMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  public void logMessage(String message){
    log.setText(message);
  }

  public void openPortfolioScreen(HowToInvestControllerGUI controller) {
    JButton buyShares = new JButton("Buy Shares");
    listOfStrategies = new JComboBox(controller.getStrategies());
    listOfStrategies.setSelectedIndex(0);
    listOfStrategies.addActionListener(this);
    buyShares.addActionListener((ActionEvent e)->{
      JTextField stockSymbol = new JTextField();
      JTextField amount = new JTextField();
      JTextField date = new JTextField();
      JTextField commision = new JTextField();
      Object[] message = {
              "Stock :", stockSymbol,
              "Amount :", amount,
              "Date [yyyy-mm-dd] :", date,
              "Commision :", commision
      };

      int option = JOptionPane.showConfirmDialog(null, message, "Buy Shares",
              JOptionPane.OK_CANCEL_OPTION);
      if (option == JOptionPane.OK_OPTION) {
        try{

          controller.addStockToPortfolio(stockSymbol.getText(), Double.parseDouble(amount.getText()),
                  date.getText(), commision.getText());
        }catch(IllegalArgumentException ex){
          promptMessage("Transaction Unsuccessful. Please try again.");
        }
      } else {
        System.out.println("Transaction Unsuccessful");
      }
    });

    JButton displayStocks = new JButton( "Display Stocks In Portfolio");
    JButton invest = new JButton("Invest in Portfolio.");
    JButton applyStrategy = new JButton( "Apply Strategy");
    JButton saveStrategy = new JButton( "Save Portfolio");
    JButton getCostBasis = new JButton( "Get Portfolio Cost Basis for Date");
    JButton getValue = new JButton( "Get Portfolio Value for Date");
    JButton closeP = new JButton( "Close Portfolio");
    getCostBasis.addActionListener((ActionEvent e)->{
      JTextField date = new JTextField();
      JTextField commision = new JTextField();
      Object[] message = {
              "Date :", date
      };

      int option = JOptionPane.showConfirmDialog(null, message,
              "Portfolio Cost Basis",
              JOptionPane.OK_CANCEL_OPTION);
      if (option == JOptionPane.OK_OPTION) {
        String dateToQuery = date.getText();
        log.setText("Cost basis of Portfolio as of "+dateToQuery+" is "+
                controller.getPortfolioCostBasis(dateToQuery));
      } else {
        System.out.println("");
      }
    });
    getValue.addActionListener((ActionEvent e)->{
      JTextField date = new JTextField();
      Object[] message = {
              "Date :", date,
      };

      int option = JOptionPane.showConfirmDialog(null, message,
              "Portfolio Value",
              JOptionPane.OK_CANCEL_OPTION);
      if (option == JOptionPane.OK_OPTION) {
        String dateToQuery = date.getText();
        log.setText("Value of Portfolio as of "+dateToQuery+" is "+
                controller.getPortfolioValue(dateToQuery));
      } else {
        System.out.println("");
      }
    });

    displayStocks.addActionListener((ActionEvent e)->{
      JTextField date = new JTextField();
      Object[] message = {
              "Date :", date,
      };

      int option = JOptionPane.showConfirmDialog(null, message,
              "Display Stocks",
              JOptionPane.OK_CANCEL_OPTION);
      if (option == JOptionPane.OK_OPTION) {
        openStocksInPortfolio(controller,date.getText());
      } else {
        System.out.println("");
      }
    });

    applyStrategy.addActionListener((ActionEvent e)->{
      try{
        JTextField commision = new JTextField();
        Object[] message = {
                "Commision :", commision,
        };

        int option = JOptionPane.showConfirmDialog(null, message,
                "Apply Strategy",
                JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
          openStocksInPortfolio(controller,commision.getText());
        } else {
          System.out.println("");
        }
        String strategyToApply = listOfStrategies.getSelectedItem().toString();
        controller.applyStrategy(strategyToApply, commision.getText());
        log.setText("Strategy "+strategyToApply + " has been applied.");
        openPortfolio.setEnabled(false);
        closePortfolio.setEnabled(true);
      } catch(IllegalArgumentException ex){
      }
    });

    saveStrategy.addActionListener((ActionEvent e)->{
      try{
        JTextField fileName = new JTextField();
        Object[] message = {
                "Save as :", fileName,
        };

        int option = JOptionPane.showConfirmDialog(null, message,
                "Save Strategy",
                JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
          controller.savePortfolio(fileName.getText());
        } else {
          System.out.println("");
        }
        log.setText("Portfolio  "+fileName.getText() + " has been saved.");
        openPortfolio.setEnabled(false);
        closePortfolio.setEnabled(true);
      } catch(IllegalArgumentException ex){
        promptMessage("Portfolio could not be saved.");
      }
    });


    stockPanel.add(buyShares);
    stockPanel.add(displayStocks);
    stockPanel.add(invest);
    stockPanel.add(saveStrategy);
    stockPanel.add(applyStrategy);
    stockPanel.add(listOfStrategies);
    stockPanel.add(getCostBasis);
    stockPanel.add(getValue);
    stockPanel.add(closeP);

    closeP.addActionListener((ActionEvent e)->{
      stockPanel.removeAll();
      stockPanel.revalidate();
      stockPanel.repaint();
      openPortfolio.setEnabled(true);
      closePortfolio.setEnabled(false);});
    this.add(stockPanel);
    pack();
    setVisible(true);
  }

  private void openStocksInPortfolio(HowToInvestControllerGUI controller, String date) {

    log.setText("Stocks in portfolio have been opened for " + date);
    for (Component c : this.getContentPane().getComponents())    {
      if (c.equals(stocksPortfolioPanel)) {
        stocksPortfolioPanel.removeAll();
        stocksPortfolioPanel.revalidate();
        stocksPortfolioPanel.repaint();
      }
    }
    DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames) {

      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    HashMap<String, Double> stocks = controller.getStocksInPortfolio(date);
    for(Map.Entry<String, Double> stock: stocks.entrySet()){
      String stockName = stock.getKey();
      String numberOfShares = stock.getValue().toString();
      tableModel.addRow(new Object[]{stockName,numberOfShares});
    }

    JTable listTable;
    listTable = new JTable(tableModel);
//    listTable.setPreferredScrollableViewportSize(new Dimension(100, 200));
    listTable.setFillsViewportHeight(true);
    listTable.setPreferredSize(new Dimension(300,500));
    listTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    stocksPortfolioPanel.add(new JScrollPane(listTable));
    JButton closeP = new JButton( "Close Stock List");
    stocksPortfolioPanel.add(closeP);

    closeP.addActionListener((ActionEvent e)->{
      stocksPortfolioPanel.removeAll();
      stocksPortfolioPanel.revalidate();
      stocksPortfolioPanel.repaint();});
    stocksPortfolioPanel.setVisible(true);
    this.add(stocksPortfolioPanel);
  }
}

