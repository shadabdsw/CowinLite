package com.shadabdsw.cowinlitefe.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Vaccination {
    String vaccinationType;

    String vaccinationCentre;

    String vaccinationBy;

    Date vaccinationDate;

    Date nextVaccinationDate;
}
