package domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.InputStream;

import static domain.InformationalColor.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class InformationalImageTest {

    private InformationalImage image;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        this.image = InformationalImage.madeOf(streamOf("images/testable/4x4.jpg")); // 32 x 32 (px)
    }

    @Test
    public void informationalImageMadeOfByteArray() {
        assertThat(InformationalImage.madeOf(image.asByteArray()), notNullValue());
    }

    @Test
    public void informationalImageMadeOfInputStream() {
        assertThat(InformationalImage.madeOf(image.asStream()), notNullValue());
    }

    @Test
    public void allowedWidth() {
        assertTrue(image.widthLessThan(33));
        assertTrue(image.widthMoreThan(0));
    }

    @Test
    public void allowedHeight() {
        assertTrue(image.heightLessThan(33));
        assertTrue(image.heightMoreThan(0));
    }

    @Test
    public void informationalImageAsStream() {
        assertNotNull(image.asStream());
    }

    @Test
    public void informationalImageAsBytes() {
        assertNotNull(image.asByteArray());
    }

    @Test
    public void whiteAveragedColor() {
        this.image = InformationalImage.madeOf(streamOf("images/testable/1-white.jpg"));
        InformationalColor white = image.averagedColor();
        assertEquals(WHITE, white);
    }

    @Test
    public void grayAveragedColor() {
        this.image = InformationalImage.madeOf(streamOf("images/testable/2-gray.jpg"));
        InformationalColor gray = image.averagedColor();
        assertEquals(GRAY, gray);
    }

    @Test
    public void blackAveragedColor() {
        this.image = InformationalImage.madeOf(streamOf("images/testable/3-black.jpg"));
        InformationalColor black = image.averagedColor();
        assertEquals(BLACK, black);
    }

    @Test
    public void getSubImage() {

        assertThat(image.getSubImage(0, 0, 8, 8).averagedColor().almostEqualTo(BLACK), is(true));
        assertThat(image.getSubImage(8, 0, 8, 8).averagedColor().almostEqualTo(WHITE), is(true));
        assertThat(image.getSubImage(0, 8, 8, 8).averagedColor().almostEqualTo(WHITE), is(true));
        assertThat(image.getSubImage(8, 8, 8, 8).averagedColor().almostEqualTo(BLUE), is(true));

        assertThat(image.getSubImage(16, 0, 8, 8).averagedColor().almostEqualTo(GREEN), is(true));
        assertThat(image.getSubImage(24, 0, 8, 8).averagedColor().almostEqualTo(WHITE), is(true));
        assertThat(image.getSubImage(16, 8, 8, 8).averagedColor().almostEqualTo(WHITE), is(true));
        assertThat(image.getSubImage(24, 8, 8, 8).averagedColor().almostEqualTo(RED), is(true));

        assertThat(image.getSubImage(0, 16, 8, 8).averagedColor().almostEqualTo(RED), is(true));
        assertThat(image.getSubImage(8, 16, 8, 8).averagedColor().almostEqualTo(WHITE), is(true));
        assertThat(image.getSubImage(0, 24, 8, 8).averagedColor().almostEqualTo(WHITE), is(true));
        assertThat(image.getSubImage(8, 24, 8, 8).averagedColor().almostEqualTo(GREEN), is(true));

        assertThat(image.getSubImage(16, 16, 8, 8).averagedColor().almostEqualTo(BLUE), is(true));
        assertThat(image.getSubImage(24, 16, 8, 8).averagedColor().almostEqualTo(WHITE), is(true));
        assertThat(image.getSubImage(16, 24, 8, 8).averagedColor().almostEqualTo(WHITE), is(true));
        assertThat(image.getSubImage(24, 24, 8, 8).averagedColor().almostEqualTo(BLACK), is(true));

        assertThat(image.getSubImage(24, 24, 8, 8).averagedColor().almostEqualTo(WHITE), is(false));
    }

    @Test
    public void resizeTo() {
        int width  = 250;
        int height = 250;
        InformationalImage resized = image.resizeTo(width, height);

        assertThat(resized.getWidth(),  equalTo(width));
        assertThat(resized.getHeight(), equalTo(height));
    }

    private InputStream streamOf(String pathToImage) {
        return getClass().getClassLoader().getResourceAsStream(pathToImage);
    }
}