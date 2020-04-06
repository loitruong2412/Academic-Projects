package commands;

import controller.ImageCommand;
import model.ImageModelPlus;

/**
 * This class represents the Sepia command. The command sepia, supports the go method which
 * calls the method sepia on the model.
 */
public class Sepia implements ImageCommand {

  @Override
  public void run(ImageModelPlus model) {
    model.sepia();
  }
}
