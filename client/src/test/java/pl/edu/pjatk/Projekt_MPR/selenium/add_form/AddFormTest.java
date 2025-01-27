package pl.edu.pjatk.Projekt_MPR.selenium.add_form;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import pl.edu.pjatk.Projekt_MPR.selenium.ViewAllPage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddFormTest {
    WebDriver webDriver;

    @BeforeEach
    public void setUp() {
        this.webDriver = new FirefoxDriver();
    }

    @Test
    public void testAddForm() {
        AddFormPage addFormPage = new AddFormPage(webDriver);
        addFormPage.open()
                .fillInNameInput("Sin")
                .fillInComputerCaseModelInput("Cos");

        ViewAllPage viewAllPage = addFormPage.submitForm();
        assertEquals("Add computer", viewAllPage.getHeaderText());

        List<WebElement> lastComputerFields =
                webDriver.findElements(By.tagName("tr"))
                        .getLast()
                        .findElements(By.tagName("td"));

        assertEquals("Sin", lastComputerFields.get(0).getText());
        assertEquals("Cos", lastComputerFields.get(1).getText());

    }
}