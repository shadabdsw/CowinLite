package com.shadabdsw.thymeleafdemo.Model;

public class VaccineEditReq {

    private String phnNumber;
    private String name;
    private String adhaar;
    private String vaccinationStatus;
    private String vaccinationType;
    private String vaccinationCentre;
    private String vaccinationBy;
    private String vaccinationDate;

    public VaccineEditReq(String phnNumber, String name, String adhaar, String vaccinationStatus, String vaccinationType,
            String vaccinationCentre, String vaccinationBy, String vaccinationDate) {
        this.phnNumber = phnNumber;
        this.name = name;
        this.adhaar = adhaar;
        this.vaccinationStatus = vaccinationStatus;
        this.vaccinationType = vaccinationType;
        this.vaccinationCentre = vaccinationCentre;
        this.vaccinationBy = vaccinationBy;
        this.vaccinationDate = vaccinationDate;
    }

    public String getPhnNumber() {
        return phnNumber;
    }
    public void setPhnNumber(String phnNumber) {
        this.phnNumber = phnNumber;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAdhaar() {
        return adhaar;
    }
    public void setAdhaar(String adhaar) {
        this.adhaar = adhaar;
    }
    public String getVaccinationStatus() {
        return vaccinationStatus;
    }
    public void setVaccinationStatus(String vaccinationStatus) {
        this.vaccinationStatus = vaccinationStatus;
    }
    public String getVaccinationType() {
        return vaccinationType;
    }
    public void setVaccinationType(String vaccinationType) {
        this.vaccinationType = vaccinationType;
    }
    public String getVaccinationCentre() {
        return vaccinationCentre;
    }
    public void setVaccinationCentre(String vaccinationCentre) {
        this.vaccinationCentre = vaccinationCentre;
    }
    public String getVaccinationBy() {
        return vaccinationBy;
    }
    public void setVaccinationBy(String vaccinationBy) {
        this.vaccinationBy = vaccinationBy;
    }
    public String getVaccinationDate() {
        return vaccinationDate;
    }
    public void setVaccinationDate(String vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
    }

    @Override
    public String toString() {
        return "VaccineEditReq [adhaar=" + adhaar + ", name=" + name + ", phnNumber=" + phnNumber
                + ", vaccinationBy=" + vaccinationBy + ", vaccinationCentre=" + vaccinationCentre + ", vaccinationDate="
                + vaccinationDate + ", vaccinationStatus=" + vaccinationStatus + ", vaccinationType=" + vaccinationType
                + "]";
    }

}
