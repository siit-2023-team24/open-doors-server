package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.reservation.MakeReservationRequestDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
@ExtendWith({MockitoExtension.class, SpringExtension.class})
public class ReservationRequestControllerIntegrationTest {

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

    @Test
    @DisplayName("Should return BAD REQUEST when making a POST request with invalid data to endpoint - http://localhost:9090/open-doors/reservations/createRequest")
    public void testCreateReservationRequest_Failure() throws Exception {
        // Prepare test data with invalid accommodation ID
        MakeReservationRequestDTO requestDTO = new MakeReservationRequestDTO();
        requestDTO.setAccommodationId(INVALID_ACCOMMODATION_ID);
        requestDTO.setGuestId(VALID_GUEST_ID);
        requestDTO.setStartDate(VALID_START_DATE);
        requestDTO.setEndDate(VALID_END_DATE);
        requestDTO.setNumberOfGuests(VALID_NUMBER_OF_GUESTS);
        requestDTO.setTotalPrice(VALID_TOTAL_PRICE);

        ResponseEntity<MakeReservationRequestDTO> responseEntity = restTemplate.postForEntity(
                "/open-doors/reservations/createRequest",
                requestDTO, MakeReservationRequestDTO.class);

        MakeReservationRequestDTO createdRequestDTO = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(createdRequestDTO);
    }
}
