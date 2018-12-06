package howtoinvest.view;

import java.util.List;

/**
 * The following interface represents a GUI view for an application to to manage data supported by
 * the controller K. The following interface would handle receiving specific input if necessary and
 * passing these inputs to the controller to carry out necessary options. The=is GUI view has two
 * layers one for managing a set of items and then another layer for editing those items.
 *
 * @param <K> type of controller.
 */
public interface IHowToInvestGUIView<K> {
  /**
   * Displays the home screen of the application. The method would display options for the user to
   * create items, getting the list of items present and entering a item to perform operations.
   */
  void openHomeScreen(List<String> listItems);

  /**
   * Adds action listeners for the operations for this view.
   *
   * @param controller type of controller.
   */
  void addFeatures(K controller);

  /**
   * Displays the view to edit and manage the data supported by the controller.
   *
   * @param controller type of controller.
   */
  void openModificationScreen(K controller);

  /**
   * Prompts a message with the given message.
   *
   * @param message message to be prompted.
   */
  void promptMessage(String message);

  /**
   * Adds the given message to the log.
   *
   * @param message message to be logged.
   */
  void logMessage(String message);
}
