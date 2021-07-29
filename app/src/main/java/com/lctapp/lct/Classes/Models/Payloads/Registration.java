package com.lctapp.lct.Classes.Models.Payloads;

public class Registration {
    private String memberNo;
    private String password;
    private String phoneNumber;

    public Registration(String memberNo, String password, String phoneNumber) {
        this.memberNo = memberNo;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
