package com.siit.team24.OpenDoors.dto.userManagement;

import com.siit.team24.OpenDoors.model.UserReport;

import java.sql.Timestamp;

public class UserReportDTO extends NewUserReportDTO {
    private final Long id;
    private Timestamp timestamp;
    private String status;

    public UserReportDTO(Long id, String recipientUsername, String complainantUsername, Timestamp timestamp, boolean isComplainantGuest, String reason, String status) {
        super(recipientUsername, complainantUsername, isComplainantGuest, reason);
        this.id = id;
        this.timestamp = timestamp;
        this.status = status;
    }

    public UserReportDTO(UserReport report) {
        this(report.getId(), report.getRecipient().getUsername(), report.getComplainant().getUsername(), report.getTimestamp(), report.getIsComplainantGuest(), report.getReason(), report.getStatus().getValue());
    }

    public Long getId() {
        return id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
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
                "id=" + id +
                ", timestamp=" + timestamp +
                ", status='" + status + '\'' +
                ", recipientUsername='" + recipientUsername + '\'' +
                ", complainantUsername='" + complainantUsername + '\'' +
                ", isComplainantGuest=" + isComplainantGuest +
                ", reason='" + reason + '\'' +
                '}';
    }
}
