package com.siit.team24.OpenDoors.dto.notification;

import com.siit.team24.OpenDoors.model.User;

import java.sql.Timestamp;

public class NotificationDTO {
    private Long id;
    private Timestamp timestamp;
    private Long userId;
    private String message;
    private String type;

    public NotificationDTO() {
    }

    public NotificationDTO(Long id, Timestamp timestamp, Long userId, String message, String type) {
        this.id = id;
        this.timestamp = timestamp;
        this.userId = userId;
        this.message = message;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", userId=" + userId +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
