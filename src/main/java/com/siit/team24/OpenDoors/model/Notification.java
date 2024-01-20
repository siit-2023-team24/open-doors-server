package com.siit.team24.OpenDoors.model;

import com.siit.team24.OpenDoors.dto.notification.NotificationDTO;
import com.siit.team24.OpenDoors.model.enums.NotificationType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp timestamp;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private User user;
    private String message;
    @Enumerated
    private NotificationType type;

    public Notification() {
    }

    public Notification(Long id, Timestamp timestamp, User user, String message, NotificationType type) {
        this.id = id;
        this.timestamp = timestamp;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        return "Notification{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", user='" + user + '\'' +
                ", message='" + message + '\'' +
                ", type=" + type +
                '}';
    }
}
