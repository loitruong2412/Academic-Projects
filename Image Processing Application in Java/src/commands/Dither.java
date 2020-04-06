package commands;

import controller.ImageCommand;
import model.ImageModelPlus;

/**
 * This class represents the command Dither. The supported methods calls the dither method on the
 * model.
 */
public class Dither implements ImageCommand {

  @Override
  public void run(ImageModelPlus model) {
    model.dither();
  }
}
