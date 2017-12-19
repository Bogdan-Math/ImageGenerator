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

    private Settings settings              = new BasicSettings();
    private InformationalImage incomeImage = mock(InformationalImage.class);

    @Before
    public void setUp() {

        //set fields
        settings.setIncomeImage(incomeImage);
        settings.setPatterns(new HashMap<>());
        settings.setExpectedColumnsCount(MAX_EXPECTED_COLUMNS_COUNT / 2);
        settings.setIncomeImageName("image-file-name.jpg");

        //mock actions
        when(incomeImage.getWidth()).thenReturn(100);
        when(incomeImage.getHeight()).thenReturn(200);
        when(incomeImage.getSubImage(anyInt(), anyInt(), anyInt(), anyInt()))
                .thenReturn(new InformationalImage(1, 1, InformationalImage.TYPE_INT_RGB));
    }

    @Test
    public void getImage() {
        assertNotNull(settings.getIncomeImage());
    }

    @Test
    public void getPatterns() {
        assertNotNull(settings.getPatterns());
    }

    @Test
    public void getExpectedColumnsCount() {
        assertThat(settings.getExpectedColumnsCount(), is(MAX_EXPECTED_COLUMNS_COUNT / 2));
    }

    @Test
    public void getImageWidth() {
        assertThat(settings.getImageWidth(), is(100));
        verify(incomeImage, times(1)).getWidth();
    }

    @Test
    public void getImageHeight() {
        assertThat(settings.getImageHeight(), is(200));
        verify(incomeImage, times(1)).getHeight();
    }

    @Test
    public void getSubImage() {
        InformationalImage subImage = settings.getSubImage(1, 1, 1, 1);

        assertNotNull(subImage);
        verify(incomeImage, times(1)).getSubImage(eq(1), eq(1), eq(1), eq(1));
        assertThat(subImage.getWidth(), is(1));
        assertThat(subImage.getHeight(), is(1));
    }

    @Test
    public void getImageFileName() {
        assertThat(settings.getIncomeImageName(), is("image-file-name.jpg"));
    }
}