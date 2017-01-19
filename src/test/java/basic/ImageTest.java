package basic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import static org.junit.Assert.*;

public class ImageTest {

    private BufferedImage bufferedImage;
    private Image originalImage;

    @Before
    public void setUp() throws Exception {
        this.bufferedImage = ImageIO.read(createFile("images/original_image.jpg"));
        this.originalImage = new Image(bufferedImage);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getBufferedImage() throws Exception {
        assertEquals(bufferedImage, originalImage.getBufferedImage());
    }

    @Test
    public void getCopy() throws Exception {

        BufferedImage original = originalImage.getBufferedImage();
        BufferedImage compared = originalImage.getCopy().getBufferedImage();

        assertNotEquals(original, compared);
        assertEquals(original.getWidth(), compared.getWidth());
        assertEquals(original.getWidth(), compared.getWidth());

        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                assertEquals(original.getRGB(x, y), compared.getRGB(x, y));
            }
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getSubImage() throws Exception {

        BufferedImage original = originalImage.getBufferedImage();
        BufferedImage copied = originalImage.getSubImage(0, 0, 25, 25);

        assertNotEquals(original, copied);
        assertNotEquals(original.getWidth(), copied.getWidth());
        assertNotEquals(original.getWidth(), copied.getWidth());

        for (int y = 0; y < copied.getHeight(); y++) {
            for (int x = 0; x < copied.getWidth(); x++) {
                assertEquals(original.getRGB(x, y), copied.getRGB(x, y));
            }
        }

        copied.getRGB(original.getWidth(), original.getHeight());
    }

    private File createFile(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("").getPath() + resourceName);
    }


}