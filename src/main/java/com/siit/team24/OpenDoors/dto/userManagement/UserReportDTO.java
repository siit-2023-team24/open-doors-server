package com.siit.team24.OpenDoors.dto.userManagement;

import com.siit.team24.OpenDoors.model.UserReport;

import java.sql.Timestamp;

public class UserReportDTO {
    private final Long id;
    private String reportedEmail;
    private String complainantEmail;
    private Timestamp timestamp;
    private String reason;
    private String status;
    public UserReportDTO(UserReport userReport) {
        this.id = userReport.getId();
        this.reportedEmail = userReport.getReportedUser().getAccount().getEmail();
        this.complainantEmail = userReport.getComplainant().getAccount().getEmail();
        this.timestamp = userReport.getTimestamp();
        this.reason = userReport.getReason();
        this.status = userReport.getStatus().name();
    }

    public Long getId() {
        return id;
    }

    public String getReportedEmail() {
        return reportedEmail;
    }

    public void setReportedEmail(String reportedEmail) {
        this.reportedEmail = reportedEmail;
    }

    public String getComplainantEmail() {
        return complainantEmail;
    }

    public void setComplainantEmail(String complainantEmail) {
        this.complainantEmail = complainantEmail;
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
                "reportedEmail='" + reportedEmail + '\'' +
                ", complainantEmail='" + complainantEmail + '\'' +
                ", timestamp=" + timestamp +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
