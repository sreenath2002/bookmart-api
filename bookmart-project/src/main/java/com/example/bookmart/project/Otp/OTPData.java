package com.example.bookmart.project.Otp;

import java.time.LocalDateTime;

public class OTPData {
    private String otp;
    private LocalDateTime createdAt;
    private LocalDateTime expirationTime;

    public  OTPData(){

    }

    public OTPData(String otp, LocalDateTime createdAt, LocalDateTime expirationTime) {
        this.otp = otp;
        this.createdAt = createdAt;
        this.expirationTime = expirationTime;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }
}
