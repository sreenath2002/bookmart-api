package com.example.bookmart.project.Request;

import org.springframework.core.SpringVersion;

public class ChangePasswordFromProfileRequest {

    private String prevousPassword;

    private String newPassword;

    public  ChangePasswordFromProfileRequest(){

    }

    public String getPrevousPassword() {
        return prevousPassword;
    }

    public void setPrevousPassword(String prevousPassword) {
        this.prevousPassword = prevousPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
