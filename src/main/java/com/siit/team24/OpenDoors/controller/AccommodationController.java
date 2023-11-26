package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationSearchDTO;
import com.siit.team24.OpenDoors.dto.accommodation.AccommodationWholeDTO;
import com.siit.team24.OpenDoors.model.Accommodation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "open-doors/accommodations")
public class AccommodationController {

    @GetMapping(value = "/all")
    public ResponseEntity<List<AccommodationWholeDTO>> getAllAccommodations() {
        List<AccommodationWholeDTO> accommodationWholeDTOS = new ArrayList<>();

        return new ResponseEntity<>(accommodationWholeDTOS, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AccommodationSearchDTO>> getAccommodationsSearchPage(Pageable page) {
        List<AccommodationSearchDTO> accommodationSearchDTOS = new ArrayList<>();

        return new ResponseEntity<>(accommodationSearchDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccommodationWholeDTO> getAccommodation(@PathVariable Long id) {
        Accommodation accommodation = new Accommodation();

        return new ResponseEntity<>(new AccommodationWholeDTO(accommodation), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<AccommodationWholeDTO> saveAccommodation(@RequestBody AccommodationWholeDTO accommodationWholeDTO) {
        Accommodation accommodation = new Accommodation();

        return new ResponseEntity<>(new AccommodationWholeDTO(), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<AccommodationWholeDTO> updateAccommodation(@RequestBody AccommodationWholeDTO accommodationWholeDTO) {
        Accommodation accommodation = new Accommodation();

        return new ResponseEntity<>(new AccommodationWholeDTO(accommodation), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable Long id) {
        Accommodation accommodation = new Accommodation();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/findName")
    public ResponseEntity<AccommodationSearchDTO> getAccommodationByName(@RequestParam String name) {
        Accommodation accommodation = new Accommodation();

        return new ResponseEntity<>(new AccommodationSearchDTO(accommodation), HttpStatus.OK);
    }

    @GetMapping(value = "/findStartDate")
    public ResponseEntity<AccommodationSearchDTO> getAccommodationByStartDate(@RequestParam LocalDate startDate) {
        Accommodation accommodation = new Accommodation();

        return new ResponseEntity<>(new AccommodationSearchDTO(accommodation), HttpStatus.OK);
    }

    @GetMapping(value = "/findEndDate")
    public ResponseEntity<AccommodationSearchDTO> getAccommodationByEndDate(@RequestParam LocalDate endDate) {
        Accommodation accommodation = new Accommodation();

        return new ResponseEntity<>(new AccommodationSearchDTO(accommodation), HttpStatus.OK);
    }

    @GetMapping(value = "/findGuest")
    public ResponseEntity<AccommodationSearchDTO> getAccommodationByGuestNumber(@RequestParam Integer guestNumber) {
        Accommodation accommodation = new Accommodation();

        return new ResponseEntity<>(new AccommodationSearchDTO(accommodation), HttpStatus.OK);
    }

    @GetMapping(value = "/findNameStartDate")
    public ResponseEntity<AccommodationSearchDTO> getAccommodationByNameAndStartDate(@RequestParam String name, @RequestParam LocalDate startDate) {
        Accommodation accommodation = new Accommodation();

        return new ResponseEntity<>(new AccommodationSearchDTO(accommodation), HttpStatus.OK);
    }

    @GetMapping(value = "/findNameEndDate")
    public ResponseEntity<AccommodationSearchDTO> getAccommodationByNameAndEndDate(@RequestParam String name, @RequestParam LocalDate endDate) {
        Accommodation accommodation = new Accommodation();

        return new ResponseEntity<>(new AccommodationSearchDTO(accommodation), HttpStatus.OK);
    }

    @GetMapping(value = "/findNameGuest")
    public ResponseEntity<AccommodationSearchDTO> getAccommodationByNameAndGuestNumber(@RequestParam String name, @RequestParam Integer guestNumber) {
        Accommodation accommodation = new Accommodation();

        return new ResponseEntity<>(new AccommodationSearchDTO(accommodation), HttpStatus.OK);
    }

    @GetMapping(value = "/findDates")
    public ResponseEntity<AccommodationSearchDTO> getAccommodationByDates(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        Accommodation accommodation = new Accommodation();

        return new ResponseEntity<>(new AccommodationSearchDTO(accommodation), HttpStatus.OK);
    }

    @GetMapping(value = "/findStartDateGuest")
    public ResponseEntity<AccommodationSearchDTO> getAccommodationByStartDateAndGuestNumber(@RequestParam LocalDate startDate, @RequestParam Integer guestNumber) {
        Accommodation accommodation = new Accommodation();

        return new ResponseEntity<>(new AccommodationSearchDTO(accommodation), HttpStatus.OK);
    }

    @GetMapping(value = "/findEndDateGuest")
    public ResponseEntity<AccommodationSearchDTO> getAccommodationByEndDateAndGuestNumber(@RequestParam LocalDate endDate, @RequestParam Integer guestNumber) {
        Accommodation accommodation = new Accommodation();

        return new ResponseEntity<>(new AccommodationSearchDTO(accommodation), HttpStatus.OK);
    }

    @GetMapping(value = "/findNameDates")
    public ResponseEntity<AccommodationSearchDTO> getAccommodationByNameAndDates(@RequestParam String name, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        Accommodation accommodation = new Accommodation();

        return new ResponseEntity<>(new AccommodationSearchDTO(accommodation), HttpStatus.OK);
    }

    @GetMapping(value = "/findNameStartDateGuest")
    public ResponseEntity<AccommodationSearchDTO> getAccommodationByNameAndStartDateAndGuestNumber(@RequestParam String name, @RequestParam LocalDate startDate, @RequestParam Integer guestNumber) {
        Accommodation accommodation = new Accommodation();

        return new ResponseEntity<>(new AccommodationSearchDTO(accommodation), HttpStatus.OK);
    }

    @GetMapping(value = "/findNameEndDateGuest")
    public ResponseEntity<AccommodationSearchDTO> getAccommodationByNameAndEndDateAndGuestNumber(@RequestParam String name, @RequestParam LocalDate endDate, @RequestParam Integer guestNumber) {
        Accommodation accommodation = new Accommodation();

        return new ResponseEntity<>(new AccommodationSearchDTO(accommodation), HttpStatus.OK);
    }

    @GetMapping(value = "/findDatesGuest")
    public ResponseEntity<AccommodationSearchDTO> getAccommodationByDatesAndGuestNumber(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @RequestParam Integer guestNumber) {
        Accommodation accommodation = new Accommodation();

        return new ResponseEntity<>(new AccommodationSearchDTO(accommodation), HttpStatus.OK);
    }

    @GetMapping(value = "/findNameDatesGuest")
    public ResponseEntity<AccommodationSearchDTO> getAccommodationByNameAndDatesAndGuestNumber(@RequestParam String name, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @RequestParam Integer guestNumber) {
        Accommodation accommodation = new Accommodation();

        return new ResponseEntity<>(new AccommodationSearchDTO(accommodation), HttpStatus.OK);
    }

    @GetMapping(value = "/{accommodationId}/images")
    public ResponseEntity<List<String>> getAccommodationImages(@PathVariable Long accommodationId) {
        List<String> images = new ArrayList<>();

        return new ResponseEntity<>(images, HttpStatus.OK);
    }
}
