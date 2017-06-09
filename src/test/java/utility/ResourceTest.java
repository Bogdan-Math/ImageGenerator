package utility;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

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
    public void getPatternsIn() throws Exception {
        String path = "images/colors";
        int patternsCount = 24;

        Map<Color, BufferedImage> patterns = resource.getPatternsIn(path);

        assertNotNull(patterns);
        assertEquals(patternsCount, patterns.values().size());
    }

    @Test
    public void readFiles() throws Exception {
        String path = "images/colors";
        int patternsCount = 24;

        List<File> files = resource.readFiles(path);

        assertNotNull(files);
        assertEquals(patternsCount, files.size());
    }

}