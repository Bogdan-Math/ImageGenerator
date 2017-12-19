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
        String resourceDir = "images/colors";
        long patternsCount = 24;

        Stream<byte[]> commons = resourceReader.readAllIn(resourceDir).asByteArrays();

        assertThat(commons, notNullValue());
        assertThat(commons.count(), equalTo(patternsCount));
    }

    @Test
    public void readFlagsImageByteArrays() {
        String resourceDir = "images/flags";
        long patternsCount = 196;

        Stream<byte[]> flags = resourceReader.readAllIn(resourceDir).asByteArrays();

        assertThat(flags, notNullValue());
        assertThat(flags.count(), equalTo(patternsCount));
    }

    @Test
    public void readPlainsImageByteArrays() {
        String resourceDir = "images/plains";
        long patternsCount = 3;

        Stream<byte[]> plains = resourceReader.readAllIn(resourceDir).asByteArrays();

        assertThat(plains, notNullValue());
        assertThat(plains.count(), equalTo(patternsCount));
    }
}