package pl.edu.pjatk.Projekt_MPR.selenium.delete_form;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pl.edu.pjatk.Projekt_MPR.selenium.ViewAllPage;
import pl.edu.pjatk.Projekt_MPR.selenium.abstract_pages.AbstractPostPage;

import java.time.Duration;
import java.util.List;

public class DeleteFormPage extends AbstractPostPage {

    public DeleteFormPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public DeleteFormPage open() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("tr")));

        List<WebElement> rows = webDriver.findElements(By.tagName("tr"));
        WebElement lastRow = rows.get(rows.size() - 1);

        List<WebElement> lastRowFields = lastRow.findElements(By.tagName("td"));
        String id = lastRowFields.get(0).getText();

        webDriver.get("http://localhost:8082/deleteForm/" + id);
        return this;
    }
}
