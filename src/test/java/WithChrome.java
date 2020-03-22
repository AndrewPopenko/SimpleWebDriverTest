import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pl.allegro.config.ProjectConfig;
import pl.allegro.pages.HomePage;
import pl.allegro.pages.ItemListing;

import static java.util.concurrent.TimeUnit.SECONDS;

public class WithChrome {
    private WebDriver driver;
    private static ProjectConfig config;

    @BeforeClass
    public static void setupClass() {
        config = ConfigFactory.create(ProjectConfig.class, System.getProperties());

        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setupTest() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, SECONDS);
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void test() {
        new HomePage(driver)
                .open(config.baseUrl())
                .findGoods("Iphone 11");

        new ItemListing(driver)
                .showOnlyInBlackColor()
                .printInformation();
    }
}
