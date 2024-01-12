package com.siit.team24.OpenDoors.controller;


import com.siit.team24.OpenDoors.dto.accommodation.AccommodationReviewsDTO;
import com.siit.team24.OpenDoors.dto.review.*;
import com.siit.team24.OpenDoors.model.AccommodationReview;
import com.siit.team24.OpenDoors.model.Guest;
import com.siit.team24.OpenDoors.service.AccommodationReviewService;
import com.siit.team24.OpenDoors.service.AccommodationService;
import com.siit.team24.OpenDoors.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(value = "open-doors/accommodation-reviews")
public class AccommodationReviewController {

    @Autowired
    private AccommodationReviewService accommodationReviewService;

    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    private UserService userService;

    AccommodationReviewDTO testAccommodationReviewDTO = new AccommodationReviewDTO(
            //(long)384743732, 5, "Very good", new Timestamp(23735834), "test@testmail.com", (long)2342534, false, "Hotel Park"
    );

    @GetMapping(value = "/{accommodationId}")
    public ResponseEntity<AccommodationReviewsDTO> getAccommodationReviewsForDetails(@PathVariable Long accommodationId, @RequestParam Long guestId) {
        AccommodationReviewsDTO dto = new AccommodationReviewsDTO(
                accommodationReviewService.findApprovedForAccommodation(accommodationId),
                false,
                null);
        if (guestId != 0) {
            dto.setIsReviewable(accommodationReviewService.isReviewable(accommodationId, guestId));
            dto.setUnapprovedReview(accommodationReviewService.findUnapprovedForGuest(guestId));
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @PostMapping(consumes = "application/json")
    public ResponseEntity<AccommodationReviewWholeDTO> createAccommodationReview(@RequestBody NewReviewDTO reviewDTO) {
        AccommodationReview review = new AccommodationReview(reviewDTO);
        review.setAccommodation(accommodationService.findById(reviewDTO.getRecipientId()));
        review.setAuthor((Guest) userService.findById(reviewDTO.getAuthorId()));
        accommodationReviewService.save(review);
        AccommodationReviewWholeDTO returnDto = new AccommodationReviewWholeDTO(review);
        return new ResponseEntity<>(returnDto, HttpStatus.CREATED);
    }


//    @PutMapping(consumes = "application/json")
//    public ResponseEntity<AccommodationReviewDTO> updateAccommodationReview(@RequestBody HostReviewForHostDTO reviewDTO) {
//        return new ResponseEntity<>(testAccommodationReviewDTO, HttpStatus.OK);
//    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodationReview(@PathVariable Long id) {
        accommodationReviewService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/pending")
    public ResponseEntity<List<PendingAccommodationReviewDetailsDTO>> getAllPending() {
        List<PendingAccommodationReviewDetailsDTO> result = accommodationReviewService.findAllPending();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/approve/{id}")
    public ResponseEntity<Void> approve(@PathVariable("id") Long id) {
        accommodationReviewService.approve(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/deny/{id}")
    public ResponseEntity<Void> deny(@PathVariable("id") Long id) {
        accommodationReviewService.deny(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
