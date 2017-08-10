package utility.helpers;

import org.junit.Before;
import org.junit.Test;
import utility.helpers.ResourceReader;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class ResourceReaderTest {

    private ResourceReader resourceReader;

    @Before
    public void setUp() throws Exception {
        this.resourceReader = new ResourceReader();
    }

    @Test
    public void testReadFile() throws Exception {
        assertTrue(resourceReader.readFile("images/canonical.jpg").exists());
        assertTrue(resourceReader.readFile("images/canonical.jpg").isFile());
        assertFalse(resourceReader.readFile("images/canonical.jpg").isDirectory());
    }

    @Test
    public void testReadFiles() throws Exception {
        String path = "images/colors";
        int patternsCount = 24;

        List<File> files = resourceReader.readFiles(path);

        assertNotNull(files);
        assertEquals(patternsCount, files.size());
    }

}