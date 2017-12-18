package system;

import static java.util.Optional.ofNullable;

public class ResourceReader {

    //TODO: add test to all brand new functionality
    public SingleResource readSingle(String pathToFile) {
        return new SingleResource(ofNullable(pathToFile)
                .orElseThrow(() -> new RuntimeException("Path to file COULD NOT be null !!!")));
    }

    //TODO: add test to all brand new functionality
    public MultiResource readAll(String pathToDir) {
        return new MultiResource(ofNullable(pathToDir)
                .orElseThrow(() -> new RuntimeException("Path to directory COULD NOT be null !!!")));
    }
}