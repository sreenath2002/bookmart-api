package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
    @Query("SELECT sc.id,sc.product, sc.quantity FROM ShoppingCart sc WHERE sc.user.id = :userId")
    List<Object[]> findProductDetailsAndQuantitiesByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM ShoppingCart sc WHERE sc.product.id = :productId")
    int deleteByProductId(@Param("productId") Long productId);

}
