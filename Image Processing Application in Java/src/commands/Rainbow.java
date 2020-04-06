package commands;

import controller.ImageCommand;
import enums.Direction;
import model.ImageModelPlus;

/**
 * This class represents the command Rainbow. The command has attributes of height, width, and
 * direction of the rainbow we want to create. The command calls the generateFlag method and passes
 * in the attributes.
 */
public class Rainbow implements ImageCommand {
  private final int height;
  private final int width;
  private final Direction direction;

  /**
   * Constructor to create a Rainbow class. This constructor takes in three strings that represent
   * the height, width, and direction of the rainbow, then tries to parse the height and width into
   * integer and direction into Direction enum.
   *
   * @param height a string that represents the height of the rainbow
   * @param width a string that represents the width of the rainbow
   * @param directionParsed a string that represents the width of the rainbow
   * @throws IllegalArgumentException if the height or width of the rainbow is invalid and cannot be
   *                                  parsed into integers, or the direction is invalid
   */
  public Rainbow(String height, String width, String directionParsed)
          throws IllegalArgumentException {
    try {
      this.height = Integer.parseInt(height);
      this.width = Integer.parseInt(width);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid height and width for rainbow");
    }
    if (directionParsed.equals("vertical")) {
      this.direction = Direction.VERTICAL;
    }
    else if (directionParsed.equals("horizontal")) {
      this.direction = Direction.HORIZONTAL;
    }
    else {
      this.direction = null;
      throw new IllegalArgumentException("Missing or invalid direction for rainbow.");
    }
  }

  @Override
  public void run(ImageModelPlus model) {
    model.generateRainbow(height, width, this.direction);
  }
}
