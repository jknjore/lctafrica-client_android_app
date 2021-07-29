package com.lctapp.lct.Classes.Helpers

import android.Manifest
import android.R
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.gson.GsonBuilder


object General {
    fun mapsok(context: Activity?): Boolean {
        val available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
        return if (available == ConnectionResult.SUCCESS) {
            true
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            val dialog = GoogleApiAvailability.getInstance().getErrorDialog(context, available, 2)
            dialog.show()
            false
        } else {
            context?.let { Loader.toast(it, "Error Fetching Maps") }
            false
        }
    }

    fun isOnline(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        return true
                    }
                }
            } else {
                try {
                    val activeNetworkInfo = connectivityManager.activeNetworkInfo
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting) {
                        return true
                    }
                } catch (e: Exception) {
                }
            }
        }
        return false
    }

    fun getDump(o: Any?): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(o)
    }

    /**
     * Returns the unique identifier for the device
     *
     * @return unique identifier for the device
     */
    /**
     * Returns the unique identifier for the device
     *
     * @return unique identifier for the device
     */
    fun getDeviceIMEI(context: Activity?): String? {
        if (context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.READ_PHONE_STATE) }
            != PackageManager.PERMISSION_GRANTED) {
            // READ_PHONE_STATE permission has not been granted.
            requestReadPhoneStatePermission(context)
            return ""
        }
        var deviceUniqueIdentifier: String? = null
//        val tm = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
//        if (null != tm) {
//            deviceUniqueIdentifier = tm.deviceId
//        }
        if (null == deviceUniqueIdentifier || deviceUniqueIdentifier.isEmpty()) {
            deviceUniqueIdentifier =
                Settings.Secure.getString(context?.getContentResolver(), Settings.Secure.ANDROID_ID)
        }
        return deviceUniqueIdentifier
    }

    private fun requestReadPhoneStatePermission(context: Activity?) {

            // READ_PHONE_STATE permission has not been granted yet. Request it directly.
        if (context != null) {
            ActivityCompat.requestPermissions(
                context, arrayOf(Manifest.permission.READ_PHONE_STATE),
                200
            )
        }

    }

}


