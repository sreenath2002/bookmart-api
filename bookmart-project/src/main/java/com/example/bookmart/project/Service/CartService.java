package com.example.bookmart.project.Service;

import com.example.bookmart.project.Repository.ShoppingCartRepository;
import com.example.bookmart.project.model.Product;
import com.example.bookmart.project.model.ShoppingCart;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final ShoppingCartRepository shoppingCartRepository;

    public CartService(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }
    public int removeProductFromCart(Long productId) {
        System.out.println("---------------------");
        int deletedItemsCount = shoppingCartRepository.deleteByProductId(productId);
        System.out.println("---CartService okay---------");
        return deletedItemsCount;
    }

    public List<Map<String, Object>> getProductDetailsAndQuantitiesByUserId(Long userId) {
        List<Object[]> resultList = shoppingCartRepository.findProductDetailsAndQuantitiesByUserId(userId);
        List<Map<String, Object>> productDetailsList = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> productDetails = new LinkedHashMap<>();
            productDetails.put("shoppingCartId", result[0]);
            productDetails.put("product", result[1]);




            productDetails.put("quantity", result[2]);
            productDetailsList.add(productDetails);
        }

        return productDetailsList;
    }
}
