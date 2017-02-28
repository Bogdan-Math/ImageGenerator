package basic;

import org.junit.Test;

import static org.junit.Assert.*;

public class ImageSizeTest {

    @Test
    public void instanceTest() {
        assertNotNull(new ImageSize());
    }


    @Test
    public void widthTest() {
        assertEquals(25, ImageSize.WIDTH);
    }

    @Test
    public void heightTest() {
        assertEquals(25, ImageSize.HEIGHT);
    }

}