package pl.edu.pjatk.Projekt_MPR.selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

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
        assertEquals("All my computers:", viewAllPage.getHeaderText());
    }
}