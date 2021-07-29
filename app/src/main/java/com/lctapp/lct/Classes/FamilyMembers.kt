package com.lctapp.lct.Classes

import java.io.Serializable

class FamilyMembers : Serializable{
    var memberId = 0
    var familyMemId = 0
    var famMemberNo: String? = null
    var famMemFirstName: String? = null
    var famMemLastName: String? = null
    var famMemFullName: String? = null
    var famGender: String? = null
    var famMemNationalId: String? = null
    var relationId = 0
    var relationDesc: String? = null
    var familyMemPic: String? = null
    var familyMemBioId = 0
    var tempFamilyMemId = 0
    var isEditMode = false
    var iconStyle: String? = null
    var isCancelled = false
    var createdBy = 0
    var respCode = 0
    var famPic: String? = null
    var isChecked = false
    var isNew = false
    var verifyStatus: String? = null
    var approveStatus: String? = null
    var lctCode: String? = null
    var memberType: String? = null
    var idNumber: String? = null
    var isActive = false
    var disabled: String? = null
    var famMemPhoneNumber: String? = null
    var cellPhone: String? = null
}
