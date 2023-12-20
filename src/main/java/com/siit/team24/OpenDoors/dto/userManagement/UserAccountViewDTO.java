package com.siit.team24.OpenDoors.dto.userManagement;

import com.siit.team24.OpenDoors.model.User;

public class UserAccountViewDTO extends UserDTO {
    private String username;

    private String role;

    public UserAccountViewDTO() {

    }

    public UserAccountViewDTO(Long id, String firstName, String lastName, String phone, String street, int number, String city, String country, Long imageId, String email, String role) {
        super(id, firstName, lastName, phone, street, number, city, country, imageId);
        this.username = email;
        this.role = role;
    }

    public UserAccountViewDTO(User user, String email, String role) {
        super(user);
        this.username = email;
        this.role = role;
    }

    public UserAccountViewDTO(String email, String role) {
        this.username = email;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserAccountDTO{" +
                "email='" + username + '\'' +
                "role=" + role + '\'' +
                '}';
    }
}
