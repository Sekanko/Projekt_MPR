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
        UpdateFormPage updateFormPage = new UpdateFormPage(webDriver);
        updateFormPage.open()
                .fillIdInput("1")
                .fillInNameInput("Tang")
                .fillInComputerCaseModelInput("Ens");

        ViewAllPage viewAllPage = updateFormPage.submitForm();
        assertEquals("All my computers:", viewAllPage.getHeaderText());

        List<WebElement> firstComputerFields = getFirstComputerFields();

        assertEquals("Tang" , firstComputerFields.get(0).getText());
        assertEquals("Ens" , firstComputerFields.get(1).getText());

    }
    @Test
    public void updateName(){
        UpdateFormPage updateFormPage = new UpdateFormPage(webDriver);
        updateFormPage.open()
                .fillIdInput("1")
                .fillInNameInput("Cotang")
                .fillInComputerCaseModelInput("");

        ViewAllPage viewAllPage = updateFormPage.submitForm();
        assertEquals("All my computers:", viewAllPage.getHeaderText());

        List<WebElement> firstComputerFields = getFirstComputerFields();

        assertEquals("Cotang" , firstComputerFields.get(0).getText());
    }

    @Test
    public void updateComputerCaseModel(){
        UpdateFormPage updateFormPage = new UpdateFormPage(webDriver);
        updateFormPage.open()
                .fillIdInput("1")
                .fillInNameInput("")
                .fillInComputerCaseModelInput("Lu");

        ViewAllPage viewAllPage = updateFormPage.submitForm();
        assertEquals("All my computers:", viewAllPage.getHeaderText());

        List<WebElement> firstComputerFields = getFirstComputerFields();

        assertEquals("Lu" , firstComputerFields.get(1).getText());

    }

    private List<WebElement> getFirstComputerFields() {
        return webDriver.findElements(By.tagName("tr"))
                .get(1)
                .findElements(By.tagName("td"));
    }
}
