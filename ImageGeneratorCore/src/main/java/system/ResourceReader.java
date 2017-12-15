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
    public Resource read(String path) {
        return new Resource(path);
    }

    public class Resource {

        private Stream<Path> paths;

        private Resource(String pathToDir) {
            this.paths = ((UncheckedFunction<String, Stream<Path>>) uncheckedPath -> list(get(full(uncheckedPath)))).apply(pathToDir);
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
            return Optional.ofNullable(paths)
                    .orElseThrow(RuntimeException::new)//TODO: add description
                    .filter(Files::isRegularFile)
                    .map(uncheckedFunction);
        }

    }

    private interface UncheckedFunction<T, R> extends Function<T, R> {

        @Override
        default R apply(T t) {
            try {
                return uncheckedApply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        R uncheckedApply(T t) throws Exception;
    }
}