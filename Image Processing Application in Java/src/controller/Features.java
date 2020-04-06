package controller;

/**
 * This is an interface that represents a controller that has the features that is supported by
 * the view with a GUI. The controller handles all interactions with the model and tells the view
 * what to do in response. The methods below are available for the view to call to tell the
 * controller that the user has done something that should activate the feature. The controller
 * will then act respond accordingly.
 *
 */
public interface Features {

  /**
   * Load takes the file path passed from the view to load the object. Loading the object creates
   * a model that stores one image in a data form. Then, the controller passes the image back to
   * view to display and update the view to show now available options. Tells view to display
   * error message if fails.
   */
  void load();

  /**
   * Save method takes the file path from the view to save the image. Gets the data from the
   * model, and then saves the image. Tells view to display error message if fails.
   */
  void save();

  /**
   * Method that is called by the view when the user clicks on "Quit" on the menu. This enables the
   * program to close.
   */
  void quit();

  /**
   * CreateRainbow method takes the inputs that the user has given in the view to communicate it
   * to the model and then create a rainbow with those inputs. It then gives that image back to
   * the view to display and update the view to show now available options. If fails view displays
   * error message.
   *
   * @param inputs String input that represents the inputs that the user has given to the view.
   *               This input is the height, width, and whether they selected vertical or
   *               horizontal.
   */
  void createRainbow(String inputs);

  /**
   * CreateFlag method takes the inputs that the user has given in the view to communicate it
   * to the model and then create a flag with those inputs. It then gives that image back to
   * the view to display and update the view to show now available options. If fails, view displays
   * error message.
   *
   * @param inputs String input that represents the inputs that the user has given to the view.
   *               This input is the height, width, and whether they selected France, Greece, or
   *               Switzerland.
   */
  void createFlag(String inputs);

  /**
   * CreateCheckerboard method takes the inputs that the user has given in the view to
   * communicate it to the model and then create a 8 x 8 checkerboard with that input. It then
   * gives that image back to the view to display and update the view to show now available options.
   * If fails, view displays error message.
   *
   * @param sqSize String input that represents the inputs that the user has given to the view.
   *               This input is an int that represents the size of each square's side length in
   *               pixels.
   */
  void createCheckerboard(String sqSize);

  /**
   * BlurImage method is called by the view when the user wants to blur the image. This method
   * communicates this with the model then passes the image back to the view to display. If
   * fails, view displays error message.
   */
  void blurImage();

  /**
   * SharpenImage method is called by the view when the user wants to sharpen the image. This
   * method communicates this with the model then passes the image back to the view to display. If
   * fails, view displays error message.
   */
  void sharpenImage();

  /**
   * GreyScale method is called by the view when the user wants to color transform the image to a
   * grey-scaled version of the image. This method communicates with the model then passes the image
   * back to the view to display. If fails, view displays error message.
   */
  void greyscaleImage();

  /**
   * SepiaImage method is called by the view when the user wants to color transform the image to a
   * sepia version of the image. This method communicates with the model then passes the image
   * back to the view to display. If fails, view displays error message.
   */
  void sepiaImage();

  /**
   * DitherImage method is called by the view when the user wants to get a picture that looks
   * like it has been dithered. This method communicates with the model then passes the image
   * back to the view to display. If fails, view displays error message.
   */
  void ditherImage();

  /**
   * MosaicImage method is called by the view when the user wants to get a picture that looks
   * like a mosaic, a "stained glass window" effect. Seed number is an input that is gotten from
   * the view. The more seeds there are the less abstract the image is. If the seed number is less
   * than 1 or more than the pixels in the image it fails. If fails, view displays error message.
   *
   * @param seedNum String that is the input which represents the number of seeds taken from the
   *                image to create a mosaic
   */
  void mosaicImage(String seedNum);

  /**
   * ProcessScript method is called by the view and it gives it the script that was entered by
   * the user. The script can be used instead of the user interface. It is separate from the
   * displayed image and will not be displayed. To keep the image, you must save image. To work
   * on the displayed image with the script, it must be saved using the UI and then the script
   * will load it. A requirement of the script, similar to the UI is that you must load or create
   * an image first to work on it.
   *
   * @param script String that is the input which represents the script that the user wants to
   *               run on an image.
   */
  void processScript(String script);

  /**
   * Redo method is called by the view to redo a change to the image. The controller interacts
   * with the model to create the image that the view will display.
   */
  void redo();

  /**
   * Undo method is called by the view to reverse a change to the image. The controller interacts
   * with the model to create the image that the view will display.
   */
  void undo();
}
