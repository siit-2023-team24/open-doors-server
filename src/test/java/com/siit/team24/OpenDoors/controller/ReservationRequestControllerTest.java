package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.reservation.MakeReservationRequestDTO;
import com.siit.team24.OpenDoors.model.*;
import com.siit.team24.OpenDoors.model.enums.Amenity;
import com.siit.team24.OpenDoors.model.enums.UserRole;
import com.siit.team24.OpenDoors.service.AccommodationService;
import com.siit.team24.OpenDoors.service.ReservationRequestService;
import com.siit.team24.OpenDoors.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest
class ReservationRequestControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @InjectMocks
    private ReservationRequestController reservationRequestController;
    @Mock
    private AccommodationService accommodationService;
    @Mock
    private UserService userService;
    @Mock
    private ReservationRequestService reservationRequestService;

    private final long VALID_ACCOMMODATION_ID = 1L;
    private final long INVALID_ACCOMMODATION_ID = 0L;
    private final long VALID_GUEST_ID = 1L;
    private final long INVALID_GUEST_ID = 0L;

    @Test
    public void testCreateReservationRequest_SuccessfulRequest() {
        // Create a sample MakeReservationRequestDTO for testing
        MakeReservationRequestDTO requestDTO = createSampleRequestDTO();

        // Mock the behavior of accommodationService.findById()
        when(accommodationService.findById(VALID_ACCOMMODATION_ID)).thenReturn(createSampleAccommodation());

        // Mock the behavior of userService.findById()
        when(userService.findById(VALID_GUEST_ID)).thenReturn(createSampleGuest());

        // Mock the behavior of reservationRequestService.createReservationRequest()
        when(reservationRequestService.createReservationRequest(any(), any(), any()))
                .thenReturn(true);

        // Perform the actual test
        ResponseEntity<MakeReservationRequestDTO> responseEntity =
                reservationRequestController.createReservationRequest(requestDTO);

        // Assertions
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(requestDTO, responseEntity.getBody());
    }

    @Test
    public void testSearchReservationRequests() {
        // Implementacija testa za pretragu zahteva za rezervaciju
        // Proverite da li su rezultati pretrage tačni i odgovaraju očekivanim rezultatima
    }

    @Test
    public void testCancelReservationRequest() {
        // Implementacija testa za otkazivanje zahteva za rezervaciju
        // Proverite status odgovora i da li je rezervacija uspešno otkazana
    }

    // Dodajte ostale testove prema potrebi
    // Helper method to create a sample MakeReservationRequestDTO
    private MakeReservationRequestDTO createSampleRequestDTO() {
        MakeReservationRequestDTO sampleRequestDTO = new MakeReservationRequestDTO(
                VALID_ACCOMMODATION_ID,
                VALID_GUEST_ID,
                Timestamp.valueOf(LocalDateTime.now().minusDays(1)),
                Timestamp.valueOf(LocalDateTime.now()),
                2,
                50.0
        );

        return sampleRequestDTO;
    }

    // Helper method to create a sample Accommodation object
    private Accommodation createSampleAccommodation() {

        Accommodation sampleAccommodation = new Accommodation(
                VALID_ACCOMMODATION_ID,
                null, null, null, null, null, 1, 2,
                null, null, 1.0, false, 2.0, null,
                null, null, 0, false
        );

        return sampleAccommodation;
    }

    // Helper method to create a sample Guest object
    private Guest createSampleGuest() {
        Guest sampleGuest = new Guest(
                VALID_GUEST_ID,
                null, null, null, null, null, null,
                null, null, null, false, null
        );

        return sampleGuest;
    }
}