package model;

import enums.Country;
import enums.Direction;

/**
 * This is an interface that represents an image processing model. This model is currently able to
 * store one image. This interface offers some image processing features such as filter or color
 * transformation. In addition, the interface offers methods for creating certain images with
  * array. The model handles all the data processing to handle the processes.
 */
public interface ImageModel {

  /**
   * Generate rainbow method takes in the height, width of the picture and the direction of the
   * stripes. Generates a rainbow made of the following colors {Red, Orange, Yellow, Green, Cyan,
   * Blue, Magenta}.
   *
   * @param height an int, which is the height of the image specified by the user,
   *               measured in pixels
   * @param width an int, which is the width of the image specified by the user, measured in pixels
   * @param direction an enum that represents the direction of the rainbow specified by user,
   *                  VERTICAL or HORIZONTAL
   * @throws IllegalArgumentException if width or height is less than one pixel, or if the
   *          important side (vertical : width) (horizontal : height) is less than 7 pixels since
   *          rainbows have at least 7 colors
   */
  void generateRainbow(int height, int width, Direction direction) throws IllegalArgumentException;

  /**
   * Method that generates an 8x8 black and white checkerboard image. Each square size is
   * specified by the user.
   *
   * @param squareSize an int specified by user for how big the squares of the checkerboard should
   *                   be in pixels
   * @throws IllegalArgumentException if the size of the squares is less than 1 pixel
   */
  void generateCheckerBoard(int squareSize) throws IllegalArgumentException;

  /**
   * Method that blurs an image that is loaded to the processing model using a simple predefined 3x3
   * kernel. This method is for beginner users who just want to simply blur an image.
   */
  void blur();

  /**
   * Method that sharpens an image that is loaded to the processing model using a predefined 5x5
   * kernel. This method is for beginner users who just want to simply sharpen an image.
   */
  void sharpen();

  /**
   * Method that applies filter to an image by using a kernel, which is a 2D array of floats,
   * specified by the users. This method is for intermediate to advanced users who know how kernel
   * works and which kernel they want to use.
   *
   * @param kernel a 2D array of floats
   * @throws IllegalArgumentException if the kernel is null or empty or if its dimension is an odd
   *                                  number
   */
  void filter(float[][] kernel) throws IllegalArgumentException;

  /**
   * Method that converts an image into a greyscale image using a predefined formula. This method
   * is for beginner users who just want to convert a colored image to a simple greyscale image.
   */
  void greyscale();

  /**
   * Method that converts an image into a sepia-toned image using a predefined formula. This method
   * is for beginner users who just want to convert a colored image to a simple sepia-toned image.
   */
  void sepia();

  /**
   * Method that performs color transformation on an image by using a matrix, which is a 2D array of
   * doubles, specified by the users. This method is for intermediate to advanced users who know
   * how matrix works and which matrix they want to use.
   *
   * @param matrix 3x3 matrix for specific color transformations
   * @throws IllegalArgumentException if the dimension of the matrix is not 3x3
   */
  void colorTrans(double[][] matrix) throws IllegalArgumentException;

  /**
   * Method that generates a national flag image where the nation, image's height and width are
   * specified by the users. Currently supports only France, Switzerland and Greece. Planned for
   * future expansion of national flags. If the users put in some height and width such that the
   * height and width do not satisfy a legal proportion of the flags (but still greater than
   * minimum allowed width and height), the model will still create a flag with a new height and
   * width inside the dimension given by the user. The extra space will be white and there will be
   * a black border surrounding the flags.
   *
   * @param height the height (an int), in pixels, of the image specified by the user
   * @param width the width (an int), in pixels, of the image specified by the user
   * @param country an enum that represents the countries whose flags the users want to generate
   *                Currently supports: FRANCE, SWITZERLAND, and GREECE.
   * @throws IllegalArgumentException if the dimensions does not allow for a legal proportion of
   *          the flags
   */
  void generateFlags(int height, int width, Country country) throws IllegalArgumentException;

  /**
   * Method that returns the processed image that is stored in a new 3D array.
   *
   * @return a 3D array that represents an image.
   */
  int[][][] getData();

}
