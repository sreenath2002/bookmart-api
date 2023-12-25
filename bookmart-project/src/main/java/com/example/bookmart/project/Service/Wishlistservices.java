package com.example.bookmart.project.Service;


import com.example.bookmart.project.Repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class Wishlistservices {


    private  final WishlistRepository wishlistRepository;

    public Wishlistservices(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public int removeProductFromWishlist(Long productId) {
        System.out.println("---------------------");
        int deletedItemsCount = wishlistRepository.deleteByProductId(productId);
        System.out.println("---CartService okay---------");
        return deletedItemsCount;
    }
    public List<Map<String, Object>> getProductDetailsAndQuantitiesByUserIdFromwishlist(Long userId) {
        List<Object[]> resultList = wishlistRepository.findProductDetailsAndQuantitiesByUserId(userId);
        List<Map<String, Object>> productDetailsList = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> productDetails = new LinkedHashMap<>();
            productDetails.put("wishlistId", result[0]);
            productDetails.put("product", result[1]);


            productDetails.put("quantity", result[2]);
            productDetailsList.add(productDetails);
        }

        return productDetailsList;
    }
    public List<Map<String, Object>> getWishlistDetailsById(Long wishlistId) {
        List<Object[]> resultList = wishlistRepository.findProductDetailsAndQuantitiesByWishlistId(wishlistId);
        List<Map<String, Object>> productDetailsList = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> productDetails = new LinkedHashMap<>();
            productDetails.put("wishlistId", result[0]);
            productDetails.put("product", result[1]);


            productDetails.put("quantity", result[2]);
            productDetailsList.add(productDetails);
        }

        return productDetailsList;
    }
}
