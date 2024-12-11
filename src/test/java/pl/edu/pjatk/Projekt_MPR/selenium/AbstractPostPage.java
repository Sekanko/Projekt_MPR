package pl.edu.pjatk.Projekt_MPR.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class AbstractPostPage {
    protected WebDriver webDriver;
    @FindBy(id = "submit")
    protected WebElement submitButton;

    public AbstractPostPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public ViewAllPage submitForm(){
        this.submitButton.click();
        return new ViewAllPage(webDriver);
    }

    public abstract AbstractPostPage open();

}
