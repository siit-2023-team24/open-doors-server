package com.siit.team24.OpenDoors.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class AccommodationReview extends Review {

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Accommodation accommodation;

    private boolean approved;

    public AccommodationReview() {
        super();
    }

    public AccommodationReview(Long id, int rating, String comment, Timestamp timestamp, Guest author,
                               Accommodation accommodation, boolean approved) {
        super(id, rating, comment, timestamp, author);
        this.accommodation = accommodation;
        this.approved = approved;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public String toString() {
        return "AccommodationReview{" +
                "accommodation=" + accommodation +
                ", approved=" + approved +
                ", id=" + id +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", timestamp=" + timestamp +
                ", author=" + author +
                '}';
    }
}
