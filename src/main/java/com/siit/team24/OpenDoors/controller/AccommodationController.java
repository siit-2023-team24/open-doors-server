package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationHostDTO;
import com.siit.team24.OpenDoors.dto.accommodation.AccommodationSearchDTO;
import com.siit.team24.OpenDoors.dto.accommodation.AccommodationWholeDTO;
import com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationHostDTO;
import com.siit.team24.OpenDoors.dto.searchAndFilter.SearchAndFilterDTO;
import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.DateRange;
import com.siit.team24.OpenDoors.model.Image;
import com.siit.team24.OpenDoors.model.Price;
import com.siit.team24.OpenDoors.model.enums.AccommodationType;
import com.siit.team24.OpenDoors.model.enums.Amenity;
import com.siit.team24.OpenDoors.model.enums.Country;
import com.siit.team24.OpenDoors.service.AccommodationService;
import com.siit.team24.OpenDoors.service.PendingAccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping(value = "open-doors/accommodations")
public class AccommodationController {
    // test data
    AccommodationSearchDTO testAccommodationSearchDTO = new AccommodationSearchDTO(
            (long)463453243, (long)363543252, "Hotel Park", 4.5, 340, true,
            "Novi Sad", "Serbia"
    );
    List<Amenity> testAmenities = new ArrayList<>(Arrays.asList(Amenity.BAR, Amenity.GYM));
    Image testImage = new Image((long)432343252, "./image", "Test Image", "jpg");
    Set<Image> testImages = Set.of(
            testImage);
    List<DateRange> testDates = new ArrayList<>(Arrays.asList(
            new DateRange(LocalDate.now(), LocalDate.now().plusDays(17))));
    List<Price> testPrices = new ArrayList<>(Arrays.asList(
            new Price(5000.0, new DateRange(
                    LocalDate.now().plusDays(200), LocalDate.now().plusDays(231)))));



    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    private PendingAccommodationService pendingAccommodationService;

    AccommodationWholeDTO testAccommodationWholeDTO = new AccommodationWholeDTO(
            (long)34873493, "Hotel Plaza", "Description", "45.3554 19.3453",
            testAmenities, testImages, 3, 8, AccommodationType.HOTEL.name(), testDates, 4000.0, testPrices,
            "New York City", Country.UNITED_STATES.getCountryName(), "Manhattan Street", 5, 10, true
    );


    @GetMapping(value = "/all")
    public ResponseEntity<List<AccommodationSearchDTO>> getAllAccommodations() {
        List<AccommodationSearchDTO> accommodations = new ArrayList<>();
        accommodations.add(testAccommodationSearchDTO);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AccommodationSearchDTO>> getAccommodationsSearchPage(Pageable page) {
        List<AccommodationSearchDTO> accommodationSearchDTOS = new ArrayList<>();
        accommodationSearchDTOS.add(testAccommodationSearchDTO);
        return new ResponseEntity<>(accommodationSearchDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccommodationWholeDTO> getAccommodation(@PathVariable Long id) {
        return new ResponseEntity<>(testAccommodationWholeDTO, HttpStatus.OK);
    }

    //TODO: create and edit are handled here
    //for edit (accommodationId!=null): soft delete from a.repo and save to pa.repo
    //for create save to pa.repo
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
        return new ResponseEntity<>(testAccommodationWholeDTO, HttpStatus.OK);
    }



    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable Long id) {
        //accommodationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(consumes="application/json", value = "/search")
    public ResponseEntity<List<AccommodationSearchDTO>> searchAccommodations(@RequestBody SearchAndFilterDTO dto) {
        List<AccommodationSearchDTO> accommodations = new ArrayList<>();
        accommodations.add(testAccommodationSearchDTO);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @GetMapping(value = "/{accommodationId}/images")
    public ResponseEntity<List<byte[]>> getAccommodationImages(@PathVariable Long accommodationId) {
        List<byte[]> images = new ArrayList<>();
        images.add(testImage.toString().getBytes());
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @GetMapping(value = "/host/{hostId}")
    public ResponseEntity<Collection<AccommodationHostDTO>> getForHost(@PathVariable Long hostId) {
        Collection<AccommodationHostDTO> accommodations = accommodationService.getForHost(hostId);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }


}
