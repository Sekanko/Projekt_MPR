package pl.edu.pjatk.Projekt_MPR.selenium.update_form;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pl.edu.pjatk.Projekt_MPR.selenium.abstract_pages.AbstractPageWithComputerFields;

import java.time.Duration;
import java.util.List;

public class UpdateFormPage extends AbstractPageWithComputerFields {

    public UpdateFormPage(WebDriver webDriver) {
        super(webDriver);
    }


    @Override
    public UpdateFormPage open(){
        this.webDriver.get("http://localhost:8082/view/all");
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("tr")));

        List<WebElement> rows = webDriver.findElements(By.tagName("tr"));
        WebElement lastRow = rows.get(rows.size() - 1);

        List<WebElement> lastRowFields = lastRow.findElements(By.tagName("td"));
        String id = lastRowFields.get(0).getText();

        this.webDriver.get("http://localhost:8082/updateForm/" + id);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("input")));
        return this;
    }
}
