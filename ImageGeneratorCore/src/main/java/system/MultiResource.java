package system;

import utility.UncheckedFunction;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

//TODO: add separated test
public class MultiResource {

    private Stream<Path> pathsToFiles;

    MultiResource(Path pathToDir) {
        UncheckedFunction<Path, Stream<Path>> toFullPaths = Files::list;
        this.pathsToFiles = toFullPaths.apply(pathToDir);
    }

    public Stream<byte[]> asByteArrays() {
        UncheckedFunction<Path, byte[]> toByteArray = Files::readAllBytes;
        return pathsToFiles.filter(Files::isRegularFile).map(toByteArray);
    }
}