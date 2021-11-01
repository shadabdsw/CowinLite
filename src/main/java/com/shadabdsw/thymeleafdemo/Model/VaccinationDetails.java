package com.shadabdsw.thymeleafdemo.Model;

public class VaccinationDetails {

    private String vaccine;
    private String vaccinationCentre;
    private String vaccinatedBy;
    private String vaccinationDate;

    public VaccinationDetails(String vaccine, String vaccinationCentre, String vaccinatedBy, String vaccinationDate) {
        this.vaccine = vaccine;
        this.vaccinationCentre = vaccinationCentre;
        this.vaccinatedBy = vaccinatedBy;
        this.vaccinationDate = vaccinationDate;
    }
    public String getVaccine() {
        return vaccine;
    }
    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }
    public String getVaccinationCentre() {
        return vaccinationCentre;
    }
    public void setVaccinationCentre(String vaccinationCentre) {
        this.vaccinationCentre = vaccinationCentre;
    }
    public String getVaccinatedBy() {
        return vaccinatedBy;
    }
    public void setVaccinatedBy(String vaccinatedBy) {
        this.vaccinatedBy = vaccinatedBy;
    }
    public String getVaccinationDate() {
        return vaccinationDate;
    }
    public void setVaccinationDate(String vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
    }
    @Override
    public String toString() {
        return "VaccinationDetails [vaccinatedBy=" + vaccinatedBy + ", vaccinationCentre=" + vaccinationCentre
                + ", vaccinationDate=" + vaccinationDate + ", vaccine=" + vaccine + "]";
    }
    
}
