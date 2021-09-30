package com.lctapp.lct.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.loader.content.Loader
import com.lctapp.lct.Classes.Helpers.General
import com.lctapp.lct.Classes.Helpers.Loader.toast
import com.lctapp.lct.Classes.Models.ScanData
import com.lctapp.lct.R

class QRCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)

        getSupportActionBar()?.setTitle("QR CODE"); // for set actionbar title
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar

        val scandata:ScanData
        scandata = intent.extras!!.getSerializable("scandata") as ScanData

        toast(applicationContext,General.getDump(scandata))
    }
}