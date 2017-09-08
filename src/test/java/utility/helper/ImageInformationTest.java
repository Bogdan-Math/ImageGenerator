package utility.helper;

import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.Assert.*;

public class ImageInformationTest {

    private ImageInformation imageInformation;

    @Before
    public void setUp() throws Exception {
        this.imageInformation = new ImageInformation();
    }

    @Test
    public void testAveragedColor() throws Exception {
        ResourceReader resourceReader = new ResourceReader();
        BufferedImage whiteImage = ImageIO.read(resourceReader.readFile("images/colors/1-white.jpg"));
        BufferedImage grayImage  = ImageIO.read(resourceReader.readFile("images/colors/2-grey.jpg"));
        BufferedImage blackImage = ImageIO.read(resourceReader.readFile("images/colors/3-black.jpg"));

        Color white = imageInformation.averagedColor(whiteImage);
        Color gray  = imageInformation.averagedColor(grayImage);
        Color black = imageInformation.averagedColor(blackImage);

        assertEquals(Color.WHITE, white);
        assertEquals(Color.GRAY,  gray);
        assertEquals(Color.BLACK, black);
    }

}