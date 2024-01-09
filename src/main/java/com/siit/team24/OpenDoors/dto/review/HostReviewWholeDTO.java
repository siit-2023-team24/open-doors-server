package com.siit.team24.OpenDoors.dto.review;

import com.siit.team24.OpenDoors.model.HostReview;

import java.sql.Timestamp;

public class HostReviewWholeDTO extends NewReviewDTO {
    private Long id;
    private Timestamp timestamp;

    private boolean reported;

    public HostReviewWholeDTO() {
    }

    public HostReviewWholeDTO(HostReview review) {
        this(review.getRating(), review.getComment(), review.getAuthor().getId(), review.getHost().getId(), review.getId(), review.getTimestamp(), review.isReported());
    }

    public HostReviewWholeDTO(int rating, String comment, Long authorId, Long recipientId, Long id, Timestamp timestamp, boolean reported) {
        super(rating, comment, authorId, recipientId);
        this.id = id;
        this.timestamp = timestamp;
        this.reported = reported;
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

    public boolean isReported() {
        return reported;
    }

    public void setReported(boolean reported) {
        this.reported = reported;
    }
}
