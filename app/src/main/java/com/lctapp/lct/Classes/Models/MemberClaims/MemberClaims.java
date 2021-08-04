package com.lctapp.lct.Classes.Models.MemberClaims;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lctapp.lct.Classes.Models.MemberClaims.FamilyMemList;
import com.lctapp.lct.Classes.Models.MemberClaims.Voucher;
import com.lctapp.lct.Classes.Models.MemberClaims.Programme;

import java.util.List;

public class MemberClaims {
    public String memberId;
    public int customerId;
    public Object merchantId;
    public String memberNo;
    public Object firstName;
    public Object lastName;
    public String fullName;
    public String dateOfBirth;
    public Object deviceId;
    public int respCode;
    public Object bioId;
    @JsonProperty("FileName")
    public Object fileName;
    public int createdBy;
    public Object memberIdList;
    public List<Voucher> vouchers;
    public List<Object> memberServices;
    public boolean selected;
    public boolean expanded;
    public String memberType;
    public String lctCode;
    public Object title;
    public Object otherName;
    public Object idPassPortNo;
    public String gender;
    public Object maritalStatus;
    public Object weight;
    public Object height;
    public String nhifNo;
    public Object employerName;
    public Object occupation;
    public Object nationality;
    public Object postalAdd;
    public Object physicalAdd;
    public Object homeTelephone;
    public Object officeTelephone;
    public String cellPhone;
    public Object email;
    public Object cardStatus;
    public Object uploadStatus;
    public int accType;
    public String cardNumber;
    public Object serialNumber;
    public Object expiryDate;
    public Object status;
    public List<Programme> programmes;
    public Object binRange;
    public int programmeId;
    public String programmeDesc;
    public Object imageList;
    public Object bioImages;
    public Object biometrics;
    public Object bioImage;
    public double cardBalance;
    public Object cardSerialNo;
    public Object cardPin;
    public int familySize;
    public int bnfGrpId;
    public Object bnfStatus;
    public Object verifyStatus;
    public Object approveStatus;
    public Object bioStatus;
    public boolean printStatus;
    public boolean rightPrint;
    public boolean leftPrint;
    public Object memType;
    public List<FamilyMemList> familyMemList;
    public boolean isChecked;
    public Object serialNo;
    public Object verifySelection;
    public Object famVerifySelection;
    public Object userName;
    public Object userEmail;
    public int topupCount;
    public Object bnfGrpName;
    public int productId;
    public boolean active;
    public int accNo;
    public String accNumber;
    public int accId;
    public double accBalance;
    public int orgId;
    public Object memberProgrammes;
    public Object fromDate;
    public Object toDate;
    public int count;
    public Object nationalId;
    public String scheme;
    public boolean schemeActive;
    public boolean schemeExpired;
    public boolean hasSpouse;
    public String relation;
    public int principleId;
    public Object principle;
    public int voucherId;
    public int age;
    public int familyMemId;
    public Object famMemberNo;
    public Object famMemFirstName;
    public Object famMemLastName;
    public Object famMemFullName;
    public Object famDob;
    public Object famGender;
    public Object famMemNationalId;
    public int relationId;
    public Object relationDesc;
    public Object familyMemPic;
    public int familyMemBioId;
    public int tempFamilyMemId;
    public boolean editMode;
    public Object iconStyle;
    public boolean cancelled;
    public Object famPic;
    public boolean isNew;
    public int idNumber;
    public Object productName;
    public Object orgName;
    public Object famMemPhoneNumber;
    public Object verificationType;
    public String payer;
    public double copay;
    public Object disabled;
    public double buffer;
    public Object deviceID;
    public Object sessionName;
    public Object hospitalName;
    public Object expireTime;
    public Object activationTime;
    public String policyStartDate;
    public String policyEndDate;
    public int schemeId;
    public Object salutation;
    public Object familyCode;
    public int policyId;
    public Object expiryTime;
    public Object search_results;
    public Object guardian;
    public String memberPic;
    public Object other_name;
    public Object first_name;
    public Object id_number;
    public Object date_of_birth;
    public Object citizenship;
    public String insuranceCode;
    public String schemeCode;
    public String outpatientStatus;
    public Object surname;

