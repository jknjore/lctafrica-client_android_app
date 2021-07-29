package com.lctapp.lct.Classes.Helpers

import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.lctapp.lct.R

object Loader {
    lateinit var pd:ProgressDialog


    fun showprogress(context: Context?, msg: String?) {
        pd = ProgressDialog(context)
        pd.setMessage(msg)
        pd.setCancelable(false)
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        pd.isIndeterminate = true
        pd.show()
    }

    fun dismissprogress() {
        try {
            pd.dismiss()
        }
        catch (ex:Exception)
        {

        }

    }


    fun showstaticprogress(context: Context?, msg: String?) {
        pd = ProgressDialog(context)
        pd.setMessage(msg)
        pd.setCancelable(false)
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        pd.isIndeterminate = true
        pd.show()
    }

    fun dismissstaticprogress() {
        pd.dismiss()
    }

    fun toast(context: Context, msg: String?) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    fun toastError(context: Context?, errormsg: String) {
        var errormsg = errormsg
        //errormsg = "Error: Please try again. $errormsg"
        errormsg = "Error: Please try again."
        Toast.makeText(context, errormsg, Toast.LENGTH_LONG).show()
        println(errormsg)
    }
}