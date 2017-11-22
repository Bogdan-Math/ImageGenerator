package layers.web.vaadin;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import system.ResourceReader;

import java.net.URL;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.$;
import static java.lang.Integer.valueOf;
import static layers.web.vaadin.layout.footer.FooterLayout.FLAGS_LINK_ID;
import static layers.web.vaadin.layout.footer.FooterLayout.JAVARUSH_LINK_ID;
import static layers.web.vaadin.layout.header.HeaderLayout.CODACY_LINK_ID;
import static layers.web.vaadin.layout.header.HeaderLayout.GITHUB_LINK_ID;

public class ImageGeneratorPageObject {

    public ImageGeneratorPageObject() {
        Configuration.browser = "chrome";
    }

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
        return $(By.id(CODACY_LINK_ID));
    }

    public SelenideElement githubLink() {
        return $(By.id(GITHUB_LINK_ID));
    }

    public SelenideElement javarushLink() {
        return $(By.id(JAVARUSH_LINK_ID));
    }

    public SelenideElement flagsLink() {
        return $(By.id(FLAGS_LINK_ID));
    }
}
