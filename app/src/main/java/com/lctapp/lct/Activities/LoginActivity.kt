package com.lctapp.lct.Activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.lctapp.lct.Classes.Api.HospitalsAPi
import com.lctapp.lct.Classes.ConnectionDetector
import com.lctapp.lct.Classes.Constants.APIClient
import com.lctapp.lct.Classes.Helpers.AppConstants
import com.lctapp.lct.Classes.Helpers.Loader
import com.lctapp.lct.Classes.Helpers.Saver
import com.lctapp.lct.Classes.Models.Payloads.Login
import com.lctapp.lct.Classes.Models.Responses.LoginResp
import com.lctapp.lct.R
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


@RequiresApi(Build.VERSION_CODES.M)
class LoginActivity : AppCompatActivity() {


    var loginURL: String =
        //"http://34.123.154.111:8085/compas/rest/user/signIn"
       // "https://staging.lctafrica.io/compas/rest/user/signIn"
        "http://35.241.171.182:8085/compas/rest/user/signIn"
       // "https://lctafrica.io/compas/rest/user/signIn"

    // This captures logged in user to save it locally
    val MyPREFERENCES = "MyPrefs"
    var sharedpreferences: SharedPreferences? = null

    lateinit var cd: ConnectionDetector
   // lateinit var mSnackbar: Snackbar
    private val LoginTask = 0
    private  var SignupTask = 0
    private var apiC: HospitalsAPi? = null
    var loggedIn = 0
    var firstTimeLogin =0
    var l: Loader = Loader
    var s: Saver = Saver

    private var mProgressDialog: ProgressDialog? = null

    var images: String= ""
    var user: String = ""
    var pass: String = ""
    var member:String =""
    var username:String=""
    var password:String=""

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setTitle("LCT - Login")
        supportActionBar?.hide()
        // instantiate
        sharedpreferences = getSharedPreferences(MyPREFERENCES , Context.MODE_PRIVATE)
        SignupTask = sharedpreferences?.getInt("signed",0)!!
        images = sharedpreferences?.getString("image","").toString()
        Log.e("###Images",images.toString())

        apiC = APIClient.client?.create(HospitalsAPi::class.java)
        cd = ConnectionDetector()
        cd.isConnectingToInternet(this@LoginActivity)

        val button = findViewById(R.id.btnLink) as TextView
        button.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }


        btnlogIn.setOnClickListener {

            images = sharedpreferences?.getString("image","").toString()
            Log.e("###Imagess",images.toString())

//            if (cd.isConnectingToInternet(this@LoginActivity)) {
//
//                Toast.makeText(applicationContext,
//                    "connected", Toast.LENGTH_LONG)
//            } else {
//                startActivity(Intent(this@LoginActivity, LoginActivity::class.java)) //see this part of launching the activity
//                finish()
//                Toast.makeText(applicationContext,
//                    "make sure you are connected to the internet", Toast.LENGTH_LONG).show()
//            }

            login()
        }
        val btn = findViewById<TextView>(R.id.btnReset)
        btn.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgotPassword::class.java)
            startActivity(intent)
        }


    }

    @Throws(IOException::class , JSONException::class)

    private fun login() {

        user = etEnterName!!.text.toString()
        pass = etPassword!!.text.toString()

        if (user.isEmpty() || pass.isEmpty()) {
            validate()
            Log.e("###here1","haha")
      }

        else {
            login_user(user,pass)
//            showSimpleProgressDialog(this@LoginActivity , null , "Loading..." , false)
//
//            Log.e("###here2","haha")
//            val det =
//                "{\"memberNo\":$user,\"password\":$pass}"// the pay load
//            val details = JSONObject(det)
//            println(details)
//            try {
//                Fuel.post(
//                    loginURL
//                ).jsonBody(details.toString()).responseJson { request , response , result ->
//                    Log.d("response: " , result.get().content)
//                    onTaskCompleted(result.get().content , LoginTask)
//                }
//            } catch (e: Exception) {
//
//            } finally {
//
//            }
        }
    }


    private fun login_user(username:String, password:String)
    {
        l.showprogress(this@LoginActivity,"please wait......")

        try {
            val login_details = Login(username,password)
            val call: Call<LoginResp> = apiC!!.validate_login(login_details)
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
                        // use this line to run an instance of sharedprefs, used to save stuff in.
                        member=resp.getMemberNo()
                        val editor = sharedpreferences!!.edit()
                        loggedIn = 1
                        editor.putInt("logged" , loggedIn)
                        editor.putString("user" , user)
                        editor.putString("member", member)
                        editor.putString("pass",pass)
                        editor.putInt("firstTimeLogin",0)
                        editor.apply()

                        s.savedata(applicationContext,AppConstants.USERNAME,username)

                        val intent = Intent(this@LoginActivity , HomepageActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                    else
                    {
                        Loader.toast(this@LoginActivity, "Kindly check your username/password")
                    }

                }

                override fun onFailure(call: Call<LoginResp>, t: Throwable) {
                    Loader.toastError(this@LoginActivity, t.message!!)
                    l.dismissprogress()
                }
            })
        } catch (e: Exception) {
            Loader.toast(this@LoginActivity, "Error loggin in")
            l.dismissprogress()
        }
    }



    private fun onTaskCompleted(response: String , task: Int) {
        Log.d("responsejson" , response)
        removeSimpleProgressDialog()  //will remove progress dialog
        when (task) {
            LoginTask -> if (isSuccess(response)) {

                JSONObject(response)

                Toast.makeText(
                    this@LoginActivity ,
                    "LoginResp Successful!" ,
                    Toast.LENGTH_SHORT
                ).show()

                // use this line to run an instance of sharedprefs, used to save stuff in.
                val editor = sharedpreferences!!.edit()
                loggedIn = 1
                editor.putInt("logged" , loggedIn)
                editor.putString("user" , user)
                editor.putString("member", member)
                editor.putString("pass",pass)
                editor.putInt("firstTimeLogin",0)
                editor.apply()

                val intent = Intent(this@LoginActivity , HomepageActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                this.finish()

            } else if(notSuccessful(response)) {
                Toast.makeText(
                    this@LoginActivity ,
                    "Enter valid username and password!" ,
                    Toast.LENGTH_LONG
                ).show()

            }else {

                val editor = sharedpreferences!!.edit()
                editor.putInt("logged" , loggedIn)
                editor.apply()
                Toast.makeText(
                    this@LoginActivity ,
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

    private fun notSuccessful(response: String):Boolean{
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
        var valid = true
        user = etEnterName.text.toString()
        pass = etPassword.text.toString()

        if (user.isEmpty()) {
            etEnterName.error = "enter a valid Member Number"
        } else {
            etEnterName.error = null
            valid = true
        }
        if (pass.isEmpty()) {
            etPassword.error = "Enter Password"
        } else {
            etPassword.error = null
            valid = true
        }
        return valid
    }
}