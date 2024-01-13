package com.siit.team24.OpenDoors.dto.userManagement;

public class NewUserReportDTO {

    protected String recipientUsername;

    protected String complainantUsername;

    protected boolean isComplainantGuest;

    protected String reason;

    public NewUserReportDTO(String recipientUsername, String complainantUsername, boolean isComplainantGuest, String reason) {
        this.recipientUsername = recipientUsername;
        this.complainantUsername = complainantUsername;
        this.isComplainantGuest = isComplainantGuest;
        this.reason = reason;
    }

    public NewUserReportDTO () {}

    public String getRecipientUsername() {
        return recipientUsername;
    }

    public void setRecipientUsername(String recipientUsername) {
        this.recipientUsername = recipientUsername;
    }

    public String getComplainantUsername() {
        return complainantUsername;
    }

    public void setComplainantUsername(String complainantUsername) {
        this.complainantUsername = complainantUsername;
    }

    public boolean getIsComplainantGuest() {
        return isComplainantGuest;
    }

    public void setIsComplainantGuest(boolean isComplainantGuest) {
        this.isComplainantGuest = isComplainantGuest;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
