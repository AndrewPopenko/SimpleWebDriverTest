package pl.allegro.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;

    @FindBy(xpath = "//button[@type = 'submit']")
    private WebElement submit;

    @FindBy(xpath = "//form/input")
    private WebElement search;

    @FindBy(xpath = "//img[contains(@alt, 'zamknij')]")
    private WebElement closeBanner;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public HomePage open(String baseUrl) {
        driver.get(baseUrl);
        if (closeBanner.isEnabled()) {
            closeBanner.click();
        }
        return this;
    }

    public HomePage findGoods(String item) {
        search.clear();
        search.sendKeys(item);
        submit.click();
        return this;
    }
}
