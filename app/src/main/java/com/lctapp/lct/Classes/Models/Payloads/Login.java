package com.lctapp.lct.Classes.Models.Payloads;

public class Login {
    private String memberNo;
    private String password;

    public Login(String memberNo, String password) {
        this.memberNo = memberNo;
        this.password = password;
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
}
