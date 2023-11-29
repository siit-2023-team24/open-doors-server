package com.siit.team24.OpenDoors.dto.userManagement;

import com.siit.team24.OpenDoors.model.User;

public class UserAccountViewDTO extends UserDTO {
    private String email;

    private String role;

    public UserAccountViewDTO() {

    }

    public UserAccountViewDTO(Long id, String firstName, String lastName, String phone, String street, int number, String city, String country, Long imageId, String email, String role) {
        super(id, firstName, lastName, phone, street, number, city, country, imageId);
        this.email = email;
        this.role = role;
    }

    public UserAccountViewDTO(User user, String email, String role) {
        super(user);
        this.email = email;
        this.role = role;
    }

    public UserAccountViewDTO(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
                "email='" + email + '\'' +
                "role=" + role + '\'' +
                '}';
    }
}
