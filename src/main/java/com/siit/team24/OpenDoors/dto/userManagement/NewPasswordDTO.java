package com.siit.team24.OpenDoors.dto.userManagement;

public class NewPasswordDTO {
    private String user;
    private String oldPassword;
    private String newPassword;
    private String repeatPassword;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public NewPasswordDTO(String user, String oldPassword, String newPassword, String repeatPassword) {
        this.user = user;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.repeatPassword = repeatPassword;
    }

    @Override
    public String toString() {
        return "NewPasswordDTO{" +
                "user=" + user +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }


}
