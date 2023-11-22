package com.siit.team24.OpenDoors.dto.review;

import java.sql.Timestamp;

public class HostReviewDTO extends NewHostReviewDTO {
    private Long id;
    private Timestamp timestamp;

    private boolean reported;

    public HostReviewDTO() {
    }

    public HostReviewDTO(int rating, String comment, String authorUsername, String hostUsername, Long id, Timestamp timestamp, boolean reported) {
        super(rating, comment, authorUsername, hostUsername);
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
