package com.lctapp.lct.Classes.Models.MemberBalances

import java.io.Serializable

class MemberProgramme: Serializable {
    var programmeCode: String? = null
    var programmeId = 0
    var programmeName: String? = null
    var vouchers: List<Voucher>? = null
    var schemeId = 0
    var isActive = false
    var insuranceCode: Any? = null
    var schemeCode: Any? = null
}