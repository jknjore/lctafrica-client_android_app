package com.lctapp.lct.Classes.Models.Payloads;

public class StartVisitData {
    private Integer merchantId;
    private String memberNo;
    private String deviceID;
    private String guardian;

    public StartVisitData(Integer merchantId, String memberNo, String deviceID, String guardian) {
        this.merchantId = merchantId;
        this.memberNo = memberNo;
        this.deviceID = deviceID;
        this.guardian = guardian;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getGuardian() {
        return guardian;
    }

    public void setGuardian(String guardian) {
        this.guardian = guardian;
    }
}
