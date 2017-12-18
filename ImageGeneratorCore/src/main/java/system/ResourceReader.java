package system;

import static java.util.Optional.ofNullable;

public class ResourceReader {

    //TODO: add test to all brand new functionality
    //TODO: rename to readAllIn
    public MultiResource readAll(String pathToDir) {
        return new MultiResource(ofNullable(pathToDir)
                //TODO: add if DIRECTORY check
                .orElseThrow(() -> new RuntimeException("Path to directory COULD NOT be null !!!")));
    }
}