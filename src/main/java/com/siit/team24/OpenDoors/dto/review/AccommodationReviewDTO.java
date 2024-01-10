package com.siit.team24.OpenDoors.dto.review;

import java.sql.Timestamp;

public class AccommodationReviewDTO { // extends ReviewDetailsDTO {
    private boolean approved;
    private String accommodationUniqueName;

    public AccommodationReviewDTO(){

    }

//    public AccommodationReviewDTO(Long id, int rating, String comment, Timestamp timestamp, String authorUsername, Long imageId, boolean approved, String accommodationUniqueName) {
//        super(id, rating, comment, timestamp, authorUsername, imageId);
//        this.approved = approved;
//        this.accommodationUniqueName = accommodationUniqueName;
//    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getAccommodationUniqueName() {
        return accommodationUniqueName;
    }

    public void setAccommodationUniqueName(String accommodationUniqueName) {
        this.accommodationUniqueName = accommodationUniqueName;
    }

    @Override
    public String toString() {
        return "AccommodationReviewDTO{" +
                "approved=" + approved +
                ", accommodationUniqueName='" + accommodationUniqueName + '\'' +
                '}';
    }
}
