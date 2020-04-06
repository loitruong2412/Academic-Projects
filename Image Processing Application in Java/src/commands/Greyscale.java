package commands;

import controller.ImageCommand;
import model.ImageModelPlus;

/**
 * This class represents the Greyscale command. The command Greyscale, supports the go method which
 * calls the method greyscale on the model.
 */
public class Greyscale implements ImageCommand {

  @Override
  public void run(ImageModelPlus model) {
    model.greyscale();
  }
}
