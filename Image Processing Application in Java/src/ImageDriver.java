import controller.ControllerImpl;
import controller.GUIController;
import model.ImageModelFactory;
import view.IView;
import view.JFrameView;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.swing.JOptionPane;

/**
 * This class represents the ImageDriver that allows us to run the program. The supported method
 * that it contains is run to execute the program.
 */
public class ImageDriver {

  /**
   * Main method to make sure our controller can run as a stand alone component.
   *
   */
  public static void main(String[] args) {
//    for (String arg: args) {
//      Reader in = new FileReader(arg);
//      StringBuffer out = new StringBuffer();
//      new ControllerImpl(in, out).run(new ImageModelImplPlus());
//    }
//    Reader in = new FileReader("input1.txt");
//    StringBuffer out = new StringBuffer();
//    new ControllerImpl(in, out, new ImageModelFactory()).run();
    ImageModelFactory modelFactory = new ImageModelFactory();
    IView v = new JFrameView("Image Processing Application");
    new GUIController(modelFactory, v);
//    switch (args[0]) {
//      case "-interactive":
//        if (args.length > 1) {
//          String error = "Invalid command-line arguments. Should only be one word 'interactive";
//          JOptionPane.showMessageDialog(null, error, "Error",
//                  JOptionPane.ERROR_MESSAGE);
//        } else {
//          ImageModelFactory modelFactory = new ImageModelFactory();
//          IView v = new JFrameView("Image Processing Application");
//          new GUIController(modelFactory, v);
//        }
//        break;
//      case "-script":
//        if (args.length > 2) {
//          String error = "Invalid command-line arguments. Only 'script' " +
//                  "and script path are needed\n";
//          JOptionPane.showMessageDialog(null, error, "Error",
//                  JOptionPane.ERROR_MESSAGE);
//        } else {
//          try {
//            File file = new File(args[1]);
//            Reader in = new FileReader(file);
//            StringBuffer out = new StringBuffer();
//            new ControllerImpl(in, out, new ImageModelFactory()).run();
//            JOptionPane.showMessageDialog(null, out, "Script notification",
//                    JOptionPane.INFORMATION_MESSAGE);
//          } catch (IOException e) {
//            String error = "Script file does not exist or can not be read\n";
//            JOptionPane.showMessageDialog(null, error, "Error",
//                    JOptionPane.ERROR_MESSAGE);
//          }
//        }
//        break;
//      default:
//        String error = "Invalid command-line arguments";
//        JOptionPane.showMessageDialog(null, error, "Error",
//                JOptionPane.ERROR_MESSAGE);
//        break;
//    }
  }

}
