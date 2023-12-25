package com.example.bookmart.project.Controller;


import com.example.bookmart.project.Repository.*;
import com.example.bookmart.project.Request.OrderLineRequest;
import com.example.bookmart.project.Request.OrderPlacedEmailRequest;
import com.example.bookmart.project.Response.CommonResponse;
import com.example.bookmart.project.Service.OrderPlacedEmailService;
import com.example.bookmart.project.model.OrderLine;
import com.example.bookmart.project.model.OrderStatusDetails;
import com.example.bookmart.project.model.Status;
import com.example.bookmart.project.model.StockManagement;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")

public class OrderController {



    @Autowired

    private final OrderLineRepository orderLineRepository;

    private final OrderStatusDetailsRepository orderStatusDetailsRepository;

    private final StatusRepository statusRepository;

    private final OrderPlacedEmailService orderPlacedEmailService;

    private  final StockManagementRepository stockManagementRepository;
    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final ShopOrderRepository shopOrderRepository;


    public OrderController(OrderLineRepository orderLineRepository, OrderStatusDetailsRepository orderStatusDetailsRepository, StatusRepository statusRepository, OrderPlacedEmailService orderPlacedEmailService, StockManagementRepository stockManagementRepository, UserRepository userRepository, ProductRepository productRepository, ShopOrderRepository shopOrderRepository) {
        this.orderLineRepository = orderLineRepository;
        this.orderStatusDetailsRepository = orderStatusDetailsRepository;
        this.statusRepository = statusRepository;
        this.orderPlacedEmailService = orderPlacedEmailService;
        this.stockManagementRepository = stockManagementRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.shopOrderRepository = shopOrderRepository;
    }

    @PostMapping("/addorder")

    public ResponseEntity<CommonResponse<List<OrderLine>>> orderLine(@RequestBody OrderLineRequest req) {
        CommonResponse<List<OrderLine>> commonResponse = new CommonResponse<>();

        try {
            System.out.println("Haiii sreenath");
            Long orderId = req.getOrderId();
            System.out.println(orderId);
            List<Long> productIdList = req.getProductIdList();
            System.out.println(productIdList);
            List<Integer> qtyList = req.getQty();
            List<Integer> priceList = req.getPrice();
            System.out.println(priceList);
            Optional<Status> optionalStatus = statusRepository.findById(1L);
//            Optional<User> user= userRepository.findById(req.getUserId());
//            User user1=user.get();
            Status status = optionalStatus.get();

            if (productIdList.size() != qtyList.size() || productIdList.size() != priceList.size()) {
                commonResponse.setStatuscode(String.valueOf(HttpStatus.BAD_REQUEST));
                System.out.println("mmmmmm");
                commonResponse.setMessage("Invalid request: Product ID list, quantity list, and price list should be of the same size");
                return ResponseEntity.badRequest().body(commonResponse);
            }



            ; // Save OrderStatusDetails outside the loop

            List<OrderLine> orderLines = new ArrayList<>();

            for (int i = 0; i < productIdList.size(); i++) {
                Long productId = productIdList.get(i);
                Integer qty = qtyList.get(i);
                Integer price = priceList.get(i);
                StockManagement stockManagement=stockManagementRepository.findByProductId(productId);
                 stockManagement.setLeft(stockManagement.getLeft()-qty);
                 stockManagementRepository.save(stockManagement);
                OrderLine orderLine = new OrderLine();
                orderLine.setProduct(productRepository.findById(productId).orElse(null));
                orderLine.setShopOrder(shopOrderRepository.findById(orderId).orElse(null));
                orderLine.setQty(qty);
                orderLine.setUserId(req.getUserId());
                orderLine.setPrice(price);
                orderLine.setStatus("true");


                orderLines.add(orderLine);
                orderLineRepository.save(orderLine);
                OrderStatusDetails orderStatusDetails = new OrderStatusDetails();
                orderStatusDetails.setStatus(status);
                orderStatusDetails.setReachedDate(new Date());
                orderStatusDetails.setOrderLine(orderLine);
                orderStatusDetailsRepository.save(orderStatusDetails);


            }

            ; // Save all OrderLines together after the loop

            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setMessage("Order lines added successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (Exception e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            commonResponse.setMessage("Error occurred while adding order lines: " + e.getMessage());
            System.out.println(e.getMessage());
            // Handle the exception as required
            return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/orderconfirmemail")
    public ResponseEntity<CommonResponse<String>> orderConfirm(@RequestBody OrderPlacedEmailRequest orderPlacedEmailRequest) {
        CommonResponse<String> commonResponse = new CommonResponse<>();

        try {
            System.out.println("100");
            orderPlacedEmailService.sendOrderConfirmEmail(orderPlacedEmailRequest);

            commonResponse.setStatuscode(String.valueOf(HttpStatus.CREATED));
            commonResponse.setMessage("Email Sent Successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
        } catch (MessagingException e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            System.out.println("12");
            System.out.println(e.getMessage());

            commonResponse.setStatuscode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            commonResponse.setMessage("Failed to send email");

            return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
