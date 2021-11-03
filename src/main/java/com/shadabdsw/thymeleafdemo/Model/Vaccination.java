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
        return "Vaccination [vaccinationBy=" + vaccinationBy + ", vaccinationCentre=" + vaccinationCentre
                + ", vaccinationDate=" + vaccinationDate + ", vaccinationType=" + vaccinationType + "]";
    }

}
