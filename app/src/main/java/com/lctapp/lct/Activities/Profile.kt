package com.lctapp.lct.Activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import com.lctapp.lct.Classes.Variables
import com.lctapp.lct.R
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

class Profile : AppCompatActivity()  {
    var pictureURL: String =
        //"http://34.123.154.111:8085/compas/rest/user/uploadProfile"
        //"https://staging.lctafrica.io/compas/rest/user/uploadProfile"
    // "http://10.33.12.106:8080/compas/rest/user/uploadProfile"
        "http://35.241.171.182:8085/compas/rest/user/uploadProfile"
       // "https://lctafrica.io/compas/rest/user/uploadProfile"

    private val FILE_NAME ="photo.jpg"
    val MyPREFERENCES = "MyPrefs"
    var sharedpreferences: SharedPreferences? = null
    private var mProgressDialog: ProgressDialog? = null

    private var uploadedTask = 1

    lateinit var camera: Button
    private lateinit var storage: Button

    private lateinit var photoFile: File

    var member:String = ""

    private val REQUEST_CODE = 42

    private var cPhoto: Bitmap? = null

    var cPhotoRotated: Bitmap?=null

    private lateinit var selfi : ShapeableImageView
    companion object
    {
        private val SELECT_IMAGE_CODE=100

        // private val CAMERA_REQUEST = 123
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        getActionBar()?.setDisplayHomeAsUpEnabled(true);
        setTitle("My Profile")

        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE)

        if (Variables.getDependantChecker() == 0) {
            member = sharedpreferences?.getString("member", "null").toString()
        } else {
            member = Variables.getDependantNumber()
        }

        Log.e("####member", member)


        // initializeWidgets()

        selfi = findViewById(R.id.passport)


        val encode = findViewById<FloatingActionButton>(R.id.done)

        encode.setOnClickListener {

            val intent = Intent(this@Profile , HomepageActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

            this.finish()
            Toast.makeText(this@Profile, "Please wait.........", Toast.LENGTH_SHORT).show()

        }

        camera = findViewById(R.id.capture)


        camera.setOnClickListener {

            capturePhoto()
            //no camera permissions? how add them well it's not the permissions that are bringing this issue... ni URI add them just in case
        }

        storage = findViewById(R.id.gallery)

        storage.setOnClickListener {
            //check permissions at runtime
            openGallery()
        }

    }

    private fun capturePhoto(){

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        photoFile = photoFile(FILE_NAME)

        val fileProvider = FileProvider.getUriForFile(
            this@Profile,
            "com.lctapp.lct.fileprovider",
            photoFile
        )
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
        if (takePictureIntent.resolveActivity(this.packageManager)!=null) {
            startActivityForResult(takePictureIntent, REQUEST_CODE)
            Log.i("### camera intent", REQUEST_CODE.toString())

        }else{

            Toast.makeText(this@Profile, "Unable to Open Camera", Toast.LENGTH_SHORT).show()

        }
    }

    private fun photoFile(fileName: String): File {
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpeg", storageDirectory)
    }


    private fun openGallery(){

        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"

        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(Intent.createChooser(intent, "Select Picture..."), SELECT_IMAGE_CODE)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        //var cPhotoRotated: Bitmap? = null

        // Gallery Image
        if(requestCode == SELECT_IMAGE_CODE && resultCode == Activity.RESULT_OK){

            if(data!= null)
            {
                try {
                    cPhoto = MediaStore.Images.Media.getBitmap(
                        application.contentResolver,
                        data.data
                    )
                    //  Set photo to imageView
                    selfi.setImageBitmap(cPhoto) // here: this is when image is picked from the gallery

                    val base64ImageString : String? = cPhoto?.let { it1 -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        encode(it1)
                    }
                    else {
                        TODO("VERSION.SDK_INT < O")
                    }
                    }

                    Log.e("####gallery", "Base64ImageString = $base64ImageString")
                }
                catch (exp: IOException){
                    exp.printStackTrace()
                }

            } else if(resultCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(applicationContext, "Cancelled", Toast.LENGTH_LONG).show()
            }

            // Camera Image
        }
        else if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                cPhoto = BitmapFactory.decodeFile(photoFile.absolutePath)
                Timber.e("CHECk")

