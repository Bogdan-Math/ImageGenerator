package utility.core;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import utility.system.ObjectTypeConverter;
import utility.system.ResourceReader;

import static org.junit.Assert.assertNotNull;

//TODO: add tests
public class InformationalImageTest {

    private InformationalImage image;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        this.image = new ObjectTypeConverter().informationalImage(new ResourceReader().readFile("images/testable/4x4.jpg"));
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

}