package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.hostReview.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "open-doors/host-review")
public class HostReviewController {
    //TODO : add authorization
    //service

    HostReviewForHostDTO testHostReviewForHostDTO = new HostReviewForHostDTO(
            (long)34793983, 3, "It's pretty rough", new Timestamp(32864682), "teston@testhoo.co.uk", true
    );

    HostReviewProfileDTO testHostReviewProfileDTO = new HostReviewProfileDTO(
            (long)9873485, 2, "Horrible.", new Timestamp(2837835), "tttest@testestest.rs"
    );

    ReportedHostReviewDTO testReportedHostReviewDTO = new ReportedHostReviewDTO(
            (long)1485622, 4, "Solid indeed!", new Timestamp(37283472), "howmany@arethere.com", "another@one.com"
    );

    HostReviewDTO testHostReviewDTO = new HostReviewDTO(
            1, "Worst place EVER", "ireally@hate.it", "sorry@bro.com", (long)1234567123, new Timestamp(349834534), true
    );

    @GetMapping(value = "/my/{hostId}")
    public ResponseEntity<List<HostReviewForHostDTO>> getHostReviewsForHost(@PathVariable Long hostId) {
        List<HostReviewForHostDTO> reviews = new ArrayList<>();
        reviews.add(testHostReviewForHostDTO);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping(value = "/{hostId}")
    public ResponseEntity<List<HostReviewProfileDTO>> getHostReviewsForProfile(@PathVariable Long hostId) {
        List<HostReviewProfileDTO> reviews = new ArrayList<>();
        reviews.add(testHostReviewProfileDTO);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping(value = "/reported")
    public ResponseEntity<List<ReportedHostReviewDTO>> getReportedHostReviews() {
        List<ReportedHostReviewDTO> reviews = new ArrayList<>();
        reviews.add(testReportedHostReviewDTO);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<HostReviewDTO> createHostReview(@RequestBody NewHostReviewDTO reviewDTO) {
        return new ResponseEntity<>(testHostReviewDTO, HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<HostReviewDTO> updateHostReview(@RequestBody HostReviewForHostDTO reviewDTO) {
        return new ResponseEntity<>(testHostReviewDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteHostReview(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{reviewId}/status")
    public ResponseEntity<Void> changeReviewStatus(@PathVariable Long reviewId,
                                                  @RequestParam boolean isReported){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
