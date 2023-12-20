package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.accommodationReview.AccommodationReviewDetailsDTO;
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

    public List<AccommodationReviewDetailsDTO> findAllForAccommodation(Long accommodationId) {
        List<AccommodationReview> allReviews =  accommodationReviewRepository.findAll();
        List<AccommodationReviewDetailsDTO> appropriateReviews = new ArrayList<>();

        for(AccommodationReview r: allReviews) {
            if(r.getAccommodation().getId().equals(accommodationId))
                appropriateReviews.add(new AccommodationReviewDetailsDTO(r));
        }

        return appropriateReviews;
    }
}
