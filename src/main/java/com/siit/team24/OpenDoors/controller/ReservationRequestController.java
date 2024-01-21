package com.siit.team24.OpenDoors.controller;


import com.siit.team24.OpenDoors.dto.reservation.*;
import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.DateRange;
import com.siit.team24.OpenDoors.model.Guest;
import com.siit.team24.OpenDoors.model.ReservationRequest;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;
import com.siit.team24.OpenDoors.service.AccommodationService;
import com.siit.team24.OpenDoors.service.ReservationRequestService;
import com.siit.team24.OpenDoors.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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

    @PreAuthorize("hasRole('HOST')")
    @GetMapping(value = "/host/{hostId}")
    public ResponseEntity<List<ReservationRequestForHostDTO>> getAllForHost(@PathVariable Long hostId) {
        List<ReservationRequestForHostDTO> requests = reservationRequestService.getAllForHost(hostId);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('GUEST')")
    @PostMapping(consumes = "application/json", value = "/search/{guestId}")
    public ResponseEntity<List<ReservationRequestForGuestDTO>> searchReservationRequests(
            @PathVariable Long guestId,
            @RequestBody ReservationRequestSearchAndFilterDTO requestSearchAndFilterDTO) {

        System.out.println(requestSearchAndFilterDTO);
        List<ReservationRequestForGuestDTO> requests = reservationRequestService.searchRequests(guestId, requestSearchAndFilterDTO);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST')")
    @PostMapping(consumes = "application/json", value = "/host-search/{hostId}")
    public ResponseEntity<List<ReservationRequestForHostDTO>> searchReservationRequestsForHost(
            @PathVariable Long hostId,
            @RequestBody ReservationRequestSearchAndFilterDTO requestSearchAndFilterDTO) {

        System.out.println(requestSearchAndFilterDTO);
        List<ReservationRequestForHostDTO> requests = reservationRequestService.searchRequestsForHost(hostId, requestSearchAndFilterDTO);
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

    @PreAuthorize("hasRole('GUEST')")
    @GetMapping(value = "cancel/{id}")
    public ResponseEntity<Void> cancelRequest(@PathVariable Long id) {
        reservationRequestService.cancel(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('GUEST')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationRequestService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST')")
    @GetMapping(value = "confirm/{id}")
    public ResponseEntity<Void> confirm(@PathVariable Long id) {
        reservationRequestService.confirm(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST')")
    @GetMapping(value = "deny/{id}")
    public ResponseEntity<Void> deny(@PathVariable Long id) {
        reservationRequestService.deny(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PreAuthorize("hasRole('GUEST')")
    @PostMapping(consumes = "application/json", value = "/createRequest")
    public ResponseEntity<MakeReservationRequestDTO> createReservationRequest(@Valid @RequestBody MakeReservationRequestDTO requestDTO) {

        System.out.println(requestDTO);

        Accommodation accommodation = accommodationService.findById(requestDTO.getAccommodationId());

        ReservationRequest request = new ReservationRequest();
        request.setGuest((Guest) userService.findById(requestDTO.getGuestId()));
        request.setAccommodation(accommodation);
        request.setDateRange(new DateRange(requestDTO.getStartDate(), requestDTO.getEndDate()));

        if(accommodation.getIsAutomatic()) {
            request.setStatus(ReservationRequestStatus.CONFIRMED);
            accommodationService.removeDatesFromAccommodationAvailability(requestDTO.getAccommodationId(), request.getDateRange());
            reservationRequestService.denyAllOverlappingRequests(request.getAccommodation().getId(), request.getDateRange());
        } else {
            request.setStatus(ReservationRequestStatus.PENDING);
        }

        request.setGuestNumber(requestDTO.getNumberOfGuests());
        request.setTimestamp(new Timestamp(System.currentTimeMillis()));
        request.setTotalPrice(requestDTO.getTotalPrice());

        reservationRequestService.save(request);

        return new ResponseEntity<>(requestDTO, HttpStatus.CREATED);
    }

}
