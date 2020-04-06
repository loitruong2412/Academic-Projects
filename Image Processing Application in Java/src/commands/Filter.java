package commands;

import controller.ImageCommand;
import model.ImageModelPlus;

/**
 * This class represents the command Filter. This command needs the attribute a float 2D array which
 * is a kernel. It supports the go method that calls filter on the model while taking in the
 * attribute.
 */
public class Filter implements ImageCommand {
  private final float[][] kernel;

  /**
   * Constructor sets the needed attribute of a kernel. It parses the input for the 2D to set the as
   * the kernel attribute. The kernel has odd dimensions and is taken in as fractions.
   *
   * @param kernelParsed String, that is the input to parse and get the kernel
   * @throws IllegalArgumentException if the kernel is not the legal, either it is not a matrix or
   *                                  if its not inputted in fraction form
   */
  public Filter(String kernelParsed) throws IllegalArgumentException {
    String[] lines = kernelParsed.split(";");
    int width = lines.length;
    String[] cells = lines[0].split(",");
    int height = cells.length;
    if (height != width) {
      throw new IllegalArgumentException("Height and width of kernel has to be the same");
    }
    float[][] kernel = new float[width][height];
    try {
      for (int i = 0; i < width; i++) {
        String[] row = lines[i].split(",");
        kernel[i] = new float[row.length];
        for (int j = 0; j < row.length; j++) {
          String[] fraction = row[j].split("/");
          kernel[i][j] = Float.parseFloat(fraction[0]) / Float.parseFloat(fraction[1]);
        }
      }
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid kernel.");
    }
    this.kernel = kernel;
  }

  @Override
  public void run(ImageModelPlus model) {
    model.filter(this.kernel);
  }
}
