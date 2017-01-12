import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ImageGeneratorTest {

    private File resourceFolder = createFile("images/");

    private File originalImage = createFile("images/image.jpg");
    private File generateImage = createFile("images/new_image.jpg");

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        originalImage.delete();
        generateImage.delete();
    }

    @Test
    public void testCreate() throws Exception {
        assertEquals(1, resourceFolder.list().length);
        new ImageGenerator().copy("jpg", originalImage, generateImage);
        assertEquals(2, resourceFolder.list().length);
    }

    private File createFile(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("").getPath() + resourceName);
    }
}