package howtoinvest.view;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import howtoinvest.controller.HowToInvestControllerGUI;

public class StrategyViewGUI extends JFrame implements ActionListener,
        ListSelectionListener {

  private JList list;
  private JPanel stockPanel = new JPanel();
  private JPanel mainStrategyPanel = new JPanel();
  private JPanel loggerPanel = new JPanel();
  private JLabel log = new JLabel();
  private JTextField newStrategyName;
  private JButton createStrategy = new JButton("Create Strategy");
  private JButton openStrategy = new JButton("Open Strategy");
  private JButton closeStrategy = new JButton("Close Strategy");
  private DefaultListModel listModel;

  public StrategyViewGUI() {
    super();
    setLocation(200, 200);
    Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
    this.setPreferredSize(DimMax);
    loggerPanel.setLayout(new GridLayout(6,6));
    stockPanel.setLayout(new GridLayout(6, 6));
    mainStrategyPanel.setLayout(new GridLayout(6, 6));
    loggerPanel.add(log);
    this.add(loggerPanel);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    this.setLayout(new FlowLayout());
    this.setLayout(new GridLayout(6, 24));

  }

  public void openHomeScreen(List<String> listItems) {
    mainStrategyPanel.add(new JLabel("List Of STrategies"));
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
    createStrategy.addActionListener((ActionEvent e)-> {
              String strategyName = newStrategyName.getText();
              if(strategyName.equals("")){
                promptMessage("Please enter strategy name.\n");
                return;
              }
              try{
                controller.addStrategy(strategyName);
                listModel.insertElementAt(strategyName, listModel.size()-1);
                newStrategyName.setText("");
              } catch(IllegalArgumentException ex){
                promptMessage("Strategy "+strategyName+" already exists.\n");
              }
            }
    );

    openStrategy.addActionListener((ActionEvent e)-> {
              try{

                String strategyToOpen = list.getSelectedValue().toString();
                controller.openStrategies(strategyToOpen);
                log.setText("Strategy "+strategyToOpen + " has been opened.");
                openStrategy.setEnabled(false);
                closeStrategy.setEnabled(true);
              } catch(IllegalArgumentException ex){
              }
            }
    );

  }

  public void promptMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  public void logMessage(String message){
    log.setText(message);
  }

  public void openStrategyScreen(HowToInvestControllerGUI controller) {
    JButton buyShares = new JButton("Add stock");
    buyShares.addActionListener((ActionEvent e)->{
      JTextField stockSymbol = new JTextField();
      Object[] message = {
              "Stock :", stockSymbol,
      };

      int option = JOptionPane.showConfirmDialog(null, message, "Add stock",
              JOptionPane.OK_CANCEL_OPTION);
      if (option == JOptionPane.OK_OPTION) {
        try{
          controller.addStockToStrategy(stockSymbol.getText());
        }catch(IllegalArgumentException ex){
          promptMessage("Add failed. Please try again.");
        }
      } else {
        System.out.println("Add failed");
      }
    });
    JButton invest = new JButton("Invest in Strategy.");
    JButton applyStrategy = new JButton( "Apply Strategy");
    JButton getCostBasis = new JButton( "Get Strategy Cost Basis for Date");
    JButton getValue = new JButton( "Get Strategy Value for Date");
    JButton closeP = new JButton( "Close Strategy");
    stockPanel.add(buyShares);
    stockPanel.add(invest);
    stockPanel.add(applyStrategy);

    stockPanel.add(getCostBasis);
    stockPanel.add(getValue);
    stockPanel.add(closeP);

    closeP.addActionListener((ActionEvent e)->{
      stockPanel.removeAll();
      stockPanel.revalidate();
      stockPanel.repaint();
      openStrategy.setEnabled(true);
      closeStrategy.setEnabled(false);});
    this.add(stockPanel);
    pack();
    setVisible(true);
  }
}


