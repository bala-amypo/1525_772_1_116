package com.example.demo.dto;

public class JwtResponse {

    private String token;
    private Long userId;
    private String email;
    private String roles;

    public JwtResponse() {
    }

    public JwtResponse(String token, Long userId, String email, String roles) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.roles = roles;
    }

    // getters & setters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
