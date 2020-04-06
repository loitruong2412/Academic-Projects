package commands;

import controller.ImageCommand;
import enums.Country;
import model.ImageModelPlus;

/**
 * This class represents the command Flag. The command has attributes of height, width, and country
 * of the flag we want to create. The command calls the generateFlag method and passes in the
 * attribute.
 */
public class Flag implements ImageCommand {
  private final int height;
  private final int width;
  private final Country country;

  /**
   * Constructor to create a Flag class. This constructor takes in three strings that represent the
   * height, width, and country of the flag, then tries to parse the height and width into integer
   * and country into Country enum.
   *
   * @param height        a string that represents the height of the flag
   * @param width         a string that represents the width of the flag
   * @param countryParsed a string that represents the country of the flag
   * @throws IllegalArgumentException if the country's flag is not supported
   */
  public Flag(String height, String width, String countryParsed) throws IllegalArgumentException {
    try {
      this.height = Integer.parseInt(height);
      this.width = Integer.parseInt(width);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid height and width for flag");
    }
    if (countryParsed.equals("France")) {
      this.country = Country.FRANCE;
    } else if (countryParsed.equals("Switzerland")) {
      this.country = Country.SWITZERLAND;
    } else if (countryParsed.equals("Greece")) {
      this.country = Country.GREECE;
    } else {
      this.country = null;
      throw new IllegalArgumentException("Missing or not supported country flag.");
    }
  }

  @Override
  public void run(ImageModelPlus model) {
    model.generateFlags(height, width, this.country);
  }
}
