package model;

/**
 * This class represents the factory that generates the models. The methods generate models for
 * each scenario and is more flexible with adapting to changes.
 */
public class ImageModelFactory implements Factory {

  @Override
  public ImageModelPlus loadImage(int[][][] imageData) {
    return new ImageModelImplPlus(imageData);
  }

  @Override
  public ImageModelPlus createImage() {
    return new ImageModelImplPlus();
  }
}
