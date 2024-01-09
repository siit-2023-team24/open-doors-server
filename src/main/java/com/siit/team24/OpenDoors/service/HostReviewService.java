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
        boolean hasAlreadyReviewed = hostReviewRepository.findByHostAndAuthor(hostId, guestId).isPresent();
        boolean hasStayed = reservationRequestService.hasStayed(guestId, hostId);
        return (!hasAlreadyReviewed && hasStayed);
    }

    public void save(HostReview review) {
        this.hostReviewRepository.save(review);
    }
}
