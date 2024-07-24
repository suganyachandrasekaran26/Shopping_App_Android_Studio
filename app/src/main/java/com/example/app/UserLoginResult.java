package com.example.app;


public class UserLoginResult {
    private boolean success;
    private String email;

    public UserLoginResult(boolean success, String email) {
        this.success = success;
        this.email = email;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getEmail() {
        return email;
    }
}