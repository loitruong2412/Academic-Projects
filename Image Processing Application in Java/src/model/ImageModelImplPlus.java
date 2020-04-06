package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is the implementation of the ImageModelPlus Interface also extends the ImageModelImpl
 * class. It has all the operations that the interface and class offers. It can store an image that
 * is loaded to it and the image's derived height and width.
 */
public class ImageModelImplPlus extends ImageModelImpl implements ImageModelPlus {

  /**
   * Constructs an image processor that can load in images for image processing. This constructor
   * will take in a 3D array of integers that represents an image and is used for processing
   * image. The constructor supers the data to the parent class.
   *
   * @param imageData a 3D array of integers that represents an image. The first two sub-arrays are
   *                  for accessing the pixel location and the third subarray stores the RGB values
   *                  of that pixel.
   */
  public ImageModelImplPlus(int[][][] imageData) {
    super(imageData);
  }

  /**
   * Constructs an image processor that can load in images for image processing. This constructor
   * does not take in any image input and is used for generating images from scratch.
   */
  public ImageModelImplPlus() {
    //Empty Constructor for a model.ImageModelImplPlus Object to generate images.
  }

  @Override
  public void mosaic(int seeds) throws IllegalArgumentException {
    if (seeds < 1) {
      throw new IllegalArgumentException("You can only have a positive number of seeds.");
    }

    // Create model.Point2D Objects for every pixel in the picture.
    List<Point2D> pixelSet = createPoint2DList();
    if (seeds == pixelSet.size()) {
      return;
    }

    //Choose the centroids.
    List<Point2D> centroids = pickCentroids(pixelSet, seeds);

    // Map, keys that are the indexes that correspond to the centroid list and value is a list of
    // model.Point2D that belongs in that cluster.
    Map<Integer, List<Point2D>> centroidMatch = centroidIndexMatch(pixelSet, centroids);

    // Map, keys that are the indexes and a Integer array with the RGB value of the avg color.
    Map<Integer, Integer[]> colorsCluster = new HashMap<>();
    centroidMatch.forEach((index, list) -> colorsCluster.put(index, getNewColor(list)));

    // Creates the mosaic by setting the values of the pixels to the corresponding avg color.
    for (Map.Entry<Integer, List<Point2D>> entry: centroidMatch.entrySet()) {
      List<Point2D> cluster = entry.getValue();
      for (Point2D pixel : cluster) {
        int h = (int) pixel.getX();
        int w = (int) pixel.getY();

        Integer[] newColor = colorsCluster.get(entry.getKey());
        this.imageData[h][w][0] = newColor[0];
        this.imageData[h][w][1] = newColor[1];
        this.imageData[h][w][2] = newColor[2];
      }
    }
  }

  /**
   * Private method to transform the coordinate of the pixels into 2D objects. The x-coordinate
   * of the Point 2D object is the row number (height) while the y-coordinate is the column
   * number (width). The top left pixel is (0, 0).
   *
   * @return List of Point 2D objects that represent the location of the pixels
   */
  private List<Point2D> createPoint2DList() {
    List<Point2D> pixelSet = new ArrayList<>();
    for (int h = 0; h < height; h++) {
      for (int w = 0; w < width; w++) {
        pixelSet.add(new Point2D((double) h, (double) w));
      }
    }
    return pixelSet;
  }

  /**
   * Private method to pick a List of Point 2D objects that are the seeds or the centroids of the
   * image. The chosen objects would be used to create groups of pixels with the same color.
   *
   * @param pixelSet List of Point 2D objects that represented the pixels of the image
   * @param seeds an int, number of pixels to be randomly picked from the image
   * @return List of Point 2D objects that are the seeds of the image
   * @throws IllegalArgumentException if the number of seeds is higher than the number of pixels
   *                                  in the image
   */
  private List<Point2D> pickCentroids(List<Point2D> pixelSet, int seeds)
          throws IllegalArgumentException {
    if (pixelSet.size() < seeds) {
      throw new IllegalArgumentException("There are not enough pixels to create mosaic.");
    }
    List<Point2D> centroids = new ArrayList<>();
    int count = 0;
    while (count < seeds) {
      Point2D centroid = pixelSet.get((int) (Math.random() * pixelSet.size()));
      if (!centroids.contains(centroid)) {
        centroids.add(centroid);
        count++;
      }
    }
    return centroids;
  }

  /**
   * Private method to calculate Euclidean distance between 2 Point2D objects.
   *
   * @param start Point 2D object that is the location of the first
   * @param end Point 2D object that is the location end of the distance
   * @return double that represents the distance between 2 points
   */
  private double computeEuclideanDistance(Point2D start, Point2D end) {
    double dist = Math.sqrt(Math.pow(start.getX() - end.getX(), 2)
            + Math.pow(start.getY() - end.getY(), 2));
    return dist;
  }

