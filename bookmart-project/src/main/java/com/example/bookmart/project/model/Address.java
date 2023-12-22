package com.example.bookmart.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="first_name")
   private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name="building_number")
    private Long buildingnumber;

    @Column(name = "streename")
    private String streetAddress;

    @Column (name="landmark")
    private  String landmark;

    @Column(name = "city")
    private String city;
    @Column(name="state")
    private String state;

    @Column(name = "country")
    private  String country;
    @Column(name="zip_code")
    private String zipCode;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private String mobile;

    public  Address()
    {

    }

    public Address(Long id, String firstName, String lastName, Long buildingnumber, String streetAddress, String landmark, String city, String state, String country, String zipCode, User user, String mobile) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.buildingnumber = buildingnumber;
        this.streetAddress = streetAddress;
        this.landmark = landmark;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
        this.user = user;
        this.mobile = mobile;
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

    public Long getBuildingnumber() {
        return buildingnumber;
    }

    public void setBuildingnumber(Long buildingnumber) {
        this.buildingnumber = buildingnumber;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
