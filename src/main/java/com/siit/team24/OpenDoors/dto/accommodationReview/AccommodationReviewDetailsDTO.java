package com.siit.team24.OpenDoors.dto.accommodationReview;

import com.siit.team24.OpenDoors.model.AccommodationReview;

import java.sql.Timestamp;

public class AccommodationReviewDetailsDTO {
    protected Long id;
    protected int rating;
    protected String comment;
    protected Timestamp timestamp;
    protected String authorUsername;
    protected Long authorId;

    public AccommodationReviewDetailsDTO() {
    }

    public AccommodationReviewDetailsDTO(Long id, int rating, String comment, Timestamp timestamp, String authorUsername, Long authorId) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = timestamp;
        this.authorUsername = authorUsername;
        this.authorId = authorId;
    }

    public AccommodationReviewDetailsDTO(AccommodationReview accommodationReview) {
        this(accommodationReview.getId(),
            accommodationReview.getRating(),
            accommodationReview.getComment(),
            accommodationReview.getTimestamp(),
            accommodationReview.getAuthor().getAccount().getEmail(),
            accommodationReview.getAuthor().getId());
    }

    public Long getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
