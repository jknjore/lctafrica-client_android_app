package com.lctapp.lct.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.fragment.app.Fragment
import com.lctapp.lct.R

class RegistrationActivity : AppCompatActivity() {
    lateinit var fowardbtn:Button
    lateinit var backbtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        fowardbtn = findViewById(R.id.foward)
        backbtn = findViewById(R.id.back)

        backbtn.setOnClickListener {
            val insurerFragment= InsurerFragment()
            loadFragment(insurerFragment)
        }

        fowardbtn.setOnClickListener {
            val detailsFragment = DetailsFragment()
            loadFragment(detailsFragment)
        }

    }
    private fun loadFragment(fragment: Fragment){
        val fragmentManager =supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentView,fragment)
        fragmentTransaction.commit()
    }
}