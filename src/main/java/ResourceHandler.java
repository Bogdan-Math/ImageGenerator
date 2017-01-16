import java.io.File;

public class ResourceHandler {

    private String path;
    private File resource;

    public ResourceHandler(String path) {
        this.path = path;
        this.resource = new File(path);
    }

    public boolean exists() {
        return resource.exists();
    }

    public boolean isFolder() {
        return resource.isDirectory();
    }

    public boolean isFile() {
        return resource.isFile();
    }
}