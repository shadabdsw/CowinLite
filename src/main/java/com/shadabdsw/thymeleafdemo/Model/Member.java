package com.shadabdsw.thymeleafdemo.Model;

import java.util.List;

public class Member {
    private String adhaar;
    private String name;
    private String gender;
    private String dob;
    private List<Vaccination> vaccine;

    public Member(String adhaar, String name, String gender, String dob) {
        this.adhaar = adhaar;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
    }

    public Member(String adhaar, String name, String gender, String dob, List<Vaccination> vaccine) {
        this.adhaar = adhaar;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.vaccine = vaccine;
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

    public List<Vaccination> getVaccine() {
        return vaccine;
    }

    public void setVaccine(List<Vaccination> vaccine) {
        this.vaccine = vaccine;
    }

    @Override
    public String toString() {
        return "Member [adhaar=" + adhaar + ", dob=" + dob + ", gender=" + gender + ", name=" + name + ", vaccine="
                + vaccine + "]";
    }

}
