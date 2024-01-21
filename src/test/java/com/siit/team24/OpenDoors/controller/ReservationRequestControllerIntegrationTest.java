package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.reservation.MakeReservationRequestDTO;
import com.siit.team24.OpenDoors.model.Accommodation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReservationRequestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    @DisplayName("Should create a reservation request when making POST request to endpoint - /reservationRequests/createRequest")
    public void shouldCreateAReservationRequest() {
        Accommodation accommodation = new Accommodation();
        MakeReservationRequestDTO requestDTO = new MakeReservationRequestDTO();
        ResponseEntity<MakeReservationRequestDTO> responseEntity = restTemplate.exchange("/reservationRequests/createRequest",
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<MakeReservationRequestDTO>() {
                });

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }


}
