package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine,Long> {
    List<OrderLine> findByShopOrderId(Long orderId);

    @Query("SELECT ol.id,ol.qty, ol.price, ol.orderStatusDetails, ol.product FROM OrderLine ol WHERE ol.shopOrder.id = :shopOrderId")
    List<Object[]> findOrderLineDetailsByShopOrderId(@Param("shopOrderId") Long shopOrderId);
}
