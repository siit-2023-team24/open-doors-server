package com.siit.team24.OpenDoors.dto.review;

public class NewHostReviewDTO {
    private int rating;
    private String comment;
    private String authorUsername;
    private String hostUsername;

    public NewHostReviewDTO() {
    }

    public NewHostReviewDTO(int rating, String comment, String authorUsername, String hostUsername) {
        this.rating = rating;
        this.comment = comment;
        this.authorUsername = authorUsername;
        this.hostUsername = hostUsername;
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
}
