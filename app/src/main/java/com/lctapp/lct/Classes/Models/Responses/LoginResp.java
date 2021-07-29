package com.lctapp.lct.Classes.Models.Responses;

public class LoginResp {
    public int respCode;
    public String respMessage;
    public Object email;
    public String phoneNumber;
    public String memberNo;
    public Object otp;
    public String password;
    public int id;


    public int getRespCode() {
        return respCode;
    }

    public String getRespMessage() {
        return respMessage;
    }

    public Object getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public Object getOtp() {
        return otp;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }
}
