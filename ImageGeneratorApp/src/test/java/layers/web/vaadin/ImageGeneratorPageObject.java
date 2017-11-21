package layers.web.vaadin;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import system.ResourceReader;

import java.net.URL;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.$;
import static java.lang.Integer.valueOf;

public class ImageGeneratorPageObject {

    public URL url() throws Exception {
        URL propertiesURL     = new ResourceReader().readURL("webapp-integration-tests.properties");
        Properties properties = new Properties();

        properties.load(propertiesURL.openStream());

        String protocol = properties.getProperty("webapp.protocol");
        String host     = properties.getProperty("webapp.host");
        Integer port    = valueOf(properties.getProperty("webapp.port"));
        String route    = "/" + properties.getProperty("webapp.route");

        return new URL(protocol, host, port, route);
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
