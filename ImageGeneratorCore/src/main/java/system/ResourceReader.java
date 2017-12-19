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
    public MultiResource readAllIn(String resourceDir) {

        String checkedPathToDir = ofNullable(resourceDir)
                .orElseThrow(() -> new RuntimeException("Path to directory COULD NOT be null !!!"));

        UncheckedFunction<String, URI> toURI = this::uri;
        Path checkedPath = Stream.of(toURI.apply(checkedPathToDir))
                .map(Paths::get)
                .filter(Files::isDirectory)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Path to directory COULD NOT be null !!!"));

        return new MultiResource(checkedPath);
    }

    private URI uri(String uncheckedPath) throws URISyntaxException {
        return requireNonNull(getClass().getClassLoader().getResource(uncheckedPath)).toURI();
    }

}