    public String getMemberId() {
        return memberId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public Object getMerchantId() {
        return merchantId;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public Object getFirstName() {
        return firstName;
    }

    public Object getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public Object getDeviceId() {
        return deviceId;
    }

    public int getRespCode() {
        return respCode;
    }

    public Object getBioId() {
        return bioId;
    }

    public Object getFileName() {
        return fileName;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public Object getMemberIdList() {
        return memberIdList;
    }

    public List<Voucher> getVouchers() {
        return vouchers;
    }

    public List<Object> getMemberServices() {
        return memberServices;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public String getMemberType() {
        return memberType;
    }

    public String getLctCode() {
        return lctCode;
    }

    public Object getTitle() {
        return title;
    }

    public Object getOtherName() {
        return otherName;
    }

    public Object getIdPassPortNo() {
        return idPassPortNo;
    }

    public String getGender() {
        return gender;
    }

    public Object getMaritalStatus() {
        return maritalStatus;
    }

    public Object getWeight() {
        return weight;
    }

    public Object getHeight() {
        return height;
    }

    public String getNhifNo() {
        return nhifNo;
    }

    public Object getEmployerName() {
        return employerName;
    }

    public Object getOccupation() {
        return occupation;
    }

    public Object getNationality() {
        return nationality;
    }

    public Object getPostalAdd() {
        return postalAdd;
    }

    public Object getPhysicalAdd() {
        return physicalAdd;
    }

    public Object getHomeTelephone() {
        return homeTelephone;
    }

    public Object getOfficeTelephone() {
        return officeTelephone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public Object getEmail() {
        return email;
    }

    public Object getCardStatus() {
        return cardStatus;
    }

    public Object getUploadStatus() {
        return uploadStatus;
    }

    public int getAccType() {
        return accType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Object getSerialNumber() {
        return serialNumber;
    }

    public Object getExpiryDate() {
        return expiryDate;
    }

    public Object getStatus() {
        return status;
    }

    public List<Programme> getProgrammes() {
        return programmes;
    }

    public Object getBinRange() {
        return binRange;
    }

    public int getProgrammeId() {
        return programmeId;
    }

    public String getProgrammeDesc() {
        return programmeDesc;
    }

    public Object getImageList() {
        return imageList;
    }

    public Object getBioImages() {
        return bioImages;
    }

    public Object getBiometrics() {
        return biometrics;
    }

    public Object getBioImage() {
        return bioImage;
    }

    public double getCardBalance() {
        return cardBalance;
    }

    public Object getCardSerialNo() {
        return cardSerialNo;
    }

    public Object getCardPin() {
        return cardPin;
    }

    public int getFamilySize() {
        return familySize;
    }

    public int getBnfGrpId() {
        return bnfGrpId;
    }

    public Object getBnfStatus() {
        return bnfStatus;
    }

    public Object getVerifyStatus() {
        return verifyStatus;
    }

    public Object getApproveStatus() {
        return approveStatus;
    }

    public Object getBioStatus() {
        return bioStatus;
    }

    public boolean isPrintStatus() {
        return printStatus;
    }

    public boolean isRightPrint() {
        return rightPrint;
    }

    public boolean isLeftPrint() {
        return leftPrint;
    }

    public Object getMemType() {
        return memType;
    }

    public List<FamilyMemList> getFamilyMemList() {
        return familyMemList;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public Object getSerialNo() {
        return serialNo;
    }

    public Object getVerifySelection() {
        return verifySelection;
    }

    public Object getFamVerifySelection() {
        return famVerifySelection;
    }

    public Object getUserName() {
        return userName;
    }

    public Object getUserEmail() {
        return userEmail;
    }

    public int getTopupCount() {
        return topupCount;
    }

    public Object getBnfGrpName() {
        return bnfGrpName;
    }

    public int getProductId() {
        return productId;
    }

    public boolean isActive() {
        return active;
    }

    public int getAccNo() {
        return accNo;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public int getAccId() {
        return accId;
    }

    public double getAccBalance() {
        return accBalance;
    }

    public int getOrgId() {
        return orgId;
    }

    public Object getMemberProgrammes() {
        return memberProgrammes;
    }

    public Object getFromDate() {
        return fromDate;
    }

    public Object getToDate() {
        return toDate;
    }

    public int getCount() {
        return count;
    }

    public Object getNationalId() {
        return nationalId;
    }

    public String getScheme() {
        return scheme;
    }

    public boolean isSchemeActive() {
        return schemeActive;
    }

    public boolean isSchemeExpired() {
        return schemeExpired;
    }

    public boolean isHasSpouse() {
        return hasSpouse;
    }

    public String getRelation() {
        return relation;
    }

    public int getPrincipleId() {
        return principleId;
    }

    public Object getPrinciple() {
        return principle;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public int getAge() {
        return age;
    }

    public int getFamilyMemId() {
        return familyMemId;
    }

    public Object getFamMemberNo() {
        return famMemberNo;
    }

    public Object getFamMemFirstName() {
        return famMemFirstName;
    }

    public Object getFamMemLastName() {
        return famMemLastName;
    }

    public Object getFamMemFullName() {
        return famMemFullName;
    }

    public Object getFamDob() {
        return famDob;
    }

    public Object getFamGender() {
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

    public Object getFamPic() {
        return famPic;
    }

    public boolean isNew() {
        return isNew;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public Object getProductName() {
        return productName;
    }

    public Object getOrgName() {
        return orgName;
    }

    public Object getFamMemPhoneNumber() {
        return famMemPhoneNumber;
    }

    public Object getVerificationType() {
        return verificationType;
    }

    public String getPayer() {
        return payer;
    }

    public double getCopay() {
        return copay;
    }

    public Object getDisabled() {
        return disabled;
    }

    public double getBuffer() {
        return buffer;
    }

    public Object getDeviceID() {
        return deviceID;
    }

    public Object getSessionName() {
        return sessionName;
    }

    public Object getHospitalName() {
        return hospitalName;
    }

    public Object getExpireTime() {
        return expireTime;
    }

    public Object getActivationTime() {
        return activationTime;
    }

    public String getPolicyStartDate() {
        return policyStartDate;
    }

    public String getPolicyEndDate() {
        return policyEndDate;
    }

    public int getSchemeId() {
        return schemeId;
    }

    public Object getSalutation() {
        return salutation;
    }

    public Object getFamilyCode() {
        return familyCode;
    }

    public int getPolicyId() {
        return policyId;
    }

    public Object getExpiryTime() {
        return expiryTime;
    }

    public Object getSearch_results() {
        return search_results;
    }

    public Object getGuardian() {
        return guardian;
    }

    public String getMemberPic() {
        return memberPic;
    }

    public Object getOther_name() {
        return other_name;
    }

    public Object getFirst_name() {
        return first_name;
    }

    public Object getId_number() {
        return id_number;
    }

    public Object getDate_of_birth() {
        return date_of_birth;
    }

    public Object getCitizenship() {
        return citizenship;
    }

    public String getInsuranceCode() {
        return insuranceCode;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public String getOutpatientStatus() {
        return outpatientStatus;
    }

    public Object getSurname() {
        return surname;
    }
}
