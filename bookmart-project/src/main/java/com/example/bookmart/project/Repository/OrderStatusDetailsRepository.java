package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.OrderStatusDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusDetailsRepository extends JpaRepository<OrderStatusDetails,Long> {
}
