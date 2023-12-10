package com.example.bookmart.project.Controller;

import com.example.bookmart.project.Repository.*;
import com.example.bookmart.project.Response.CommonResponse;
import com.example.bookmart.project.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")

public class ProductsController {
    @Autowired
    private final ProductRepository productRepository;
    private  final CategoryRepository categoryRepository;
     private  final CourseRepository courseRepository;
     private  final UniversityRepository universityRepository;

     private  final SemesterRepository semesterRepository;

     private  final SubjectRepository subjectRepository;
    public ProductsController(ProductRepository productRepository, CategoryRepository categoryRepository, CourseRepository courseRepository, UniversityRepository universityRepository, SemesterRepository semesterRepository, SubjectRepository subjectRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.courseRepository = courseRepository;
        this.universityRepository = universityRepository;
        this.semesterRepository = semesterRepository;
        this.subjectRepository = subjectRepository;
    }

    @GetMapping("/getProducts")
    public ResponseEntity<CommonResponse<List<Product>>> getAllProducts() {
        CommonResponse<List<Product>> commonResponse = new CommonResponse<>();
        System.out.println("kjfhdsh");
        try {
            List<Product> products = productRepository.findAll();

            if (!products.isEmpty()) {
                commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
                commonResponse.setResult(products);
                commonResponse.setMessage("All Products retrieved successfully");

                return new ResponseEntity<>(commonResponse, HttpStatus.OK);
            } else {
                commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
                commonResponse.setMessage("No Products found");

                return new ResponseEntity<>(commonResponse, HttpStatus.OK);
            }
        } catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(commonResponse);
        }
    }
    @GetMapping("/getsciencproducts")
    public ResponseEntity<CommonResponse<List<Product>>> getscienceProducts() {
        CommonResponse<List<Product>> commonResponse = new CommonResponse<>();
        System.out.println("haiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
        List<Product> products = productRepository.findProductsByCourseNameBscOrMsc();
        if (!products.isEmpty()) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(products);
            commonResponse.setMessage("Science Products retrieved successfully");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage("Science Products not retrieved");
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getlanguageproducts")
    public ResponseEntity<CommonResponse<List<Product>>> getlanguageProducts() {
        CommonResponse<List<Product>> commonResponse = new CommonResponse<>();
        System.out.println("haiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
        List<Product> products = productRepository.findProductsByCourseNameBAOrMA();
        if (!products.isEmpty()) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(products);
            commonResponse.setMessage("Language Products retrieved successfully");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage("Language Products not retrieved");
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/categoryfilter")
    public ResponseEntity<CommonResponse<List<Category>>> getAllCategoryForFilter() {
        CommonResponse<List<Category>> commonResponse = new CommonResponse<>();

        List<Category> categories = categoryRepository.findAll();

        if (!categories.isEmpty()) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(categories);
            commonResponse.setMessage("All categories retrieved successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage("No categories found");

            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/universityfilter")
    public ResponseEntity<CommonResponse<List<University>>> getAllUniversitiesForFilter() {
        CommonResponse<List<University>> commonResponse = new CommonResponse<>();

        List<University> universities = universityRepository.findAll();

        if (!universities.isEmpty()) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(universities);
            commonResponse.setMessage("All universities retrieved successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage("No universities found");

            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/coursefilter/{categoryName}")
    public ResponseEntity<CommonResponse<List<Course>>> getCourseFilterByCategory(@PathVariable String categoryName) {
        CommonResponse<List<Course>> commonResponse = new CommonResponse<>();
        System.out.println("fghjk");
        Category category = categoryRepository.findByName(categoryName);
        if (category != null) {
            List<Course> courses= courseRepository.findByParentCategory(category);

            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(courses);
            commonResponse.setMessage("Courses for category '" + categoryName + "' retrieved successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage("Category not found: '" + categoryName + "'");

            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/semesterfilter/{categoryName}")
    public ResponseEntity<CommonResponse<List<Semester>>> getSemesterFilterByCategory(@PathVariable String categoryName) {
        CommonResponse<List<Semester>> commonResponse = new CommonResponse<>();

        Category category = categoryRepository.findByName(categoryName);
        if (category != null) {
            List<Semester> semesters = semesterRepository.findByParentCategory(category);

            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(semesters);
            commonResponse.setMessage("Semesters for category '" + categoryName + "' retrieved successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage("Category not found: '" + categoryName + "'");

            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/subjectsFilter/{courseName}")
    public ResponseEntity<CommonResponse<List<Subject>>> getSubjectsFilterByCourse(@PathVariable String courseName) {
        CommonResponse<List<Subject>> commonResponse = new CommonResponse<>();

        Course course = courseRepository.findByCourseName(courseName);
        if (course != null) {
            List<Subject> subjects = subjectRepository.findByCourse(course);

            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(subjects);
            commonResponse.setMessage("Subjects for course '" + courseName + "' retrieved successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage("Course not found: '" + courseName + "'");

            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getcommerceproducts")
    public ResponseEntity<CommonResponse<List<Product>>> getcommerceProducts() {
        CommonResponse<List<Product>> commonResponse = new CommonResponse<>();
        System.out.println("haiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
        List<Product> products = productRepository.findProductsByCourseNameBComOrMcom();
        if (!products.isEmpty()) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(products);
            commonResponse.setMessage("commerce Products retrieved successfully");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage("commerce Products not retrieved");
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/getproduct/{id}")
    public ResponseEntity<CommonResponse<Product>> getAllProduct(@PathVariable Long id) {
        CommonResponse<Product> commonResponse = new CommonResponse<>();
        System.out.println("kjfhdsh");
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
                commonResponse.setResult(product);
                commonResponse.setMessage("Product retrieved successfully");

                return new ResponseEntity<>(commonResponse, HttpStatus.OK);
            } else {
                commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
                commonResponse.setMessage("No Product found");

                return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(commonResponse);
        }
    }

}
