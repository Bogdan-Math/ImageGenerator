import java.io.File;

public class Resource {

    private File resource;

    public Resource(File resource) {
        this.resource = resource;
    }

    public boolean isFolder() {
        return resource.isDirectory();
    }

    public boolean isFile() {
        return resource.isFile();
    }
}