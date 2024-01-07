package com.example.bookmart.project.Service;

import com.example.bookmart.project.Repository.*;
import com.example.bookmart.project.model.*;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CancellationService {

    private final OrderLineRepository orderLineRepository;
    private final CancelResonsRepository cancelReasonRepository;
    private final CancelledProductsRepository cancelledProductsRepository;

    private final StockManagementRepository stockManagementRepository;

    private final OrderStatusDetailsRepository orderStatusDetailsRepository;

    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final ProductRepository productRepository;
    private final ShopOrderRepository shopOrderRepository;
    private final PaymentInformationRepository paymentInformationRepository;

    private final WalletRepository walletRepository;

    public CancellationService(OrderLineRepository orderLineRepository, CancelResonsRepository cancelReasonRepository,
                               CancelledProductsRepository cancelledProductsRepository, StockManagementRepository stockManagementRepository, OrderStatusDetailsRepository orderStatusDetailsRepository, UserRepository userRepository, StatusRepository statusRepository, ProductRepository productRepository, ShopOrderRepository shopOrderRepository, PaymentInformationRepository paymentInformationRepository, WalletRepository walletRepository) {
        this.orderLineRepository = orderLineRepository;
        this.cancelReasonRepository = cancelReasonRepository;
        this.cancelledProductsRepository = cancelledProductsRepository;
        this.stockManagementRepository = stockManagementRepository;
        this.orderStatusDetailsRepository = orderStatusDetailsRepository;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
        this.productRepository = productRepository;
        this.shopOrderRepository = shopOrderRepository;
        this.paymentInformationRepository = paymentInformationRepository;
        this.walletRepository = walletRepository;
    }

    public boolean cancelProductOrder(Long orderLineId, Long reasonId) {
        OrderLine orderLine = orderLineRepository.findById(orderLineId).orElse(null);
        CancelResons cancelReason = cancelReasonRepository.findById(reasonId).orElse(null);


        Integer quantity = orderLine.getQty();
       Long id= orderLine.getProduct().getId();
       Long shopOrderId=orderLine.getShopOrder().getId();
       User user=userRepository.findById(orderLine.getUserId()).orElse(null);
       Product product=productRepository.findById(id).orElse(null);
       ShopOrder shopOrder=shopOrderRepository.findById(shopOrderId).orElse(null);
       PaymentInformation paymentInformation=paymentInformationRepository.findById(shopOrder.getPaymentInformation().getId()).orElse(null);
       String paymentmethodname=paymentInformation.getPaymentType().getTypeName();
        OrderStatusDetails orderStatusDetails = new OrderStatusDetails();
        List<Object[]> firstOrderStatus = orderStatusDetailsRepository.findFirstOrderStatusByOrderIdWithLimit(orderLineId);
        Object[] firstStatus = firstOrderStatus.get(0);
        // Assuming the 'location' is at index 2 in the Object array
        String location = (String) firstStatus[2];
        Optional<Status> optionalStatus = statusRepository.findById(6L);
//            Optional<User> user= userRepository.findById(req.getUserId());
//            User user1=user.get();
        Status status = optionalStatus.get();
        if (orderLine != null && cancelReason != null) {
            // Create CancelledProducts entity and save it
            CancelledProducts cancelledProduct = new CancelledProducts();
            cancelledProduct.setOrderLine(orderLine);
            cancelledProduct.setCancelResons(cancelReason);
            cancelledProductsRepository.save(cancelledProduct);
            StockManagement stockManagement=stockManagementRepository.findByProductId(id);
            stockManagement.setLeft(stockManagement.getLeft()+quantity);
            stockManagementRepository.save(stockManagement);
            orderStatusDetails.setLocation(location);
            orderStatusDetails.setReachedTime(Time.valueOf(LocalTime.now()));
            orderStatusDetails.setReachedDate(new Date());
            orderStatusDetails.setOrderLine(orderLine);
            orderStatusDetails.setStatus(status);

            orderStatusDetailsRepository.save(orderStatusDetails);
            if (!paymentmethodname.equals("Cash On Delivery")) {
                // Check if a wallet exists for the user
                Optional<Wallet> existingWallet = walletRepository.findByUser(user);

                Wallet wallet;
                if (existingWallet.isPresent()) {

                    wallet = existingWallet.get();
                    Integer existingAmount = wallet.getAmount();
                    wallet.setAmount(existingAmount + orderLine.getPrice());
                } else {

                    wallet = new Wallet();
                    wallet.setUser(user);
                    wallet.setAmount(orderLine.getPrice());

                    wallet.setCreatedAt(LocalDateTime.now());
                }






                walletRepository.save(wallet);
            }


            // Perform any additional operations related to order cancellation here
            // For example, update order status, send notifications, etc.

            return true; // Cancellation successful
        }

        return false; // Cancellation failed due to invalid orderLineId or reasonId
    }
}
