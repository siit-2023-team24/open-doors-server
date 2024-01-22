package com.siit.team24.OpenDoors.selenium_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @FindBy(id="second-page")
    private List<WebElement> secondPage;

    @FindBy(id="third-page")
    private WebElement thirdPage;

    @FindBy(id="availability-calendar")
    private WebElement availabilityCalendar;

    @FindBy(id="seasonal-rate-calendar")
    private WebElement seasonalRateCalendar;

    @FindBy(id="price-input")
    private WebElement priceInput;

    @FindBy(id="create-btn")
    private WebElement createBtn;

    public CreateAccommodationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void fillFirstPage(String name, String country, String city, String street, String type) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(accommodationNameInput));
        accommodationNameInput.sendKeys(name);

        accommodationTypeSelect.click();
        WebElement typeToSelect = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[contains(text(), '" + type + "')]")));
        typeToSelect.click();

        accommodationCountrySelect.click();
        WebElement countryToSelect = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[contains(text(), '" + country + "')]")));
        countryToSelect.click();

        accommodationCityInput.sendKeys(city);
        accommodationStreetInput.sendKeys(street);

    }

    public boolean canInputDeadline(String deadline){
        deadlineInput.clear();
        deadlineInput.sendKeys(deadline);
        List<WebElement> errors = driver.findElements (By.xpath("//*[@id='deadline-field']//mat-error"));
        return errors.isEmpty();
    }

    private void pickAvailableDate(String date) throws ParseException {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");

        List<WebElement> dates = driver.findElements(By.xpath("//button[@aria-label='" + date + "']"));
        while (dates.isEmpty()) {
            WebElement moveButton = driver.findElement(By.cssSelector(".mat-calendar-next-button"));
            WebElement month = driver.findElement(By.xpath("//*[@class='mdc-button__label']//span[@aria-hidden='true']"));
            if(monthFormat.parse(month.getText()).after(dateFormat.parse(date))) {
                moveButton = driver.findElement(By.cssSelector(".mat-calendar-previous-button"));
            }
            moveButton.click();

            dates = driver.findElements(By.xpath("//button[@aria-label='" + date + "']"));
        }

        dates.get(0).click();
    }

    public List<String> getAvailability(List<String> dates) {
        secondPage.get(0).click();
        for (String date : dates) {
            try {
                pickAvailableDate(date);
            } catch (ParseException e) {
                System.out.println("Nope");
            }
        }
        List<WebElement> dateRangeContainers = driver.findElements(By.tagName("li"));
        List<String> dateRanges = new ArrayList<>();
        for (WebElement liElement : dateRangeContainers) {
            dateRanges.add(liElement.getText());
        }
        return dateRanges;
    }

    public List<String> getSeasonalRates(List<String> dates, String value) {
        if(!secondPage.isEmpty()) secondPage.get(0).click();
        thirdPage.click();
        priceInput.clear();
        priceInput.sendKeys(value);
        for (String date : dates) {
            try {
                pickAvailableDate(date);
            } catch (ParseException e) {
                System.out.println("Nope");
            }
        }
        List<WebElement> dateRangeContainers = driver.findElements(By.tagName("li"));
        List<String> seasonalRates = new ArrayList<>();
        for (WebElement liElement : dateRangeContainers) {
            seasonalRates.add(liElement.getText());
        }
        return seasonalRates;
    }

    public void create() {
        createBtn.click();
    }

}
