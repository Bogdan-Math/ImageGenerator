package utility.helpers;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ResourceReader {

    public List<File> readFiles(String path) {
        return Arrays.stream(Optional.ofNullable(readFile(path).listFiles())
                     .orElseThrow(() -> new RuntimeException("Directory \'" + path + "\': is not exist or empty.")))
                     .filter(File::isFile)
                     .collect(Collectors.toList());
    }

    @SuppressWarnings("ConstantConditions")
    public File readFile(String resourceName) {
        return new File(getClass().getClassLoader()
                                  .getResource(resourceName)
                                  .getFile());
    }

}