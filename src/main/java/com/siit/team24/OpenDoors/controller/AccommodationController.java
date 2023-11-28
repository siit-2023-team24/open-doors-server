package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationSearchDTO;
import com.siit.team24.OpenDoors.dto.accommodation.AccommodationWholeDTO;
import com.siit.team24.OpenDoors.dto.searchAndFilter.SearchAndFilterDTO;
import com.siit.team24.OpenDoors.model.Accommodation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "open-doors/accommodations")
public class AccommodationController {

    @GetMapping(value = "/all")
    public ResponseEntity<List<AccommodationSearchDTO>> getAllAccommodations() {
        List<AccommodationSearchDTO> accommodations = new ArrayList<>();

        return new ResponseEntity<>(accommodations, HttpStatus.OK);
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

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(consumes="application/json", value = "/search")
    public ResponseEntity<List<AccommodationSearchDTO>> searchAccommodations(@RequestBody SearchAndFilterDTO dto) {
        List<AccommodationSearchDTO> accommodations = new ArrayList<>();
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @GetMapping(value = "/{accommodationId}/images")
    public ResponseEntity<List<byte[]>> getAccommodationImages(@PathVariable Long accommodationId) {
        List<byte[]> images = new ArrayList<>();

        return new ResponseEntity<>(images, HttpStatus.OK);
    }
}
