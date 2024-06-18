package com.siit.team24.OpenDoors.selenium_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomePage {
    private WebDriver driver;

    private static String PAGE_URL = "http://localhost:4200/home";

    @FindBy(id="log-in")
    private WebElement loginBtn;

    @FindBy(id="accommodations")
    private WebElement accommodationsBtn;

    @FindBy(id="search-accommodations-button")
    private WebElement searchBtn;

    @FindBy(id="filter-accommodations-button")
    private WebElement filterBtn;

    @FindBy(id="location-input")
    private WebElement locationInput;

    @FindBy(id="check-in-date-input")
    private WebElement checkInDateInput;

    @FindBy(id="check-out-date-input")
    private WebElement checkOutDateInput;

    @FindBy(id="not-a-pass-field")
    private WebElement numberOfGuestsInput;

    @FindBy(className = "accommodation-type")
    private List<WebElement> accommodationTypes;

    @FindBy(className = "amenity")
    private List<WebElement> amenities;

    @FindBy(id="min-price-input")
    private WebElement minPriceInput;

    @FindBy(id="max-price-input")
    private WebElement maxPriceInput;

    @FindBy(className = "accommodation-card")
    private List<WebElement> accommodationGrid;

    @FindBy(id="filter-search-button")
    private WebElement filterSearchButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public void clickOnLogin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(loginBtn));
        loginBtn.click();
    }

    public void clickAccommodations() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(accommodationsBtn));
        accommodationsBtn.click();
    }

    public void searchAccommodations(String location, String checkInDate, String checkOutDate, String numberOfGuests, int numberOfResults) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(locationInput));
        locationInput.sendKeys(location);
        checkInDateInput.sendKeys(checkInDate);
        checkOutDateInput.sendKeys(checkOutDate);
        numberOfGuestsInput.sendKeys(numberOfGuests);
        clickSearchButton();

        // Wait until the search results are updated
        try {
            wait.until(ExpectedConditions.numberOfElementsToBe(By.className("accommodation-card"), numberOfResults));
        } catch (TimeoutException e) {
            System.out.println("Expected number of results not found within the timeout period");
        }
    }

    public void filterAccommodations(String location, String checkInDate, String checkOutDate, String numberOfGuests, List<Integer> accommodationTypes, String minPrice, String maxPrice, List<Integer> amenities, int numberOfResults) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        clickFilterButton_OpenDialog();

        wait.until(ExpectedConditions.visibilityOf(this.accommodationTypes.get(0)));
        // Wait until amenities list is not null and has elements
        wait.until(ExpectedConditions.visibilityOfAllElements(this.amenities));
        minPriceInput.sendKeys(minPrice);
        maxPriceInput.sendKeys(maxPrice);
        selectAmenities(amenities);
        selectAccommodationTypes(accommodationTypes);

        clickFilterButton_ActualFilter();

        // Wait until the search results are updated
        try {
            wait.until(ExpectedConditions.numberOfElementsToBe(By.className("accommodation-card"), numberOfResults));
        } catch (TimeoutException e) {
            System.out.println("Expected number of results not found within the timeout period");
        }
    }
    public void selectAmenities(List<Integer> amenities){
        if(amenities != null)
            for (Integer i : amenities){
                this.amenities.get(i).click();
            }
    }
    public void selectAccommodationTypes(List<Integer> accommodationTypes){
        System.out.println(accommodationTypes);
        System.out.println(this.accommodationTypes);
        if(accommodationTypes != null)
            for (Integer i : accommodationTypes){
                this.accommodationTypes.get(i).click();
            }
    }
    public void clickSearchButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(searchBtn));
        searchBtn.click();
    }

    public void clickFilterButton_OpenDialog() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(filterBtn));
        filterBtn.click();
    }

    public void clickFilterButton_ActualFilter() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(filterSearchButton));
        filterSearchButton.click();
    }

    public void assertTitles(String title) {
        for (WebElement tile : accommodationGrid) {
            assertEquals(title.toLowerCase().trim(), getTitle(tile).toLowerCase().trim());
        }
    }

    public void verifyNumberOfResults(int expectedSize) {
        assertEquals(expectedSize, accommodationGrid.size());
    }

    public String getTitle(WebElement tile){
        return tile.findElement(By.className("location")).getText();
    }
}
