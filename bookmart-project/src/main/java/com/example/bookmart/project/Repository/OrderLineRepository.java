package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine,Long> {
    List<OrderLine> findByShopOrderId(Long orderId);

    @Query("SELECT ol.id,ol.qty, ol.price,  ol.product ,ol.status FROM OrderLine ol WHERE ol.userId = :userId")
    List<Object[]> findOrderLineDetailsByShopOrderId(@Param("userId") Long userId);

    @Query("SELECT ol.id, ol.qty, ol.price, ol.product, ol.shopOrder.paymentInformation.user.firstName,ol.shopOrder.address,ol.shopOrder.paymentInformation.paymentType,ol.shopOrder.paymentInformation.amount,ol.shopOrder.id FROM OrderLine ol")
    List<Object[]> findOrderLineDetails();
}
