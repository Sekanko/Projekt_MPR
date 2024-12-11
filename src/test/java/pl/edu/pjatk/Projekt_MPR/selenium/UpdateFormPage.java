package pl.edu.pjatk.Projekt_MPR.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UpdateFormPage extends AbstractPageWithComputerFields{
    @FindBy(id = "id")
    private WebElement idInput;

    public UpdateFormPage(WebDriver webDriver) {
        super(webDriver);
    }

    public UpdateFormPage fillIdInput(String id) {
        idInput.sendKeys(id);
        return this;
    }

    @Override
    public AbstractPostPage open(){
        this.webDriver.get("http://localhost:8080/updateForm");
        return this;
    }
}
