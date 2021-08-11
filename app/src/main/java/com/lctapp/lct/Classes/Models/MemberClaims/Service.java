package com.lctapp.lct.Classes.Models.MemberClaims;

import java.io.Serializable;

public class Service implements Serializable {
    public int parentServiceId;
    public int level;
    public boolean hasChild;
    public int serviceId;
    public Object serviceCode;
    public Object merchantCode;
    public String serviceName;
    public double quantity;
    public double price;
    public double serviceValue;
    public boolean active;
    public int createdBy;
    public int respCode;
    public boolean isActive;
    public Object params;
    public Object compoType;
    public Object compoName;
    public int serSubtype;
    public int count;
    public int categoryId;
    public Object serviceDesc;
    public Object categoryName;
    public Object image;
    public Object serviceDetails;
    public Object uom;
    public Object serSubtypeName;
    public double limit;
    public double ipLimit;
    public int ipLimitType;
    public int serSubtypeId;
    public Object ipLimitPlaceHolder;
    public double opLimit;
    public int opLimitType;
    public Object limitDetail;
    public boolean requireAuth;
    public double serviceBalance;
    public double usage;
    public Object status;
    public boolean notNewToVoucher;
    public boolean notRemovedFromVoucher;
    public boolean isOld;
    public boolean isRemoved;
    public Object applicable;
    public Object applicableTo;
    public double coverLimit;
    public double used;
    public double voucherBalance;
    public double coverBalance;
    public int basketId;
    public int voucherId;
    public double spentService;
    public double spentBasket;
    public double basketValue;
    public double basketBalance;

    public int getParentServiceId() {
        return parentServiceId;
    }

    public int getLevel() {
        return level;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public int getServiceId() {
        return serviceId;
    }

    public Object getServiceCode() {
        return serviceCode;
    }

    public Object getMerchantCode() {
        return merchantCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getServiceValue() {
        return serviceValue;
    }

    public boolean isActive() {
        return active;
    }

    public Object getParams() {
        return params;
    }

    public Object getCompoType() {
        return compoType;
    }

    public Object getCompoName() {
        return compoName;
    }

    public int getSerSubtype() {
        return serSubtype;
    }

    public int getCount() {
        return count;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public Object getServiceDesc() {
        return serviceDesc;
    }

    public Object getCategoryName() {
        return categoryName;
    }

    public Object getImage() {
        return image;
    }

    public Object getServiceDetails() {
        return serviceDetails;
    }

    public Object getUom() {
        return uom;
    }

    public Object getSerSubtypeName() {
        return serSubtypeName;
    }

    public double getLimit() {
        return limit;
    }

    public double getIpLimit() {
        return ipLimit;
    }

    public int getIpLimitType() {
        return ipLimitType;
    }

    public int getSerSubtypeId() {
        return serSubtypeId;
    }

    public Object getIpLimitPlaceHolder() {
        return ipLimitPlaceHolder;
    }

    public double getOpLimit() {
        return opLimit;
    }

    public int getOpLimitType() {
        return opLimitType;
    }

    public Object getLimitDetail() {
        return limitDetail;
    }

    public boolean isRequireAuth() {
        return requireAuth;
    }

    public double getServiceBalance() {
        return serviceBalance;
    }

    public double getUsage() {
        return usage;
    }

    public Object getStatus() {
        return status;
    }

    public boolean isNotNewToVoucher() {
        return notNewToVoucher;
    }

    public boolean isNotRemovedFromVoucher() {
        return notRemovedFromVoucher;
    }

    public boolean isOld() {
        return isOld;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public Object getApplicable() {
        return applicable;
    }

    public Object getApplicableTo() {
        return applicableTo;
    }

    public double getCoverLimit() {
        return coverLimit;
    }

    public double getUsed() {
        return used;
    }

    public double getVoucherBalance() {
        return voucherBalance;
    }

    public double getCoverBalance() {
        return coverBalance;
    }

    public int getBasketId() {
        return basketId;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public double getSpentService() {
        return spentService;
    }

    public double getSpentBasket() {
        return spentBasket;
    }

    public double getBasketValue() {
        return basketValue;
    }

    public double getBasketBalance() {
        return basketBalance;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public int getRespCode() {
        return respCode;
    }
}
