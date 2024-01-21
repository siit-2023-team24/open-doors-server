package com.siit.team24.OpenDoors.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.sql.Timestamp;

@Where(clause = "deleted=false")
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id;

    @Min(1)
    @Max(5)
    protected int rating;
    protected String comment;
    protected Timestamp timestamp;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    protected Guest author;

    protected boolean deleted;

    public Review() {
    }

    public Review(Long id, int rating, String comment, Timestamp timestamp, Guest author) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = timestamp;
        this.author = author;
    }

    public Long getId() {
        return id;
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

    public Guest getAuthor() {
        return author;
    }

    public void setAuthor(Guest author) {
        this.author = author;
    }
}
