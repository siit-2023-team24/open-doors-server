package com.siit.team24.OpenDoors.model;

import com.siit.team24.OpenDoors.dto.review.NewReviewDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.sql.Timestamp;

@SQLDelete(sql = "UPDATE accommodation_review SET deleted=true WHERE id=?")
@Where(clause = "deleted=false")
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

    public AccommodationReview(NewReviewDTO dto) {
        this.rating = dto.getRating();
        this.comment = dto.getComment();
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.approved = false;
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
