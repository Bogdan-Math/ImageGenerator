package layers.web.vaadin.layout.footer;

import layers.web.vaadin.ImageGeneratorPageObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.title;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class FooterLayoutUIIT {

    @Autowired
    private ImageGeneratorPageObject imageGeneratorPage;

    @Before
    public void setUp() throws Exception {
        open(imageGeneratorPage.url());
        imageGeneratorPage.generatorTab().click();
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