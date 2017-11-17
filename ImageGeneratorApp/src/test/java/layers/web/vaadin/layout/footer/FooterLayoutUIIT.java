package layers.web.vaadin.layout.footer;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class FooterLayoutUIIT {

    @Before
    public void setUp() throws Exception {
        open("https://image-generator-app.herokuapp.com/");
    }

    @Test
    public void javarushLinkSpecification() throws Exception {
        $(By.id("javarush-link-id")).click();
        assertThat(title(), containsString("JavaRush"));
    }

    @Test
    public void flagsLink() throws Exception {
        $(By.id("flags-link-id")).click();
        assertThat(title(), containsString("IconDrawer"));
    }

}