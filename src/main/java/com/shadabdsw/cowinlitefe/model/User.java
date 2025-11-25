package com.shadabdsw.cowinlitefe.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    public User(String name, String newPhoneNumber, String password, String userType, List<Member> member) {}

    String _id;

    String name;

    String phoneNumber;

    String password;

    String userType;

    List<Member> member;
}
