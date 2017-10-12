package utility.system;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class ObjectTypeConverterTest {

    private ObjectTypeConverter converter;
    private File originalImage;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        this.converter     = new ObjectTypeConverter();
        this.originalImage = new ResourceReader().readFile("images/testable/4x4.jpg");
    }

    @Test
    public void informationalImageFromBytes() throws Exception {
        assertNotNull(converter.informationalImage(readAllBytes(get(originalImage.getAbsolutePath()))));
    }

    @Test
    public void informationalImageFromBytesException() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        converter.informationalImage((byte[]) null);
        fail();
    }

    @Test
    public void informationalImageFromFile() throws Exception {
        assertNotNull(converter.informationalImage(originalImage));
    }

    @Test
    public void informationalImageFromFileException() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        converter.informationalImage((File) null);
        fail();
    }
}