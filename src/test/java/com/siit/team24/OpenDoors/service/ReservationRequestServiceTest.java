package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.reservation.MakeReservationRequestDTO;
import com.siit.team24.OpenDoors.model.*;
import com.siit.team24.OpenDoors.model.enums.AccommodationType;
import com.siit.team24.OpenDoors.model.enums.NotificationType;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;
import com.siit.team24.OpenDoors.model.enums.UserRole;
import com.siit.team24.OpenDoors.repository.ReservationRequestRepository;
import com.siit.team24.OpenDoors.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.lang.Nullable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
public class ReservationRequestServiceTest {

    private static final long VALID_ACCOMMODATION_ID = 1L;
    private static final long VALID_GUEST_ID = 2L;
    private static final Timestamp VALID_START_DATE = Timestamp.valueOf("2024-07-01 00:00:00");
    private static final Timestamp VALID_END_DATE = Timestamp.valueOf("2024-07-10 00:00:00");
    private static final int VALID_NUMBER_OF_GUESTS = 1;
    private static final double VALID_TOTAL_PRICE = 200;

    private static final long INVALID_ACCOMMODATION_ID = 0L;
    private static final long INVALID_GUEST_ID = 0L;
    private static final long VALID_USER_NOT_GUEST_ID = 1L;

    @Mock
    private AccommodationService accommodationService;

    @Mock
    private UserService userService;

    @Mock
    private ReservationRequestRepository repo;

    @InjectMocks
    private ReservationRequestService reservationRequestService;

