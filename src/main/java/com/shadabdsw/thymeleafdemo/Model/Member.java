package com.shadabdsw.thymeleafdemo.Model;


public class Member {
    private String adhaar;
    private String name;
    private String gender;
    private String dob;

    public Member(String adhaar, String name, String gender, String dob) {
        this.adhaar = adhaar;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
    }

    public Member() {
    }

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

    @Override
    public String toString() {
        return "Member [adhaar=" + adhaar + ",name=" + name + ",gender=" + gender + ",dob=" + dob + "]";
    }
}
