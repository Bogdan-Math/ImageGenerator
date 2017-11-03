package core;

import domain.InformationalImage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;

import static core.Settings.MAX_EXPECTED_COLUMNS_COUNT;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SettingsTest {

    private Settings settings = new BasicSettings();
    private InformationalImage incomeImage;

    @Before
    public void setUp() throws Exception {
        incomeImage = mock(InformationalImage.class);

        settings.setIncomeImage(incomeImage);
        settings.setPatterns(new HashMap<>());
        settings.setExpectedColumnsCount(Settings.MAX_EXPECTED_COLUMNS_COUNT / 2);
        settings.setImageFileName("image-file-name.jpg");

        when(incomeImage.getWidth()).thenReturn(100);
        when(incomeImage.getHeight()).thenReturn(200);
        when(incomeImage.getSubImage(anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(new InformationalImage(1, 1, InformationalImage.TYPE_INT_RGB));
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
        verify(incomeImage, times(1)).getWidth();
    }

    @Test
    public void getImageHeight() throws Exception {
        assertThat(settings.getImageHeight(), is(200));
        verify(incomeImage, times(1)).getHeight();
    }

    @Test
    public void getSubImage() throws Exception {
        InformationalImage subImage = settings.getSubImage(1, 1, 1, 1);

        assertNotNull(subImage);
        verify(incomeImage, times(1)).getSubImage(eq(1), eq(1), eq(1), eq(1));
        assertThat(subImage.getWidth(), is(1));
        assertThat(subImage.getHeight(), is(1));
    }

    @Test
    public void getImageFileName() throws Exception {
        assertThat(settings.getImageFileName(), is("image-file-name.jpg"));
    }
}