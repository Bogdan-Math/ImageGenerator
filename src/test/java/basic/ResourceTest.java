package basic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utility.FileReader;

import static org.junit.Assert.*;

public class ResourceTest {

    private FileReader fileReader;

    private Resource resource;

    @Before
    public void setUp() throws Exception {
        this.fileReader = new FileReader();
        this.resource  = new Resource(fileReader.read("images/canonical.jpg"));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getFullName() {
        assertEquals("canonical.jpg", resource.getNameWithExtension());
    }

    @Test
    public void getOnlyName() {
        assertEquals("canonical", resource.getOnlyName());
    }

    @Test
    public void getOnlyExtension() throws Exception {
        assertEquals("jpg", resource.getOnlyExtension());
    }

}