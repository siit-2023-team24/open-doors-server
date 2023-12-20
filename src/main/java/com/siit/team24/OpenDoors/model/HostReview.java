package com.siit.team24.OpenDoors.model;

import com.siit.team24.OpenDoors.dto.hostReview.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

import java.sql.Timestamp;

@Entity
public class HostReview extends Review {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Host host;

    private boolean reported;

    public HostReview() {
        super();
    }

    public HostReview(Long id, int rating, String comment, Timestamp timestamp, Guest author, Host host, boolean reported) {
        super(id, rating, comment, timestamp, author);
        this.host = host;
        this.reported = reported;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public boolean isReported() {
        return reported;
    }

    public void setReported(boolean reported) {
        this.reported = reported;
    }

    @Override
    public String toString() {
        return "HostReview{" +
                "host=" + host +
                ", reported=" + reported +
                ", id=" + id +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", timestamp=" + timestamp +
                ", author=" + author +
                '}';
    }

    public HostReviewDTO toDTO() {
        return new HostReviewDTO(rating, comment, author.getUsername(),
                host.getUsername(), id, timestamp, reported);
    }

    public HostReviewProfileDTO toProfileDTO() {
        return new HostReviewProfileDTO(id, rating, comment, timestamp, author.getUsername());
    }

    public HostReviewForHostDTO toForHostDTO() {
        return new HostReviewForHostDTO(id, rating, comment, timestamp, author.getUsername(), reported);
    }

    public ReportedHostReviewDTO toReportedDTO() {
        return new ReportedHostReviewDTO(id, rating, comment, timestamp, author.getUsername(),
                host.getUsername());
    }

}
