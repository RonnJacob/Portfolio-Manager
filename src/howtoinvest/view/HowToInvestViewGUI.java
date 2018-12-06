package howtoinvest.view;

import java.util.Map;
import java.util.List;
import java.awt.Color;
import java.awt.Toolkit;
import java.util.HashMap;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import java.awt.event.FocusListener;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import howtoinvest.controller.HowToInvestControllerGUI;


/**
 * The following class is an implementation of the IHowToInvestViewGUI interface which implements
 * all the necessary operations specified in the interface. This class would represent a GUI based
 * view implementation of the interview whereby the program and the necessary outputs/inputs and
 * messages would be displayed to the user through appropriate components of the Swing framework.
 * This class would implement the ActionListener, ListSelectionListener interfaces as well because
 * of the use of components such as JButtons, JTables, JLists.
 */
public class HowToInvestViewGUI extends JFrame implements
        IHowToInvestGUIView<HowToInvestControllerGUI> {


  private JList list;
  private DefaultListModel listModel;
  private JTextArea log = new JTextArea();
  private JTextField newPortfolioName;

  /**
   * Initializing buttons for opening, loading, closing and creating a portfolio.
   */
  private JButton openPortfolio = new JButton("Open Portfolio");
  private JButton loadPortfolio = new JButton("Load Portfolio");
  private JButton closePortfolio = new JButton("Close Portfolio");
  private JButton createPortfolio = new JButton("Create Portfolio");

  /**
   * Initializing panels for the main portfolio manager screen.
   */
  private JPanel stockPanel = new JPanel();
  private JPanel stockDisplayPanel = new JPanel();
  private JPanel mainPortfolioPanel = new JPanel();
  private JPanel listOfPortfoliosPanel = new JPanel();

  private JComboBox listOfStrategies;

  /**
   * Initializing the Object arrays for displaying the stocks on user input of the date.
   */
  private static final Object[][] rowData = {};
  private static final Object[] columnNames = {"Stock", "Number Of Shares"};

  /**
   * Initializing a JTextField array for dynamic creation of text fields.
   */
  private static List<JTextField> listOfWeights = new ArrayList<>();


  /**
   * Constructor which would initialize the portfolio manager screen and carries out the necessary
   * operations.
   */
  public HowToInvestViewGUI() {
    super();
    setLocation(200, 200);
    this.setTitle("How To Invest For Dummies - Portfolio Manager");

    /**
     * Setting the dimensions of the frame as the maximum size of the screen.
     */
    Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
    this.setPreferredSize(DimMax);

    /**
     * Setting the grid layouts for the panels that are to be in the frame.
     */

    this.setLayout(new GridLayout(5, 2));
    stockPanel.setLayout(new GridLayout(6, 2));
    stockDisplayPanel.setLayout(new GridLayout(2, 12));
    mainPortfolioPanel.setLayout(new GridLayout(3, 2));
    listOfPortfoliosPanel.setLayout(new GridLayout(3, 1));

    /**
     * The logger panel would contain messages and would simulate a logger which would display
     * messages of operations that have been carried out on user interaction.
     */
    JPanel loggerPanel = new JPanel();
    JScrollPane sp = new JScrollPane(log);
    loggerPanel.setLayout(new GridLayout(3, 1));
    log.setEditable(false);
    loggerPanel.add(new JLabel("Messages"));
    loggerPanel.add(sp);
    this.add(loggerPanel);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }


  /**
   * Adds panels containing buttons for creating, loading and opening a portfolio along with a list
   * of available portfolios. This would also include a text area for entering the name of a
   * portfolio to be created which would have a focus listener.
   *
   * @param listItems the list of portfolios that are to be displayed in the panel to carry out
   *                  portfolio related operations.
   */
  @Override
  public void openHomeScreen(List<String> listItems) {

    /**
     * Creating a JList which would contain the portfolios.
     */
    listOfPortfoliosPanel.add(new JLabel("List Of Portfolios"));
    listModel = new DefaultListModel();
    for (String listItem : listItems) {
      listModel.addElement(listItem);
    }
    list = new JList(listModel);
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    list.setSelectedIndex(0);
    JScrollPane listScrollPane = new JScrollPane(list);
    listScrollPane.setPreferredSize(new Dimension(300, 500));
    listOfPortfoliosPanel.add(listScrollPane);
    listOfPortfoliosPanel.add(createPortfolio);

    /**
     * Adding a text field with a focus listener for the placeholder. This would be the area
     * where the user would enter the name of a portfolio to be created.
     */
    newPortfolioName = new JTextField("Enter Name Of Portfolio Here");
    newPortfolioName.setForeground(Color.GRAY);
    newPortfolioName.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        /**
         * When the focus of the text area has been gained, empty the text area and allow
         * user to input the portfolio name.
         */
        if (newPortfolioName.getText().equals("Enter Name Of Portfolio Here")) {
          newPortfolioName.setText("");
          newPortfolioName.setForeground(Color.BLACK);
        }
      }

      @Override
      public void focusLost(FocusEvent e) {
        /**
         * When the focus of the text area has been lost, set the placeholder text in the
         * text area.
         */
        if (newPortfolioName.getText().isEmpty()) {
          newPortfolioName.setForeground(Color.GRAY);
          newPortfolioName.setText("Enter Name Of Portfolio Here");
        }
      }
    });


    /**
     * Adding the create portfolio button, textfield for accepting portfolio name, load portfolio
     * button and open portfolio to mainPortfolioPanel which is added to the frame.
     */
    mainPortfolioPanel.add(createPortfolio);
    mainPortfolioPanel.add(newPortfolioName);
    mainPortfolioPanel.add(openPortfolio);
    mainPortfolioPanel.add(loadPortfolio);
    this.add(listOfPortfoliosPanel);
    this.add(mainPortfolioPanel);
    pack();
    setVisible(true);
  }


  /**
   * This method would addActionListeners to the buttons created in the mainPortfolioPanel which
   * would be the buttons for creating, opening and loading a portfolio.
   *
   * @param controller type of controller.
   */
  @Override
  public void addFeatures(HowToInvestControllerGUI controller) {
    /**
     * ActionListener for create portfolio button.
     */
    createPortfolio.addActionListener((ActionEvent e) -> {
              String portfolioName = newPortfolioName.getText();
              /**
               * Prompt user to enter portfolio name if the user input is empty or is the
               * placeholder.
               */
              if (portfolioName.trim().isEmpty() ||
                      portfolioName.equals("Enter Name Of Portfolio Here")) {
                promptMessage("Please enter portfolio name.\n");
                return;
              }
              try {
                /**
                 * On successful creation of the portfolio, the portfolio name would be
                 * added to the list of portfolios in the mainPortfolioPanel.
                 */
                controller.createPortfolio(portfolioName);
                listModel.insertElementAt(portfolioName, listModel.size() - 1);
                newPortfolioName.setText("");
                closePortfolioButtonClicked();
              } catch (IllegalArgumentException ex) {
                promptMessage("Portfolio " + portfolioName + " already exists.\n");
              }
            }
    );


    /**
     * ActionListener for open portfolio button.
     */
    openPortfolio.addActionListener((ActionEvent e) -> {
              try {
                String portfolioToOpen = list.getSelectedValue().toString();
                controller.openPortfolios(portfolioToOpen);
                log.setText("Portfolio " + portfolioToOpen + " has been opened.");
                openPortfolio.setEnabled(false);
                closePortfolio.setEnabled(true);
              } catch (IllegalArgumentException ex) {
                promptMessage("Unable to open portfolio " + ex.getMessage());
              }
            }
    );


    /**
     * ActionListener for load portfolio button.
     */
    loadPortfolio.addActionListener((ActionEvent e) -> {
              JTextField fileName = new JTextField();
              Object[] message = {
                      "Load Portfolio :", fileName,
              };

              int option = JOptionPane.showConfirmDialog(null, message,
                      "Load Portfolio",
                      JOptionPane.OK_CANCEL_OPTION);
              if (option == JOptionPane.OK_OPTION) {
                try {
                  /**
                   * On successful load of the portfolio, the portfolio name would be
                   * added to the list of portfolios in the mainPortfolioPanel.
                   */
                  controller.loadList(fileName.getText(), "Portfolio");
                  listModel.insertElementAt(fileName.getText(), listModel.size() - 1);
                  log.setText("Portfolio " + fileName.getText() + " has been loaded.");
                  closePortfolioButtonClicked();
                } catch (IllegalArgumentException ex) {
                  promptMessage("Portfolio could not be loaded: " + ex.getMessage());
                }
              }
            }
    );
  }

  /**
   * Displays a message dialog box with an appropriate message that is passed as an argument.
   *
   * @param message message to be prompted.
   */
  @Override
  public void promptMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  /**
   * Sets the text of the textarea in the logger panel with the message that is passed as an
   * argument.
   *
   * @param message message to be logged.
   */
  @Override
  public void logMessage(String message) {
    log.setText(message);
  }

  /**
   * @param controller type of controller.
   */
  @Override
  public void openModificationScreen(HowToInvestControllerGUI controller) {

    /**
     * Initializing the buttons and JButtons for the portfolio related operations.
     */
    JButton buyShares = new JButton("Buy Shares");
    listOfStrategies = new JComboBox(controller.showStrategies());
    ((JLabel) listOfStrategies.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
    listOfStrategies.setSelectedIndex(0);
    JButton displayStocks = new JButton("Display Stocks In Portfolio");
    JButton invest = new JButton("Invest With Custom Weights");
    JButton investEqualWeights = new JButton("Invest With Equal Weights");
    JButton applyStrategy = new JButton("Apply Strategy");
    JButton savePortfolio = new JButton("Save Portfolio");
    JButton getCostBasis = new JButton("Get Portfolio Cost Basis for Date");
    JButton getValue = new JButton("Get Portfolio Value for Date");
    JButton closeP = new JButton("Close Portfolio");
    JTextField dateField = new JTextField("Enter Date To Invest [yyyy-mm-dd]");
    dateField.setForeground(Color.GRAY);


    /**
     * Adding action listeners for each button that has been initialized above which is for
     * portfolio related operations.
     */

    displayStocks.addActionListener((ActionEvent e) -> {
      this.displayStocksActionListener(controller);
    });

    buyShares.addActionListener((ActionEvent e) -> {
      this.buyShares(controller);
    });
    getCostBasis.addActionListener((ActionEvent e) -> {
      this.getCostBasisActionListener(controller);
    });
    getValue.addActionListener((ActionEvent e) -> {
      this.getPortfolioValueListener(controller);
    });


    applyStrategy.addActionListener((ActionEvent e) -> {
      this.applyStrategyListener(controller);

    });

    savePortfolio.addActionListener((ActionEvent e) -> {
      this.savePortfolioListener(controller);
    });

    invest.addActionListener((ActionEvent e) -> {
      this.investWithCustomWeights(controller, dateField.getText());

    });

    investEqualWeights.addActionListener((ActionEvent e) -> {
      this.investInEqualWeights(controller);
    });

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

    closeP.addActionListener((ActionEvent e) -> {
      closePortfolioButtonClicked();
    });

    /**
     * Adding the buttons related to the portfolio operations to stockPanel which would be added
     * to the frame.
     */
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
    this.add(stockPanel);
    pack();
    setVisible(true);
  }

  /**
   * This method would display stocks as of a particular date which is input by the user via a
   * dialog box in the yyyy-mm-dd format.
   *
   * @param controller the GUI controller.
   */
  private void displayStocksActionListener(HowToInvestControllerGUI controller) {
    JTextField date = new JTextField();
    Object[] message = {
            "Date [yyyy-mm-dd] :", date,
    };

    int option = JOptionPane.showConfirmDialog(null, message, "Display Shares",
            JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
      try {
        if (date.getText().equals("")) {
          promptMessage("Enter date input.");
          return;
        }
        openStocksInPortfolio(controller, date.getText());
        openStocksInPortfolio(controller, date.getText());
      } catch (IllegalArgumentException ex) {
        promptMessage("Display Unsuccessful. Please try again: " + ex.getMessage());
      }
    }
  }

  /**
   * This method would retrieve stock ticker symbol, amount, date in yyyy-mm-dd format and
   * commission from the user through a dialog box and would carry out the operations for buying the
   * share(s) by passing the input data to the controller.
   *
   * @param controller the GUI controller.
   */
  private void buyShares(HowToInvestControllerGUI controller) {
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
      try {
        controller.addStockToPortfolio(stockSymbol.getText(), Double.parseDouble(amount.getText()),
                date.getText(), commision.getText());
      } catch (IllegalArgumentException ex) {
        promptMessage("Transaction Unsuccessful: " + ex.getMessage());
      }
    }
  }

  /**
   * This method would retrieve the portfolio cost basis for a particular date which is input by
   * user through a dialog box.
   *
   * @param controller the GUI controller.
   */
  private void getCostBasisActionListener(HowToInvestControllerGUI controller) {
    JTextField date = new JTextField();
    JTextField commision = new JTextField();
    Object[] message = {
            "Date :", date
    };

    int option = JOptionPane.showConfirmDialog(null, message,
            "Portfolio Cost Basis",
            JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
      try {
        String dateToQuery = date.getText();
        String costbasis = String.format("%.2f", controller.getPortfolioCostBasis(dateToQuery));
        log.setText("Cost basis of Portfolio as of " + dateToQuery + " is " + costbasis);
      } catch (IllegalArgumentException ex) {
        promptMessage("Action failed: " + ex.getMessage());
      }
    }
  }

  /**
   * This method would retrieve the portfolio value for a particular date which is input by user
   * through a dialog box.
   *
   * @param controller the GUI controller.
   */
  private void getPortfolioValueListener(HowToInvestControllerGUI controller) {
    JTextField date = new JTextField();
    Object[] message = {
            "Date :", date,
    };

    int option = JOptionPane.showConfirmDialog(null, message,
            "Portfolio Value",
            JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
      try {
        String dateToQuery = date.getText();
        String value = String.format("%.2f", controller.getPortfolioValue(dateToQuery));
        log.setText("Value of Portfolio as of " + dateToQuery + " is " + value);
      } catch (IllegalArgumentException ex) {
        promptMessage("Action failed: " + ex.getMessage());
      }
    }
  }

  /**
   * This method would apply the strategy for which the option has been selected in the
   * listOfStrategies JComboBox and asks the user to input the commission for applying the
   * strategy.
   *
   * @param controller the GUI controller.
   */
  private void applyStrategyListener(HowToInvestControllerGUI controller) {
    try {
      JTextField commision = new JTextField();
      Object[] message = {
              "Commision :", commision,
      };

      int option = JOptionPane.showConfirmDialog(null, message,
              "Apply Strategy",
              JOptionPane.OK_CANCEL_OPTION);
      if (option == JOptionPane.OK_OPTION) {
        openStocksInPortfolio(controller, commision.getText());
      } else {
        System.out.println("");
      }
      /**
       * Applies the strategy that is selected in the JComboBox.
       */
      String strategyToApply = listOfStrategies.getSelectedItem().toString();
      controller.applyStrategy(strategyToApply, commision.getText());
      log.setText("Strategy " + strategyToApply + " has been applied.");
      openPortfolio.setEnabled(false);
      closePortfolio.setEnabled(true);
    } catch (IllegalArgumentException ex) {
      promptMessage("Action failed: " + ex.getMessage());
    }
  }

  /**
   * This method would request the user to enter a file name under which the portfolio is to be
   * saved.
   *
   * @param controller the GUI controller.
   */
  private void savePortfolioListener(HowToInvestControllerGUI controller) {
    try {
      JTextField fileName = new JTextField();
      Object[] message = {
              "Save as :", fileName,
      };

      /**
       * Request the user to enter the file name under which portfolio is to be saved. Portfolios
       * will be saved as .json files.
       */
      int option = JOptionPane.showConfirmDialog(null, message,
              "Save Portfolio",
              JOptionPane.OK_CANCEL_OPTION);
      if (option == JOptionPane.OK_OPTION) {
        controller.savePortfolio(fileName.getText());
      } else {
        System.out.println("");
      }
      log.setText("Portfolio  " + fileName.getText() + " has been saved.");
      openPortfolio.setEnabled(false);
      closePortfolio.setEnabled(true);
    } catch (IllegalArgumentException ex) {
      promptMessage("Portfolio could not be saved: " + ex.getMessage());
    }
  }

  /**
   * This method would request the user to enter the date for investing, commission and amount, and
   * weights for each stock in the portfolio for carrying out the investment operation.
   *
   * @param controller the GUI controller.
   * @param date       the date for which the investment is to be made.
   */
  private void investWithCustomWeights(HowToInvestControllerGUI controller, String date) {
    JTextField commision = new JTextField();
    JTextField amount = new JTextField();
    if (date.equals("") || date.equals("Enter Date To Invest [yyyy-mm-dd]")) {
      promptMessage("Please enter a date to invest in stocks.");
      return;
    }
    try {
      /**
       * Dynamic creation of the dialog box with the text fields that are the same number of stocks
       * of portfolios as of the input date.
       */
      HashMap<String, Double> stocks = controller.getStocksInPortfolio(date);
      Object[] message = new Object[(stocks.size() * 2) + 6];
      int counter = 0;
      for (String stockName : stocks.keySet()) {
        message[counter] = "Enter weights for " + stockName;
        JTextField field = new JTextField();
        listOfWeights.add(field);
        message[counter + 1] = field;
        counter += 2;
      }
      message[counter++] = "Amount: ";
      message[counter++] = amount;
      message[counter++] = "Commission: ";
      message[counter++] = commision;

      int option = JOptionPane.showConfirmDialog(null, message,
              "Invest",
              JOptionPane.OK_CANCEL_OPTION);
      if (option == JOptionPane.OK_OPTION) {
      } else {
        System.out.println("");
      }

      /**
       * Adding weights to a list of weights inorder to pass it to the controller to carry
       * out investment operation with custom weights.
       */
      List<Double> weights = new LinkedList<>();
      for (int i = 0; i < listOfWeights.size(); i++) {
        weights.add(Double.parseDouble(listOfWeights.get(i).getText()));
      }

      controller.investWithWeights(Double.parseDouble(amount.getText()), date,
              commision.getText(), weights);

    } catch (IllegalArgumentException | IllegalStateException ex) {
      promptMessage("Action failed: " + ex.getMessage());
    }
  }


  /**
   * This method would request the user to enter the date for investing, commission and amount in a
   * dialog box and carries out necessary operations.
   *
   * @param controller that GUI controller
   */
  private void investInEqualWeights(HowToInvestControllerGUI controller) {
    JTextField dateToInvest = new JTextField();
    JTextField commision = new JTextField();
    JTextField amount = new JTextField();
    Object[] message = {
            "Date [yyyy-mm-dd] :", dateToInvest,
            "Amount: ", amount,
            "Commision: ", commision
    };
    int option = JOptionPane.showConfirmDialog(null, message,
            "Invest",
            JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
      try {
        controller.investEqually(Double.parseDouble(amount.getText()), dateToInvest.getText(),
                commision.getText());
      } catch (IllegalArgumentException | IllegalStateException ex) {
        promptMessage("Invalid input. Please enter correct input.");
      }
    }
  }


  /**
   * Displays the stocks within a portfolio as of a particular date by creating a scrollable table
   * containing the name of the stock and the number of shares as of that date within the
   * portfolio.
   *
   * @param controller the GUI controller.
   * @param date       the date for which the user wishes to display the stocks in a portfolio.
   * @throws IllegalArgumentException if stocks cannot be retrieved for a portfolio for that date.
   */
  private void openStocksInPortfolio(HowToInvestControllerGUI controller, String date)
          throws IllegalArgumentException {

    /**
     * Check if a list of stocks is being displayed already. If so, remove the list and refresh
     * the list with the new list of stocks in the portfolio.
     */
    for (Component c : this.getContentPane().getComponents()) {
      if (c.equals(stockDisplayPanel)) {
        stockDisplayPanel.removeAll();
        stockDisplayPanel.revalidate();
        stockDisplayPanel.repaint();
      }
    }

    /**
     * Creating a table for displaying the list of stocks in the portfolio.
     */
    DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames) {

      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    try {

      HashMap<String, Double> stocks = controller.getStocksInPortfolio(date);

      /**
       * Populating the table with the date which would be the stock ticker symbol and the number
       * of shares bought as of that date.
       */
      for (Map.Entry<String, Double> stock : stocks.entrySet()) {
        String stockName = stock.getKey();
        String numberOfShares = stock.getValue().toString();
        tableModel.addRow(new Object[]{stockName, numberOfShares});
      }

      JTable listTable;
      listTable = new JTable(tableModel);
      listTable.setFillsViewportHeight(true);
      listTable.setPreferredSize(new Dimension(300, 150));
      listTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      stockDisplayPanel.add(new JScrollPane(listTable));
      JButton closeP = new JButton("Close Stock List");
      closeP.addActionListener((ActionEvent e) -> {
        stockDisplayPanel.removeAll();
        stockDisplayPanel.revalidate();
        stockDisplayPanel.repaint();
      });

      stockDisplayPanel.add(closeP);
      stockDisplayPanel.setVisible(true);
      this.add(stockDisplayPanel);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  /**
   * This method is a helper function which would handle the operations to take place on closing of
   * a portfolio. This would include clearing the panel of any components specific to portfolio
   * related operations.
   */
  private void closePortfolioButtonClicked() {
    for (Component c : this.getContentPane().getComponents()) {
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
    this.repaint();
  }


}


