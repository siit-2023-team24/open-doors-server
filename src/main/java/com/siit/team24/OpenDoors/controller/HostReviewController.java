package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.review.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "opendoors/hostreview")
public class HostReviewController {

    //service

    @GetMapping(value = "/my/{hostId}")
    public ResponseEntity<List<HostReviewForHostDTO>> getHostReviewsForHost(@PathVariable String hostId) {
        List<HostReviewForHostDTO> reviews = new ArrayList<>();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping(value = "/{hostId}")
    public ResponseEntity<List<HostReviewProfileDTO>> getHostReviewsForProfile(@PathVariable String hostId) {
        List<HostReviewProfileDTO> reviews = new ArrayList<>();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping(value = "/reported")
    public ResponseEntity<List<ReportedHostReviewDTO>> getReportedHostReviews() {
        List<ReportedHostReviewDTO> reviews = new ArrayList<>();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<HostReviewDTO> createHostReview(@RequestBody NewHostReviewDTO reviewDTO) {
        return new ResponseEntity<>(new HostReviewDTO(), HttpStatus.CREATED);
    }

    @PutMapping(value = "application/json")
    public ResponseEntity<HostReviewDTO> updateHostReview(@RequestBody HostReviewForHostDTO reviewDTO) {
        return new ResponseEntity<>(new HostReviewDTO(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteHostReview(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
