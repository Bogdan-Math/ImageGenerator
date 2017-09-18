package domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ContextConfiguration(locations = {
        "classpath:spring/basic-settings.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class BasicSettingsTest {

    @Autowired
    private Settings settings;

    @Resource(name = "image")
    private BufferedImage image;

    @Before
    public void setUp() throws Exception {
        when(image.getWidth()).thenReturn(100);
        when(image.getHeight()).thenReturn(200);
        when(image.getSubimage(anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
    }

    @Test
    public void getImage() throws Exception {
        assertNotNull(settings.getImage());
        assertTrue(settings.getImage() instanceof BufferedImage);
    }

    @Test
    public void getPatterns() throws Exception {
        assertNotNull(settings.getPatterns());
        assertTrue(settings.getPatterns() instanceof HashMap);
    }

    @Test
    public void getExpectedColumnsNumber() throws Exception {
        assertThat(settings.getExpectedColumnsNumber(), is(10));
    }

    @Test
    public void getImageWidth() throws Exception {
        assertThat(settings.getImageWidth(), is(100));
    }

    @Test
    public void getImageHeight() throws Exception {
        assertThat(settings.getImageHeight(), is(200));
    }

    @Test
    public void getSubImage() throws Exception {
        assertThat(settings.getSubImage(1, 1, 1, 1).getWidth(), is(1));
        assertThat(settings.getSubImage(1, 1, 1, 1).getHeight(), is(1));
    }

}