package pl.allegro.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Slf4j
public class ItemListing {

    private final WebDriver driver;

    @FindBy(xpath = "//*[@id='opbox-listing-filters']/div/fieldset[10]/div/ul/li[3]/label")
    private WebElement blackColor;

    @FindBy(xpath = "//*[@id='opbox-listing--base']")
    private WebElement listing;

    public ItemListing(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ItemListing showOnlyInBlackColor() {
        Actions actions = new Actions(driver);

        actions.moveToElement(blackColor);
        actions.perform();
        blackColor.click();
        return this;
    }

    public void printInformation() {
        float max = -1;
        List<WebElement> list = listing.findElements(By.tagName("article"));

        for (WebElement item : list) {
            List<WebElement> spanList = item.findElements(By.tagName("span"));

            String[] strArr = item.getText().trim().split("\n");

            for (String str: strArr) {
                String template = "zł";
                String substr = str.substring(0, str.length()-2);
                if (Character.isDigit(str.charAt(0)) &&
                        str.substring(str.length()-2).equals(template)) {
                    String d = substr.replace(",", ".").replaceAll("\\s", "");
                    float cost = Float.parseFloat(d);
                    if (cost > max) {
                        max = cost;
                    }
                    break;
                }
            }
        }

        log.info("znaleziono " + list.size() + " artykułów na liście");
        if (max > -1) {
            log.info("maksymalna cena na liście = " + max);
            float withVat = (float) (max + (max*0.23));
            log.info("cena + 23% = " + withVat);
        }
    }
}
