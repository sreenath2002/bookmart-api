package com.example.bookmart.project.Service;

import com.example.bookmart.project.Exception.CategoryException;
import com.example.bookmart.project.Repository.CategoryRepository;
import com.example.bookmart.project.Repository.CourseRepository;
import com.example.bookmart.project.Request.CreateCategoryRequest;
import com.example.bookmart.project.Request.CreateCourseCategoryRequest;
import com.example.bookmart.project.Request.UpdateCategoryRequest;
import com.example.bookmart.project.Request.UpdateCourseCategoryRequest;
import com.example.bookmart.project.model.Category;
import com.example.bookmart.project.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CategoryServiceImp implements  CategoryService {


    private final CategoryRepository categoryRepository;

    private  final CourseRepository courseRepository;

    @Autowired
    public CategoryServiceImp(CategoryRepository categoryRepository, CourseRepository courseRepository) {
        this.categoryRepository = categoryRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Category createCategory(CreateCategoryRequest req) throws CategoryException {
        if (categoryRepository.findByName(req.getName()) != null) {
            throw new CategoryException("Category already exists");
        }

        Category newCategory = new Category();
        newCategory.setName(req.getName());
        newCategory.setShowstatus("true");

        return categoryRepository.save(newCategory);
    }


    @Override
    public String deleteCategory(Long categoryId) throws CategoryException {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setShowstatus("false");


            categoryRepository.save(category);

            return "Category with ID " + categoryId + " has been deleted successfully";
        } else {
            throw new CategoryException("Category not found for ID: " + categoryId);
        }
    }

    @Override
    public Category updateCategory(Long categoryId, UpdateCategoryRequest req) throws CategoryException {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        existingCategory.setName(req.getName());

        // Save the changes to the existing category
        return categoryRepository.save(existingCategory);
    }


    @Override
    public Course createCategory(CreateCourseCategoryRequest req) throws CategoryException {
        return null;
    }

    @Override
    public Course createCourseCategory(CreateCourseCategoryRequest req) throws CategoryException {
        // Check if the course already exists by name
        boolean courseExists = courseRepository.existsByCourseName(req.getCourses());
        if (courseExists) {
            throw new CategoryException("Course with name '" + req.getCourses() + "' already exists");
            // Handle the scenario where the course already exists
        }

        Course course = new Course();
        course.setCourseName(req.getCourses());
        course.setShowStatus("true");

        // Fetch the parent category by name
        Category parentCategory = categoryRepository.findByName(req.getParentCategory());
        if (parentCategory == null) {
            throw new CategoryException("Parent category '" + req.getParentCategory() + "' not found");
            // Handle the scenario where the parent category doesn't exist
        }

        course.setParentCategory(parentCategory);

        // Save the new course
        return courseRepository.save(course);
    }

    @Override
    public String deleteCourseCategory(Long categoryId) throws CategoryException {
        Optional<Course> categoryOptional = courseRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            try {
                Course course = categoryOptional.get();
               course.setShowStatus("false");
               courseRepository.save(course);
                return "Course with ID " + categoryId + " has been deleted successfully";
            } catch (Exception e) {
                throw new CategoryException("Failed to delete Course for ID: " + categoryId + ". Error: " + e.getMessage());
                // Handle the deletion failure and throw a meaningful exception
            }
        } else {
            throw new CategoryException("Course not found for ID: " + categoryId);
        }
    }


    @Override
    public Course updateCategory(Long categoryId, UpdateCourseCategoryRequest req) throws CategoryException {
        return null;
    }

    @Override
    public Course updateCourseCategory(Long categoryId, UpdateCourseCategoryRequest req) throws CategoryException {
        Course existingCategory = courseRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        existingCategory.setCourseName(req.getName());

        Category parentCategory = categoryRepository.findByName(req.getParentCategory());
        if (parentCategory == null) {
            throw new CategoryException("Parent category not found");
            // You might handle this situation differently, like creating the category or throwing a different exception.
        }
        existingCategory.setParentCategory(parentCategory);

        // Save the updated course and return the updated entity
        return courseRepository.save(existingCategory);
    }







//    @Override
//    public Category updateCategory(Long categoryId, Category req) throws CategoryException {
//        return null;
//    }
}
