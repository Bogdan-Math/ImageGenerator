package system;

import utility.UncheckedFunction;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

public class ResourceReader {

    //TODO: add test to all brand new functionality
    public Resource readAllIn(String resourceDir) {

        String checkedResourceDir = ofNullable(resourceDir)
                .orElseThrow(() -> new RuntimeException("Path to directory COULD NOT be null !!!"));

        UncheckedFunction<String, URI> toURI = this::uri;
        Path checkedPath = Stream.of(toURI.apply(checkedResourceDir))
                .map(Paths::get)
                .filter(Files::isDirectory)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Path MUST BE directory !!!"));

        return new Resource(checkedPath);
    }

    private URI uri(String resourceName) throws URISyntaxException {
        return requireNonNull(getClass().getClassLoader().getResource(resourceName)).toURI();
    }

}