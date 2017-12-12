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
    public void setUp() {
        this.resourceReader = new ResourceReader();
    }

    @Test
    public void readCommonsImageFiles() {
        String path = "images/colors";
        int patternsCount = 24;

        List<File> fileList = resourceReader.readFiles(path);

        assertThat(fileList, notNullValue());
        assertThat(fileList.size(), equalTo(patternsCount));
    }

    @Test
    public void readFlagsImageFiles() {
        String path = "images/flags";
        int patternsCount = 196;

        List<File> fileList = resourceReader.readFiles(path);

        assertThat(fileList, notNullValue());
        assertThat(fileList.size(), equalTo(patternsCount));
    }

    @Test
    public void readPlainsImageFiles() {
        String path = "images/plains";
        int patternsCount = 3;

        List<File> fileList = resourceReader.readFiles(path);

        assertThat(fileList, notNullValue());
        assertThat(fileList.size(), equalTo(patternsCount));
    }

    @Test
    public void readFile() {
        File file = resourceReader.readFile("images/testable/4x4.jpg");

        assertTrue(file.exists());
        assertTrue(file.isFile());
        assertFalse(file.isDirectory());
    }
}