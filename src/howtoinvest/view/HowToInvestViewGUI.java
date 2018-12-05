package howtoinvest.view;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
  private JPanel listOfPortfoliosPanel = new JPanel();
  private JPanel stocksPortfolioPanel = new JPanel();
  private JPanel stockDisplayPanel = new JPanel();
  private JTextField log = new JTextField();
  private JTextField newPortfolioName;
  private JButton createPortfolio = new JButton("Create Portfolio");
  private JButton openPortfolio = new JButton("Open Portfolio");
  private JButton closePortfolio = new JButton("Close Portfolio");
  private JButton openStrategyMenu = new JButton("Open Strategy Menu");
  private JButton loadPortfolio = new JButton("Load Portfolio");
  private DefaultListModel listModel;
  private JComboBox listOfStrategies;
  private static List<JLabel> listOfStocks = new ArrayList<JLabel>();
  private static List<JTextField> listOfweights = new ArrayList<JTextField>();
  private static final Object[][] rowData = {};
  private static final Object[] columnNames = {"Stock", "Number Of Shares"};


  public HowToInvestViewGUI() {
    super();
    setLocation(200, 200);
    Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
    this.setPreferredSize(DimMax);
    loggerPanel.setLayout(new GridLayout(3,1));
    stockPanel.setLayout(new GridLayout(6, 2));
    stockDisplayPanel.setLayout(new GridLayout(2,12));
    listOfPortfoliosPanel.setLayout(new GridLayout(3,1));
    mainPortfolioPanel.setLayout(new GridLayout(3, 2));
    log.setEditable(false);
    loggerPanel.add(new JLabel("Messages"));
    loggerPanel.add(log);
    this.add(loggerPanel);
    createPortfolio.setText("Create Portfolio");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    this.setLayout(new FlowLayout());
    this.setLayout(new GridLayout(5, 2));

  }

  public void openHomeScreen(List<String> listItems) {
    listOfPortfoliosPanel.add(new JLabel("List Of Portfolios"));
    listModel = new DefaultListModel();
    for (String listItem : listItems) {
      listModel.addElement(listItem);
    }
    list = new JList(listModel);
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    list.setSelectedIndex(0);
    list.addListSelectionListener(this);
    JScrollPane listScrollPane = new JScrollPane(list);
    listScrollPane.setPreferredSize(new Dimension(300, 500));
    listOfPortfoliosPanel.add(listScrollPane);
    listOfPortfoliosPanel.add(createPortfolio);

    /**
     * Create Portfolio Button.
     */
    newPortfolioName = new JTextField("Enter Name Of Portfolio Here");
    newPortfolioName.setForeground(Color.GRAY);
    newPortfolioName.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        if (newPortfolioName.getText().equals("Enter Name Of Portfolio Here")) {
          newPortfolioName.setText("");
          newPortfolioName.setForeground(Color.BLACK);
        }
      }
      @Override
      public void focusLost(FocusEvent e) {
        if (newPortfolioName.getText().isEmpty()) {
          newPortfolioName.setForeground(Color.GRAY);
          newPortfolioName.setText("Enter Name Of Portfolio Here");
        }
      }
    });
    createPortfolio.addActionListener(this);
    mainPortfolioPanel.add(createPortfolio);
    mainPortfolioPanel.add(newPortfolioName);
    openPortfolio.addActionListener(this);
    mainPortfolioPanel.add(openPortfolio);
    loadPortfolio.addActionListener(this);
    mainPortfolioPanel.add(loadPortfolio);
    openStrategyMenu.addActionListener(this);
    mainPortfolioPanel.add(openStrategyMenu);
    this.add(listOfPortfoliosPanel);
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
        if(portfolioName.equals("")||
                portfolioName.equals("Enter portfolio name to be created.")){
          promptMessage("Please enter portfolio name.\n");
          return;
        }
        try{
          controller.createPortfolio(portfolioName);
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
    JButton displayStocks = new JButton( "Display Stocks In Portfolio");
    displayStocks.addActionListener((ActionEvent e)->{
      JTextField stockSymbol = new JTextField();
      JTextField amount = new JTextField();
      JTextField date = new JTextField();
      JTextField commision = new JTextField();
      Object[] message = {
              "Date [yyyy-mm-dd] :", date,
      };

      int option = JOptionPane.showConfirmDialog(null, message, "Display Shares",
              JOptionPane.OK_CANCEL_OPTION);
      if (option == JOptionPane.OK_OPTION) {
        try{
          if(date.getText().equals("")){
            promptMessage("Enter date input.");
            return;
          }
          openStocksInPortfolio(controller,date.getText());
          openStocksInPortfolio(controller,date.getText());
        }catch(IllegalArgumentException ex){
          promptMessage("Display Unsuccessful. Please try again.");
        }
      } else {
        System.out.println("Display Unsuccessful");
      }
    });
    JButton buyShares = new JButton("Buy Shares");
    listOfStrategies = new JComboBox(controller.getStrategies());
    ((JLabel) listOfStrategies.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
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
//          openStocksInPortfolio(controller,date.getText());
        }catch(IllegalArgumentException ex){
          promptMessage("Transaction Unsuccessful. Please try again.");
        }
      } else {
        System.out.println("Transaction Unsuccessful");
      }
    });

    JButton invest = new JButton("Invest With Custom Weights");
    JButton investEqualWeights = new JButton("Invest With Equal Weights");
    JButton applyStrategy = new JButton( "Apply Strategy");
    JButton savePortfolio = new JButton( "Save Portfolio");
    JTextField dateField = new JTextField("Enter Date To Invest [yyyy-mm-dd]");
    dateField.setForeground(Color.GRAY);
    dateField.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        if (dateField.getText().equals("Enter Date To Invest [yyyy-mm-dd]")) {
          dateField.setText("");
          dateField.setForeground(Color.BLACK);
        }
      }
      @Override
      public void focusLost(FocusEvent e) {
        if (dateField.getText().isEmpty()) {
          dateField.setForeground(Color.GRAY);
          dateField.setText("Enter Date To Invest [yyyy-mm-dd]");
        }
      }
    });
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

    savePortfolio.addActionListener((ActionEvent e)->{
      try{
        JTextField fileName = new JTextField();
        Object[] message = {
                "Save as :", fileName,
        };

        int option = JOptionPane.showConfirmDialog(null, message,
                "Save Portfolio",
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

    invest.addActionListener((ActionEvent e)->{

      JTextField commision = new JTextField();
      JTextField amount = new JTextField();
      String date = dateField.getText();
      if(date.equals("") || date.equals("Enter Date To Invest [yyyy-mm-dd]")){
        promptMessage("Please enter a date to invest in stocks.");
        return;
      }
      try{
        HashMap<String, Double> stocks = controller.getStocksInPortfolio(date);
        Object[] message = new Object[(stocks.size()*2)+6];
        int counter = 0;
        for(String stockName: stocks.keySet()){
          message[counter] = "Enter weights for "+stockName;
          JTextField field = new JTextField();
          listOfweights.add(field);
          message[counter+1] = field;
          counter+=2;
        }
        message[counter++] = "Amount: ";
        message[counter++] = amount;
        message[counter++] = "Commission: ";
        message[counter++] = commision;

        int option = JOptionPane.showConfirmDialog(null, message,
                "Invest",
                JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
//          openStocksInPortfolio(controller,date.getText());
        } else {
          System.out.println("");
        }
         List<Double> weights = new LinkedList<>();
        for(int i=0; i<listOfweights.size();i++){
          weights.add(Double.parseDouble(listOfweights.get(i).getText()));
        }

        controller.investWithWeights(Double.parseDouble(amount.getText()), date,
                commision.getText(), weights);
        openStocksInPortfolio(controller,date);

      } catch(IllegalArgumentException | IllegalStateException ex){
        promptMessage("Invalid input. Please enter correct input.");
      }
    });

    investEqualWeights.addActionListener((ActionEvent e)->{

      JTextField dateToInvest = new JTextField();
      JTextField commision = new JTextField();
      JTextField amount = new JTextField();
      try{
        Object[] message = {
                "Date [yyyy-mm-dd] :", dateToInvest,
                "Amount: ",amount,
                "Commision: ", commision
        };


        int option = JOptionPane.showConfirmDialog(null, message,
                "Invest",
                JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
//          openStocksInPortfolio(controller,date.getText());
        } else {
          System.out.println("");
        }
        List<Double> weights = new LinkedList<>();
        controller.investEqually(Double.parseDouble(amount.getText()), dateToInvest.getText(),
                commision.getText());
        openStocksInPortfolio(controller,dateToInvest.getText());

      } catch(IllegalArgumentException | IllegalStateException ex){
        promptMessage("Invalid input. Please enter correct input.");
      }
    });


    stockPanel.add(buyShares);
    stockPanel.add(displayStocks);
    stockPanel.add(applyStrategy);
    stockPanel.add(listOfStrategies);
    stockPanel.add(getCostBasis);
    stockPanel.add(getValue);
    stockPanel.add(savePortfolio);
    stockPanel.add(investEqualWeights);
    stockPanel.add(invest);
    stockPanel.add(dateField);
    stockPanel.add(closeP);

    closeP.addActionListener((ActionEvent e)->{
      for (Component c : this.getContentPane().getComponents())    {
        if (c.equals(stockDisplayPanel)) {
          stockDisplayPanel.removeAll();
          this.remove(stockDisplayPanel);
        }
      }
      stockPanel.removeAll();
      stockPanel.revalidate();
      stockPanel.repaint();
      openPortfolio.setEnabled(true);
      closePortfolio.setEnabled(false);
      this.revalidate();
      this.repaint();});
    this.add(stockPanel);
    pack();
    setVisible(true);
  }

  private void openStocksInPortfolio(HowToInvestControllerGUI controller, String date) {

    for (Component c : this.getContentPane().getComponents())    {
      if (c.equals(stockDisplayPanel)) {
        stockDisplayPanel.removeAll();
        stockDisplayPanel.revalidate();
        stockDisplayPanel.repaint();
      }
    }
    DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames) {

      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    try {

      HashMap<String, Double> stocks = controller.getStocksInPortfolio(date);

      for (Map.Entry<String, Double> stock : stocks.entrySet()) {
        String stockName = stock.getKey();
        String numberOfShares = stock.getValue().toString();
        tableModel.addRow(new Object[]{stockName, numberOfShares});
      }

      JTable listTable;
      listTable = new JTable(tableModel);
      listTable.setFillsViewportHeight(true);
      listTable.setPreferredSize(new Dimension(300,150));
      listTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      stockDisplayPanel.add(new JScrollPane(listTable));
      JButton closeP = new JButton( "Close Stock List");
      closeP.addActionListener((ActionEvent e)->{
        stockDisplayPanel.removeAll();
        stockDisplayPanel.revalidate();
        stockDisplayPanel.repaint();});

      stockDisplayPanel.add(closeP);

      stockDisplayPanel.setVisible(true);

      this.add(stockDisplayPanel);
    }catch(IllegalArgumentException ex){
      promptMessage("Enter valid date.");
    }
  }


}


