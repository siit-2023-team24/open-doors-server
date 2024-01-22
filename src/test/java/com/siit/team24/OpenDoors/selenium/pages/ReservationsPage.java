package com.siit.team24.OpenDoors.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ReservationsPage {

    private WebDriver driver;

    @FindBy(css = ".cancel-button")
    private List<WebElement> cancelBtns;

    @FindBy(id = "header-title")
    private WebElement title;





    public ReservationsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened() {
        boolean isOpened = (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.textToBePresentInElement(title, "My requests"));

        return isOpened;
    }

    public void clickCancel(Long id) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".details-and-buttons")));
        List<WebElement> elements = driver.findElements(By.cssSelector(".details-and-buttons"));
        for (WebElement element: elements) {
            Actions actions = new Actions(driver);
            actions.moveToElement(element.findElement(By.cssSelector(".cancel-button"))).perform();
            if (element.findElement(By.id("req-id")).getText().equals(id.toString())) {
                element.findElement(By.cssSelector(".cancel-button")).click();
                return;
            }
        }
    }

    public void check(Long id, String status) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".details-and-buttons")));
        List<WebElement> elements = driver.findElements(By.cssSelector(".details-and-buttons"));
        for (WebElement element: elements) {
            if (checkElement(element, id, status)) return;
        }
    }

    private boolean checkElement(WebElement element, Long id, String status) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        wait.until(ExpectedConditions.visibilityOf(element));
        if (element.findElement(By.id("req-id")).getText().equals(id.toString())) {
            wait.until(ExpectedConditions.textToBePresentInElement(element.findElement(By.id("status")), status));
            return true;
        }
        return false;
    }

}
