package system;

import org.junit.Before;
import org.junit.Test;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ResourceReaderTest {

    private ResourceReader resourceReader;

    @Before
    public void setUp() {
        this.resourceReader = new ResourceReader();
    }

    @Test
    public void readCommonsImageByteArrays() {
        String path = "images/colors";
        long patternsCount = 24;

        Stream<byte[]> commons = resourceReader.readAll(path).asByteArrays();

        assertThat(commons, notNullValue());
        assertThat(commons.count(), equalTo(patternsCount));
    }

    @Test
    public void readFlagsImageByteArrays() {
        String path = "images/flags";
        long patternsCount = 196;

        Stream<byte[]> flags = resourceReader.readAll(path).asByteArrays();

        assertThat(flags, notNullValue());
        assertThat(flags.count(), equalTo(patternsCount));
    }

    @Test
    public void readPlainsImageByteArrays() {
        String path = "images/plains";
        long patternsCount = 3;

        Stream<byte[]> plains = resourceReader.readAll(path).asByteArrays();

        assertThat(plains, notNullValue());
        assertThat(plains.count(), equalTo(patternsCount));
    }
}