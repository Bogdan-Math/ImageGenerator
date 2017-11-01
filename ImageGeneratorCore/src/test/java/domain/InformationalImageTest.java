package domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import system.ObjectTypeConverter;
import system.ResourceReader;

import static domain.InformationalColor.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

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
        String message              = "Message from UNCHECKED exception!";
        InformationalImage spyImage = spy(image);
        doThrow(new IllegalArgumentException(message)).when(spyImage).asUncheckedStream();
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(message);

        spyImage.asStream();

        fail();
    }

    @Test
    public void whiteAveragedColor() throws Exception {
        this.image = new ObjectTypeConverter().informationalImage(new ResourceReader().readFile("images/testable/1-white.jpg"));
        InformationalColor white = image.averagedColor();
        assertEquals(WHITE, white);
    }

    @Test
    public void grayAveragedColor() throws Exception {
        this.image = new ObjectTypeConverter().informationalImage(new ResourceReader().readFile("images/testable/2-gray.jpg"));
        InformationalColor gray = image.averagedColor();
        assertEquals(GRAY, gray);
    }

    @Test
    public void blackAveragedColor() throws Exception {
        this.image = new ObjectTypeConverter().informationalImage(new ResourceReader().readFile("images/testable/3-black.jpg"));
        InformationalColor black = image.averagedColor();
        assertEquals(BLACK, black);
    }

    @Test
    public void getSubImage() throws Exception {
        this.image = new ObjectTypeConverter().informationalImage(new ResourceReader().readFile("images/testable/4x4.jpg"));

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

}