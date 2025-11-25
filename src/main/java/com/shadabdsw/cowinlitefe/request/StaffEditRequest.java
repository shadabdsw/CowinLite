package com.shadabdsw.cowinlitefe.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffEditRequest {
    String name;

    String oldPhoneNumber;

    String newPhoneNumber;
}