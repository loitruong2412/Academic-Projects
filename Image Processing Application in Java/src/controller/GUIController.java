package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.StringReader;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

import commands.Flag;
import commands.Rainbow;
import model.ImageModelFactory;
import model.ImageModelPlus;
import view.IView;

/**
 * This is the class is an implementation of a controller, specifically one with features. This
 * controller is the communicator between the model and the view. This class contains all the
 * features available in the view and communicates with the model to get the view what it needs.
 * The attributes of this class include an ImageModelFactory to get the model, the view, and 2
 * stacks to keep track of the data the program is currently using or in the past.
 */
public class GUIController implements Features {
  private ImageModelFactory modelFactory;
  private ImageModelPlus model;
  private IView view;
  private Stack<int[][][]> undoStack;
  private Stack<int[][][]> redoStack;

  /**
   * Constructor for the GUIController class. It takes in a ImageModelFactory and a view. The
   * constructor also creates new Stacks for the undoStack, redoStack attributes. Lastly, it
   * communicates to the view, to set up its Listeners and display the undo and redo menu items
   * properly.
   *
   * @param modelFactory ImageModelFactor object used for creating the needed model for the features
   * @param view IView object that is the user interface for the program
   */
  public GUIController(ImageModelFactory modelFactory, IView view) {
    this.modelFactory = modelFactory;
    this.view = view;
    this.view.setFeatures(this);
    undoStack = new Stack<>();
    redoStack = new Stack<>();
    checkRedoUndo();
  }

  @Override
  public void load() {
    String filePath = this.view.loadFile();
    if (filePath != null) {
      try {
        this.model = this.modelFactory.loadImage(ImageUtil.readImage(filePath));
        postCommand();
        this.view.setMenuItemDisplay();
      } catch (IOException e) {
        this.view.displayMessage(e.getMessage(), "error");
      }
    }
  }

