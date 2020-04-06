package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

import commands.Blur;
import commands.Checkerboard;
import commands.Dither;
import commands.Filter;
import commands.Flag;
import commands.Greyscale;
import commands.Mosaic;
import commands.Rainbow;
import commands.Sepia;
import commands.Sharpen;
import commands.Transform;
import model.ImageModelFactory;
import model.ImageModelPlus;

/**
 * This class is the implementation of the controller. The controller can parse the inputs and
 * ensures that the correct commands result in the correct model calls and actions. It has two
 * attributes and it takes in and has an output.
 */
public class ControllerImpl implements Controller {
  final Readable in;
  final Appendable out;
  private ImageModelFactory modelFactory;
  private ImageModelPlus model;

  /**
   * ControllerImpl constructor that takes in the readable object attribute and the appendable
   * attribute.
   */
  public ControllerImpl(Readable in, Appendable out, ImageModelFactory modelFactory) {
    this.in = in;
    this.out = out;
    this.modelFactory = modelFactory;
  }

  @Override
  public void run() throws IOException {

    int[][][] input = null;
    Scanner scan = new Scanner(this.in);

    Map<String, Function<Scanner, ImageCommand>> knownCommands = knownCommandsMap(scan);

    try {
      String token = scan.next();
      if (!(token.equals("load") || token.equals("create"))) {
        throw new IllegalArgumentException("Start with a load or create.");
      }
      if (token.equals("load")) {
        input = fileInput(scan);
        model = modelFactory.loadImage(input);
      } else {
        model = modelFactory.createImage();
        String create = scan.next();
        ImageCommand c;
        Function<Scanner, ImageCommand> cmd = knownCommands.getOrDefault(create, null);
        if (cmd == null) {
          throw new IllegalArgumentException(create + ": cannot be created.");
        } else {
          c = cmd.apply(scan);
          c.run(model);
        }
      }
    } catch (NoSuchElementException error) {
      this.out.append("This script is empty or your create does not have enough arguments.");
      return;
    } catch (IllegalArgumentException c) {
      this.out.append(c.getMessage());
      return;
    }

    while (true) {
      try {
        ImageCommand command;
        String nextItem = scan.next();
        if (nextItem.equals("q") || nextItem.equals("quit")) {
          this.out.append("Quitting");
          scan.close();
          return;
        }
        switch (nextItem) {
          case "load":
            input = fileInput(scan);
            model = modelFactory.loadImage(input);
            break;
          case "restore":
            if (input == null) {
              throw new IllegalStateException("There is no original image");
            } else {
              model = modelFactory.loadImage(input);
              break;
            }
          case "save":
            String output = scan.next();
            int[][][] image = model.getData();
            ImageUtil.writeImage(image, image[1].length, image.length, output);
            break;
          default:
            if (nextItem.equals("create")) {
              model = modelFactory.createImage();
              nextItem = scan.next();
            }
            Function<Scanner, ImageCommand> cmd =
                    knownCommands.getOrDefault(nextItem, null);
            if (cmd == null) {
              throw new IllegalArgumentException(nextItem + ": The command does not exist.");
            } else {
              command = cmd.apply(scan);
              command.run(model);
              break;
            }
        }
      } catch (IllegalArgumentException | IllegalStateException | ArrayIndexOutOfBoundsException e)
      {
        this.out.append(e.getMessage());
        scan.close();
        return;
      } catch (NoSuchElementException n) {
        this.out.append("Reached the end of script...Finished processing");
        scan.close();
        return;
      }
    }
  }

  /**
   * Private method to create the Map of of the text command->method in a map of function objects.
   *
   * @param scan Scanner that holds the command and its attributes
   */
  private Map<String, Function<Scanner, ImageCommand>> knownCommandsMap(Scanner scan) {
    Map<String, Function<Scanner, ImageCommand>> knownCommands = new HashMap<>();
    knownCommands.put("blur", s -> new Blur());
    knownCommands.put("sharpen", s -> new Sharpen());
    knownCommands.put("sepia", s -> new Sepia());
    knownCommands.put("greyscale", s -> new Greyscale());
    knownCommands.put("dither", s -> new Dither());
    knownCommands.put("mosaic", s -> new Mosaic(scan.next()));
    knownCommands.put("filter", s -> new Filter(scan.next()));
    knownCommands.put("transform", s -> new Transform(scan.next()));
    knownCommands.put("checkerboard", (Scanner s) -> new Checkerboard(s.next()));
    knownCommands.put("rainbow", (Scanner s) -> new Rainbow(s.next(), s.next(), s.next()));
    knownCommands.put("flag", (Scanner s) -> new Flag(s.next(), s.next(), s.next()));
    return knownCommands;
  }

  /**
   * Private helper method to handle the IO exceptions in loading.
   *
   * @param scan Scanner object that holds the scanned stream
   * @return int 3D Array which is the data representation of the
   * @throws IllegalArgumentException if there is an IO Exception
   */
  private int[][][] fileInput(Scanner scan) throws IllegalArgumentException {
    int[][][] input;
    try {
      String file = scan.next();
      input = ImageUtil.readImage(file);
    } catch (IOException e) {
      throw new IllegalArgumentException("File name not provided or image file does not exist");
    }
    return input;
  }
}