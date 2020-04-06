package controller;

import model.ImageModelPlus;

/**
 * This interface represents the commands that can be performed on model objects. All commands
 * should support the go method that does something to the model.
 */
public interface ImageCommand {

  /**
   * Method to execute the command on the model.
   *
   * @param model ImageModelPlus that the method is executing on
   */
  void run(ImageModelPlus model);
}
