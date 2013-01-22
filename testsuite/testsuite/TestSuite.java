package testsuite;

import static junit.framework.Assert.assertEquals;
import static testsuite.TestSuiteHelper.runMain;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import picture.Utils;

public class TestSuite {

  @Rule
  public TemporaryFolder tmpFolder = new TemporaryFolder();

  @Test
  public void invertBlack() throws IOException {
    assertEquals(Utils.loadPicture("images/white64x64.png"),
        runMain(tmpFolder, "invert", "images/black64x64.png"));
  }

  @Test
  public void grayscaleBlack() throws IOException {
    assertEquals(Utils.loadPicture("images/black64x64.png"),
        runMain(tmpFolder, "grayscale", "images/black64x64.png"));
  }

  @Test
  public void rotate90Green() throws IOException {
    assertEquals(Utils.loadPicture("images/green64x64R90doc.png"),
        runMain(tmpFolder, "rotate", "90", "images/green64x64doc.png"));
  }

  @Test
  public void flipVGreen() throws IOException {
    assertEquals(Utils.loadPicture("images/green64x64FVdoc.png"),
        runMain(tmpFolder, "flip", "V", "images/green64x64doc.png"));
  }

  @Test
  public void blurBWPatterns() throws IOException {
    assertEquals(Utils.loadPicture("images/bwpatternsblur64x64.png"),
        runMain(tmpFolder, "blur", "images/bwpatterns64x64.png"));
  }

  @Test
  public void blendRainbowPatterns() throws IOException {
    assertEquals(Utils.loadPicture("images/rainbowpatternsblend64x64.png"),
        runMain(tmpFolder, "blend", "images/bwpatterns64x64.png", 
          "images/rainbow64x64doc.png"));
  }

  /* This should always be successful as there are no reference images... */
  @Test
  public void mosaicBlackWhiteGreenBlue() throws IOException {
    assertEquals(Utils.loadPicture("images/blackwhitegreenblueMosaic64x64.png"),
        runMain(tmpFolder, "mosaic", "10", "images/black64x64.png", "images/white64x64.png", 
          "images/green64x64doc.png", "images/blue64x32doc.png"));
  }

}
