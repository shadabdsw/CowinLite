package com.shadabdsw.thymeleafdemo.Model;

public class Registration {

    private String name;
    private String phoneNumber;
    private String password;
    private String userType;

    public Registration() {

    }
    public Registration(String name, String phoneNumber, String password, String userType) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userType = userType;
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

    public String getUserType() {
        return userType;
    }

    @Override
    public String toString() {
        return "Registration [name=" + name + ", password=" + password + ", phoneNumber=" + phoneNumber + ", userType="
                + userType + "]";
    }    
    
}
