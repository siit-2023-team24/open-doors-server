package com.siit.team24.OpenDoors.dto.review;

import java.sql.Timestamp;

public class ReportedHostReviewDTO extends ReviewDetailsDTO {
    private String hostUsername;

    public ReportedHostReviewDTO() {
    }

    public ReportedHostReviewDTO(Long id, int rating, String comment, Timestamp timestamp, String authorUsername, Long authorId, String hostUsername) {
        super(id, rating, comment, timestamp, authorUsername, authorId);
        this.hostUsername = hostUsername;
    }

    public String getHostUsername() {
        return hostUsername;
    }

    public void setHostUsername(String hostUsername) {
        this.hostUsername = hostUsername;
    }
}
