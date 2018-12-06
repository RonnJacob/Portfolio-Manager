package howtoinvest.view;

import java.util.List;

import javax.swing.*;

import howtoinvest.controller.HowToInvestControllerGUI;

public interface IHowToInvestGUIView<K> {
  void openHomeScreen(List<String> listItems);

  void addFeatures(K controller);

  void openModificationScreen(K controller);

  void promptMessage(String message);

  void logMessage(String message);
}
