package com.siit.team24.OpenDoors.dto.review;

import java.util.List;

public class AccommodationReviewsDTO {
    private List<ReviewDetailsDTO> reviews;

    private boolean isReviewable;

    public AccommodationReviewsDTO() {
        this.isReviewable = false;
    };

    public AccommodationReviewsDTO(List<ReviewDetailsDTO> reviews, boolean isReviewable) {
        this.reviews = reviews;
        this.isReviewable = isReviewable;
    }

    public List<ReviewDetailsDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDetailsDTO> reviews) {
        this.reviews = reviews;
    }

    public boolean getIsReviewable() {
        return isReviewable;
    }

    public void setIsReviewable(boolean isReviewable) {
        this.isReviewable = isReviewable;
    }
}
