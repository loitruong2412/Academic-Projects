package commands;

import controller.ImageCommand;
import model.ImageModelPlus;

/**
 * This class represents the command Transform. This command needs the attribute a double 2D array
 * which is a matrix. It supports the go method that calls colorTrans on the model while taking in
 * the attribute.
 */
public class Transform implements ImageCommand {
  private final double[][] matrix;

  /**
   * Constructor sets the needed attribute of a matrix. It parses the input for the 2D to set the as
   * the matrix attribute. The matrix is always 3x3 dimensions and is taken in as fractions.
   *
   * @param matrixParsed String, that is the input to parse and get the matrix attribute
   * @throws IllegalArgumentException if the matrix is not the legal, either it is not a matrix or
   *                                  if its not inputted in decimal form
   */
  public Transform(String matrixParsed) throws IllegalArgumentException {
    String[] lines = matrixParsed.split(";");
    int width = lines.length;
    String[] cells = lines[0].split(",");
    int height = cells.length;
    if (height != width) {
      throw new IllegalArgumentException("Height and width of matrix has to be the same");
    }
    double[][] matrix = new double[width][height];
    try {
      for (int i = 0; i < width; i++) {
        String[] row = lines[i].split(",");
        matrix[i] = new double[row.length];
        for (int j = 0; j < row.length; j++) {
          matrix[i][j] = Double.parseDouble(row[j]);
        }
      }
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid matrix");
    }
    this.matrix = matrix;
  }

  @Override
  public void run(ImageModelPlus model) {
    model.colorTrans(this.matrix);
  }
}
