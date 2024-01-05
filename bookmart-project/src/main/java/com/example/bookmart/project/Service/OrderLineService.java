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

    private final OrderStatusService orderStatusService;

    public OrderLineService(OrderLineRepository orderLineRepository, OrderStatusService orderStatusService) {
        this.orderLineRepository = orderLineRepository;
        this.orderStatusService = orderStatusService;
    }
    public List<Map<String, Object>> getOrderLineDetails(Long userId) {
        List<Object[]> resultList = orderLineRepository.findOrderLineDetailsByShopOrderId(userId);
        List<Map<String, Object>> orderLineDetailsList = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> orderLineDetails = new LinkedHashMap<>();

            // Assuming the sequence of elements in the resultList matches the expected order of details
            orderLineDetails.put("id", result[0]);

            orderLineDetails.put("quantity", result[1]);
            orderLineDetails.put("price", result[2]);

            orderLineDetails.put("product", result[3]);
            List<Map<String, Object>> orderLines=orderStatusService.getOrderStatues((Long) result[0]);

            orderLineDetails.put("currentstatus" ,orderLines);
            orderLineDetails.put("cancelstatus" ,result[4]);
            orderLineDetailsList.add(orderLineDetails);
        }

        return orderLineDetailsList;
    }
    public List<Map<String, Object>> getOrderDetails() {
        List<Object[]> resultList = orderLineRepository.findOrderLineDetails();
        List<Map<String, Object>> orderLineDetailsList = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> orderLineDetails = new LinkedHashMap<>();

            // Assuming the sequence of elements in the resultList matches the expected order of details
            orderLineDetails.put("id", result[0]);

            orderLineDetails.put("quantity", result[1]);
            orderLineDetails.put("price", result[2]);

            orderLineDetails.put("product", result[3]);
            orderLineDetails.put("user" ,result[4]);
            orderLineDetails.put("address",result[5]);
            orderLineDetails.put("paymentType",result[6]);

            orderLineDetails.put("paymentAmt",result[7]);
            orderLineDetails.put("orderId",result[8]);
            List<Map<String, Object>> statuses = orderStatusService.getOrderStatues((Long) result[0]);
            orderLineDetails.put("currentstatus" ,statuses);
            orderLineDetailsList.add(orderLineDetails);
        }

        return orderLineDetailsList;
    }

}
