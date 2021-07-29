package com.lctapp.lct.Classes.Models.Responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionResp {
    public int orgId;
    public String memberNo;
    public Object fromDate;
    public Object toDate;
    public Object userName;
    public Object totalAmount;
    public String billNo;
    public String txnDate;
    public String serviceName;
    public Object subServiceName;
    public int deviceId;
    public String status;
    public Object cardNo;
    public Object accNo;
    public String name;
    public double serviceAmount;
    public String productName;
    public String merchantName;
    public Object hqName;
    public Object providerName;
    public int merchantId;
    public Object productId;
    public int userId;
    public Object orgName;
    public double memberBalance;
    public Object benefitBalance;
    public String invoiceNo;
    public Object basketValue;
    public Object basketBalance;
    public Object utilization;
    public Object createdBy;
    @JsonProperty("Description")
    public Object description;
    public Object created_on;
    @JsonProperty("ModuleAccessed")
    public Object moduleAccessed;
    public Object username;
    @JsonProperty("LoggedInUser")
    public Object loggedInUser;
    public int count;
    public Object dateofBirth;
    public Object memberType;
    public double coverBalance;
    public Object serName;
    public Object reportDtls;
    public Object sessionName;
    public double serviceBalance;
    public double serviceValue;
    public Object memberNumber;
    public Object memberName;
    public int transId;
    public int schemeAssigned;
    public Object foreignOrgId;
    @JsonProperty("InsurerPortalAcl")
    public boolean insurerPortalAcl;


    public int getOrgId() {
        return orgId;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public Object getFromDate() {
        return fromDate;
    }

    public Object getToDate() {
        return toDate;
    }

    public Object getUserName() {
        return userName;
    }

    public Object getTotalAmount() {
        return totalAmount;
    }

    public String getBillNo() {
        return billNo;
    }

    public String getTxnDate() {
        return txnDate;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Object getSubServiceName() {
        return subServiceName;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public String getStatus() {
        return status;
    }

    public Object getCardNo() {
        return cardNo;
    }

    public Object getAccNo() {
        return accNo;
    }

    public String getName() {
        return name;
    }

    public double getServiceAmount() {
        return serviceAmount;
    }

    public String getProductName() {
        return productName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public Object getHqName() {
        return hqName;
    }

    public Object getProviderName() {
        return providerName;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public Object getProductId() {
        return productId;
    }

    public int getUserId() {
        return userId;
    }

    public Object getOrgName() {
        return orgName;
    }

    public double getMemberBalance() {
        return memberBalance;
    }

    public Object getBenefitBalance() {
        return benefitBalance;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public Object getBasketValue() {
        return basketValue;
    }

    public Object getBasketBalance() {
        return basketBalance;
    }

    public Object getUtilization() {
        return utilization;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public Object getDescription() {
        return description;
    }

    public Object getCreated_on() {
        return created_on;
    }

    public Object getModuleAccessed() {
        return moduleAccessed;
    }

    public Object getUsername() {
        return username;
    }

    public Object getLoggedInUser() {
        return loggedInUser;
    }

    public int getCount() {
        return count;
    }

    public Object getDateofBirth() {
        return dateofBirth;
    }

    public Object getMemberType() {
        return memberType;
    }

    public double getCoverBalance() {
        return coverBalance;
    }

    public Object getSerName() {
        return serName;
    }

    public Object getReportDtls() {
        return reportDtls;
    }

    public Object getSessionName() {
        return sessionName;
    }

    public double getServiceBalance() {
        return serviceBalance;
    }

    public double getServiceValue() {
        return serviceValue;
    }

    public Object getMemberNumber() {
        return memberNumber;
    }

    public Object getMemberName() {
        return memberName;
    }

    public int getTransId() {
        return transId;
    }

    public int getSchemeAssigned() {
        return schemeAssigned;
    }

    public Object getForeignOrgId() {
        return foreignOrgId;
    }

    public boolean isInsurerPortalAcl() {
        return insurerPortalAcl;
    }
}
