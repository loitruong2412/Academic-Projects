package commands;

import controller.ImageCommand;
import model.ImageModelPlus;

/**
 * This class represents the command Checkerboard. The command has an attribute of squareSize
 * which is the size of the 8x8 squares pixel size. The command calls the generateRainbow
 * method and passes in the attributes.
 */
public class Checkerboard implements ImageCommand {
  private final int squareSize;

  /**
   * Constructor to create a Checkerboard class. This constructor takes in a string to sets up the
   * attribute of the squareSize by parsing it to integer.
   *
   * @param squareSize a string, that represents the size of each square in the board by number of
   *                   pixels
   * @throws IllegalArgumentException if the square size is invalid and cannot be parsed into
   *                                  integers
   */
  public Checkerboard(String squareSize) throws IllegalArgumentException {
    try {
      this.squareSize = Integer.parseInt(squareSize);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid square size for checkerboard");
    }
  }

  @Override
  public void run(ImageModelPlus model) {
    model.generateCheckerBoard(squareSize);
  }
}