  /**
   * Private helper method return an Integer array that holds the RGB value of the average color
   * from a List of Point2Ds objects that are supposed to be from the same cluster. The average
   * color adds all the RGB values of the list and divides it by the number of objects in the list.
   *
   * @param cluster List of Point 2D objects that belong in the same cluster
   * @return Integer array that holds the RGB value of the average color {red, green, blue}
   */
  private Integer[] getNewColor(List<Point2D> cluster) {
    int red = 0;
    int green = 0;
    int blue = 0;
    int count = cluster.size();

    for (Point2D pixel : cluster) {
      int h = (int) pixel.getX();
      int w = (int) pixel.getY();

      red += this.imageData[h][w][0];
      green += this.imageData[h][w][1];
      blue += this.imageData[h][w][2];
    }
    red = red / count;
    green = green / count;
    blue = blue / count;

    Integer[] newColor = {red, green, blue};
    return newColor;
  }

  /**
   * Private helper method to return a Map with the key being the cluster the (value) list of point
   * 2D objects belonged to.
   *
   * @param pixelSet List of Point 2D objects that represented the pixels of the image
   * @param centroids List of Point 2D objects that are the random seeds or centroids picked from
   *                 the image
   * @return Map, key an Integer representing the index of the centroid in a corresponding list
   *          and value being a list of Point 2D objects that belong in the cluster that is
   *          closest to that centroid
   */
  private Map<Integer, List<Point2D>> centroidIndexMatch(List<Point2D> pixelSet,
                                                         List<Point2D> centroids) {
    int sizeOfPixSet = pixelSet.size();
    Map<Integer, List<Point2D>> centroidMatch = new HashMap<>();

    for (int i = 0; i < sizeOfPixSet; i++) {
      double minDist = Double.POSITIVE_INFINITY;
      Integer index = 0;
      Point2D pixel = pixelSet.get(i);
      for (int j = 0; j < centroids.size(); j++) {
        Point2D centroid = centroids.get(j);
        if (pixel.equals(centroid)) {
          index = j;
          break;
        }
        double dist = computeEuclideanDistance(pixelSet.get(i), centroids.get(j));
        if (minDist > dist) {
          minDist = dist;
          index = j;
        }
      }
      if (!centroidMatch.containsKey(index)) {
        List<Point2D> pixelsInCluster = new ArrayList<>();
        pixelsInCluster.add(pixel);
        centroidMatch.put(index, pixelsInCluster);
      }
      else {
        centroidMatch.get(index).add(pixel);
      }
    }
    return centroidMatch;
  }

  @Override
  public void dither() {
    this.greyscale();
    int oldColor;
    int newColor;
    int error;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        oldColor = this.imageData[i][j][0];
        newColor = findNewColor(oldColor);
        error = oldColor - newColor;
        Color c = new Color(newColor, newColor, newColor);
        this.updateRGB(i, j, c);
        addColor(i, j, error);
      }
    }
  }

  /**
   * Private helper method to return 0 or 255 (whichever is closer) from the red value of the image.
   *
   * @param oldColor image's red value of the color of the pixel
   * @return int, 0 if the value was less than 255 minus the value, else 255
   */
  private int findNewColor(int oldColor) {
    if (oldColor < (255 - oldColor)) {
      return 0;
    }
    return 255;
  }

  /**
   * Private helper to check that the logic that made sure that the coordinates were in the
   * bounds of the image.
   *
   * @param i an int, that is the row number
   * @param j an int, that is the column number
   * @return boolean, True if it is within the bounds of the row and column and the column is
   *          more than 0, False otherwise
   */
  private boolean isInBound(int i, int j) {
    return ((i <= height - 1) && (j <= width - 1) && (j >= 0));
  }

  /**
   * Private helper method that updates the pixels around the currently scanned pixels with the
   * adding the algorithm values multiplied by the error.
   *
   * @param i an int, that is the row number
   * @param j an int, that is the column number
   * @param error an int, the difference between the new value and the current red value
   */
  private void addColor(int i, int j, int error) {
    int error1 = ((error * 7) / 16);
    int error2 = ((error * 3) / 16);
    int error3 = ((error * 5) / 16);
    int error4 = (error / 16);

    if (isInBound(i, j + 1)) {
      Color added = new Color(clampCheck(this.imageData[i][j + 1][0] + error1),
              clampCheck(this.imageData[i][j + 1][1] + error1),
              clampCheck(this.imageData[i][j + 1][2] + error1));
      updateRGB(i, j + 1, added);
    }
    if (isInBound(i + 1, j - 1)) {
      Color added = new Color(clampCheck(this.imageData[i + 1][j - 1][0] + error2),
              clampCheck(this.imageData[i + 1][j - 1][1] + error2),
              clampCheck(this.imageData[i + 1][j - 1][2] + error2));
      updateRGB(i + 1, j - 1, added);
    }
    if (isInBound(i + 1, j)) {
      Color added = new Color(clampCheck(this.imageData[i + 1][j][0] + error3),
              clampCheck(this.imageData[i + 1][j][1] + error3),
              clampCheck(this.imageData[i + 1][j][2] + error3));
      updateRGB(i + 1, j, added);
    }
    if (isInBound(i + 1, j + 1)) {
      Color added = new Color(clampCheck(this.imageData[i + 1][j + 1][0] + error4),
              clampCheck(this.imageData[i + 1][j + 1][1] + error4),
              clampCheck(this.imageData[i + 1][j + 1][2] + error4));
      updateRGB(i + 1, j + 1, added);
    }
  }

}
