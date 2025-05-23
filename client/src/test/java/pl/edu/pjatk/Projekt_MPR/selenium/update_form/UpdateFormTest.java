package pl.edu.pjatk.Projekt_MPR.selenium.update_form;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import pl.edu.pjatk.Projekt_MPR.selenium.ViewAllPage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateFormTest {
    private WebDriver webDriver;

    @BeforeEach
    public void setUp() {
        webDriver = new FirefoxDriver();
    }

    @Test
    public void updateAllComputerFields() {

        String name = "Not taken value";
        String computerCaseModel = "Not taken value";
        UpdateFormPage updateFormPage = new UpdateFormPage(webDriver);
        updateFormPage.open()
                .fillInNameInput(name)
                .fillInComputerCaseModelInput(computerCaseModel);

        ViewAllPage viewAllPage = updateFormPage.submitForm();
        assertEquals("Computers", viewAllPage.getHeaderText());

        List<WebElement> firstComputerFields = getLastComputerFields();

        assertEquals(name , firstComputerFields.get(1).getText());
        assertEquals(computerCaseModel , firstComputerFields.get(2).getText());

    }

    private List<WebElement> getLastComputerFields() {
        return webDriver.findElements(By.tagName("tr"))
                .getLast()
                .findElements(By.tagName("td"));
    }
}
