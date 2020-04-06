package model;

/**
 * This is an interface that represents an image processing model. This model is currently able to
 * store one image. This interface adds features available to the original ImageModel interface.
 * The additional features are dither and mosaic. It also inherits offers some image processing
 * features such as filter or color transformation. In addition, the interface offers methods for
 * creating certain images. The model handles all the data processing to handle the processes
 * before mentioned features.
 */
public interface ImageModelPlus extends ImageModel {

  /**
   * Method that coverts an image into black and white dots, also known as dithering. This method
   * is for beginner users who want to simply convert a image to a dithered image.
   *
   */
  void dither();

  /**
   * Method that converts an image into a mosaic-like photo with a "stained glass window" effect.
   * Seeds are the number of pixels randomly picked from the image to be the center of a cluster
   * of pixels. Those belonging to the same cluster will have the same color which is the average
   * of the colors in the cluster. The closer the seed number is to the number of pixels the more
   * like the original image it will be. The greater the difference the more abstract the
   * mosaic-ed image will be. If the seed number is equal to the pixels, it returns the same image.
   *
   * @param seeds an int, number of pixels to be randomly picked from the image
   * @throws IllegalArgumentException is seeds is less than 1 or more than the number of pixels
   *          in image
   */
  void mosaic(int seeds) throws IllegalArgumentException;
}
