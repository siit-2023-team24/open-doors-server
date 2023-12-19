package com.siit.team24.OpenDoors.controller;


import com.siit.team24.OpenDoors.dto.reservation.ReservationRequestDTO;
import com.siit.team24.OpenDoors.dto.reservation.ReservationRequestForGuestDTO;
import com.siit.team24.OpenDoors.dto.reservation.ReservationRequestForHostDTO;
import com.siit.team24.OpenDoors.model.DateRange;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "open-doors/reservations")
public class ReservationRequestController {

    //service

    @GetMapping(value = "/all/guest/{guestId}")
    public ResponseEntity<List<ReservationRequestForGuestDTO>> getAllForGuest(@PathVariable Long guestId) {
        List<ReservationRequestForGuestDTO> requests = new ArrayList<>();
        //requests.add(testReservationRequestForGuestDTO);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping(value = "/confirmed/guest/{guestId}")
    public ResponseEntity<List<ReservationRequestForGuestDTO>> getConfirmedForGuest(@PathVariable Long guestId) {
        List<ReservationRequestForGuestDTO> requests = new ArrayList<>();
        //requests.add(testReservationRequestForGuestDTO);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping(value = "/host/{hostId}")
    public ResponseEntity<List<ReservationRequestForHostDTO>> getAllForHost(@PathVariable Long hostId) {
        List<ReservationRequestForHostDTO> requests = new ArrayList<>();
        //requests.add(testReservationRequestForHostDTO);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping(value = "/filtered")
    public ResponseEntity<List<ReservationRequestForGuestDTO>> getFiltered(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "dateStart", required = false) String startDate,
            @RequestParam(name = "dateEnd", required = false) String endDate,
            @RequestParam(name = "filter", required = false) ReservationRequestStatus filter) {
        List<ReservationRequestForGuestDTO> requests = new ArrayList<>();
        //requests.add(testReservationRequestForGuestDTO);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

//    @PostMapping(consumes = "application/json")
//    public ResponseEntity<ReservationRequestDTO> createReservationRequest(@RequestBody ReservationRequestDTO requestDTO) {
//        return new ResponseEntity<>(testReservationRequestDTO, HttpStatus.CREATED);
//    }
//
//    @PutMapping(consumes = "application/json")
//    public ResponseEntity<ReservationRequestDTO> updateReservationRequest(@RequestBody ReservationRequestDTO requestDTO){
//        return new ResponseEntity<>(testReservationRequestForHostDTO, HttpStatus.OK);
//    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReservationRequest(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
