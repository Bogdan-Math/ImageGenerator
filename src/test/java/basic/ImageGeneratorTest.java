package basic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ImageGeneratorTest {

    private File originalImage;
    private File generateImage;
    private File resourceFolder;

    private ImageGenerator imageGenerator;

    @Before
    public void setUp() throws Exception {
        this.originalImage = createFile("images/original_image.jpg");
        this.generateImage = createFile("images/generate_image.jpg");
        this.resourceFolder = createFile("images/");

        this.imageGenerator = new ImageGenerator();
    }

    @After
    public void tearDown() throws Exception {
        generateImage.delete();
    }

    @Test
    public void copyImage() throws Exception {
        assertEquals(1, resourceFolder.list().length);
        imageGenerator.copyImage(originalImage, generateImage, "jpg");
        assertEquals(2, resourceFolder.list().length);
    }

    @Test
    public void fileToBufferedImage() throws Exception {
        assertNotNull(imageGenerator.fileToBufferedImage(originalImage));
    }

    @Test
    public void bufferedImageToByteArray() throws Exception {
        assertNotNull(imageGenerator
                .bufferedImageToByteArray(imageGenerator.fileToBufferedImage(originalImage), "jpg"));
    }

    @Test
    public void byteArrayToBufferedImage() throws Exception {
        assertNotNull(imageGenerator
                .byteArrayToBufferedImage(imageGenerator
                        .bufferedImageToByteArray(imageGenerator.fileToBufferedImage(originalImage), "jpg")));
    }

    private File createFile(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("").getPath() + resourceName);
    }
}