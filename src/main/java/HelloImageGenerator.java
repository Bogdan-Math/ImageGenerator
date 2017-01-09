public class HelloImageGenerator {

    public static void main(String[] args) {
        System.out.println(new HelloImageGenerator().hello("Bogdan"));
    }

    public String hello(String name) {
        return "Hello ImageGenerator!\nP.S. Your creator, " + name + ".";
    }
}
