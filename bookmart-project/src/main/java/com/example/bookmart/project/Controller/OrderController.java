package com.example.bookmart.project.Controller;


import com.example.bookmart.project.Repository.*;
import com.example.bookmart.project.Request.OrderLineRequest;
import com.example.bookmart.project.Response.CommonResponse;
import com.example.bookmart.project.model.OrderLine;
import com.example.bookmart.project.model.OrderStatusDetails;
import com.example.bookmart.project.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")

public class OrderController {



    @Autowired

    private final OrderLineRepository orderLineRepository;

    private final OrderStatusDetailsRepository orderStatusDetailsRepository;

    private final StatusRepository statusRepository;

    private final ProductRepository productRepository;

    private final ShopOrderRepository shopOrderRepository;


    public OrderController(OrderLineRepository orderLineRepository, OrderStatusDetailsRepository orderStatusDetailsRepository, StatusRepository statusRepository, ProductRepository productRepository, ShopOrderRepository shopOrderRepository) {
        this.orderLineRepository = orderLineRepository;
        this.orderStatusDetailsRepository = orderStatusDetailsRepository;
        this.statusRepository = statusRepository;
        this.productRepository = productRepository;
        this.shopOrderRepository = shopOrderRepository;
    }

    @PostMapping("/addorder")
    public ResponseEntity<CommonResponse<List<OrderLine>>> orderLine(@RequestBody OrderLineRequest req) {
        CommonResponse<List<OrderLine>> commonResponse = new CommonResponse<>();

        try {
            Long orderId = req.getOrderId();
            List<Long> productIdList = req.getProductIdList();
            List<Integer> qtyList = req.getQty();
            List<Integer> priceList = req.getPrice();
            Optional<Status> optionalStatus = statusRepository.findById(1L);
            Status status = optionalStatus.get();

            if (productIdList.size() != qtyList.size() || productIdList.size() != priceList.size()) {
                commonResponse.setStatuscode(String.valueOf(HttpStatus.BAD_REQUEST));
                commonResponse.setMessage("Invalid request: Product ID list, quantity list, and price list should be of the same size");
                return ResponseEntity.badRequest().body(commonResponse);
            }

            OrderStatusDetails orderStatusDetails = new OrderStatusDetails();
            orderStatusDetails.setStatus(status);

            orderStatusDetailsRepository.save(orderStatusDetails); // Save OrderStatusDetails outside the loop

            List<OrderLine> orderLines = new ArrayList<>();

            for (int i = 0; i < productIdList.size(); i++) {
                Long productId = productIdList.get(i);
                Integer qty = qtyList.get(i);
                Integer price = priceList.get(i);

                OrderLine orderLine = new OrderLine();
                orderLine.setProduct(productRepository.findById(productId).orElse(null));
                orderLine.setShopOrder(shopOrderRepository.findById(orderId).orElse(null));
                orderLine.setQty(qty);
                orderLine.setPrice(price);
                orderLine.setOrderStatusDetails(orderStatusDetails);

                orderLines.add(orderLine);
            }

            orderLineRepository.saveAll(orderLines); // Save all OrderLines together after the loop

            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setMessage("Order lines added successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (Exception e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            commonResponse.setMessage("Error occurred while adding order lines: " + e.getMessage());
            // Handle the exception as required
            return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
