package model;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import enums.Country;
import enums.Direction;
import enums.RGB;

/**
 * This class is the implementation of the model.ImageModel Interface and has all the operations
 * that the interface offers. It can store an image that is loaded to it and the image's derived
 * height and width.
 */
public class ImageModelImpl implements ImageModel {
  protected int[][][] imageData;
  protected int height;
  protected int width;

  /**
   * Constructs an image processor that can load in images for image processing. This constructor
   * will take in a 3D array of integers that represents an image and is used for processing image.
   *
   * @param imageData a 3D array of integers that represents an image. The first two sub-arrays are
   *                  for accessing the pixel location and the third subarray stores the RGB value
   *                  of that pixel.
   */
  public ImageModelImpl(int[][][] imageData) {
    this.height = imageData.length;
    this.width = imageData[1].length;

    int[][][] copyData = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        copyData[i][j][0] = imageData[i][j][0];
        copyData[i][j][1] = imageData[i][j][1];
        copyData[i][j][2] = imageData[i][j][2];
      }
    }
    this.imageData = copyData;
  }

  /**
   * Constructs an image processor that can load in images for image processing. This constructor
   * does not take in any image input and is used for generating images from scratch.
   */
  public ImageModelImpl() {
    //Empty Constructor for a model.ImageModelImpl Object to generate images.
  }

  @Override
  public void generateRainbow(int height, int width, Direction direction) {
    Color[] colors = new Color[]{Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN,
                                 Color.BLUE, Color.MAGENTA};

    // Checks where the change is suppose to occur for every x pixel and sets it to the current max.
    int changeLocation = changeLoca(height, width, colors.length, direction);
    int max = changeLocation;
    this.imageData = new int[height][width][3];
    int i = 0;

    // Loops through all the pixels from top left to bottom right.
    for (int h = 0; h < height; h++) {
      for (int w = 0; w < width; w++) {
        // Checks if the current location is the max location. True, change color and update max.
        if (isMax(h, w, max, direction)) {
          i++;
          max += changeLocation;
        } else {
          // Check for Vertical stripes to reset color at the start of the left hand side.
          if ((direction == Direction.VERTICAL) && (w == 0)) {
            i = 0;
            max = changeLocation;
          }
        }
        // Checks if there is a next color.
        if (i == colors.length) {
          i--;
        }
        // Set color values to the correct spot in the 3d array that corresponds to the pixel.
        Color color = colors[i];
        updateRGB(h, w, color);
      }
    }
    this.height = height;
    this.width = width;
  }

  /**
   * Private helper method for generate rainbow to compare current pixel location with the location
   * that the color is suppose to switch. Returns true, if at that location. Checks w if rainbow
   * with vertical stripes and h otherwise.
   *
   * @param h         int that represents the current vertical pixel
   * @param w         int that represents the current horizontal pixel
   * @param max       int that represents the location that the color should change
   * @param direction Direction object that represents the direction the stripes should go VERTICAL
   *                  or HORIZONTAL
   * @return boolean that is true if the color is to switch at that location otherwise false
   */
  private boolean isMax(int h, int w, int max, Direction direction) {
    if (direction.equals(Direction.VERTICAL)) {
      return (w == max);
    }
    return (h == max);
  }

  /**
   * Private helper method for generate rainbow. Checks the location and helps set the location of
   * where the color changes by returning the pixel number.
   *
   * @param height      int height of the image in pixels
   * @param width       int width of the image in pixels
   * @param lengthArray int length of the array that is the colors
   * @param direction   Direction object of Horizontal or Vertical stripes
   * @return int representing the location that the color for the pixel is to change
   * @throws IllegalArgumentException if the height or width is less than 1
   */
  private int changeLoca(int height, int width, int lengthArray, Direction direction) throws
          IllegalArgumentException {
    if (height < 1 || width < 1) {
      throw new IllegalArgumentException("There must be at least 1 pixel declared for height and "
              + "width.");
    }
    if (direction.equals(Direction.VERTICAL)) {
      return changeLocationHelper(width, lengthArray, direction);
    }
    return changeLocationHelper(height, lengthArray, direction);
  }

  /**
   * Helper method to calculate the change location pixel based on number of pixels of the important
   * side.
   *
   * @param importantSide int to divide by (vertical : width) (horizontal : height)
   * @param lengthArray   int that represents the length of the color array
   * @param direction     Direction object of Horizontal or Vertical stripes
   * @return int that represents the change location based
   * @throws IllegalArgumentException if the important side is less than the length of color array
   *                                  meaning there are not enough pixels to draw all the colors of
   *                                  the rainbow
   */
  private int changeLocationHelper(int importantSide, int lengthArray, Direction direction)
          throws IllegalArgumentException {
    int location;

    if (importantSide < lengthArray) {
      throw new IllegalArgumentException("You cannot create a rainbow with fewer than 7 pixels.");
    } else if (importantSide >= 42) {
      location = (int) Math.ceil((double) importantSide / lengthArray);
    } else {
      location = importantSide / lengthArray;
    }
    return location;
  }

  @Override
  public void generateCheckerBoard(int squareSize) throws IllegalArgumentException {
    if (squareSize < 1) {
      throw new IllegalArgumentException("Square size cannot be less than 1 pixel");
    }
    int size = squareSize * 8;
    BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
    Graphics graphics = image.getGraphics();
    paintCheckerBoard(graphics, squareSize);
    this.height = size;
    this.width = size;
    this.imageData = new int[image.getHeight()][image.getWidth()][3];
    updateImageData(image.getHeight(), image.getWidth(), image);
    graphics.dispose();
  }

  /**
   * A helper method that paints the black and white square on a graphics object.
   *
   * @param graphics   a Graphics object to be painted on
   * @param squareSize int representing the pixels number for length of the side of the squares
   */
  private void paintCheckerBoard(Graphics graphics, int squareSize) {
    int size = squareSize * 8;
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, size, size);
    graphics.setColor(Color.BLACK);
    for (int i = 0; i < 8; i++) {
      for (int j = i % 2; j < 8; j += 2) {
        graphics.fillRect(i * squareSize, j * squareSize, squareSize, squareSize);
      }
    }
  }

  /**
   * A helper method used for creating a Buffer Image object from an image. Represented by a 3D
   * array, with specified height and width. This is used for the filter process as the filter
   * process can only operates on a Buffer Image.
   *
   * @param imageData a 3D array that represents an image
   * @param height    an int, that sets the height of the Buffer Image, measured in pixels
   * @param width     an int, that sets the width of the Buffer Image, measured in pixels
   * @return a Buffer Image object
   */
  private BufferedImage getBufferImage(int[][][] imageData, int height, int width) {
    BufferedImage buff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = imageData[i][j][0];
        int g = imageData[i][j][1];
        int b = imageData[i][j][2];
        int color = (r << 16) + (g << 8) + b;
        buff.setRGB(j, i, color);
      }
    }
    return buff;
  }

  /**
   * As specified in the interface, this method is used by intermediate to advanced users for
   * filtering an image. It creates a Buffer Image object from the current image and applies the
   * filter directly to the Buffer Image object. The Buffer Image's data is then copied back to the
   * current image. This might seem like an unnecessary process but the ConvolutionOP class only
   * works on Buffer Image process, and unarguable by using this approach the code is much cleaner
   * than the tradition approach.
   *
   * @param kernel a 2D array of floats
   * @throws IllegalArgumentException if the kernel is null or empty or its dimension is not an odd
   *                                  number
   */
  @Override
  public void filter(float[][] kernel) throws IllegalArgumentException {
    if (kernel == null || kernel.length % 2 == 0 || kernel[0].length % 2 == 0) {
      throw new IllegalArgumentException("Kernel cannot be null or empty or has even dimension.");
    }
    BufferedImage buffer = this.getBufferImage(this.imageData, this.imageData.length,
            this.imageData[1].length);
    float[] flatKernel = kernelFlatten(kernel);
    Kernel filter = new Kernel(kernel.length, kernel.length, flatKernel);
    BufferedImageOp op = new ConvolveOp(filter);
    buffer = op.filter(buffer, null);
    this.imageData = new int[buffer.getHeight()][buffer.getWidth()][3];
    updateImageData(buffer.getHeight(), buffer.getWidth(), buffer);
  }

  @Override
  public void blur() {
    float[][] kernel = {
            {1f / 16f, 1f / 8f, 1f / 16f},
            {1f / 8f, 1f / 4f, 1f / 8f},
            {1f / 16f, 1f / 8f, 1f / 16f}};
    this.filter(kernel);
  }

  @Override
  public void sharpen() {
    float[][] kernel = {
            {-1f / 8f, -1f / 8f, -1f / 8f, -1f / 8f, -1f / 8f},
            {-1f / 8f, 1f / 4f, 1f / 4f, 1f / 4f, -1f / 8f},
            {-1f / 8f, 1f / 4f, 1f, 1f / 4f, -1f / 8f},
            {-1f / 8f, 1f / 4f, 1f / 4f, 1f / 4f, -1f / 8f},
            {-1f / 8f, -1f / 8f, -1f / 8f, -1f / 8f, -1f / 8f}};
    this.filter(kernel);
  }

  /**
   * A helper method that flattens a 2D array to 1D array. That array is used to work with built-in
   * Kernel class.
   *
   * @param kernel a 2D array of floats
   * @return a float 1D array that represents the kernel
   */
  private float[] kernelFlatten(float[][] kernel) {
    float[] flatten = new float[kernel.length * kernel.length];
    int s = 0;
    for (int i = 0; i < kernel.length; i++) {
      for (int j = 0; j < kernel.length; j++) {
        flatten[s] = kernel[i][j];
        s++;
      }
    }
    return flatten;
  }

  @Override
  public void greyscale() {
    double[][] greyscale = {
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}};
    colorTrans(greyscale);
  }

  @Override
  public void sepia() {
    double[][] sepia = {
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.169},
            {0.272, 0.534, 0.131}};
    colorTrans(sepia);
  }

  @Override
  public void colorTrans(double[][] matrix) throws IllegalArgumentException {
    if (matrix == null || matrix.length != 3 || matrix[0].length != 3) {
      throw new IllegalArgumentException("Matrix does not exist and needs to be 3x3.");
    }
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        double red = imageData[i][j][0];
        double green = imageData[i][j][1];
        double blue = imageData[i][j][2];
        double[] pixRGB = {red, green, blue};

        int newR = newValue(pixRGB, RGB.RED, matrix);
        int newG = newValue(pixRGB, RGB.GREEN, matrix);
        int newB = newValue(pixRGB, RGB.BLUE, matrix);

        this.imageData[i][j][0] = clampCheck(newR);
        this.imageData[i][j][1] = clampCheck(newG);
        this.imageData[i][j][2] = clampCheck(newB);
      }
    }
  }

  /**
   * Private helper to limit the correct RGB values. This is to prevent RGB values to be out of the
   * legal range of 0 - 255.
   *
   * @param rgbValue the value of red, green or blue values
   * @return 0 or 255, if input is lower or higher than those numbers respectively, otherwise
   *          returns the inputted value
   */
  int clampCheck(int rgbValue) {
    int min = 0;
    int max = 255;
    if (rgbValue < min) {
      return min;
    } else if (rgbValue > max) {
      return max;
    } else {
      return rgbValue;
    }
  }

  /**
   * Private method to calculate the new value of the red, green or blue light. Needs to be called
   * for each light once for the respective values.
   *
   * @param rgb    double array that stores the pixel's RGB
   * @param color  RGB the color light that is being calculated
   * @param matrix 2D double array that represents the color transformation matrix
   * @return int which is the new value of the the red, green or blue light
   */
  private int newValue(double[] rgb, RGB color, double[][] matrix) {
    int i;
    double value = 0;

    if (color.equals(RGB.RED)) {
      i = 0;
    } else if (color.equals(RGB.GREEN)) {
      i = 1;
    } else if (color.equals(RGB.BLUE)) {
      i = 2;
    } else {
      throw new IllegalStateException("Not a valid color.");
    }

    for (int j = 0; j < matrix.length; j++) {
      value += (matrix[i][j] * rgb[j]);
    }
    return (int) value;
  }

  /**
   * Helper method to adds a black border around a flag.
   *
   * @param i         the height index of a pixel
   * @param j         the width index of a pixel
   * @param newHeight the height of the flag
   * @param newWidth  the width of the flag
   */
  private void addBorder(int i, int j, int newHeight, int newWidth) {
    if ((i == newHeight && j < newWidth) || (j == newWidth && i < newHeight)
            || (j == newWidth && i == newHeight)) {
      updateRGB(i, j, Color.BLACK);
    }
  }

  /**
   * Method that generates an image of the flag of France with the specified height and width.
   *
   * @param height the height (an int), in pixels, of the image specified by the user
   * @param width  the width (an int), in pixels, of the image specified by the user
   * @throws IllegalArgumentException if the height is less than 3 pixels and width less than 2
   *                                  pixels because French flag has 3 vertical stripes and its
   *                                  height x width dimension is 2 x 3
   */
  private void generateFranceFlag(int height, int width) throws IllegalArgumentException {
    if (width < 3 && height < 2) {
      throw new IllegalArgumentException("Height and width not optimal.");
    }
    this.height = height;
    this.width = width;
    this.imageData = new int[height][width][3];
    Color blue = new Color(0, 35, 149);
    Color red = new Color(237, 41, 57);
    int newWidth;
    int tempThickness = width / 3;
    int thickness;
    int tempWidth = tempThickness * 3;
    int newHeight = ((tempWidth * 2) / 3);
    if (newHeight > height) {
      newWidth = ((height * 3) / 2);
      thickness = newWidth / 3;
    } else {
      newWidth = tempWidth;
      thickness = tempThickness;
    }
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (j < thickness && i < newHeight) {
          updateRGB(i, j, blue);
        } else if (j > thickness && j < thickness * 2 && i < newHeight) {
          updateRGB(i, j, Color.WHITE);
        } else if (j > thickness * 2 && j < newWidth && i < newHeight) {
          updateRGB(i, j, red);
        } else {
          updateRGB(i, j, Color.WHITE);
        }
        addBorder(i, j, newHeight, newWidth);
      }
    }
  }

  /**
   * Method that generates an image of the flag of Switzerland with the specified height and width.
   *
   * @param height the height (an int), in pixels, of the image specified by the user
   * @param width  the width (an int), in pixels, of the image specified by the user
   * @throws IllegalArgumentException if height and width are under 32 because under 32 pixels, the
   *                                  proportion of the flag are distorted
   */
  private void generateSwitzerlandFlag(int height, int width) throws IllegalArgumentException {
    if (height < 32 || width < 32) {
      throw new IllegalArgumentException("Size is not optimal for creating a Swiss flag");
    }
    this.height = height;
    this.width = width;
    int size;
    if (height > width) {
      size = width;
    } else {
      size = height;
    }
    Color red = new Color(213, 43, 30);
    int crossThickness = (size * 6) / 32;
    int ratio1 = (size * 13) / 32;
    int ratio2 = crossThickness;
    int newSize = (crossThickness * 32) / 6;
    this.imageData = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (((j > ratio2) && (i > ratio1 && i < ratio1 + ratio2)) && ((j < size - ratio2)
                && (i > ratio1 && i < ratio1 + ratio2)) || (((i > ratio2)
                && (j > ratio1 && j < ratio1 + ratio2)) && ((i < size - ratio2)
                && (j > ratio1 && j < ratio1 + ratio2))) || (i > newSize || j > newSize)) {
          updateRGB(i, j, Color.WHITE);
        } else {
          updateRGB(i, j, red);
        }
        addBorder(i, j, newSize, newSize);
      }
    }
  }

  /**
   * Method that generates an image of the flag of Greece with the specified height and width.
   *
   * @param height the height (an int), in pixels, of the image specified by the user
   * @param width  the width (an int), in pixels, of the image specified by the user
   * @throws IllegalArgumentException if width is less than 14 and height is less than 9 because the
   *                                  flag of Greece has 9 stripes and its height x width proportion
   *                                  is 2 x 3
   */
  private void generateGreeceFlag(int height, int width) {
    if (height < 9 && width < 27) {
      throw new IllegalArgumentException("Height and Width not optimal");
    }
    this.height = height;
    this.width = width;
    this.imageData = new int[height][width][3];
    Color blue = new Color(13, 94, 175);
    int tempThickness = height / 9;
    int newHeight;
    int thickness;
    int tempHeight = tempThickness * 9;
    int newWidth = (tempHeight * 3) / 2;
    if (newWidth > width) {
      newHeight = (width * 2) / 3;
      thickness = newHeight / 9;
    } else {
      newHeight = tempHeight;
      thickness = tempThickness;
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (i < thickness || (i > thickness * 2 && i < thickness * 3)
                || (i > thickness * 4 && i < thickness * 5)
                || (i > thickness * 6 && i < thickness * 7)
                || (i > thickness * 8 && i < thickness * 9)) {
          updateRGB(i, j, blue);
        } else {
          updateRGB(i, j, Color.WHITE);
        }
        if (i > newHeight || j > newWidth) {
          updateRGB(i, j, Color.WHITE);
        }
        addBorder(i, j, newHeight, newWidth);
      }
    }
    int crossSize = thickness * 5;
    int ratio1 = thickness;
    int ratio2 = crossSize * 2 / 5;
    for (int m = 0; m < crossSize; m++) {
      for (int n = 0; n < crossSize; n++) {
        if ((m > ratio2 && m < ratio1 + ratio2) || (n > ratio2 && n < ratio1 + ratio2)) {
          updateRGB(m, n, Color.WHITE);

        } else {
          updateRGB(m, n, blue);
        }
      }
    }
  }

  @Override
  public void generateFlags(int height, int width, Country country)
          throws IllegalArgumentException {
    switch (country) {
      case FRANCE:
        generateFranceFlag(height, width);
        break;
      case GREECE:
        generateGreeceFlag(height, width);
        break;
      case SWITZERLAND:
        generateSwitzerlandFlag(height, width);
        break;
      default:
        break;
    }
  }

  /**
   * A helper method that helps set the pixel RGB values of an image (represented by a 3D array)
   * using the pixel RGB values from a Buffer Image.
   */
  private void updateImageData(int height, int width, BufferedImage image) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int color = image.getRGB(j, i);
        Color c = new Color(color);
        updateRGB(i, j, c);
      }
    }
  }

  /**
   * A helper method that helps change the current RGB values of an image pixel to a specified
   * color's RGB values.
   *
   * @param indexH the row index of the pixel
   * @param indexW the column index of the pixel
   * @param color  the color whose RGB values we want to set our image's pixel's RGB values to
   */
  protected void updateRGB(int indexH, int indexW, Color color) {
    this.imageData[indexH][indexW][0] = color.getRed();
    this.imageData[indexH][indexW][1] = color.getGreen();
    this.imageData[indexH][indexW][2] = color.getBlue();
  }

  @Override
  public int[][][] getData() {
    int[][][] copyData = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        copyData[i][j][0] = this.imageData[i][j][0];
        copyData[i][j][1] = this.imageData[i][j][1];
        copyData[i][j][2] = this.imageData[i][j][2];
      }
    }
    return copyData;
  }

}