package com.shadabdsw.cowinlitefe.request;

import com.shadabdsw.cowinlitefe.model.Member;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddMemberRequest {
    String phoneNumber;

    Member member;
}
