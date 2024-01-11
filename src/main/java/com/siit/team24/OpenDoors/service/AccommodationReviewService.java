package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.review.ReviewDetailsDTO;
import com.siit.team24.OpenDoors.model.AccommodationReview;
import com.siit.team24.OpenDoors.repository.AccommodationReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccommodationReviewService {

    @Autowired
    private AccommodationReviewRepository accommodationReviewRepository;

    @Autowired
    private ReservationRequestService reservationRequestService;

    public List<ReviewDetailsDTO> findApprovedForAccommodation(Long accommodationId) {
        List<AccommodationReview> reviews =  accommodationReviewRepository.findAllByAccommodationIdApproved(accommodationId);
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

    public Double getAverageRating(Long accommodationId) { return accommodationReviewRepository.getAverageRating(accommodationId); }

    public ReviewDetailsDTO findUnapprovedForGuest(Long guestId) {
        List<AccommodationReview> reviews = accommodationReviewRepository.findByGuestId(guestId);
        if (reviews.isEmpty()) return null;
        return new ReviewDetailsDTO(reviews.get(0));
    }

    public void delete(Long id) { accommodationReviewRepository.deleteById(id); }
}
