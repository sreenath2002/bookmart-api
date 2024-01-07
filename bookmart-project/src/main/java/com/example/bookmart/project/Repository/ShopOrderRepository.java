package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.ShopOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ShopOrderRepository extends JpaRepository<ShopOrder,Long> {
    ShopOrder findByUserId(Long userId);

    @Query("SELECT so.orderdateandtime, pi.amount FROM ShopOrder so " +
            "JOIN so.paymentInformation pi " +
            "WHERE so.userId = :userId AND pi.paymentType.typeName <> :paymentMethodName")
    List<Object[]> findOrdersByUserIdAndNonCashPaymentMethod(
            @Param("userId") Long userId, @Param("paymentMethodName") String paymentMethodName);

}
