package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationHostDTO;
import com.siit.team24.OpenDoors.dto.accommodation.AccommodationSearchDTO;
import com.siit.team24.OpenDoors.dto.accommodation.AccommodationWholeDTO;
import com.siit.team24.OpenDoors.dto.accommodation.AccommodationWithTotalPriceDTO;
import com.siit.team24.OpenDoors.dto.searchAndFilter.SearchAndFilterDTO;

import com.siit.team24.OpenDoors.exception.ExistingReservationsException;

import com.siit.team24.OpenDoors.model.*;

import com.siit.team24.OpenDoors.model.enums.AccommodationType;
import com.siit.team24.OpenDoors.model.enums.Amenity;
import com.siit.team24.OpenDoors.service.AccommodationService;

import com.siit.team24.OpenDoors.service.PendingAccommodationService;
import jakarta.persistence.EntityNotFoundException;

import com.siit.team24.OpenDoors.service.ImageService;
import com.siit.team24.OpenDoors.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import com.siit.team24.OpenDoors.model.enums.Country;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequestMapping(value = "open-doors/accommodations")
public class AccommodationController {

    @Autowired
    private AccommodationService accommodationService;

  
    @GetMapping(value = "/all")
    public ResponseEntity<List<AccommodationSearchDTO>> getAllAccommodations() {
        List<Accommodation> accommodations = accommodationService.findAll();

        List<AccommodationSearchDTO> AccommodationSearchDTOS = new ArrayList<>();

        for (Accommodation a : accommodations)
            AccommodationSearchDTOS.add(new AccommodationSearchDTO(a));

        return new ResponseEntity<>(AccommodationSearchDTOS, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AccommodationSearchDTO>> getAccommodationsSearchPage(Pageable page) {
        Page<Accommodation> accommodations = accommodationService.findAll(page);

        List<AccommodationSearchDTO> accommodationSearchDTOS = new ArrayList<>();

        for (Accommodation a : accommodations)
            accommodationSearchDTOS.add(new AccommodationSearchDTO(a));

        for (AccommodationSearchDTO dto : accommodationSearchDTOS)
            System.out.println(dto);

        return new ResponseEntity<>(accommodationSearchDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "editable/{id}")
    public ResponseEntity<AccommodationWholeDTO> getAccommodationForEdit(@PathVariable Long id) {
        try {
            Accommodation accommodation = accommodationService.findById(id);
            return new ResponseEntity<>(new AccommodationWholeDTO(accommodation), HttpStatus.OK);
        }
        catch (EntityNotFoundException e) {
            System.err.println("Active accommodation not found with id: " + id);
            return new ResponseEntity<>(new AccommodationWholeDTO(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccommodationWithTotalPriceDTO> getAccommodation(@PathVariable Long id) {

        Optional<Accommodation> accommodation = accommodationService.findOne(id);

        if (accommodation.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new AccommodationWithTotalPriceDTO(accommodation.get(), 0.0), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable Long id) {
        accommodationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", value = "/search")
    public ResponseEntity<List<AccommodationSearchDTO>> searchAccommodations(@RequestBody SearchAndFilterDTO searchAndFilterDTO) {

        List<AccommodationSearchDTO> accommodations = accommodationService.searchAndFilter(searchAndFilterDTO);
        
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @GetMapping(value = "/{accommodationId}/images")
    public ResponseEntity<List<byte[]>> getAccommodationImages(@PathVariable Long accommodationId) {
        Optional<Accommodation> accommodation = accommodationService.findOne(accommodationId);

        if (accommodation.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<byte[]> images = new ArrayList<>();
        for (Image image : accommodation.get().getImages())
            images.add(image.toString().getBytes());

        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST')")
    @GetMapping(value = "/host/{hostId}")
    public ResponseEntity<Collection<AccommodationHostDTO>> getForHost(@PathVariable Long hostId) {
        Collection<AccommodationHostDTO> accommodations = accommodationService.getForHost(hostId);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @GetMapping("/accommodationTypes")
    public ResponseEntity<List<String>> getAccommodationTypes() {
        List<String> accommodationTypes = Arrays.stream(AccommodationType.values())
                .map(type -> type.name().toUpperCase()) // Convert to uppercase
                .collect(Collectors.toList());
        return ResponseEntity.ok(accommodationTypes);
    }

    @GetMapping("/amenities")
    public ResponseEntity<List<String>> getAmenities() {
        List<String> amenities = Arrays.stream(Amenity.values())
                .map(type -> type.name().toUpperCase())
                .collect(Collectors.toList());
        return ResponseEntity.ok(amenities);
    }

}
