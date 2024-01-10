package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.review.ReviewDetailsDTO;
import com.siit.team24.OpenDoors.model.HostReview;
import com.siit.team24.OpenDoors.repository.HostReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HostReviewService {

    @Autowired
    private HostReviewRepository hostReviewRepository;

    @Autowired
    private ReservationRequestService reservationRequestService;
    public List<ReviewDetailsDTO> findAllForHost(Long hostId) {
        List<HostReview> reviews = hostReviewRepository.findAllByHostId(hostId);
        List<ReviewDetailsDTO> dtos = new ArrayList<>();
        for (HostReview hr : reviews) {
            dtos.add(new ReviewDetailsDTO(hr));
        }
        return dtos;
    }

    public boolean isReviewable(Long hostId, Long guestId) {
        boolean hasNotYetReviewed = hostReviewRepository.findByHostAndAuthor(hostId, guestId).isEmpty();
        boolean hasStayed = reservationRequestService.hasStayed(guestId, hostId);
        return (hasNotYetReviewed && hasStayed);
    }

    public void save(HostReview review) {
        this.hostReviewRepository.save(review);
    }

    public void delete(Long id) { this.hostReviewRepository.deleteById(id); }

    public void changeReportedStatus(Long id) {
        HostReview review = hostReviewRepository.findById(id).get();
        review.setReported(!review.isReported());
        hostReviewRepository.save(review);
    }
}
