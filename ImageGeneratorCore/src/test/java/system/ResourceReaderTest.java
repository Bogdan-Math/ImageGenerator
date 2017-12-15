package system;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.stream.Stream;

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
        long patternsCount = 24;

        Stream<File> files = resourceReader.readAll(path).asFiles();

        assertThat(files, notNullValue());
        assertThat(files.count(), equalTo(patternsCount));
    }

    @Test
    public void readFlagsImageFiles() {
        String path = "images/flags";
        long patternsCount = 196;

        Stream<File> files = resourceReader.readAll(path).asFiles();

        assertThat(files, notNullValue());
        assertThat(files.count(), equalTo(patternsCount));
    }

    @Test
    public void readPlainsImageFiles() {
        String path = "images/plains";
        long patternsCount = 3;

        Stream<File> files = resourceReader.readAll(path).asFiles();

        assertThat(files, notNullValue());
        assertThat(files.count(), equalTo(patternsCount));
    }

    @Test
    public void readFile() {
        File file = resourceReader.readSingle("images/testable/4x4.jpg").asFile();

        assertTrue(file.exists());
        assertTrue(file.isFile());
        assertFalse(file.isDirectory());
    }
}