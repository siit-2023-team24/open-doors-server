package com.siit.team24.OpenDoors.selenium_tests;

import com.siit.team24.OpenDoors.selenium_pages.CreateAccommodationPage;
import com.siit.team24.OpenDoors.selenium_pages.MyAccommodationsPage;
import com.siit.team24.OpenDoors.selenium_pages.HomePage;
import com.siit.team24.OpenDoors.selenium_pages.LoginPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class CreateAccommodationTest extends TestBase {
    static final String USERNAME = "vaske@test.test";
    static final String PASSWORD = "12345";
    static final String NAME = "Test accommodation";
    static final String COUNTRY = "Afghanistan";
    static final String CITY = "Kabul";
    static final String STREET = "Service Rd";
    static final String TYPE = "Entire Home";
    static final String DEADLINE_LOWER_BOUNDARY = "-1";
    static final String DEADLINE_UPPER_BOUNDARY = "366";
    static final String DEADLINE_VALID = "20";
    private boolean runBeforeEach = true;
    private CreateAccommodationPage working_space;

    @ParameterizedTest
    @MethodSource("provider")
    public void testCreateAccommodation(List<String> availableDates, List<String> dateRanges,
                                        List<String> seasonalRateDates, List<String> seasonalRates, String value) {
        if (!runBeforeEach) return;
        runBeforeEach = false;
        HomePage home = new HomePage(driver);
        home.clickOnLogin();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);
        home.clickAccommodations();
        MyAccommodationsPage myAccommodationsPage = new MyAccommodationsPage(driver);
        myAccommodationsPage.clickCreateAccommodation();
        working_space = new CreateAccommodationPage(driver);
        working_space.fillFirstPage(NAME, COUNTRY, CITY, STREET, TYPE);

        assertFalse(working_space.canInputDeadline(DEADLINE_LOWER_BOUNDARY));
        assertFalse(working_space.canInputDeadline(DEADLINE_UPPER_BOUNDARY));
        assertTrue(working_space.canInputDeadline(DEADLINE_VALID));

        List<String> result = working_space.getAvailability(availableDates);
        assertEquals(result.size(), dateRanges.size());
        for(int i=1; i<result.size(); i++) {
            assertEquals(result.get(i), dateRanges.get(i));
        }

        List<String> result1 = working_space.getSeasonalRates(seasonalRateDates, value);
        assertEquals(result1.size(), seasonalRates.size());
        for(int i=1; i<result1.size(); i++) {
            assertEquals(result1.get(i), seasonalRates.get(i));
        }
        working_space.create();
    }

    static Stream<Arguments> provider() {
        return Stream.of(
                Arguments.of(
                        Arrays.asList("January 25, 2024", "January 27, 2024", "January 26, 2024",
                                "February 26, 2024", "January 26, 2024", "March 26, 2024"),
                        Arrays.asList("Available period: Jan 25, 2024 to Jan 25, 2024",
                                "Available period: Jan 27, 2024 to Jan 27, 2024",
                                "Available period: Feb 26, 2024 to Mar 26, 2024"),
                        Arrays.asList("January 25, 2024", "January 27, 2024", "January 26, 2024",
                                "February 26, 2024", "March 26, 2024"),
                        Arrays.asList("A new price of 4.5 from Jan 25, 2024 to Jan 27, 2024",
                                "A new price of 4.5 from Feb 26, 2024 to Feb 26, 2024",
                                "A new price of 4.5 from Mar 26, 2024 to Mar 26, 2024"),
                        "4.5"
                )
        );
    }

}
