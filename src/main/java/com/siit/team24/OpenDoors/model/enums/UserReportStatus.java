package com.siit.team24.OpenDoors.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum UserReportStatus {
    ACTIVE("active"),
    DISMISSED("dismissed"),
    RESOLVED("resolved"),
     DELETED("deleted");

    private final String value;
    UserReportStatus(String value) {
        this.value = value;
    }

    private static final Map<String, UserReportStatus> enumMap = new HashMap<>();

    static {
        for (UserReportStatus status : UserReportStatus.values()) {
            enumMap.put(status.getValue(), status);
        }
    }

    public String getValue() {
        return value;
    }

    public static UserReportStatus fromString(String stringValue) {
        return enumMap.get(stringValue);
    }

}
