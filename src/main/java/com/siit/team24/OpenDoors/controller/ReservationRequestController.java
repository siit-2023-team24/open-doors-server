package com.siit.team24.OpenDoors.controller;


import com.siit.team24.OpenDoors.dto.reservation.ReservationRequestDTO;
import com.siit.team24.OpenDoors.dto.reservation.ReservationRequestForGuestDTO;
import com.siit.team24.OpenDoors.dto.reservation.ReservationRequestForHostDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "open-doors/reservations")
public class ReservationRequestController {

    //service

    @GetMapping(value = "/all/{guestId}")
    public ResponseEntity<List<ReservationRequestForGuestDTO>> getAllForGuest(@PathVariable Long guestId) {
        List<ReservationRequestForGuestDTO> requests = new ArrayList<>();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping(value = "/confirmed/{guestId}")
    public ResponseEntity<List<ReservationRequestForGuestDTO>> getConfirmedForGuest(@PathVariable Long guestId) {
        List<ReservationRequestForGuestDTO> requests = new ArrayList<>();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping(value = "/host/{hostId}")
    public ResponseEntity<List<ReservationRequestForHostDTO>> getAllForHost(@PathVariable Long hostId) {
        List<ReservationRequestForHostDTO> requests = new ArrayList<>();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping(value = "/findAccommodationName")
    public ResponseEntity<List<ReservationRequestForGuestDTO>> getByAccommodationName(@RequestParam String name) {
        List<ReservationRequestForGuestDTO> requests = new ArrayList<>();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping(value = "/findDate")
    public ResponseEntity<List<ReservationRequestForGuestDTO>> getByDate(@RequestParam LocalDate start,
                                                                         @RequestParam LocalDate end) {
        List<ReservationRequestForGuestDTO> requests = new ArrayList<>();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping(value = "/pending")
    public ResponseEntity<List<ReservationRequestForGuestDTO>> getPending() {
        List<ReservationRequestForGuestDTO> requests = new ArrayList<>();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping(value = "/confirmed")
    public ResponseEntity<List<ReservationRequestForGuestDTO>> getConfirmed() {
        List<ReservationRequestForGuestDTO> requests = new ArrayList<>();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping(value = "/denied")
    public ResponseEntity<List<ReservationRequestForGuestDTO>> getDenied() {
        List<ReservationRequestForGuestDTO> requests = new ArrayList<>();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ReservationRequestDTO> createReservationRequest(@RequestBody ReservationRequestDTO requestDTO) {
        return new ResponseEntity<>(new ReservationRequestDTO(), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<ReservationRequestDTO> updateReservationRequest(@RequestBody ReservationRequestDTO requestDTO){
        return new ResponseEntity<>(new ReservationRequestDTO(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReservationRequest(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
