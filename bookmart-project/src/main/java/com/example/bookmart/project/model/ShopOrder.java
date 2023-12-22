package com.example.bookmart.project.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ShopOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    private LocalDateTime orderdateandtime;

    @ManyToOne // Assuming a Product belongs to one User
    @JoinColumn(name = "payment_id", nullable = false)
    private PaymentInformation paymentInformation;


    @ManyToOne // Assuming a Product belongs to one User
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

//    @ManyToOne // Assuming a Product belongs to one User
//    @JoinColumn(name = "status_id", nullable = false)
//    private  OrderStatusDetails orderStatusDetails;

    private Long OrderTotal;

    public ShopOrder(){

    }

    public ShopOrder(Long id, Long userId, LocalDateTime orderdateandtime, PaymentInformation paymentInformation, Address address, Long orderTotal) {
        this.id = id;
        this.userId = userId;
        this.orderdateandtime = orderdateandtime;
        this.paymentInformation = paymentInformation;
        this.address = address;
        OrderTotal = orderTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getOrderdateandtime() {
        return orderdateandtime;
    }

    public void setOrderdateandtime(LocalDateTime orderdateandtime) {
        this.orderdateandtime = orderdateandtime;
    }

    public PaymentInformation getPaymentInformation() {
        return paymentInformation;
    }

    public void setPaymentInformation(PaymentInformation paymentInformation) {
        this.paymentInformation = paymentInformation;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Long getOrderTotal() {
        return OrderTotal;
    }

    public void setOrderTotal(Long orderTotal) {
        OrderTotal = orderTotal;
    }
}
