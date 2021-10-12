package com.lctapp.lct.Activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.core.HttpException
import com.lctapp.lct.Classes.Insurer.Companion.productName
import com.lctapp.lct.Classes.Adapters.ListAdapter
import com.lctapp.lct.Classes.Api.HospitalsAPi
import com.lctapp.lct.Classes.Constants.APIClient
import com.lctapp.lct.Classes.Helpers.Loader
import com.lctapp.lct.Classes.Models.MyData
import com.lctapp.lct.Classes.Models.Payloads.Transaction
import com.lctapp.lct.Classes.Models.Responses.TransactionResp
import com.lctapp.lct.Classes.utills.toast
import com.lctapp.lct.R
import kotlinx.android.synthetic.main.activity_transaction.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.properties.Delegates


class TransactionActivity : AppCompatActivity() {

    lateinit var pDialog: ProgressDialog

    val MyPREFERENCES = "MyPrefs"
    var sharedpreferences: SharedPreferences? = null
    var member: String = ""
    var fromDate = ""
    var toDate = ""
    var det: String = ""
    var resp: String = ""
    lateinit var today :Date
    var minDate by Delegates.notNull<Long>()
    var maxDate by Delegates.notNull<Long>()
    var l: Loader = Loader
    private var apiC: HospitalsAPi? = null
    
    //lateinit var spinner: Spinner
    lateinit var mylist: ListView


    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        getActionBar()?.setDisplayHomeAsUpEnabled(true);
        setTitle("My Transactions")

        mylist = findViewById<ListView>(R.id.mylist)as ListView
        apiC = APIClient.client?.create(HospitalsAPi::class.java)

        doInBackground.setOnClickListener {

            Log.e("### ", "start spinner >> " +start.text)
            Log.e("###", "stop spinner : >> " +stop.text)

            if (start.text .isEmpty() || stop.text.isEmpty()) {

                start.error = "Please select a Start Date first"
                start.hint = "Please select a Start Date first"
                start.setHintTextColor(Color.parseColor("#FF0000"))


                stop.error = "Please select an End Date first"
                stop.hint = "Please select an End Date firs"
                stop.setHintTextColor(Color.parseColor("#FF0000"))
            }else {

//                val task = AsyncTaskHandler()
//                task.execute()
                member_transactions()
            }
            //gtMemberUtilizationBalance/
        }

        doInBackgrnd.setOnClickListener {

            Log.e("### ", "start spinner >> " +start.text)
            Log.e("###", "stop spinner : >> " +stop.text)


            if (start.text == "" || stop.text == "") {

                start.error = "Please select a Start Date first"
                start.hint = "Please select a Start Date first"
                start.setHintTextColor(Color.parseColor("#FF0000"))


                stop.error ="Please select an End Date first"
                stop.hint = "Please select an End Date firs"
                stop.setHintTextColor(Color.parseColor("#FF0000"))

            } else {
//                val task2 = AsyncTaskHandler2()
//                task2.execute()
                family_transactions()
            }
        }
        start.setOnClickListener {
            val dp = DatePickerDialog(this , { view, mYear, mMonth, mDay -> val newMonth = mMonth + 1

                    start.text = "$mYear-$newMonth-$mDay"
                },
                year ,
                month ,
                day
            )

            today = Date()
            c.time = today
            c.add( Calendar.MONTH,+0)
            maxDate = c.time.time
            dp.datePicker.maxDate = maxDate

            when (productName) {
                "THE JUDICIAL SERVICE COMMISSION" -> {
                    today =  Date()
                    c.time = today
                    c.add(Calendar.MONTH, -4) // Subtract 6 months
                    c.set(year, 0, 18)
                    minDate = c.timeInMillis
                    minDate = c.time.time
                    dp.datePicker.minDate = minDate


                }
                "LIAISON GROUP STAFF" -> {
                    today =  Date()
                    c.time = today
                    c.add(Calendar.MONTH, -4) // Subtract 6 months
                    c.set(year, 2, 8)
                    minDate = c.timeInMillis
                    minDate = c.time.time
                    dp.datePicker.minDate = minDate
                }
                else -> {
                    (productName == "LAKE TURKANA WIND POWER LIMITED")
                    today =  Date()
                    c.time = today
                    c.add(Calendar.MONTH, -4) // Subtract 6 months
                    c.set(year, 11, 1)
                    minDate = c.timeInMillis
                    minDate = c.time.time
                    dp.datePicker.minDate = minDate
                }
            }
            dp.show()
        }

