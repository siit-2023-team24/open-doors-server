package com.siit.team24.OpenDoors.model;

import com.siit.team24.OpenDoors.model.enums.UserReportStatus;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class UserReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reason;
    private Timestamp timestamp;
    @Enumerated
    private UserReportStatus status;
    @ManyToOne
    private User complainant;
    @ManyToOne
    private User reportedUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public UserReportStatus getStatus() {
        return status;
    }

    public void setStatus(UserReportStatus status) {
        this.status = status;
    }

    public User getComplainant() {
        return complainant;
    }

    public void setComplainant(User complainant) {
        this.complainant = complainant;
    }

    public User getReportedUser() {
        return reportedUser;
    }

    public void setReportedUser(User reportedUser) {
        this.reportedUser = reportedUser;
    }

    @Override
    public String toString() {
        return "UserReport{" +
                "id=" + id +
                ", reason='" + reason + '\'' +
                ", timestamp=" + timestamp +
                ", status=" + status +
                ", complainant=" + complainant +
                ", reportedUser=" + reportedUser +
                '}';
    }

    public UserReport() {
    }
}
