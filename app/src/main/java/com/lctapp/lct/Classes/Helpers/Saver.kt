package com.lctapp.lct.Classes.Helpers

import android.content.Context
import android.widget.Toast


class Saver {

    fun savedata(context: Context, dataName: String?, dataValue: String?): Boolean {
        return try {
            val pref = context.getSharedPreferences(storeName, Context.MODE_PRIVATE)
            val ed = pref.edit()
            ed.putString(dataName, dataValue)
            ed.putString(dataName, dataValue)
            ed.apply()
            true
        } catch (ex: Exception) {
            Toast.makeText(context, "Error saving data", Toast.LENGTH_LONG).show()
            false
        }
    }

    fun getdata(context: Context, dataName: String?): String? {
        return try {
            val pref = context.getSharedPreferences(storeName, Context.MODE_PRIVATE)
            pref.getString(dataName, "NA")
        } catch (ex: Exception) {
            Toast.makeText(context, "Error getting data", Toast.LENGTH_LONG).show()
            "NA"
        }
    }

    companion object {
        private const val storeName = "env"
    }
}
