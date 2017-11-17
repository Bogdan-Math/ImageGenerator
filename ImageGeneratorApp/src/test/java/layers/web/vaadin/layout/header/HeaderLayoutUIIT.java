package layers.web.vaadin.layout.header;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class HeaderLayoutUIIT {

    @Before
    public void setUp() throws Exception {
        open("https://image-generator-app.herokuapp.com/");
    }

    @Test
    public void codacyLink() throws Exception {
        $(By.id("codacy-link-id")).click();
        assertThat(title(), containsString("ImageGenerator - Codacy - Dashboard"));
    }

    @Test
    public void githubLink() throws Exception {
        $(By.id("github-link-id")).click();
        assertThat(title(), containsString("GitHub - Bogdan-Math/ImageGenerator"));
    }
}