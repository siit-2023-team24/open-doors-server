package com.siit.team24.OpenDoors.dto.notification;

import com.siit.team24.OpenDoors.model.User;
import com.siit.team24.OpenDoors.model.enums.NotificationType;

import java.sql.Timestamp;

public class NotificationDTO {
    private Long id;
    private Timestamp timestamp;
    private String username;
    private String message;
    private NotificationType type;

    public NotificationDTO() {
    }

    public NotificationDTO(Long id, Timestamp timestamp, String username, String message, NotificationType type) {
        this.id = id;
        this.timestamp = timestamp;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", username=" + username +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
