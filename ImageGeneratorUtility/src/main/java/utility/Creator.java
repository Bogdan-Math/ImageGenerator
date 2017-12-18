package utility;

public class Creator {

    private String name;

    Creator(String name) {
        this.name = name;
    }

    String getDescription() {
        return String.format("Hello, I'm %s - creator of ImageGenerator!", name);
    }

}
