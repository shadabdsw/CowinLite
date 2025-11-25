package com.shadabdsw.cowinlitefe.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VaccineEditRequest {
    String phoneNumber;

    String name;

    String memberName;

    String aadhaar;

    String vaccinationStatus;

    String vaccinationType;

    String vaccinationCentre;

    String vaccinationBy;

    String vaccinationDate;

    String nextVaccinationDate;
}
