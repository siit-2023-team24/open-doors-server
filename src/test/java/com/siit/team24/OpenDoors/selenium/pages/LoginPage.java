package com.siit.team24.OpenDoors.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;

    @FindBy(id="username")
    private WebElement usernameInput;
    @FindBy(id="password")
    private WebElement passwordInput;

    @FindBy(id="sign-in-btn")
    private WebElement signInBtn;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(usernameInput));
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        signInBtn.click();

    }
}