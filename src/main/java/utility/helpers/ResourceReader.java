package utility.helpers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class ResourceReader {

    public List<File> readFiles(String path) {
        return Arrays.stream(Optional.ofNullable(readFile(path).listFiles())
                     .orElseThrow(() -> new RuntimeException("Directory \'" + path + "\': is not exist or empty.")))
                     .filter(File::isFile)
                     .collect(Collectors.toList());
    }

    public File readFile(String resourceName) {
        return new File(getClass().getClassLoader()
                                            .getResource("")
                                            .getPath() + resourceName);
    }

}