package layers.web.vaadin.layout.header;

import layers.web.vaadin.ImageGeneratorPageObject;
import org.junit.Before;
import org.junit.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.title;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class HeaderLayoutUIIT {

    private ImageGeneratorPageObject imageGeneratorPage = new ImageGeneratorPageObject();

    @Before
    public void setUp() throws Exception {
        open(imageGeneratorPage.url());
    }

    @Test
    public void codacyLink() throws Exception {
        imageGeneratorPage.codacyLink().click();
        assertThat(title(), containsString("ImageGenerator - Codacy - Dashboard"));
    }

    @Test
    public void githubLink() throws Exception {
        imageGeneratorPage.githubLink().click();
        assertThat(title(), containsString("GitHub - Bogdan-Math/ImageGenerator"));
    }
}