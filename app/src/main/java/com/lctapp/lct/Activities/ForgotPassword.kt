package com.lctapp.lct.Activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lctapp.lct.Classes.Api.HospitalsAPi
import com.lctapp.lct.Classes.Constants.APIClient
import com.lctapp.lct.Classes.Helpers.Loader
import com.lctapp.lct.Classes.Models.Payloads.Member
import com.lctapp.lct.Classes.Models.Responses.LoginResp
import com.lctapp.lct.R
import kotlinx.android.synthetic.main.activity_forgot_password.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.jvm.Throws

class ForgotPassword : AppCompatActivity() {

    var verifyUserURL: String =
        //"http://34.123.154.111:8085/compas/rest/user/forgotPassword"
       // "https://staging.lctafrica.io/compas/rest/user/forgotPassword"
        "http://35.241.171.182:8085/compas/rest/user/forgotPassword"
        //"https://lctafrica.io/compas/rest/user/forgotPassword"

    val MyPREFERENCES = "MyPrefs"
    var sharedpreferences: SharedPreferences? = null
    private val verifyUserTask = 1
    var verified= 0
    private var mProgressDialog: ProgressDialog? = null
    var user: String = ""

    var l: Loader = Loader
    private var apiC: HospitalsAPi? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // instantiate
        sharedpreferences = getSharedPreferences(MyPREFERENCES , Context.MODE_PRIVATE)
        apiC = APIClient.client?.create(HospitalsAPi::class.java)

        val img = findViewById<ImageView>(R.id.imageView) as ImageView
        img.setOnClickListener {

        }

        btnReset.setOnClickListener {
            Reset()
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    @Throws(IOException::class , JSONException::class)

    private fun Reset(){

        user = member!!.text.toString()

        if (user.isEmpty()) {
            validate()
            Log.e("###here1" , "haha")

        }else {
            reset_password(user)
//            showSimpleProgressDialog(this@Member , null , "Loading..." , false)
//
//            Log.e("###here2","haha")
//            val det =
//                "{\"memberNo\":$user}"// the pay load
//            val details = JSONObject(det)
//            println(details)
//            try {
//                Fuel.post(
//                    verifyUserURL
//                ).jsonBody(details.toString()).responseJson { request , response , result ->
//                    Log.d("response: " , result.get().content)
//                    onTaskCompleted(result.get().content , verifyUserTask)
//                }
//            } catch (e: HttpException) {
//                toast("Please make sure you are connected to the internet")
//
//            } finally {
//
//            }
        }
    }



    private fun reset_password(memberNo:String)
    {
        l.showprogress(this@ForgotPassword,"please wait......")

        try {
            val payload_details = Member(memberNo)
            val call: Call<LoginResp> = apiC!!.forgot_password(payload_details)
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
                            this@ForgotPassword ,
                            "Successful!" ,
                            Toast.LENGTH_SHORT
                        ).show()

                        // use this line to run an instance of sharedprefs, used to save stuff in.
                        val editor = sharedpreferences!!.edit()
                        verified = 1
                        editor.putInt("verify" , verified)
                        editor.putString("user" , user)
                        editor.apply()

                        val intent = Intent(this@ForgotPassword , ResetPassword::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                    else
                    {
                        Loader.toast(this@ForgotPassword, "Please confirm that the details are valid.")
                    }

                }

                override fun onFailure(call: Call<LoginResp>, t: Throwable) {
                    Loader.toastError(this@ForgotPassword, t.message!!)
                    l.dismissprogress()
                }
            })
        } catch (e: Exception) {
            Loader.toast(this@ForgotPassword, "Error resetting password")
            l.dismissprogress()
        }
    }



    private fun onTaskCompleted(response: String , task: Int) {
        Log.d("responsejson" , response)
        removeSimpleProgressDialog()  //will remove progress dialog
        when (task) {
            verifyUserTask -> if (isSuccess(response)) {

                JSONObject(response)
                Toast.makeText(
                    this@ForgotPassword ,
                    "Successful!" ,
                    Toast.LENGTH_SHORT
                ).show()

                // use this line to run an instance of sharedprefs, used to save stuff in.
                val editor = sharedpreferences!!.edit()
                verified = 1
                editor.putInt("verify" , verified)
                editor.putString("user" , user)
                editor.apply()

                val intent = Intent(this@ForgotPassword , ResetPassword::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                this.finish()

            } else if(notSuccesful(response)) {

                val editor = sharedpreferences!!.edit()
                editor.putInt("verify" , verified)
                editor.apply()

                Toast.makeText(
                    this@ForgotPassword ,
                    "PLease Signup or Enter valid username " ,
                    Toast.LENGTH_LONG
                ).show()

            }else {
                Toast.makeText(
                    this@ForgotPassword ,
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

        return "User Does Not Exist, Please Sign Up"
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

        val valid: Boolean

        user = member.text.toString()


        if (user.isEmpty()) {
            member.error = "enter a valid Username"
            valid = false
        } else {
            member.error = null
            valid = true
        }
        return valid
    }

}


