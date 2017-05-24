package basic;

import java.io.File;

public class Resource {

    private String fullName;

    public Resource(File resource) {
        this.fullName = resource.getName();
    }

    public Resource(String fullName) {
        this.fullName = fullName;
    }

    public String getOnlyName() {
        return fullName.substring(0, fullName.lastIndexOf("."));
    }

    public String getOnlyExtension() {
        return fullName.substring(1 + fullName.lastIndexOf("."));
    }
}