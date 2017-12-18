package system;

import utility.UncheckedFunction;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static java.nio.file.Paths.get;
import static java.util.Objects.requireNonNull;

//TODO: add separated test
public class SingleResource {

    private Path pathToFile;

    SingleResource(String checkedPathToFile) {
        UncheckedFunction<String, Path> toFullPath = path -> get(full(path));
        this.pathToFile = toFullPath.apply(checkedPathToFile);
    }

    public byte[] asByteArray() {
        UncheckedFunction<Path, byte[]> toByteArray = Files::readAllBytes;
        return convert(toByteArray);
    }

    private <R> R convert(UncheckedFunction<Path, R> uncheckedFunction) {
        return Stream.of(pathToFile).map(uncheckedFunction).findFirst()
                .orElseThrow(() -> new RuntimeException("There are NO object here !!!"));
    }

    private URI full(String uncheckedPath) throws URISyntaxException {
        return requireNonNull(getClass().getClassLoader().getResource(uncheckedPath)).toURI();
    }

}
