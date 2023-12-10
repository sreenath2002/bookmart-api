package com.example.bookmart.project.Controller;


import com.example.bookmart.project.Exception.ProductException;
import com.example.bookmart.project.Exception.UserException;
import com.example.bookmart.project.Repository.ProductRepository;
import com.example.bookmart.project.Request.CreateProductRequest;
import com.example.bookmart.project.Request.UpdateProductRequest;
import com.example.bookmart.project.Response.CommonResponse;
import com.example.bookmart.project.Service.ProductServiceImp;
import com.example.bookmart.project.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired

    private final ProductServiceImp productServiceImp;

    private final ProductRepository productRepository;


    public UserController(ProductServiceImp productServiceImp,ProductRepository productRepository) {
        this.productServiceImp = productServiceImp;
        this.productRepository=productRepository;
    }

    @PostMapping("/product/createProduct")
    public ResponseEntity<CommonResponse<Object>> createProduct(@RequestBody CreateProductRequest req) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            Product newProduct = productServiceImp.createProduct(req);

            commonResponse.setStatuscode(String.valueOf(HttpStatus.CREATED));
            commonResponse.setResult(newProduct);
            commonResponse.setMessage("Product created successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
        } catch (ProductException e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.BAD_REQUEST));
            commonResponse.setMessage(e.getMessage());

            return new ResponseEntity<>(commonResponse, HttpStatus.BAD_REQUEST);
        } catch (UserException e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.BAD_REQUEST));
            commonResponse.setMessage(e.getMessage());

            return new ResponseEntity<>(commonResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/product/deleteProduct/{productId}")
    public ResponseEntity<CommonResponse<Object>> deleteProduct(@PathVariable Long productId) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            String deletionMessage = productServiceImp.deleteProduct(productId);

            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(deletionMessage);
            commonResponse.setMessage("Product deleted successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (ProductException e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage(e.getMessage());

            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }
    }




    @PutMapping("/product/updateProduct/{productId}")
    public ResponseEntity<CommonResponse<Object>> updateProduct(@PathVariable Long productId, @RequestBody UpdateProductRequest req) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            Product updatedProduct = productServiceImp.updateProduct(productId, req);

            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(updatedProduct);
            commonResponse.setMessage("Product updated successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (ProductException e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage(e.getMessage());

            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }
    }
}
