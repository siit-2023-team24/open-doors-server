package com.siit.team24.OpenDoors.dto.hostReview;

import java.sql.Timestamp;

public class ReportedHostReviewDTO extends HostReviewProfileDTO{
    private String hostUsername;

    public ReportedHostReviewDTO() {
    }

    public ReportedHostReviewDTO(Long id, int rating, String comment, Timestamp timestamp, String authorUsername, String hostUsername) {
        super(id, rating, comment, timestamp, authorUsername);
        this.hostUsername = hostUsername;
    }

    public String getHostUsername() {
        return hostUsername;
    }

    public void setHostUsername(String hostUsername) {
        this.hostUsername = hostUsername;
    }
}
