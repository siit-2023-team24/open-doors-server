package com.siit.team24.OpenDoors.controller;


import com.siit.team24.OpenDoors.dto.accommodationReview.AccommodationReviewDTO;
import com.siit.team24.OpenDoors.dto.accommodationReview.AccommodationReviewDetailsDTO;
import com.siit.team24.OpenDoors.dto.hostReview.HostReviewDTO;
import com.siit.team24.OpenDoors.dto.hostReview.HostReviewForHostDTO;
import com.siit.team24.OpenDoors.dto.hostReview.HostReviewProfileDTO;
import com.siit.team24.OpenDoors.dto.hostReview.NewHostReviewDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "open-doors/accommodation-reviews")
public class AccommodationReviewController {

    //service

    @GetMapping(value = "/{accommodationId}")
    public ResponseEntity<List<AccommodationReviewDetailsDTO>> getAccommodationReviewsForDetails(@PathVariable Long accommodationId) {
        List<AccommodationReviewDetailsDTO> reviews = new ArrayList<>();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping(value = "/reported")
    public ResponseEntity<List<AccommodationReviewDTO>> getAccommodationReviewsForDetails() {
        List<AccommodationReviewDTO> reviews = new ArrayList<>();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<AccommodationReviewDTO> createAccommodationReview(@RequestBody AccommodationReviewDTO reviewDTO) {
        return new ResponseEntity<>(new AccommodationReviewDTO(), HttpStatus.CREATED);
    }

    @PutMapping(value = "application/json")
    public ResponseEntity<AccommodationReviewDTO> updateAccommodationReview(@RequestBody HostReviewForHostDTO reviewDTO) {
        return new ResponseEntity<>(new AccommodationReviewDTO(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodationReview(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
