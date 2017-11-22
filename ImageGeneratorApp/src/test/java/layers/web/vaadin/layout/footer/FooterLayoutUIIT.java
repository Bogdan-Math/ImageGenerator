package layers.web.vaadin.layout.footer;

import layers.web.vaadin.ImageGeneratorPageObject;
import org.junit.Before;
import org.junit.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.title;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class FooterLayoutUIIT {

    private ImageGeneratorPageObject imageGeneratorPage = new ImageGeneratorPageObject();

    @Before
    public void setUp() throws Exception {
        open(imageGeneratorPage.url());
    }

    @Test
    public void javarushLink() throws Exception {
        imageGeneratorPage.javarushLink().click();
        assertThat(title(), containsString("JavaRush"));
    }

    @Test
    public void flagsLink() throws Exception {
        imageGeneratorPage.flagsLink().click();
        assertThat(title(), containsString("IconDrawer"));
    }

}