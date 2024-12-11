package pl.edu.pjatk.Projekt_MPR.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddFormPage extends AbstractPageWithComputerFields {

    public AddFormPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public AbstractPostPage open(){
        this.webDriver.get("http://localhost:8080/addForm");
        return this;
    }

}
