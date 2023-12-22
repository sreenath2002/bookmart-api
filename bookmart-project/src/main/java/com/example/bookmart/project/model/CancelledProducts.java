package com.example.bookmart.project.model;

import jakarta.persistence.*;

@Entity
public class CancelledProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderline_id", nullable = false)
    private OrderLine orderLine;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancelreson_id", nullable = false)
    private CancelResons cancelResons;

    public  CancelledProducts(){

    }

    public CancelledProducts(Long id, OrderLine orderLine, CancelResons cancelResons) {
        this.id = id;
        this.orderLine = orderLine;
        this.cancelResons = cancelResons;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderLine getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(OrderLine orderLine) {
        this.orderLine = orderLine;
    }

    public CancelResons getCancelResons() {
        return cancelResons;
    }

    public void setCancelResons(CancelResons cancelResons) {
        this.cancelResons = cancelResons;
    }
}
