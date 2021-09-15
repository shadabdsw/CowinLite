package com.shadabdsw.thymeleafdemo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "CowinUsers")
public class User {
    private String phoneNumber;
    private String password;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User [phoneNumber=" + phoneNumber + ",password=" + password + "]";
    }
}
