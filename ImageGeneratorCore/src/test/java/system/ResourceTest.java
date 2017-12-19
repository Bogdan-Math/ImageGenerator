package system;

import org.junit.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ResourceTest {

    private Resource resource;

    @Test
    public void readCommonsImageByteArrays() throws Exception {
        this.resource = new Resource(get("images/colors"));
        long patternsCount = 24;

        Stream<byte[]> commons = resource.asByteArrays();

        assertThat(commons, notNullValue());
        assertThat(commons.count(), equalTo(patternsCount));
    }

    @Test
    public void readFlagsImageByteArrays() throws Exception {
        this.resource = new Resource(get("images/flags"));
        long patternsCount = 196;

        Stream<byte[]> flags = resource.asByteArrays();

        assertThat(flags, notNullValue());
        assertThat(flags.count(), equalTo(patternsCount));
    }

    @Test
    public void readPlainsImageByteArrays() throws Exception {
        this.resource = new Resource(get("images/plains"));
        long patternsCount = 3;

        Stream<byte[]> plains = resource.asByteArrays();

        assertThat(plains, notNullValue());
        assertThat(plains.count(), equalTo(patternsCount));
    }

    private Path get(String resourceName) throws URISyntaxException {
        return Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(resourceName)).toURI());
    }
}