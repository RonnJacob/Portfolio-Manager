package howtoinvest.view;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import howtoinvest.controller.HowToInvestControllerGUI;

/**
 * The following class is an implementation of the IHowToInvestViewGUI interface which implements
 * all the necessary operations specified in the interface. This class would represent a GUI based
 * view implementation of a strategy manager whereby the program and the necessary outputs/inputs
 * and messages would be displayed to the user through appropriate components of the Swing
 * framework. This class would implement the ActionListener, ListSelectionListener interfaces as
 * well because of the use of components such as JButtons, JTables, JLists.
 */
public class StrategyViewGUI extends JFrame implements
        IHowToInvestGUIView<HowToInvestControllerGUI> {

  private JList list;
  private JTextArea log = new JTextArea();
  private JTextField newStrategyName;
  private DefaultListModel listModel;

  /**
   * Initializing panels for the main strategy manager screen.
   */
  private JPanel strategyPanel = new JPanel();
  private JPanel listOfStrategiesPanel = new JPanel();
  private JPanel mainStrategyPanel = new JPanel();
  private JPanel loggerPanel = new JPanel();
  private JPanel stockDisplayPanel = new JPanel();

  /**
   * Initializing buttons for opening, loading, closing and creating a strategy.
   */
  private JButton createStrategy = new JButton("Create Strategy");
  private JButton openStrategy = new JButton("Open Strategy");
  private JButton closeStrategy = new JButton("Close Strategy");
  private JButton loadStrategy = new JButton("Load Strategy");

  /**
   * Constructor which would initialize the strategy manager screen and carries out the necessary
   * operations.
   */
  public StrategyViewGUI() {
    super();
    this.setTitle("How To Invest For Dummies - Strategy Manager");
    setLocation(200, 200);

    /**
     * Setting the dimensions of the frame as the maximum size of the screen.
     */
    Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
    this.setPreferredSize(DimMax);

    /**
     * Setting the grid layouts for the panels that are to be in the frame.
     */
    loggerPanel.setLayout(new GridLayout(3, 1));
    strategyPanel.setLayout(new GridLayout(6, 2));
    mainStrategyPanel.setLayout(new GridLayout(3, 2));
    listOfStrategiesPanel.setLayout(new GridLayout(3, 1));
    stockDisplayPanel.setLayout(new GridLayout(2, 12));
    log.setEditable(false);
    JScrollPane sp = new JScrollPane(log);

    /**
     * The logger panel would contain messages and would simulate a logger which would display
     * messages of operations that have been carried out on user interaction.
     */
    loggerPanel.add(new JLabel("Messages"));
    loggerPanel.add(sp);
    this.add(loggerPanel);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new GridLayout(6, 24));

  }

  /**
   * Adds panels containing buttons for creating, loading and opening a strategy along with a list
   * of available strategies. This would also include a text area for entering the name of a
   * strategy to be created which would have a focus listener.
   *
   * @param listItems the list of strategies that are to be displayed in the panel to carry out
   *                  strategy related operations.
   */
  @Override
  public void openHomeScreen(List<String> listItems) {
    /**
     * Creating a JList which would contain the strategies.
     */
    listOfStrategiesPanel.add(new JLabel("List Of Strategies"));
    listModel = new DefaultListModel();
    for (String listItem : listItems) {
      listModel.addElement(listItem);
    }
    list = new JList(listModel);
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    list.setSelectedIndex(0);
    JScrollPane listScrollPane = new JScrollPane(list);
    listOfStrategiesPanel.add(listScrollPane);
    listOfStrategiesPanel.add(createStrategy);

    /**
     * Adding a text field with a focus listener for the placeholder. This would be the area
     * where the user would enter the name of a strategy to be created.
     */
    newStrategyName = new JTextField("Enter Name Of Strategy Here");
    newStrategyName.setForeground(Color.GRAY);
    newStrategyName.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        /**
         * When the focus of the text area has been gained, empty the text area and allow
         * user to input the strategy name.
         */
        if (newStrategyName.getText().equals("Enter Name Of Strategy Here")) {
          newStrategyName.setText("");
          newStrategyName.setForeground(Color.BLACK);
        }
      }

      @Override
      public void focusLost(FocusEvent e) {
        /**
         * When the focus of the text area has been lost, set the placeholder text in the
         * text area.
         */
        if (newStrategyName.getText().isEmpty()) {
          newStrategyName.setForeground(Color.GRAY);
          newStrategyName.setText("Enter Name Of Strategy Here");
        }
      }
    });
    /**
     * Adding the create strategy button, textfield for accepting strategy name, load strategy
     * button and open strategy to mainStrategyPanel which is added to the frame.
     */
    mainStrategyPanel.add(createStrategy);
    mainStrategyPanel.add(newStrategyName);
    mainStrategyPanel.add(openStrategy);
    mainStrategyPanel.add(loadStrategy);
    this.add(listOfStrategiesPanel);
    this.add(mainStrategyPanel);
    pack();
    setVisible(true);
  }

  /**
   * This method would addActionListeners to the buttons created in the mainStrategyPanel which
   * would be the buttons for creating, opening and loading a strategy.
   *
   * @param controller type of controller.
   */
  public void addFeatures(HowToInvestControllerGUI controller) {
    /**
     * ActionListener for create strategy button.
     */
    createStrategy.addActionListener((ActionEvent e) -> {
              String strategyName = newStrategyName.getText();
              if (strategyName.trim().isEmpty() ||
                      strategyName.equals("Enter Name Of Strategy Here")) {
                promptMessage("Please enter strategy name.\n");
                return;
              }
              try {
                /**
                 * On successful creation of the strategy, the strategy name would be
                 * added to the list of strategies in the mainStrategyPanel.
                 */
                controller.addStrategy(strategyName);
                listModel.insertElementAt(strategyName, listModel.size() - 1);
                newStrategyName.setText("");
              } catch (IllegalArgumentException ex) {
                promptMessage("Failed to create Strategy: " + ex.getMessage());
              }
            }
    );

    /**
     * ActionListener for open strategy button.
     */
    openStrategy.addActionListener((ActionEvent e) -> {
              try {
                String strategyToOpen = list.getSelectedValue().toString();
                controller.openStrategies(strategyToOpen);
                log.setText("Strategy " + strategyToOpen + " has been opened.");
                openStrategy.setEnabled(false);
                closeStrategy.setEnabled(true);
              } catch (IllegalArgumentException ex) {
                promptMessage("Failed to open Strategy: " + ex.getMessage());
              }
            }
    );

    /**
     * ActionListener for load strategy button.
     */
    loadStrategy.addActionListener((ActionEvent e) -> {
              JTextField fileName = new JTextField();
              Object[] message = {
                      "Load Strategy :", fileName,
              };

              int option = JOptionPane.showConfirmDialog(null, message,
                      "Load Strategy",
                      JOptionPane.OK_CANCEL_OPTION);
              if (option == JOptionPane.OK_OPTION) {
                try {
                  /**
                   * On successful load of the strategy, the strategy name would be
                   * added to the list of strategies in the mainStrategyPanel.
                   */
                  controller.loadList(fileName.getText(), "Strategy");
                  listModel.insertElementAt(fileName.getText(), listModel.size() - 1);
                  String msg = "Strategy " + fileName.getText() + " has been loaded.";
                  log.setText(msg);
                  promptMessage(msg);
                } catch (IllegalArgumentException ex) {
                  promptMessage("STrategy could not be loaded: " + ex.getMessage());
                }
              }
              closeStrategyButtonClicked();
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
   * Displays the view to edit and manage the strategy supported by the controller. Relevant UI
   * controls along with the action listeners are added to the panel to support these operations.
   *
   * @param controller type of controller.
   */
  @Override
  public void openModificationScreen(HowToInvestControllerGUI controller) {
    /**
     * Initializing the buttons and JButtons for the portfolio related operations.
     */
    JButton displayStocks = new JButton("Display Stocks In Strategy");
    JButton addStock = new JButton("Add stock");
    JButton setAmount = new JButton("Set amount");
    JButton setFrequency = new JButton("Set frequency");
    JButton setTimeRange = new JButton("Set time-range");
    JButton saveStrategy = new JButton("Save Strategy");
    JButton setWeights = new JButton("Set Weights");
    JButton closeS = new JButton("Close Strategy");

    /**
     * Adding action listeners for each button that has been initialized above which is for
     * portfolio related operations.
     */
    displayStocks.addActionListener((ActionEvent e) -> {
      this.displayStocksActionListener(controller);
    });

    addStock.addActionListener((ActionEvent e) -> {
      this.addStocksActionListener(controller);
    });
    setAmount.addActionListener((ActionEvent e) -> {
      this.setAmountActionListener(controller);
    });
    setFrequency.addActionListener((ActionEvent e) -> {
      this.setFrequencyActionListener(controller);
    });

    setTimeRange.addActionListener((ActionEvent e) -> {
      this.setTimeRangeActionListener(controller);
    });

    saveStrategy.addActionListener((ActionEvent e) -> {
      this.saveStrategyActionListener(controller);
    });

    setWeights.addActionListener((ActionEvent e) -> {
      this.setWeightsActionListener(controller);
    });

    closeS.addActionListener((ActionEvent e) -> {
      closeStrategyButtonClicked();
    });

    /**
     * Adding the buttons related to the portfolio operations to strategyPanel which would be added
     * to the frame.
     */
    strategyPanel.add(displayStocks);
    strategyPanel.add(addStock);
    strategyPanel.add(setAmount);
    strategyPanel.add(setFrequency);
    strategyPanel.add(setTimeRange);
    strategyPanel.add(setWeights);
    strategyPanel.add(saveStrategy);
    strategyPanel.add(closeS);


    this.add(strategyPanel);
    pack();
    setVisible(true);
  }

  /**
   * This method would display stocks as in a strategy.
   *
   * @param controller the GUI controller.
   */
  private void displayStocksActionListener(HowToInvestControllerGUI controller) {
    try {
      openStocksInStrategy(controller);
      openStocksInStrategy(controller);
    } catch (IllegalArgumentException ex) {
      promptMessage("Display Unsuccessful: " + ex.getMessage());
    }
  }

  /**
   * This method add stocks to a strategy.
   *
   * @param controller the GUI controller.
   */
  private void addStocksActionListener(HowToInvestControllerGUI controller) {

    JTextField stockSymbol = new JTextField();
    Object[] message = {
            "Stock :", stockSymbol,
    };

    int option = JOptionPane.showConfirmDialog(null, message, "Add stock",
            JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
      try {
        controller.addStockToStrategy(stockSymbol.getText());
      } catch (IllegalArgumentException ex) {
        promptMessage("Add failed: " + ex.getMessage());
      }
    }
  }

  /**
   * This method set the amount in a strategy.
   *
   * @param controller the GUI controller.
   */
  private void setAmountActionListener(HowToInvestControllerGUI controller) {
    JTextField amount = new JTextField();
    Object[] message = {
            "Amount :", amount,
    };

    int option = JOptionPane.showConfirmDialog(null, message, "Set amount",
            JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
      try {
        controller.setStrategyAmount(amount.getText());
      } catch (IllegalArgumentException ex) {
        promptMessage("Action failed: " + ex.getMessage());
      }
    }
  }

  /**
   * This method would set the frequency in a strategy.
   *
   * @param controller the GUI controller.
   */
  private void setFrequencyActionListener(HowToInvestControllerGUI controller) {
    JTextField frequency = new JTextField();
    Object[] message = {
            "Frequency :", frequency,
    };

    int option = JOptionPane.showConfirmDialog(null, message, "Set amount",
            JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
      try {
        controller.setStrategyFrequency(frequency.getText());
      } catch (IllegalArgumentException ex) {
        promptMessage("Action failed: " + ex.getMessage());
      }
    }
  }

  /**
   * This method would set the time range in a strategy.
   *
   * @param controller the GUI controller.
   */
  private void setTimeRangeActionListener(HowToInvestControllerGUI controller) {
    JTextField begDate = new JTextField();
    JTextField endDate = new JTextField();
    Object[] message = {
            "Beginning date :", begDate,
            "End date :", endDate
    };

    int option = JOptionPane.showConfirmDialog(null, message, "Set amount",
            JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
      try {
        controller.setStrategyTimerange(begDate.getText(), endDate.getText());
      } catch (IllegalArgumentException ex) {
        promptMessage("Action failed: " + ex.getMessage());
      }
    }
  }

  /**
   * This method would save the strategy.
   *
   * @param controller the GUI controller.
   */
  private void saveStrategyActionListener(HowToInvestControllerGUI controller) {
    try {
      JTextField fileName = new JTextField();
      Object[] message = {
              "Save as :", fileName,
      };

      int option = JOptionPane.showConfirmDialog(null, message,
              "Save Strategy",
              JOptionPane.OK_CANCEL_OPTION);
      if (option == JOptionPane.OK_OPTION) {
        controller.saveStrategy(fileName.getText());
      } else {
        System.out.println("");
      }
      log.setText("Strategy  " + fileName.getText() + " has been saved.");
      openStrategy.setEnabled(false);
      closeStrategy.setEnabled(true);
    } catch (IllegalArgumentException ex) {
      promptMessage("Action failed: " + ex.getMessage());
    }
  }

  /**
   * This method would set the weights of the stocks as in a strategy.
   *
   * @param controller the GUI controller.
   */
  private void setWeightsActionListener(HowToInvestControllerGUI controller) {
    try {
      List<String> stocks = controller.getStocksInStrategy();
      Object[] message = new Object[(stocks.size() * 2)];
      int counter = 0;
      List<JTextField> listOfWeights = new ArrayList<>();
      for (String stockName : stocks) {
        message[counter] = "Enter weights for " + stockName;
        JTextField field = new JTextField();
        listOfWeights.add(field);
        message[counter + 1] = field;
        counter += 2;
      }
      int option = JOptionPane.showConfirmDialog(null, message,
              "Set weights",
              JOptionPane.OK_CANCEL_OPTION);
      if (option == JOptionPane.OK_OPTION) {

        TreeMap<String, Double> weights = new TreeMap<>();
        int i = 0;
        for (String stock : stocks) {
          weights.put(stock, Double.parseDouble(listOfWeights.get(i).getText()));
          i++;
        }
        controller.setStrategyWeights(weights);
      }
    } catch (IllegalArgumentException | IllegalStateException ex) {
      promptMessage("Action failed: " + ex.getMessage());
    }
  }

  /**
   * Displays the stocks within a strategy  by creating a scrollable table containing the names of
   * the stocks int he strategy
   *
   * @param controller the GUI controller.
   * @throws IllegalArgumentException if stocks cannot be retrieved for a portfolio for that date.
   */
  private void openStocksInStrategy(HowToInvestControllerGUI controller) {

    /**
     * Initializing the Object arrays for displaying the stocks on user input of the date.
     */
    Object[][] rowData = {};
    Object[] columnNames = {"Stock"};

    for (Component c : this.getContentPane().getComponents()) {
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

      List<String> stocks = controller.getStocksInStrategy();

      for (String stock : stocks) {
        String stockName = stock;
        tableModel.addRow(new Object[]{stockName});
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
      promptMessage("Action failed: " + ex.getMessage());
    }
  }

  /**
   * This method is a helper function which would handle the operations to take place on closing of
   * a strategy. This would include clearing the panel of any components specific to strategy
   * related operations.
   */
  private void closeStrategyButtonClicked() {
    for (Component c : this.getContentPane().getComponents()) {
      if (c.equals(stockDisplayPanel)) {
        stockDisplayPanel.removeAll();
        this.remove(stockDisplayPanel);
      }
    }
    strategyPanel.removeAll();
    strategyPanel.revalidate();
    strategyPanel.repaint();
    openStrategy.setEnabled(true);
    closeStrategy.setEnabled(false);
  }
}


