package pl.edu.pjatk.Projekt_MPR.selenium.add_form;

import org.openqa.selenium.WebDriver;
import pl.edu.pjatk.Projekt_MPR.selenium.abstract_pages.AbstractPageWithComputerFields;

public class AddFormPage extends AbstractPageWithComputerFields {

    public AddFormPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public AddFormPage open(){
        this.webDriver.get("http://localhost:8080/addForm");
        return this;
    }

}
