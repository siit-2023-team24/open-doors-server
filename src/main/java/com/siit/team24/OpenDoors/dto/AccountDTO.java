package com.siit.team24.OpenDoors.dto;

import com.siit.team24.OpenDoors.model.Account;

public class AccountDTO {
    private final String email;
    private String password;
    public AccountDTO(Account account){
        this.email=account.getEmail();
        this.password=account.getPassword();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
