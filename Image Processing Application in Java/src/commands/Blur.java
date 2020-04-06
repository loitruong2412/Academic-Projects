package commands;

import controller.ImageCommand;
import model.ImageModelPlus;

/**
 * This class represents the command blur. The command blur, supports the go method which calls the
 * method blur on the model.
 */
public class Blur implements ImageCommand {

  @Override
  public void run(ImageModelPlus model) {
    model.blur();
  }
}