  /**
   * A helper method used for creating a Buffer Image object from an image data. It gets the data
   * which represents the image in a 3D array from the model to produce the Buffer Image. This
   * image is given to the view for display in the GUI.
   *
   * @return a Buffer Image object that is given to the view to display
   */
  private BufferedImage createBuffImage() {
    int[][][] image = model.getData();
    int height = image.length;
    int width = image[1].length;
    BufferedImage buff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = image[i][j][0];
        int g = image[i][j][1];
        int b = image[i][j][2];
        int color = (r << 16) + (g << 8) + b;
        buff.setRGB(j, i, color);
      }
    }
    return buff;
  }


  @Override
  public void save() {
    String filePath  = view.saveImage();
    if (filePath != null) {
      try {
        int[][][] data = this.model.getData();
        ImageUtil.writeImage(data, data[1].length, data.length, filePath);
      } catch (IOException e) {
        this.view.displayMessage(e.getMessage(), "error");
      }
    }
  }

  @Override
  public void quit() {
    System.exit(0);
  }

  @Override
  public void createRainbow(String inputs) {
      try {
        this.model = modelFactory.createImage();
        Scanner s = new Scanner(inputs);
        Rainbow r = new Rainbow(s.next(), s.next(), s.next());
        if (s.hasNext()) {
          throw new IllegalStateException("Invalid inputs in height and width.");
        }
        r.run(this.model);
        postCommand();
        this.view.setMenuItemDisplay();
      } catch (NoSuchElementException n) {
        this.view.displayMessage("Input valid values for height and width.",
                "error");
      } catch (Exception e) {
        this.view.displayMessage(e.getMessage(), "error");
      }
    }

  @Override
  public void createFlag(String inputs) {
    try {
      this.model = modelFactory.createImage();
      Scanner s = new Scanner(inputs);
      Flag r = new Flag(s.next(), s.next(), s.next());
      if (s.hasNext()) {
        throw new IllegalStateException ("Invalid inputs for height and width.");
      }
      r.run(this.model);
      postCommand();
      this.view.setMenuItemDisplay();
    } catch (NoSuchElementException n) {
      this.view.displayMessage("Input valid values for height and width.",
              "error");
    } catch (Exception e) {
      this.view.displayMessage(e.getMessage(), "error");
    }

  }

  @Override
  public void createCheckerboard(String sqSize) {
    try {
      int number = Integer.parseInt(sqSize);
      this.model = modelFactory.createImage();
      this.model.generateCheckerBoard(number);
      postCommand();
      this.view.setMenuItemDisplay();
    }
    catch (NumberFormatException n) {
      this.view.displayMessage("Error: Invalid number.", "error");
    }
    catch (Exception e) {
      this.view.displayMessage(e.getMessage(), "error");
    }
  }

  @Override
  public void blurImage() {
    try {
      this.model.blur();
      postCommand();
    }
    catch (Exception e) {
      this.view.displayMessage(e.getMessage(), "error");
    }
  }

  @Override
  public void sharpenImage() {
    try {
      this.model.sharpen();
      postCommand();
    }
    catch (Exception e) {
      this.view.displayMessage(e.getMessage(), "error");
    }
  }

  @Override
  public void greyscaleImage() {
    try {
      this.model.greyscale();
      postCommand();
    }
    catch (Exception e) {
      this.view.displayMessage(e.getMessage(), "error");
    }
  }

  @Override
  public void sepiaImage() {
    try {
      this.model.sepia();
      postCommand();
    }
    catch (Exception e) {
      this.view.displayMessage(e.getMessage(), "error");
    }
  }

  @Override
  public void ditherImage() {
    try {
      this.model.dither();
      postCommand();
    }
    catch (Exception e) {
      this.view.displayMessage(e.getMessage(), "error");
    }
  }

  @Override
  public void mosaicImage(String seedNum) {
    try {
      int number = Integer.parseInt(seedNum);
      this.model.mosaic(number);
      postCommand();
    }
    catch (NumberFormatException n) {
      this.view.displayMessage("Not a valid number.", "error");
    }
    catch (Exception e) {
      this.view.displayMessage(e.getMessage(), "error");
    }
  }

  @Override
  public void processScript(String script) {
    try {
      StringBuffer out = new StringBuffer();
      new ControllerImpl(new StringReader(script), out, modelFactory).run();
      this.view.displayMessage(out.toString(), "info");
    } catch (Exception e) {
      this.view.displayMessage(e.getMessage(), "error");
    }
  }

  @Override
  public void redo() {
    this.model = modelFactory.loadImage(redoStack.pop());
    this.undoStack.push(model.getData());
    BufferedImage image = createBuffImage();
    this.view.displayImage(image);
    checkRedoUndo();
  }

  @Override
  public void undo() {
    this.redoStack.push(undoStack.pop());
    int[][][] prev = undoStack.peek();
    this.model = modelFactory.loadImage(prev);
    BufferedImage image = createBuffImage();
    this.view.displayImage(image);
    checkRedoUndo();
  }

  /**
   * Helper function to determine whether or not to tell the view to display redo or undo menu
   * options dependent on if it is an available feature at that point in time. The
   * availability is dependent on the stacks's size.
   */
  private void checkRedoUndo() {
    if (redoStack.isEmpty()) {
      this.view.disable("redo");
    } else {
      this.view.enable("redo");
    }
    if (undoStack.isEmpty() || undoStack.size() < 2) {
      this.view.disable("undo");
    } else {
      this.view.enable("undo");
    }
  }

  /**
   * Helper function that is called after most features. It creates an image that is given to
   * view to display, then saved in a stack, and then check the status of the stacks for the
   * redo/undo availability.
   */
  private void postCommand() {
    BufferedImage image = createBuffImage();
    this.view.displayImage(image);
    this.undoStack.push(model.getData());
    checkRedoUndo();
  }

}
