package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;

/**
 * Class that represents the GUI of the program. This view is a simple but easily navigable user
 * interface. To prevent user decision paralysis, all features are laid out in the menu bar with a
 * max of 4 menu items at most. If it is a legal feature at that point in time, it will be enabled
 * and clickable. Features that are supported are loading a file, saving a file, quitting the
 * program, undo a feature, redo a feature, create certain images, filter image, change color of
 * image, and apply an effect on a image. The image appears in the grey area, in the center.
 */
public class JFrameView extends JFrame implements IView {
  private JMenuItem load;
  private JMenuItem save;
  private JMenuItem quit;
  private JMenuItem script;
  private JMenuItem redo;
  private JMenuItem undo;
  private JMenuItem rainbow;
  private JMenuItem flag;
  private JMenuItem checkerboard;
  private JMenuItem blur;
  private JMenuItem sharpen;
  private JMenuItem greyscale;
  private JMenuItem sepia;
  private JMenuItem dither;
  private JMenuItem mosaic;
  private JPanel picturePanel;
  private JLabel imageLabel;
  private JScrollPane imageScroll;
  private JTextField height;
  private JTextField width;
  private JTextField num;
  private ButtonGroup bGroup;
  private JTextArea scriptText;

  /**
   * Constructor for the JFrameView, sets up the JFrame window, MenuBar and the Panel where the
   * photo is displayed. Sets the window size, layout, and general look.
   *
   * @param caption String representing the program name
   */
  public JFrameView(String caption) {
    super(caption);

    setPreferredSize(new Dimension(800, 800));
    setLayout(new BorderLayout());

    setJMenuBar(createMenuBar());

    picturePanel = new JPanel(new GridLayout(0, 1));
    imageLabel = new JLabel("", SwingConstants.CENTER);
    imageScroll = new JScrollPane(imageLabel);
    this.add(picturePanel, BorderLayout.CENTER);

    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    pack();
    setVisible(true);
  }

  /**
   * Helper method to create and set up the menu bar and menu items. Also sets if the menu items can
   * be clicked.
   *
   * @return JMenuBar that has all the allowed features of the program
   */
  private JMenuBar createMenuBar() {
    JMenuBar menuBar = new JMenuBar();

    JMenu file = new JMenu("File");
    load = new JMenuItem("Load");
    save = new JMenuItem("Save");
    script = new JMenuItem("Script");
    quit = new JMenuItem("Quit");

    JMenu edit = new JMenu("Edit");
    undo = new JMenuItem("Undo");
    redo = new JMenuItem("Redo");

    JMenu create = new JMenu("Create");
    rainbow = new JMenuItem("Rainbow");
    flag = new JMenuItem("Flag");
    checkerboard = new JMenuItem("Checkerboard");

    JMenu filter = new JMenu("Filter");
    blur = new JMenuItem("Blur");
    sharpen = new JMenuItem("Sharpen");

    JMenu transform = new JMenu("Color");
    greyscale = new JMenuItem("Greyscale");
    sepia = new JMenuItem("Sepia");

    JMenu effects = new JMenu("Effects");
    dither = new JMenuItem("Dither");
    mosaic = new JMenuItem("Mosaic");

    file.add(load);
    file.add(save);
    file.add(script);
    file.add(quit);

    create.add(rainbow);
    create.add(flag);
    create.add(checkerboard);

    edit.add(undo);
    edit.add(redo);

    filter.add(blur);
    filter.add(sharpen);

    transform.add(greyscale);
    transform.add(sepia);

    effects.add(dither);
    effects.add(mosaic);

    menuBar.add(file);
    menuBar.add(edit);
    menuBar.add(create);
    menuBar.add(filter);
    menuBar.add(transform);
    menuBar.add(effects);

    save.setEnabled(false);
    blur.setEnabled(false);
    sharpen.setEnabled(false);
    greyscale.setEnabled(false);
    sepia.setEnabled(false);
    dither.setEnabled(false);
    mosaic.setEnabled(false);

    return menuBar;
  }

