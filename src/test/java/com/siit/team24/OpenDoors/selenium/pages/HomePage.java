package com.siit.team24.OpenDoors.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private WebDriver driver;

    private static String PAGE_URL = "http://localhost:4200/home";

    @FindBy(id="log-in")
    private WebElement loginBtn;

    @FindBy(id = "header-title")
    private WebElement title;

    @FindBy(id = "reservations")
    private WebElement reservationsBtn;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public void clickOnLogin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn));
        loginBtn.click();
    }

    public void clickReservations() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(reservationsBtn));
        reservationsBtn.click();
    }

    public boolean isPageOpened() {
        boolean isOpened = (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.textToBePresentInElement(title, "Open Doors"));

        return isOpened;
    }
}
