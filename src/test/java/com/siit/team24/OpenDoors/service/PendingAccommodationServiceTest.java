package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationWholeEditedDTO;
import com.siit.team24.OpenDoors.exception.InvalidAvailabilityException;
import com.siit.team24.OpenDoors.exception.InvalidDeadlineException;
import com.siit.team24.OpenDoors.exception.InvalidSeasonalRatesException;
import com.siit.team24.OpenDoors.model.*;
import com.siit.team24.OpenDoors.model.enums.UserRole;
import com.siit.team24.OpenDoors.repository.PendingAccommodationRepository;
import com.siit.team24.OpenDoors.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith({MockitoExtension.class, SpringExtension.class})
public class PendingAccommodationServiceTest {

    private static final int VALID_DEADLINE = 1;
    private static final int INVALID_DEADLINE = -1;
    private static final double DEFAULT_PRICE = 10.0;
    private static final double VALID_SEASONAL_RATE_PRICE_1 = 20.0;
    private static final double VALID_SEASONAL_RATE_PRICE_2 = 30.0;
    private static final double INVALID_SEASONAL_RATE_PRICE = -10.0;
    private static final String HOST_USERNAME = "host@host.host";

    @Mock
    private PendingAccommodationRepository repo;

    @Mock
    private AccommodationService accommodationService;

    @Mock
    private UserService userService;

    @Mock
    private ImageService imageService;

    @Mock
    private ReservationRequestService reservationRequestService;

    @Captor
    private ArgumentCaptor<PendingAccommodation> pendingAccommodationArgumentCaptor;


    @Captor
    private ArgumentCaptor<String> usernameArgumentCaptor;

    @InjectMocks
    private PendingAccommodationService pendingAccommodationService;

    // Deadline test

    @Test
    @DisplayName("Should throw Exception when deadline is negative.")
    public void shouldNotHaveNegativeDeadline() {
        PendingAccommodationWholeEditedDTO dto = new PendingAccommodationWholeEditedDTO(
            null, "", "", "", new ArrayList<>(), null, 1, 1, "",
                new ArrayList<>(), DEFAULT_PRICE, false, new ArrayList<>(), "", "", "", 1,
                INVALID_DEADLINE, false, HOST_USERNAME, null, null
        );
        assertThrows(InvalidDeadlineException.class, () -> pendingAccommodationService.save(dto));
        verifyNoInteractions(repo);
        verifyNoInteractions(userService);
        verifyNoInteractions(reservationRequestService);
        verifyNoInteractions(accommodationService);
        verifyNoInteractions(imageService);
    }

    // Availability tests

    @ParameterizedTest
    @MethodSource(value = "overlappingDates")
    @DisplayName("Should throw Exception when available periods overlap.")
    public void shouldAvailablePeriodsNotOverlap(int startDate1, int endDate1, int startDate2, int endDate2) {
        PendingAccommodationWholeEditedDTO dto = new PendingAccommodationWholeEditedDTO(
                null, "", "", "", new ArrayList<>(), null, 1, 1, "",
                new ArrayList<>(), DEFAULT_PRICE, false, new ArrayList<>(), "", "", "", 1,
                VALID_DEADLINE, false, HOST_USERNAME, null, null
        );
        dto.getAvailability().add(new DateRange(generateMidnightDate(startDate1), generateMidnightDate(endDate1)));
        dto.getAvailability().add(new DateRange(generateMidnightDate(startDate2), generateMidnightDate(endDate2)));
        assertThrows(InvalidAvailabilityException.class, () -> pendingAccommodationService.save(dto));
        verifyNoInteractions(repo);
        verifyNoInteractions(userService);
        verifyNoInteractions(reservationRequestService);
        verifyNoInteractions(accommodationService);
        verifyNoInteractions(imageService);
    }

    @Test
    @DisplayName("Should throw Exception when an available period has start date after end date.")
    public void shouldAvailablePeriodsNotStartAfterTheyEnd() {
        PendingAccommodationWholeEditedDTO dto = new PendingAccommodationWholeEditedDTO(
                null, "", "", "", new ArrayList<>(), null, 1, 1, "",
                new ArrayList<>(), DEFAULT_PRICE, false, new ArrayList<>(), "", "", "", 1,
                VALID_DEADLINE, false, HOST_USERNAME, null, null
        );
        dto.getAvailability().add(new DateRange(generateMidnightDate(2), generateMidnightDate(1)));
        assertThrows(InvalidAvailabilityException.class, () -> pendingAccommodationService.save(dto));
        verifyNoInteractions(repo);
        verifyNoInteractions(userService);
        verifyNoInteractions(reservationRequestService);
        verifyNoInteractions(accommodationService);
        verifyNoInteractions(imageService);
    }

