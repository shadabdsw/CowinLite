package com.shadabdsw.cowinlitefe.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Member {
    String phoneNumber;

    String aadhaar;

    String memberName;

    String gender;

    String yob;

    String vaccinationStatus;

    List<Vaccination> vaccine;
}
