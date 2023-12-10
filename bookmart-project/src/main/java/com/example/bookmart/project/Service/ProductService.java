package com.example.bookmart.project.Service;

import com.example.bookmart.project.Exception.ProductException;
import com.example.bookmart.project.Exception.UserException;
import com.example.bookmart.project.Request.CreateProductRequest;
import com.example.bookmart.project.Request.UpdateProductRequest;
import com.example.bookmart.project.Request.UploadImageRequest;
import com.example.bookmart.project.model.Product;
import com.example.bookmart.project.model.ProductImage;
//import org.springframework.data.domain.Page;
//
//import java.util.List;

public interface ProductService {

    public Product createProduct(CreateProductRequest req) throws UserException,ProductException;

    public  String deleteProduct(Long productId) throws ProductException;
//
    public  Product updateProduct(Long productId, UpdateProductRequest req) throws ProductException;

//    public ProductImage addImages(Long productId, UploadImageRequest req)throws  ProductException;
////
//    public  Product findProductById(Long id)throws ProductException;
//
//    public List<Product> findProductByCategory(String category);

//  public  Page<Product>getAllProduct(String category,List<String >subjects,List<String>university,List<String>semster,Integer minPrice,Integer maxPrice, Integer minDiscount,String sort,String stock,Integer pageNumber,Integer pageSize);

}
