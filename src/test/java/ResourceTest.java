import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ResourceTest {

    private Resource resource;

    @Before
    public void setUp() throws Exception {
        this.resource  = new Resource(createFile("images/original_image.jpg"));
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