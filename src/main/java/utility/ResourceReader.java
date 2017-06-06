package utility;

import java.io.File;

public class ResourceReader {

    public File readFile(String resourceName) {
        return new File(getClass().getClassLoader().getResource("").getPath() + resourceName);
    }

}
