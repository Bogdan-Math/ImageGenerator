package system;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

//TODO: read https://stackoverflow.com/questions/27644361/how-can-i-throw-checked-exceptions-from-inside-java-8-streams-not-wrapping-it
public class ResourceReader {

    private Stream<Path> paths;

    public ResourceReader read(String path) {
        try {
            this.paths = Files.list(Paths.get(path));
            return this;
        } catch (IOException e) { throw new UncheckedIOException(e); }
    }

    public Stream<File> asFiles() {
        return Optional.ofNullable(paths)
                .orElseThrow(RuntimeException::new)//TODO: add description
                .filter(Files::isRegularFile)
                .map(Path::toFile);
    }

    public Stream<byte[]> asByteArrays() {
        return Optional.ofNullable(paths)
                .orElseThrow(RuntimeException::new)//TODO: add description
                .filter(Files::isRegularFile)
                .map(path -> {
                    try {
                        return Files.readAllBytes(path);
                    } catch (IOException e) { throw new UncheckedIOException(e); }
                });
    }

    public List<File> readFiles(String path) {
        return Arrays.stream(Optional.ofNullable(readFile(path).listFiles())
                     .orElseThrow(() -> new RuntimeException("Directory \'" + path + "\': is not exists or empty.")))
                     .filter(File::isFile)
                     .collect(toList());
    }

    public File readFile(String resourceName) {
        return new File(readURL(resourceName).getFile());
    }


    private URL readURL(String resourceName) {
        return getClass().getClassLoader()
                         .getResource(resourceName);
    }
}