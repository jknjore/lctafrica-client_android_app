package com.lctapp.lct.Classes.Models.Payloads;

public class Member {
    private String memberNo;

    public Member(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }
}
