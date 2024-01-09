package com.siit.team24.OpenDoors.dto.review;

import com.siit.team24.OpenDoors.model.AccommodationReview;
import com.siit.team24.OpenDoors.model.Review;

import java.sql.Timestamp;

public class ReviewDetailsDTO {
    protected Long id;
    protected int rating;
    protected String comment;
    protected Timestamp timestamp;
    protected String authorUsername;

    protected Long imageId;

    public ReviewDetailsDTO() {
    }

    public ReviewDetailsDTO(Long id, int rating, String comment, Timestamp timestamp, String authorUsername, Long imageId) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = timestamp;
        this.authorUsername = authorUsername;
        this.imageId = imageId;
    }

    public ReviewDetailsDTO(Review review) {
        this(review.getId(),
            review.getRating(),
            review.getComment(),
            review.getTimestamp(),
            review.getAuthor().getUsername(),
            null);
        if (review.getAuthor().getImage()!=null) {
            imageId = review.getAuthor().getImage().getId();
        }
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }
}
