package layers.service;

import org.junit.Before;
import org.junit.Test;
import utility.helpers.ResourceReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;

public class GeneralColorInfoTest {

    private ColorInfo colorInfo;

    @Before
    public void setUp() throws Exception {
        this.colorInfo = new GeneralColorInfo();
    }

    @Test
    public void averagedColor() throws Exception {
        ResourceReader resourceReader = new ResourceReader();
        BufferedImage whiteImage = ImageIO.read(resourceReader.readFile("images/colors/1-white.jpg"));
        BufferedImage grayImage  = ImageIO.read(resourceReader.readFile("images/colors/2-grey.jpg"));
        BufferedImage blackImage = ImageIO.read(resourceReader.readFile("images/colors/3-black.jpg"));

        Color white = colorInfo.averagedColor(whiteImage);
        Color gray = colorInfo.averagedColor(grayImage);
        Color black = colorInfo.averagedColor(blackImage);

        assertEquals(Color.WHITE, white);
        assertEquals(Color.GRAY, gray);
        assertEquals(Color.BLACK, black);
    }
}