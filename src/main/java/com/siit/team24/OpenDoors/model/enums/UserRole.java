package com.siit.team24.OpenDoors.model.enums;

import lombok.Getter;

public enum UserRole {
    ROLE_GUEST("ROLE_GUEST"),
    ROLE_HOST("ROLE_HOST"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String role;
    UserRole(String role) {
        this.role = role;
    }

}
