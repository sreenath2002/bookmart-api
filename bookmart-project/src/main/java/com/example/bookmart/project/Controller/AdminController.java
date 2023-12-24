package com.example.bookmart.project.Controller;

import com.example.bookmart.project.Exception.CategoryException;
import com.example.bookmart.project.Exception.ProductException;
import com.example.bookmart.project.Exception.UserException;
import com.example.bookmart.project.Repository.*;
import com.example.bookmart.project.Request.*;
import com.example.bookmart.project.Response.CommonResponse;
import com.example.bookmart.project.Service.CategoryServiceImp;
import com.example.bookmart.project.Service.OrderLineService;
import com.example.bookmart.project.Service.OrderStatusService;
import com.example.bookmart.project.Service.ProductServiceImp;
import com.example.bookmart.project.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private final CourseRepository courseRepository;

    private final SubjectRepository subjectRepository;

    private final CategoryRepository categoryRepository;
    private  final SemesterRepository semesterRepository;
    private  final OrderStatusService orderStatusService;
    private final OrderLineRepository orderLineRepository;
    private final OrderStatusDetailsRepository orderStatusDetailsRepository;
    private final  StatusRepository statusRepository;

    private final UniversityRepository universityRepository;

  private final StockManagementRepository stockManagementRepository;

    private final ProductRepository productRepository;

    private final CuponRepository cuponRepository;

    private final UserRepository userRepository;

    private final ProductServiceImp productServiceImp;

    private final CategoryServiceImp categoryServiceImp;

