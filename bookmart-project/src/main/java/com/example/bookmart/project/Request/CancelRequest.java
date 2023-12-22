package com.example.bookmart.project.Request;

public class CancelRequest {

    private Long orderLineId;

    private Long reasonId;

    public CancelRequest(){

    }

    public Long getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(Long orderLineId) {
        this.orderLineId = orderLineId;
    }

    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }
}
