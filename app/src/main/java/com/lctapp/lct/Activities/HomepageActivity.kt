package com.lctapp.lct.Activities

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.HttpException
import com.google.gson.Gson
import com.lctapp.lct.*
import com.lctapp.lct.Classes.Insurer
import com.lctapp.lct.Classes.MemberData
import com.lctapp.lct.Classes.Variables
import com.lctapp.lct.Classes.security.Biometric
import com.lctapp.lct.Classes.utills.toast
import kotlinx.android.synthetic.main.activity_homepage.*
import org.json.JSONObject
import java.io.Serializable
import com.google.android.material.imageview.ShapeableImageView as ShapeableImageView1

class HomepageActivity: AppCompatActivity() {


    lateinit var dialog: Dialog
    lateinit var imageView: ImageView
    var firstTimeLogin =0
    var useBio = 0
    private var verified:Boolean = false
    lateinit var bios:Biometric
    lateinit var btnLogout: Button
    var member:String=""
    var cardViewhURL: String = ""
    val MyPREFERENCES = "MyPrefs"
    lateinit var pic : ShapeableImageView1
    var images:String = ""
    var memberId:String = ""
    private var Scheme:String =""
    // No Internet Dialog
    //private var noInternetDialog: NoInternetDialog? = null

    var CHANNEL_ID_ANDROID = "com.lctapp.lct"
    var CHANNEL_NAME = "ANDROID_CHANNEL"

    var sharedpreferences: SharedPreferences? = null

    private var mProgressDialog: ProgressDialog? = null

