package com.example.bookmart.project.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Userpaymet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne // Assuming a Product belongs to one User
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne // Assuming a Product belongs to one User
    @JoinColumn(name = "paymenttype_id", nullable = false)
    private PaymentType paymentType;

    @Column(name = "accountNumber")

    private  Long accountNumber;

    private LocalDateTime paidAt;


    public  Userpaymet(){

    }

    public Userpaymet(Long id, User user, PaymentType paymentType, Long accountNumber, LocalDateTime paidAt) {
        this.id = id;
        this.user = user;
        this.paymentType = paymentType;
        this.accountNumber = accountNumber;
        this.paidAt = paidAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }
}
