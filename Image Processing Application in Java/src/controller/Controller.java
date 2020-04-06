package controller;

import java.io.IOException;

/**
 * This is an interface that represents a controller. The controller responds to the view and
 * also interacts with the model. This interface has the method that interacts with the model.
 */
public interface Controller {

  /**
   * Go method that takes in a model to handle commands. It will call the correct methods with
   * the appropriate method. Most errors result in a message about the error. It will keep
   * running and automatically quit at the end of the input stream. There are many commands
   * supported: load, create, restore, save, blur, sharpen, sepia, greyscale, dither, mosaic,
   * filter, transform. Some of the commands need additional information.
   *
   */
  void run() throws IOException;
}
