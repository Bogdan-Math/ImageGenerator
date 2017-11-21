package system;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

public class ResourceReaderTest {

    private ResourceReader resourceReader;

    @Before
    public void setUp() throws Exception {
        this.resourceReader = new ResourceReader();
    }

    @Test
    public void readFile() throws Exception {
        assertTrue(resourceReader.readFile("images/testable/4x4.jpg").exists());
        assertTrue(resourceReader.readFile("images/testable/4x4.jpg").isFile());
        assertFalse(resourceReader.readFile("images/testable/4x4.jpg").isDirectory());
    }

    @Test
    public void readCommonsImageFiles() throws Exception {
        String path = "images/colors";
        int patternsCount = 24;

        List<File> fileList = resourceReader.readFiles(path);

        assertThat(fileList, notNullValue());
        assertThat(fileList.size(), equalTo(patternsCount));
    }

    @Test
    public void readFlagsImageFiles() throws Exception {
        String path = "images/flags";
        int patternsCount = 196;

        List<File> fileList = resourceReader.readFiles(path);

        assertThat(fileList, notNullValue());
        assertThat(fileList.size(), equalTo(patternsCount));
    }

    @Test
    public void readPlainsImageFiles() throws Exception {
        String path = "images/plains";
        int patternsCount = 3;

        List<File> fileList = resourceReader.readFiles(path);

        assertThat(fileList, notNullValue());
        assertThat(fileList.size(), equalTo(patternsCount));
    }

    @Test
    public void readURL() throws Exception {
        //TODO: add test
    }
}