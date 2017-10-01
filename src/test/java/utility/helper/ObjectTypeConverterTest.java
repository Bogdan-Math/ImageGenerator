package utility.helper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import utility.pattern.InformationalImage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static javax.imageio.ImageIO.read;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ObjectTypeConverterTest {

    private ObjectTypeConverter converter;
    private File originalImage;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        this.converter      = new ObjectTypeConverter();
        this.originalImage  = new ResourceReader().readFile("images/testable/4x4.jpg");
    }

    @Test
    public void inputStreamFromInformationalImage() throws Exception {
        assertNotNull(converter.inputStream(new InformationalImage(read(originalImage))));
    }

    @Test
    public void inputStreamFromInformationalImageException() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        assertNotNull(converter.inputStream((InformationalImage) null));
    }

    @Test
    public void informationalImageFromBytes() throws Exception {
        assertNotNull(converter.informationalImage(Files.readAllBytes(Paths.get(originalImage.getAbsolutePath()))));
    }

    @Test
    public void informationalImageFromBytesException() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        assertNotNull(converter.informationalImage((byte[]) null));
    }

    @Test
    public void informationalImageFromFile() throws Exception {
        assertNotNull(converter.informationalImage(originalImage));
    }

    @Test
    public void informationalImageFromFileException() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        assertNotNull(converter.informationalImage((File) null));
    }
}