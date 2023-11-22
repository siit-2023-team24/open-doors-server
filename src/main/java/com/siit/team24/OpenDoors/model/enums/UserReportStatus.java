package com.siit.team24.OpenDoors.model.enums;

import lombok.Getter;

public enum UserReportStatus {
    ACTIVE("active"),
    DISMISSED("dismissed"),
    RESOLVED("resolved");

    private final String status;
    UserReportStatus(String status) {
        this.status = status;
    }

}
