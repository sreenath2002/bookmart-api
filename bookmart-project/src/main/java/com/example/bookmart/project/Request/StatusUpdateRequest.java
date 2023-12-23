package com.example.bookmart.project.Request;

import java.sql.Time;
import java.util.Date;

public class StatusUpdateRequest {

    private Long orderLineId;
    private Long statusId;
    private Date reachedDate;
    private Time reachedTime;

    private String location;

    public  StatusUpdateRequest()
    {

    }

    public Long getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(Long orderLineId) {
        this.orderLineId = orderLineId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Date getReachedDate() {
        return reachedDate;
    }

    public void setReachedDate(Date reachedDate) {
        this.reachedDate = reachedDate;
    }

    public Time getReachedTime() {
        return reachedTime;
    }

    public void setReachedTime(Time reachedTime) {
        this.reachedTime = reachedTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
