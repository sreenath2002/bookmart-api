package com.example.bookmart.project.Service;

import com.example.bookmart.project.Otp.OTPData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class OTPGenerator {


    private static final String OTP_CHARS = "0123456789";
    private static final int OTP_LENGTH = 6;
    private static final int OTP_EXPIRATION_MINUTES = 1;

    public static OTPData generateOTP() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < OTP_LENGTH; i++) {
            int index = random.nextInt(OTP_CHARS.length());
            otp.append(OTP_CHARS.charAt(index));
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = now.plus(OTP_EXPIRATION_MINUTES, ChronoUnit.MINUTES);

        return new OTPData(otp.toString(), now, expirationTime);
    }
}
