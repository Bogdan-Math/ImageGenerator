package layers.web.vaadin.layout.footer;

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

@Ignore
public class FooterLayoutTestUI {

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
    public void javarushLink() throws Exception {
        WebElement codacyLink = wait.until(elementToBeClickable(By.id("javarush-link-id")));

        codacyLink.click();

        assertThat(firefox.getWindowHandles().stream()
                .filter(handle -> {
                    firefox.switchTo().window(handle);
                    wait.until(firefox -> ((JavascriptExecutor) firefox)
                        .executeScript("return document.readyState").equals("complete"));
                    return firefox.getTitle().contains("JavaRush");
                }).count(), is(1L));
    }

    @Test
    public void flagsLink() throws Exception {
        WebElement codacyLink = wait.until(elementToBeClickable(By.id("flags-link-id")));

        codacyLink.click();

        assertThat(firefox.getWindowHandles().stream()
                .filter(handle -> {
                    firefox.switchTo().window(handle);
                    wait.until(firefox -> ((JavascriptExecutor) firefox)
                        .executeScript("return document.readyState").equals("complete"));
                    return firefox.getTitle().contains("IconDrawer");
                }).count(), is(1L));
    }

}