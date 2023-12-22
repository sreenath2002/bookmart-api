package com.example.bookmart.project.Service;

import com.example.bookmart.project.Exception.ProductException;
import com.example.bookmart.project.Exception.UserException;
import com.example.bookmart.project.Repository.*;
import com.example.bookmart.project.Request.CreateProductRequest;
import com.example.bookmart.project.Request.UpdateProductRequest;
import com.example.bookmart.project.Request.UploadImageRequest;
import com.example.bookmart.project.model.*;
//import com.example.bookmart.project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProductServiceImp implements  ProductService{


    @Autowired
    private final ProductRepository productRepository;

    private final UserServiceImp userService;
    private final EntityIdService entityIdService;

    private final CategoryRepository categoryRepository;
    private final SemesterRepository semesterRepository;
    private final UniversityRepository universityRepository;

    private  final CourseRepository  courseRepository;
    private final ProductImageRepository productImageRepository;

//    private final ProductException productException;




    private final SubjectRepository subjectRepository;

    public ProductServiceImp(ProductRepository productRepository, UserServiceImp userService, CategoryRepository categoryRepository, SemesterRepository semesterRepository, UniversityRepository universityRepository, CourseRepository courseRepository, EntityIdService entityIdService, SubjectRepository subjectRepository,  ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
        this.semesterRepository = semesterRepository;
        this.universityRepository = universityRepository;
        this.courseRepository = courseRepository;
         this.entityIdService=entityIdService;
//        this.productException=productException;
        this.subjectRepository=subjectRepository;


        this.productImageRepository=productImageRepository;
    }

    @Override
    public Product createProduct(CreateProductRequest req) throws UserException ,ProductException{

        System.out.println(req.getPrice());
        String id;
        Product product = new Product();


        User user = userService.findUserById(req.getUserId());
        if(user==null)
        {
            throw new UserException("User  not fount");
        }
        product.setUser(user);


//        Set<University> universities = new HashSet<>();
//        for (String universityName : req.getUniversitys()) {
//            University university = entityIdService.findUniversity(universityName);
//            if (university != null) {
//                universities.add(university);
//            }
//        }
//        product.setUniversity(universities);
//
//        Set<Subject> subjects = new HashSet<>();
//        for (String subjectName: req.getSubjects()) {
//            Subject subject= entityIdService.findSubject(subjectName);
//            if (subject!= null) {
//                subjects.add(subject);
//            }
//        }
//        product.setSubjects(subjects);
//
//        Set<Author> authors = new HashSet<>();
//        for (String authorName : req.getAuthors()) {
//          Author author = entityIdService.findAuthor(authorName);
//            if (author != null) {
//                authors.add(author);
//            }
//        }
//        product.setAuthors(authors);
//
//        Set<Course> courses= new HashSet<>();
//        for (String courseName : req.getCourses()) {
//            Course course = entityIdService.findCourse(courseName);
//            if (course != null) {
//                courses.add(course);
//            }
//        }
        System.out.println(req.getPrice());
//        product.setCourses(courses);
        Course course=courseRepository.findByCourseName(req.getCourses());
//        Long courseId = courseRepository.findIdByCourseName(req.getCourses());

        System.out.println("---course okay--");
        Subject subject=subjectRepository.findBySubjectNameAndCourse(req.getSubjects(),course);
        System.out.println("---subjcet okay--");
        University university=universityRepository.findByUniversityName(req.getUniversitys());
        System.out.println("---univr=ersity okay--");

        Category parentCategory = categoryRepository.findByName(req.getParentCategory());
        System.out.println("---category okay--");
        Semester semester = semesterRepository.findByNameAndParentCategory(req.getSemester(),parentCategory );
        System.out.println("---seme okay--");
//        Set<Subject> subjects =subjectRepository.findByName(req.getSubjects());
//        Set<Course> courses = entityIdService.getCoursesFromNames(req.getCourses());
//        Set<University> universities = entityIdService.getUniversitiesFromNames(req.getUniversitys());

        product.setTitle(req.getTitle());
        product.setDescription(req.getDescription());
        product.setUser(user);
        product.setPrice(req.getPrice());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPresent(req.getDiscountPresent());
//        product.setQuantity(req.getQuantity());
        product.setStatus("true");

        product.setUniversity(university);
        product.setSubject(subject);
        product.setCourse(course);
        product.setAuthor(req.getAuthors());
        product.setParentCategory(parentCategory);

        product.setSemester(semester);
        product.setCreatedAt(LocalDateTime.now());


        String role=userService.findUserRoleById(req.getUserId());

        if(role.equals("ADMIN"))
        {
            product.setType("NEW");
        }
        else if(role.equals("USER")){
            product.setType("OLD");
        }
        else{
            throw new UserException("Type is not added");
        }

//        product= productRepository.save(product);
        product = productRepository.save(product);

        if (req.getImageUrls() != null && !req.getImageUrls().isEmpty()) {
            List<String> imageUrls = req.getImageUrls();

            for (String imageUrl : imageUrls) {
                ProductImage productImage = new ProductImage();
                productImage.setImageUrl(imageUrl);
                productImage.setProduct(product);
                product.addImage(productImage); // Add image to the product using the addImage method
                productImageRepository.save(productImage); // Save each ProductImage
            }
        }

        return productRepository.save(product);

    }

    public List<Product> getProductsByIds(List<Long> productIds) {
        return productRepository.findAllById(productIds);
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setStatus("false");
            return "Product with ID " + productId + " has been deleted successfully";
        } else {
            throw new ProductException("Product not found for ID: " + productId);
        }
    }


