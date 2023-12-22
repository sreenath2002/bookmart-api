package com.example.bookmart.project.model;

import jakarta.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
public class OrderStatusDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    @Column
    private Date reachedDate;

    @Column
    private String location;
    @Column
    private Time reachedTime;

    @ManyToOne
    @JoinColumn(name = "statusname_id") // Adjust the join column name if necessary
    private Status status;
    @ManyToOne
    @JoinColumn(name = "order_id") // Adjust the join column name if necessary
    private OrderLine orderLine;


    public OrderStatusDetails() {
        // Initialization tasks can be added here if needed
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getReachedDate() {
        return reachedDate;
    }

    public void setReachedDate(Date reachedDate) {
        this.reachedDate = reachedDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Time getReachedTime() {
        return reachedTime;
    }

    public void setReachedTime(Time reachedTime) {
        this.reachedTime = reachedTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public OrderLine getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(OrderLine orderLine) {
        this.orderLine = orderLine;
    }
}