        stop.setOnClickListener {
            val dpd = DatePickerDialog(this , { view , nYear , nMonth , nDay -> val newMonth = nMonth + 1

                    stop.text = "$nYear-$newMonth-$nDay"
                },
                year ,
                month ,
                day
            )
            today = Date()
            c.time = today
            c.add( Calendar.MONTH,+0)
            maxDate = c.time.time
            dpd.datePicker.maxDate = maxDate

            when (productName) {
                "THE JUDICIAL SERVICE COMMISSION" -> {
                    today =  Date()
                    c.time = today
                    c.add(Calendar.MONTH, -4) // Subtract 6 months
                    c.set(year, 0, 18)
                    minDate = c.timeInMillis
                    minDate = c.time.time
                    dpd.datePicker.minDate = minDate


                }
                "LIAISON GROUP STAFF" -> {
                    today =  Date()
                    c.time = today
                    c.add(Calendar.MONTH, -4) // Subtract 6 months
                    c.set(year, 2, 8)
                    minDate = c.timeInMillis
                    minDate = c.time.time
                    dpd.datePicker.minDate = minDate
                }
                else -> {
                    (productName == "LAKE TURKANA WIND POWER LIMITED")
                    today = Date()
                    c.time = today
                    c.add(Calendar.MONTH, -4) // Subtract 6 months
                    c.set(year, 11, 1)
                    minDate = c.timeInMillis
                    minDate = c.time.time
                    dpd.datePicker.minDate = minDate
                }
            }

            dpd.show()
        }

