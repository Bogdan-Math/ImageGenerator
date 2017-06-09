package utility;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
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
        String path = "images/flags";
        int patternsCount = 195;

        Map<Color, BufferedImage> patterns = resource.getPatternsIn(path);

        assertNotNull(patterns);
        assertEquals(patternsCount, patterns.values().size());
    }
}