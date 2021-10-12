package com.lctapp.lct.Activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.hbb20.CountryCodePicker
import com.lctapp.lct.R
import java.net.URLEncoder

class ContactUs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
        getActionBar()?.setDisplayHomeAsUpEnabled(true);
        setTitle("Contact Us")
    }
}