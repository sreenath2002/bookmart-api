package com.example.bookmart.project.model;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class PaymentInformation {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paymentType_id", nullable = false)
    private PaymentType paymentType;

    @Column
    private Long amount;




    public  PaymentInformation()
    {

    }

    public PaymentInformation(Long id, User user, PaymentType paymentType, Long amount) {
        this.id = id;
        this.user = user;
        this.paymentType = paymentType;
        this.amount = amount;
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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}



