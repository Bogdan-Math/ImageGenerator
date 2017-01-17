import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ResourceTest {

    private String folderName = "images/";
    private String originalImageName = "original_image";
    private String format = "jpg";

    private Resource resource = new Resource(createFile(folderName + originalImageName + "." + format));

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getFullName() {
        assertEquals("original_image.jpg", resource.getNameWithExtension());
    }

    @Test
    public void getName() {
        assertEquals("original_image", resource.getName());
    }

    @Test
    public void getExtension() throws Exception {
        assertEquals("jpg", resource.getExtension());
    }

    private File createFile(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("").getPath() + resourceName);
    }

}