package com.shadabdsw.thymeleafdemo.Model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "CowinUsers")
public class User {
    private String phoneNumber;
    private String password;
    private List<Member> member;

    public User() {
    }

    public User(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public User(String phoneNumber, String password, List<Member> member) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.member = member;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public List<Member> getMember() {
        return member;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setMember(List<Member> member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "User [phoneNumber=" + phoneNumber + ",password=" + password + "]";
    }
}
