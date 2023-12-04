package com.siit.team24.OpenDoors.dto.userManagement;

public class NewPasswordDTO {
    private Long userId;
    private String oldPassword;
    private String newPassword;
    private String repeatPassword;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOldPasswod() {
        return oldPassword;
    }

    public void setOldPasswod(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public NewPasswordDTO(Long userId, String oldPassword, String newPassword, String repeatPassword) {
        this.userId = userId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.repeatPassword = repeatPassword;
    }

    @Override
    public String toString() {
        return "NewPasswordDTO{" +
                "userId=" + userId +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }


}
