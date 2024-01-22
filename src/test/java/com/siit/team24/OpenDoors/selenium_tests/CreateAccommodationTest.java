package com.siit.team24.OpenDoors.selenium_tests;

import com.siit.team24.OpenDoors.selenium_pages.CreateAccommodationPage;
import com.siit.team24.OpenDoors.selenium_pages.MyAccommodationsPage;
import com.siit.team24.OpenDoors.selenium_pages.HomePage;
import com.siit.team24.OpenDoors.selenium_pages.LoginPage;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CreateAccommodationTest extends TestBase {
    static final String USERNAME = "vasilije.markovic.privatni+s@gmail.com";
    static final String PASSWORD = "12345678";
    static final String NAME = "Test accommodation";
    static final String COUNTRY = "Afghanistan";
    static final String CITY = "Kabul";
    static final String STREET = "Service Rd";
    static final String TYPE = "Hotel";
    static final String DEADLINE_LOWER_BOUNDARY = "-1";
    static final String DEADLINE_UPPER_BOUNDARY = "366";
    static final String DEADLINE_VALID = "20";

    private CreateAccommodationPage working_space;

    @BeforeTest
    public void createAccommodation() {
        HomePage home = new HomePage(driver);
        home.clickOnLogin();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);
        home.clickAccommodations();
        MyAccommodationsPage myAccommodationsPage = new MyAccommodationsPage(driver);
        myAccommodationsPage.clickCreateAccommodation();
        working_space = new CreateAccommodationPage(driver);
        working_space.fillFirstPage(NAME, COUNTRY, CITY, STREET, TYPE);
    }

    @Test
    public void testDeadline() {
        assertFalse(working_space.canInputDeadline(DEADLINE_LOWER_BOUNDARY));
        assertFalse(working_space.canInputDeadline(DEADLINE_UPPER_BOUNDARY));
        assertTrue(working_space.canInputDeadline(DEADLINE_VALID));
    }
}
