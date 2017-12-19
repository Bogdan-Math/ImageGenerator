package model;

public class PatternImage {

    public String name;
    public String path;

    public byte[] inByteArray;

    public PatternImage() {
    }

    public PatternImage(byte[] inByteArray) {
        this.inByteArray = inByteArray;
    }
}
