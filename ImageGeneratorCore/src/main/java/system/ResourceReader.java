package system;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.nio.file.Files.list;
import static java.nio.file.Paths.get;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

public class ResourceReader {

    //TODO: add test to all brand new functionality
    public SingleResource readSingle(String pathToFile) {
        return new SingleResource(ofNullable(pathToFile)
                .orElseThrow(() -> new RuntimeException("Path to file COULD NOT be null !!!")));
    }

    public class SingleResource {

        private Path pathToFile;

        private SingleResource(String checkedPathToFile) {
            UncheckedFunction<String, Path> toFullPath = path -> get(full(path));
            this.pathToFile = toFullPath.apply(checkedPathToFile);
        }

        public File asFile() {
            UncheckedFunction<Path, File> toFile = Path::toFile;
            return convert(toFile);
        }

        public byte[] asByteArray() {
            UncheckedFunction<Path, byte[]> toByteArray = Files::readAllBytes;
            return convert(toByteArray);
        }

        private <R> R convert(UncheckedFunction<Path, R> uncheckedFunction) {
            return Stream.of(pathToFile)
                         .map(uncheckedFunction)
                         .findFirst()
                         .orElseThrow(() -> new RuntimeException("There are NO object here !!!"));
        }
    }

    //TODO: add test to all brand new functionality
    public MultiResource readAll(String pathToDir) {
        return new MultiResource(ofNullable(pathToDir)
                .orElseThrow(() -> new RuntimeException("Path to directory COULD NOT be null !!!")));
    }

    public class MultiResource {

        private Stream<Path> pathsToFiles;

        private MultiResource(String checkedPathToDir) {
            UncheckedFunction<String, Stream<Path>> toFullPaths = path -> list(get(full(path)));
            this.pathsToFiles = toFullPaths.apply(checkedPathToDir);
        }

        public Stream<File> asFiles() {
            UncheckedFunction<Path, File> toFiles = Path::toFile;
            return convert(toFiles);
        }

        public Stream<byte[]> asByteArrays() {
            UncheckedFunction<Path, byte[]> toByteArrays = Files::readAllBytes;
            return convert(toByteArrays);
        }

        private <R> Stream<R> convert(UncheckedFunction<Path, R> uncheckedFunction) {
            return pathsToFiles.filter(Files::isRegularFile)
                               .map(uncheckedFunction);
        }

    }

    private URI full(String uncheckedPath) throws URISyntaxException {
        return requireNonNull(getClass().getClassLoader()
                                        .getResource(uncheckedPath)).toURI();
    }

    @FunctionalInterface
    private interface UncheckedFunction<T, R> extends Function<T, R> {

        R uncheckedApply(T t) throws Throwable;

        @Override
        default R apply(T t) {
            try {
                return uncheckedApply(t);
            } catch (Throwable throwable) {
                throw new UncheckedFunctionException(throwable);
            }
        }

        class UncheckedFunctionException extends RuntimeException {
            UncheckedFunctionException(Throwable throwable) {
                super(throwable);
            }
        }
    }
}