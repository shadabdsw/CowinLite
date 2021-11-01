package com.shadabdsw.thymeleafdemo.Model;

public class Vaccination {

    private String vaccinationStatus;
    private String vaccinationType;
    private String vaccinationCentre;
    private String vaccinationBy;
    private String vaccinationDate;

    public Vaccination(String vaccinationStatus, String vaccinationType, String vaccinationCentre, String vaccinationBy, String vaccinationDate) {
        this.vaccinationStatus = vaccinationStatus;
        this.vaccinationType = vaccinationType;
        this.vaccinationCentre = vaccinationCentre;
        this.vaccinationBy = vaccinationBy;
        this.vaccinationDate = vaccinationDate;
    }
    
    public String getVaccinationStatus() {
        return vaccinationStatus;
    }
    public void setVaccinationStatus(String vaccinationStatus) {
        this.vaccinationStatus = vaccinationStatus;
    }
    public String getvaccinationType() {
        return vaccinationType;
    }
    public void setvaccinationType(String vaccinationType) {
        this.vaccinationType = vaccinationType;
    }
    public String getVaccinationCentre() {
        return vaccinationCentre;
    }
    public void setVaccinationCentre(String vaccinationCentre) {
        this.vaccinationCentre = vaccinationCentre;
    }
    public String getvaccinationBy() {
        return vaccinationBy;
    }
    public void setvaccinationBy(String vaccinationBy) {
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
        return "Vaccination [vaccinationBy=" + vaccinationBy + ", vaccinationCentre=" + vaccinationCentre
                + ", vaccinationDate=" + vaccinationDate + ", vaccinationStatus=" + vaccinationStatus
                + ", vaccinationType=" + vaccinationType + "]";
    }
    
    
}
