package basic;

import java.io.File;

public class Resource {

    private File resource;

    public Resource(File resource) {
        this.resource = resource;
    }

    public String getNameWithExtension() {
        return resource.getName();
    }

    public String getOnlyName() {
        String fullName = resource.getName();
        return fullName.substring(0, fullName.lastIndexOf("."));
    }

    public String getOnlyExtension() {
        String fullName = resource.getName();
        return fullName.substring(1 + fullName.lastIndexOf("."));
    }
}