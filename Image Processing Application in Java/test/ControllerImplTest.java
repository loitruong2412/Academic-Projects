import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import controller.Controller;
import controller.ControllerImpl;
import model.Factory;
import model.ImageModelFactory;
import model.ImageModelPlus;

import static org.junit.Assert.assertEquals;

/**
 * This is a class to test ControllerImpl and to ensure the correct statements are outputted and
 * that the methods are getting called in for the model.
 */
public class ControllerImplTest {


  // Test that the correct out message is logged instead of exceptions.
  // Empty Script
  @Test
  public void testGoEmptyInput() throws IOException {
    String input = "";
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader(input);
    StringBuilder log = new StringBuilder();
    ImageModelPlus mock = new MockModel(log);
    ImageModelFactoryMock mockFactory = new ImageModelFactoryMock(mock);
    Controller c = new ControllerImpl(in, out, mockFactory);
    c.run();
    assertEquals("model...", log.toString());
    assertEquals("This script is empty or your create does not have enough arguments.",
            out.toString());
  }

  @Test
  public void testGoSpaceInput() throws IOException {
    String input = "   ";
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader(input);
    StringBuilder log = new StringBuilder();
    ImageModelPlus mock = new MockModel(log);
    ImageModelFactoryMock mockFactory = new ImageModelFactoryMock(mock);
    Controller c = new ControllerImpl(in, out, mockFactory);
    c.run();
    assertEquals("model...", log.toString());
    assertEquals("This script is empty or your create does not have enough arguments.",
            out.toString());
  }

  // Doesn't start with load or create
  @Test
  public void testGoIllegalStart() throws IOException {
    String input = "blur";
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader(input);
    StringBuilder log = new StringBuilder();
    ImageModelPlus mock = new MockModel(log);
    ImageModelFactoryMock mockFactory = new ImageModelFactoryMock(mock);
    Controller c = new ControllerImpl(in, out, mockFactory);
    c.run();
    assertEquals("model...", log.toString());
    assertEquals("Start with a load or create.",
            out.toString());
  }

  // Test that create logs correct message.
  @Test
  public void testGoIllegalStartCreate() throws IOException {
    String input = "create puppies";
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader(input);
    StringBuilder log = new StringBuilder();
    ImageModelPlus mock = new MockModel(log);
    ImageModelFactoryMock mockFactory = new ImageModelFactoryMock(mock);
    Controller c = new ControllerImpl(in, out, mockFactory);
    c.run();
    assertEquals("model...", log.toString());
    assertEquals("puppies: cannot be created.",
            out.toString());
  }

  @Test
  public void testRestoreOnCreate() throws IOException {
    String input = "create checkerboard 30\nrestore";
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader(input);
    StringBuilder log = new StringBuilder();
    ImageModelPlus mock = new MockModel(log);
    ImageModelFactoryMock mockFactory = new ImageModelFactoryMock(mock);
    Controller c = new ControllerImpl(in, out, mockFactory);
    c.run();
    assertEquals("model...checkerboard\n", log.toString());
    assertEquals("There is no original image", out.toString());
  }

  // Loading non-existent file
  @Test
  public void testGoIllegalFailedLoad() throws IOException {
    String input = "load foo.png";
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader(input);
    StringBuilder log = new StringBuilder();
    ImageModelPlus mock = new MockModel(log);
    ImageModelFactoryMock mockFactory = new ImageModelFactoryMock(mock);
    Controller c = new ControllerImpl(in, out, mockFactory);
    c.run();
    assertEquals("model...", log.toString());
    assertEquals("File name not provided or image file does not exist",
            out.toString());
  }


  // Testing that parser throws correct exceptions.
  @Test
  public void testCreateCBNoInt() throws IOException {
    String input = "create checkerboard abc";
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader(input);
    StringBuilder log = new StringBuilder();
    ImageModelPlus mock = new MockModel(log);
    ImageModelFactoryMock mockFactory = new ImageModelFactoryMock(mock);
    Controller c = new ControllerImpl(in, out, mockFactory);
    c.run();
    assertEquals("model...", log.toString());
    assertEquals("Invalid square size for checkerboard",
            out.toString());
  }

  @Test
  public void testCreateRainbowBadInt() throws IOException {
    String input = "create rainbow abc 123 vertical";
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader(input);
    StringBuilder log = new StringBuilder();
    ImageModelPlus mock = new MockModel(log);
    ImageModelFactoryMock mockFactory = new ImageModelFactoryMock(mock);
    Controller c = new ControllerImpl(in, out, mockFactory);
    c.run();
    assertEquals("model...", log.toString());
    assertEquals("Invalid height and width for rainbow",
            out.toString());
  }

  @Test
  public void testCreateRainbowBadDirection() throws IOException {
    String input = "create rainbow 20 20\nblur";
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader(input);
    StringBuilder log = new StringBuilder();
    ImageModelPlus mock = new MockModel(log);
    ImageModelFactoryMock mockFactory = new ImageModelFactoryMock(mock);
    Controller c = new ControllerImpl(in, out, mockFactory);
    c.run();
    assertEquals("model...", log.toString());
    assertEquals("Missing or invalid direction for rainbow.",
            out.toString());
  }

