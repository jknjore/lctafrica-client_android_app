package com.lctapp.lct.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.lctapp.lct.Classes.ViewerAdapter
import com.lctapp.lct.R

class ServiceBalances : AppCompatActivity() {

    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

//    private var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_balances)
        setTitle("Service Balances")

        tabLayout = findViewById(R.id.tabLayout)

        viewPager = findViewById(R.id.viewPager)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("OUTPATIENT OVERALL"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("INPATIENT OVERALL"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("COMPLEMENTARY COVER"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = ViewerAdapter(this, supportFragmentManager, tabLayout!!.tabCount)
        viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

    }

}



