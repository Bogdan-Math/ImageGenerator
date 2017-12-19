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

    MultiResource(String pathToDir) {
        UncheckedFunction<String, Stream<Path>> toFullPaths = path -> list(get(full(path)));
        this.pathsToFiles = toFullPaths.apply(pathToDir);
    }

    public Stream<byte[]> asByteArrays() {
        UncheckedFunction<Path, byte[]> toByteArray = Files::readAllBytes;
        return pathsToFiles.filter(Files::isRegularFile).map(toByteArray);
    }

    private URI full(String uncheckedPath) throws URISyntaxException {
        return requireNonNull(getClass().getClassLoader().getResource(uncheckedPath)).toURI();
    }

}