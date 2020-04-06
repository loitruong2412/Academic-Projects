import java.io.IOException;

import controller.ImageUtil;
import model.ImageModelImplPlus;
import model.ImageModelPlus;

/**
 * This class is a main driver to be the placeholder of the controller of the program. The driver is
 * responsible for loading image files to feed data to the model, calling methods that the model
 * offers to process image data or to create image from scratch, and writing out newly processed or
 * created image using controller.ImageUtil class. In this driver we will attempt to apply blur and
 * sharpen filter and sepia-toned and greyscale transformation to two existing images. In addition,
 * we will generate a checkerboard image, images of vertical and horizontal rainbow, and images of
 * the national flag of France, Greece, and Switzerland. It also tested mosaic and dither. It was
 * used to test the model.
 */
public class ImageDriverTester {

  /**
   * Class is a fake main to test model.
   *
   * @param args Input for main, nothing for this case
   * @throws IOException if the file cannot be read or cannot be written
   */
  public static void main(String[] args) throws IOException {
    int[][][] dog1 = controller.ImageUtil.readImage("res/dog.jpg");
    model.ImageModelPlus doge = new model.ImageModelImplPlus(dog1);
    doge.mosaic(4000);
    int[][][] mosaic4000 = doge.getData();
    controller.ImageUtil.writeImage(mosaic4000, mosaic4000[1].length, mosaic4000.length,
            "mosaicDog4000.png");

    model.ImageModelPlus doge2 = new model.ImageModelImplPlus(dog1);
    doge2.mosaic(2000);
    int[][][] mosaic2000 = doge2.getData();
    controller.ImageUtil.writeImage(mosaic2000, mosaic2000[1].length, mosaic2000.length,
            "mosaicDog2000.png");

    model.ImageModelPlus doge3 = new model.ImageModelImplPlus(dog1);
    doge3.mosaic(50000);
    int[][][] mosaic10k = doge3.getData();
    controller.ImageUtil.writeImage(mosaic10k, mosaic10k[1].length, mosaic10k.length,
            "mosaicDog50k.png");

    model.ImageModelPlus doge4 = new model.ImageModelImplPlus(dog1);
    doge4.mosaic(25000);
    int[][][] mosaic25k = doge4.getData();
    controller.ImageUtil.writeImage(mosaic25k, mosaic25k[1].length, mosaic25k.length,
            "mosaicDog25k.png");

    model.ImageModelPlus doge5 = new model.ImageModelImplPlus(dog1);
    doge5.mosaic(5);
    int[][][] mosaic5 = doge5.getData();
    controller.ImageUtil.writeImage(mosaic5, mosaic5[1].length, mosaic5.length,
            "mosaicDog5.png");

    model.ImageModelPlus dogeOne = new model.ImageModelImplPlus(dog1);
    dogeOne.mosaic(1);
    int[][][] mosaicOne = dogeOne.getData();
    controller.ImageUtil.writeImage(mosaicOne, mosaicOne[1].length, mosaicOne.length,
            "mosaicDogOne.png");


    // Generatingrating a horizontal rainbow with 300 x 350 dimension
    model.ImageModelPlus model = new model.ImageModelImplPlus();
    model.generateRainbow(300, 350, enums.Direction.HORIZONTAL);
    int[][][] horizRain = model.getData();
    controller.ImageUtil.writeImage(horizRain, horizRain[1].length, horizRain.length,
            "horizRain.png");

    //Generating a checkerboard with square size 100 pixels
    model.generateCheckerBoard(100);
    int[][][] checkerBoard = model.getData();
    controller.ImageUtil.writeImage(checkerBoard, checkerBoard[1].length, checkerBoard.length,
            "checkerboard.png");

    //Blurs an image of a fox using blur method
    int[][][] fox1 = controller.ImageUtil.readImage("fox.jpg");
    model.ImageModelPlus foxBlurProcessor = new model.ImageModelImplPlus(fox1);
    foxBlurProcessor.blur();
    int[][][] blurredFox = foxBlurProcessor.getData();
    controller.ImageUtil.writeImage(blurredFox, blurredFox[1].length, blurredFox.length,
            "fox-with-blur-filter.png");

    // Picture of a dog sharpened 5x and sepia.
    int[][][] dog6 = controller.ImageUtil.readImage("dog.jpg");
    model.ImageModelPlus dogMultipleProcesses = new model.ImageModelImplPlus(dog6);
    dogMultipleProcesses.sharpen();
    dogMultipleProcesses.sharpen();
    dogMultipleProcesses.sharpen();
    dogMultipleProcesses.sharpen();
    dogMultipleProcesses.sharpen();
    dogMultipleProcesses.greyscale();
    int[][][] dogMultData = dogMultipleProcesses.getData();
    controller.ImageUtil.writeImage(dogMultData, dogMultData[1].length, dogMultData.length,
            "dog-with-multiple-processes2.png");

    int[][][] city = controller.ImageUtil.readImage("city.png");
    model.ImageModelPlus cityTest = new model.ImageModelImplPlus(city);
    cityTest.mosaic(1000);
    int[][][] city1k = cityTest.getData();
    controller.ImageUtil.writeImage(city1k, city1k[1].length, city1k.length,
            "city1k.png");

    int[][][] city2 = controller.ImageUtil.readImage("city.png");
    model.ImageModelPlus cityTest2 = new model.ImageModelImplPlus(city2);
    cityTest2.mosaic(2000);
    int[][][] city2k = cityTest2.getData();
    controller.ImageUtil.writeImage(city2k, city2k[1].length, city2k.length,
            "city2k.png");

    int[][][] city3 = ImageUtil.readImage("city.png");
    ImageModelPlus cityTest3 = new ImageModelImplPlus(city3);
    cityTest3.mosaic(4000);
    int[][][] city4k = cityTest3.getData();
    ImageUtil.writeImage(city4k, city4k[1].length, city4k.length,
            "city4k.png");

    ImageUtil.writeImage(city3, city3[1].length, city3.length,
            "city4kv2.png");


    int[][][] city4 = controller.ImageUtil.readImage("city.png");
    model.ImageModelPlus cityTest4 = new model.ImageModelImplPlus(city4);
    cityTest4.mosaic(8000);
    int[][][] city8k = cityTest4.getData();
    controller.ImageUtil.writeImage(city8k, city8k[1].length, city8k.length,
            "city8k.png");

    int[][][] city5 = controller.ImageUtil.readImage("city.png");
    model.ImageModelPlus cityTest5 = new model.ImageModelImplPlus(city5);
    cityTest5.mosaic(15000);
    int[][][] city15k = cityTest5.getData();
    controller.ImageUtil.writeImage(city15k, city15k[1].length, city15k.length,
            "city15k.png");

    int[][][] vert2 = controller.ImageUtil.readImage("res/vertRain.png");
    model.ImageModelPlus verty = new model.ImageModelImplPlus(vert2);
    verty.mosaic(100);
    int[][][] mosaicTest = verty.getData();
    controller.ImageUtil.writeImage(mosaicTest, mosaicTest[1].length, mosaicTest.length,
            "mosaicTest.png");

    model.ImageModelPlus vertRain2 = new model.ImageModelImplPlus(vert2);
    vertRain2.mosaic(1444);
    int[][][] mosaicNorm = vertRain2.getData();
    controller.ImageUtil.writeImage(mosaicNorm, mosaicNorm[1].length, mosaicNorm.length,
            "mosaicNorm.png");
  }

}
