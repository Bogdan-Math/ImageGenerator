package utility;

import java.io.File;

public class FileReader {

    public File getFileObject(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("").getPath() + resourceName);
    }

}