                if (cPhoto!=null) {
                    cPhotoRotated = imageRotation(cPhoto!!)
                    selfi.setImageBitmap(cPhotoRotated) // here too : so this is for the camera
                    val base64ImageString = cPhotoRotated?.let { it1 -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        encode(it1)
                    } else {
                        TODO("VERSION.SDK_INT < O")
                    }
                    }
                    Log.e("####", "Base64ImageString = $base64ImageString")
                }

            }catch (exp: IOException){
                exp.printStackTrace()

                Toast.makeText(this, "Something went Wrong", Toast.LENGTH_SHORT).show()
                Timber.e(cPhoto.toString())
            }

            // set photo to imageView

        } else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
    // encoding image to base 64
    @RequiresApi(Build.VERSION_CODES.O)
    private fun encode(bm: Bitmap): String? {
        showSimpleProgressDialog(this@Profile , null , "Uploading..." , false)
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        val reuslt : String = Base64.encodeToString(b, Base64.DEFAULT)
        val result = "data:image/jpeg;base64,$reuslt"

        if (Variables.getDependantChecker() == 0) {
            val editor = this.sharedpreferences!!.edit()
            editor.putString("image", result) //im saving the image in shared preferences then display the same image in homepage

            editor.apply()
            Variables.setImageStatus(1)
        }

        //Log.e("##### ","ENCODE Base64 : ")
//        longLog(reuslt)

        // post details to the back end
        // HERE

    var imageObject = JSONObject ()
    imageObject.put("memberPic", result)
    imageObject.put("memberNo", member)

        Log.e("##### imgJson", imageObject.toString())
    try {
          
        Fuel.put(
            pictureURL
        ).jsonBody(imageObject.toString()).responseJson { request , response , result ->
            Log.e("####response: " , result.get().content)
            onTaskCompleted(result.get().content , uploadedTask)


        }
    } catch (e: Exception) {


    } finally {

        return result
    }
        //PROGRESS
    }

    private fun onTaskCompleted(response: String , task: Int) {
        Log.d("responsejson" , response)
        removeSimpleProgressDialog() //will remove progress dialog
        when(task){
            uploadedTask ->
         if (isSuccess(response)) {

                Toast.makeText(
                    this@Profile ,
                    "Upload Successful!" ,
                    Toast.LENGTH_SHORT
                ).show()
            } else if(notSuccessful(response)) {
                Toast.makeText(
                    this@Profile ,
                    "Please Try Again The Upload Failed !" ,
                    Toast.LENGTH_LONG
                ).show()

            }else {

                Toast.makeText(
                    this@Profile ,
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

    private fun getErrorMessage(response: String): String {
        try {
            val jsonObject = JSONObject(response)
            return jsonObject.getString("message")

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return "Kindly use a more clear Photo"
    }

    fun showSimpleProgressDialog(
        context: Context,
        title: String?,
        msg: String,
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


    private fun imageRotation(bitmap: Bitmap): Bitmap? {
        val ei = ExifInterface(photoFile)
        val orientation = ei.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )
        var rotatedBitmap: Bitmap? = null
        rotatedBitmap =
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270f)
                ExifInterface.ORIENTATION_NORMAL -> bitmap
                else -> bitmap
            }
        return rotatedBitmap
    }

    private fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }



    // DISPLAYS Long Strings kwa Logcat, Axel
//    fun longLog(str: String) {
//        if (str.length > 4000) {
//            Log.e("", str.substring(0, 4000))
//            longLog(str.substring(4000))
//        } else Log.e("", str)
//    }

}