package com.shadabdsw.thymeleafdemo.Model;

import java.util.List;

public class Member {

    private String phnNumber;
    private String adhaar;
    private String memberName;
    private String gender;
    private String yob;
    private String vaccinationStatus;
    private List<Vaccination> vaccine;

    public Member() {
    }

    public Member(String phnNumber, String adhaar, String memberName, String gender, String yob,
            String vaccinationStatus,
            List<Vaccination> vaccine) {
        this.phnNumber = phnNumber;
        this.adhaar = adhaar;
        this.memberName = memberName;
        this.gender = gender;
        this.yob = yob;
        this.vaccinationStatus = vaccinationStatus;
        this.vaccine = vaccine;
    }

    public String getPhnNumber() {
        return phnNumber;
    }

    public void setPhnNumber(String phnNumber) {
        this.phnNumber = phnNumber;
    }

    public String getAdhaar() {
        return adhaar;
    }

    public String getYob() {
        return yob;
    }

    public void setYob(String yob) {
        this.yob = yob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
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
        return "Member [adhaar=" + adhaar + ", gender=" + gender + ", memberName=" + memberName + ", phnNumber="
                + phnNumber + ", vaccinationStatus=" + vaccinationStatus + ", vaccine=" + vaccine + ", yob=" + yob
                + "]";
    }

}
