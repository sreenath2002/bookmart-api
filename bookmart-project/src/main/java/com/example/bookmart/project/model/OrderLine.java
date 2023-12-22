package com.example.bookmart.project.model;

import jakarta.persistence.*;

@Entity
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Assuming a Product belongs to one User
    @JoinColumn(name = "payment_id", nullable = false)
    private Product product;

    @ManyToOne // Assuming a Product belongs to one User
    @JoinColumn(name = "order_id", nullable = false)
    private ShopOrder shopOrder;

   @Column

    private  Integer qty;

   @Column

    private  Integer price;

    @ManyToOne
    @JoinColumn(name = "orderstatus_id", nullable = false)
   private  OrderStatusDetails orderStatusDetails;


   public  OrderLine(){

   }

    public OrderLine(Long id, Product product, ShopOrder shopOrder, Integer qty, Integer price, OrderStatusDetails orderStatusDetails) {
        this.id = id;
        this.product = product;
        this.shopOrder = shopOrder;
        this.qty = qty;
        this.price = price;
        this.orderStatusDetails = orderStatusDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ShopOrder getShopOrder() {
        return shopOrder;
    }

    public void setShopOrder(ShopOrder shopOrder) {
        this.shopOrder = shopOrder;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public OrderStatusDetails getOrderStatusDetails() {
        return orderStatusDetails;
    }

    public void setOrderStatusDetails(OrderStatusDetails orderStatusDetails) {
        this.orderStatusDetails = orderStatusDetails;
    }
}
