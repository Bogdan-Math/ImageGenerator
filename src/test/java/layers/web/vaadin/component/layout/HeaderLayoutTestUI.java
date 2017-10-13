package layers.web.vaadin.component.layout;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.system.ResourceReader;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

//@Ignore
public class HeaderLayoutTestUI {

    private static Map<String, String> driverLocations = new HashMap<String, String>() {{
        put("windows", new ResourceReader().readFile("geckodriver.exe").getAbsolutePath());
    }};

    private static FirefoxDriver firefox;
    private static WebDriverWait wait;

    @BeforeClass
    public static void beforeClass() {
        driverLocations.keySet().stream()
                .filter(osName -> System.getProperty("os.name").toLowerCase().contains(osName)).findFirst()
                .ifPresent(osName -> System.setProperty("webdriver.gecko.driver", driverLocations.get(osName)));

        firefox = new FirefoxDriver();
        wait    = new WebDriverWait(firefox, 10);
    }

    @AfterClass
    public static void afterClass() {
        firefox.quit();
    }

    @Before
    public void setUp() throws Exception {
        firefox.get("http://localhost:8080/ImageGenerator/");
    }

    @Test
    public void codacyLink() throws Exception {
        WebElement codacyLink = wait.until(elementToBeClickable(By.id("codacy-link-id")));

        codacyLink.click();

        assertThat(firefox.getWindowHandles().stream()
                .filter(handle -> {
                    firefox.switchTo().window(handle);
                    wait.until(firefox -> ((JavascriptExecutor) firefox)
                        .executeScript("return document.readyState").equals("complete"));
                    return firefox.getTitle().contains("ImageGenerator - Codacy - Dashboard");
                }).count(), is(1L));
    }

    @Test
    public void githubLink() throws Exception {
        WebElement githubLink = wait.until(elementToBeClickable(By.id("github-link-id")));

        githubLink.click();

        assertThat(firefox.getWindowHandles().stream()
                .filter(handle -> {
                    firefox.switchTo().window(handle);
                    wait.until(firefox -> ((JavascriptExecutor) firefox)
                        .executeScript("return document.readyState").equals("complete"));
                    return firefox.getTitle().contains("GitHub - Bogdan-Math/ImageGenerator");
                }).count(), is(1L));
    }
}