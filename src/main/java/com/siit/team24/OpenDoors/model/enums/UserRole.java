package com.siit.team24.OpenDoors.model.enums;

import lombok.Getter;

public enum UserRole {
    GUEST("guest"),
    HOST("host"),
    ADMIN("admin");

    private final String role;
    UserRole(String role) {
        this.role = role;
    }

}
