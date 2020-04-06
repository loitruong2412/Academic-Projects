package commands;

import controller.ImageCommand;
import model.ImageModelPlus;

/**
 * This class represents the Sharpen command. The command sharpen, supports the go method which
 * calls the method sharpen on the model.
 */
public class Sharpen implements ImageCommand {

  @Override
  public void run(ImageModelPlus model) {
    model.sharpen();
  }
}
