package com.lctapp.lct.Activities


import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lctapp.lct.Classes.security.Biometric
import com.lctapp.lct.R
import kotlinx.android.synthetic.main.activity_login.*


class LauncherActivity: AppCompatActivity() {

    internal val TimeOut=3000
    val MyPREFERENCES = "MyPrefs"
    var loginTask = 0
    var user : String = ""
    var useBio = 0
    lateinit var bios:Biometric

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        // Used to get user that is already logged in from shared preferences
        val sharedPreferences = getSharedPreferences(MyPREFERENCES , Context.MODE_PRIVATE)
        loginTask = sharedPreferences.getInt("logged" , 0)

        fun isOnline(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }


//        if (isOnline(this)){

            if (loginTask == 1) {
                user = sharedPreferences.getString("user", "null").toString()
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this@LauncherActivity, HomepageActivity::class.java)) //see this part of launching the activity
                    finish()
                }, TimeOut.toLong())

            }

            else {
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this@LauncherActivity, LoginActivity::class.java)) //see this part of launching the activity
                    finish()
                }, TimeOut.toLong())
            }
//        }
//        else{
//            Handler(Looper.getMainLooper()).postDelayed({
//                startActivity(Intent(this@LauncherActivity, LauncherActivity::class.java)) //see this part of launching the activity
//                finish()
//                Toast.makeText(this,"there is no Internet connection",Toast.LENGTH_LONG).show()
//            }, TimeOut.toLong())
//        }

        }
    }