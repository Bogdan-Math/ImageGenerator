import org.junit.Test;
import utility.helpers.ObjectTypeConverter;
import utility.helpers.ResourceReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CutActionTest {

    @Test
    public void testCut() throws Exception {
        ResourceReader resourceReader = new ResourceReader();
        resourceReader.readFiles("images/for_cut/32x32")
                      //.stream()
                      //.filter(file -> file.getName().equals("1-white.jpg"))
                      .forEach(file -> {
                          BufferedImage oldImage = new ObjectTypeConverter().bufferedImage(file);
                          System.out.println(oldImage.getWidth() + "x" + oldImage.getHeight());
                          BufferedImage newImage = cut(oldImage);
                          System.out.println(newImage.getWidth() + "x" + newImage.getHeight());
                          try {
                              String pathname = "/home/bmath/32x32/" + file.getName();

                              ImageIO.write(newImage, "png", new FileOutputStream(new File(pathname)));
                          } catch (IOException e) {
                              e.printStackTrace();
                          }
                      });
    }

    private BufferedImage cut(BufferedImage inputImg) {
        return inputImg.getSubimage(1, 5, inputImg.getWidth() - 2, inputImg.getHeight() - 10);
    }
}
