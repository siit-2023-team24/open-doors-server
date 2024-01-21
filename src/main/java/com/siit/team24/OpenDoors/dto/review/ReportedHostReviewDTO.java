package com.siit.team24.OpenDoors.dto.review;

import com.siit.team24.OpenDoors.model.HostReview;

import java.sql.Timestamp;

public class ReportedHostReviewDTO {

    protected Long id;
    protected int rating;
    protected String comment;
    protected Timestamp timestamp;
    protected String authorUsername;
    private String hostUsername;

    public ReportedHostReviewDTO() {}

    public ReportedHostReviewDTO(Long id, int rating, String comment, Timestamp timestamp, String authorUsername, String hostUsername) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = timestamp;
        this.authorUsername = authorUsername;
        this.hostUsername = hostUsername;
    }

    public ReportedHostReviewDTO(HostReview review) {
        this.id = review.getId();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.timestamp = review.getTimestamp();
        this.authorUsername = review.getAuthor().getUsername();
        this.hostUsername = review.getHost().getUsername();
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

    public String getHostUsername() {
        return hostUsername;
    }

    public void setHostUsername(String hostUsername) {
        this.hostUsername = hostUsername;
    }

    @Override
    public String toString() {
        return "ReportedHostReviewDTO{" +
                "id=" + id +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", timestamp=" + timestamp +
                ", authorUsername='" + authorUsername + '\'' +
                ", hostUsername='" + hostUsername + '\'' +
                '}';
    }
}
