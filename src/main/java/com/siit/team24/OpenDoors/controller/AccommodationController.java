package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationHostDTO;
import com.siit.team24.OpenDoors.dto.accommodation.AccommodationSearchDTO;
import com.siit.team24.OpenDoors.dto.accommodation.AccommodationWholeDTO;
import com.siit.team24.OpenDoors.dto.searchAndFilter.SearchAndFilterDTO;
import com.siit.team24.OpenDoors.exception.ExistingReservationsException;
import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.DateRange;
import com.siit.team24.OpenDoors.model.Image;
import com.siit.team24.OpenDoors.model.SeasonalRate;
import com.siit.team24.OpenDoors.model.enums.AccommodationType;
import com.siit.team24.OpenDoors.model.enums.Amenity;
import com.siit.team24.OpenDoors.model.enums.Country;
import com.siit.team24.OpenDoors.service.AccommodationService;
import com.siit.team24.OpenDoors.service.PendingAccommodationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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
            new DateRange(new Timestamp(329597), new Timestamp(2934823))));
    List<SeasonalRate> testSeasonalRates = new ArrayList<>(Arrays.asList(
            new SeasonalRate(5000.0, new DateRange(
                    new Timestamp(12345), new Timestamp(123456)))));

    AccommodationWholeDTO testAccommodationWholeDTO = new AccommodationWholeDTO();


    @Autowired
    private AccommodationService accommodationService;



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
        try {
            Accommodation accommodation = accommodationService.findById(id);
            return new ResponseEntity<>(new AccommodationWholeDTO(accommodation), HttpStatus.OK);
        }
        catch (EntityNotFoundException e) {
            System.err.println("Active accommodation not found with id: " + id);
            return new ResponseEntity<>(new AccommodationWholeDTO(), HttpStatus.NOT_FOUND);
        }
    }


    // @PreAuthorize("hasRole('HOST')")
/*
    @PostMapping(consumes = "application/json")
    public ResponseEntity<AccommodationWholeDTO> saveAccommodation(@RequestBody AccommodationWholeDTO accommodationWholeDTO) {
        System.out.println("Old DTO: " + accommodationWholeDTO);
        Accommodation accommodation = new Accommodation();

        accommodation.setId(accommodationWholeDTO.getId());
        accommodation.setName(accommodationWholeDTO.getName());
        accommodation.setDescription(accommodationWholeDTO.getDescription());
        accommodation.setLocation(accommodationWholeDTO.getLocation());
        accommodation.setAmenities(Amenity.fromStringList(accommodationWholeDTO.getAmenities()));
        // TODO: images
        accommodation.setMinGuests(accommodationWholeDTO.getMinGuests());
        accommodation.setMaxGuests(accommodationWholeDTO.getMaxGuests());
        accommodation.setType(AccommodationType.fromString(accommodationWholeDTO.getType()));

        accommodation.getAddress().setCity(accommodationWholeDTO.getCity());
        accommodation.getAddress().setCountry(Country.fromString(accommodationWholeDTO.getCountry()));
        accommodation.getAddress().setStreet(accommodationWholeDTO.getStreet());
        accommodation.getAddress().setNumber(accommodationWholeDTO.getNumber());
        accommodation.setDeadline(accommodationWholeDTO.getDeadline());
        accommodation.setIsAutomatic(accommodationWholeDTO.getIsAutomatic());

        accommodation.setAvailability(accommodationWholeDTO.getAvailability());
        accommodation.setPrice(accommodationWholeDTO.getPrice());
        accommodation.setIsPricePerGuest(accommodationWholeDTO.getIsPricePerGuest());
        accommodation.setSeasonalRates(accommodationWholeDTO.getSeasonalRates());
        accommodation.setAverageRating(0);

        accommodationService.save(accommodation);
        AccommodationWholeDTO newDto = new AccommodationWholeDTO(accommodation);
        System.out.println("New DTO: " + newDto);

        return new ResponseEntity<>(newDto, HttpStatus.CREATED);
    }
*/

    //@PreAuthorize("hasRole('HOST')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable Long id) {
        accommodationService.delete(id);
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

//    @PreAuthorize("hasRole('HOST')")
    @GetMapping(value = "/host/{hostId}")
    public ResponseEntity<Collection<AccommodationHostDTO>> getForHost(@PathVariable Long hostId) {
        Collection<AccommodationHostDTO> accommodations = accommodationService.getForHost(hostId);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }


}
