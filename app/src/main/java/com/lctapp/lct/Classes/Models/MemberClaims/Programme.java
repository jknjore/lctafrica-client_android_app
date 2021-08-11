package com.lctapp.lct.Classes.Models.MemberClaims;

import com.lctapp.lct.Classes.Models.MemberClaims.Membervoucher;

import java.io.Serializable;
import java.util.List;

public class Programme implements Serializable {
    public int programmeId;
    public String programmeCode;
    public String programmeDesc;
    public Object programmeName;
    public boolean active;
    public int createdBy;
    public int respCode;
    public boolean isActive;
    public Object services;
    public double programmeValue;
    public Object currency;
    public int productId;
    public int schemeId;
    public Object productName;
    public Object productCode;
    public Object insuranceCode;
    public Object vouchers;
    public List<Membervoucher> membervouchers;
    public Object itmList;
    public Object chtmList;
    public Object intmList;
    public Object startDate;
    public Object endDate;
    public String itmModes;
    public String chtmModes;
    public String intmModes;
    public int count;
    public Object programmeType;
    public int orgId;
    public int coverTypeId;
    public Object otmList;
    public int voucherId;
    public int serviceId;
    public double serviceValue;
    public double price;
    public double serviceBalance;
    public Object tier;


    public int getProgrammeId() {
        return programmeId;
    }

    public String getProgrammeCode() {
        return programmeCode;
    }

    public String getProgrammeDesc() {
        return programmeDesc;
    }

    public Object getProgrammeName() {
        return programmeName;
    }

    public boolean isActive() {
        return active;
    }

    public Object getServices() {
        return services;
    }

    public double getProgrammeValue() {
        return programmeValue;
    }

    public Object getCurrency() {
        return currency;
    }

    public int getProductId() {
        return productId;
    }

    public int getSchemeId() {
        return schemeId;
    }

    public Object getProductName() {
        return productName;
    }

    public Object getProductCode() {
        return productCode;
    }

    public Object getInsuranceCode() {
        return insuranceCode;
    }

    public Object getVouchers() {
        return vouchers;
    }

    public List<Membervoucher> getMembervouchers() {
        return membervouchers;
    }

    public Object getItmList() {
        return itmList;
    }

    public Object getChtmList() {
        return chtmList;
    }

    public Object getIntmList() {
        return intmList;
    }

    public Object getStartDate() {
        return startDate;
    }

    public Object getEndDate() {
        return endDate;
    }

    public String getItmModes() {
        return itmModes;
    }

    public String getChtmModes() {
        return chtmModes;
    }

    public String getIntmModes() {
        return intmModes;
    }

    public int getCount() {
        return count;
    }

    public Object getProgrammeType() {
        return programmeType;
    }

    public int getOrgId() {
        return orgId;
    }

    public int getCoverTypeId() {
        return coverTypeId;
    }

    public Object getOtmList() {
        return otmList;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public double getServiceValue() {
        return serviceValue;
    }

    public double getPrice() {
        return price;
    }

    public double getServiceBalance() {
        return serviceBalance;
    }

    public Object getTier() {
        return tier;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public int getRespCode() {
        return respCode;
    }
}
