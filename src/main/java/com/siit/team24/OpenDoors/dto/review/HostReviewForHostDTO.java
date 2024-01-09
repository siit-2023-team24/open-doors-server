package com.siit.team24.OpenDoors.dto.review;

import java.sql.Timestamp;

public class HostReviewForHostDTO extends ReviewDetailsDTO {

    private boolean reported;

    public HostReviewForHostDTO() {
    }

    public HostReviewForHostDTO(Long id, int rating, String comment, Timestamp timestamp, String authorUsername, Long authorId, boolean reported) {
        super(id, rating, comment, timestamp, authorUsername, authorId);
        this.reported = reported;
    }

    public boolean isReported() {
        return reported;
    }

    public void setReported(boolean reported) {
        this.reported = reported;
    }
}
