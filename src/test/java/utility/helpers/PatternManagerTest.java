package utility.helpers;

import org.junit.Before;
import org.junit.Test;
import utility.helpers.PatternManager;
import utility.helpers.ResourceReader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PatternManagerTest {

    private PatternManager patternManager;
    private ResourceReader resourceReader;
    private File originalImage;

    @Before
    public void setUp() throws Exception {
        this.patternManager = new PatternManager();
        this.resourceReader = new ResourceReader();
        this.originalImage  = resourceReader.readFile("images/canonical.jpg");
    }

    @Test
    public void patternsMap() throws Exception {
        String path = "images/colors";
        int patternsCount = 24;

        Map<Color, BufferedImage> patterns = patternManager.patternsMap(resourceReader.readFiles(path));

        assertNotNull(patterns);
        assertEquals(patternsCount, patterns.values().size());

    }

    @Test
    public void averagedColor() throws Exception {
        Color averagedColor = patternManager.averagedColor(originalImage);

        assertThat(averagedColor.getRed(), is(68));
        assertThat(averagedColor.getGreen(), is(138));
        assertThat(averagedColor.getBlue(), is(172));
    }

}