//    @Override
//    public ProductImage productImage(Long productId, UploadImageRequest req)throws ProductException{
//
//    }

    @Override
    public Product updateProduct(Long productId, UpdateProductRequest req) throws ProductException{
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product existingProduct = productOptional.get();

            // Update fields with new values
            existingProduct.setTitle(req.getTitle());
            existingProduct.setDescription(req.getDescription());
            existingProduct.setPrice(req.getPrice());
            existingProduct.setDiscountedPrice(req.getDiscountedPrice());
            existingProduct.setDiscountPresent(req.getDiscountPresent());
//            existingProduct.setQuantity(req.getQuantity());


            // Fetch IDs for various entities by names
            Category parentCategory = categoryRepository.findByName(req.getParentCategory());
            Course course=courseRepository.findByCourseName(req.getCourses());
//            Long courseId = courseRepository.findIdByCourseName(req.getCourses());
            Semester semester = semesterRepository.findByNameAndParentCategory(req.getSemester(),parentCategory);
            Subject subject=subjectRepository.findBySubjectNameAndCourse(req.getSubjects(),course);
            University university=universityRepository.findByUniversityName(req.getUniversitys());



            // Update with IDs
            existingProduct.setParentCategory(parentCategory);

            existingProduct.setSemester(semester);
            existingProduct.setAuthor(req.getAuthors());
            existingProduct.setCourse(course);
            existingProduct.setSubject(subject);
            existingProduct.setUniversity(university);

            existingProduct.setCreatedAt(LocalDateTime.now());



            if (req.getImageUrls() != null && !req.getImageUrls().isEmpty()) {
                List<String> imageUrls = req.getImageUrls();

                for (String imageUrl : imageUrls) {
                    ProductImage productImage = new ProductImage();
                    productImage.setImageUrl(imageUrl);
                    productImage.setProduct(existingProduct);
                    existingProduct.addImage(productImage); // Add image to the product using the addImage method
                    productImageRepository.save(productImage); // Save each ProductImage
                }
            }
           productRepository.save(existingProduct);
            return existingProduct;
        } else {
            throw new ProductException("Product not found for ID: " + productId);
        }
    }

}
