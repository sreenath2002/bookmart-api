package com.example.bookmart.project.Controller;


import com.example.bookmart.project.Configuration.JwtProvider;
import com.example.bookmart.project.Exception.CategoryException;
import com.example.bookmart.project.Exception.ProductException;
import com.example.bookmart.project.Exception.SubCategoryException;
import com.example.bookmart.project.Exception.UserException;
import com.example.bookmart.project.Otp.OTPData;
import com.example.bookmart.project.Repository.*;
import com.example.bookmart.project.Request.*;
import com.example.bookmart.project.Response.AuthResponse;
import com.example.bookmart.project.Response.CommonResponse;
import com.example.bookmart.project.Service.*;
import com.example.bookmart.project.model.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserServiceImp userServiceImp;
    private final ProductServiceImp productServiceImp;

    private final CategoryServiceImp categoryServiceImp;
    private final OtpService otpService;

    private final CourseRepository courseRepository;

    private final UniversityRepository universityRepository;

    private final CategoryRepository categoryRepository;


    private final SubjectRepository subjectRepository;

    private  final OTPGenerator otpGenerator;
    private final SemesterRepository semesterRepository;

    public AuthController(UserRepository userRepository, UserServiceImp userServiceImp, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, ProductServiceImp productServiceImp, CategoryServiceImp categoryServiceImp, OtpService otpService, CourseRepository courseRepository, UniversityRepository universityRepository, CategoryRepository categoryRepository,  SubjectRepository subjectRepository,  OTPGenerator otpGenerator, SemesterRepository semesterRepository) {
        this.userRepository = userRepository;
        this.userServiceImp = userServiceImp;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.productServiceImp = productServiceImp;
        this.categoryServiceImp = categoryServiceImp;
        this.otpService=otpService;
        this.courseRepository = courseRepository;
        this.universityRepository = universityRepository;
        this.categoryRepository = categoryRepository;

        this.subjectRepository = subjectRepository;

        this.otpGenerator = otpGenerator;
        this.semesterRepository = semesterRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> register(@RequestBody User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String  mobile=user.getMobile();

        User isEmailExists = userRepository.findByEmail(email);
        if (isEmailExists != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is Already Exists");
        }


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


    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        User user = userRepository.findByEmail(email);

        if (user == null) {
            // If the user is not found, return a response indicating that the email does not exist
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("User with this email does not exist");
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } else if (user.getStatus().equals("BLOCKED")) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("USER IS BLOCKED");
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } else if ( user.getShowstatus().equals("false")) {

            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("USER IS DELETED");
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } else {


        Authentication authentication = authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);


        authResponse.setMessage("Sign in succecsfully");
        authResponse.setId(user.getId());
        authResponse.setEmail(user.getEmail());
        authResponse.setRole(user.getRole());
        authResponse.setFirstName(user.getFirstName());
        authResponse.setLastName(user.getLastName());
        authResponse.setMobile(user.getMobile());

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
    }

    @PostMapping("/emailexists")
    public ResponseEntity<CommonResponse<String>> emailExists(@RequestBody EmailExistsRequest emailExistsRequest) {
        CommonResponse<String> commonResponse = new CommonResponse<>();

        User isEmailExists = userRepository.findByEmail(emailExistsRequest.getEmail());

        if (isEmailExists != null) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult("Email Exists");
            commonResponse.setMessage("Success");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult("Email does not exist");
            commonResponse.setMessage("Failed");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
    }




    @PostMapping("/send-otp")
    public ResponseEntity<CommonResponse<String>> sendOtp(@RequestBody SendOtpRequest sendOtpRequest) {
        OTPData otpData = otpGenerator.generateOTP();
        otpService.storeOTP(sendOtpRequest.getEmail(), otpData);

        CommonResponse<String> commonResponse= new CommonResponse<>();

        try {
            otpService.sendOtpEmail(sendOtpRequest.getEmail(), otpData.getOtp());
            commonResponse.setStatuscode(String.valueOf(HttpStatus.CREATED));
            commonResponse.setResult(otpData.getOtp());
           commonResponse.setMessage("Otp Sent SuccesFully");

            return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);

        } catch (MessagingException e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
           commonResponse.setStatuscode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
           commonResponse.setMessage("Failed to send OTP");

            return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<CommonResponse<String>> verifyOtp(@RequestBody VerifyOtpRequest verifyOtpRequest) {
        CommonResponse<String> commonResponse = new CommonResponse<>();
        OTPData storedOTPData = otpService.getStoredOTP(verifyOtpRequest.getEmail());
        
        if (storedOTPData != null && verifyOtpRequest.getEnteredotp().equals(storedOTPData.getOtp())) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setMessage("OTP Verified Successfully");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setMessage("OTP verification failed");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
    }


    //    @GetMapping("/users")
//    public ResponseEntity<List<User>> getAllUsers() {
//        List<User> users = userRepository.findAll();
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }
//    @GetMapping("/courses")
//    public ResponseEntity<List<Course>> getAllCourses() {
//        List<Course> courses = courseRepository.findAll();
//        return new ResponseEntity<>(courses, HttpStatus.OK);
//    }
//
//    @GetMapping("/subjects/{courseName}")
//    public ResponseEntity<List<Subject>> getSubjectsByCourse(@PathVariable String courseName) {
//        Course course = courseRepository.findByCourseName(courseName);
//        if (course != null) {
//            List<Subject> subjects = subjectRepository.findByCourse(course);
//            return new ResponseEntity<>(subjects, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//    @GetMapping("/semester/{categoryName}")
//    public ResponseEntity<List<Semester>> getSemesterByCategory(@PathVariable String categoryName) {
//        System.out.println("slgdjhlvjhldksjh");
//       Category category = categoryRepository.findByName(categoryName);
//        if (category != null) {
//            System.out.println("slgdjhlvjhldksjh");
//            List<Semester> semesters = semesterRepository.findByParentCategory(category);
//            return new ResponseEntity<>(semesters, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping("/universities")
//    public ResponseEntity<List<University>> getAllUniversities() {
//        List<University> universities = universityRepository.findAll();
//        return new ResponseEntity<>(universities, HttpStatus.OK);
//    }
//    @GetMapping("/category")
//    public ResponseEntity<List<Category>> getAllCategory() {
//        List<Category> categories= categoryRepository.findAll();
//        return new ResponseEntity<>(categories, HttpStatus.OK);
//    }
//    @GetMapping("/subcategory")
//    public ResponseEntity<List<SubCategory>> getAllSubCategory() {
//        List<SubCategory> subCategories= subCategoryRepository.findAll();
//        return new ResponseEntity<>(subCategories, HttpStatus.OK);
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
//        User existingUser = userRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
//
//        existingUser.setEmail(updatedUser.getEmail());
//        existingUser.setFirstName(updatedUser.getFirstName());
//        existingUser.setLastName(updatedUser.getLastName());
//        existingUser.setMobile(updatedUser.getMobile());
//        existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
//
//        User savedUser = userRepository.save(existingUser);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("statusCode", HttpStatus.OK.value());
//        response.put("message", "User updated successfully");
//        response.put("user", savedUser);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
@PostMapping("/changePassword")
public ResponseEntity<CommonResponse<String>> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
    User existingUser = userRepository.findByEmail(changePasswordRequest.getEmail());

    if (existingUser != null) {
        existingUser.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        // Save the updated user with the new password
        userRepository.save(existingUser);

        CommonResponse<String> commonResponse = new CommonResponse<>();
        commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
        commonResponse.setMessage("Password Changed");

        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    } else {
        // Handle case when user is not found
        CommonResponse<String> errorResponse = new CommonResponse<>();
        errorResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
        errorResponse.setMessage("User not found");

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
//        // Check if the user with the given ID exists
//        if (!userRepository.existsById(id)) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
//        }
//
//        userRepository.deleteById(id);
//
//        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
//    }

//    @PostMapping("/logout")
//    public ResponseEntity<CommonResponse> signout() {
//
//        System.out.println("sgcj,b,sd");
//        CommonResponse commonResponse= new CommonResponse();
//        // Clear the authentication context
//        SecurityContextHolder.clearContext();
//        commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
//        commonResponse.setMessage("LOgged oyt successfully");
//        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
//    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<String>> signout(HttpServletRequest request) {
        CommonResponse<String> commonResponse = new CommonResponse<>();


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {

            SecurityContextHolder.clearContext();

            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setMessage("Logged out successfully");
            System.out.println("tyuidsususu");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.UNAUTHORIZED));
            commonResponse.setMessage("User is not authenticated");
            return new ResponseEntity<>(commonResponse, HttpStatus.UNAUTHORIZED);
        }
    }


