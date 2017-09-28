package utility.helper;

import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;
import static utility.helper.ImageInformation.averagedColor;

public class ImageInformationTest {

    @Before
    public void setUp() throws Exception {
        new ImageInformation();
    }

    @Test
    public void testAveragedColor() throws Exception {
        ResourceReader resourceReader = new ResourceReader();
        BufferedImage whiteImage = ImageIO.read(resourceReader.readFile("images/colors/1-white.jpg"));
        BufferedImage grayImage  = ImageIO.read(resourceReader.readFile("images/colors/2-grey.jpg"));
        BufferedImage blackImage = ImageIO.read(resourceReader.readFile("images/colors/3-black.jpg"));

        Color white = averagedColor(whiteImage);
        Color gray  = averagedColor(grayImage);
        Color black = averagedColor(blackImage);

        assertEquals(Color.WHITE, white);
        assertEquals(Color.GRAY,  gray);
        assertEquals(Color.BLACK, black);
    }

}