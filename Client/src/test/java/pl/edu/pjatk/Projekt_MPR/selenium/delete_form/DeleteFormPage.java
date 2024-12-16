package pl.edu.pjatk.Projekt_MPR.selenium.delete_form;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.edu.pjatk.Projekt_MPR.selenium.abstract_pages.AbstractPostPage;

public class DeleteFormPage extends AbstractPostPage {
    @FindBy(id = "id")
    private WebElement idInput;

    public DeleteFormPage(WebDriver webDriver) {
        super(webDriver);
    }

    public DeleteFormPage fillIdInput(String id) {
        idInput.sendKeys(id);
        return this;
    }

    @Override
    public DeleteFormPage open() {
        webDriver.get("http://localhost:8080/deleteForm");
        return this;
    }
}
