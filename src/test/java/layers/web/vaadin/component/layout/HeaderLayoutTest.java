package layers.web.vaadin.component.layout;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class HeaderLayoutTest {

    private FirefoxDriver firefox;
    private WebDriverWait wait;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.gecko.driver", "D:\\Projects\\Java\\ImageGenerator\\src\\test\\resources\\geckodriver.exe");

        this.firefox = new FirefoxDriver();
        this.wait    = new WebDriverWait(firefox, 10);

        this.firefox.get("http://localhost:8080/ImageGenerator/");
    }

    @After
    public void tearDown() throws Exception {
        this.firefox.quit();
    }

    @Test
    public void codacyLink() throws Exception {
        WebElement codacyLink = wait.until(elementToBeClickable(By.id("codacy-link-id")));

        codacyLink.click();

        for (String handle : firefox.getWindowHandles()) {
            firefox.switchTo().window(handle);
            assertThat(firefox.getTitle(), is("ImageGenerator - Codacy - Dashboard"));
        }
    }

    @Test
    public void githubLink() throws Exception {
        WebElement githubLink = wait.until(elementToBeClickable(By.id("github-link-id")));

        githubLink.click();

        for (String handle : firefox.getWindowHandles()) {
            firefox.switchTo().window(handle);
            assertThat(firefox.getTitle(), containsString("GitHub - Bogdan-Math/ImageGenerator"));
        }
    }
}