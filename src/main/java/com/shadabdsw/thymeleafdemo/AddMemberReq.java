package com.shadabdsw.thymeleafdemo;

import com.shadabdsw.thymeleafdemo.Model.Member;

public class AddMemberReq {

    private String phoneNumber;
    public AddMemberReq(String phoneNumber, Member member) {
        this.phoneNumber = phoneNumber;
        this.member = member;
    }
    private Member member;
    
    public Member getMember() {
        return member;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setMember(Member member) {
        this.member = member;
    }
}
