package com.lctapp.lct.Classes.Models.Payloads;

public class UserLocationData {
    private Integer merchantId;
    private Double latitude;
    private Double longitude;

    public UserLocationData(Integer merchantId, Double latitude, Double longitude) {
        this.merchantId = merchantId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
