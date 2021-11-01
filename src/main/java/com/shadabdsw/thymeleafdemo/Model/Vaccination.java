package com.shadabdsw.thymeleafdemo.Model;

public class Vaccination {

    private String vaccinationType;
    private String vaccinationCentre;
    private String vaccinationBy;
    private String vaccinationDate;

    public Vaccination(String vaccinationType, String vaccinationCentre, String vaccinationBy, String vaccinationDate) {
        this.vaccinationType = vaccinationType;
        this.vaccinationCentre = vaccinationCentre;
        this.vaccinationBy = vaccinationBy;
        this.vaccinationDate = vaccinationDate;
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
        return "VaccinationDetails [vaccinationBy=" + vaccinationBy + ", vaccinationCentre=" + vaccinationCentre
                + ", vaccinationDate=" + vaccinationDate + ", vaccinationType=" + vaccinationType + "]";
    }
    
}
