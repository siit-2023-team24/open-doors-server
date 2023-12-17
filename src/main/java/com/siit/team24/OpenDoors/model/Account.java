package com.siit.team24.OpenDoors.model;

import com.siit.team24.OpenDoors.model.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.sql.Timestamp;

@Entity
public class Account {
    @Id
    @Email
    private String email;
    private String password;
    private boolean isBlocked;
    private Timestamp lastPasswordResetDate;
    @Enumerated
    private UserRole role;
//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private User user;

    public Account() {
    }

    public Account(String email, String password, boolean isBlocked, UserRole role) {
        this.email = email;
        this.password = password;
        this.isBlocked = isBlocked;
        this.role = role;
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

    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    //    public User getUser() {
//        return user;
//    }

//    public void setUser(User user) {
//        this.user = user;
//    }

    @Override
    public String toString() {
        return "Account{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isBlocked=" + isBlocked +
                ", role=" + role +
//                ", user=" + user +
                '}';
    }
}
