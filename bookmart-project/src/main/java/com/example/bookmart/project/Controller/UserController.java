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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired

    private final ProductServiceImp productServiceImp;
    private final PaymentInformationRepository paymentInformationRepository;



    private  final OrderStatusDetailsRepository orderStatusDetailsRepository;
    private  final AddresRepository addresRepository;

    private final ShopOrderRepository shopOrderRepository;

    private final PaymentTypeRepository paymentTypeRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private  final OrderLineRepository orderLineRepository;

    private final CancelResonsRepository cancelResonsRepository;
    private final  StatusRepository statusRepository;
    private  final UserServiceImp userServiceImp;
    private final AddressServiceImp addressServiceImp;
    private final CartService cartService;

    private final CancellationService cancellationService;

    private final OrderLineService orderLineService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserController(ProductServiceImp productServiceImp, PaymentInformationRepository paymentInformationRepository, OrderStatusDetailsRepository orderStatusDetailsRepository, AddresRepository addresRepository, ShopOrderRepository shopOrderRepository, PaymentTypeRepository paymentTypeRepository, ShoppingCartRepository shoppingCartRepository, OrderLineRepository orderLineRepository, CancelResonsRepository cancelResonsRepository, StatusRepository statusRepository, UserServiceImp userServiceImp, AddressServiceImp addressServiceImp, CartService cartService, CancellationService cancellationService, OrderLineService orderLineService, ProductRepository productRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.productServiceImp = productServiceImp;
        this.paymentInformationRepository = paymentInformationRepository;
        this.orderStatusDetailsRepository = orderStatusDetailsRepository;
        this.addresRepository = addresRepository;
        this.shopOrderRepository = shopOrderRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.orderLineRepository = orderLineRepository;
        this.cancelResonsRepository = cancelResonsRepository;
        this.statusRepository = statusRepository;
        this.userServiceImp = userServiceImp;
        this.addressServiceImp = addressServiceImp;
        this.cartService = cartService;
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
    @GetMapping("/orderline/{shoporderId}")
    public ResponseEntity<CommonResponse<List<Map<String, Object>>>> getOrderLine(@PathVariable Long shoporderId) {
        CommonResponse<List<Map<String, Object>>> commonResponse = new CommonResponse<>();

        List<Map<String, Object>> orderLines = orderLineService.getOrderLineDetails(shoporderId);

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
            commonResponse.setStatuscode(String.valueOf(HttpStatus.OK));
            commonResponse.setMessage("Removed cart item with ID: " + cartId);
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (Exception e) {
            commonResponse.setStatuscode(String.valueOf(HttpStatus.NOT_FOUND));
            commonResponse.setMessage("No cart item found with ID: " + cartId);
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }
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
}