    lateinit var notificationButton:TextView
    lateinit var membInfo: MemberData


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        Log.e("####called create", "so many times")
        //instantiating sharedpreferences
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE)
        member = sharedpreferences?.getString("member", "null").toString()
        images = sharedpreferences?.getString("image", "").toString()
        Log.e("####sharedprefs", images)
        cardViewhURL= //"http://34.123.154.111:8085/compas/rest/member/gtEclaimMembers?memberNo=$member"
                //"https://staging.lctafrica.io/compas/rest/member/gtEclaimMembers?memberNo=$member"
        "http://35.241.171.182:8085/compas/rest/member/gtEclaimMembers?memberNo=$member"
        //"https://lctafrica.io/compas/rest/member/gtEclaimMembers?memberNo=$member"
        Log.e("URL>>", cardViewhURL)

        val editor= sharedpreferences!!.edit()
        editor.putString("images", images)
        editor.apply()

        val bio = findViewById<View>(R.id.fingerPrintView)
        val home = findViewById<View>(R.id.homeView)

        firstTimeLogin = sharedpreferences?.getInt("firstTimeLogin",0)!!

        Log.e("####>>", "Vast time rogin : " +firstTimeLogin)

        useBio = sharedpreferences?.getInt("useBio",0)!!

        Log.e("####>>","useBio: " +useBio)


        if ( firstTimeLogin == 0) {

            firstTimeLogin = 1
            editor.putInt("firstTimeLogin", firstTimeLogin)
            editor.commit()

            var temp = sharedpreferences!!.getInt("firstTimeLogin", 0).toString()
            Log.e("####>>", temp)

            val mAlertDialogBuilder = AlertDialog.Builder(this)
            mAlertDialogBuilder.setTitle("Enable biometrics for privacy?")
            mAlertDialogBuilder.setIcon(R.drawable.lctpng)
            mAlertDialogBuilder.setCancelable(false)
            mAlertDialogBuilder.setPositiveButton("OK") { _, _ ->
                useBio = 1
                editor.putInt("useBio", useBio)
                editor.commit()
                bio.visibility = View.VISIBLE
                bios = Biometric(this)
                Log.e("###>>>BIOMETRICS",bios.toString())
                bios.showBioDialog()


            }
            mAlertDialogBuilder.setNegativeButton("Cancel") { dialog, negativeButton_ ->
                useBio = 0
                dialog.dismiss()
            }
            val mAlertDialog = mAlertDialogBuilder.create()
            mAlertDialog.show()
            mAlertDialog.window?.setGravity(Gravity.BOTTOM) // controls width and height of the alert dialogue
        }

        if( useBio == 1){
            bio.visibility = View.VISIBLE
            bios = Biometric(this)
            bios.showBioDialog()

            if (verified) {
                useBio = 0
            }
        }


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle(R.string.app_name)

        // options menu
        val options = findViewById<TextView>(R.id.optionMenu)

        options.setOnClickListener {
            showPopup(it)
        }

        btnLogout = findViewById(R.id.btnLogout)

        pic = findViewById(R.id.selfie)

        try {
// DECODING BASE64
            if (Variables.getImageStatus() == 1) {
                var images = sharedpreferences?.getString("image", "")

                val decodedImage = images?.let { decodeString(it) }

                pic.setImageBitmap(decodedImage)

            }

        } catch (z: Exception) {
            Log.e("#### ", "ERROR Z : " + z.toString())
        }

        pic.setOnClickListener {

            Variables.setImageStatus(0)

            val intent = Intent(this@HomepageActivity, Profile::class.java)
            startActivity(intent)
        }


        val  imgbasketBalance = findViewById(R.id.imgbasketBalance) as ImageButton
        imgbasketBalance.setOnClickListener {
            val intent = Intent(this@HomepageActivity, ServiceBalances::class.java)
            startActivity(intent)
        }

        val imgbMemberDetails = findViewById(R.id.imgbMemberDetails) as ImageButton
        imgbMemberDetails.setOnClickListener {
            Log.e("###Memberdetails", member)
            val intent = Intent(this@HomepageActivity, MemberActivity::class.java)
            startActivity(intent)
        }


        val imgtransactionHistory = findViewById<ImageButton>(R.id.imgtransactionHistory) as ImageButton
        imgtransactionHistory.setOnClickListener {
            val intent = Intent(this@HomepageActivity, TransactionActivity::class.java)
            startActivity(intent)
        }

        val img6 = findViewById<ImageButton>(R.id.imgFaqs) as ImageButton
        img6.setOnClickListener {
            val intent = Intent(this@HomepageActivity, FrequentlyAskedQuestions::class.java)
            startActivity(intent)
            Toast.makeText(this, "Frequently Asked Questions and Answers", Toast.LENGTH_SHORT).show()
        }

        val bmi = findViewById<ImageButton>(R.id.bmiImageView) as ImageButton
        bmi.setOnClickListener {
            val intent =Intent(this@HomepageActivity, BmiCalculator::class.java)
            startActivity(intent)

        }

        val hospitalVisit =findViewById<ImageButton>(R.id.hospitalVisit) as ImageButton
        hospitalVisit.setOnClickListener {

            val intent = Intent(this,StartVisit::class.java)
            intent.putExtra("MemberData",membInfo as Serializable)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            logout()
        }

        cardView()

        getGreetingMessage()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.homepage_menu,menu)

        return true
        //return super.onCreateOptionsMenu(menu)
    }

    fun showPopup(v: View){
        val popup = PopupMenu(this, v)
        val inflater:MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.homepage_menu,popup.menu)
        popup.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.aboutLct-> {
                    startActivity(Intent(this@HomepageActivity, AboutLct::class.java))
                }
                R.id.contactUs->{

                    intent = Intent(this@HomepageActivity, ContactUs::class.java)
                    startActivity(intent)

                    //Toast.makeText(this, "I have been Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.notifications->{

                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                        val imp = NotificationManager.IMPORTANCE_HIGH
                        val mNotificationChannel = NotificationChannel(CHANNEL_ID_ANDROID,CHANNEL_NAME,imp)
                        val notificationBuilder:Notification.Builder = Notification.Builder(this@HomepageActivity,CHANNEL_ID_ANDROID)
                            .setSmallIcon(R.drawable.lctpng)
                            .setContentTitle("LCT Africa")
                            .setContentText("You have received an OTP")

                        val notificationManager:NotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        notificationManager.createNotificationChannel(mNotificationChannel)
                        notificationManager.notify(0,notificationBuilder.build())
                    }else{
                        val notificationBuilder2: NotificationCompat.Builder = NotificationCompat.Builder(this@HomepageActivity,CHANNEL_ID_ANDROID)
                            .setSmallIcon(R.drawable.lctpng)
                            .setContentTitle("LCT Africa")
                            .setContentText("You have received an OTP")
                        val notificationManager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        notificationManager.notify(0,notificationBuilder2.build())
                    }

                }

                R.id.logout->{
                    logout()
                }
            }
            true
        }
        popup.show()
    }

    //   code to show Greetings dependent on system time.
    @RequiresApi(Build.VERSION_CODES.N)
    fun getGreetingMessage() {
        val c = Calendar.getInstance()
        var message: String=""

        message = when (c.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> "Good Morning,"
            in 12..15 -> "Good Afternoon,"
            in 16..20 -> "Good Evening,"
            in 21..23 -> "Good Night,"
            else -> {
                "Hello"
            }
        }

        greetingDisplay.text = message
    }



    private fun cardView(){
        showSimpleProgressDialog(this@HomepageActivity, null, "fetching...", false)
        try {

            Fuel.get(cardViewhURL).responseJson { request, response, result ->
                Log.e("response: ", result.get().content)
                onTaskCompleted(result.get().content)
            }
        } catch (e: HttpException) {
            Log.e("Exception", e.toString())
            toast("Please Make Sure You Are Connected To The Internet!!!")
        }
    }
    private fun onTaskCompleted(response: String) {
        Log.e("responsejson", response)
        removeSimpleProgressDialog()  //will remove progress dialog
        Log.e("check>>>>:", "one")
        try {
            Log.e("CHECK>>:", "two")

            val jsonObject = JSONObject(response)
            var m: MemberData
            val gson = Gson()
             membInfo = gson.fromJson(jsonObject.toString(), MemberData::class.java)


            Log.e("###MemberInfo>>: " , membInfo.fullName.toString())


            Log.e("JSON>>: ", jsonObject.toString())

            try {
                if (jsonObject.getString("memberPic") != null) {
                    images = jsonObject.getString("memberPic")

                    val decodedImage = images?.let { decodeString(it) }

                    pic.setImageBitmap(decodedImage)

                }

            } catch (e : Exception) {
                Log.e("#### ERROR ", "HomePageActivity : " +e.toString())

            }

            memberId = jsonObject.getString("memberId")
            Log.e("####memberId", jsonObject.getString("memberId"))

            memberNo.text = jsonObject.getString("memberNo")

            Log.e("member>>>", jsonObject.getString("memberNo"))


            policy.text =jsonObject.getString("policyStartDate")
            Log.e("###policyStartDate>>>",jsonObject.getString("policyStartDate")).toString()
//            used this to get full name.
//            Name.text = jsonObject.getString("fullName")

            var fullName = jsonObject.getString("fullName")

            val username = fullName.substringBefore(
                delimiter = " ",
                missingDelimiterValue = "Username Not properly structured"
            )
            println("Username -> $username")

            Name.text = username

            Log.e("Conc USERNAME >>>  ", username)



            val editor = sharedpreferences!!.edit()
            editor.putString("memberId", memberId)     //im saving the image in shared prefrences then display the same image in homepage
            editor.apply()

            // THIS LINE COMMENTED OUT REPRESENTS THE USERS SCHEME ALSO COMMENTED OUT IN THE XML

            val jsonObject1 = JSONObject(response)
            Scheme =jsonObject1.optString("scheme")
            Log.e("###scheme>>>", jsonObject.getString("scheme"))
            Insurer.productName = Scheme
            Log.e("###Insurer.productName",Scheme.toString())



        } catch (e: java.lang.Exception) {
            Log.e("##EXE >>", e.toString())
        }

        Log.e("##CHECK >>", "three")
    }
    fun showSimpleProgressDialog(
        context: Context,
        title: String?,
        msg: String,
        isCancelable: Boolean
    ) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg)
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

    private fun logout(){
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedpreferences!!.edit()
        editor.clear()
        editor.apply()
        finish()
        startActivity(Intent(this@HomepageActivity, LoginActivity::class.java))

    }

    private fun decodeString(sitring : String): Bitmap? {

        // Removes the Base64 header before decoding and stores the result into 'finalImage' String
        val finalImage = sitring?.drop(23)

        if (finalImage != null) {
            Log.e("###Image", finalImage)
        }

        val imageBytes = Base64.decode(finalImage, Base64.DEFAULT)
        var mbitmap: Bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)


        return mbitmap
    }

}