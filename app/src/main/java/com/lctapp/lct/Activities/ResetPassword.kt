package com.lctapp.lct.Activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.HttpException
import com.lctapp.lct.Classes.Api.HospitalsAPi
import com.lctapp.lct.Classes.Constants.APIClient
import com.lctapp.lct.Classes.Helpers.Loader
import com.lctapp.lct.Classes.Models.Payloads.ChangePassword
import com.lctapp.lct.Classes.Models.Payloads.Login
import com.lctapp.lct.Classes.Models.Responses.LoginResp
import com.lctapp.lct.Classes.utills.toast
import com.lctapp.lct.R
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.activity_signup.Password
import kotlinx.android.synthetic.main.activity_signup.memb
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.jvm.Throws

class ResetPassword : AppCompatActivity() {
    var setPasswordURL: String =
        //"http://34.123.154.111:8085/compas/rest/user/changeMobilePassword"
        //"https://staging.lctafrica.io/compas/rest/user/changeMobilePassword"
         "http://35.241.171.182:8085/compas/rest/user/changeMobilePassword"
       // "https://lctafrica.io/compas/rest/user/changeMobilePassword"

    val MyPREFERENCES = "MyPrefs"
    var sharedpreferences: SharedPreferences? = null

    var user: String = ""
    var otp: String =""
    var password: String =""

    var l: Loader = Loader
    private var apiC: HospitalsAPi? = null

    private var setPasswordTask = 0
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        apiC = APIClient.client?.create(HospitalsAPi::class.java)
        // instantiate
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE)


        btnSetPassword.setOnClickListener {
            SetPassword()
        }
    }

    @Throws(IOException::class , JSONException::class)
    private fun SetPassword() {

        user = sharedpreferences?.getString("user","null").toString()
        password = Password!!.text.toString()

        otp = oneTimePassword!!.text.toString()
        // user = memb!!.text.toString()

        if ( password.isEmpty() || otp.isEmpty() || user.isEmpty()) {
            validate()
            Log.e("###here1" , "haha")

        } else {
            //showSimpleProgressDialog(this@ResetPassword , null , "Registering...." , false)

//            Log.e("###here2","haha")
//
//            val det = "{\"password\":$password,\"otp\":$otp,\"memberNo\":$user}" // the pay load
//            val details = JSONObject(det)
//            println(details)
            val editor =sharedpreferences!!.edit()
            editor.putString("password",password)
            editor.apply()

            change_password(user,password,otp)
//            try {
//                Fuel.post(
//                    setPasswordURL
//                ).jsonBody(details.toString()).responseJson { request , response , result ->
//                    Log.d("response: " , result.get().content)
//                    onTaskCompleted(result.get().content, setPasswordTask)
//
//                }
//            } catch (e: HttpException) {
//                toast( "Please Make Sure You Are Connected To The Internet!!!")
//
//            } finally {
//
//            }
        }

    }




    private fun change_password(memberNo:String, password:String, otp:String)
    {
        l.showprogress(this@ResetPassword,"please wait......")

        try {
            val payload_details = ChangePassword(memberNo,password,otp)
            val call: Call<LoginResp> = apiC!!.change_password(payload_details)
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
                            this@ResetPassword ,
                            "Password Changed Successfully!" ,
                            Toast.LENGTH_LONG
                        ).show()

                        val intent = Intent(this@ResetPassword , LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                    else
                    {
                        Loader.toast(this@ResetPassword, resp?.getRespMessage())
                    }

                }

                override fun onFailure(call: Call<LoginResp>, t: Throwable) {
                    Loader.toastError(this@ResetPassword, t.message!!)
                    l.dismissprogress()
                }
            })
        } catch (e: Exception) {
            Loader.toast(this@ResetPassword, "Error changing password")
            l.dismissprogress()
        }
    }



    private fun onTaskCompleted(response: String , task: Int) {
        Log.d("responsejson" , response)
        removeSimpleProgressDialog()  //will remove progress dialog
        when (task) {
            setPasswordTask -> if (isSuccess(response)) {
                Toast.makeText(
                    this@ResetPassword ,
                    "Password Changed Successfully!" ,
                    Toast.LENGTH_LONG
                ).show()

                val intent = Intent(this@ResetPassword , LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                this.finish()

            }
            else if (notSucces(response)) {

                Toast.makeText(this@ResetPassword , "User does not exist please Sign up." , Toast.LENGTH_LONG)
                    .show()
                val intent = Intent(this@ResetPassword , ResetPassword::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                this.finish()
                Log.d("responsejson" , response)

            } else {
                Toast.makeText(
                    this@ResetPassword,
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

    fun notSucces(response: String): Boolean {
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

        return "This is not a registered Member, contact admin if your credentials were correct"
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

        val pass = Password.text.toString()
        val otp = oneTimePassword.text.toString()
        val member = memb.text.toString()

        if (otp.isEmpty()) {
            oneTimePassword.error = "enter your phone number"
        } else {
            oneTimePassword.error = null
            valid = true
        }
        if (member.isEmpty()) {
            memb.error = "Enter your member number"
        } else {
            memb.error = null
            valid =true
        }
        if (pass.isEmpty()) {
            Password.error = "Enter Password"
        } else {
            Password.error = null
            valid = true
        }
        return valid


    }

//    private fun userValidationFunc(user: String): Boolean {
//        return android.util.Patterns.EMAIL_ADDRESS.matcher(user).matches()
//    }
}