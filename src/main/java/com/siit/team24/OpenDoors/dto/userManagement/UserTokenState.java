package com.siit.team24.OpenDoors.dto.userManagement;

public class UserTokenState {

    private String accessToken;

    private String message;
    private Long expiresIn;

    public UserTokenState() {
        this.accessToken = null;
        this.expiresIn = null;
        this.message = null;
    }

    public UserTokenState(String accessToken, long expiresIn, String message) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.message = message;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}