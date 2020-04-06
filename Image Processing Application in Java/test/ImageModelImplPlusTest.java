import org.junit.Test;

import enums.Direction;
import model.ImageModelImplPlus;
import model.ImageModelPlus;

/**
 *  This is a class to test the ImageModelImplPlus specifically mosaic for expected exceptions.
 */

public class ImageModelImplPlusTest {

  @Test(expected = IllegalArgumentException.class)
  public void testNegSeeds() {
    ImageModelPlus horizRainbow = new ImageModelImplPlus();
    horizRainbow.generateRainbow(38, 38, Direction.HORIZONTAL);
    horizRainbow.mosaic(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroSeeds() {
    ImageModelPlus horizRainbow = new ImageModelImplPlus();
    horizRainbow.generateRainbow(38, 38, Direction.HORIZONTAL);
    horizRainbow.mosaic(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoreSeeds() {
    ImageModelPlus horizRainbow = new ImageModelImplPlus();
    horizRainbow.generateRainbow(38, 38, Direction.HORIZONTAL);
    horizRainbow.mosaic(1500);
  }
}