  @Override
  public void setFeatures(Features f) {
    load.addActionListener(l -> f.load());
    save.addActionListener(l -> f.save());
    blur.addActionListener(l -> f.blurImage());
    sharpen.addActionListener(l -> f.sharpenImage());
    greyscale.addActionListener(l -> f.greyscaleImage());
    sepia.addActionListener(l -> f.sepiaImage());
    dither.addActionListener(l -> f.ditherImage());
    undo.addActionListener(l -> f.undo());
    redo.addActionListener(l -> f.redo());
    mosaic.addActionListener(l -> {
      int result = displayInput("Mosaic Seeds (Whole numbers only):");
      if (result == 0) {
        f.mosaicImage(getNumber());
      }
    });
    checkerboard.addActionListener(l -> {
      int result = displayInput("Square Size (Whole numbers only):");
      if (result == 0) {
        f.createCheckerboard(getNumber());
      }
    });
    rainbow.addActionListener(l -> {
      int result = displayCustomBox(rainbowRadioButtons());
      if (result == 0) {
        String input = getInputs();
        f.createRainbow(input);
      }
    });
    flag.addActionListener(l -> {
      int result = displayCustomBox(flagRadioButtons());
      if (result == 0) {
        String input = getInputs();
        f.createFlag(input);
      }
    });
    script.addActionListener(l -> {
      int result = displayScriptBox();
      if (result == 0) {
        String script = getScript();
        f.processScript(script);
      }
    });
    quit.addActionListener(l -> {
      int result = displayQuit();
      if (result == 0) {
        f.quit();
      }
    });
    this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        int result = displayQuit();
        if (result == 0) {
          f.quit();
        }
      }
    });
  }

  /**
   * Helper method to display a JOptionPane to ensure the user wants to quit the user interface
   * program. Their choice is represented as a int.
   *
   * @return an int representing the choice they selected on if user wanted to quit
   */
  private int displayQuit() {
    JPanel popup = new JPanel(new BorderLayout());
    JPanel label = new JPanel(new GridLayout(0, 1, 8, 8));
    label.add(new JLabel("Are you sure you want to exit? All unsaved changes will be lost.",
            SwingConstants.CENTER));
    popup.add(label, BorderLayout.NORTH);
    return JOptionPane.showConfirmDialog(this, popup, "Exiting Program",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
  }

  /**
   * Helper method to display a JOptionPane to get user input from one text box. Takes in a string
   * input for what kind of input we are asking for.
   *
   * @return an int representing the choice if they want to continue with this feature
   */
  private int displayInput(String input) {
    JPanel popup = new JPanel(new BorderLayout());
    JPanel label = new JPanel(new GridLayout(0, 1, 8, 8));
    label.add(new JLabel(input, SwingConstants.CENTER));
    popup.add(label, BorderLayout.NORTH);
    JPanel text = new JPanel(new FlowLayout());
    this.num = new JTextField();
    this.num.setPreferredSize(new Dimension(100, 30));
    text.add(this.num);
    popup.add(text);
    popup.setPreferredSize(new Dimension(250, 50));
    return JOptionPane.showConfirmDialog(this, popup, "",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
  }

  /**
   * Helper method to display a custom JOptionPane to take in inputs for certain menu items. This
   * has 2 text boxes that is suppose to take in the height and the width. Then it has a Jpanel that
   * is a usually a list of predetermined choices with radio buttons. After the user is done with
   * the display, they have 3 choices on whether or not to pass the information on to continue the
   * feature.
   *
   * @param radioPanel JPanel object that is another type of input rather than the text boxes.
   *                   Usually in this case it will be radio buttons.
   * @return int that represents the button option chosen by the user in the JOptionPane, either OK
   * meaning do this, Cancel or "x" meaning forget my inputs and don't act on them.
   */
  private int displayCustomBox(JPanel radioPanel) {
    JPanel popup = new JPanel(new BorderLayout());

    JPanel labels = new JPanel(new GridLayout(0, 1, 8, 8));
    labels.add(new JLabel("Height:", SwingConstants.RIGHT));
    labels.add(new JLabel("Width:", SwingConstants.RIGHT));
    popup.add(labels, BorderLayout.WEST);

    JPanel inputs = new JPanel(new GridLayout(0, 1, 8, 8));
    height = new JTextField();
    width = new JTextField();
    inputs.add(height);
    inputs.add(width);
    popup.add(inputs, BorderLayout.CENTER);

    JPanel radioSelection = radioPanel;

    popup.add(radioSelection, BorderLayout.SOUTH);
    popup.setPreferredSize(new Dimension(275, 150));

    return JOptionPane.showConfirmDialog(this, popup, "Create",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
  }

  /**
   * Helper method to display a custom JOptionPane with a large text area to take in inputs for
   * scripting. After the user is done with the display, they have 3 choices on whether or not to
   * pass the information on to continue the feature.
   *
   * @return int that represents the button option chosen by the user in the JOptionPane, either OK
   * meaning do this, Cancel or "x" meaning forget my inputs and don't act on them.
   */
  private int displayScriptBox() {
    JPanel popup = new JPanel(new BorderLayout());
    JPanel labels = new JPanel(new GridLayout(0, 1, 8, 8));
    labels.add(new JLabel("Please input the script. Scripting instructions are in the README.",
            SwingConstants.CENTER));
    popup.add(labels, BorderLayout.NORTH);
    JPanel text = new JPanel(new GridLayout(0, 1, 8, 8));
    scriptText = new JTextArea(400, 400);
    text.add(scriptText);
    popup.add(text);
    popup.setPreferredSize(new Dimension(500, 500));
    return JOptionPane.showConfirmDialog(this, popup, "Script Processing",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
  }

  /**
   * Helper method to set up a JPanel of radio buttons for rainbows allowing two choices, Vertical
   * or Horizontal direction of rainbow choices.
   *
   * @return JPanel of radio buttons for create rainbows
   */
  private JPanel rainbowRadioButtons() {
    JRadioButton vertical = new JRadioButton("Vertical", true);
    vertical.setActionCommand("vertical");
    JRadioButton horizontal = new JRadioButton("Horizontal");
    horizontal.setActionCommand("horizontal");

    bGroup = new ButtonGroup();
    bGroup.add(vertical);
    bGroup.add(horizontal);

    JPanel radioSelection = new JPanel(new GridLayout(2, 1));
    radioSelection.add(vertical);
    radioSelection.add(horizontal);
    radioSelection.setBorder((BorderFactory.createTitledBorder("Direction of rainbow " +
            "stripes:")));

    return radioSelection;
  }

  /**
   * Helper method to set up a JPanel of radio buttons for flags allowing for 3 choices of
   * countries, France, Greece or Switzerland. Used to create flags of these countries.
   *
   * @return JPanel of radio buttons for create flag
   */
  private JPanel flagRadioButtons() {
    JRadioButton france = new JRadioButton("France", true);
    france.setActionCommand("France");
    JRadioButton greece = new JRadioButton("Greece");
    greece.setActionCommand("Greece");
    JRadioButton swiss = new JRadioButton("Switzerland");
    swiss.setActionCommand("Switzerland");

    bGroup = new ButtonGroup();
    bGroup.add(france);
    bGroup.add(greece);
    bGroup.add(swiss);

    JPanel radioSelection = new JPanel(new GridLayout(3, 1));
    radioSelection.add(france);
    radioSelection.add(greece);
    radioSelection.add(swiss);
    radioSelection.setBorder((BorderFactory.createTitledBorder("Country:")));

    return radioSelection;
  }

  /**
   * Helper method to retrieve the information that the view gets from the user input from 1 text
   * field. The inputs that is taken in should be a number.
   *
   * @return String that represents the input of the user
   */
  private String getNumber() {
    String num;
    num = this.num.getText();
    return num;
  }

  /**
   * Helper method to retrieve the information that the view gets from the user input from 3
   * different fields. The inputs are taken in, first 2 are the height and width, last input is a
   * predetermined list of choices. The string will be provided to the controller.
   *
   * @return String that represents the inputs of the user
   */
  private String getInputs() {
    StringBuilder output = new StringBuilder();
    String h = height.getText();
    String w = width.getText();
    String picked = bGroup.getSelection().getActionCommand();
    if (h.equals("") || w.equals("")) {
      return "1";
    }
    output.append(h).append(" ").append(w).append(" ").append(picked);
    return output.toString();
  }

  /**
   * Helper method to retrieve the information that the view gets from the user input from the
   * script panel popup. This will be given to the controller.
   *
   * @return String that represents the inputs of the user in the script text box
   */
  private String getScript() {
    String script = scriptText.getText();
    return script;
  }

  @Override
  public String loadFile() {
    String filePath = null;
    final JFileChooser fchooser = new JFileChooser(("."));
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG, GIF, PNG Images", "png", "jpg", "gif");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      filePath = f.getAbsolutePath();
    }
    return filePath;
  }

  @Override
  public void displayImage(BufferedImage image) {
    this.imageLabel.setIcon(new ImageIcon(image));
    this.picturePanel.add(this.imageScroll, CENTER_ALIGNMENT);
    this.setVisible(true);
  }

  @Override
  public void displayMessage(String message, String messageType) {
    switch (messageType) {
      case "error":
        JOptionPane.showMessageDialog(this, message, "Error",
                JOptionPane.ERROR_MESSAGE);
        break;
      case "info":
        JOptionPane.showMessageDialog(this, message, "Message",
                JOptionPane.INFORMATION_MESSAGE);
        break;
    }
  }

  @Override
  public String saveImage() {
    String filePath = null;
    final JFileChooser fchooser = new JFileChooser(("."));
    int retvalue = fchooser.showSaveDialog(this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      filePath = f.getAbsolutePath();
    }
    return filePath;
  }

  @Override
  public void enable(String menuItem) {
    switch (menuItem) {
      case "undo":
        undo.setEnabled(true);
        break;
      case "redo":
        redo.setEnabled(true);
        break;
    }
  }

  @Override
  public void disable(String menuItem) {
    switch (menuItem) {
      case "undo":
        undo.setEnabled(false);
        break;
      case "redo":
        redo.setEnabled(false);
        break;
    }
  }

  @Override
  public void setMenuItemDisplay() {
    save.setEnabled(true);
    blur.setEnabled(true);
    sharpen.setEnabled(true);
    greyscale.setEnabled(true);
    sepia.setEnabled(true);
    dither.setEnabled(true);
    mosaic.setEnabled(true);
  }

}