  // Testing Quit
  @Test
  public void testQ() throws IOException {
    String input = "create checkerboard 30\nblur\nq";
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader(input);
    StringBuilder log = new StringBuilder();
    ImageModelPlus mock = new MockModel(log);
    ImageModelFactoryMock mockFactory = new ImageModelFactoryMock(mock);
    Controller c = new ControllerImpl(in, out, mockFactory);
    c.run();
    assertEquals("model...checkerboard\nblur\n", log.toString());
    assertEquals("Quitting", out.toString());
  }

  @Test
  public void testQuit() throws IOException {
    String input = "create checkerboard 30\nblur\nquit";
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader(input);
    StringBuilder log = new StringBuilder();
    ImageModelPlus mock = new MockModel(log);
    ImageModelFactoryMock mockFactory = new ImageModelFactoryMock(mock);
    Controller c = new ControllerImpl(in, out, mockFactory);
    c.run();
    assertEquals("model...checkerboard\nblur\n", log.toString());
    assertEquals("Quitting", out.toString());
  }

  // Testing Illegal Command and it doesn't continue after
  @Test
  public void testIllegalCommand2() throws IOException {
    String input = "create checkerboard 30\nblur\nfoo\nsharpen";
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader(input);
    StringBuilder log = new StringBuilder();
    ImageModelPlus mock = new MockModel(log);
    ImageModelFactoryMock mockFactory = new ImageModelFactoryMock(mock);
    Controller c = new ControllerImpl(in, out, mockFactory);
    c.run();
    assertEquals("model...checkerboard\nblur\n", log.toString());
    assertEquals("foo: The command does not exist.", out.toString());
  }

  // Testing good inputs
  @Test
  public void testGoLoad() throws IOException {
    String input = "create checkerboard 30";
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader(input);
    StringBuilder log = new StringBuilder();
    ImageModelPlus mock = new MockModel(log);
    ImageModelFactoryMock mockFactory = new ImageModelFactoryMock(mock);
    Controller c = new ControllerImpl(in, out, mockFactory);
    c.run();
    assertEquals("model...checkerboard\n", log.toString());
    assertEquals("Reached the end of script...Finished processing", out.toString());
  }

  // Testing legal commands without quit
  @Test
  public void testCommands() throws IOException {
    String input = "create checkerboard 30\nblur";
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader(input);
    StringBuilder log = new StringBuilder();
    ImageModelPlus mock = new MockModel(log);
    ImageModelFactoryMock mockFactory = new ImageModelFactoryMock(mock);
    Controller c = new ControllerImpl(in, out, mockFactory);
    c.run();
    assertEquals("model...checkerboard\nblur\n", log.toString());
    assertEquals("Reached the end of script...Finished processing", out.toString());
  }

  @Test
  public void testMultiCommands() throws IOException {
    String input = "create checkerboard 30\nblur\nmosaic 1000";
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader(input);
    StringBuilder log = new StringBuilder();
    ImageModelPlus mock = new MockModel(log);
    ImageModelFactoryMock mockFactory = new ImageModelFactoryMock(mock);
    Controller c = new ControllerImpl(in, out, mockFactory);
    c.run();
    assertEquals("model...checkerboard\nblur\nmosaic\n", log.toString());
    assertEquals("Reached the end of script...Finished processing",
            out.toString());
  }

  @Test
  public void testMostCommands() throws IOException {
    String input = "create checkerboard 30\nblur\nsharpen\nsepia\ngreyscale\ndither\nmosaic 1000\n"
            + "filter 1/16,1/8,1/16;1/8,1/4,1/8;1/16,1/8,1/16\n"
            + "transform 0.2126,0.7152,0.0722;0.2126,0.7152,0.0722;0.2126,0.7152,0.0722";
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader(input);
    StringBuilder log = new StringBuilder();
    ImageModelPlus mock = new MockModel(log);
    ImageModelFactoryMock mockFactory = new ImageModelFactoryMock(mock);
    Controller c = new ControllerImpl(in, out, mockFactory);
    c.run();
    String expected = "model...checkerboard\nblur\nsharpen\nsepia\ngreyscale\ndither\nmosaic"
            + "\nfilter\ntransform\n";
    assertEquals(expected, log.toString());
    assertEquals("Reached the end of script...Finished processing",
            out.toString());
  }

  @Test
  public void testCommandsWQ() throws IOException {
    String input = "create checkerboard 30\nblur\nq";
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader(input);
    StringBuilder log = new StringBuilder();
    ImageModelPlus mock = new MockModel(log);
    ImageModelFactoryMock mockFactory = new ImageModelFactoryMock(mock);
    Controller c = new ControllerImpl(in, out, mockFactory);
    c.run();
    assertEquals("model...checkerboard\nblur\n", log.toString());
    assertEquals("Quitting", out.toString());
  }
}