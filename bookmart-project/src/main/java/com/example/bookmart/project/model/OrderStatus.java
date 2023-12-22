package com.example.bookmart.project.model;

import jakarta.persistence.*;

@Entity
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne // Assuming a Product belongs to one User
    @JoinColumn(name = "orderstatusdetials_id", nullable = false)
    private OrderStatusDetails orderStatusDetails;


    public  OrderStatus()
    {

    }

    public OrderStatus(Long id, OrderStatusDetails orderStatusDetails) {
        this.id = id;
        this.orderStatusDetails = orderStatusDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatusDetails getOrderStatusDetails() {
        return orderStatusDetails;
    }

    public void setOrderStatusDetails(OrderStatusDetails orderStatusDetails) {
        this.orderStatusDetails = orderStatusDetails;
    }
}
