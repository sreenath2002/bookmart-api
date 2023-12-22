package com.example.bookmart.project.Request;

public class PaymentRequest {

    private Long paymentId;

    private Long amount;

    public PaymentRequest()
    {

    }





    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
