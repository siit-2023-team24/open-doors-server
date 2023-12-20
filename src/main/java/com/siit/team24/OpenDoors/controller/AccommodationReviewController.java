package com.siit.team24.OpenDoors.controller;


import com.siit.team24.OpenDoors.dto.accommodationReview.AccommodationReviewDTO;
import com.siit.team24.OpenDoors.dto.accommodationReview.AccommodationReviewDetailsDTO;
import com.siit.team24.OpenDoors.dto.hostReview.HostReviewForHostDTO;
import com.siit.team24.OpenDoors.service.AccommodationReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(value = "open-doors/accommodation-reviews")
public class AccommodationReviewController {

    @Autowired
    private AccommodationReviewService accommodationReviewService;


    AccommodationReviewDTO testAccommodationReviewDTO = new AccommodationReviewDTO(
            (long)384743732, 5, "Very good", new Timestamp(23735834), "test@testmail.com", false, "Hotel Park", 2L
    );

    @GetMapping(value = "/accommodation/{accommodationId}")
    public ResponseEntity<List<AccommodationReviewDetailsDTO>> getAccommodationReviewsForDetails(@PathVariable Long accommodationId) {
        List<AccommodationReviewDetailsDTO> reviews = accommodationReviewService.findAllForAccommodation(accommodationId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
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

    @PutMapping(consumes = "application/json")
    public ResponseEntity<AccommodationReviewDTO> updateAccommodationReview(@RequestBody HostReviewForHostDTO reviewDTO) {
        return new ResponseEntity<>(testAccommodationReviewDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodationReview(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
