package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationWholeDTO;
import com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationWholeEditedDTO;
import com.siit.team24.OpenDoors.dto.reservation.MakeReservationRequestDTO;
import com.siit.team24.OpenDoors.model.DateRange;
import com.siit.team24.OpenDoors.model.ReservationRequest;
import com.siit.team24.OpenDoors.model.SeasonalRate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
@ExtendWith({MockitoExtension.class, SpringExtension.class})
public class PendingAccommodationControllerIntegrationTest {

    private static final int VALID_DEADLINE = 1;
    private static final int INVALID_DEADLINE = -1;
    private static final double DEFAULT_PRICE = 10.0;
    private static final double VALID_SEASONAL_RATE_PRICE_1 = 20.0;
    private static final double VALID_SEASONAL_RATE_PRICE_2 = 30.0;
    private static final double INVALID_SEASONAL_RATE_PRICE = -10.0;

    private static final long VALID_ACCOMMODATION_ID = 1L;
    private static final long INVALID_ACCOMMODATION_ID = 0L;
    private static final long VALID_GUEST_ID = 2L;
    private static final Timestamp VALID_START_DATE = Timestamp.valueOf("2024-07-01 00:00:00");
    private static final Timestamp VALID_END_DATE = Timestamp.valueOf("2024-07-10 00:00:00");
    private static final int VALID_NUMBER_OF_GUESTS = 1;
    private static final double VALID_TOTAL_PRICE = 200;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Should save pending accommodation when making a POST request to endpoint - http://localhost:9090/open-doors/pending-accommodations")
    public void shouldSavePendingAccommodation() {

        PendingAccommodationWholeEditedDTO dto = new PendingAccommodationWholeEditedDTO(
                null, "Test accommodation", "This is just a test", "45.45 19.19", new ArrayList<>(), null, 1, 1, "Hotel",
                new ArrayList<>(), DEFAULT_PRICE, false, new ArrayList<>(), "Novi Sad", "Serbia", "Zlatne grede", 4,
                VALID_DEADLINE, false, "vaske@test.test", null, null
        );

        dto.getSeasonalRates().add(new SeasonalRate(VALID_SEASONAL_RATE_PRICE_1, new DateRange(generateMidnightDate(1), generateMidnightDate(3))));
        dto.getSeasonalRates().add(new SeasonalRate(VALID_SEASONAL_RATE_PRICE_2, new DateRange(generateMidnightDate(5), generateMidnightDate(6))));
        dto.getAvailability().add(new DateRange(generateMidnightDate(1), generateMidnightDate(3)));
        dto.getAvailability().add(new DateRange(generateMidnightDate(5), generateMidnightDate(7)));

        ResponseEntity<PendingAccommodationWholeDTO> responseEntity = restTemplate.postForEntity(
                "/open-doors/pending-accommodations",
                dto, PendingAccommodationWholeDTO.class);

        PendingAccommodationWholeDTO savedPendingAccommodation = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(savedPendingAccommodation);
    }

    @Test
    @DisplayName("Should create reservation request when making a POST request to endpoint - http://localhost:9090/open-doors/reservations/createRequest")
    public void testCreateReservationRequest_Success() throws Exception {
        // Priprema test podataka
        MakeReservationRequestDTO requestDTO = new MakeReservationRequestDTO();
        requestDTO.setAccommodationId(VALID_ACCOMMODATION_ID);
        requestDTO.setGuestId(VALID_GUEST_ID);
        requestDTO.setStartDate(VALID_START_DATE);
        requestDTO.setEndDate(VALID_END_DATE);
        requestDTO.setNumberOfGuests(VALID_NUMBER_OF_GUESTS);
        requestDTO.setTotalPrice(VALID_TOTAL_PRICE);

        ResponseEntity<MakeReservationRequestDTO> responseEntity = restTemplate.postForEntity(
                "/open-doors/reservations/createRequest",
                requestDTO, MakeReservationRequestDTO.class);

        MakeReservationRequestDTO createdRequestDTO = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(createdRequestDTO);
    }

    private static Timestamp generateMidnightDate(int n) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis()
                + n * 1000 * 60 * 60 * 24);
        timestamp.setNanos(0);
        timestamp.setSeconds(0);
        timestamp.setMinutes(0);
        timestamp.setHours(0);
        return timestamp;
    }

}