//    @PostMapping("/product/createProduct")
//    public ResponseEntity<Object> createProduct(@RequestBody CreateProductRequest req) {
//        try {
//            System.out.println("gadaidhsa;das;");
//            Product newProduct = productServiceImp.createProduct(req);
//            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
//        } catch (ProductException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (UserException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @DeleteMapping("/product/deleteProduct/{productId}")
//    public ResponseEntity<Object> deleteProduct(@PathVariable Long productId) {
//        try {
//            String deletionMessage = productServiceImp.deleteProduct(productId);
//            return new ResponseEntity<>(deletionMessage, HttpStatus.OK);
//        } catch (ProductException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PutMapping("/product/updateProduct/{productId}")
//    public ResponseEntity<Object> updateProduct(@PathVariable Long productId, @RequestBody UpdateProductRequest req) {
//        try {
//            Product updatedProduct = productServiceImp.updateProduct(productId, req);
//            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
//        } catch (ProductException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PostMapping("/category/createCategory")
//    public ResponseEntity<Object> createCategory(@RequestBody CreateCategoryRequest req) {
//        try {
//            Category newCategory = categoryServiceImp.createCategory(req);
//            return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
//        } catch (CategoryException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//    @DeleteMapping("/category/deleteCategory/{categoryId}")
//    public ResponseEntity<Object> deleteCategory(@PathVariable Long categoryId) {
//        try {
//            String deletionMessage = categoryServiceImp.deleteCategory(categoryId);
//            return new ResponseEntity<>(deletionMessage, HttpStatus.OK);
//        } catch (CategoryException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }
//    @PostMapping("/subcategory/createSubcategory")
//    public ResponseEntity<Object> createSubCategory(@RequestBody CreateSubCategoryRequest req) {
//        try {
//            SubCategory newSubCategory = subCategoryServiceImp.createSubCategory(req);
//            return new ResponseEntity<>(newSubCategory, HttpStatus.CREATED);
//        } catch (SubCategoryException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }



    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = userServiceImp.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid Username");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
