package com.siit.team24.OpenDoors.dto.userManagement;

import com.siit.team24.OpenDoors.model.UserReport;

import java.sql.Timestamp;

public class UserReportDTO {
    private final Long id;
    private String reportedUsername;
    private String complainantUsername;
    private Timestamp timestamp;
    private String reason;
    private String status;

    public UserReportDTO(Long id, String reportedEmail, String complainantEmail, Timestamp timestamp, String reason, String status) {
        this.id = id;
        this.reportedUsername = reportedEmail;
        this.complainantUsername = complainantEmail;
        this.timestamp = timestamp;
        this.reason = reason;
        this.status = status;
    }

    public UserReportDTO(UserReport userReport) {
        this.id = userReport.getId();
        this.timestamp = userReport.getTimestamp();
        this.reason = userReport.getReason();
        this.status = userReport.getStatus().name();
    }

    public Long getId() {
        return id;
    }

    public String getReportedUsername() {
        return reportedUsername;
    }

    public void setReportedUsername(String reportedUsername) {
        this.reportedUsername = reportedUsername;
    }

    public String getComplainantUsername() {
        return complainantUsername;
    }

    public void setComplainantUsername(String complainantUsername) {
        this.complainantUsername = complainantUsername;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserReportDTO{" +
                "reportedEmail='" + reportedUsername + '\'' +
                ", complainantEmail='" + complainantUsername + '\'' +
                ", timestamp=" + timestamp +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
