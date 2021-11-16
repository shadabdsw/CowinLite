package com.shadabdsw.thymeleafdemo.Model;

import java.util.Date;

public class Vaccination {

    private String vaccinationType;
    private String vaccinationCentre;
    private String vaccinationBy;
    private Date vaccinationDate;
    private Date nextVaccinationDate;

    public Vaccination(String vaccinationType, String vaccinationCentre, String vaccinationBy, Date vaccinationDate, Date nextVaccinationDate) {
        this.vaccinationType = vaccinationType;
        this.vaccinationCentre = vaccinationCentre;
        this.vaccinationBy = vaccinationBy;
        this.vaccinationDate = vaccinationDate;
        this.nextVaccinationDate = nextVaccinationDate;
    }

    public Vaccination() {
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


    public Date getVaccinationDate() {
        return vaccinationDate;
    }


    public void setVaccinationDate(Date vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
    }

    public Date getNextVaccinationDate() {
        return nextVaccinationDate;
    }

    public void setNextVaccinationDate(Date nextVaccinationDate) {
        this.nextVaccinationDate = nextVaccinationDate;
    }


    @Override
    public String toString() {
        return "Vaccination [nextVaccinationDate=" + nextVaccinationDate + ", vaccinationBy=" + vaccinationBy
                + ", vaccinationCentre=" + vaccinationCentre + ", vaccinationDate=" + vaccinationDate
                + ", vaccinationType=" + vaccinationType + "]";
    }

}
