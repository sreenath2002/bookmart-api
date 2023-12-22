package com.example.bookmart.project.Service;

import com.example.bookmart.project.Repository.OrderLineRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderLineService {

    private  final OrderLineRepository orderLineRepository;

    public OrderLineService(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }
    public List<Map<String, Object>> getOrderLineDetails(Long shopOrderId) {
        List<Object[]> resultList = orderLineRepository.findOrderLineDetailsByShopOrderId(shopOrderId);
        List<Map<String, Object>> orderLineDetailsList = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> orderLineDetails = new LinkedHashMap<>();

            // Assuming the sequence of elements in the resultList matches the expected order of details
            orderLineDetails.put("id", result[0]);
            orderLineDetails.put("quantity", result[1]);
            orderLineDetails.put("price", result[2]);
            orderLineDetails.put("orderStatusDetails", result[3]);
            orderLineDetails.put("product", result[4]);

            orderLineDetailsList.add(orderLineDetails);
        }

        return orderLineDetailsList;
    }

}
