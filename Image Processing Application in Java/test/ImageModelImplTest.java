import org.junit.Test;

import java.io.IOException;

import controller.ImageUtil;
import enums.Direction;
import model.ImageModel;
import model.ImageModelImpl;

import static org.junit.Assert.assertArrayEquals;

/**
 * This is a class to test the ImageModelImpl to test correctly generated data and errors.
 */
public class ImageModelImplTest {

  @Test
  public void testGenerateRainbow() {
    ImageModel testVertRainbow = new ImageModelImpl();
    testVertRainbow.generateRainbow(5, 8, Direction.VERTICAL);
    int[][][] expected = {
            {{255, 0, 0}, {255, 200, 0}, {255, 255, 0}, {0, 255, 0}, {0, 255, 255}, {0, 0, 255},
                    {255, 0, 255}, {255, 0, 255}},
            {{255, 0, 0}, {255, 200, 0}, {255, 255, 0}, {0, 255, 0}, {0, 255, 255}, {0, 0, 255},
                    {255, 0, 255}, {255, 0, 255}},
            {{255, 0, 0}, {255, 200, 0}, {255, 255, 0}, {0, 255, 0}, {0, 255, 255}, {0, 0, 255},
                    {255, 0, 255}, {255, 0, 255}},
            {{255, 0, 0}, {255, 200, 0}, {255, 255, 0}, {0, 255, 0}, {0, 255, 255}, {0, 0, 255},
                    {255, 0, 255}, {255, 0, 255}},
            {{255, 0, 0}, {255, 200, 0}, {255, 255, 0}, {0, 255, 0}, {0, 255, 255}, {0, 0, 255},
                    {255, 0, 255}, {255, 0, 255}},
    };
    assertArrayEquals(expected, testVertRainbow.getData());
  }

  @Test
  public void testGenerateHorizRainbow() {
    ImageModel testHorizRainbow = new ImageModelImpl();
    testHorizRainbow.generateRainbow(13, 1, Direction.HORIZONTAL);
    int[][][] expected = {
            {{255, 0, 0}},
            {{255, 200, 0}},
            {{255, 255, 0}},
            {{0, 255, 0}},
            {{0, 255, 255}},
            {{0, 0, 255}},
            {{255, 0, 255}},
            {{255, 0, 255}},
            {{255, 0, 255}},
            {{255, 0, 255}},
            {{255, 0, 255}},
            {{255, 0, 255}},
            {{255, 0, 255}}
    };
    assertArrayEquals(expected, testHorizRainbow.getData());
  }

  @Test
  public void testGenerateHorizRainbow2() {
    ImageModel testHorizRainbow2 = new ImageModelImpl();
    testHorizRainbow2.generateRainbow(14, 1, Direction.HORIZONTAL);
    int[][][] expected = {
            {{255, 0, 0}},
            {{255, 0, 0}},
            {{255, 200, 0}},
            {{255, 200, 0}},
            {{255, 255, 0}},
            {{255, 255, 0}},
            {{0, 255, 0}},
            {{0, 255, 0}},
            {{0, 255, 255}},
            {{0, 255, 255}},
            {{0, 0, 255}},
            {{0, 0, 255}},
            {{255, 0, 255}},
            {{255, 0, 255}},
    };
    assertArrayEquals(expected, testHorizRainbow2.getData());
  }

  // Tests for less than 1 input.
  @Test(expected = IllegalArgumentException.class)
  public void testGenerateZeroRainbow() {
    ImageModel testHorizRainbow = new ImageModelImpl();
    testHorizRainbow.generateRainbow(7, 0, Direction.HORIZONTAL);
  }

  // Tests for less than 1 for input.
  @Test(expected = IllegalArgumentException.class)
  public void testGenerateZeroRainbow2() {
    ImageModel testHorizRainbow = new ImageModelImpl();
    testHorizRainbow.generateRainbow(-1, 10, Direction.HORIZONTAL);
  }

