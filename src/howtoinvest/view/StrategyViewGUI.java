package howtoinvest.view;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import howtoinvest.controller.HowToInvestControllerGUI;

public class StrategyViewGUI extends JFrame implements ActionListener,
        ListSelectionListener {

  private JList list;
  private JPanel strategyPanel = new JPanel();
  private JPanel mainStrategyPanel = new JPanel();
  private JPanel loggerPanel = new JPanel();
  private JPanel stockDisplayPanel = new JPanel();
  private JTextArea log = new JTextArea();
  private JTextField newStrategyName;
  private JButton createStrategy = new JButton("Create Strategy");
  private JButton openStrategy = new JButton("Open Strategy");
  private JButton closeStrategy = new JButton("Close Strategy");
  private JButton loadStrategy = new JButton("Load Strategy");

  private static final Object[][] rowData = {};
  private static final Object[] columnNames = {"Stock"};
  private DefaultListModel listModel;

  public StrategyViewGUI() {
    super();
    setLocation(200, 200);
    Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
    this.setPreferredSize(DimMax);
    loggerPanel.setLayout(new GridLayout(6, 6));
    strategyPanel.setLayout(new GridLayout(6, 6));
    mainStrategyPanel.setLayout(new GridLayout(6, 6));
    stockDisplayPanel.setLayout(new GridLayout(2, 12));
    loggerPanel.add(log);
    this.add(loggerPanel);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    this.setLayout(new FlowLayout());
    this.setLayout(new GridLayout(6, 24));

  }

  public void openHomeScreen(List<String> listItems) {
    mainStrategyPanel.add(new JLabel("List Of Strategies"));
    listModel = new DefaultListModel();
    for (String listItem : listItems) {
      listModel.addElement(listItem);
    }
    list = new JList(listModel);
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    list.setSelectedIndex(0);
    list.addListSelectionListener(this);
    JScrollPane listScrollPane = new JScrollPane(list);
    mainStrategyPanel.add(listScrollPane);
    mainStrategyPanel.add(createStrategy);

    /**
     * Create Strategy Button.
     */
    JLabel strategyNameLabel = new JLabel("Enter strategy name to be created.");
    newStrategyName = new JTextField(15);
    createStrategy.addActionListener(this);
    mainStrategyPanel.add(createStrategy);
    openStrategy.addActionListener(this);
    mainStrategyPanel.add(openStrategy);
    mainStrategyPanel.add(strategyNameLabel);
    mainStrategyPanel.add(newStrategyName);
    loadStrategy.addActionListener(this);
    mainStrategyPanel.add(loadStrategy);
    this.add(mainStrategyPanel);
    pack();
    setVisible(true);
  }


  @Override
  public void actionPerformed(ActionEvent arg0) {

  }

  @Override
  public void valueChanged(ListSelectionEvent e) {

  }

  public void addFeatures(HowToInvestControllerGUI controller) {
    createStrategy.addActionListener((ActionEvent e) -> {
              String strategyName = newStrategyName.getText();
              if (strategyName.trim().isEmpty()) {
                promptMessage("Please enter strategy name.\n");
                return;
              }
              try {
                controller.addStrategy(strategyName);
                listModel.insertElementAt(strategyName, listModel.size() - 1);
                newStrategyName.setText("");
              } catch (IllegalArgumentException ex) {
                promptMessage("Failed to create Strategy: " + ex.getMessage());
              }
            }
    );

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
                  controller.loadList(fileName.getText(), "Strategy");
                  listModel.insertElementAt(fileName.getText(), listModel.size() - 1);
                  log.setText("Strategy " + fileName.getText() + " has been loaded.");
                } catch (IllegalArgumentException ex) {
                  promptMessage("Failed to load Strategy: " + ex.getMessage());
                }
              }
            }
    );

  }

  public void promptMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  public void logMessage(String message) {
    log.setText(message);
  }

  public void openStrategyScreen(HowToInvestControllerGUI controller) {
    JButton displayStocks = new JButton("Display Stocks In Strategy");
    displayStocks.addActionListener((ActionEvent e) -> {
      try {
        openStocksInStrategy(controller);
        openStocksInStrategy(controller);
      } catch (IllegalArgumentException ex) {
        promptMessage("Display Unsuccessful. Please try again.");
      }
    });
    JButton addStock = new JButton("Add stock");
    addStock.addActionListener((ActionEvent e) -> {
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
    });
    JButton setAmount = new JButton("Set amount");
    setAmount.addActionListener((ActionEvent e) -> {
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
    });
    JButton setFrequency = new JButton("Set frequency");
    setFrequency.addActionListener((ActionEvent e) -> {
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
    });
    JButton setTimeRange = new JButton("Set time-range");
    setTimeRange.addActionListener((ActionEvent e) -> {
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
    });
    JButton saveStrategy = new JButton("Save Strategy");
    saveStrategy.addActionListener((ActionEvent e) -> {
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
    });
    JButton setWeights = new JButton("Set Weights");
    setWeights.addActionListener((ActionEvent e) -> {
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

    });

    JButton closeS = new JButton("Close Strategy");
    strategyPanel.add(displayStocks);
    strategyPanel.add(addStock);
    strategyPanel.add(setAmount);
    strategyPanel.add(setFrequency);
    strategyPanel.add(setTimeRange);
    strategyPanel.add(setWeights);
    strategyPanel.add(saveStrategy);
    strategyPanel.add(closeS);

    closeS.addActionListener((ActionEvent e) -> {
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
    });
    this.add(strategyPanel);
    pack();
    setVisible(true);
  }

  private void openStocksInStrategy(HowToInvestControllerGUI controller) {

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
}


