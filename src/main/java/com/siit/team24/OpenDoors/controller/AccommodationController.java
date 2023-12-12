package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationSearchDTO;
import com.siit.team24.OpenDoors.dto.accommodation.AccommodationSearchDTO;
import com.siit.team24.OpenDoors.dto.accommodation.AccommodationWholeDTO;
import com.siit.team24.OpenDoors.dto.accommodation.AccommodationWithTotalPriceDTO;
import com.siit.team24.OpenDoors.dto.searchAndFilter.SearchAndFilterDTO;
import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.Image;
import com.siit.team24.OpenDoors.model.enums.AccommodationType;
import com.siit.team24.OpenDoors.model.enums.Amenity;
import com.siit.team24.OpenDoors.service.AccommodationService;
import com.siit.team24.OpenDoors.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

        for(Accommodation a : accommodations)
            AccommodationSearchDTOS.add(new AccommodationSearchDTO(a));

        return new ResponseEntity<>(AccommodationSearchDTOS, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AccommodationSearchDTO>> getAccommodationsSearchPage(Pageable page) {
        Page<Accommodation> accommodations = accommodationService.findAll(page);

        List<AccommodationSearchDTO> AccommodationSearchDTOS = new ArrayList<>();

        for(Accommodation a : accommodations)
            AccommodationSearchDTOS.add(new AccommodationSearchDTO(a));

        return new ResponseEntity<>(AccommodationSearchDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccommodationWithTotalPriceDTO> getAccommodation(@PathVariable Long id) {

        Accommodation accommodation = accommodationService.findOne(id);

        if(accommodation == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new AccommodationWithTotalPriceDTO(accommodation, null), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<AccommodationWholeDTO> saveAccommodation(@RequestBody AccommodationWholeDTO accommodationWholeDTO) {
        ImageService imageService = new ImageService();
        Accommodation accommodation = new Accommodation(accommodationWholeDTO);

        for(Long imageId : accommodationWholeDTO.getImages()) {
            accommodation.addImage(imageService.findOne(imageId));
        }

        accommodation = accommodationService.save(accommodation);

        return new ResponseEntity<>(new AccommodationWholeDTO(accommodation), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<AccommodationWholeDTO> updateAccommodation(@RequestBody AccommodationWholeDTO accommodationWholeDTO) {
        ImageService imageService = new ImageService();

        Accommodation accommodation = accommodationService.findOne(accommodationWholeDTO.getId());

        if(accommodation == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        accommodation = new Accommodation(accommodationWholeDTO);

        for(Long imageId : accommodationWholeDTO.getImages()) {
            accommodation.addImage(imageService.findOne(imageId));
        }

        accommodation = accommodationService.save(accommodation);

        return new ResponseEntity<>(new AccommodationWholeDTO(accommodation), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable Long id) {

        Accommodation accommodation = accommodationService.findOne(id);

        if(accommodation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        accommodationService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(consumes="application/json", value = "/search")
    public ResponseEntity<List<AccommodationSearchDTO>> searchAccommodations(@RequestBody SearchAndFilterDTO searchAndFilterDTO) {

        List<AccommodationSearchDTO> accommodations = accommodationService.searchAndFilter(searchAndFilterDTO);

        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @GetMapping(value = "/{accommodationId}/images") // TODO: sta je sa ovim? :(
    public ResponseEntity<List<byte[]>> getAccommodationImages(@PathVariable Long accommodationId) {
        Accommodation accommodation = accommodationService.findOne(accommodationId);

        List<byte[]> images = new ArrayList<>();
        for(Image image : accommodation.getImages())
            images.add(image.toString().getBytes());

        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @GetMapping("/accommodationTypes")
    public ResponseEntity<List<String>> getAccommodationTypes() {
        List<String> accommodationTypes = Arrays.stream(AccommodationType.values())
                .map(AccommodationType::getValue)
                .collect(Collectors.toList());
        return ResponseEntity.ok(accommodationTypes);
    }

    @GetMapping("/amenities")
    public ResponseEntity<List<String>> getAmenities() {
        List<String> amenities = Arrays.stream(Amenity.values())
                .map(Amenity::getDisplayName)
                .collect(Collectors.toList());
        return ResponseEntity.ok(amenities);
    }
}
