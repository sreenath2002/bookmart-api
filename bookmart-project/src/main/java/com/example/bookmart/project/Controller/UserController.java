package com.example.bookmart.project.Controller;


import com.example.bookmart.project.Exception.AddresException;
import com.example.bookmart.project.Exception.ProductException;
import com.example.bookmart.project.Exception.UserException;
import com.example.bookmart.project.Repository.*;
import com.example.bookmart.project.Request.*;
import com.example.bookmart.project.Response.CommonResponse;
import com.example.bookmart.project.Service.*;
import com.example.bookmart.project.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired

    private final ProductServiceImp productServiceImp;

    private final ShoporderService shoporderService;
    private final PaymentInformationRepository paymentInformationRepository;

    private final Reviewservice reviewservice;

    private  final OrderStatusDetailsRepository orderStatusDetailsRepository;
    private  final AddresRepository addresRepository;

    private final ShopOrderRepository shopOrderRepository;

    private final PaymentTypeRepository paymentTypeRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private  final OrderLineRepository orderLineRepository;

    private final WalletRepository walletRepository;
 private final Wishlistservices wishlistservices;


 private final ReviewRepository reviewRepository;
    private final OrderStatusService orderStatusService;

    private final CancelResonsRepository cancelResonsRepository;
    private final  StatusRepository statusRepository;
    private  final UserServiceImp userServiceImp;
    private final AddressServiceImp addressServiceImp;
    private final CartService cartService;

  private final WishlistRepository wishlistRepository;

  private final CuponRepository cuponRepository;
    private final CancellationService cancellationService;

    private final OrderLineService orderLineService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserController(ProductServiceImp productServiceImp, ShoporderService shoporderService, PaymentInformationRepository paymentInformationRepository, Reviewservice reviewservice, OrderStatusDetailsRepository orderStatusDetailsRepository, AddresRepository addresRepository, ShopOrderRepository shopOrderRepository, PaymentTypeRepository paymentTypeRepository, ShoppingCartRepository shoppingCartRepository, OrderLineRepository orderLineRepository, WalletRepository walletRepository, Wishlistservices wishlistservices, ReviewRepository reviewRepository, OrderStatusService orderStatusService, CancelResonsRepository cancelResonsRepository, StatusRepository statusRepository, UserServiceImp userServiceImp, AddressServiceImp addressServiceImp, CartService cartService, WishlistRepository wishlistRepository, CuponRepository cuponRepository, CancellationService cancellationService, OrderLineService orderLineService, ProductRepository productRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.productServiceImp = productServiceImp;
        this.shoporderService = shoporderService;
        this.paymentInformationRepository = paymentInformationRepository;
        this.reviewservice = reviewservice;
        this.orderStatusDetailsRepository = orderStatusDetailsRepository;
        this.addresRepository = addresRepository;
        this.shopOrderRepository = shopOrderRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.orderLineRepository = orderLineRepository;
        this.walletRepository = walletRepository;
        this.wishlistservices = wishlistservices;
        this.reviewRepository = reviewRepository;
        this.orderStatusService = orderStatusService;
        this.cancelResonsRepository = cancelResonsRepository;
        this.statusRepository = statusRepository;
        this.userServiceImp = userServiceImp;
        this.addressServiceImp = addressServiceImp;
        this.cartService = cartService;
        this.wishlistRepository = wishlistRepository;
        this.cuponRepository = cuponRepository;
        this.cancellationService = cancellationService;
        this.orderLineService = orderLineService;
        this.productRepository=productRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    @GetMapping("/getuser/{userId}")
    public ResponseEntity<CommonResponse<Map<String, String>>> getUser(@PathVariable Long userId) {
        CommonResponse<Map<String, String>> commonResponse = new CommonResponse<>();
        try {
            Optional<User> userOptional = userRepository.findById(userId);

            if (userOptional.isPresent()) {
                User user = userOptional.get();

                Map<String, String> userDetails = new HashMap<>();
                userDetails.put("firstName", user.getFirstName());
                userDetails.put("lastName", user.getLastName());
                userDetails.put("email", user.getEmail());
                userDetails.put("phoneNumber", user.getMobile());
                userDetails.put("profileImage",user.getProfileimage());

                commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
                commonResponse.setResult(userDetails);
                commonResponse.setMessage("User details retrieved successfully");
                return new ResponseEntity<>(commonResponse, HttpStatus.OK);
            } else {
                commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
                commonResponse.setMessage("User not found");
                return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            commonResponse.setMessage("Failed to retrieve user details: " + e.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/addaddress")
    public ResponseEntity<CommonResponse<Object>> addAddress(@RequestBody AddAddresRequest req) {
        System.out.println("haiiiii");
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            Address address = addressServiceImp.AddAddress(req);
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(address);
            commonResponse.setMessage("Address Added Successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (AddresException e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.BAD_REQUEST)); // Example: Setting a bad request status
            commonResponse.setResult(null);
            commonResponse.setMessage("Address Not Added: " + e.getMessage());

            return new ResponseEntity<>(commonResponse, HttpStatus.BAD_REQUEST);
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/getaddresses/{userId}")
    public ResponseEntity<CommonResponse<List<Address>>> getUserAddresses(@PathVariable Long userId) {
        CommonResponse<List<Address>> commonResponse = new CommonResponse<>();
        System.out.println("?????????");

        try {
            List<Address> userAddresses = addressServiceImp.getAddressesByUserId(userId);

            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(userAddresses);
            commonResponse.setMessage("Addresses retrieved successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (Exception e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.BAD_REQUEST));
            commonResponse.setResult(null);
            commonResponse.setMessage("Failed to retrieve addresses: " + e.getMessage());

            return new ResponseEntity<>(commonResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping ("/updateaddress/{addressId}")
    public ResponseEntity<CommonResponse<Object>> updateAddress( @PathVariable Long addressId,@RequestBody UpdateAddressRequest req) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            Address address = addressServiceImp.UpdateAddress(addressId,req);
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(address);
            commonResponse.setMessage("Address updated Successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (AddresException e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.BAD_REQUEST)); // Example: Setting a bad request status
            commonResponse.setResult(null);
            commonResponse.setMessage("Address Not updated: " + e.getMessage());

            return new ResponseEntity<>(commonResponse, HttpStatus.BAD_REQUEST);
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
    }



    @PutMapping("/addprofileImage/{userId}")
    public ResponseEntity<CommonResponse<Object>> addProfileImage(@PathVariable Long userId, @RequestBody AddProfileImageRequest req) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            Optional<User> userOptional = userRepository.findById(userId);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setProfileimage(req.getImageUrl());
                userRepository.save(user);

                commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
                commonResponse.setResult(user);
                commonResponse.setMessage("Profile Image Updated Successfully");

                return new ResponseEntity<>(commonResponse, HttpStatus.OK);
            } else {
                commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
                commonResponse.setResult(null);
                commonResponse.setMessage("User Not Found for ID: " + userId);

                return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            commonResponse.setResult(null);
            commonResponse.setMessage("Failed to update profile image: " + e.getMessage());

            return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/updatepassword/{userId}")
    public ResponseEntity<CommonResponse<Object>> updatePassword(@PathVariable Long userId, @RequestBody ChangePasswordFromProfileRequest req) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();
        System.out.println("jiii");
        try {
            Optional<User> userOptional = userRepository.findById(userId);

            if (userOptional.isPresent()) {
                User user = userOptional.get();


                if (passwordEncoder.matches(req.getPrevousPassword(), user.getPassword())) {

                    user.setPassword(passwordEncoder.encode(req.getNewPassword()));
                    userRepository.save(user);

                    commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
                    commonResponse.setResult(user);
                    commonResponse.setMessage("Password Updated successfully");

                    return new ResponseEntity<>(commonResponse, HttpStatus.OK);
                } else {
                    commonResponse.setStatuscode(String.valueOf(HttpStatus.BAD_REQUEST));
                    commonResponse.setMessage("Previous password does not match");

                    return new ResponseEntity<>(commonResponse, HttpStatus.BAD_REQUEST);
                }
            } else {
                commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
                commonResponse.setMessage("User Not Found for ID: " + userId);

                return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            commonResponse.setMessage("Failed to update password: " + e.getMessage());

            return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addtocart")
    public ResponseEntity<CommonResponse<Object>> addToCart(@RequestBody AddToCartRequest req) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            Optional<User> userOptional = userRepository.findById(req.getUserId());
            Optional<Product> productOptional = productRepository.findById(req.getProductId());

            if (userOptional.isPresent() && productOptional.isPresent()) {
                User user = userOptional.get();
                Product product = productOptional.get();

                ShoppingCart shoppingCart = new ShoppingCart();
                shoppingCart.setUser(user);
                shoppingCart.setProduct(product);
                shoppingCart.setQuantity(req.getQuantity());

                shoppingCartRepository.save(shoppingCart);

                commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
                commonResponse.setResult(shoppingCart.getProduct());
                commonResponse.setMessage("Added to cart Successfully");

                return new ResponseEntity<>(commonResponse, HttpStatus.OK);
            } else {
                commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
                commonResponse.setMessage("User or Product not found");

                return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            commonResponse.setMessage("Failed to add to cart d: " + e.getMessage());

            return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addtowishlist")
    public ResponseEntity<CommonResponse<Object>> addToCwishlist(@RequestBody AddtowishlistRequest req) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            Optional<User> userOptional = userRepository.findById(req.getUserId());
            Optional<Product> productOptional = productRepository.findById(req.getProductId());

            if (userOptional.isPresent() && productOptional.isPresent()) {
                User user = userOptional.get();
                Product product = productOptional.get();

               Userwishlist userwishlist=new Userwishlist();
               userwishlist.setUser(user);
                userwishlist.setProduct(product);
                userwishlist.setQuantity(req.getQuantity());

                wishlistRepository.save(userwishlist);

                commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
                commonResponse.setResult(userwishlist.getProduct());
                commonResponse.setMessage("Added to Wishlist Successfully");

                return new ResponseEntity<>(commonResponse, HttpStatus.OK);
            } else {
                commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
                commonResponse.setMessage("User or Product not found");

                return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            commonResponse.setMessage("Failed to add to wishlist d: " + e.getMessage());

            return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/userwishlist/{userId}")
    public ResponseEntity<CommonResponse<List<Map<String, Object>>>> getUserWishlist(@PathVariable Long userId) {
        CommonResponse<List<Map<String, Object>>> commonResponse = new CommonResponse<>();

        List<Map<String, Object>> wishlistDetails= wishlistservices.getProductDetailsAndQuantitiesByUserIdFromwishlist(userId);

        if (wishlistDetails.isEmpty()) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setMessage("Wishlist is Empty is Empty");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(wishlistDetails);
            commonResponse.setMessage("Wishlist is Retrieved");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
    }
    @GetMapping("/getwishlist/{wishlistId}")
    public ResponseEntity<CommonResponse<List<Map<String, Object>>>> getWishlistDetailsById(@PathVariable Long wishlistId) {
        CommonResponse<List<Map<String, Object>>> commonResponse = new CommonResponse<>();
        List<Map<String, Object>> wishlistDetailsList = wishlistservices.getWishlistDetailsById(wishlistId);

        if (wishlistDetailsList == null || wishlistDetailsList.isEmpty()) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage("No wishlist found for the given ID.");
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }

        commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
        commonResponse.setResult(wishlistDetailsList);
        commonResponse.setMessage("wishlist found.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
    @GetMapping("/usercart/{userId}")
    public ResponseEntity<CommonResponse<List<Map<String, Object>>>> getUserCart(@PathVariable Long userId) {
        CommonResponse<List<Map<String, Object>>> commonResponse = new CommonResponse<>();

        List<Map<String, Object>> cartDetails = cartService.getProductDetailsAndQuantitiesByUserId(userId);

        if (cartDetails.isEmpty()) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setMessage("Cart is Empty");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(cartDetails);
            commonResponse.setMessage("Cart is Retrieved");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
    }
    @GetMapping("/getcart/{cartId}")
    public ResponseEntity<CommonResponse<List<Map<String, Object>>>> getCartDetailsById(@PathVariable Long cartId) {
        CommonResponse<List<Map<String, Object>>> commonResponse = new CommonResponse<>();
        List<Map<String, Object>> cartDetailsList = cartService.getCartDetailsById(cartId);

        if (cartDetailsList == null || cartDetailsList.isEmpty()) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage("No carts found for the given ID.");
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }

        commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
        commonResponse.setResult(cartDetailsList);
        commonResponse.setMessage("Carts found.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }


    @GetMapping("/paymenttypes")
    public ResponseEntity<CommonResponse<List<PaymentType>>> getAllPaymentTypes() {
        CommonResponse<List<PaymentType>> commonResponse = new CommonResponse<>();
        try {
            List<PaymentType> paymentTypes = paymentTypeRepository.findAll();
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(paymentTypes);
            commonResponse.setMessage("Successfully retrieved all payment types.");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);


        } catch (Exception e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));

            commonResponse.setMessage("Error occurred while fetching payment types");
            // Handle the exception as required

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/cancelresons")
    public ResponseEntity<CommonResponse<List<CancelResons>>> getAllCancelResons() {
        CommonResponse<List<CancelResons>> commonResponse = new CommonResponse<>();
        try {
            List<CancelResons> cancelResons = cancelResonsRepository.findAll();
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(cancelResons);
            commonResponse.setMessage("Successfully retrieved all Cancel Resons.");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);


        } catch (Exception e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));

            commonResponse.setMessage("Error occurred while fetching Cancel Resons");
            // Handle the exception as required

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
    }
    @GetMapping("/orderstatusoptions")
    public ResponseEntity<CommonResponse<List<Status>>> getAllorderstatusoptions() {
        CommonResponse<List<Status>> commonResponse = new CommonResponse<>();
        try {
            List<Status> statuses = statusRepository.findAll();
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(statuses);
            commonResponse.setMessage("Successfully retrieved all Status options.");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);


        } catch (Exception e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));

            commonResponse.setMessage("Error occurred while fetching Status options");
            // Handle the exception as required

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
    }
    @PostMapping("/cancelorder")
    public ResponseEntity<CommonResponse<String>> cancelProductOrder(@RequestBody CancelRequest cancelRequest) {
        CommonResponse<String> commonResponse = new CommonResponse<>();

        boolean isCancelled = cancellationService.cancelProductOrder(cancelRequest.getOrderLineId(), cancelRequest.getReasonId());

        if (isCancelled) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setMessage("order canceled successfully");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.BAD_REQUEST));
            commonResponse.setMessage("Failed to cancel order");
            return new ResponseEntity<>(commonResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/addreview/{productId}")
    public ResponseEntity<CommonResponse<Object>> addreviewrequest(@PathVariable Long productId, @RequestBody ReviewRequest request) {
        System.out.println("mmmmmmmmmmmmmmmmmmmmmmmm");
        CommonResponse<Object> commonResponse = new CommonResponse<>();
        System.out.println("kaooooo");
        try {
            Review review = new Review();
            Product product = productRepository.findById(productId).orElse(null);
            User user = userRepository.findById(request.getUserId()).orElse(null);

            review.setReview(request.getReview());
            review.setUserfirstname(user.getFirstName());
            review.setUserlastname(user.getLastName());
            review.setProduct(product);
            review.setRating(request.getRating());
            review.setCreatedAt(LocalDateTime.now());

            reviewRepository.save(review);

            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(review);
            commonResponse.setMessage("Review created");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }  catch (Exception e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            commonResponse.setMessage("An error occurred while processing the request");
            return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/reviews/{productId}")
    public ResponseEntity<Object> getReviewsByProductId(@PathVariable Long productId) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();
        List<Review> reviews = reviewservice.getAllReviewsByProductId(productId);

        if (reviews.isEmpty()) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setMessage("No reviews found for the specified product.");
            commonResponse.setResult(Collections.emptyList()); // Optionally, set an empty list as the result
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(reviews);
            commonResponse.setMessage("All reviews retrieved");
        }

        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }


    @PutMapping("/setcancelstatus/{orderLineId}")
    public ResponseEntity<CommonResponse<Object>> setCancelStatus(@PathVariable Long orderLineId) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        Optional<Status> optionalStatus = statusRepository.findById(6L);
        Optional<OrderLine> orderLine = orderLineRepository.findById(orderLineId);

        if (orderLine.isPresent() && optionalStatus.isPresent()) {
            OrderLine orderLine1 = orderLine.get();
            orderLine1.setStatus("false"); // Assuming status is boolean

            orderLineRepository.save(orderLine1);

            Status status = optionalStatus.get();

            OrderStatusDetails orderStatusDetails = new OrderStatusDetails();
            orderStatusDetails.setReachedDate(new Date());
            LocalTime currentTime = LocalTime.now();
            Time reachedTime = Time.valueOf(currentTime);
            orderStatusDetails.setReachedTime(reachedTime); // Setting current time

            orderStatusDetails.setOrderLine(orderLine1);
            orderStatusDetails.setStatus(status);

            // Save orderStatusDetails entity to its repository if necessary
            // orderStatusDetailsRepository.save(orderStatusDetails);

            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setMessage("Status updated successfully for orderLineId: " + orderLineId);
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setMessage("Status with id 6 not found or OrderLine not found for id: " + orderLineId);
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/getCouponDiscount/{couponId}")
    public ResponseEntity<CommonResponse<Integer>> getCouponDiscount(@PathVariable Long couponId) {
        CommonResponse<Integer> commonResponse = new CommonResponse<>();

        try {
            Optional<Cupons> optionalCoupon = cuponRepository.findById(couponId);

            if (optionalCoupon.isPresent()) {
                Cupons coupon = optionalCoupon.get();
                Integer discount = coupon.getDiscount();

                commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
                commonResponse.setResult(discount);
                commonResponse.setMessage("Discount retrieved successfully");

                return new ResponseEntity<>(commonResponse, HttpStatus.OK);
            } else {
                commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
                commonResponse.setMessage("Coupon with ID " + couponId + " not found");

                return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            commonResponse.setMessage("Failed to retrieve Discount: " + e.getMessage());

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
    @GetMapping("/getValidCouponsList")
    public ResponseEntity<CommonResponse<List<Cupons>>> getValidCouponlist() {
        CommonResponse<List<Cupons>> commonResponse = new CommonResponse<>();
        List<Cupons> coupons = cuponRepository.findAll();
        List<Cupons> validCoupons = new ArrayList<>();

        // Get the current date
        Date currentDate = new Date();

        for (Cupons coupon : coupons) {
            Date validFrom = coupon.getValid_fromDate();
            Date validUpto = coupon.getValid_uptoDate();

            // Check if the current date is between validFrom and validUpto dates
            if (currentDate.after(validFrom) && currentDate.before(validUpto)) {
                validCoupons.add(coupon);
            }
        }

        if (!validCoupons.isEmpty()) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(validCoupons);
            commonResponse.setMessage("Valid Coupons retrieved successfully");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setMessage("No Valid Coupons Available");

            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
    }






    @GetMapping("/orderline/{userId}")
    public ResponseEntity<CommonResponse<List<Map<String, Object>>>> getOrderLine(@PathVariable Long userId) {
        CommonResponse<List<Map<String, Object>>> commonResponse = new CommonResponse<>();

        List<Map<String, Object>> orderLines = orderLineService.getOrderLineDetails(userId);

        if (orderLines.isEmpty()) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage("Orders are Empty");
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(orderLines);
            commonResponse.setMessage("Orders are Retrieved");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
    }
    @GetMapping("/stauses/{orderId}")
    public ResponseEntity<CommonResponse<List<Map<String, Object>>>> getOrderStatus(@PathVariable Long orderId) {
        CommonResponse<List<Map<String, Object>>> commonResponse = new CommonResponse<>();

        List<Map<String, Object>> statuses = orderStatusService.getOrderStatuesDetails(orderId);

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
    @GetMapping("/shopOrderId/{userId}")
    public ResponseEntity<CommonResponse<Long>> getShopOrderIdByUserId(@PathVariable Long userId) {
        CommonResponse<Long> commonResponse = new CommonResponse<>();

        ShopOrder shopOrder = shopOrderRepository.findByUserId(userId);

        if (shopOrder != null) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setResult(shopOrder.getId());
            commonResponse.setMessage("ShopOrder ID is Retrieved");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage("No ShopOrder found for the given User ID");
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping("/paymentrequest/{userId}")
    public ResponseEntity<CommonResponse<Object>> makePayment(@PathVariable Long userId, @RequestBody PaymentRequest req) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            PaymentInformation paymentInformation = new PaymentInformation();
            PaymentType paymentType = paymentTypeRepository.findById(req.getPaymentId()).orElse(null);
            User user = userRepository.findById(userId).orElse(null);

            if (paymentType == null || user == null) {
                commonResponse.setMessage("Payment type or user not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(commonResponse);
            }


            paymentInformation.setPaymentType(paymentType);
            paymentInformation.setUser(user);
            paymentInformation.setAmount(req.getAmount());

            paymentInformationRepository.save(paymentInformation);

            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
           commonResponse.setResult(paymentInformation.getId());
            commonResponse.setMessage("Payment successful");

            return ResponseEntity.ok(commonResponse);
        } catch (Exception e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            commonResponse.setMessage("Error occurred while processing payment: ");


            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(commonResponse);
        }
    }

    @PostMapping("/shoporder/{userId}")
    public ResponseEntity<CommonResponse<Object>> shopOrder(@PathVariable Long userId, @RequestBody ShopOrderRequest req) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();
        try {
            Optional<PaymentInformation> optionalPaymentInformation = paymentInformationRepository.findById(req.getPaymentInfoId());
            Optional<Address> optionalAddress = addresRepository.findById(req.getAddresId());
            Optional<Status> optionalStatus = statusRepository.findById(1L);

            if (optionalPaymentInformation.isPresent() && optionalAddress.isPresent() && optionalStatus.isPresent()) {
                PaymentInformation paymentInformation = optionalPaymentInformation.get();
                Address address = optionalAddress.get();
                Status status = optionalStatus.get();

//                OrderStatusDetails orderStatusDetails = new OrderStatusDetails();
//                orderStatusDetails.setReachedDate(LocalDate.now());
//                orderStatusDetails.setReachedTime(LocalTime.now());
//                orderStatusDetails.setStatus(status);
//
//               orderStatusDetailsRepository.save(orderStatusDetails);

                ShopOrder shopOrder = new ShopOrder();
                shopOrder.setOrderdateandtime(LocalDateTime.now());
                shopOrder.setPaymentInformation(paymentInformation);
                shopOrder.setAddress(address);
//                shopOrder.setOrderStatusDetails(orderStatusDetails);
                shopOrder.setOrderTotal(req.getTotalAmount());
                shopOrder.setUserId(userId);

              shopOrderRepository.save(shopOrder);

                commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
               commonResponse.setResult(shopOrder.getId());
                commonResponse.setMessage("Shop order placed successfully");

                return new ResponseEntity<>(commonResponse, HttpStatus.OK);
            } else {
                commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
                commonResponse.setMessage("Payment information, address, or status not found");
                return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            commonResponse.setMessage("Error occurred while placing shop order: " + e.getMessage());

            return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/removefromcart/{cartId}")
    public ResponseEntity<CommonResponse<Object>> removeFromCartByCartId(@PathVariable Long cartId) {
        System.out.println("---------Remove------------");
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            shoppingCartRepository.deleteById(cartId);
            System.out.println("removed........");
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setMessage("Removed cart item with ID: " + cartId);
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (Exception e) {

            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage("No cart item found with ID: " + cartId);
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/removefromwishlist/{wishlistId}")
    public ResponseEntity<CommonResponse<Object>> removeFromwishlistByCartId(@PathVariable Long wishlistId) {
        System.out.println("---------Remove------------");
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            wishlistRepository.deleteById(wishlistId);
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setMessage("Removed wishlist item with ID: " + wishlistId);
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (Exception e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage("No wishlist item found with ID: " + wishlistId);
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/productidsfromcart/{userId}")
    public ResponseEntity<CommonResponse<Object>> getProductIdsByUserId(@PathVariable Long userId) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();
        List<Long> productIds = shoppingCartRepository.findDistinctProductIdsByUserId(userId);

        if (productIds.isEmpty()) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setMessage("No product IDs found for the user from cart.");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }

        commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
        commonResponse.setResult(productIds);
        commonResponse.setMessage("Product IDs retrieved successfully.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
    @GetMapping("/productidsfromwishlist/{userId}")
    public ResponseEntity<CommonResponse<Object>> getProductIdsFromwishlistByUserId(@PathVariable Long userId) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();
        List<Long> productIds = wishlistRepository.findDistinctProductIdsByUserId(userId);

        if (productIds.isEmpty()) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setMessage("No product IDs found for the user from wishlist.");
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }

        commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
        commonResponse.setResult(productIds);
        commonResponse.setMessage("Product IDs retrieved successfully.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }


    @PutMapping("/incrementquantity/{cartId}")
    public ResponseEntity<CommonResponse<Object>> incrementQuantity(@PathVariable Long cartId) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(cartId);
            if (optionalShoppingCart.isPresent()) {
                ShoppingCart shoppingCart = optionalShoppingCart.get();
                int newQuantity = shoppingCart.getQuantity() + 1;
                shoppingCart.setQuantity(newQuantity);
                shoppingCartRepository.save(shoppingCart);

                commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
                commonResponse.setMessage("Incremented quantity for cart item with ID: " + cartId);
                return new ResponseEntity<>(commonResponse, HttpStatus.OK);
            } else {
                commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
                commonResponse.setMessage("No cart item found with ID: " + cartId);
                return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            commonResponse.setMessage("Failed to increment quantity: " + e.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/decrementquantity/{cartId}")
    public ResponseEntity<CommonResponse<Object>> decrementQuantity(@PathVariable Long cartId) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(cartId);
            if (optionalShoppingCart.isPresent()) {
                ShoppingCart shoppingCart = optionalShoppingCart.get();
                int newQuantity = shoppingCart.getQuantity()-1;


                if (newQuantity >= 0) {
                    shoppingCart.setQuantity(newQuantity);
                    shoppingCartRepository.save(shoppingCart);

                    commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
                    commonResponse.setMessage("Decremented quantity for cart item with ID: " + cartId);
                    return new ResponseEntity<>(commonResponse, HttpStatus.OK);
                } else {
                    commonResponse.setStatuscode(String.valueOf(HttpStatus.BAD_REQUEST));
                    commonResponse.setMessage("Quantity cannot be less than 0");
                    return new ResponseEntity<>(commonResponse, HttpStatus.BAD_REQUEST);
                }
            } else {
                commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
                commonResponse.setMessage("No cart item found with ID: " + cartId);
                return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            commonResponse.setMessage("Failed to decrement quantity: " + e.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    @PutMapping("editprofile/{userId}")
    public ResponseEntity<CommonResponse<Map<String, String>>> editProfile(@PathVariable Long userId, @RequestBody EditProfileRequest req) {
        CommonResponse<Map<String, String>> commonResponse = new CommonResponse<>();

        try {
            Optional<User> userOptional = userRepository.findById(userId);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setFirstName(req.getFirstName());
                user.setLastName(req.getLastName());
                user.setEmail(req.getEmail());
                user.setMobile(req.getMobile());
                userRepository.save(user);

                Map<String, String> profileDetails = new HashMap<>();
                profileDetails.put("firstName", user.getFirstName());
                profileDetails.put("lastName", user.getLastName());
                profileDetails.put("email", user.getEmail());
                profileDetails.put("mobile", user.getMobile());

                commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
                commonResponse.setResult(profileDetails);
                commonResponse.setMessage("Profile Updated Successfully");
                return new ResponseEntity<>(commonResponse, HttpStatus.OK);
            } else {
                commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
                commonResponse.setMessage("User Not Found for ID: " + userId);
                return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            commonResponse.setMessage("Failed to update Profile: " + e.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
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
    @GetMapping("/walletamt/{userId}")
    public ResponseEntity<Object> getWalletAmountByUserId(@PathVariable Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<Wallet> walletOptional = walletRepository.findByUser(user);

            if (walletOptional.isPresent()) {
                Wallet wallet = walletOptional.get();
                commonResponse.setStatuscode(HttpStatus.OK.toString());
                commonResponse.setResult(wallet.getAmount());
                commonResponse.setMessage("Wallet Amount");

                return ResponseEntity.ok(commonResponse);
            } else {
                commonResponse.setStatuscode(HttpStatus.OK.toString());
                commonResponse.setMessage("Wallet not found for the user");
                return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
            }
        } else {
            commonResponse.setStatuscode(HttpStatus.NOT_FOUND.toString());
            commonResponse.setMessage("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(commonResponse);
        }
    }
    @GetMapping("/transaction/{userId}")
    public ResponseEntity<Object> getTransactionDetailsByUserId(@PathVariable Long userId) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();

        try {
            List<Object[]> shopOrders = shoporderService.getOrdersForUserWithNonCashPayments(userId);

            if (shopOrders.isEmpty()) {
                commonResponse.setStatuscode(HttpStatus.OK.toString());
                commonResponse.setMessage("No transactions ");
                return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
            }

            commonResponse.setStatuscode(HttpStatus.OK.toString());
            commonResponse.setResult(shopOrders);
            commonResponse.setMessage("transcations");
            return ResponseEntity.ok(commonResponse);
        } catch (Exception e) {
            // Log the exception details for debugging
            e.printStackTrace(); // Or use a logging framework like SLF4J/Logback

            // Return a generic error message
            commonResponse.setStatuscode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            commonResponse.setMessage("An error occurred while processing the request.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(commonResponse);
        }
    }



}
