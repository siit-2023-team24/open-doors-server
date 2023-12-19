package com.siit.team24.OpenDoors.controller;


import com.siit.team24.OpenDoors.dto.accommodationReview.AccommodationReviewDTO;
import com.siit.team24.OpenDoors.dto.accommodationReview.AccommodationReviewDetailsDTO;
import com.siit.team24.OpenDoors.dto.hostReview.HostReviewForHostDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "open-doors/accommodation-reviews")
public class AccommodationReviewController {
    //TODO : add authorization
    //service

    AccommodationReviewDetailsDTO testAccommodationReviewDetailsDTO = new AccommodationReviewDetailsDTO(
            (long)8236432, 5, "Very good", new Timestamp(23735834), "test@testmail.com"
    );

    AccommodationReviewDTO testAccommodationReviewDTO = new AccommodationReviewDTO(
            (long)384743732, 5, "Very good", new Timestamp(23735834), "test@testmail.com", false, "Hotel Park"
    );

    @GetMapping(value = "/accommodation/{accommodationId}")
    public ResponseEntity<List<AccommodationReviewDetailsDTO>> getAccommodationReviewsForDetails(@PathVariable Long accommodationId) {
        List<AccommodationReviewDetailsDTO> reviews = new ArrayList<>();
        reviews.add(testAccommodationReviewDetailsDTO);
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
