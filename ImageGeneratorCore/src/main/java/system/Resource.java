package system;

import utility.UncheckedFunction;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Resource {

    private Stream<Path> pathsToFiles;

    Resource(Path pathToDir) {
        UncheckedFunction<Path, Stream<Path>> toFullPaths = Files::list;
        this.pathsToFiles = toFullPaths.apply(pathToDir);
    }

    public Stream<byte[]> asByteArrays() {
        UncheckedFunction<Path, byte[]> toByteArray = Files::readAllBytes;
        return pathsToFiles.filter(Files::isRegularFile).map(toByteArray);
    }
}