    private static Stream<Arguments> provideDateRanges() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return Stream.of(
                Arguments.of(Timestamp.valueOf("2024-10-15 00:00:00"), Timestamp.valueOf("2024-10-20 00:00:00"), false, "Accommodation is not available for the given date range"),
                Arguments.of(Timestamp.valueOf("2024-06-25 00:00:00"), Timestamp.valueOf("2024-07-05 00:00:00"), false, "Accommodation is not available for the given date range"),
                Arguments.of(Timestamp.valueOf("2024-09-25 00:00:00"), Timestamp.valueOf("2024-10-10 00:00:00"), false, "Accommodation is not available for the given date range"),
                Arguments.of(Timestamp.valueOf("2024-07-01 00:00:00"), Timestamp.valueOf("2024-10-01 00:00:00"), true, null)
        );
    }

    @Test
    @DisplayName("Should create reservation request successfully, accommodation isAutomatic=false")
    public void testCreateReservationRequest_AccommodationIsAutomaticFalse_Success() {
        // Arrange
        MakeReservationRequestDTO requestDTO = getValidRequestDTO();
        Accommodation accommodation = getValidIsNotAutomaticAccommodation();
        Guest guest = getValidGuest();
        ReservationRequest reservationRequest = getValidPendingRequest();

        when(accommodationService.findById(requestDTO.getAccommodationId())).thenReturn(accommodation);
        when(userService.findById(requestDTO.getGuestId())).thenReturn(guest);
        when(accommodationService.isAvailable(any(), any(), any())).thenReturn(true);
        when(accommodationService.calculateTotalPrice(any(), any(), any(), anyInt())).thenReturn(200.0);
        when(repo.save(any())).thenReturn(reservationRequest);

        // Act
        ReservationRequest result = reservationRequestService.save(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(ReservationRequestStatus.PENDING, result.getStatus());
        verify(accommodationService).findById(requestDTO.getAccommodationId());
        verify(userService).findById(requestDTO.getGuestId());
        verify(accommodationService).isAvailable(accommodation, requestDTO.getStartDate(), requestDTO.getEndDate());
        verify(accommodationService).calculateTotalPrice(accommodation, requestDTO.getStartDate(), requestDTO.getEndDate(), requestDTO.getNumberOfGuests());
        verify(repo).save(any(ReservationRequest.class));
    }

    @Test
    @DisplayName("Should create reservation request successfully, accommodation isAutomatic=true")
    public void testCreateReservationRequest_AccommodationIsAutomaticTrue_Success() {
        // Arrange
        MakeReservationRequestDTO requestDTO = getValidRequestDTO();
        Accommodation accommodation = getValidIsAutomaticAccommodation();
        Guest guest = getValidGuest();
        ReservationRequest reservationRequest = getValidConfirmedRequest();

        when(accommodationService.findById(requestDTO.getAccommodationId())).thenReturn(accommodation);
        when(userService.findById(requestDTO.getGuestId())).thenReturn(guest);
        when(accommodationService.isAvailable(any(), any(), any())).thenReturn(true);
        when(accommodationService.calculateTotalPrice(any(), any(), any(), anyInt())).thenReturn(200.0);
        when(repo.save(any())).thenReturn(reservationRequest);

        // Act
        ReservationRequest result = reservationRequestService.save(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(ReservationRequestStatus.CONFIRMED, result.getStatus());

        verify(accommodationService).findById(requestDTO.getAccommodationId());
        verify(userService).findById(requestDTO.getGuestId());
        verify(accommodationService).isAvailable(accommodation, requestDTO.getStartDate(), requestDTO.getEndDate());
        verify(accommodationService).calculateTotalPrice(accommodation, requestDTO.getStartDate(), requestDTO.getEndDate(), requestDTO.getNumberOfGuests());
        verify(accommodationService).removeDatesFromAccommodationAvailability(requestDTO.getAccommodationId(), reservationRequest.getDateRange());
        verify(repo).save(any(ReservationRequest.class));
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when accommodation not found")
    public void testCreateReservationRequest_AccommodationNotFound() {
        // Arrange
        MakeReservationRequestDTO requestDTO = getInvalidRequestDTOAccommodationId();
        when(accommodationService.findById(requestDTO.getAccommodationId())).thenReturn(null);

        // Act & Assert
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> reservationRequestService.save(requestDTO));
        assertEquals("Accommodation not found", thrown.getMessage());

        verify(accommodationService).findById(requestDTO.getAccommodationId());
        verifyNoInteractions(userService);
        verifyNoInteractions(repo);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when user not found")
    public void testCreateReservationRequest_UserNotFound() {
        // Arrange
        MakeReservationRequestDTO requestDTO = getInvalidRequestDTOUserId();
        Accommodation accommodation = getValidAccommodation();
        when(accommodationService.findById(requestDTO.getAccommodationId())).thenReturn(accommodation);
        when(userService.findById(requestDTO.getGuestId())).thenReturn(null);

        // Act & Assert
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> reservationRequestService.save(requestDTO));
        assertEquals("User not found", thrown.getMessage());

        verify(accommodationService).findById(requestDTO.getAccommodationId());
        verify(userService).findById(requestDTO.getGuestId());
        verifyNoInteractions(repo);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when user is not a guest")
    public void testCreateReservationRequest_UserIsNotGuest() {
        // Arrange
        MakeReservationRequestDTO requestDTO = getInvalidRequestDTOUserNotGuest();
        Accommodation accommodation = getValidAccommodation();
        User nonGuestUser = getValidNonGuestUser();
        when(accommodationService.findById(requestDTO.getAccommodationId())).thenReturn(accommodation);
        when(userService.findById(requestDTO.getGuestId())).thenReturn(nonGuestUser);

        // Act & Assert
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> reservationRequestService.save(requestDTO));
        assertEquals("User is not guest", thrown.getMessage());

        verify(accommodationService).findById(requestDTO.getAccommodationId());
        verify(userService).findById(requestDTO.getGuestId());
        verifyNoInteractions(repo);
    }

    @Test
    @DisplayName("Should throw ValidationException when start date is before today")
    public void testCreateReservationRequest_StartDateBeforeToday() {
        // Arrange
        MakeReservationRequestDTO requestDTO = getValidRequestDTO();
        requestDTO.setStartDate(new Timestamp(System.currentTimeMillis() - 100000));
        Accommodation accommodation = getValidAccommodation();
        User guestUser = getValidGuest();
        when(accommodationService.findById(requestDTO.getAccommodationId())).thenReturn(accommodation);
        when(userService.findById(requestDTO.getGuestId())).thenReturn(guestUser);

        // Act & Assert
        ValidationException thrown = assertThrows(ValidationException.class, () -> reservationRequestService.save(requestDTO));
        assertEquals("Start date cannot be before today", thrown.getMessage());

        verify(accommodationService).findById(requestDTO.getAccommodationId());
        verify(userService).findById(requestDTO.getGuestId());
        verifyNoInteractions(repo);
    }

    @Test
    @DisplayName("Should throw ValidationException when start date is after end date")
    public void testCreateReservationRequest_StartDateAfterEndDate() {
        // Arrange
        MakeReservationRequestDTO requestDTO = getValidRequestDTO();
        requestDTO.setStartDate(new Timestamp(System.currentTimeMillis() + 200000));
        requestDTO.setEndDate(new Timestamp(System.currentTimeMillis() + 100000));
        Accommodation accommodation = getValidAccommodation();
        User guestUser = getValidGuest();
        when(accommodationService.findById(requestDTO.getAccommodationId())).thenReturn(accommodation);
        when(userService.findById(requestDTO.getGuestId())).thenReturn(guestUser);

        // Act & Assert
        ValidationException thrown = assertThrows(ValidationException.class, () -> reservationRequestService.save(requestDTO));
        assertEquals("Start date cannot be after end date", thrown.getMessage());

        verify(accommodationService).findById(requestDTO.getAccommodationId());
        verify(userService).findById(requestDTO.getGuestId());
        verifyNoInteractions(repo);
    }

    @Test
    @DisplayName("Should throw ValidationException when number of guests is less than minimum")
    public void testCreateReservationRequest_NumberOfGuestsLessThanMinimum() {
        // Arrange
        MakeReservationRequestDTO requestDTO = getValidRequestDTO();
        requestDTO.setNumberOfGuests(0);
        Accommodation accommodation = getValidAccommodation();
        User guestUser = getValidGuest();
        when(accommodationService.findById(requestDTO.getAccommodationId())).thenReturn(accommodation);
        when(userService.findById(requestDTO.getGuestId())).thenReturn(guestUser);

        // Act & Assert
        ValidationException thrown = assertThrows(ValidationException.class, () -> reservationRequestService.save(requestDTO));
        assertEquals("Number of guests must be at least " + accommodation.getMinGuests() + " for this accommodation", thrown.getMessage());

        verify(accommodationService).findById(requestDTO.getAccommodationId());
        verify(userService).findById(requestDTO.getGuestId());
        verifyNoInteractions(repo);
    }

    @Test
    @DisplayName("Should throw ValidationException when number of guests is more than maximum")
    public void testCreateReservationRequest_NumberOfGuestsMoreThanMaximum() {
        // Arrange
        MakeReservationRequestDTO requestDTO = getValidRequestDTO();
        requestDTO.setNumberOfGuests(2);
        Accommodation accommodation = getValidAccommodation();
        User guestUser = getValidGuest();
        when(accommodationService.findById(requestDTO.getAccommodationId())).thenReturn(accommodation);
        when(userService.findById(requestDTO.getGuestId())).thenReturn(guestUser);

        // Act & Assert
        ValidationException thrown = assertThrows(ValidationException.class, () -> reservationRequestService.save(requestDTO));
        assertEquals("Number of guests must be at most " + accommodation.getMaxGuests() + " for this accommodation", thrown.getMessage());

        verify(accommodationService).findById(requestDTO.getAccommodationId());
        verify(userService).findById(requestDTO.getGuestId());
        verifyNoInteractions(repo);
    }

    @ParameterizedTest
    @MethodSource("provideDateRanges")
    @DisplayName("Test accommodation availability for given date ranges")
    public void testCreateReservationRequest_AccommodationAvailability(Timestamp startDate, Timestamp endDate, boolean isAvailable) {
        // Arrange
        MakeReservationRequestDTO requestDTO = getValidRequestDTO();
        requestDTO.setStartDate(startDate);
        requestDTO.setEndDate(endDate);
        Accommodation accommodation = getValidAccommodation();
        Guest guest = getValidGuest();

        when(accommodationService.findById(requestDTO.getAccommodationId())).thenReturn(accommodation);
        when(userService.findById(requestDTO.getGuestId())).thenReturn(guest);
        when(accommodationService.isAvailable(accommodation, requestDTO.getStartDate(), requestDTO.getEndDate())).thenReturn(isAvailable);

        if (isAvailable) {
            ReservationRequest reservationRequest = getValidPendingRequest();
            when(accommodationService.calculateTotalPrice(accommodation, requestDTO.getStartDate(), requestDTO.getEndDate(),
                    requestDTO.getNumberOfGuests())).thenReturn(200.0);
            when(repo.save(any())).thenReturn(reservationRequest);
        }

        // Act & Assert
        if (isAvailable) {
            ReservationRequest result = reservationRequestService.save(requestDTO);
            assertNotNull(result);
            verify(repo).save(any(ReservationRequest.class));
        } else {
            ValidationException thrown = assertThrows(ValidationException.class, () -> reservationRequestService.save(requestDTO));
            assertEquals("Accommodation is not available for the given date range", thrown.getMessage());
            verifyNoInteractions(repo);
        }

        verify(accommodationService).findById(requestDTO.getAccommodationId());
        verify(userService).findById(requestDTO.getGuestId());
        verify(accommodationService).isAvailable(accommodation, requestDTO.getStartDate(), requestDTO.getEndDate());
    }

    public MakeReservationRequestDTO getValidRequestDTO() {
        MakeReservationRequestDTO requestDTO = new MakeReservationRequestDTO();
        requestDTO.setAccommodationId(VALID_ACCOMMODATION_ID);
        requestDTO.setGuestId(VALID_GUEST_ID);
        requestDTO.setStartDate(VALID_START_DATE);
        requestDTO.setEndDate(VALID_END_DATE);
        requestDTO.setNumberOfGuests(VALID_NUMBER_OF_GUESTS);
        requestDTO.setTotalPrice(VALID_TOTAL_PRICE);
        return requestDTO;
    }

    public MakeReservationRequestDTO getInvalidRequestDTOAccommodationId() {
        MakeReservationRequestDTO requestDTO = new MakeReservationRequestDTO();
        requestDTO.setAccommodationId(INVALID_ACCOMMODATION_ID);
        requestDTO.setGuestId(VALID_GUEST_ID);
        requestDTO.setStartDate(VALID_START_DATE);
        requestDTO.setEndDate(VALID_END_DATE);
        requestDTO.setNumberOfGuests(VALID_NUMBER_OF_GUESTS);
        requestDTO.setTotalPrice(VALID_TOTAL_PRICE);
        return requestDTO;
    }

    public MakeReservationRequestDTO getInvalidRequestDTOUserId() {
        MakeReservationRequestDTO requestDTO = new MakeReservationRequestDTO();
        requestDTO.setAccommodationId(VALID_ACCOMMODATION_ID);
        requestDTO.setGuestId(INVALID_GUEST_ID);
        requestDTO.setStartDate(VALID_START_DATE);
        requestDTO.setEndDate(VALID_END_DATE);
        requestDTO.setNumberOfGuests(VALID_NUMBER_OF_GUESTS);
        requestDTO.setTotalPrice(VALID_TOTAL_PRICE);
        return requestDTO;
    }

    public MakeReservationRequestDTO getInvalidRequestDTOUserNotGuest() {
        MakeReservationRequestDTO requestDTO = new MakeReservationRequestDTO();
        requestDTO.setAccommodationId(VALID_ACCOMMODATION_ID);
        requestDTO.setGuestId(VALID_USER_NOT_GUEST_ID);
        requestDTO.setStartDate(VALID_START_DATE);
        requestDTO.setEndDate(VALID_END_DATE);
        requestDTO.setNumberOfGuests(VALID_NUMBER_OF_GUESTS);
        requestDTO.setTotalPrice(VALID_TOTAL_PRICE);
        return requestDTO;
    }

    // kad nije bitan isAutomatic
    public Accommodation getValidAccommodation() {
        Host host = new Host(1L, "vaske@test.test", "123", Timestamp.valueOf("2024-07-01 00:00:00"), UserRole.ROLE_HOST,
                "vasilije", "markovic", "123123", null, null, true, null);
        List<DateRange> availability = new ArrayList<>();
        availability.add(new DateRange(Timestamp.valueOf("2024-07-01 00:00:00"), Timestamp.valueOf("2024-10-01 00:00:00")));
        List<SeasonalRate> seasonalRates = new ArrayList<>();
        Accommodation accommodation = new Accommodation(1L, "cao", "opis", "lokacija", new ArrayList<>(), null,
                1, 1, availability, AccommodationType.APARTMENT, 1, true, 0, host, new ArrayList<>(),
                null,10, false);
        return accommodation;
    }

    public Accommodation getValidIsNotAutomaticAccommodation() {
        Host host = new Host(1L, "vaske@test.test", "123", Timestamp.valueOf("2024-07-01 00:00:00"), UserRole.ROLE_HOST,
                "vasilije", "markovic", "123123", null, null, true, null);
        List<DateRange> availability = new ArrayList<>();
        availability.add(new DateRange(Timestamp.valueOf("2024-07-01 00:00:00"), Timestamp.valueOf("2024-10-01 00:00:00")));
        List<SeasonalRate> seasonalRates = new ArrayList<>();
        Accommodation accommodation = new Accommodation(1L, "cao", "opis", "lokacija", new ArrayList<>(), null,
                1, 1, availability, AccommodationType.APARTMENT, 1, true, 0, host, new ArrayList<>(),
                null,10, false);
        return accommodation;
    }

    public Accommodation getValidIsAutomaticAccommodation() {
        Host host = new Host(1L, "vaske@test.test", "123", Timestamp.valueOf("2024-07-01 00:00:00"), UserRole.ROLE_HOST,
                "vasilije", "markovic", "123123", null, null, true, null);
        List<DateRange> availability = new ArrayList<>();
        availability.add(new DateRange(Timestamp.valueOf("2024-07-01 00:00:00"), Timestamp.valueOf("2024-10-01 00:00:00")));
        List<SeasonalRate> seasonalRates = new ArrayList<>();
        Accommodation accommodation = new Accommodation(1L, "cao", "opis", "lokacija", new ArrayList<>(), null,
                1, 1, availability, AccommodationType.APARTMENT, 1, true, 0, host, new ArrayList<>(),
                null,10, true);
        return accommodation;
    }

    public Guest getValidGuest() {
        Guest guest = new Guest(2L, "aaa@gmail.com", "123", Timestamp.valueOf("2024-06-01 00:00:00"), UserRole.ROLE_GUEST,
                "ime", "prezime", "12344567", null, null, true, new HashSet<>(),
                new ArrayList<>());
        return guest;
    }

    public User getValidNonGuestUser() {
        Host host = new Host(1L, "vaske@test.test", "123", Timestamp.valueOf("2024-07-01 00:00:00"), UserRole.ROLE_HOST,
                "vasilije", "markovic", "123123", null, null, true, null);
        return host;
    }

    public ReservationRequest getValidPendingRequest() {
        ReservationRequest request = new ReservationRequest(1L,getValidGuest(),getValidIsNotAutomaticAccommodation(),
                new DateRange(VALID_START_DATE, VALID_END_DATE),1,0,
                ReservationRequestStatus.PENDING, Timestamp.valueOf("2024-06-17 00:00:00"));
        return request;
    }

    public ReservationRequest getValidConfirmedRequest() {
        ReservationRequest request = new ReservationRequest(1L,getValidGuest(),getValidIsAutomaticAccommodation(),
                new DateRange(VALID_START_DATE, VALID_END_DATE),1,0,
                ReservationRequestStatus.CONFIRMED, Timestamp.valueOf("2024-06-17 00:00:00"));
        return request;
    }
}
