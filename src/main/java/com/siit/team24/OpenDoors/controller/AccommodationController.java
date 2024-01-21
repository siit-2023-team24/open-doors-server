package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.accommodation.*;
import com.siit.team24.OpenDoors.dto.reservation.AccommodationSeasonalRateDTO;
import com.siit.team24.OpenDoors.dto.reservation.SeasonalRatesPricingDTO;
import com.siit.team24.OpenDoors.dto.searchAndFilter.SearchAndFilterDTO;

import com.siit.team24.OpenDoors.exception.ExistingReservationsException;
import com.siit.team24.OpenDoors.model.*;

import com.siit.team24.OpenDoors.model.enums.AccommodationType;
import com.siit.team24.OpenDoors.model.enums.Amenity;
import com.siit.team24.OpenDoors.service.AccommodationReviewService;
import com.siit.team24.OpenDoors.service.AccommodationService;

import com.siit.team24.OpenDoors.service.ReservationRequestService;
import com.siit.team24.OpenDoors.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequestMapping(value = "open-doors/accommodations")
public class AccommodationController {

    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccommodationReviewService accommodationReviewService;
    
    @Autowired
    private ReservationRequestService reservationRequestService;
  
    @GetMapping(value = "/all")
    public ResponseEntity<List<AccommodationSearchDTO>> getAllAccommodations() {
        List<Accommodation> accommodations = accommodationService.findAll();

        List<AccommodationSearchDTO> AccommodationSearchDTOS = new ArrayList<>();

        for (Accommodation a : accommodations) {
            AccommodationSearchDTO as = new AccommodationSearchDTO(a);
            as.setAverageRating(accommodationReviewService.getAverageRating(a.getId()));
            AccommodationSearchDTOS.add(as);
        }

        return new ResponseEntity<>(AccommodationSearchDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('GUEST')")
    @GetMapping(value = "/all/{guestId}")
    public ResponseEntity<List<AccommodationSearchDTO>> getAccommodationsWhenGuest(@PathVariable Long guestId) {
        Guest guest = (Guest) userService.findById(guestId);
        List<AccommodationSearchDTO> accommodationSearchDTOS = accommodationService.findAllWithFavorites(guest);
        for (AccommodationSearchDTO as : accommodationSearchDTOS) {
            as.setAverageRating(accommodationReviewService.getAverageRating(as.getId()));
        }
        return new ResponseEntity<>(accommodationSearchDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST')")
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
        AccommodationWithTotalPriceDTO dto = new AccommodationWithTotalPriceDTO(accommodation.get(), 0.0);
        dto.setAverageRating(accommodationReviewService.getAverageRating(id));

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('GUEST')")
    @GetMapping(value = "/{accommodationId}/{guestId}")
    public ResponseEntity<AccommodationWithTotalPriceDTO> getAccommodationWhenGuest(@PathVariable Long accommodationId, @PathVariable Long guestId) {
        Guest guest = (Guest) userService.findById(guestId);
        Accommodation accommodation = accommodationService.findById(accommodationId);
        AccommodationWithTotalPriceDTO dto = new AccommodationWithTotalPriceDTO(accommodation, 0.0);
        if(guest.getFavorites().contains(accommodation))
            dto.setIsFavoriteForGuest(true);
        dto.setAverageRating(accommodationReviewService.getAverageRating(accommodationId));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable Long id) {
        if (!reservationRequestService.isAccommodationReadyForDelete(id))
            throw new ExistingReservationsException();

        reservationRequestService.denyAllFor(id);
        userService.removeFromAnyFavorites(accommodationService.findById(id));
        accommodationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", value = "/search")
    public ResponseEntity<List<AccommodationSearchDTO>> searchAccommodations(@Valid @RequestBody SearchAndFilterDTO searchAndFilterDTO) {

        List<AccommodationSearchDTO> accommodations = accommodationService.searchAndFilter(searchAndFilterDTO);
        for (AccommodationSearchDTO as : accommodations) {
            as.setAverageRating(accommodationReviewService.getAverageRating(as.getId()));
        }

        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('GUEST')")
    @PostMapping(consumes = "application/json", value = "/search/{guestId}")
    public ResponseEntity<List<AccommodationSearchDTO>> searchAccommodationsWhenGuest(@PathVariable Long guestId, @RequestBody SearchAndFilterDTO searchAndFilterDTO) {

        Guest guest = (Guest) userService.findById(guestId);
        List<AccommodationSearchDTO> accommodationSearchDTOS = accommodationService.searchWithFavorites(guest, searchAndFilterDTO);
        for (AccommodationSearchDTO as : accommodationSearchDTOS) {
            as.setAverageRating(accommodationReviewService.getAverageRating(as.getId()));
        }
        return new ResponseEntity<>(accommodationSearchDTOS, HttpStatus.OK);
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
        Collection<AccommodationHostDTO> accommodations = accommodationService.getDTOsForHost(hostId);
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

    @PostMapping("/seasonalRate")
    public ResponseEntity<List<SeasonalRatesPricingDTO>> getSeasonalRatesForAccommodation(@Valid @RequestBody AccommodationSeasonalRateDTO accommodationSeasonalRateDTO) {
        List<SeasonalRatesPricingDTO> dtos = accommodationService.getSeasonalRatePricingsForAccommodation(accommodationSeasonalRateDTO);
        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasRole('GUEST')")
    @PostMapping("/addToFavorites")
    public ResponseEntity<Void> addToFavorites(@Valid @RequestBody AccommodationFavoritesDTO dto) {
        Accommodation accommodation = accommodationService.findById(dto.getAccommodationId());
        Guest guest = (Guest) userService.findById(dto.getGuestId());
        guest.addFavoriteAccommodation(accommodation);
        userService.save(guest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('GUEST')")
    @PostMapping("/removeFromFavorites")
    public ResponseEntity<Void> removeFromFavorites(@Valid @RequestBody AccommodationFavoritesDTO dto) {
        Accommodation accommodation = accommodationService.findById(dto.getAccommodationId());
        Guest guest = (Guest) userService.findById(dto.getGuestId());
        guest.removeFavoriteAccommodation(accommodation);
        userService.save(guest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('GUEST')")
    @GetMapping(value = "/favorites/{guestId}")
    public ResponseEntity<List<AccommodationSearchDTO>> getAccommodationsFavoritesPage(@PathVariable Long guestId) {
        Guest guest = (Guest) userService.findById(guestId);
        List<AccommodationSearchDTO> accommodationSearchDTOS = new ArrayList<>();
        for(Accommodation a: guest.getFavorites()) {
            AccommodationSearchDTO dto = new AccommodationSearchDTO(a);
            dto.setIsFavoriteForGuest(true);
            dto.setAverageRating(accommodationReviewService.getAverageRating(a.getId()));
            accommodationSearchDTOS.add(dto);
        }
        return new ResponseEntity<>(accommodationSearchDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST')")
    @GetMapping(value = "/names/{hostId}")
    public ResponseEntity<Collection<AccommodationNameDTO>> getHostAccommodations(@PathVariable Long hostId) {
        Collection<AccommodationNameDTO> accommodations = accommodationService.getHostAccommodations(hostId);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }
}
