package com.siit.team24.OpenDoors.dto.notification;

import com.siit.team24.OpenDoors.model.Notification;

import java.sql.Timestamp;

public class NotificationShowDTO {
    private String type;
    private String message;
    private Timestamp timestamp;

    public NotificationShowDTO(String type, String message, Timestamp timestamp) {
        this.type = type;
        this.message = message;
        this.timestamp = timestamp;
    }

    public NotificationShowDTO(Notification notification){
        this.type = notification.getType().getTypeMessage();
        this.message = notification.getMessage();
        this.timestamp = notification.getTimestamp();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