    @ParameterizedTest
    @MethodSource(value = "nonMidnightDates")
    @DisplayName("Should throw Exception when the start or end of an available period is not at midnight.")
    public void shouldAvailablePeriodsStartAndEndAtMidnight(Timestamp startDate, Timestamp endDate) {
        PendingAccommodationWholeEditedDTO dto = new PendingAccommodationWholeEditedDTO(
                null, "", "", "", new ArrayList<>(), null, 1, 1, "",
                new ArrayList<>(), DEFAULT_PRICE, false, new ArrayList<>(), "", "", "", 1,
                VALID_DEADLINE, false, HOST_USERNAME, null, null
        );
        dto.getAvailability().add(new DateRange(startDate, endDate));
        assertThrows(InvalidAvailabilityException.class, () -> pendingAccommodationService.save(dto));
        verifyNoInteractions(repo);
        verifyNoInteractions(userService);
        verifyNoInteractions(reservationRequestService);
        verifyNoInteractions(accommodationService);
        verifyNoInteractions(imageService);
    }

    @Test
    @DisplayName("Should throw Exception when two available periods are consecutive.")
    public void shouldNotHaveConsecutiveAvailablePeriods() {
        PendingAccommodationWholeEditedDTO dto = new PendingAccommodationWholeEditedDTO(
                null, "", "", "", new ArrayList<>(), null, 1, 1, "",
                new ArrayList<>(), DEFAULT_PRICE, false, new ArrayList<>(), "", "", "", 1,
                VALID_DEADLINE, false, HOST_USERNAME, null, null
        );
        dto.getAvailability().add(new DateRange(generateMidnightDate(1), generateMidnightDate(3)));
        dto.getAvailability().add(new DateRange(generateMidnightDate(4), generateMidnightDate(6)));
        assertThrows(InvalidAvailabilityException.class, () -> pendingAccommodationService.save(dto));
        verifyNoInteractions(repo);
        verifyNoInteractions(userService);
        verifyNoInteractions(reservationRequestService);
        verifyNoInteractions(accommodationService);
        verifyNoInteractions(imageService);
    }

    // Seasonal rate tests

    @ParameterizedTest
    @MethodSource(value = "overlappingDates")
    @DisplayName("Should throw Exception when when seasonal rate periods overlap.")
    public void shouldSeasonalRatePeriodsNotOverlap(int startDate1, int endDate1, int startDate2, int endDate2) {
        PendingAccommodationWholeEditedDTO dto = new PendingAccommodationWholeEditedDTO(
                null, "", "", "", new ArrayList<>(), null, 1, 1, "",
                new ArrayList<>(), DEFAULT_PRICE, false, new ArrayList<>(), "", "", "", 1,
                VALID_DEADLINE, false, HOST_USERNAME, null, null
        );
        dto.getSeasonalRates().add(new SeasonalRate(VALID_SEASONAL_RATE_PRICE_1, new DateRange(generateMidnightDate(startDate1), generateMidnightDate(endDate1))));
        dto.getSeasonalRates().add(new SeasonalRate(VALID_SEASONAL_RATE_PRICE_2, new DateRange(generateMidnightDate(startDate2), generateMidnightDate(endDate2))));
        assertThrows(InvalidSeasonalRatesException.class, () -> pendingAccommodationService.save(dto));
        verifyNoInteractions(repo);
        verifyNoInteractions(userService);
        verifyNoInteractions(reservationRequestService);
        verifyNoInteractions(accommodationService);
        verifyNoInteractions(imageService);
    }

