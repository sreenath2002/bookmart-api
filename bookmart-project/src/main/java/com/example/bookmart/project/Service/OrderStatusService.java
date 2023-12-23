package com.example.bookmart.project.Service;

import com.example.bookmart.project.Repository.OrderStatusDetailsRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderStatusService {

    private final OrderStatusDetailsRepository orderStatusDetailsRepository;

    public OrderStatusService(OrderStatusDetailsRepository orderStatusDetailsRepository) {
        this.orderStatusDetailsRepository = orderStatusDetailsRepository;
    }
    public List<Map<String, Object>> getOrderStatuesDetails(Long orderId) {
        List<Object[]> resultList = orderStatusDetailsRepository.findOrderStatusDetailsByOrderId(orderId);
        List<Map<String, Object>> orderLineDetailsList = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> orderLineDetails = new LinkedHashMap<>();

            // Assuming the sequence of elements in the resultList matches the expected order of details
            orderLineDetails.put("id", result[0]);
            orderLineDetails.put("location", result[1]);
            orderLineDetails.put("reacheddate", result[2]);

            orderLineDetails.put("reachedtime", result[3]);
            orderLineDetails.put("status", result[4]);
            orderLineDetailsList.add(orderLineDetails);
        }

        return orderLineDetailsList;
    }
    public List<Map<String, Object>> getOrderStatues(Long orderId) {

        List<Object[]> firstOrderStatus = orderStatusDetailsRepository.findFirstOrderStatusByOrderIdWithLimit(orderId);

        List<Map<String, Object>> orderLineDetailsList = new ArrayList<>();

        for (Object[] result : firstOrderStatus) { // Change variable name to firstOrderStatus
            Map<String, Object> orderLineDetails = new LinkedHashMap<>();

            // Assuming the sequence of elements in the resultList matches the expected order of details
            orderLineDetails.put("id", result[0]);
            orderLineDetails.put("statusId",result[1]);
            orderLineDetails.put("status", result[2]);
            orderLineDetailsList.add(orderLineDetails);
        }

        return orderLineDetailsList;
    }

}
