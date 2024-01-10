package com.siit.team24.OpenDoors.controller;


import com.siit.team24.OpenDoors.dto.review.AccommodationReviewDTO;
import com.siit.team24.OpenDoors.dto.review.AccommodationReviewsDTO;
import com.siit.team24.OpenDoors.dto.review.ReviewDetailsDTO;
import com.siit.team24.OpenDoors.service.AccommodationReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(value = "open-doors/accommodation-reviews")
public class AccommodationReviewController {

    @Autowired
    private AccommodationReviewService accommodationReviewService;


    AccommodationReviewDTO testAccommodationReviewDTO = new AccommodationReviewDTO(
            //(long)384743732, 5, "Very good", new Timestamp(23735834), "test@testmail.com", (long)2342534, false, "Hotel Park"
    );

    @GetMapping(value = "/{accommodationId}")
    public ResponseEntity<AccommodationReviewsDTO> getAccommodationReviewsForDetails(@PathVariable Long accommodationId, @RequestParam Long guestId) {
        AccommodationReviewsDTO dto = new AccommodationReviewsDTO(accommodationReviewService.findAllForAccommodation(accommodationId), false);
        if (guestId != 0) {
            dto.setIsReviewable(accommodationReviewService.isReviewable(accommodationId, guestId));
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/reported")
    public ResponseEntity<List<AccommodationReviewDTO>> getAccommodationReviewsForDetails() {
        List<AccommodationReviewDTO> reviews = new ArrayList<>();
        reviews.add(testAccommodationReviewDTO);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<AccommodationReviewDTO> createAccommodationReview(@RequestBody AccommodationReviewDTO reviewDTO) {
        return new ResponseEntity<>(testAccommodationReviewDTO, HttpStatus.CREATED);
    }

//    @PutMapping(consumes = "application/json")
//    public ResponseEntity<AccommodationReviewDTO> updateAccommodationReview(@RequestBody HostReviewForHostDTO reviewDTO) {
//        return new ResponseEntity<>(testAccommodationReviewDTO, HttpStatus.OK);
//    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodationReview(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
