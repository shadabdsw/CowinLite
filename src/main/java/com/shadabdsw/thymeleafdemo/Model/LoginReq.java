package com.shadabdsw.thymeleafdemo.Model;

public class LoginReq {

    private String phoneNumber;
    private String password;

    public LoginReq() {
    }

    public LoginReq(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String setPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "LoginReq{" + "phoneNumber=" + phoneNumber + ", password=" + password + '}';
    }
    
}
