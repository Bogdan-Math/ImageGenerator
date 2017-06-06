package utility;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResourceReaderTest {

    private ResourceReader resourceReader;

    @Before
    public void setUp() throws Exception {
        this.resourceReader = new ResourceReader();
    }

    @Test
    public void read() throws Exception {
        assertTrue(resourceReader.readFile("images/canonical.jpg").exists());
        assertTrue(resourceReader.readFile("images/canonical.jpg").isFile());
        assertFalse(resourceReader.readFile("images/canonical.jpg").isDirectory());
    }

}