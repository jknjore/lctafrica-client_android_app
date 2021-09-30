package com.lctapp.lct.Activities

import android.R.attr
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.loader.content.Loader
import com.lctapp.lct.Classes.Helpers.General
import com.lctapp.lct.Classes.Helpers.Loader.toast
import com.lctapp.lct.Classes.Models.ScanData
import com.lctapp.lct.R
import com.google.zxing.WriterException

import android.R.attr.bitmap
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView

import androidmads.library.qrgenearator.QRGContents

import androidmads.library.qrgenearator.QRGEncoder
import android.view.Display

import android.view.WindowManager
import android.util.DisplayMetrics
import com.fasterxml.jackson.databind.ObjectMapper
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class QRCodeActivity : AppCompatActivity() {
    private var bitmap: Bitmap? = null
    private val qrgEncoder: QRGEncoder? = null
    lateinit var mainHandler: Handler
    lateinit var scandata: ScanData
    lateinit var qr_image: ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)

        getSupportActionBar()?.setTitle("QR CODE"); // for set actionbar title
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar

        scandata = intent.extras!!.getSerializable("scandata") as ScanData
        qr_image=findViewById<ImageView>(R.id.qr_image)


        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val device_width = displayMetrics.widthPixels
        qr_image.layoutParams.width=device_width-20
        qr_image.layoutParams.height=device_width-20

        mainHandler = Handler(Looper.getMainLooper())


    }

    private val updateTextTask = object : Runnable {
        override fun run() {
            scandata.timestamp= SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( Date())
            generate_qr_code(scandata)
            mainHandler.postDelayed(this, 5000)
        }
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(updateTextTask)
    }

    override fun onResume() {
        super.onResume()
        mainHandler.post(updateTextTask)
    }


    private fun generate_qr_code(scandata:ScanData)
    {
        val mapper= ObjectMapper()
        val manager = getSystemService(WINDOW_SERVICE) as WindowManager
        val display = manager.defaultDisplay
        val point = Point()
        display.getSize(point)
        val width: Int = point.x
        val height: Int = point.y
        var smallerDimension = if (width < height) width else height
        smallerDimension = smallerDimension * 3 / 4

        val qrgEncoder = QRGEncoder(mapper.writeValueAsString(scandata), null, QRGContents.Type.TEXT, smallerDimension)
        qrgEncoder.colorBlack = Color.BLACK
        qrgEncoder.colorWhite = Color.WHITE
        try {
            // Getting QR-Code as Bitmap
            bitmap = qrgEncoder.bitmap
            // Setting Bitmap to ImageView
            qr_image.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            Log.v(TAG, e.toString())
        }
    }
}