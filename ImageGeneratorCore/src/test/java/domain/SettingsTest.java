package domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import core.InformationalImage;

import javax.annotation.Resource;

import static domain.Settings.MAX_EXPECTED_COLUMNS_COUNT;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ContextConfiguration(locations = {
        "classpath:spring/basic-settings.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class SettingsTest {

    @Autowired
    private Settings settings;

    @Resource(name = "image")
    private InformationalImage image;

    @Before
    public void setUp() throws Exception {
        when(image.getWidth()).thenReturn(100);
        when(image.getHeight()).thenReturn(200);
        when(image.getSubImage(anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(new InformationalImage(1, 1, InformationalImage.TYPE_INT_RGB));
    }

    @Test
    public void getImage() throws Exception {
        assertNotNull(settings.getIncomeImage());
    }

    @Test
    public void getPatterns() throws Exception {
        assertNotNull(settings.getPatterns());
    }

    @Test
    public void getExpectedColumnsCount() throws Exception {
        assertThat(settings.getExpectedColumnsCount(), is(MAX_EXPECTED_COLUMNS_COUNT / 2));
    }

    @Test
    public void getImageWidth() throws Exception {
        assertThat(settings.getImageWidth(), is(100));
        verify(image, times(1)).getWidth();
    }

    @Test
    public void getImageHeight() throws Exception {
        assertThat(settings.getImageHeight(), is(200));
        verify(image, times(1)).getHeight();
    }

    @Test
    public void getSubImage() throws Exception {
        InformationalImage subImage = settings.getSubImage(1, 1, 1, 1);

        assertNotNull(subImage);
        verify(image, times(1)).getSubImage(eq(1), eq(1), eq(1), eq(1));
        assertThat(subImage.getWidth(), is(1));
        assertThat(subImage.getHeight(), is(1));
    }

    @Test
    public void getImageFileName() throws Exception {
        assertThat(settings.getImageFileName(), is("image-file-name.jpg"));
    }
}