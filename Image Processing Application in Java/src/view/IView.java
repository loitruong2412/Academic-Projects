package view;

import java.awt.image.BufferedImage;

import controller.Features;

/**
 * This interface represents a graphical user interface, also known as the view of the program. This
 * interface contains all the methods that the controller needs to receive information from the view
 * and notify it on how the view should change.
 */
public interface IView {

  /**
   * SetFeatures method maps the view to call the methods in the controller as a response to an
   * interaction a user has with the program such as a menu item click. These methods in the
   * controller will handle all back end interaction and the view will be notified on how to
   * respond.
   *
   * @param f Features object that the view will have to call the controller methods on
   */
  void setFeatures(Features f);

  /**
   * DisplayImage method allows the controller to pass an image for the view to display. This view
   * only displays one photo at a time. If the image is too small, it will be scrollable. If the
   * image is loaded and is smaller than the window it will display in the center of the window.
   *
   * @param image BufferedImage image that needs to be currently displayed by the view
   */
  void displayImage(BufferedImage image);

  /**
   * Method to tell the view to display a panel to allow user to select the file in a location to
   * load an image. Returns a string representing the path of the file to load and read.
   *
   * @return String representing the file path to load the file
   */
  String loadFile();

  /**
   * Method to tell the view to display a panel to allow user to select location and provide a
   * filename to save an image.Returns a string representing the path of the file and filename to
   * save the file as.
   *
   * @return String representing the file path to save the file
   */
  String saveImage();

  /**
   * Method to tell the view to display a error or information message.
   *
   * @param message     String representing the message to be shown
   * @param messageType String "info" or "error" dependent on the type of message that needs to be
   *                    displayed
   */
  void displayMessage(String message, String messageType);

  /**
   * Method to tell the view to allow certain menuItems to be clickable. The undo and redo menu
   * items are being set to true if this method is called.
   *
   * @param menuItem String representing the menu item to be enables "undo" or "redo"
   */
  void enable(String menuItem);

  /**
   * Method ot tell the view to stop allowing certain menuItems to be clickable. The undo and redo
   * menu items are being set to false if this method is called.
   *
   * @param menuItem String representing the menu item to be disabled "undo" or "redo"
   */
  void disable(String menuItem);

  /**
   * Method to tell the view to set menu items to be clickable. It would be setting menuItems, as
   * true if this method is called.
   */
  void setMenuItemDisplay();
}
