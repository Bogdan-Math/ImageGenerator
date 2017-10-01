package utility.helper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertNotNull;

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