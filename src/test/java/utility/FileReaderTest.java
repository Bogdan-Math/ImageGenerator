package utility;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileReaderTest {

    private FileReader fileReader;

    @Before
    public void setUp() throws Exception {
        this.fileReader = new FileReader();
    }

    @Test
    public void read() throws Exception {
        assertTrue(fileReader.getFileObject("images/canonical.jpg").exists());
        assertTrue(fileReader.getFileObject("images/canonical.jpg").isFile());
        assertFalse(fileReader.getFileObject("images/canonical.jpg").isDirectory());
    }

}