        // Fetch using member number
       // AsyncTaskHandler().execute()
        sharedpreferences = getSharedPreferences(MyPREFERENCES , Context.MODE_PRIVATE)
        member_transactions()
    }


    private fun member_transactions()
    {

        member = sharedpreferences?.getString("member" , "null").toString()
        val editor = sharedpreferences!!.edit()
        editor.putString("member" , member)
        editor.apply()
        fromDate = start!!.text.toString()//hapa ndo nimeset value ya from date
        toDate = stop!!.text.toString()//hapa ndo ya to date

        if (fromDate.isEmpty() || toDate.isEmpty()) {
            det = "{\"memberNo\": \"$member\"}"
            Log.e("###CHECK 1 >> " , det)
            Loader.toast(this@TransactionActivity,"Please select a date range")
            return
        }

        get_member_transactions(member,fromDate,toDate)
        //get_member_transactions("73168-01",fromDate,toDate)


    }


    private fun family_transactions()
    {
        member = sharedpreferences?.getString("member" , "null").toString()
        val editor = sharedpreferences!!.edit()
        editor.putString("member" , member)
        editor.apply()
        fromDate = start!!.text.toString()//hapa ndo nimeset value ya from date
        toDate = stop!!.text.toString()//hapa ndo ya to date


        if (fromDate.isEmpty() || toDate.isEmpty()) {
            det = "{\"memberNo\": \"$member\"}"
            Log.e("###CHECK 1 >> " , det)
            Loader.toast(this@TransactionActivity,"Please select a date range")
            return
        }

        get_family_transactions(member,fromDate,toDate)
        //get_family_transactions("73168-01",fromDate,toDate)
    }


    // new asynctask

    @SuppressLint("StaticFieldLeak")
    inner class AsyncTaskHandler : AsyncTask<String , String , String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            pDialog = ProgressDialog(this@TransactionActivity)
            pDialog.setMessage("Fetching....")
            pDialog.setCancelable(false)
            pDialog.show()

        }

        override fun doInBackground(vararg params: String?): String {

            val client = OkHttpClient.Builder()
                .connectTimeout(1000 , TimeUnit.SECONDS)
                .writeTimeout(1000 , TimeUnit.MINUTES)
                .readTimeout(1000 , TimeUnit.MINUTES)
                .build()

            member = sharedpreferences?.getString("member" , "null").toString()
            val editor = sharedpreferences!!.edit()
            editor.putString("member" , member)
            editor.apply()
            fromDate = start!!.text.toString()//hapa ndo nimeset value ya from date
            toDate = stop!!.text.toString()//hapa ndo ya to date


            try {
                if (fromDate.isEmpty() || toDate.isEmpty()) {
                    det = "{\"memberNo\": \"$member\"}"
                    Log.e("###CHECK 1 >> " , det)
                } else {

                    det =
                        "{\"memberNo\":\"$member\",\"fromDate\":\"$fromDate\",\"toDate\":\"$toDate\"}"
                    Log.e("###CHECK 1 >> " , det)
                }

                val request = Request.Builder()
                    //.url("http://34.123.154.111:8085/compas/rest/transaction/gtAllTxnDetailMobileApp")
                    //.url("https://staging.lctafrica.io/compas/rest/transaction/gtAllTxnDetailMobileApp")
                    //.url("https://lctafrica.io/compas/rest/transaction/gtAllTxnDetailMobileApp")
                    .url("http://35.241.171.182:8085/compas/rest/transaction/gtAllTxnDetailMobileApp")

                    .post(det.toRequestBody("application/json".toMediaType()))
                    //  hapa sasa ndio mimi hutumia retrofit

                    .build()

                Log.e("###URL >> " , "yeah")
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {

                        resp = response.body!!.string()
                        Log.e("## RESP >> " , resp)
                    } else throw IOException("Unexpected code $response")
                }

            } catch (e: HttpException) {
                Log.e("## OKHTTPEXception >> " , e.toString())
                toast( "Please Ensure you're connected to the Internet!!!")
                return "FAIL"
            }

            return "OK"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            // after the results have been fetched, the progress dialog is dismissed and the results get to be displayed
            if (pDialog.isShowing)
                pDialog.dismiss()

            if (result.equals("OK")) {
                jsonResult(resp)
            } else {
                if (result != null) {
                    Log.e("### RESULT WAS >> " , result)
                }
            }

        }

    }




    @SuppressLint("StaticFieldLeak")
    inner class AsyncTaskHandler2 : AsyncTask<String , String , String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            pDialog = ProgressDialog(this@TransactionActivity)
            pDialog.setMessage("Fetching....")
            pDialog.setCancelable(false)
            pDialog.show()

        }

        override fun doInBackground(vararg params: String?): String {

            val client = OkHttpClient.Builder()
                .connectTimeout(1000 , TimeUnit.SECONDS)
                .writeTimeout(1000 , TimeUnit.MINUTES)
                .readTimeout(1000 , TimeUnit.MINUTES)
                .build()

            member = sharedpreferences?.getString("member" , "null").toString()
            val editor = sharedpreferences!!.edit()
            editor.putString("member" , member)
            editor.apply()
            fromDate = start!!.text.toString()//hapa ndo nimeset value ya from date
            toDate = stop!!.text.toString()//hapa ndo ya to date


            try {
                if (fromDate.isEmpty() || toDate.isEmpty()) {
                    det = "{\"memberNo\": \"$member\"}"
                    Log.e("###CHECK 1 >> " , det)
                } else {

                    det =
                        "{\"memberNo\":\"$member\",\"fromDate\":\"$fromDate\",\"toDate\":\"$toDate\"}"
                    Log.e("###CHECK 1 >> " , det)
                }

                val request = Request.Builder()
                    //.url("http://34.123.154.111:8085/compas/rest/transaction/gtAllTxnDetailMobileApp")
                   // .url("https://staging.lctafrica.io/compas/rest/transaction/gtAllTxnDetailMobileApp")
                    //.url("https://lctafrica.io/compas/rest/transaction/gtMemberTxnDetail/")
                    .url("http://35.241.171.182:8085/compas/rest/transaction/gtMemberTxnDetail/")

                    .post(det.toRequestBody("application/json".toMediaType()))
                    .build()

                Log.e("###URL >>>>> " , "yeahooo <<<<<< ")
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {

                        resp = response.body!!.string()
                        Log.e("## RESP >> " , resp)
                    } else throw IOException("Unexpected code $response")
                }

            } catch (e: HttpException) {

                Log.e("### OKHTTPEXception >> " , e.toString())
                toast( "Please Ensure you're connected to the Internet!!!")
                return "FAIL"
            }

            return "OK"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            // after the results have been fetched, the progress dialog is dismissed and the results get to be displayed
            if (pDialog.isShowing)
                pDialog.dismiss()

            if (result.equals("OK")) {
                jsonResult(resp)
            } else {
                if (result != null) {
                    Log.e("### RESULT WAS >> " , result)
                }
            }

        }

    }

    private fun jsonResult(jsonString: String?) {
        var jsonArray = JSONArray(jsonString)
        var list = ArrayList<MyData>()
        // while (i=0),the program goes fetching all the json data.
        var i = 0
        while (i < jsonArray.length()) {
            var jsonObject = jsonArray.getJSONObject(i)
            list.add(
                MyData(
                    jsonObject.getString("txnDate") ,
                    jsonObject.getString("serviceName") ,
                    jsonObject.getString("serviceAmount") ,
                    jsonObject.getString("merchantName")
                )
            )

            i++
        }
        list.sortByDescending { it.txnDate }
        val adapter = ListAdapter(this@TransactionActivity , list)
        mylist.adapter = adapter
        Log.e("mylist>>" , mylist.adapter.toString())
    }


    private fun json_Result(trans:List<TransactionResp>) {
        var list = ArrayList<MyData>()
        // while (i=0),the program goes fetching all the json data.
        var i = 0
        for (x:TransactionResp in trans)
        {
            list.add(
                MyData(
                    x.txnDate,
                    x.serviceName,
                    x.serviceAmount.toString(),
                    x.merchantName
                )
            )
        }
        list.sortByDescending { it.txnDate }
        val adapter = ListAdapter(this@TransactionActivity , list)
        mylist.adapter = adapter
        Log.e("mylist>>" , mylist.adapter.toString())
    }



    private fun get_member_transactions(memberNo:String,fromdate:String,todate:String)
    {
        l.showprogress(this@TransactionActivity,"please wait......")
        try {
            val payload_details = Transaction(memberNo,fromDate,toDate)
            val call: Call<List<TransactionResp>> = apiC!!.get_member_transactions(payload_details)
            call.enqueue(object : Callback<List<TransactionResp>> {
                override fun onResponse(
                    call: Call<List<TransactionResp>>,
                    response: Response<List<TransactionResp>>
                ) {
                    l.dismissprogress()
                    if (!response.isSuccessful) {
                        var errorBodyString = ""
                        try {
                            errorBodyString = response.errorBody()!!.string()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        Loader.toastError(this@TransactionActivity, errorBodyString)
                        l.dismissprogress()
                        return
                    }
                    val resp: List<TransactionResp>? = response.body()
                    if(resp!!.size > 0)
                    {
                        json_Result(resp)
                    }
                    else
                    {
                        Loader.toast(this@TransactionActivity, "No member transactions were found")
                    }

                }

                override fun onFailure(call: Call<List<TransactionResp>>, t: Throwable) {
                    Loader.toastError(this@TransactionActivity, t.message!!)
                    l.dismissprogress()
                }
            })
        } catch (e: Exception) {
            Loader.toast(this@TransactionActivity, "Error getting member transactions")
            l.dismissprogress()
        }
    }



    private fun get_family_transactions(memberNo:String,fromdate:String,todate:String)
    {
        l.showprogress(this@TransactionActivity,"please wait......")
        try {
            val payload_details = Transaction(memberNo,fromDate,toDate)
            val call: Call<List<TransactionResp>> = apiC!!.get_family_transactions(payload_details)
            call.enqueue(object : Callback<List<TransactionResp>> {
                override fun onResponse(
                    call: Call<List<TransactionResp>>,
                    response: Response<List<TransactionResp>>
                ) {
                    l.dismissprogress()
                    if (!response.isSuccessful) {
                        var errorBodyString = ""
                        try {
                            errorBodyString = response.errorBody()!!.string()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        Loader.toastError(this@TransactionActivity, errorBodyString)
                        l.dismissprogress()
                        return
                    }
                    val resp: List<TransactionResp>? = response.body()
                    if(resp!!.size > 0)
                    {
                        json_Result(resp)
                    }
                    else
                    {
                        Loader.toast(this@TransactionActivity, "No member transactions were found")
                    }

                }

                override fun onFailure(call: Call<List<TransactionResp>>, t: Throwable) {
                    Loader.toastError(this@TransactionActivity, t.message!!)
                    l.dismissprogress()
                }
            })
        } catch (e: Exception) {
            Loader.toast(this@TransactionActivity, "Error getting member transactions")
            l.dismissprogress()
        }
    }

}