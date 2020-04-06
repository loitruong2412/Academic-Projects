import org.junit.Before;
import org.junit.Test;

import java.util.EmptyStackException;

import controller.GUIController;
import model.ImageModelPlus;
import view.IView;

import static org.junit.Assert.assertEquals;

/**
 * A test for the GUI Controller with a mock view and a mock model.
 */
public class GUIControllerTest {

  private StringBuilder modelLog;
  private StringBuilder viewLog;
  private ImageModelPlus mockModel;
  private IView mockView;
  private ImageModelFactoryMock mockFactory;

  @Before
  public void setUp() {
    modelLog = new StringBuilder();
    viewLog = new StringBuilder();
    mockModel = new MockModel(modelLog);
    mockView = new MockView(viewLog);
    mockFactory = new ImageModelFactoryMock(mockModel);
  }

  @Test
  public void testInit() {
    new GUIController(mockFactory, mockView);
    assertEquals("model...", modelLog.toString());
    assertEquals("view...setting features\ndisable menu item\n" +
            "disable menu item\n", viewLog.toString());
  }

  @Test
  public void testLoad() {
    new GUIController(mockFactory, mockView).load();
    assertEquals("model...", modelLog.toString());
    String init = "view...setting features\ndisable menu item\n" +
            "disable menu item\n";
    assertEquals(init + "load image\ndisplaying message\n", viewLog.toString());
  }

  @Test
  public void testSave() {
    String init = "view...setting features\ndisable menu item\n" +
            "disable menu item\n";
    try {
      new GUIController(mockFactory, mockView).save();
      assertEquals("model...", modelLog.toString());
      assertEquals(init + "save image\ndisplaying message\n", viewLog.toString());
    } catch (NullPointerException e) {
    }
  }

  @Test (expected = NullPointerException.class)
  public void testSaveNoFile() {
    String init = "view...setting features\ndisable menu item\n" +
            "disable menu item\n";
    new GUIController(mockFactory, mockView).save();
    assertEquals("model...", modelLog.toString());
    assertEquals(init + "save image\ndisplaying message\n", viewLog.toString());
  }

  @Test
  public void testCreateRainbow() {
    new GUIController(mockFactory, mockView).createRainbow("inputs");
    assertEquals("model...", modelLog.toString());
    String init = "view...setting features\ndisable menu item\n" +
            "disable menu item\n";
    assertEquals(init + "displaying message\n", viewLog.toString());
  }

  @Test
  public void testCreateFlag() {
    new GUIController(mockFactory, mockView).createFlag("inputs");
    assertEquals("model...", modelLog.toString());
    String init = "view...setting features\ndisable menu item\n" +
            "disable menu item\n";
    assertEquals(init + "displaying message\n", viewLog.toString());
  }

  @Test
  public void testCreateCheckerboard() {
    new GUIController(mockFactory, mockView).createCheckerboard("inputs");
    assertEquals("model...", modelLog.toString());
    String init = "view...setting features\ndisable menu item\n" +
            "disable menu item\n";
    assertEquals(init + "displaying message\n", viewLog.toString());
  }

  @Test
  public void testBlurImage() {
    new GUIController(mockFactory, mockView).blurImage();
    assertEquals("model...", modelLog.toString());
    String init = "view...setting features\ndisable menu item\n" +
            "disable menu item\n";
    assertEquals(init + "displaying message\n", viewLog.toString());
  }

  @Test
  public void testSharpenImage() {
    new GUIController(mockFactory, mockView).sharpenImage();
    assertEquals("model...", modelLog.toString());
    String init = "view...setting features\ndisable menu item\n" +
            "disable menu item\n";
    assertEquals(init + "displaying message\n", viewLog.toString());
  }

  @Test
  public void testGreyscaleImage() {
    new GUIController(mockFactory, mockView).greyscaleImage();
    assertEquals("model...", modelLog.toString());
    String init = "view...setting features\ndisable menu item\n" +
            "disable menu item\n";
    assertEquals(init + "displaying message\n", viewLog.toString());
  }

  @Test
  public void testSepiaImage() {
    new GUIController(mockFactory, mockView).sepiaImage();
    assertEquals("model...", modelLog.toString());
    String init = "view...setting features\ndisable menu item\n" +
            "disable menu item\n";
    assertEquals(init + "displaying message\n", viewLog.toString());
  }

  @Test
  public void testDitherImage() {
    new GUIController(mockFactory, mockView).ditherImage();
    assertEquals("model...", modelLog.toString());
    String init = "view...setting features\ndisable menu item\n" +
            "disable menu item\n";
    assertEquals(init + "displaying message\n", viewLog.toString());
  }

  @Test
  public void testMosaicImage() {
    new GUIController(mockFactory, mockView).mosaicImage("input");
    assertEquals("model...", modelLog.toString());
    String init = "view...setting features\ndisable menu item\n" +
            "disable menu item\n";
    assertEquals(init + "displaying message\n", viewLog.toString());
  }

  @Test
  public void testProcessScript() {
    new GUIController(mockFactory, mockView).processScript("abc");
    assertEquals("model...", modelLog.toString());
    String init = "view...setting features\ndisable menu item\n" +
            "disable menu item\n";
    assertEquals(init + "displaying message\n", viewLog.toString());
  }

  @Test
  public void testRedo() {
    try {
      new GUIController(mockFactory, mockView).redo();
      assertEquals("model...", modelLog.toString());
      String init = "view...setting features\ndisable menu item\n" +
              "disable menu item\n";
      assertEquals(init + "displaying message\n", viewLog.toString());
    } catch (EmptyStackException e) {
    }
  }

  @Test (expected = EmptyStackException.class)
  public void testRedoExceptionExpected() {
    new GUIController(mockFactory, mockView).redo();
    assertEquals("model...", modelLog.toString());
    String init = "view...setting features\ndisable menu item\n" +
            "disable menu item\n";
    assertEquals(init + "displaying message\n", viewLog.toString());
  }

  @Test
  public void testUndo() {
    try {
      new GUIController(mockFactory, mockView).undo();
      assertEquals("model...", modelLog.toString());
      String init = "view...setting features\ndisable menu item\n" +
              "disable menu item\n";
      assertEquals(init + "displaying message\n", viewLog.toString());
    } catch (EmptyStackException e) {
    }
  }

  @Test (expected = EmptyStackException.class)
  public void testUndoExceptionExpected() {
    new GUIController(mockFactory, mockView).undo();
    assertEquals("model...", modelLog.toString());
    String init = "view...setting features\ndisable menu item\n" +
            "disable menu item\n";
    assertEquals(init + "displaying message\n", viewLog.toString());
  }

}