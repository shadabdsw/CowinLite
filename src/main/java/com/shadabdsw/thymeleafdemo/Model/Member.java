package com.shadabdsw.thymeleafdemo.Model;

import java.util.Date;
import java.util.List;

public class Member {
    private String adhaar;
    private String name;
    private String gender;
    private Date dob;
    private String vaccinationStatus;
    private List<Vaccination> vaccine;

    public Member() {
    }

    public Member(String adhaar, String name, String gender, Date dob, String vaccinationStatus,
            List<Vaccination> vaccine) {
        this.adhaar = adhaar;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.vaccinationStatus = vaccinationStatus;
        this.vaccine = vaccine;
    }

    public String getAdhaar() {
        return adhaar;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
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

    public String getVaccinationStatus() {
        return vaccinationStatus;
    }

    public void setVaccinationStatus(String vaccinationStatus) {
        this.vaccinationStatus = vaccinationStatus;
    }

    @Override
    public String toString() {
        return "Member [adhaar=" + adhaar + ", dob=" + dob + ", gender=" + gender + ", name=" + name
                + ", vaccinationStatus=" + vaccinationStatus + ", vaccine=" + vaccine + "]";
    }

    

}
