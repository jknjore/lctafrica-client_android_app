package com.lctapp.lct.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.lctapp.lct.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var sharedpreferences: SharedPreferences? = null
    val MyPREFERENCES = "MyPrefs"
    var identification = 0
    private var Id:String = ""
    private var idText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE)

        idText = findViewById<EditText>(R.id.idNumber).toString()

        val nxt = findViewById<Button>(R.id.nxt)

        nxt.setOnClickListener {
            click()
        }
    }



    private fun click() {

        Id = idNumber!!.text.toString()

        if (Id.isEmpty()) {
            validate()
            Log.e("###Empty>>>>", Id)
            Toast.makeText(
                this@MainActivity ,
                "Enter Your ID Number!" ,
                Toast.LENGTH_LONG
            ).show()

        }else {

            val editor = sharedpreferences!!.edit()
            identification = 1
            editor.putInt("identified", identification)
            editor.putString("Id", Id)
            editor.apply()

            startActivity(Intent(this@MainActivity, MemberRegistration::class.java))
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            finish()
        }
    }

    private fun validate(): Boolean {
        var valid = true
        Id = idNumber.text.toString()

        if (Id.isEmpty()) {
            idNumber.error = "enter your Id number"
        }else{
            idNumber.error = null
            valid =true
        }
        return valid
    }
}