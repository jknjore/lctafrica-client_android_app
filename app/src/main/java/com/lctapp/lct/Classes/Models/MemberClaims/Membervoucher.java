package com.lctapp.lct.Classes.Models.MemberClaims;

import com.lctapp.lct.Classes.Models.MemberClaims.Service;

import java.io.Serializable;
import java.util.List;

public class Membervoucher implements Serializable {
    public int voucherId;
    public Object voucherCode;
    public String voucherDesc;
    public boolean active;
    public int createdBy;
    public int respCode;
    public boolean isActive;
    public List<Service> services;
    public double voucherValue;
    public Object voucherType;
    public Object currency;
    public Object frqSelect;
    public Object modeSelect;
    public Object startDate;
    public Object endDate;
    public boolean expanded;
    public Object schemeType;
    public Object voucherName;
    public double voucherBalance;
    public Object amountRemaining;
    public double usage;
    public String used;
    public Object value;
    public boolean notNewToProgramme;
    public boolean valueHasNotChanged;
    public boolean voucherNotRemoved;
    public boolean isOld;
    public boolean isRemoved;
    public String coverType;
    public int count;
    public double serviceValue;
    public double coverLimit;
    public double serviceBalance;
    public int orgId;

    public int getVoucherId() {
        return voucherId;
    }

    public Object getVoucherCode() {
        return voucherCode;
    }

    public String getVoucherDesc() {
        return voucherDesc;
    }

    public boolean isActive() {
        return active;
    }

    public List<Service> getServices() {
        return services;
    }

    public double getVoucherValue() {
        return voucherValue;
    }

    public Object getVoucherType() {
        return voucherType;
    }

    public Object getCurrency() {
        return currency;
    }

    public Object getFrqSelect() {
        return frqSelect;
    }

    public Object getModeSelect() {
        return modeSelect;
    }

    public Object getStartDate() {
        return startDate;
    }

    public Object getEndDate() {
        return endDate;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public Object getSchemeType() {
        return schemeType;
    }

    public Object getVoucherName() {
        return voucherName;
    }

    public double getVoucherBalance() {
        return voucherBalance;
    }

    public Object getAmountRemaining() {
        return amountRemaining;
    }

    public double getUsage() {
        return usage;
    }

    public String getUsed() {
        return used;
    }

    public Object getValue() {
        return value;
    }

    public boolean isNotNewToProgramme() {
        return notNewToProgramme;
    }

    public boolean isValueHasNotChanged() {
        return valueHasNotChanged;
    }

    public boolean isVoucherNotRemoved() {
        return voucherNotRemoved;
    }

    public boolean isOld() {
        return isOld;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public String getCoverType() {
        return coverType;
    }

    public int getCount() {
        return count;
    }

    public double getServiceValue() {
        return serviceValue;
    }

    public double getCoverLimit() {
        return coverLimit;
    }

    public double getServiceBalance() {
        return serviceBalance;
    }

    public int getOrgId() {
        return orgId;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public int getRespCode() {
        return respCode;
    }
}
