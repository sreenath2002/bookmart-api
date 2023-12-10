package com.example.bookmart.project.Request;

public class VerifyOtpRequest {

    private String enteredotp;

    private String email;

    public  VerifyOtpRequest(){

    }

    public String getEnteredotp() {
        return enteredotp;
    }

    public void setEnteredotp(String enteredotp) {
        this.enteredotp = enteredotp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
