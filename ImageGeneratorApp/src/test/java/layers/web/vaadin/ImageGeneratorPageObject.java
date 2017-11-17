package layers.web.vaadin;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class ImageGeneratorPageObject {

    public String url() {
        return "https://image-generator-app.herokuapp.com/";
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
