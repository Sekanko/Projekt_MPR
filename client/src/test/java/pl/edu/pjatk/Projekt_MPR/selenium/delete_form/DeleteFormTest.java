package pl.edu.pjatk.Projekt_MPR.selenium.delete_form;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pl.edu.pjatk.Projekt_MPR.selenium.SharedValues;
import pl.edu.pjatk.Projekt_MPR.selenium.ViewAllPage;

import java.time.Duration;
import java.util.List;

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

        webDriver.get(SharedValues.BASE_URL + "view/all");

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("tr")));

        List<WebElement> rowsBeforeDelete = webDriver.findElements(By.tagName("tr"));
        int countBeforeDelete = rowsBeforeDelete.size() - 1;

        deleteFormPage.open();

        ViewAllPage viewAllPage = deleteFormPage.submitForm();
        assertEquals("Computers", viewAllPage.getHeaderText());

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("tr")));

        List<WebElement> rowsAfterDelete = webDriver.findElements(By.tagName("tr"));
        int countAfterDelete = rowsAfterDelete.size() - 1;

        assertEquals(countBeforeDelete - 1, countAfterDelete);
    }
}
