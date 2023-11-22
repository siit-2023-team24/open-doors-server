package com.siit.team24.OpenDoors.model;

import com.siit.team24.OpenDoors.model.enums.UserReportStatus;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class UserReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String reason;
    private Timestamp timestamp;
    @Enumerated
    private UserReportStatus status;
    @ManyToOne
    private User complainant;
    @ManyToOne
    private User reportedUser;


    public UserReport() {
    }
}
