package com.lctapp.lct.Classes.Api

import com.google.gson.JsonObject
import com.lctapp.lct.Classes.Models.HospitalData
import com.lctapp.lct.Classes.Models.Member.MemberDetails
import com.lctapp.lct.Classes.Models.MemberBalances.MemberBalances
import com.lctapp.lct.Classes.Models.MemberClaims.MemberClaims
import com.lctapp.lct.Classes.Models.Payloads.*
import com.lctapp.lct.Classes.Models.Responses.GeneralResponse
import com.lctapp.lct.Classes.Models.Responses.LoginResp
import com.lctapp.lct.Classes.Models.Responses.RegistrationResp
import com.lctapp.lct.Classes.Models.Responses.TransactionResp
import retrofit2.Call
import retrofit2.http.*


interface HospitalsAPi {
    @get:GET("/compas/rest/merchant/gtMerchants")
    val hospitals: Call<List<HospitalData>>

    @POST("/compas/rest/user/signIn")
    fun validate_login(@Body req: Login?): Call<LoginResp>

    @POST("/compas/rest/user/changeMobilePassword")
    fun change_password(@Body req: ChangePassword?): Call<LoginResp>

    @POST("/compas/rest/user/forgotPassword")
    fun forgot_password(@Body req: Member?): Call<LoginResp>

    @POST("/compas/rest/user/signUp")
    fun register_member(@Body req: Registration?): Call<RegistrationResp>

    @PUT("/compas/rest/user/verifyEmail")
    fun validate_otp(@Body req: ValidateOTP?): Call<LoginResp>

    @POST("/compas/rest/user/validateUserMobileAppLocation")
    fun validate_user_location(@Body req: UserLocationData?): Call<GeneralResponse>

    @POST("/compas/rest/user/insertVerifiedMember")
    fun start_visit(@Body req: StartVisitData?): Call<GeneralResponse>

    @POST("/compas/rest/user/downloadMember")
    fun get_member_details(@Body req: Member?): Call<MemberDetails>

    @POST("/compas/rest/transaction/gtMemberTxnDetail")
    fun get_member_transactions(@Body req: Transaction?): Call<List<TransactionResp>>

    @POST("/compas/rest/transaction/gtAllTxnDetailMobileApp")
    fun get_family_transactions(@Body req: Transaction?): Call<List<TransactionResp>>

    @GET("/compas/rest/member/gtEclaimMembers")
    fun get_member_claims(@Query("memberNo") memberNo:String): Call<MemberClaims>

    @POST("/compas/rest/member/gtBalancesMobileApp")
    fun get_member_balances(@Body req: Member?): Call<MemberBalances>

}
