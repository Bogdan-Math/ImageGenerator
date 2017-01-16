import java.io.File;

public class Resource {

    private File resource;

    public Resource(File resource) {
        this.resource = resource;
    }

    public String getFullName() {
        return resource.getName();
    }

    public String getExtension() {
        String fullName = resource.getName();
        return fullName.substring(1 + fullName.lastIndexOf("."));
    }

    public String getName() {
        String fullName = resource.getName();
        return fullName.substring(0, fullName.lastIndexOf("."));
    }
}