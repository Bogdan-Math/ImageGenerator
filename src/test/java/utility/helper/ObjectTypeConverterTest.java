package utility.helper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ObjectTypeConverterTest {

    private ObjectTypeConverter converter;
    private File originalImage;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        ResourceReader resourceReader = new ResourceReader();
        this.converter      = new ObjectTypeConverter();
        this.originalImage  = resourceReader.readFile("images/testable/4x4.jpg");
    }

    @Test
    public void inputStreamFromBytes() throws Exception {
        assertTrue(converter.inputStream(new byte[1]) != null);
    }

    @Test
    public void inputStreamFromBufferedImage() throws Exception {
        assertNotNull(converter.inputStream(ImageIO.read(originalImage)));
    }

    @Test
    public void inputStreamFromBufferedImageException() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        assertNotNull(converter.inputStream((BufferedImage) null));
    }

    @Test
    public void bufferedImageFromBytes() throws Exception {
        assertNotNull(converter.bufferedImage(Files.readAllBytes(Paths.get(originalImage.getAbsolutePath()))));
    }

    @Test
    public void bufferedImageFromBytesException() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        assertNotNull(converter.bufferedImage((byte[]) null));
    }

    @Test
    public void bufferedImageFromFile() throws Exception {
        assertNotNull(converter.bufferedImage(originalImage));
    }

    @Test
    public void bufferedImageFromFileException() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        assertNotNull(converter.bufferedImage((File) null));
    }
}