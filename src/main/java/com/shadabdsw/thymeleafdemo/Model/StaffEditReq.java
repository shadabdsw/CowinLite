package com.shadabdsw.thymeleafdemo.Model;

public class StaffEditReq {

    private String name;
    private String oldPhoneNumber;
    private String newPhoneNumber;

    public StaffEditReq() {
    }
    
    public StaffEditReq(String name, String oldPhoneNumber, String newPhoneNumber) {
    this.name = name;
    this.oldPhoneNumber = oldPhoneNumber;
    this.newPhoneNumber = newPhoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldPhoneNumber() {
        return oldPhoneNumber;
    }

    public void setOldPhoneNumber(String oldPhoneNumber) {
        this.oldPhoneNumber = oldPhoneNumber;
    }

    public String getNewPhoneNumber() {
        return newPhoneNumber;
    }

    public void setNewPhoneNumber(String newPhoneNumber) {
        this.newPhoneNumber = newPhoneNumber;
    }

    @Override
    public String toString() {
        return "StaffEditReq{" + "name=" + name + ", oldPhoneNumber=" + oldPhoneNumber + ", newPhoneNumber=" + newPhoneNumber + '}';
    }

}