package com.shadabdsw.thymeleafdemo.Model;

public class Registration {

    private String name;
    private String phoneNumber;
    private String password;

    public Registration(String name, String phoneNumber, String password) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public Registration() {
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Registration [name=" + name + ", phoneNumber=" + phoneNumber + ", password=" + password + "]";
    }
    
}
