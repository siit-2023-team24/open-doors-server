package com.siit.team24.OpenDoors.selenium;

import com.siit.team24.OpenDoors.selenium.pages.HomePage;
import com.siit.team24.OpenDoors.selenium.pages.LoginPage;
import com.siit.team24.OpenDoors.selenium.pages.ReservationsPage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class CancelReservationTest extends TestBase {

    private final String USERNAME = "milica@test.test";
    private final String PASSWORD = "12345";

    @Test
    public void testCancelReservation() {
        HomePage homePage = new HomePage(driver);
        homePage.clickOnLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);

        assertTrue(homePage.isPageOpened());

        homePage.clickReservations();

        ReservationsPage reservationsPage = new ReservationsPage(driver);

        assertTrue(reservationsPage.isPageOpened());

        reservationsPage.clickCancel(1L, "Status: CANCELLED");

        reservationsPage.clickCancel(3L, "Status: CONFIRMED");

    }



}
