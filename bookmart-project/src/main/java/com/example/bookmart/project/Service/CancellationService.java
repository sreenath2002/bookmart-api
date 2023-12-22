package com.example.bookmart.project.Service;

import com.example.bookmart.project.Repository.CancelResonsRepository;
import com.example.bookmart.project.Repository.CancelledProductsRepository;
import com.example.bookmart.project.Repository.OrderLineRepository;
import com.example.bookmart.project.model.CancelResons;
import com.example.bookmart.project.model.CancelledProducts;
import com.example.bookmart.project.model.OrderLine;
import org.springframework.stereotype.Service;

@Service
public class CancellationService {

    private final OrderLineRepository orderLineRepository;
    private final CancelResonsRepository cancelReasonRepository;
    private final CancelledProductsRepository cancelledProductsRepository;

    public CancellationService(OrderLineRepository orderLineRepository, CancelResonsRepository cancelReasonRepository,
                               CancelledProductsRepository cancelledProductsRepository) {
        this.orderLineRepository = orderLineRepository;
        this.cancelReasonRepository = cancelReasonRepository;
        this.cancelledProductsRepository = cancelledProductsRepository;
    }

    public boolean cancelProductOrder(Long orderLineId, Long reasonId) {
        OrderLine orderLine = orderLineRepository.findById(orderLineId).orElse(null);
        CancelResons cancelReason = cancelReasonRepository.findById(reasonId).orElse(null);

        if (orderLine != null && cancelReason != null) {
            // Create CancelledProducts entity and save it
            CancelledProducts cancelledProduct = new CancelledProducts();
            cancelledProduct.setOrderLine(orderLine);
            cancelledProduct.setCancelResons(cancelReason);
            cancelledProductsRepository.save(cancelledProduct);

            // Perform any additional operations related to order cancellation here
            // For example, update order status, send notifications, etc.

            return true; // Cancellation successful
        }

        return false; // Cancellation failed due to invalid orderLineId or reasonId
    }
}
