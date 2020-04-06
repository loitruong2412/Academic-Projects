import model.ImageModelFactory;
import model.ImageModelPlus;

public class ImageModelFactoryMock extends ImageModelFactory {
  private ImageModelPlus mock;

  public ImageModelFactoryMock(ImageModelPlus mock) {
    this.mock = mock;
  }

  @Override
  public ImageModelPlus loadImage(int[][][] imageData) {
    return this.mock;
  }

  @Override
  public ImageModelPlus createImage() {
    return this.mock;
  }

}
