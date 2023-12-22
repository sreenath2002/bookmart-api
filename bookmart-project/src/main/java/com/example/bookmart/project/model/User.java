  package com.example.bookmart.project.model;

        import com.fasterxml.jackson.annotation.JsonIgnore;
        import jakarta.persistence.*;

        import java.time.LocalDateTime;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Optional;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private  String firstName;
    private String lastName;

    private String password;
    private String email;
    private  String role;
    private String mobile;

    private String status;
    private String showstatus;

    private String profileimage;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)

    private List<Address>address=new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)

    private List<ShoppingCart>shoppingCart=new ArrayList<>();




    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Rating> ratings=new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Review>reviews=new ArrayList<>();
    //    private LocalDateTime createdAt;
    public User (){

    }

    public User(Long id, String firstName, String lastName, String password, String email, String role, String mobile, String status, String showstatus, String profileimage, List<Address> address, List<ShoppingCart> shoppingCart, List<Rating> ratings, List<Review> reviews) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.role = role;
        this.mobile = mobile;
        this.status = status;
        this.showstatus = showstatus;
        this.profileimage = profileimage;
        this.address = address;
        this.shoppingCart = shoppingCart;
        this.ratings = ratings;
        this.reviews = reviews;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShowstatus() {
        return showstatus;
    }

    public void setShowstatus(String showstatus) {
        this.showstatus = showstatus;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public List<ShoppingCart> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(List<ShoppingCart> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}