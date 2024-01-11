package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.review.ReviewDetailsDTO;
import com.siit.team24.OpenDoors.model.AccommodationReview;
import com.siit.team24.OpenDoors.repository.AccommodationReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccommodationReviewService {

    @Autowired
    private AccommodationReviewRepository accommodationReviewRepository;

    @Autowired
    private ReservationRequestService reservationRequestService;

    public List<ReviewDetailsDTO> findAllForAccommodation(Long accommodationId) {
        List<AccommodationReview> reviews =  accommodationReviewRepository.findAllByAccommodationId(accommodationId);
        List<ReviewDetailsDTO> dtos = new ArrayList<>();

        for(AccommodationReview ar : reviews) {
                dtos.add(new ReviewDetailsDTO(ar));
        }

        return dtos;
    }

    public boolean isReviewable(Long accommodationId, Long guestId) {
        boolean hasNotYetReviewed = accommodationReviewRepository.findByAccommodationAndAuthor(accommodationId, guestId).isEmpty();
        boolean hasStayed = reservationRequestService.hasStayed(guestId, accommodationId);
        return (hasNotYetReviewed && hasStayed);
    }

    public void save(AccommodationReview review) { accommodationReviewRepository.save(review); }
}
