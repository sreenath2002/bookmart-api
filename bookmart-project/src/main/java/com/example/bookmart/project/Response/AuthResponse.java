package com.example.bookmart.project.Response;

public class AuthResponse {
    private String jwt;
    private String message;
    private String role;
    private  String email;
    private String firstName;

    private String lastName;

    private String mobile;

    private Long id;

    private String profilepictute;

    public AuthResponse(String jwt, String message, String role, String email, String firstName, String lastName, String mobile, Long id, String profilepictute) {
        this.jwt = jwt;
        this.message = message;
        this.role = role;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.id = id;
        this.profilepictute = profilepictute;
    }

    public AuthResponse() {

    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfilepictute() {
        return profilepictute;
    }

    public void setProfilepictute(String profilepictute) {
        this.profilepictute = profilepictute;
    }
}
