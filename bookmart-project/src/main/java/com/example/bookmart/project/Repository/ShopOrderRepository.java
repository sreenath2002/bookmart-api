package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.ShopOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopOrderRepository extends JpaRepository<ShopOrder,Long> {
    ShopOrder findByUserId(Long userId);
}
