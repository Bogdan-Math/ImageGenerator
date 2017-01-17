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
    public void getOriginalImage() throws Exception {
        assertEquals(bufferedImage, originalImage.getOriginalImage());
    }

    @Test//TODO: add test
    public void getCopy() throws Exception {
    }

    @Test//TODO: add test
    public void getSubImage() throws Exception {
    }

    private File createFile(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("").getPath() + resourceName);
    }


}