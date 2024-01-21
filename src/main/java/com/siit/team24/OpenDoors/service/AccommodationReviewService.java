package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.notification.NotificationShowDTO;
import com.siit.team24.OpenDoors.dto.review.PendingAccommodationReviewDetailsDTO;
import com.siit.team24.OpenDoors.dto.review.ReviewDetailsDTO;
import com.siit.team24.OpenDoors.model.AccommodationReview;
import com.siit.team24.OpenDoors.repository.AccommodationReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AccommodationReviewService {

    @Autowired
    private AccommodationReviewRepository accommodationReviewRepository;

    public AccommodationReview findById(Long id) {
        Optional<AccommodationReview> review = accommodationReviewRepository.findById(id);
        if (review.isEmpty())
            throw new EntityNotFoundException();
        return review.get();
    }

    public List<ReviewDetailsDTO> findApprovedForAccommodation(Long accommodationId) {
        List<AccommodationReview> reviews =  accommodationReviewRepository.findAllByAccommodationIdApproved(accommodationId);
        List<ReviewDetailsDTO> dtos = new ArrayList<>();

        for(AccommodationReview ar : reviews) {
                dtos.add(new ReviewDetailsDTO(ar));
        }
        dtos.sort(Comparator.comparing(ReviewDetailsDTO::getTimestamp).reversed());
        return dtos;
    }

    public boolean isReviewable(Long accommodationId, Long guestId) {
        boolean hasNotYetReviewed = accommodationReviewRepository.findByAccommodationAndAuthor(accommodationId, guestId).isEmpty();
        return hasNotYetReviewed;
    }

    public void save(AccommodationReview review) { accommodationReviewRepository.save(review); }

    public Double getAverageRating(Long accommodationId) { return accommodationReviewRepository.getAverageRating(accommodationId); }

    public ReviewDetailsDTO findUnapprovedForGuest(Long guestId) {
        List<AccommodationReview> reviews = accommodationReviewRepository.findByGuestId(guestId);
        if (reviews.isEmpty()) return null;
        return new ReviewDetailsDTO(reviews.get(0));
    }

    public void delete(Long id) { accommodationReviewRepository.deleteById(id); }

    public List<PendingAccommodationReviewDetailsDTO> findAllPending() {
        List<PendingAccommodationReviewDetailsDTO> pending = accommodationReviewRepository.findAllPending();
        pending.sort(Comparator.comparing(PendingAccommodationReviewDetailsDTO::getTimestamp).reversed());
        return pending;
    }

    public void approve(Long id) {
        AccommodationReview review = findById(id);
        if (review.isApproved())
            throw new IllegalArgumentException("Accommodation review already approved.");
        review.setApproved(true);
        accommodationReviewRepository.save(review);
    }

    public void deny(Long id) {
        AccommodationReview review = findById(id);
        if (review.isApproved())
            throw new IllegalArgumentException("Accommodation review is already approved.");
        accommodationReviewRepository.deleteById(id);
    }

    public void deleteAllForAccommodation(Long accommodationId) {
        List<AccommodationReview> reviews = accommodationReviewRepository.findAll();
        for (AccommodationReview review: reviews) {
            if (review.getAccommodation().getId() == accommodationId)
                delete(review.getId());
        }
    }
}
