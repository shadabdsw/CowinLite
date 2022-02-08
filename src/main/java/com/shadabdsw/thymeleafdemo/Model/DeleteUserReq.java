package com.shadabdsw.thymeleafdemo.Model;

public class DeleteUserReq {

    private String phoneNumber;

    public DeleteUserReq() {
    }

    public DeleteUserReq(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "DeleteUserReq [phoneNumber=" + phoneNumber + "]";
    }
    
}
