package com.lctapp.lct.Activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lctapp.lct.Classes.ConnectionDetector
import com.lctapp.lct.Classes.utills.toast
import com.lctapp.lct.R
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import android.widget.EditText
import com.lctapp.lct.Classes.Api.HospitalsAPi
import com.lctapp.lct.Classes.Constants.APIClient
import com.lctapp.lct.Classes.Helpers.General
import com.lctapp.lct.Classes.Helpers.Loader
import com.lctapp.lct.Classes.Models.Payloads.Registration
import com.lctapp.lct.Classes.Models.Responses.RegistrationResp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignupActivity : AppCompatActivity() {
    var signupURL: String =
        //"http://34.123.154.111:8085/compas/rest/user/signUp"
       // "https://staging.lctafrica.io/compas/rest/user/signUp"
      "http://35.241.171.182:8085/compas/rest/user/signUp"
        //"https://lctafrica.io/compas/rest/user/signUp"

    val MyPREFERENCES = "MyPrefs"
    var sharedpreferences: SharedPreferences? = null

    var l: Loader = Loader
    private var apiC: HospitalsAPi? = null

    var user: String = ""
    private var userIMEI: String = ""
    private var phn: String =""
    private var pass: String =""
    private var pass2: String =""
    lateinit var cd: ConnectionDetector
    private var SignupTask = 0
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val confirmPassword = findViewById<EditText>(R.id.confirmPassword)
        apiC = APIClient.client?.create(HospitalsAPi::class.java)

        val button = findViewById<TextView>(R.id.btnLink)
        button.setOnClickListener {
            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        // instantiate
        sharedpreferences = getSharedPreferences(MyPREFERENCES , Context.MODE_PRIVATE)

        cd = ConnectionDetector()
        cd.isConnectingToInternet(this@SignupActivity)

        btnSignUp.setOnClickListener {

//            if (cd.isConnectingToInternet(this@SignupActivity)) {
//                //startActivity(Intent(this@SignupActivity,LoginActivity::class.java))
//                Toast.makeText(applicationContext,
//                    "connected", Toast.LENGTH_LONG)
//            } else {
//                startActivity(Intent(this@SignupActivity, SignupActivity::class.java)) //see this part of launching the activity
//                finish()
//                Toast.makeText(applicationContext,
//                    "make sure you are connected to the internet", Toast.LENGTH_LONG).show()
//            }

            userIMEI = General.getDeviceIMEI(this).toString()

            if(userIMEI.isEmpty())
            {
                return@setOnClickListener
            }
            else
            {
                //toast("sending imei $userIMEI")
            }
            signUp()
        }

        //actionbar
//        val actionbar = this.supportActionBar
        //set actionbar title
//        actionbar!!.title = "Sign Up"
//        //set back button
//        actionbar.setDisplayHomeAsUpEnabled(true)
//        actionbar.setDisplayHomeAsUpEnabled(true)
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @Throws(IOException::class , JSONException::class)
    private fun signUp() {

        pass = Password!!.text.toString()
        pass2 = confirmPassword!!.text.toString()
        phn = phone!!.text.toString()
        user = memb!!.text.toString()

        if (!pass.equals(pass2)){

            toast("Please Ensure that You have entered similar Passwords")
            return
        }

        if ( pass.isEmpty() || phn.isEmpty() || user.isEmpty()) {
            validate()
            Log.e("###here1" , "haha")

        } else {
            register_member(user,phn,pass)
//            showSimpleProgressDialog(this@SignupActivity , null , "Registering...." , false)
//
//            Log.e("###here2","haha")
//
//            val det = "{\"password\":$pass,\"phoneNumber\":$phn,\"memberNo\":$user}" // the pay load
//            val details = JSONObject(det)
//            println(details)
//            Log.e("###", det)
//
//            try {
//                Fuel.post(
//                    signupURL
//                ).jsonBody(details.toString()).responseJson { request , response , result ->
//                    Log.d("response: " , result.get().content)
//                    onTaskCompleted(result.get().content, SignupTask)
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



    private fun register_member(memberNo:String,phoneNumber:String,password:String)
    {
        l.showprogress(this@SignupActivity,"please wait......")

        try {
            val payload_details = Registration(memberNo,password,phoneNumber)
            val call: Call<RegistrationResp> = apiC!!.register_member(payload_details)
            call.enqueue(object : Callback<RegistrationResp> {
                override fun onResponse(
                    call: Call<RegistrationResp>,
                    response: Response<RegistrationResp>
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
                    val resp: RegistrationResp? = response.body()
                    if(resp?.respCode == 200)
                    {
                        Toast.makeText(
                            this@SignupActivity ,
                            "Sign up Successful!" ,
                            Toast.LENGTH_LONG
                        ).show()

                        // use this line to run an instance of sharedprefs, used to save stuff in.
                        val editor = sharedpreferences!!.edit()
                        SignupTask = 1
                        editor.putInt("signed" , SignupTask)
                        editor.putString("phn" , phn)
                        editor.putString("pass",pass)
                        editor.putString("user",user)
                        editor.apply()

                        val intent = Intent(this@SignupActivity , OneTimePassword::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                    else
                    {
                        Loader.toast(this@SignupActivity, "Registration was not successful. Kindly contact LCT if issue persists")
                    }

                }

                override fun onFailure(call: Call<RegistrationResp>, t: Throwable) {
                    Loader.toastError(this@SignupActivity, t.message!!)
                    l.dismissprogress()
                }
            })
        } catch (e: Exception) {
            Loader.toast(this@SignupActivity, "Error registering your details")
            l.dismissprogress()
        }
    }


    private fun onTaskCompleted(response: String , task: Int) {
        Log.d("responsejson" , response)
        removeSimpleProgressDialog()  //will remove progress dialog
        when (task) {
            SignupTask -> if (isSuccess(response)) {

                Toast.makeText(
                    this@SignupActivity ,
                    "Sign up Successful!" ,
                    Toast.LENGTH_LONG
                ).show()

                // use this line to run an instance of sharedprefs, used to save stuff in.
                val editor = sharedpreferences!!.edit()
                SignupTask = 1
                editor.putInt("signed" , SignupTask)
                editor.putString("phn" , phn)
                editor.putString("pass",pass)
                editor.putString("user",user)
                editor.apply()

                val intent = Intent(this@SignupActivity , OneTimePassword::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                this.finish()

            }
            else if (notSuccess(response)) {
                val editor = sharedpreferences!!.edit()
                editor.putInt("signed" , SignupTask)
                editor.apply()

                Toast.makeText(this@SignupActivity , "User Already Exists" , Toast.LENGTH_LONG)
                    .show()
                Log.d("responsejson" , response)

            } else {
                Toast.makeText(
                    this@SignupActivity ,
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

    private fun notSuccess(response: String): Boolean {
        try {
            val jsonObject = JSONObject(response)
            return jsonObject.optString("respCode") == "201"

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return false
    }

    fun getErrorMessage(response: String): String {
        try {
            val jsonObject = JSONObject(response)
            return jsonObject.getString("message")

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return "you are not a registered Member Contact admin if your credentials were correct"
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
        val phn = phone.text.toString()
        val member = memb.text.toString()

        if (phn.isEmpty()) {
            phone.error = "enter your phone number"
        } else {
            phone.error = null
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
}