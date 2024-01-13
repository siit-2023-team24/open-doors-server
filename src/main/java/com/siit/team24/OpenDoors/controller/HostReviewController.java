package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.review.*;
import com.siit.team24.OpenDoors.dto.userManagement.HostPublicDataDTO;
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


    @GetMapping(value = "/{hostId}")
    public ResponseEntity<HostPublicDataDTO> getHostReviewsForProfile(@PathVariable Long hostId, @RequestParam Long guestId) {
        HostPublicDataDTO host = userService.getPublicData(hostId);
        if (guestId != 0) {
            host.setIsReviewable(hostReviewService.isReviewable(hostId, guestId));
        }
        List<ReviewDetailsDTO> reviews = hostReviewService.findAllForHost(hostId);
        host.setReviews(reviews);
        return new ResponseEntity<>(host, HttpStatus.OK);
    }

    @GetMapping(value = "/reported")
    public ResponseEntity<List<ReportedHostReviewDTO>> getAllReported() {
        List<ReportedHostReviewDTO> reviews = hostReviewService.findAllReported();
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

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteHostReview(@PathVariable Long id) {
        this.hostReviewService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/{reviewId}/status")
    public ResponseEntity<Void> changeReportedStatus(@PathVariable Long reviewId){
        hostReviewService.changeReportedStatus(reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
