import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ImageGeneratorTest {

    private String format = "jpg";
    private String folderName = "images/";

    private String originalImageName = "original_image";
    private File originalImage = createFile(folderName + originalImageName + "." + format);

    private String generateImageName = "generate_image";
    private File generateImage = createFile(folderName + generateImageName + "." + format);

    private File resourceFolder = createFile(folderName);

    private ImageGenerator imageGenerator = new ImageGenerator();

    @Before
    public void setUp() throws Exception {
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
                .bufferedImageToByteArray(imageGenerator.fileToBufferedImage(originalImage), format));
    }

    @Test
    public void byteArrayToBufferedImage() throws Exception {
        assertNotNull(imageGenerator
                .byteArrayToBufferedImage(imageGenerator
                        .bufferedImageToByteArray(imageGenerator.fileToBufferedImage(originalImage), format)));
    }

    private File createFile(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("").getPath() + resourceName);
    }
}