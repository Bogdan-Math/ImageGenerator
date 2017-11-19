package layers.web.vaadin;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.net.MalformedURLException;
import java.net.URL;

import static com.codeborne.selenide.Selenide.$;

public class ImageGeneratorPageObject {

    public URL url() throws MalformedURLException {
        return new URL("http", "localhost", 9090,"/image-generator-app");
    }

    public SelenideElement codacyLink() {
        return $(By.id("codacy-link-id"));
    }

    public SelenideElement githubLink() {
        return $(By.id("github-link-id"));
    }

    public SelenideElement javarushLink() {
        return $(By.id("javarush-link-id"));
    }

    public SelenideElement flagsLink() {
        return $(By.id("flags-link-id"));
    }
}
