package com.example.bookmart.project.Repository;

import com.example.bookmart.project.model.Course;
import com.example.bookmart.project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByCourse(Course course);
    @Query("SELECT p FROM Product p WHERE p.course.courseName IN ('Bsc', 'Msc')")
    List<Product> findProductsByCourseNameBscOrMsc();
    @Query("SELECT p FROM Product p WHERE p.course.courseName IN ('BA', 'MA')")
    List<Product> findProductsByCourseNameBAOrMA();
    @Query("SELECT p FROM Product p WHERE p.course.courseName IN ('BCom', 'Mcom')")
    List<Product> findProductsByCourseNameBComOrMcom();


    @Query("SELECT p.title FROM Product p WHERE p.id = :productId")
    String findTitleById(@Param("productId") Long productId);
//
//    @Query("SELECT p FROM Product p"+ "WHERE (p.category.name=:category OR :category='')" + "AND ((:minPrice IS NULL AND :maxPrice IS NULL ) OR (p.discountedPrice BETWEEN :minPrice AND:maxPrice))"+
//            "AND (:minDiscount IS NULL OR p.discountPresent>=:minDiscount)"+
//            "ORDER BY "+
//            "CASE WHEN :sort = 'price_low' THEN p.discountedPrice END ASC,"+
//            "CASE WHEN :sort = 'price_high' THEN p.discountedPrice END DESC")
//
//    public List<Product>filterProducts(@Param("category")String category,@Param("minPrice")Integer minPrice,
//                                       @Param("maxPrice")Integer maxPrice,@Param("minDiscount")Integer minDiscount,
//                                       @Param("sort")String sort);
}
