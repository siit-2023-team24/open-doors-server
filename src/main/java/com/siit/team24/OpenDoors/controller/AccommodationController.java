package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationSearchDTO;
import com.siit.team24.OpenDoors.dto.accommodation.AccommodationWholeDTO;
import com.siit.team24.OpenDoors.dto.accommodation.AccommodationWithTotalPriceDTO;
import com.siit.team24.OpenDoors.dto.searchAndFilter.SearchAndFilterDTO;
import com.siit.team24.OpenDoors.model.*;
import com.siit.team24.OpenDoors.model.enums.AccommodationType;
import com.siit.team24.OpenDoors.model.enums.Amenity;
import com.siit.team24.OpenDoors.model.enums.UserRole;
import com.siit.team24.OpenDoors.service.AccommodationService;
import com.siit.team24.OpenDoors.service.ImageService;
import com.siit.team24.OpenDoors.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import com.siit.team24.OpenDoors.model.enums.Country;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccommodationWithTotalPriceDTO> getAccommodation(@PathVariable Long id) {

        Optional<Accommodation> accommodation = accommodationService.findOne(id);

        if (accommodation.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new AccommodationWithTotalPriceDTO(accommodation.get(), 0.0), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<AccommodationWholeDTO> saveAccommodation(@RequestBody AccommodationWholeDTO accommodationWholeDTO) {

        Accommodation accommodation = new Accommodation();

        accommodation.setId(accommodationWholeDTO.getId());
        accommodation.setName(accommodationWholeDTO.getName());
        accommodation.setDescription(accommodationWholeDTO.getDescription());
        accommodation.setLocation(accommodationWholeDTO.getLocation());
        accommodation.setAmenities(accommodationWholeDTO.getAmenities());
        // TODO: images
        accommodation.setMinGuests(accommodationWholeDTO.getMinGuests());
        accommodation.setMaxGuests(accommodationWholeDTO.getMaxGuests());
        accommodation.setType(AccommodationType.fromString(accommodationWholeDTO.getType()));

        accommodation.getAddress().setCity(accommodationWholeDTO.getCity());
        accommodation.getAddress().setCountry(Country.fromString(accommodationWholeDTO.getCountry()));
        accommodation.getAddress().setStreet(accommodationWholeDTO.getStreet());
        accommodation.getAddress().setNumber(accommodationWholeDTO.getNumber());
        accommodation.setDeadline(accommodationWholeDTO.getDeadline());
        accommodation.setAutomatic(accommodation.isAutomatic());

        // TODO: expand front-end with the following

        accommodation.setAvailability(accommodationWholeDTO.getAvailability());
        accommodation.setPrice(accommodationWholeDTO.getPrice());
        accommodation.setSeasonalRates(accommodationWholeDTO.getSeasonalRates());

        accommodationService.save(accommodation);

        return new ResponseEntity<>(new AccommodationWholeDTO(accommodation), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<AccommodationWholeDTO> updateAccommodation(@RequestBody AccommodationWholeDTO accommodationWholeDTO) {
        ImageService imageService = new ImageService();

        Optional<Accommodation> optionalAccommodation = accommodationService.findOne(accommodationWholeDTO.getId());

        if (optionalAccommodation.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Accommodation accommodation = new Accommodation(accommodationWholeDTO);

//        for(Long imageId : accommodationWholeDTO.getImages()) {
//            try {
//                accommodation.addImage(imageService.findById(imageId, false));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }

        accommodation = accommodationService.save(accommodation);

        return new ResponseEntity<>(new AccommodationWholeDTO(accommodation), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable Long id) {

        Optional<Accommodation> accommodation = accommodationService.findOne(id);

        if (accommodation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        accommodationService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", value = "/search")
    public ResponseEntity<List<AccommodationSearchDTO>> searchAccommodations(@RequestBody SearchAndFilterDTO searchAndFilterDTO) {

        List<AccommodationSearchDTO> accommodations = accommodationService.searchAndFilter(searchAndFilterDTO);

        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @GetMapping(value = "/{accommodationId}/images") // TODO: sta je sa ovim? :(
    public ResponseEntity<List<byte[]>> getAccommodationImages(@PathVariable Long accommodationId) {
        Optional<Accommodation> accommodation = accommodationService.findOne(accommodationId);

        if (accommodation.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<byte[]> images = new ArrayList<>();
        for (Image image : accommodation.get().getImages())
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
