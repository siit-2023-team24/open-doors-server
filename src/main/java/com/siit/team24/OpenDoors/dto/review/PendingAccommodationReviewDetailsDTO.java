package com.siit.team24.OpenDoors.dto.review;

import com.siit.team24.OpenDoors.model.AccommodationReview;

import java.sql.Timestamp;

public class PendingAccommodationReviewDetailsDTO {

    protected Long id;
    protected int rating;
    protected String comment;
    protected Timestamp timestamp;
    protected String authorUsername;
    protected String accommodationName;
    protected String hostUsername;

    public PendingAccommodationReviewDetailsDTO() {
    }

    public PendingAccommodationReviewDetailsDTO(Long id, int rating, String comment, Timestamp timestamp, String authorUsername, String accommodationName) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = timestamp;
        this.authorUsername = authorUsername;
        this.accommodationName = accommodationName;
    }

    public PendingAccommodationReviewDetailsDTO(AccommodationReview review) {
        this.id = review.getId();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.timestamp = review.getTimestamp();
        this.authorUsername = review.getAuthor().getUsername();
        this.accommodationName = review.getAccommodation().getName();
        this.hostUsername = review.getAccommodation().getHost().getUsername();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAccommodationName() {
        return accommodationName;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    @Override
    public String toString() {
        return "PendingAccommodationReviewDetailsDTO{" +
                "id=" + id +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", timestamp=" + timestamp +
                ", authorUsername='" + authorUsername + '\'' +
                ", accommodationName='" + accommodationName + '\'' +
                '}';
    }
}
