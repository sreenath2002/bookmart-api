package com.example.bookmart.project.Request;

public class ShopOrderRequest {



    private  Long paymentInfoId;

    private  Long addresId;



    private Long totalAmount;

     public ShopOrderRequest(){

     }



    public Long getPaymentInfoId() {
        return paymentInfoId;
    }

    public void setPaymentInfoId(Long paymentInfoId) {
        this.paymentInfoId = paymentInfoId;
    }

    public Long getAddresId() {
        return addresId;
    }

    public void setAddresId(Long addresId) {
        this.addresId = addresId;
    }



    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }
}