private final OrderLineService orderLineService;

    private final PasswordEncoder passwordEncoder;


    public AdminController(CourseRepository courseRepository, SubjectRepository subjectRepository, CategoryRepository categoryRepository, SemesterRepository semesterRepository, OrderStatusService orderStatusService, OrderLineRepository orderLineRepository, OrderStatusDetailsRepository orderStatusDetailsRepository, StatusRepository statusRepository, UniversityRepository universityRepository, StockManagementRepository stockManagementRepository, ProductRepository productRepository, CuponRepository cuponRepository, UserRepository userRepository, ProductServiceImp productServiceImp, CategoryServiceImp categoryServiceImp, OrderLineService orderLineService, PasswordEncoder passwordEncoder) {
        this.courseRepository = courseRepository;
        this.subjectRepository = subjectRepository;
        this.categoryRepository = categoryRepository;
        this.semesterRepository = semesterRepository;
        this.orderStatusService = orderStatusService;
        this.orderLineRepository = orderLineRepository;
        this.orderStatusDetailsRepository = orderStatusDetailsRepository;
        this.statusRepository = statusRepository;
        this.universityRepository = universityRepository;
        this.stockManagementRepository = stockManagementRepository;

        this.productRepository = productRepository;
        this.cuponRepository = cuponRepository;
        this.userRepository = userRepository;
        this.productServiceImp = productServiceImp;
        this.categoryServiceImp = categoryServiceImp;
        this.orderLineService = orderLineService;

        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/courses")
    public ResponseEntity<CommonResponse<List<Course>>> getAllCourses() {
        CommonResponse<List<Course>> commonResponse = new CommonResponse<>();

        try {
            List<Course> courses = courseRepository.findAll();
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(courses);
            commonResponse.setMessage("Courses retrieved successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (Exception e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            commonResponse.setMessage("Failed to fetch courses");

            return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/addNewuser")
    public ResponseEntity<Map<String, Object>> register(@RequestBody User user) {
        System.out.println("----------------------");
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String  mobile=user.getMobile();

        User isEmailExists = userRepository.findByEmail(email);
        if (isEmailExists != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is Already Exists");
        }

        System.out.println("dfghjklcfvgbnm");

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);
        createdUser.setMobile(mobile);
        createdUser.setRole("USER");
        createdUser.setStatus("UNBLOCK");
        createdUser.setShowstatus("true");

        User savedUser = userRepository.save(createdUser);
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", HttpStatus.CREATED.value());
        response.put("message", "User created successfully");


        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/subjects/{courseName}")
    public ResponseEntity<CommonResponse<List<Subject>>> getSubjectsByCourse(@PathVariable String courseName) {
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

    @GetMapping("/semester/{categoryName}")
    public ResponseEntity<CommonResponse<List<Semester>>> getSemesterByCategory(@PathVariable String categoryName) {
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
    @GetMapping("/course/{categoryName}")
    public ResponseEntity<CommonResponse<List<Course>>> getCourseByCategory(@PathVariable String categoryName) {
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
    @GetMapping("/products/{courseName}")
    public  ResponseEntity<CommonResponse<List<Product>>>getProducts(@PathVariable String courseName) {
        CommonResponse<List<Product>> commonResponse = new CommonResponse<>();
        System.out.println("haiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
        Course course = courseRepository.findByCourseName(courseName);
        if (course != null) {
            List<Product> products = productRepository.findByCourse(course);
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(products);
            commonResponse.setMessage("Products for course'" + courseName + "' retrieved successfully");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage("Products not found: '" + courseName + "'");

            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/universities")
    public ResponseEntity<CommonResponse<List<University>>> getAllUniversities() {
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

    @GetMapping("/category")
    public ResponseEntity<CommonResponse<List<Category>>> getAllCategory() {
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






    @PutMapping("/update/{id}")
    public ResponseEntity<CommonResponse<User>> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setMobile(updatedUser.getMobile());
        existingUser.setPassword(existingUser.getPassword());

        User savedUser = userRepository.save(existingUser);

        CommonResponse<User> commonResponse = new CommonResponse<>();

        if (savedUser != null) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(savedUser);
            commonResponse.setMessage("User updated successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            commonResponse.setMessage("Failed to update user");

            return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/updatestatus/{id}")
    public ResponseEntity<CommonResponse<User>> updateStatus(@PathVariable Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if(existingUser.getStatus().equals("UNBLOCK"))
        {
            existingUser.setStatus("BLOCKED");

        }
        else{
            existingUser.setStatus("UNBLOCK");
        }

        User savedUser = userRepository.save(existingUser);

        CommonResponse<User> commonResponse = new CommonResponse<>();

        if (savedUser != null) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(savedUser);
            commonResponse.setMessage("Status updated successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            commonResponse.setMessage("Failed to update Status");

            return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CommonResponse<String>> deleteUser(@PathVariable Long id) {
        CommonResponse<String> commonResponse = new CommonResponse<>();

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage("User not found");

            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();
        user.setShowstatus("false");
        userRepository.save(user);

        commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
        commonResponse.setMessage("User deleted successfully");

        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @PostMapping("/stockadd")
    public ResponseEntity<CommonResponse<Object>> addStock(@RequestBody StokeAddRequest req) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            Optional<Product> optionalProduct = productRepository.findById(req.getProductId());

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();


                StockManagement stockManagement = new StockManagement();
                stockManagement.setProduct(product);
                stockManagement.setLeft(req.getQtyLeft());
                stockManagement.setTotal(req.getQtyTotal());

                stockManagementRepository.save(stockManagement);

                commonResponse.setStatuscode(String.valueOf(HttpStatus.CREATED));
                commonResponse.setResult(stockManagement);
                commonResponse.setMessage("Stock created successfully");

                return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
            } else {
                commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
                commonResponse.setMessage("Product Not Found for ID: " + req.getProductId());

                return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            commonResponse.setMessage("Failed to create stock: " + e.getMessage());

            return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping ("/stokupdate/{stokeID}")
    public ResponseEntity<CommonResponse<Object>> updateStock(@PathVariable Long stokeID, @RequestBody UpdateStokeRequest req) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            Optional<StockManagement> optionalStockManagement=stockManagementRepository.findById(stokeID);


            if (optionalStockManagement.isPresent()) {
                StockManagement stockManagement = optionalStockManagement.get();



                stockManagement.setLeft(req.getQtyLeft());
                stockManagement.setTotal(req.getQtyTotal());

                stockManagementRepository.save(stockManagement);

                commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
                commonResponse.setResult(stockManagement);
                commonResponse.setMessage("Stock created successfully");

                return new ResponseEntity<>(commonResponse, HttpStatus.OK);
            } else {
                commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
                commonResponse.setMessage("Product Not Found for ID: " );

                return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            commonResponse.setMessage("Failed to create stock: " + e.getMessage());

            return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/addcupon")
    public ResponseEntity<CommonResponse<Object>> addCupon(@RequestBody AdminAddCoouponReQuest req) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            // Validate the incoming request
            if (req.getCode() == null || req.getStartdate() == null || req.getExprDate() == null || req.getDiscountRate() == null) {
                commonResponse.setStatuscode(String.valueOf(HttpStatus.BAD_REQUEST));
                commonResponse.setMessage("Invalid request. Missing required fields.");
                return new ResponseEntity<>(commonResponse, HttpStatus.BAD_REQUEST);
            }

            Cupons cupons = new Cupons();
            cupons.setCode(req.getCode());
            cupons.setValid_fromDate(req.getStartdate());
            cupons.setValid_uptoDate(req.getExprDate());
            cupons.setDiscount(req.getDiscountRate());
            cupons.setIs_activeState(true);

            // Save the coupon
            Cupons savedCoupon = cuponRepository.save(cupons);

            commonResponse.setStatuscode(String.valueOf(HttpStatus.CREATED));
            commonResponse.setResult(savedCoupon);
            commonResponse.setMessage("Coupon created successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);

        } catch (Exception e) {
            // Log the exception details for debugging purposes


            commonResponse.setStatuscode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            commonResponse.setMessage("Failed to create Coupon: " + e.getMessage());

            return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updatecupon/{couponId}")
    public ResponseEntity<CommonResponse<Object>> updateCupon(@PathVariable Long couponId, @RequestBody AdminUpdateCouponRequest req) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            Optional<Cupons> optionalCupons = cuponRepository.findById(couponId);

            if (optionalCupons.isPresent()) {
                Cupons cupons1 = optionalCupons.get();


                if (req.getCode() != null && req.getStartdate() != null && req.getExprDate() != null && req.getDiscountRate() != null) {
                    cupons1.setCode(req.getCode());
                    cupons1.setValid_fromDate(req.getStartdate());
                    cupons1.setValid_uptoDate(req.getExprDate());
                    cupons1.setDiscount(req.getDiscountRate());
                    cupons1.setIs_activeState(true);

                    Cupons updatedCoupon = cuponRepository.save(cupons1);

                    commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
                    commonResponse.setResult(updatedCoupon);
                    commonResponse.setMessage("Coupon updated successfully");

                    return new ResponseEntity<>(commonResponse, HttpStatus.OK);
                } else {
                    commonResponse.setStatuscode(String.valueOf(HttpStatus.BAD_REQUEST));
                    commonResponse.setMessage("Invalid request. Missing required fields.");
                    return new ResponseEntity<>(commonResponse, HttpStatus.BAD_REQUEST);
                }
            } else {
                commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
                commonResponse.setMessage("Coupon with ID " + couponId + " not found.");
                return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {



            commonResponse.setStatuscode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            commonResponse.setMessage("Failed to update Coupon: " + e.getMessage());

            return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getCouponsList")
    public ResponseEntity<CommonResponse<List<Cupons>>> getCouponlist() {
        CommonResponse<List<Cupons>> commonResponse = new CommonResponse<>();
        List<Cupons> coupons = cuponRepository.findAll();

        // Get the current date
        Date currentDate = new Date();

        for (Cupons coupon : coupons) {
            Date validUpto = coupon.getValid_uptoDate();


            if (currentDate.after(validUpto)) {
                coupon.setIs_activeState(false);
                cuponRepository.save(coupon);
            }
        }

        // Retrieve the updated list after setting the status of expired coupons to false
        List<Cupons> updatedCoupons = cuponRepository.findAll();

        if (!updatedCoupons.isEmpty()) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(updatedCoupons);
            commonResponse.setMessage("Coupons retrieved successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setMessage("No Coupons Available");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
    }



    @GetMapping("/getStocksList")
    public ResponseEntity<CommonResponse<List<StockManagement>>> getStocklist(){
        CommonResponse<List<StockManagement>> commonResponse = new CommonResponse<>();
        List<StockManagement> stockManagements = stockManagementRepository.findAll();
        if (!stockManagements.isEmpty()) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(stockManagements);
            commonResponse.setMessage("All Stocks retrieved successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setMessage("No Stocks Available");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }

    }




    @PostMapping("/product/createProduct")
    public ResponseEntity<CommonResponse<Object>> createProduct(@RequestBody CreateProductRequest req) {
        System.out.println("fdgsckjhkjs");
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            System.out.println("wertyjkl");
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

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setStatus("false");
            productRepository.save(product);

            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(product);
            commonResponse.setMessage("Product deleted successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage("Product not found with ID: " + productId);

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

    @PostMapping("/category/createCategory")
    public ResponseEntity<CommonResponse<Object>> createCategory(@RequestBody CreateCategoryRequest req) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            Category newCategory = categoryServiceImp.createCategory(req);

            commonResponse.setStatuscode(String.valueOf(HttpStatus.CREATED));
            commonResponse.setResult(newCategory);
            commonResponse.setMessage("Category created successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
        } catch (CategoryException e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.BAD_REQUEST));
            commonResponse.setMessage(e.getMessage());

            return new ResponseEntity<>(commonResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/category/deleteCategory/{categoryId}")
    public ResponseEntity<CommonResponse<Object>> deleteCategory(@PathVariable Long categoryId) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            String deletionMessage = categoryServiceImp.deleteCategory(categoryId);

            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(deletionMessage);
            commonResponse.setMessage("Category deleted successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (CategoryException e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage(e.getMessage());

            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updatecategory/{categoryId}")
    public ResponseEntity<CommonResponse<Object>> updateCategory(@PathVariable("categoryId") Long categoryId, @RequestBody UpdateCategoryRequest req) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            Category newCategory = categoryServiceImp.updateCategory(categoryId,req);

            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(newCategory);
            commonResponse.setMessage("Category Updated successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
        } catch (CategoryException e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.BAD_REQUEST));
            commonResponse.setMessage(e.getMessage());

            return new ResponseEntity<>(commonResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/coursecategory/createCategory")
    public ResponseEntity<CommonResponse<Object>> createCourseCategory(@RequestBody CreateCourseCategoryRequest req) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            Course newCategory = categoryServiceImp.createCourseCategory(req);

            commonResponse.setStatuscode(String.valueOf(HttpStatus.CREATED));
            commonResponse.setResult(newCategory);
            commonResponse.setMessage("Course created successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
        } catch (CategoryException e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.BAD_REQUEST));
            commonResponse.setMessage(e.getMessage());

            return new ResponseEntity<>(commonResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/category/deleteCourseCategory/{categoryId}")
    public ResponseEntity<CommonResponse<Object>> deleteCourseCategory(@PathVariable Long categoryId) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            String deletionMessage = categoryServiceImp.deleteCourseCategory(categoryId);

            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(deletionMessage);
            commonResponse.setMessage("Course deleted successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (CategoryException e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage("Course not deleted ");

            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updatecourse/{categoryId}")

    public ResponseEntity<CommonResponse<Object>> updateCourseCategory(@PathVariable Long categoryId,@RequestBody UpdateCourseCategoryRequest req) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            Course newCategory = categoryServiceImp.updateCourseCategory(categoryId,req);

            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(newCategory);
            commonResponse.setMessage("Category Updated successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
        } catch (CategoryException e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.BAD_REQUEST));
            commonResponse.setMessage(e.getMessage());

            return new ResponseEntity<>(commonResponse, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/users")
    public ResponseEntity<CommonResponse<List<Map<String, Object>>>> getAllUsers() {
        CommonResponse<List<Map<String, Object>>> commonResponse = new CommonResponse<>();

        List<User> users = userRepository.findAll();

        if (!users.isEmpty()) {
            List<Map<String, Object>> userFieldsList = new ArrayList<>();

            for (User user : users) {
                if(user.getShowstatus().equals("true")) {
                    Map<String, Object> userFields = new HashMap<>();
                    userFields.put("id", user.getId());
                    userFields.put("firstName", user.getFirstName());
                    userFields.put("lastName", user.getLastName());
                    userFields.put("mobileNumber", user.getMobile());
                    userFields.put("email", user.getEmail());
                    userFields.put("role", user.getRole());
                    userFields.put("status",user.getStatus());

                    userFieldsList.add(userFields);
                }
            }

            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(userFieldsList);
            commonResponse.setMessage("All users retrieved successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage("No users found");

            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }
    }



    @GetMapping("/showproducts")
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

    @PutMapping("/productdetails/{id}")
    public ResponseEntity<CommonResponse<Product>> getDetails(@PathVariable Long id) {
        CommonResponse<Product> commonResponse = new CommonResponse<>();

        try {
            Optional<Product> productOptional = productRepository.findById(id);

            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
                commonResponse.setResult(product);
                commonResponse.setMessage("Product Retrieved successfully");

                return new ResponseEntity<>(commonResponse, HttpStatus.OK);
            } else {
                commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
                commonResponse.setMessage("No Product found");

                return new ResponseEntity<>(commonResponse, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(commonResponse);
        }
    }
    @GetMapping("/getallorders")
    public ResponseEntity<CommonResponse<List<Map<String, Object>>>> getUserCart() {
        CommonResponse<List<Map<String, Object>>> commonResponse = new CommonResponse<>();

        List<Map<String, Object>> orderDetails= orderLineService.getOrderDetails();

        if (orderDetails.isEmpty()) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setMessage("Orders are Empty");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(orderDetails);
            commonResponse.setMessage("Orders are Retrieved");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
    }

    @PostMapping("/statuschange")
    public ResponseEntity<CommonResponse<Object>> changeStatus(@RequestBody StatusUpdateRequest req) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();
        try {
            Optional<Status> optionalStatus = statusRepository.findById(req.getStatusId());
            Optional<OrderLine> optionalOrderLine = orderLineRepository.findById(req.getOrderLineId());

            if (optionalStatus.isPresent() && optionalOrderLine.isPresent()) {
                Status status = optionalStatus.get();
                OrderLine orderLine = optionalOrderLine.get();

                OrderStatusDetails orderStatusDetails = new OrderStatusDetails();
                orderStatusDetails.setLocation(req.getLocation());
                orderStatusDetails.setReachedTime(req.getReachedTime());
                orderStatusDetails.setReachedDate(req.getReachedDate());
                orderStatusDetails.setOrderLine(orderLine);
                orderStatusDetails.setStatus(status);

                orderStatusDetailsRepository.save(orderStatusDetails);

                commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
                commonResponse.setMessage("Status updated successfully");
                return new ResponseEntity<>(commonResponse, HttpStatus.OK);
            } else {
                commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
                commonResponse.setMessage("Status or OrderLine not found");
                return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(commonResponse);
        }
    }
    @GetMapping("/allstatues")
    public ResponseEntity<CommonResponse<List<Map<String, Object>>>> getAllStatus() {
        CommonResponse<List<Map<String, Object>>> commonResponse = new CommonResponse<>();

        List<Status> statuses = statusRepository.findAll();

        if (!statuses.isEmpty()) {
            List<Map<String, Object>> statusfield = new ArrayList<>();

            for (Status status : statuses) {

                    Map<String, Object> statuesFields = new HashMap<>();
                statuesFields.put("id", status.getId());
                statuesFields.put("name", status.getName());


                statusfield.add(statuesFields);

            }

            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(statusfield );
            commonResponse.setMessage("All statues retrieved successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage("No status found");

            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/stauses/{orderId}")
    public ResponseEntity<CommonResponse<List<Map<String, Object>>>> getOrderStatus(@PathVariable Long orderId) {
        CommonResponse<List<Map<String, Object>>> commonResponse = new CommonResponse<>();

        List<Map<String, Object>> statuses = orderStatusService.getOrderStatues(orderId);

        if (statuses.isEmpty()) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage("States are Empty");
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(statuses);
            commonResponse.setMessage("stauses are Retrieved");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
    }


}
