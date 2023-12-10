package com.example.bookmart.project.Service;

import com.example.bookmart.project.Exception.CategoryException;
import com.example.bookmart.project.Exception.ProductException;
import com.example.bookmart.project.Exception.UserException;
import com.example.bookmart.project.Request.*;
import com.example.bookmart.project.model.Category;
import com.example.bookmart.project.model.Course;
import com.example.bookmart.project.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {

    public Category createCategory(CreateCategoryRequest req) throws CategoryException;
    public  String deleteCategory(Long categoryId) throws CategoryException;

    Category updateCategory(Long categoryId, UpdateCategoryRequest req) throws CategoryException;

    Course createCategory(CreateCourseCategoryRequest req) throws CategoryException;

    Course createCourseCategory(CreateCourseCategoryRequest req) throws CategoryException;

    String deleteCourseCategory(Long categoryId) throws CategoryException;

    Course updateCategory(Long categoryId, UpdateCourseCategoryRequest req) throws CategoryException;

    Course updateCourseCategory(Long categoryId, UpdateCourseCategoryRequest req) throws CategoryException;

//    public  Category updateCategory(Long categoryId,Category req) throws CategoryException;

}
