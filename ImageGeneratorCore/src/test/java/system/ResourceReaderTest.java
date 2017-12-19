package system;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class ResourceReaderTest {

    private ResourceReader resourceReader;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        this.resourceReader = new ResourceReader();
    }

    @Test
    public void readAllCorrect() {
        String resourceDir = "images";

        Resource resource = resourceReader.readAllIn(resourceDir);

        assertThat(resource, notNullValue());
    }

    @Test
    public void readAllWitNullResourceDir() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Path to directory COULD NOT be null !!!");

        resourceReader.readAllIn(null);

        fail();
    }

    @Test
    public void readAllWithNoDir() {
        String resourceFile = "images/testable/4x4.jpg";
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Path MUST BE directory !!!");

        resourceReader.readAllIn(resourceFile);

        fail();
    }
}