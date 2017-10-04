package utility.core;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import utility.system.ObjectTypeConverter;
import utility.system.ResourceReader;

import java.awt.*;

import static java.awt.Color.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class InformationalImageTest {

    private InformationalImage image;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        this.image = new ObjectTypeConverter().informationalImage(new ResourceReader().readFile("images/testable/4x4.jpg")); // 32 x 32 (px)
    }

    @Test
    public void allowedWidth() throws Exception {
        assertTrue(image.widthLessThan(33));
        assertTrue(image.widthMoreThan(0));
    }

    @Test
    public void allowedHeight() throws Exception {
        assertTrue(image.heightLessThan(33));
        assertTrue(image.heightMoreThan(0));
    }

    @Test
    public void inputStreamFromInformationalImage() throws Exception {
        assertNotNull(image.asStream());
    }

    @Test
    public void inputStreamFromInformationalImageException() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("formatName == null!");
        assertNotNull(image.asStream(null));
    }

    @Test
    public void whiteAveragedColor() throws Exception {
        this.image = new ObjectTypeConverter().informationalImage(new ResourceReader().readFile("images/testable/1-white.jpg"));
        Color white = image.averagedColor();
        assertEquals(Color.WHITE, white);
    }

    @Test
    public void grayAveragedColor() throws Exception {
        this.image = new ObjectTypeConverter().informationalImage(new ResourceReader().readFile("images/testable/2-gray.jpg"));
        Color gray = image.averagedColor();
        assertEquals(Color.GRAY, gray);
    }

    @Test
    public void blackAveragedColor() throws Exception {
        this.image = new ObjectTypeConverter().informationalImage(new ResourceReader().readFile("images/testable/3-black.jpg"));
        Color black = image.averagedColor();
        assertEquals(Color.BLACK, black);
    }

    @Test
    public void getSubImage() throws Exception {
        this.image = new ObjectTypeConverter().informationalImage(new ResourceReader().readFile("images/testable/4x4.jpg"));

        assertThat(image.getSubImage(0, 0, 8, 8).averagedColor().almostIdentical(BLACK), is(true));
        assertThat(image.getSubImage(8, 0, 8, 8).averagedColor().almostIdentical(WHITE), is(true));
        assertThat(image.getSubImage(0, 8, 8, 8).averagedColor().almostIdentical(WHITE), is(true));
        assertThat(image.getSubImage(8, 8, 8, 8).averagedColor().almostIdentical(BLUE), is(true));

        assertThat(image.getSubImage(16, 0, 8, 8).averagedColor().almostIdentical(GREEN), is(true));
        assertThat(image.getSubImage(24, 0, 8, 8).averagedColor().almostIdentical(WHITE), is(true));
        assertThat(image.getSubImage(16, 8, 8, 8).averagedColor().almostIdentical(WHITE), is(true));
        assertThat(image.getSubImage(24, 8, 8, 8).averagedColor().almostIdentical(RED), is(true));

        assertThat(image.getSubImage(0, 16, 8, 8).averagedColor().almostIdentical(RED), is(true));
        assertThat(image.getSubImage(8, 16, 8, 8).averagedColor().almostIdentical(WHITE), is(true));
        assertThat(image.getSubImage(0, 24, 8, 8).averagedColor().almostIdentical(WHITE), is(true));
        assertThat(image.getSubImage(8, 24, 8, 8).averagedColor().almostIdentical(GREEN), is(true));

        assertThat(image.getSubImage(16, 16, 8, 8).averagedColor().almostIdentical(BLUE), is(true));
        assertThat(image.getSubImage(24, 16, 8, 8).averagedColor().almostIdentical(WHITE), is(true));
        assertThat(image.getSubImage(16, 24, 8, 8).averagedColor().almostIdentical(WHITE), is(true));
        assertThat(image.getSubImage(24, 24, 8, 8).averagedColor().almostIdentical(BLACK), is(true));

        assertThat(image.getSubImage(24, 24, 8, 8).averagedColor().almostIdentical(WHITE), is(false));
    }

}