package layers.web.vaadin;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.net.URL;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.$;
import static java.lang.Integer.valueOf;

public class ImageGeneratorPageObject {

    public URL url() throws Exception {
        Properties props = new Properties();
        URL url          = getClass().getClassLoader()
                                     .getResource("webapp-integration-tests.properties");

        assert url != null;
        props.load(url.openStream());

        String protocol = props.getProperty("webapp.protocol");
        String host     = props.getProperty("webapp.host");
        Integer port    = valueOf(props.getProperty("webapp.port"));
        String route    = "/" + props.getProperty("webapp.route");

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
