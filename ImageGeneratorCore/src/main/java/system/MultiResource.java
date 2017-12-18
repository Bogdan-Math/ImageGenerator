package system;

import utility.UncheckedFunction;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static java.nio.file.Files.list;
import static java.nio.file.Paths.get;
import static java.util.Objects.requireNonNull;

//TODO: add separated test
public class MultiResource {

    private Stream<Path> pathsToFiles;

    MultiResource(String checkedPathToDir) {
        UncheckedFunction<String, Stream<Path>> toFullPaths = path -> list(get(full(path)));
        this.pathsToFiles = toFullPaths.apply(checkedPathToDir);
    }

    public MultiResource take(String... fileNames) {
        this.pathsToFiles = pathsToFiles
                .filter(path -> Stream.of(fileNames)
                        .anyMatch(name -> path.getFileName().toString().equals(name)));
        return this;
    }

    public Stream<byte[]> asByteArrays() {
        UncheckedFunction<Path, byte[]> toByteArrays = Files::readAllBytes;
        return convert(toByteArrays);
    }

    private <R> Stream<R> convert(UncheckedFunction<Path, R> uncheckedFunction) {
        return pathsToFiles.filter(Files::isRegularFile).map(uncheckedFunction);
    }

    private URI full(String uncheckedPath) throws URISyntaxException {
        return requireNonNull(getClass().getClassLoader().getResource(uncheckedPath)).toURI();
    }

}