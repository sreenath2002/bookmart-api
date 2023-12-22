package com.example.bookmart.project.Request;

import com.example.bookmart.project.model.Product;

import java.util.List;

public class OrderLineRequest {

    private List<Long> productIdList;

    private Long orderId;

    private List<Integer> qty;

    private List<Integer> price;


    public OrderLineRequest()
    {

    }

    public List<Long> getProductIdList() {
        return productIdList;
    }

    public void setProductIdList(List<Long> productIdList) {
        this.productIdList = productIdList;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<Integer> getQty() {
        return qty;
    }

    public void setQty(List<Integer> qty) {
        this.qty = qty;
    }

    public List<Integer> getPrice() {
        return price;
    }

    public void setPrice(List<Integer> price) {
        this.price = price;
    }
}


