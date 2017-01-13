import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ImageGeneratorTest {

    private String folderName = "images/";
    private File resourceFolder = createFile(folderName);

    private String originalImageName = "original_image";
    private String originalImageExtension = "jpg";
    private File originalImage = createFile(folderName + originalImageName + "." + originalImageExtension);

    private String generateImageName = "generate_image";
    private String generateImageExtension = "jpg";
    private File generateImage = createFile(folderName + generateImageName + "." + generateImageExtension);

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        originalImage.delete();
        generateImage.delete();
    }

    @Test
    public void testCopy() throws Exception {
        assertEquals(1, resourceFolder.list().length);
        new ImageGenerator().copy("jpg", originalImage, generateImage);
        assertEquals(2, resourceFolder.list().length);
    }

    private File createFile(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("").getPath() + resourceName);
    }
}