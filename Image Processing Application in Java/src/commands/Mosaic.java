package commands;

import controller.ImageCommand;
import model.ImageModelPlus;

/**
 * This class represents the Mosaic command. The command Mosaic, supports the go method which
 * calls the method mosaic on the model.
 */
public class Mosaic implements ImageCommand {
  private final int seeds;

  /**
   * Constructor to create a Mosaic class. This constructor takes in a string to sets up the
   * attribute of the seeds by parsing it to integer.
   *
   * @param seeds the number of randomly chosen pixels for mosaicing an image
   * @throws IllegalArgumentException if the seeds is invalid or cannot be parsed into integers
   */
  public Mosaic(String seeds) throws IllegalArgumentException {
    try {
      this.seeds = Integer.parseInt(seeds);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid number of seeds");
    }
  }

  @Override
  public void run(ImageModelPlus model) {
    model.mosaic(this.seeds);
  }
}
