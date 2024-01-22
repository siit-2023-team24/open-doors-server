package com.siit.team24.OpenDoors.selenium_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CreateAccommodationPage {
    private WebDriver driver;

    @FindBy(id="acc-name")
    private WebElement accommodationNameInput;

    @FindBy(id="acc-city")
    private WebElement accommodationCityInput;

    @FindBy(id="acc-street")
    private WebElement accommodationStreetInput;

    @FindBy(id="acc-country")
    private WebElement accommodationCountrySelect;

    @FindBy(id="acc-type")
    private WebElement accommodationTypeSelect;

    @FindBy(id="acc-deadline")
    private WebElement deadlineInput;

    public CreateAccommodationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void fillFirstPage(String name, String country, String city, String street, String type) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(10));
        wait.until(ExpectedConditions.visibilityOf(accommodationNameInput));
        accommodationNameInput.sendKeys(name);
        accommodationCountrySelect.click();
        WebElement countryToSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[./*[contains(text(), '" + country + "')]]")));
        countryToSelect.click();
        accommodationCityInput.sendKeys(city);
        accommodationStreetInput.sendKeys(street);
        accommodationTypeSelect.click();
        WebElement typeToSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[./*[contains(text(), '" + type + "')]]")));
        typeToSelect.click();
    }

    public boolean canInputDeadline(String deadline){
        deadlineInput.clear();
        deadlineInput.sendKeys(deadline);
        List<WebElement> errors = driver.findElements (By.xpath("//*[@id='deadline-field']//mat-error"));
        return errors.isEmpty();
    }

    public List<String> getDateRanges() {

        return new ArrayList<>();
    }

}
