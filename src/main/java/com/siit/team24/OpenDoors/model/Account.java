package com.siit.team24.OpenDoors.model;

import com.siit.team24.OpenDoors.model.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
@Entity
public class Account {
    @Id
    @Email
    private String email;
    private String password;
    private boolean isBlocked;
    @Enumerated
    private UserRole role;
    @OneToOne
    private User user;
    public Account() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Account{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isBlocked=" + isBlocked +
                ", role=" + role +
                ", user=" + user +
                '}';
    }
}
