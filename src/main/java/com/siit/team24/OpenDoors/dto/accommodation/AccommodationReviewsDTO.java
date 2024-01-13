package com.siit.team24.OpenDoors.dto.accommodation;

import com.siit.team24.OpenDoors.dto.review.ReviewDetailsDTO;

import java.util.List;

public class AccommodationReviewsDTO {
    private List<ReviewDetailsDTO> reviews;

    private boolean isReviewable;

    private Double averageRating;

    private ReviewDetailsDTO unapprovedReview;

    public AccommodationReviewsDTO() {
        this.isReviewable = false;
    };

    public AccommodationReviewsDTO(List<ReviewDetailsDTO> reviews, boolean isReviewable, ReviewDetailsDTO unapprovedReview) {
        this.reviews = reviews;
        this.isReviewable = isReviewable;
        this.averageRating = null;
        this.unapprovedReview = unapprovedReview;
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

    public boolean isReviewable() {
        return isReviewable;
    }

    public void setReviewable(boolean reviewable) {
        isReviewable = reviewable;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public ReviewDetailsDTO getUnapprovedReview() {
        return unapprovedReview;
    }

    public void setUnapprovedReview(ReviewDetailsDTO unapprovedReview) {
        this.unapprovedReview = unapprovedReview;
    }
}
