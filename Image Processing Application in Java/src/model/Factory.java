package model;

/**
 * This interface represents a factory for models. This allows us to create ImageModePlus objects
 * without specifying the exact class of the object and alternatively to calling the constructor.
 * The factory creates a model for the following scenarios when you load a picture and when I
 * create an image.
 */
public interface Factory {

  /**
   * LoadImage method creates a model with an image's data. Returns a model for a loaded image.
   *
   * @param imageData data that represents an image, pixel by pixel
   * @return ImageModelPlus object which is the model that can be used in the controller
   */
  ImageModelPlus loadImage(int[][][] imageData);

  /**
   * CreateImage method creates a model that can be used to create or generate an image. It does
   * not take in anything.
   *
   * @return ImageModelPlus object which is the model that can be used in the controller
   */
  ImageModelPlus createImage();
}
