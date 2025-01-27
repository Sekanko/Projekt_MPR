package pl.edu.pjatk.Projekt_MPR.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ViewAllPage {
    private WebDriver webDriver;
    @FindBy(tagName = "h1")
    private WebElement header;

    public ViewAllPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public String getHeaderText() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(7));
        wait.until(ExpectedConditions.visibilityOf(header));
        return this.header.getText();
    }

}
