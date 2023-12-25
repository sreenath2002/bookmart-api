package com.example.bookmart.project.Service;


import com.example.bookmart.project.Repository.ProductRepository;
import com.example.bookmart.project.Request.OrderPlacedEmailRequest;
import com.example.bookmart.project.model.OrderLine;
import com.example.bookmart.project.model.OrderStatusDetails;
import com.example.bookmart.project.model.StockManagement;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderPlacedEmailService {

    @Autowired

    private JavaMailSender javaMailSender;

    private final ProductRepository productRepository;

    public OrderPlacedEmailService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void sendOrderConfirmEmail(OrderPlacedEmailRequest req) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(req.getEnteredemail());
        helper.setSubject("Thank You For Shopping From Bookmart");

        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Your Order Details\n\n");

        List<Long> productIdList = req.getProductIdList();
        List<Integer> qtyList = req.getQty();
        List<Integer> priceList = req.getPrice();
        Integer total = 0;

        for (int i = 0; i < productIdList.size(); i++) {
            Long productId = productIdList.get(i);
            Integer qty = qtyList.get(i);
            Integer price = priceList.get(i);
            total += qty * price;
            System.out.println("bibibib");
            System.out.println(productRepository.findTitleById(productId));
            emailContent.append("Product Name: ").append(productRepository.findTitleById(productId)).append("\n");
            System.out.println("mmmmmnssmsnsm");
            emailContent.append("Product Price: ").append(price).append("\n");
            emailContent.append("Quantity: ").append(qty).append("\n");
            emailContent.append("Total: ").append(qty * price).append("\n\n");
        }

        emailContent.append("Order Total Amount: ").append(total).append("\n");

        helper.setText(emailContent.toString());
        javaMailSender.send(message);
    }

}
