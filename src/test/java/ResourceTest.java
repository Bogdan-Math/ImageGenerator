import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

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
    public void isFolder() throws Exception {
        assertFalse(resource.isFolder());
    }

    @Test
    public void isFile() throws Exception {
        assertTrue(resource.isFile());
    }

    private File createFile(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("").getPath() + resourceName);
    }

}