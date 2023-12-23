package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.OrderStatusDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface OrderStatusDetailsRepository extends JpaRepository<OrderStatusDetails,Long> {
    @Query("SELECT s.id,s.location, s.reachedDate, s.reachedTime ,s.status FROM OrderStatusDetails s WHERE s.orderLine.id = :orderId")
    List<Object[]> findOrderStatusDetailsByOrderId(@Param("orderId") Long orderId);

    List<OrderStatusDetails> findByOrderLineId(Long orderLineId);

    @Query("SELECT s.id,  s.status.id,s.status.name FROM OrderStatusDetails s WHERE s.orderLine.id = :orderId ORDER BY s.reachedDate DESC LIMIT 1")
    List<Object[]> findFirstOrderStatusByOrderIdWithLimit(Long orderId);


}
