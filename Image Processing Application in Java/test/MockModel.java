import enums.Country;
import enums.Direction;
import model.ImageModelPlus;

/**
 * This class is a MockModel which represents a dummy model to ensure that the calls to the model
 * are occurring properly. All methods available for the current ImageModelPlus interface are
 * overwritten to append to a attribute of a StringBuilder log.
 */
public class MockModel implements ImageModelPlus {
  private StringBuilder log;

  /**
   * Constructs a mock model that takes in a StringBuilder log.
   *
   * @param log a StringBuilder used for logging all the methods called to the mock model by the
   *            controller.
   */
  public MockModel(StringBuilder log) {
    this.log = log;
    this.log.append("model...");
  }

  @Override
  public void dither() {
    this.log.append("dither\n");
  }

  @Override
  public void mosaic(int seeds) throws IllegalArgumentException {
    this.log.append("mosaic\n");
  }

  @Override
  public void generateRainbow(int height, int width, Direction direction)
          throws IllegalArgumentException {
    this.log.append("rainbow\n");
  }

  @Override
  public void generateCheckerBoard(int squareSize) throws IllegalArgumentException {
    this.log.append("checkerboard\n");
  }

  @Override
  public void blur() {
    this.log.append("blur\n");
  }

  @Override
  public void sharpen() {
    this.log.append("sharpen\n");
  }

  @Override
  public void filter(float[][] kernel) throws IllegalArgumentException {
    this.log.append("filter\n");
  }

  @Override
  public void greyscale() {
    this.log.append("greyscale\n");
  }

  @Override
  public void sepia() {
    this.log.append("sepia\n");
  }

  @Override
  public void colorTrans(double[][] matrix) throws IllegalArgumentException {
    this.log.append("transform\n");
  }

  @Override
  public void generateFlags(int height, int width, Country country)
          throws IllegalArgumentException {
    this.log.append("flag\n");
  }

  @Override
  public int[][][] getData() {
    this.log.append("data\n");
    return new int[0][][];
  }

  @Override
  public String toString() {
    return this.log.toString();
  }

}

