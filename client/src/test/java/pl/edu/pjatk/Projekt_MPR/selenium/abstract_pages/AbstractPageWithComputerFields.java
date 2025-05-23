package pl.edu.pjatk.Projekt_MPR.selenium.abstract_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class AbstractPageWithComputerFields extends AbstractPostPage {
    @FindBy(id = "name")
    WebElement nameInput;
    @FindBy(id = "computerCaseModel")
    WebElement computerCaseModelInput;

    public AbstractPageWithComputerFields(WebDriver webDriver) {
        super(webDriver);
    }

    public AbstractPageWithComputerFields fillInNameInput(String name){
        this.nameInput.clear();
        this.nameInput.sendKeys(name);
        return this;
    }

    public AbstractPageWithComputerFields fillInComputerCaseModelInput(String computerCaseModel){
        this.computerCaseModelInput.clear();
        this.computerCaseModelInput.sendKeys(computerCaseModel);
        return this;
    }
}
