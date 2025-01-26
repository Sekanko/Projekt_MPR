package pl.edu.pjatk.Projekt_MPR.selenium.delete_form;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pl.edu.pjatk.Projekt_MPR.selenium.ViewAllPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteFormTest {
    private WebDriver webDriver;
    @BeforeEach
    public void setUp() {
        this.webDriver = new FirefoxDriver();
    }

    @Test
    public void testDeleteForm() {
        DeleteFormPage deleteFormPage = new DeleteFormPage(webDriver);
        deleteFormPage.open()
                .fillIdInput("1");

        ViewAllPage viewAllPage = deleteFormPage.submitForm();
        assertEquals("All my computers:", viewAllPage.getHeaderText());

        int sizeOfComputerListWithHeader = webDriver.findElements(By.tagName("tr")).size();
        assertEquals(3, sizeOfComputerListWithHeader);
    }
}
