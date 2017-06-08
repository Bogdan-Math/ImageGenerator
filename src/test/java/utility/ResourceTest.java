package utility;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ResourceTest {

    private Resource resource;

    @Before
    public void setUp() throws Exception {
        this.resource = new Resource();
    }

    @Test
    public void readFile() throws Exception {
        assertTrue(resource.readFile("images/canonical.jpg").exists());
        assertTrue(resource.readFile("images/canonical.jpg").isFile());
        assertFalse(resource.readFile("images/canonical.jpg").isDirectory());
    }

    @Test
    public void getPatternsFrom() throws Exception {
        //TODO: add test
    }
}