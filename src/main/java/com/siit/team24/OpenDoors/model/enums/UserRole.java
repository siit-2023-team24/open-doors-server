package com.siit.team24.OpenDoors.model.enums;

import lombok.Getter;

public enum UserRole {
    GUEST("GUEST"),
    HOST("HOST"),
    ADMIN("ADMIN");

    private final String role;
    UserRole(String role) {
        this.role = role;
    }

}
