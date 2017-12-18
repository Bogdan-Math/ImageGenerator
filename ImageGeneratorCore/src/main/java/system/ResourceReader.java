package system;

import static java.nio.file.Files.isDirectory;
import static java.nio.file.Paths.get;
import static java.util.Optional.ofNullable;

public class ResourceReader {

    //TODO: add test to all brand new functionality
    public MultiResource readAll(String pathToDir) {
        return new MultiResource(ofNullable(pathToDir)
                .filter(path -> isDirectory(get(path)))
                .orElseThrow(() -> new RuntimeException("Path to directory COULD NOT be null !!!")));
    }
}