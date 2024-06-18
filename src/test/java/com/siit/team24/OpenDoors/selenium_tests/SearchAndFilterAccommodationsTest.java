package com.siit.team24.OpenDoors.selenium_tests;

import com.siit.team24.OpenDoors.model.*;
import com.siit.team24.OpenDoors.model.enums.*;
import com.siit.team24.OpenDoors.selenium_pages.HomePage;
import com.siit.team24.OpenDoors.service.AccommodationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SearchAndFilterAccommodationsTest extends TestBase{
    @Autowired
    public AccommodationService accommodationService;

    HomePage homePage;

    private static final String USERNAME = "milica@test.test";
    private static final String PASSWORD = "12345678";
    private static final long VALID_ACCOMMODATION_ID = 1L;
    private static final long INVALID_ACCOMMODATION_ID = 0L;
    private static final long VALID_GUEST_ID = 2L;
    private static final Timestamp VALID_START_DATE = Timestamp.valueOf("2024-07-01 00:00:00");
    private static final Timestamp VALID_END_DATE = Timestamp.valueOf("2024-07-10 00:00:00");
    private static final int VALID_NUMBER_OF_GUESTS = 1;
    private static final double VALID_TOTAL_PRICE = 200;
    private static List<Accommodation> accommodations = new ArrayList<>();

//    public void insertTestAccommodations(){
//        List<Amenity> amenities1 = Arrays.asList(
//                Amenity.SPA, Amenity.GYM, Amenity.CHILDREN_PLAY_AREA, Amenity.AIR_CONDITIONING
//        );
//
//        List<Amenity> amenities2 = Arrays.asList(
//                Amenity.SPA, Amenity.AIR_CONDITIONING
//        );
//
//        List<Amenity> amenities3 = Arrays.asList(
//                Amenity.AIR_CONDITIONING
//        );
//
//        List<DateRange> availability = Arrays.asList(
//                new DateRange(Timestamp.valueOf("2024-06-01 00:00:00"), Timestamp.valueOf("2024-06-30 00:00:00"))
//        );
//
//        List<DateRange> availability2 = Arrays.asList(
//                new DateRange(Timestamp.valueOf("2024-08-01 00:00:00"), Timestamp.valueOf("2024-08-30 00:00:00"))
//        );
//
//        List<DateRange> availability3 = Arrays.asList(
//                new DateRange(Timestamp.valueOf("2024-10-01 00:00:00"), Timestamp.valueOf("2024-10-30 00:00:00"))
//        );
//
//        Address address1 = new Address("aaaaaa", 1, "Novi Sad", Country.ALBANIA);
//        Address address2 = new Address("aaaaaa", 1, "Novi Sad", Country.ALBANIA);
//        Address address3 = new Address("aaaaaa", 1, "Novi Sad", Country.ALBANIA);
//
//        Host host = new Host(1L, "vaske@test.test", "123", Timestamp.valueOf("2024-07-01 00:00:00"), UserRole.ROLE_HOST,
//                "vasilije", "markovic", "123123", null, null, true, null);
//
//        Accommodation accommodation1 = new Accommodation(4L, "cao1", "opis", "lokacija1", amenities1, null,
//                1, 1, availability, AccommodationType.APARTMENT, 1, true, 0, host, new ArrayList<>(),
//                address1,10, false);
//
//        Accommodation accommodation2 = new Accommodation(5L, "cao2", "opis2", "lokacija2", amenities2, null,
//                1, 1, availability2, AccommodationType.APARTMENT, 1, true, 0, host, null,
//                address2,10, false);
//
//        Accommodation accommodation3 = new Accommodation(6L, "cao3", "opis3", "lokacija3", amenities3, null,
//                1, 1, availability3, AccommodationType.APARTMENT, 1, true, 0, host, null,
//                address3,10, false);
//        Accommodation accommodationInvalidLocation = new Accommodation();
//        //accommodationService.save(accommodation1);
////        accommodationService.save(accommodation2);
////        accommodationService.save(accommodation3);
//
//        accommodations.add(accommodation1);
//        accommodations.add(accommodation2);
//        accommodations.add(accommodation3);
//    }
    
    
    @BeforeAll
    public void beforeAll(){
        //insertTestAccommodations();
    }

    public static Stream<Arguments> ProvideNoResultSearchTerms() {
        return Stream.of(
                Arguments.of("Novi Sad, afghanistan"),
                Arguments.of("Zrenjanin, afghanistan"),
                Arguments.of("Beograd, afghanistan")
        );
    }

    public static Stream<Arguments> ProvideLocationSearchTerms() {
        return Stream.of(
                Arguments.of("idontexist", 0),
                Arguments.of("city1, afghanistan", 1),
                Arguments.of("city2, afghanistan", 2),
                Arguments.of("city3, afghanistan", 1)
        );
    }

    public static Stream<Arguments> ProvideDatesSearchTerms() {
        return Stream.of(
                // Test case with 0 results
                Arguments.of("2024-06-20", "2024-06-24", 0),
                // Test case with 1 result (only accommodation 1 is available in this range)
                Arguments.of("2024-10-04", "2024-10-10", 1),
                // Test case with 2 results (accommodation 2 and 3 are available in this range)
                Arguments.of("2024-07-01", "2024-07-07", 2),
                // Test case with 3 results (all accommodations are available within this date range)
                Arguments.of("2024-06-06", "2024-06-12", 4)
        );
    }

    public static Stream<Arguments> ProvideNumberOfGuestsSearchTerms() {
        return Stream.of(
                Arguments.of("8", 0),
                Arguments.of("5", 2),
                Arguments.of("3", 3),
                Arguments.of("1", 4)
        );
    }

    public static Stream<Arguments> ProvideAccommodationTypeFilterTerms() {
        return Stream.of(
                Arguments.of(Arrays.asList(AccommodationType.HOTEL.ordinal()), 0),
                Arguments.of(Arrays.asList(AccommodationType.APARTMENT.ordinal()), 1),
                Arguments.of(Arrays.asList(AccommodationType.COTTAGE.ordinal()), 2),
                Arguments.of(Arrays.asList(AccommodationType.VILLA.ordinal()), 1),
                Arguments.of(Arrays.asList(AccommodationType.ROOM.ordinal()), 0)
        );
    }

    public static Stream<Arguments> ProvideAmenitiesFilterTerms() {
        return Stream.of(
                Arguments.of(Arrays.asList(1,2), 3), // Selecting amenities at index 0 and 1
                Arguments.of(Arrays.asList(1), 4),    // Selecting amenity at index 2
                Arguments.of(Arrays.asList(0, 2, 3), 0) // Selecting amenities at index 0, 2, and 3
        );
    }


    @Test
    public void searchWithNoResultsTest(){

    }

    @BeforeEach()
    public void setUp() {
        this.homePage = new HomePage(driver);
//        home.clickOnLogin();
//        LoginPage loginPage = new LoginPage(driver);
//        loginPage.login(USERNAME, PASSWORD);
    }




//    @ParameterizedTest
//    @MethodSource("ProvideNoResultSearchTerms")
//    public void testNoResultSearch(String location){
//        int expectedResultSize = 0;
//        homePage.searchAccommodations(location, "", "", "", expectedResultSize);
//        homePage.verifyNumberOfResults(expectedResultSize);
//    }

    @ParameterizedTest
    @MethodSource("ProvideLocationSearchTerms")
    public void searchByLocationOnly(String location, int numOfRes){
        homePage.searchAccommodations(location, "", "", "", numOfRes);
        homePage.verifyNumberOfResults(numOfRes);
        if(numOfRes != 0)
            homePage.assertTitles(location);
    }

    @ParameterizedTest
    @MethodSource("ProvideDatesSearchTerms")
    public void searchByDatesOnly(String checkInDate, String checkOutDate, int numOfRes) {
        homePage.searchAccommodations("", checkInDate, checkOutDate, "", numOfRes);
        homePage.verifyNumberOfResults(numOfRes);
    }

    @ParameterizedTest
    @MethodSource("ProvideNumberOfGuestsSearchTerms")
    public void searchByNumberOfGuestsOnly(String numberOfGuests, int numOfRes) {
        homePage.searchAccommodations("", "", "", numberOfGuests, numOfRes);
        homePage.verifyNumberOfResults(numOfRes);
    }

    @ParameterizedTest
    @MethodSource("ProvideAccommodationTypeFilterTerms")
    public void filterByAccommodationTypesOnly(List<Integer> accommodationTypes, int numOfRes) {
        homePage.filterAccommodations("", "", "", "", accommodationTypes, "", "", null, numOfRes);
        homePage.verifyNumberOfResults(numOfRes);
    }

    @ParameterizedTest
    @MethodSource("ProvideAmenitiesFilterTerms")
    public void filterByAmenitiesOnly(List<Integer> amenities, int numOfRes) {
        homePage.filterAccommodations("", "", "", "", null, "", "", amenities, numOfRes);
        homePage.verifyNumberOfResults(numOfRes);
    }
}
