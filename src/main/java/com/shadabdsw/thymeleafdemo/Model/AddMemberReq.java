package com.shadabdsw.thymeleafdemo.Model;

public class AddMemberReq {

    private String phoneNumber;
    private Member member;

    public AddMemberReq(String phoneNumber, Member member) {
        this.phoneNumber = phoneNumber;
        this.member = member;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public Member getMember() {
        return member;
    }
    public void setMember(Member member) {
        this.member = member;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
}
