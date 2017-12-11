package domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import system.ResourceReader;

import java.io.File;

import static domain.InformationalColor.*;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

public class InformationalImageTest {

    private InformationalImage image;
    private File fileImage;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        this.fileImage = new ResourceReader().readFile("images/testable/4x4.jpg");
        this.image     = InformationalImage.madeOf(fileImage); // 32 x 32 (px)
    }

    @Test
    public void informationalImageMadeOfBytes() throws Exception {
        assertThat(InformationalImage.madeOf(readAllBytes(get(fileImage.getAbsolutePath()))), notNullValue());
    }

    @Test
    public void informationalImageMadeOfBytesException() {
        thrown.expect(IllegalArgumentException.class);
        InformationalImage.madeOf((byte[]) null);
        fail();
    }

    @Test
    public void informationalImageMadeOfFile() {
        assertThat(InformationalImage.madeOf(fileImage), notNullValue());
    }

    @Test
    public void informationalImageMadeOfFileException() {
        thrown.expect(IllegalArgumentException.class);
        InformationalImage.madeOf((File) null);
        fail();
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
        assertNotNull(image.asBytes());
    }

    @Test
    public void informationalImageUncheckedAsBytes() throws Exception {
        assertNotNull(image.uncheckedAsBytes());
    }

    @Test
    public void informationalImageUncheckedAsBytesException() throws Exception {
        String message              = "Message made of UNCHECKED exception in uncheckedAsBytes method!";
        InformationalImage spyImage = spy(image);
        doThrow(new IllegalArgumentException(message)).when(spyImage).uncheckedAsBytes();
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(message);

        spyImage.asBytes();

        fail();
    }

    @Test
    public void whiteAveragedColor() {
        this.image = InformationalImage.madeOf(new ResourceReader().readFile("images/testable/1-white.jpg"));
        InformationalColor white = image.averagedColor();
        assertEquals(WHITE, white);
    }

    @Test
    public void grayAveragedColor() {
        this.image = InformationalImage.madeOf(new ResourceReader().readFile("images/testable/2-gray.jpg"));
        InformationalColor gray = image.averagedColor();
        assertEquals(GRAY, gray);
    }

    @Test
    public void blackAveragedColor() {
        this.image = InformationalImage.madeOf(new ResourceReader().readFile("images/testable/3-black.jpg"));
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
    public void resizeTo() throws Exception {
        int width  = 250;
        int height = 250;
        InformationalImage resized = image.resizeTo(width, height);

        assertThat(resized.getWidth(),  equalTo(width));
        assertThat(resized.getHeight(), equalTo(height));
    }

    @Test
    public void informationalImageUncheckedResizeTo() throws Exception {
        assertNotNull(image.uncheckedResizeTo(100, 100));
    }

    @Test
    public void informationalImageUncheckedResizeToException() throws Exception {
        String message = "Message made of UNCHECKED exception in uncheckedResizeTo method!";
        int newWidth   = 100;
        int newHeight  = 100;
        InformationalImage spyImage = spy(image);
        doThrow(new IllegalArgumentException(message)).when(spyImage).uncheckedResizeTo(newWidth, newHeight);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(message);

        spyImage.resizeTo(newWidth, newHeight);

        fail();
    }

}