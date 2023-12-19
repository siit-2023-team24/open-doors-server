package com.siit.team24.OpenDoors.dto.userManagement;

import com.siit.team24.OpenDoors.model.User;

public class UserSummaryDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String role;

    public UserSummaryDTO(String email, String firstName, String lastName, String role) {
        this.username = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public UserSummaryDTO(User user){
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole().name();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserSummaryDTO{" +
                "email='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
