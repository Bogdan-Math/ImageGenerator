package system;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.nio.file.Files.list;
import static java.nio.file.Paths.get;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public class ResourceReader {

    //TODO: delete it after refactor
    public List<File> readFiles(String path) {
        return Arrays.stream(Optional.ofNullable(readFile(path).listFiles())
                .orElseThrow(() -> new RuntimeException("Directory \'" + path + "\': is not exists or empty.")))
                .filter(File::isFile)
                .collect(toList());
    }

    //TODO: delete it after refactor
    public File readFile(String resourceName) {
        return new File(fullPath(resourceName));
    }

    //TODO: delete it after refactor
    private String fullPath(String resourceName) {
        return requireNonNull(getClass().getClassLoader()
                .getResource(resourceName)).getFile();
    }

    //TODO: add test to all brand new functionality
    public Resource read(String pathToDir) {
        return new Resource(Optional.ofNullable(pathToDir)
                                    .orElseThrow(() -> new RuntimeException("Path to dir COULD NOT be null !!!")));
    }

    public class Resource {

        private Stream<Path> pathsToFiles;

        private Resource(String checkedPathToDir) {
            this.pathsToFiles = ((UncheckedFunction<String, Stream<Path>>) path -> list(get(full(path)))).apply(checkedPathToDir);
        }

        public Stream<File> asFiles() {
            UncheckedFunction<Path, File> toFile = Path::toFile;
            return convert(toFile);
        }

        public Stream<byte[]> asByteArrays() {
            UncheckedFunction<Path, byte[]> toByteArray = Files::readAllBytes;
            return convert(toByteArray);
        }

        private String full(String uncheckedPath) {
            return requireNonNull(getClass().getClassLoader()
                    .getResource(uncheckedPath)).getFile();
        }

        private <R> Stream<R> convert(UncheckedFunction<Path, R> uncheckedFunction) {
            return pathsToFiles.filter(Files::isRegularFile)
                        .map(uncheckedFunction);
        }

    }

    @FunctionalInterface
    private interface UncheckedFunction<T, R> extends Function<T, R> {

        R uncheckedApply(T t) throws Exception;

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

