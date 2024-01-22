package com.siit.team24.OpenDoors.selenium_tests;

import com.siit.team24.OpenDoors.selenium_pages.CreateAccommodationPage;
import com.siit.team24.OpenDoors.selenium_pages.MyAccommodationsPage;
import com.siit.team24.OpenDoors.selenium_pages.HomePage;
import com.siit.team24.OpenDoors.selenium_pages.LoginPage;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CreateAccommodationTest extends TestBase {
    static final String USERNAME = "vasilije.markovic.privatni+s@gmail.com";
    static final String PASSWORD = "12345678";
    static final String NAME = "Test accommodation";
    static final String COUNTRY = "Afghanistan";
    static final String CITY = "Kabul";
    static final String STREET = "Service Rd";
    static final String TYPE = "Entire Home";
    static final String DEADLINE_LOWER_BOUNDARY = "-1";
    static final String DEADLINE_UPPER_BOUNDARY = "366";
    static final String DEADLINE_VALID = "20";

    private CreateAccommodationPage working_space;

    @BeforeTest
    public void goToCreateAccommodation() throws InterruptedException {
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

    @Test(priority = 1)
    public void testDeadline() {
        assertFalse(working_space.canInputDeadline(DEADLINE_LOWER_BOUNDARY));
        assertFalse(working_space.canInputDeadline(DEADLINE_UPPER_BOUNDARY));
        assertTrue(working_space.canInputDeadline(DEADLINE_VALID));
    }

    @Test(dataProvider = "availabilityProvider", priority = 2)
    public void testAvailability(List<String> dates, List<String> dateRanges) {
        List<String> result = working_space.getAvailability(dates);
        assertEquals(result.size(), dateRanges.size());
        for(int i=1; i<result.size(); i++) {
            assertEquals(result.get(i), dateRanges.get(i));
        }
    }

    @Test(dataProvider = "seasonalRatesProvider", priority = 3)
    public void testSeasonalRates(List<String> dates, List<String> seasonalRates, String value) {
        List<String> result = working_space.getSeasonalRates(dates, value);
        assertEquals(result.size(), seasonalRates.size());
        for(int i=1; i<result.size(); i++) {
            assertEquals(result.get(i), seasonalRates.get(i));
        }
    }

    @DataProvider(name = "availabilityProvider")
    public Object[][] availabilityProvider() {
        return new Object[][]{
                {Arrays.asList("January 25, 2024", "January 27, 2024", "January 26, 2024",
                        "February 26, 2024", "January 26, 2024", "March 26, 2024"),
                Arrays.asList("Available period: Jan 25, 2024 to Jan 25, 2024",
                            "Available period: Jan 27, 2024 to Jan 27, 2024",
                        "Available period: Feb 26, 2024 to Mar 26, 2024")}

        };
    }

    @DataProvider(name = "seasonalRatesProvider")
    public Object[][] seasonalRatesProvider() {
        return new Object[][]{
                {Arrays.asList("January 25, 2024", "January 27, 2024", "January 26, 2024",
                        "February 26, 2024", "March 26, 2024"),
                        Arrays.asList("A new price of 4.5 from Jan 25, 2024 to Jan 27, 2024",
                        "A new price of 4.5 from Feb 26, 2024 to Feb 26, 2024",
                        "A new price of 4.5 from Mar 26, 2024 to Mar 26, 2024"),
                        "4.5"}
        };
    }

    @AfterTest
    public void finish() {
        
    }
}
