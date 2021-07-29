package com.lctapp.lct.Classes.Models.Member;

import java.util.List;

public class Voucher {
    public double voucherBalance;
    public String accNo;
    public String voucherCode;
    public String voucherName;
    public int voucherId;
    public double voucherValue;
    public double usage;
    public String schemeType;
    public List<Service> services;
    public Double used;

    public double getVoucherBalance() {
        return voucherBalance;
    }

    public String getAccNo() {
        return accNo;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public Double getVoucherValue() {
        return voucherValue;
    }

    public double getUsage() {
        return usage;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public List<Service> getServices() {
        return services;
    }

    public double getUsed() {
        return used;
    }
}
