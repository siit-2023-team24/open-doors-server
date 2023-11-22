package com.siit.team24.OpenDoors.dto.hostReview;

import java.sql.Timestamp;

public class HostReviewProfileDTO {
    protected Long id;
    protected int rating;
    protected String comment;
    protected Timestamp timestamp;
    protected String authorUsername;

    public HostReviewProfileDTO() {
    }

    public HostReviewProfileDTO(Long id, int rating, String comment, Timestamp timestamp, String authorUsername) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = timestamp;
        this.authorUsername = authorUsername;
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
}
