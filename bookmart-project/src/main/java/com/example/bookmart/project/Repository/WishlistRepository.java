package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.ShoppingCart;
import com.example.bookmart.project.model.Userwishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Userwishlist,Long> {

    @Query("SELECT sc.id,sc.product, sc.quantity FROM Userwishlist sc WHERE sc.user.id = :userId")
    List<Object[]> findProductDetailsAndQuantitiesByUserId(@Param("userId") Long userId);

    @Query("SELECT sc.id,sc.product, sc.quantity FROM Userwishlist sc WHERE sc.id = :wishlistId")
    List<Object[]> findProductDetailsAndQuantitiesByWishlistId(@Param("wishlistId") Long wishlistId);
    @Modifying
    @Query("DELETE FROM Userwishlist sc WHERE sc.product.id = :productId")
    int deleteByProductId(@Param("productId") Long productId);


//    @Query("SELECT sc.id,sc.product, sc.quantity FROM ShoppingCart sc WHERE sc.id = :cartId")
//    ShoppingCart findProductDetailsAndQuantitiesByCartID(Long cartId);

    @Query("SELECT DISTINCT up.product.id FROM Userwishlist up WHERE up.user.id = :userId")
    List<Long> findDistinctProductIdsByUserId(@Param("userId") Long userId);

    @Query("SELECT sc.id,sc.product, sc.quantity FROM Userwishlist sc WHERE sc.id = :wishlistId")
    List<Userwishlist> findByWishlistId(@Param("wishlistId") Long wishlistId);
}
