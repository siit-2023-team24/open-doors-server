package com.siit.team24.OpenDoors.controller;


import com.siit.team24.OpenDoors.dto.accommodation.AccommodationSearchDTO;
import com.siit.team24.OpenDoors.dto.reservation.*;
import com.siit.team24.OpenDoors.dto.searchAndFilter.SearchAndFilterDTO;
import com.siit.team24.OpenDoors.model.DateRange;
import com.siit.team24.OpenDoors.model.Guest;
import com.siit.team24.OpenDoors.model.ReservationRequest;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;
import com.siit.team24.OpenDoors.service.AccommodationService;
import com.siit.team24.OpenDoors.service.ReservationRequestService;
import com.siit.team24.OpenDoors.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "open-doors/reservations")
public class ReservationRequestController {

    @Autowired
    private ReservationRequestService reservationRequestService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccommodationService accommodationService;


    @PreAuthorize("hasRole('GUEST')")
    @GetMapping(value = "/all/guest/{guestId}")
    public ResponseEntity<List<ReservationRequestForGuestDTO>> getAllForGuest(@PathVariable Long guestId) {
        List<ReservationRequestForGuestDTO> requests = reservationRequestService.findByGuestId(guestId);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", value = "/search/{guestId}")
    public ResponseEntity<List<ReservationRequestForGuestDTO>> searchReservationRequests(
            @PathVariable Long guestId,
            @RequestBody ReservationRequestSearchAndFilterDTO requestSearchAndFilterDTO) {

        System.out.println(requestSearchAndFilterDTO);
        List<ReservationRequestForGuestDTO> requests = reservationRequestService.searchRequests(guestId, requestSearchAndFilterDTO);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping(value = "requestStatuses")
    public ResponseEntity<List<String>> getRequestStatuses() {
        List<String> statuses = Arrays.stream(ReservationRequestStatus.values())
                .map(type -> type.name().toUpperCase())
                .collect(Collectors.toList());
        statuses.remove("DELETED");
        return ResponseEntity.ok(statuses);
    }

    @PostMapping(value = "cancelRequest/{requestId}")
    public ResponseEntity<Void> cancelRequest(@PathVariable Long requestId) {
        ReservationRequest request = reservationRequestService.findById(requestId);
        request.setStatus(ReservationRequestStatus.CANCELLED);
        reservationRequestService.save(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "deleteRequest/{requestId}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long requestId) {
        ReservationRequest request = reservationRequestService.findById(requestId);
        request.setStatus(ReservationRequestStatus.DELETED);
        reservationRequestService.save(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('GUEST')")
    @GetMapping(value = "/confirmed/guest/{guestId}")
    public ResponseEntity<List<ReservationRequestForGuestDTO>> getConfirmedForGuest(@PathVariable Long guestId) {
        List<ReservationRequestForGuestDTO> requests = new ArrayList<>();
        //requests.add(testReservationRequestForGuestDTO);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST')")
    @GetMapping(value = "/host/{hostId}")
    public ResponseEntity<List<ReservationRequestForHostDTO>> getAllForHost(@PathVariable Long hostId) {
        List<ReservationRequestForHostDTO> requests = new ArrayList<>();
        //requests.add(testReservationRequestForHostDTO);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('GUEST')")
    @PostMapping(consumes = "application/json", value = "/createRequest")
    public ResponseEntity<MakeReservationRequestDTO> createReservationRequest(@RequestBody MakeReservationRequestDTO requestDTO) {

        System.out.println(requestDTO);

        ReservationRequest request = new ReservationRequest();
        request.setGuest((Guest) userService.findById(requestDTO.getGuestId()));
        request.setAccommodation(accommodationService.findById(requestDTO.getAccommodationId()));
        request.setDateRange(new DateRange(requestDTO.getStartDate(), requestDTO.getEndDate()));
        request.setStatus(ReservationRequestStatus.PENDING);
        request.setGuestNumber(request.getGuestNumber());
        request.setTimestamp(new Timestamp(System.currentTimeMillis()));
        request.setTotalPrice(requestDTO.getTotalPrice());

        reservationRequestService.save(request);

        return new ResponseEntity<>(requestDTO, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReservationRequest(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