  // Tests for less than 7 for the important side input.
  @Test(expected = IllegalArgumentException.class)
  public void testGenerateLessThan7Rainbow() {
    ImageModel testHorizRainbow = new ImageModelImpl();
    testHorizRainbow.generateRainbow(6, 10, Direction.HORIZONTAL);
  }

  // Tests for less than 7 for the important side input.
  @Test(expected = IllegalArgumentException.class)
  public void testGenerateLessThan7Rainbow2() {
    ImageModel testHorizRainbow2 = new ImageModelImpl();
    testHorizRainbow2.generateRainbow(10, 6, Direction.VERTICAL);
  }

  // Tests CheckerBoard for 0.
  @Test(expected = IllegalArgumentException.class)
  public void testCheckerBoardZero() {
    ImageModel testChecker = new ImageModelImpl();
    testChecker.generateCheckerBoard(0);
  }

  @Test
  public void testGenerateCheckerBoard() {
    ImageModel testChecker = new ImageModelImpl();
    testChecker.generateCheckerBoard(1);
    int[][][] expected = {
            {{0, 0, 0}, {255, 255, 255}, {0, 0, 0}, {255, 255, 255}, {0, 0, 0}, {255, 255, 255},
                    {0, 0, 0}, {255, 255, 255}},
            {{255, 255, 255}, {0, 0, 0}, {255, 255, 255}, {0, 0, 0}, {255, 255, 255}, {0, 0, 0},
                    {255, 255, 255}, {0, 0, 0}},
            {{0, 0, 0}, {255, 255, 255}, {0, 0, 0}, {255, 255, 255}, {0, 0, 0}, {255, 255, 255},
                    {0, 0, 0}, {255, 255, 255}},
            {{255, 255, 255}, {0, 0, 0}, {255, 255, 255}, {0, 0, 0}, {255, 255, 255}, {0, 0, 0},
                    {255, 255, 255}, {0, 0, 0}},
            {{0, 0, 0}, {255, 255, 255}, {0, 0, 0}, {255, 255, 255}, {0, 0, 0}, {255, 255, 255},
                    {0, 0, 0}, {255, 255, 255}},
            {{255, 255, 255}, {0, 0, 0}, {255, 255, 255}, {0, 0, 0}, {255, 255, 255}, {0, 0, 0},
                    {255, 255, 255}, {0, 0, 0}},
            {{0, 0, 0}, {255, 255, 255}, {0, 0, 0}, {255, 255, 255}, {0, 0, 0}, {255, 255, 255},
                    {0, 0, 0}, {255, 255, 255}},
            {{255, 255, 255}, {0, 0, 0}, {255, 255, 255}, {0, 0, 0}, {255, 255, 255}, {0, 0, 0},
                    {255, 255, 255}, {0, 0, 0}}
    };
    assertArrayEquals(expected, testChecker.getData());
  }

  // Test filter for Illegal Argument Exception
  @Test(expected = IllegalArgumentException.class)
  public void testFilterZeroKernel() throws IOException {
    int[][][] dog = ImageUtil.readImage("res/dog.jpg");
    float[][] testKernel = null;
    ImageModel testZero = new ImageModelImpl(dog);
    testZero.filter(testKernel);
  }

  // Test filter for Illegal Argument Exception
  @Test(expected = IllegalArgumentException.class)
  public void testFilterKernel() throws IOException {
    int[][][] dog = ImageUtil.readImage("res/dog.jpg");
    float[][] testKernel = {{}};
    ImageModel testZero = new ImageModelImpl(dog);
    testZero.filter(testKernel);

  }

  // Test filter for Illegal Argument Exception with a even kernel
  @Test(expected = IllegalArgumentException.class)
  public void testFilterKernel2() throws IOException {
    int[][][] dog = ImageUtil.readImage("res/dog.jpg");
    float[][] testKernel = {{}, {}};
    ImageModel testZero = new ImageModelImpl(dog);
    testZero.filter(testKernel);

  }
}