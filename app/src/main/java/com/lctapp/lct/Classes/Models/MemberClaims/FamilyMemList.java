package com.lctapp.lct.Classes.Models.MemberClaims;

import java.io.Serializable;

public class FamilyMemList implements Serializable {
    public int memberId;
    public int familyMemId;
    public String famMemberNo;
    public Object famMemFirstName;
    public Object famMemLastName;
    public String famMemFullName;
    public String famDob;
    public String famGender;
    public Object famMemNationalId;
    public int relationId;
    public Object relationDesc;
    public Object familyMemPic;
    public int familyMemBioId;
    public int tempFamilyMemId;
    public boolean editMode;
    public Object iconStyle;
    public boolean cancelled;
    public int createdBy;
    public int respCode;
    public Object famPic;
    public boolean isChecked;
    public boolean isNew;
    public Object verifyStatus;
    public Object approveStatus;
    public String lctCode;
    public String memberType;
    public Object idNumber;
    public boolean active;
    public Object disabled;
    public Object famMemPhoneNumber;
    public Object cellPhone;

    public int getMemberId() {
        return memberId;
    }

    public int getFamilyMemId() {
        return familyMemId;
    }

    public String getFamMemberNo() {
        return famMemberNo;
    }

    public Object getFamMemFirstName() {
        return famMemFirstName;
    }

    public Object getFamMemLastName() {
        return famMemLastName;
    }

    public String getFamMemFullName() {
        return famMemFullName;
    }

    public String getFamDob() {
        return famDob;
    }

    public String getFamGender() {
        return famGender;
    }

    public Object getFamMemNationalId() {
        return famMemNationalId;
    }

    public int getRelationId() {
        return relationId;
    }

    public Object getRelationDesc() {
        return relationDesc;
    }

    public Object getFamilyMemPic() {
        return familyMemPic;
    }

    public int getFamilyMemBioId() {
        return familyMemBioId;
    }

    public int getTempFamilyMemId() {
        return tempFamilyMemId;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public Object getIconStyle() {
        return iconStyle;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public int getRespCode() {
        return respCode;
    }

    public Object getFamPic() {
        return famPic;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public boolean isNew() {
        return isNew;
    }

    public Object getVerifyStatus() {
        return verifyStatus;
    }

    public Object getApproveStatus() {
        return approveStatus;
    }

    public String getLctCode() {
        return lctCode;
    }

    public String getMemberType() {
        return memberType;
    }

    public Object getIdNumber() {
        return idNumber;
    }

    public boolean isActive() {
        return active;
    }

    public Object getDisabled() {
        return disabled;
    }

    public Object getFamMemPhoneNumber() {
        return famMemPhoneNumber;
    }

    public Object getCellPhone() {
        return cellPhone;
    }
}
