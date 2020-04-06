import java.awt.image.BufferedImage;

import controller.Features;
import view.IView;

/**
 * This class is a MockView which represents a dummy view to ensure that the calls to the view
 * are occurring properly. All methods available for the current IView interface are
 * overwritten to append to a attribute of a StringBuilder log.
 */
public class MockView implements IView {
  private StringBuilder log;

  /**
   * Constructs a mock view that takes in a StringBuilder log.
   *
   * @param log a StringBuilder used for logging all the methods called to the mock view by the
   *            controller.
   */
  public MockView(StringBuilder log) {
    this.log = log;
    this.log.append("view...");
  }
  @Override
  public void setFeatures(Features f) {
    this.log.append("setting features\n");
  }

  @Override
  public void displayImage(BufferedImage image) {
    this.log.append("displaying image\n");
  }

  @Override
  public String loadFile() {
    this.log.append("load image\n");
    return "dog.jpg";
  }

  @Override
  public String saveImage() {
    this.log.append("save image\n");
    return "save image\n";
  }

  @Override
  public void displayMessage(String message, String messageType) {
    this.log.append("displaying message\n");
  }

  @Override
  public void enable(String menuItem) {
    this.log.append("displaying image\n");
  }

  @Override
  public void disable(String menuItem) {
    this.log.append("disable menu item\n");
  }

  @Override
  public void setMenuItemDisplay() {
    this.log.append("enable menu item\n");
  }
}
