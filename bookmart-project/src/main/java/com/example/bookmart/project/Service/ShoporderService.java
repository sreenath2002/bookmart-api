package com.example.bookmart.project.Service;


import com.example.bookmart.project.Repository.ShopOrderRepository;
import com.example.bookmart.project.model.ShopOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoporderService {

    private final ShopOrderRepository shopOrderRepository;

    public ShoporderService(ShopOrderRepository shopOrderRepository) {
        this.shopOrderRepository = shopOrderRepository;
    }

    public List<Object[]> getOrdersForUserWithNonCashPayments(Long userId) {
        return shopOrderRepository.findOrdersByUserIdAndNonCashPaymentMethod(userId, "Cash on delivery");
    }
}
