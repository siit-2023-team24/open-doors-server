package com.siit.team24.OpenDoors.dto.review;

import com.siit.team24.OpenDoors.model.AccommodationReview;

import java.sql.Timestamp;

public class AccommodationReviewWholeDTO extends NewReviewDTO {
    private Long id;
    private Timestamp timestamp;

    private boolean approved;

    public AccommodationReviewWholeDTO() {
    }

    public AccommodationReviewWholeDTO(AccommodationReview review) {
        this(review.getRating(), review.getComment(), review.getAuthor().getId(), review.getAccommodation().getId(), review.getId(), review.getTimestamp(), review.isApproved());
    }

    public AccommodationReviewWholeDTO(int rating, String comment, Long authorId, Long recipientId, Long id, Timestamp timestamp, boolean approved) {
        super(rating, comment, authorId, recipientId);
        this.id = id;
        this.timestamp = timestamp;
        this.approved = approved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
