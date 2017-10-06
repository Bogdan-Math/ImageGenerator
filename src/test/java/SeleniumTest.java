import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SeleniumTest {

    private FirefoxDriver firefoxDriver;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.gecko.driver", "D:\\Projects\\Java\\ImageGenerator\\src\\test\\resources\\geckodriver.exe");
        this.firefoxDriver = new FirefoxDriver();
    }

    @After
    public void tearDown() throws Exception {
        this.firefoxDriver.quit();
    }

    @Test
    public void firstSeleniumTest() throws Exception {
        firefoxDriver.get("http://localhost:8080/ImageGenerator/");
        assertThat(firefoxDriver.getTitle(), is("Image Generator"));
    }
}
