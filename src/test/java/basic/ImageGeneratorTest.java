package basic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ImageGeneratorTest {

    private File originalImage;
    private File generateImage;
    private File resourceFolder;

    private ImageGenerator imageGenerator;

    @Before
    public void setUp() throws Exception {
        this.originalImage = createFile("puppy.jpg");
        this.generateImage = createFile("generate_image.jpg");
        this.resourceFolder = createFile("");

        this.imageGenerator = new ImageGenerator();
    }

    @After
    public void tearDown() throws Exception {
        generateImage.delete();
    }

    @Test
    public void copyImage() throws Exception {
        assertEquals(new Long(1), getFilesCount());
        imageGenerator.copyImage(originalImage, generateImage, "jpg");
        assertEquals(new Long(2), getFilesCount());
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

    private Long getFilesCount() {
        return Arrays.stream(resourceFolder.list()).filter(resource -> createFile(resource).isFile()).count();
    }

    private File createFile(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("images/").getPath() + resourceName);
    }
}