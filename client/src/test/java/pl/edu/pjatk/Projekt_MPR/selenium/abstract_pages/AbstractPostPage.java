package pl.edu.pjatk.Projekt_MPR.selenium.abstract_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pl.edu.pjatk.Projekt_MPR.selenium.ViewAllPage;

import java.time.Duration;

public abstract class AbstractPostPage {
    protected WebDriver webDriver;
    @FindBy(id = "submit")
    protected WebElement submitButton;

    public AbstractPostPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public ViewAllPage submitForm(){
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("input[type='submit']")));
        this.submitButton.click();
        return new ViewAllPage(webDriver);
    }

    public abstract  <T extends AbstractPostPage> T open();
}
