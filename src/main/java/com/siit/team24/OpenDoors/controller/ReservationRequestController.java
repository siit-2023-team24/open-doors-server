package com.siit.team24.OpenDoors.controller;


import com.siit.team24.OpenDoors.dto.reservation.ReservationRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "opendoors/reservations")
public class ReservationRequestController {

    //service

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
