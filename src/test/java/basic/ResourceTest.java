package basic;

import org.junit.Before;
import org.junit.Test;
import utility.ResourceReader;

import static org.junit.Assert.assertEquals;

public class ResourceTest {

    private Resource resource;

    @Before
    public void setUp() throws Exception {
        this.resource  = new Resource(new ResourceReader().readFile("images/canonical.jpg"));
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