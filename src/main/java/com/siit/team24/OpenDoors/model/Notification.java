package com.siit.team24.OpenDoors.model;

import com.siit.team24.OpenDoors.model.enums.NotificationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;

import java.sql.Time;
import java.sql.Timestamp;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp timestamp;
    @Email
    private String user;
    private String message;
    private NotificationType type;

    public Notification() {
    }

    public Notification(Long id, Timestamp timestamp, String user, String message, NotificationType type) {
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
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
