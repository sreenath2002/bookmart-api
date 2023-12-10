package com.example.bookmart.project.Service;

import com.example.bookmart.project.Otp.OTPData;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OtpService {

    @Autowired

    private JavaMailSender javaMailSender;
    public void  sendOtpEmail(String recipientEmail,String otp) throws MessagingException{

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setTo(recipientEmail);
        helper.setSubject("Your OTP for Verification");
        helper.setText("Your OTP is :" + otp);
        javaMailSender.send(message);

    }

    private Map<String, OTPData> otpStorage = new HashMap<>();

    public void storeOTP(String email, OTPData otpData) {
        otpStorage.put(email, otpData);
    }

    public OTPData getStoredOTP(String email) {
        return otpStorage.get(email);
    }

}
