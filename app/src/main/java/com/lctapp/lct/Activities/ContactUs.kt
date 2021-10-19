package com.lctapp.lct.Activities

import android.R.id
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hbb20.CountryCodePicker
import com.lctapp.lct.R
import java.net.URLEncoder
import android.R.id.button2
import android.widget.*
import com.lctapp.lct.Classes.Helpers.AppConstants


class ContactUs : AppCompatActivity() {
    lateinit var  mobile:LinearLayout
    lateinit var  email:LinearLayout
    lateinit var  website:LinearLayout
    lateinit var  maps:LinearLayout

    lateinit var  mobiletext:TextView
    lateinit var  emailtext:TextView
    lateinit var  websitetext:TextView
    lateinit var  mapstext:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
        getActionBar()?.setDisplayHomeAsUpEnabled(true);
        setTitle("Contact Us")

        mobile=findViewById(R.id.mobile)
        email=findViewById(R.id.email)
        website=findViewById(R.id.website)
        maps=findViewById(R.id.maps)

        mobiletext=findViewById(R.id.mobiletext)
        emailtext=findViewById(R.id.emailtext)
        websitetext=findViewById(R.id.websitetext)
        mapstext=findViewById(R.id.mapstext)

        mobiletext.text=AppConstants.LCT_MOBILE
        emailtext.text=AppConstants.LCT_EMAIL
        websitetext.text=AppConstants.LCT_WEBSITE
        mapstext.text=AppConstants.LCT_MAPS_TEXT


        mobile.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:"+AppConstants.LCT_MOBILE)
            startActivity(intent)
        })

        email.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(AppConstants.LCT_EMAIL))
            intent.putExtra(Intent.EXTRA_SUBJECT, "")

            startActivity(Intent.createChooser(intent, "Email via..."))
        })

        website.setOnClickListener(View.OnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(AppConstants.LCT_WEBSITE)
            startActivity(i)
        })

        maps.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(AppConstants.LCT_MAPS_ADDRESS)
            )
            startActivity(intent)
        })

    }
}