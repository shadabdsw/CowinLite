package com.shadabdsw.thymeleafdemo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Members")
public class Member {
    private String adhaar;
    private String name;
    private String gender;
    private String dob;
    
    public String getAdhaar() {
        return adhaar;
    }
    public String getDob() {
        return dob;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAdhaar(String adhaar) {
        this.adhaar = adhaar;
    }
}
