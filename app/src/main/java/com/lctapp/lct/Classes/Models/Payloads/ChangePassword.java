package com.lctapp.lct.Classes.Models.Payloads;

public class ChangePassword {
    private String memberNo;
    private String password;
    private String otp;

    public ChangePassword(String memberNo, String password, String otp) {
        this.memberNo = memberNo;
        this.password = password;
        this.otp = otp;
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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
