package layers.web.vaadin;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.net.URL;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static layers.web.vaadin.layout.Tabs.GALLERY;
import static layers.web.vaadin.layout.Tabs.GENERATOR;
import static layers.web.vaadin.layout.footer.FooterLayout.FLAGS_LINK_ID;
import static layers.web.vaadin.layout.footer.FooterLayout.JAVARUSH_LINK_ID;
import static layers.web.vaadin.layout.header.HeaderLayout.CODACY_LINK_ID;
import static layers.web.vaadin.layout.header.HeaderLayout.GITHUB_LINK_ID;

public class ImageGeneratorPageObject {

    private String protocol;
    private String host;
    private Integer port;
    private String route;

    public ImageGeneratorPageObject(String browser, String protocol, String host, Integer port, String route) {
        Configuration.timeout = 10000;//ms TODO: fix timeout wait
        Configuration.browser = browser;

        this.protocol = protocol;
        this.host     = host;
        this.port     = port;
        this.route    = route;
    }

    public URL url() throws Exception {
        return new URL(protocol, host, port, route);
    }

    public SelenideElement generatorTab() {
        return $(byText(GENERATOR));
    }

    public SelenideElement galleryTab() {
        return $(byText(GALLERY));
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
