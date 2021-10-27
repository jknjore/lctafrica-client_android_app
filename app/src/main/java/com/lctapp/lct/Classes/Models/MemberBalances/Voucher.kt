package com.lctapp.lct.Classes.Models.MemberBalances

import java.io.Serializable

class Voucher: Serializable {
    var voucherBalance: String? = null
    var accNo: String? = null
    var voucherCode: String? = null
    var voucherName: String? = null
    var voucherId = 0
    var voucherValue: String? = null
    var usage = 0.0
    var schemeType: String? = null
    var services: Any? = null
    var used: String? = null
    var percentage: String? = null
}