    @Test
    @DisplayName("Should throw Exception when a seasonal rate period has start date after end date.")
    public void shouldSeasonalRatePeriodsNotStartAfterTheyEnd() {
        PendingAccommodationWholeEditedDTO dto = new PendingAccommodationWholeEditedDTO(
                null, "", "", "", new ArrayList<>(), null, 1, 1, "",
                new ArrayList<>(), DEFAULT_PRICE, false, new ArrayList<>(), "", "", "", 1,
                VALID_DEADLINE, false, HOST_USERNAME, null, null
        );
        dto.getSeasonalRates().add(new SeasonalRate(VALID_SEASONAL_RATE_PRICE_1, new DateRange(generateMidnightDate(2), generateMidnightDate(1))));
        assertThrows(InvalidSeasonalRatesException.class, () -> pendingAccommodationService.save(dto));
        verifyNoInteractions(repo);
        verifyNoInteractions(userService);
        verifyNoInteractions(reservationRequestService);
        verifyNoInteractions(accommodationService);
        verifyNoInteractions(imageService);
    }

    @ParameterizedTest
    @MethodSource("nonMidnightDates")
    @DisplayName("Should throw Exception when the start or end of a seasonal rate period is not at midnight.")
    public void shouldSeasonalRatePeriodsStartAndEndAtMidnight(Timestamp startDate, Timestamp endDate) {
        PendingAccommodationWholeEditedDTO dto = new PendingAccommodationWholeEditedDTO(
                null, "", "", "", new ArrayList<>(), null, 1, 1, "",
                new ArrayList<>(), DEFAULT_PRICE, false, new ArrayList<>(), "", "", "", 1,
                VALID_DEADLINE, false, HOST_USERNAME, null, null
        );
        dto.getSeasonalRates().add(new SeasonalRate(VALID_SEASONAL_RATE_PRICE_1, new DateRange(startDate, endDate)));
        assertThrows(InvalidSeasonalRatesException.class, () -> pendingAccommodationService.save(dto));
        verifyNoInteractions(repo);
        verifyNoInteractions(userService);
        verifyNoInteractions(reservationRequestService);
        verifyNoInteractions(accommodationService);
        verifyNoInteractions(imageService);
    }

    @Test
    @DisplayName("Should throw Exception when two consecutive seasonal rates have equal price values.")
    public void shouldConsecutiveSeasonalRatesNotHaveEqualPrices() {
        PendingAccommodationWholeEditedDTO dto = new PendingAccommodationWholeEditedDTO(
                null, "", "", "", new ArrayList<>(), null, 1, 1, "",
                new ArrayList<>(), DEFAULT_PRICE, false, new ArrayList<>(), "", "", "", 1,
                VALID_DEADLINE, false, HOST_USERNAME, null, null
        );
        dto.getSeasonalRates().add(new SeasonalRate(VALID_SEASONAL_RATE_PRICE_1, new DateRange(generateMidnightDate(1), generateMidnightDate(3))));
        dto.getSeasonalRates().add(new SeasonalRate(VALID_SEASONAL_RATE_PRICE_1, new DateRange(generateMidnightDate(4), generateMidnightDate(6))));
        assertThrows(InvalidSeasonalRatesException.class, () -> pendingAccommodationService.save(dto));
        verifyNoInteractions(repo);
        verifyNoInteractions(userService);
        verifyNoInteractions(reservationRequestService);
        verifyNoInteractions(accommodationService);
        verifyNoInteractions(imageService);
    }

    @Test
    @DisplayName("Should throw Exception when seasonal rate price is negative.")
    public void shouldNotHaveNegativeSeasonalRatePrice() {
        PendingAccommodationWholeEditedDTO dto = new PendingAccommodationWholeEditedDTO(
                null, "", "", "", new ArrayList<>(), null, 1, 1, "",
                new ArrayList<>(), DEFAULT_PRICE, false, new ArrayList<>(), "", "", "", 1,
                VALID_DEADLINE, false, HOST_USERNAME, null, null
        );
        dto.getSeasonalRates().add(new SeasonalRate(INVALID_SEASONAL_RATE_PRICE, new DateRange(generateMidnightDate(1), generateMidnightDate(3))));
        assertThrows(InvalidSeasonalRatesException.class, () -> pendingAccommodationService.save(dto));
        verifyNoInteractions(repo);
        verifyNoInteractions(userService);
        verifyNoInteractions(reservationRequestService);
        verifyNoInteractions(accommodationService);
        verifyNoInteractions(imageService);
    }

