package pl.edu.pjatk.Projekt_MPR.selenium.update_form;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.edu.pjatk.Projekt_MPR.selenium.abstract_pages.AbstractPageWithComputerFields;

public class UpdateFormPage extends AbstractPageWithComputerFields {
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
    public UpdateFormPage open(){
        this.webDriver.get("http://localhost:8082/updateForm");
        return this;
    }
}
