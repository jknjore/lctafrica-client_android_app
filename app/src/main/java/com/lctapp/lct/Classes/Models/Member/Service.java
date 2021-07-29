package com.lctapp.lct.Classes.Models.Member;

public class Service {
    public int serviceId;
    public String serviceCode;
    public String serviceName;
    public double serviceValue;
    public double serviceBalance;
    public double voucherBalance;
    public String requireAuth;
    public double used;
    public double price;
    public int basketId;

    public int getServiceId() {
        return serviceId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getServiceValue() {
        return serviceValue;
    }

    public double getServiceBalance() {
        return serviceBalance;
    }

    public double getVoucherBalance() {
        return voucherBalance;
    }

    public String getRequireAuth() {
        return requireAuth;
    }

    public double getUsed() {
        return used;
    }

    public double getPrice() {
        return price;
    }

    public int getBasketId() {
        return basketId;
    }
}
