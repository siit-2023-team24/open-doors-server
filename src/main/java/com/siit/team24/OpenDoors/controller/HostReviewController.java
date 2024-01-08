package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.review.*;
import com.siit.team24.OpenDoors.model.Guest;
import com.siit.team24.OpenDoors.model.Host;
import com.siit.team24.OpenDoors.model.HostReview;
import com.siit.team24.OpenDoors.service.HostReviewService;
import com.siit.team24.OpenDoors.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "open-doors/host-reviews")
public class HostReviewController {

    @Autowired
    private HostReviewService hostReviewService;

    @Autowired
    private UserService userService;

//    HostReviewForHostDTO testHostReviewForHostDTO = new HostReviewForHostDTO(
//            (long)34793983, 3, "It's pretty rough", new Timestamp(32864682), "teston@testhoo.co.uk", true
//    );
//
//
//    ReportedHostReviewDTO testReportedHostReviewDTO = new ReportedHostReviewDTO(
//            (long)1485622, 4, "Solid indeed!", new Timestamp(37283472), "howmany@arethere.com", "another@one.com"
//    );

    HostReviewWholeDTO testHostHostReviewWholeDTO = new HostReviewWholeDTO(
            1, "Worst place EVER", (long)2379423, (long)1231241, (long)1234567123, new Timestamp(349834534), true
    );

    @GetMapping(value = "/my/{hostId}")
    public ResponseEntity<List<HostReviewForHostDTO>> getHostReviewsForHost(@PathVariable Long hostId) {
        List<HostReviewForHostDTO> reviews = new ArrayList<>();
//        reviews.add(testHostReviewForHostDTO);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping(value = "/{hostId}")
    public ResponseEntity<HostPublicDataDTO> getHostReviewsForProfile(@PathVariable Long hostId, @RequestParam Long guestId) {
        HostPublicDataDTO host = userService.getPublicData(hostId);
        if (guestId != 0) {
            host.setIsReviewable(hostReviewService.isReviewable(hostId, guestId));
            System.out.println(hostReviewService.isReviewable((long)54, (long)53));
            System.out.println(hostReviewService.isReviewable((long)102, (long)53));
        }
        List<ReviewDetailsDTO> reviews = hostReviewService.findAllForHost(hostId);
        host.setReviews(reviews);
        return new ResponseEntity<>(host, HttpStatus.OK);
    }

    @GetMapping(value = "/reported")
    public ResponseEntity<List<ReportedHostReviewDTO>> getReportedHostReviews() {
        List<ReportedHostReviewDTO> reviews = new ArrayList<>();
//        reviews.add(testReportedHostReviewDTO);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<HostReviewWholeDTO> createHostReview(@RequestBody NewReviewDTO reviewDTO) {
        HostReview review = new HostReview(reviewDTO);
        review.setHost((Host) userService.findById(reviewDTO.getRecipientId()));
        review.setAuthor((Guest) userService.findById(reviewDTO.getAuthorId()));
        hostReviewService.save(review);
        HostReviewWholeDTO returnDto = new HostReviewWholeDTO(review);
        return new ResponseEntity<>(returnDto, HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<HostReviewWholeDTO> updateHostReview(@RequestBody HostReviewForHostDTO reviewDTO) {
        return new ResponseEntity<>(testHostHostReviewWholeDTO, HttpStatus.OK);
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
