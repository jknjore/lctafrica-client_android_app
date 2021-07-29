package com.lctapp.lct.Activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.HttpException
import com.lctapp.lct.Classes.Api.HospitalsAPi
import com.lctapp.lct.Classes.Constants.APIClient
import com.lctapp.lct.Classes.Helpers.Loader
import com.lctapp.lct.Classes.Models.Payloads.Registration
import com.lctapp.lct.Classes.Models.Payloads.ValidateOTP
import com.lctapp.lct.Classes.Models.Responses.LoginResp
import com.lctapp.lct.Classes.utills.toast
import com.lctapp.lct.R
import kotlinx.android.synthetic.main.activity_one_time_password.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.jvm.Throws

class OneTimePassword : AppCompatActivity() {
    var OtpURL: String =
        //"http://34.123.154.111:8085/compas/rest/user/verifyEmail"
        //"https://staging.lctafrica.io/compas/rest/user/verifyEmail"
         //"https://lctafrica.io/compas/rest/user/verifyEmail"
      "http://35.241.171.182:8085/compas/rest/user/verifyEmail"

    val MyPREFERENCES = "MyPrefs"
    var sharedpreferences: SharedPreferences? = null
    var l: Loader = Loader
    private var apiC: HospitalsAPi? = null


    private val OtpTask = 1

    var phn:String=""
    var otp:String=""

    private var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_time_password)
        apiC = APIClient.client?.create(HospitalsAPi::class.java)
        // instantiate
        sharedpreferences = getSharedPreferences(MyPREFERENCES , Context.MODE_PRIVATE)


        val img = findViewById<ImageView>(R.id.imageView)
        img.setOnClickListener {
        }

        val verify = findViewById<Button>(R.id.verify)

        verify.setOnClickListener {
            verify()
        }


    }
    @Throws(IOException::class , JSONException::class)

    private fun verify(){
        phn = sharedpreferences?.getString("phn","null").toString()

        Log.e("Phone number>>>" , phn)
        otp = txtOTP!!.text.toString()

        Log.e("OTP>>>",otp)

        if (phn.isEmpty() || otp.isEmpty()) {
            validate()
            Log.e("###here1","haha")

        }

        else {
            validate_otp(phn,otp)
//            showSimpleProgressDialog(this@OneTimePassword , null , "Checking...." , false)
//
//            Log.e("###here2","haha")
//            val det =
//                "{\"mobileNumber\":$phn,\"otp\":$otp}"// the pay load
//            val details = JSONObject(det)
//            println(details)
//            try {
//                Fuel.put(
//                    OtpURL
//                ).jsonBody(details.toString()).responseJson { request , response , result ->
//                    Log.d("response: " , result.get().content)
//                    onTaskCompleted(result.get().content , OtpTask)
//                }
//            } catch (e: HttpException) {
//                toast( "Please Make Sure You Are Connected To The Internet!!!")
//
//            } finally {
//
//            }
        }
    }


    private fun validate_otp(phoneNumber:String,otp:String)
    {
        l.showprogress(this@OneTimePassword,"please wait......")

        try {
            val payload_details = ValidateOTP(phoneNumber,otp)
            val call: Call<LoginResp> = apiC!!.validate_otp(payload_details)
            call.enqueue(object : Callback<LoginResp> {
                override fun onResponse(
                    call: Call<LoginResp>,
                    response: Response<LoginResp>
                ) {
                    l.dismissprogress()
                    if (!response.isSuccessful) {
                        var errorBodyString = ""
                        try {
                            errorBodyString = response.errorBody()!!.string()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        Loader.toastError(applicationContext, errorBodyString)
                        l.dismissprogress()
                        return
                    }
                    val resp: LoginResp? = response.body()
                    if(resp?.respCode == 200)
                    {
                        Toast.makeText(
                            this@OneTimePassword ,
                            "verification Successful!" ,
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@OneTimePassword , LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                    else
                    {
                        Loader.toast(this@OneTimePassword, "OTP verification was not successful.")
                    }

                }

                override fun onFailure(call: Call<LoginResp>, t: Throwable) {
                    Loader.toastError(this@OneTimePassword, t.message!!)
                    l.dismissprogress()
                }
            })
        } catch (e: Exception) {
            Loader.toast(this@OneTimePassword, "Error validating otp")
            l.dismissprogress()
        }
    }

    private fun onTaskCompleted(response: String , task: Int) {
        Log.d("responsejson" , response)
        removeSimpleProgressDialog()  //will remove progress dialog
        when (task) {
            OtpTask -> if (isSuccess(response)) {

                Toast.makeText(
                    this@OneTimePassword ,
                    "verification Successful!" ,
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this@OneTimePassword , LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                this.finish()

            } else if(notSuccesful(response)) {
                Toast.makeText(
                    this@OneTimePassword ,
                    "Invalid OTP!" ,
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(this@OneTimePassword , OneTimePassword::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                this.finish()

            }else {

                Toast.makeText(
                    this@OneTimePassword ,
                    getErrorMessage(response) ,
                    Toast.LENGTH_SHORT

                ).show()
            }
        }
    }

    fun isSuccess(response: String): Boolean {
        try {
            val jsonObject = JSONObject(response)
            return jsonObject.optString("respCode") == "200"

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return false
    }

    fun notSuccesful(response: String):Boolean{
        try {
            val jsonObject = JSONObject(response)
            return jsonObject.optString("respCode") == "201"

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return true
    }

    fun getErrorMessage(response: String): String {
        try {
            val jsonObject = JSONObject(response)
            return jsonObject.getString("message")

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return "Mobile Number Does Not Exist, Please Sign Up"
    }

    fun showSimpleProgressDialog(
        context: Context ,
        title: String? ,
        msg: String ,
        isCancelable: Boolean
    ) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context , title , msg)
                mProgressDialog!!.setCancelable(isCancelable)
            }
            if (!mProgressDialog!!.isShowing) {
                mProgressDialog!!.show()
            }

        } catch (ie: IllegalArgumentException) {
            ie.printStackTrace()
        } catch (re: RuntimeException) {
            re.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog!!.isShowing) {
                    mProgressDialog!!.dismiss()
                    mProgressDialog = null
                }
            }
        } catch (ie: IllegalArgumentException) {
            ie.printStackTrace()

        } catch (re: RuntimeException) {
            re.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    private fun validate(): Boolean {

        var valid = true

        phn = mobile.text.toString()
        otp = txtOTP.text.toString()

        if (phn.isEmpty()) {
            mobile.error = "enter your registered phone number"
        } else {
            mobile.error = null
            valid= true
        }
        if (otp.isEmpty()) {
            txtOTP.error = "Enter Password"

        } else {
            txtOTP.error = null
            valid=true
        }
        return valid
    }
}