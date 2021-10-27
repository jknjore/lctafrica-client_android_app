package com.lctapp.lct.Activities

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import com.lctapp.lct.Classes.Api.HospitalsAPi
import com.lctapp.lct.Classes.Constants.APIClient
import com.lctapp.lct.Classes.Helpers.AppConstants
import com.lctapp.lct.Classes.Helpers.Loader
import com.lctapp.lct.Classes.Models.Payloads.Login
import com.lctapp.lct.Classes.Models.Responses.LoginResp
import com.lctapp.lct.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast

import android.text.Editable

import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.davidmiguel.numberkeyboard.NumberKeyboard
import com.davidmiguel.numberkeyboard.NumberKeyboardListener
import com.lctapp.lct.Classes.Helpers.General
import com.lctapp.lct.Classes.Helpers.Saver




class VerificationActivity : AppCompatActivity(), NumberKeyboardListener {

    private var apiC: HospitalsAPi? = null
    var l: Loader = Loader
    var s: Saver = Saver
    lateinit var pintext: TextView
    lateinit var principal_name: TextView
    lateinit var numberKeyboard: NumberKeyboard
    var pincode:String = ""
    var voucher_code:String = ""
    var voucher_name:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);

        voucher_name = intent.getStringExtra("voucher_name")!!
        voucher_code = intent.getStringExtra("voucher_code")!!

        setTitle(voucher_name)

        pintext=findViewById(R.id.pintext)
        principal_name=findViewById(R.id.principal_name)

        apiC = APIClient.client?.create(HospitalsAPi::class.java)

        principal_name.setText("Hi "+General.toCamelCase(s.getdata(this@VerificationActivity,AppConstants.PRINCIPAL_NAME).toString().split(" ")[0]))

        val numberKeyboard = findViewById<NumberKeyboard>(R.id.numberKeyboard)
        numberKeyboard.setListener(this)

        val btn = findViewById<TextView>(R.id.resetPIN)
        btn.setOnClickListener {
            val intent = Intent(this@VerificationActivity, ForgotPassword::class.java)
            startActivity(intent)
        }
    }


    private fun login_valid( password:String)
    {
        l.showprogress(this@VerificationActivity,"please wait......")

        try {
            val login_details = Login(s.getdata(this@VerificationActivity,AppConstants.USERNAME),password)
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
                        Loader.toastError(this@VerificationActivity, errorBodyString)
                        l.dismissprogress()
                        return
                    }
                    val resp: LoginResp? = response.body()
                    if(resp?.respCode == 200)
                    {
                        val intent = Intent(this@VerificationActivity , SchemeBenefitsActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else
                    {
                        Loader.toast(this@VerificationActivity, "Invalid PIN")
                    }

                }

                override fun onFailure(call: Call<LoginResp>, t: Throwable) {
                    Loader.toastError(this@VerificationActivity, t.message!!)
                    l.dismissprogress()
                }
            })
        } catch (e: Exception) {
            Loader.toast(this@VerificationActivity, "Error loggin in")
            l.dismissprogress()
        }
    }

    override fun onLeftAuxButtonClicked() {
    }

    override fun onNumberClicked(number: Int) {
        pincode += number;
        if(pincode.length == 4)
        {
            login_valid(pincode)
            pincode="";
        }
        update_text()
    }

    override fun onRightAuxButtonClicked() {
        if (pincode.isEmpty()) {
            return
        }
        if (pincode.length <= 1) {
            pincode = ""
        } else {
            pincode = pincode.substring(0, pincode.length - 1)
        }
        update_text()
    }

    private fun update_text()
    {
        var masked_password=""
        var x = 0
        while (x < pincode.length) {
            masked_password+="*"
            x++
        }
        pintext.setText(masked_password)
    }


}