    @Test
    @DisplayName("Should throw Exception when seasonal rate price is equal to default price.")
    public void shouldSeasonalRateAndDefaultPricesNotMatch() {
        PendingAccommodationWholeEditedDTO dto = new PendingAccommodationWholeEditedDTO(
                null, "", "", "", new ArrayList<>(), null, 1, 1, "",
                new ArrayList<>(), DEFAULT_PRICE, false, new ArrayList<>(), "", "", "", 1,
                VALID_DEADLINE, false, HOST_USERNAME, null, null
        );
        dto.getSeasonalRates().add(new SeasonalRate(DEFAULT_PRICE, new DateRange(generateMidnightDate(1), generateMidnightDate(3))));
        assertThrows(InvalidSeasonalRatesException.class, () -> pendingAccommodationService.save(dto));
        verifyNoInteractions(repo);
        verifyNoInteractions(userService);
        verifyNoInteractions(reservationRequestService);
        verifyNoInteractions(accommodationService);
        verifyNoInteractions(imageService);
    }

    // Successful save test

    @Test
    @DisplayName("Test should save a new pending accommodation.")
    public void shouldSavePendingAccommodation() throws IOException {
        PendingAccommodationWholeEditedDTO dto = new PendingAccommodationWholeEditedDTO(
                null, "", "", "", new ArrayList<>(), null, 1, 1, "",
                new ArrayList<>(), DEFAULT_PRICE, false, new ArrayList<>(), "", "", "", 1,
                VALID_DEADLINE, false, HOST_USERNAME, null, null
        );

        dto.getSeasonalRates().add(new SeasonalRate(VALID_SEASONAL_RATE_PRICE_1, new DateRange(generateMidnightDate(1), generateMidnightDate(3))));
        dto.getSeasonalRates().add(new SeasonalRate(VALID_SEASONAL_RATE_PRICE_2, new DateRange(generateMidnightDate(5), generateMidnightDate(6))));
        dto.getAvailability().add(new DateRange(generateMidnightDate(1), generateMidnightDate(3)));
        dto.getAvailability().add(new DateRange(generateMidnightDate(5), generateMidnightDate(7)));

        PendingAccommodation accommodation = new PendingAccommodation();
        accommodation.setSimpleValues(dto);

        Host host = new Host(null, HOST_USERNAME, "", null, UserRole.ROLE_HOST,
                "", "", "1234567890", null, null, true, new ArrayList<>());

        when(repo.save(any(PendingAccommodation.class))).thenReturn(accommodation);
        when(userService.findByUsername(HOST_USERNAME)).thenReturn(host);

        PendingAccommodation pendingAccommodation = pendingAccommodationService.save(dto);
        verify(repo, times(2)).save(pendingAccommodationArgumentCaptor.capture());
        verify(userService, times(1)).findByUsername(usernameArgumentCaptor.capture());
        verifyNoInteractions(reservationRequestService);
        verifyNoInteractions(accommodationService);
        verifyNoInteractions(imageService);
        assertNotNull(pendingAccommodation);
    }

    // Helper methods

    // Generates a timestamp which represents the n-th day from today, at 00:00:00:00
    private static Timestamp generateMidnightDate(int n) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis()
                + n * 1000 * 60 * 60 * 24);
        timestamp.setNanos(0);
        timestamp.setSeconds(0);
        timestamp.setMinutes(0);
        timestamp.setHours(0);
        return timestamp;
    }

    // Generates a timestamp which represents the n-th day from today, at the same time as now
    private static Timestamp generateNonMidnightDate(int n) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis()
                + n * 1000 * 60 * 60 * 24);
        return timestamp;
    }

    // Data sources

    static Stream<Arguments> overlappingDates() {
        return Stream.of(
                arguments(1, 3, 1, 2),
                arguments(1, 4, 2, 3),
                arguments(1, 3, 2, 3),
                arguments(1, 2, 2, 3),
                arguments(1, 3, 2, 4),
                arguments(2, 4, 1, 3),
                arguments(2, 3, 1, 2),
                arguments(1, 3, 1, 3),
                arguments(2, 3, 1, 4)

        );
    }

    static Stream<Arguments> nonMidnightDates() {
        return Stream.of(
                arguments(generateNonMidnightDate(1), generateMidnightDate(2)),
                arguments(generateMidnightDate(1), generateNonMidnightDate(2))
        );
    }
}
