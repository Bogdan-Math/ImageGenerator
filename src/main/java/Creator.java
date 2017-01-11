public class Creator {

    private String name;

    public Creator(String name) {
        this.name = name;
    }

    public String getDescribtion() {
        return String.format("Hello, I'm %s - creator of ImageGenerator!", name);
    }

}
