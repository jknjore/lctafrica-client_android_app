package com.lctapp.lct.Activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.View.OnTouchListener
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnSuccessListener
import com.lctapp.lct.Classes.Api.HospitalsAPi
import com.lctapp.lct.Classes.Constants.APIClient
import com.lctapp.lct.Classes.FamilyMembers
import com.lctapp.lct.Classes.Helpers.*
import com.lctapp.lct.Classes.Helpers.AppConstants.NGROK_API_
import com.lctapp.lct.Classes.Helpers.AppConstants.START_VISIT_NGROK
import com.lctapp.lct.Classes.Helpers.AppConstants.START_VISIT_URL
import com.lctapp.lct.Classes.Helpers.AppConstants.VALIDATE_LOCATION_URL
import com.lctapp.lct.Classes.MemberData
import com.lctapp.lct.Classes.Models.HospitalData
import com.lctapp.lct.Classes.Models.MemberClaims.FamilyMemList
import com.lctapp.lct.Classes.Models.MemberClaims.MemberClaims
import com.lctapp.lct.Classes.Models.Payloads.Member
import com.lctapp.lct.Classes.Models.Payloads.StartVisitData
import com.lctapp.lct.Classes.Models.Payloads.UserLocationData
import com.lctapp.lct.Classes.Models.Responses.GeneralResponse
import com.lctapp.lct.Classes.utills.toast
import com.lctapp.lct.R
import kotlinx.android.synthetic.main.activity_start_visit.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import java.util.*

class StartVisit : AppCompatActivity() {
    private var submitbtn: Button? = null
    var select_hospital: TextView? = null
    var hospitals: List<HospitalData> = ArrayList<HospitalData>()
    private var apiC: HospitalsAPi? = null
    var MerchantID = 0
    var Submitting = 0
    var closing = 0
    var deciding =0
    var invoice =""
    var l: Loader = Loader
    lateinit var memberInfo:MemberClaims
    var s:Saver = Saver()

    var locationValidated:Boolean = false

    private var mProgressDialog: ProgressDialog? = null

    private var mFusedLocationClient: FusedLocationProviderClient? = null

//   private var mAlertDialogBuilder = AlertDialog.Builder()

    private var wayLatitude: Double  = 0.0
    private  var wayLongitude: Double = 0.0
    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null
    private val isContinue = false
    private var isGPS = false
    var code_: String? = null
    private var popupshown = false
    var alertDialog: AlertDialog? = null
    val MyPREFERENCES = "MyPrefs"
    var mobileApp:String ="mobileApp"
    private lateinit var sharedpreferences: SharedPreferences
    private lateinit var sickClient:Spinner
    private var selectedMemberNo:String = ""
    lateinit var back:ImageButton
    lateinit var member:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_visit)

        back = findViewById(R.id.backButton)
        back.setOnClickListener {
            finish()
        }

      memberInfo= intent.getSerializableExtra("MemberData") as MemberClaims
        Log.e("####FromStart",memberInfo.fullName.toString())

        val output: MutableList<String> = ArrayList()
        output.add(memberInfo.fullName.toString()+ " : " + memberInfo.memberNo)
        for (f: FamilyMemList in memberInfo.familyMemList!!) {
                output.add(f.famMemFullName.toString()+ " : " + f.famMemberNo)
            }

         sickClient = findViewById(R.id.sickPatient)

        var arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,output)
        sickClient.adapter =arrayAdapter
        sickClient.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                var selectedClient = position
                if (selectedClient == 0){
                    selectedMemberNo = memberInfo.memberNo.toString()
                }else{
                    selectedClient -=1
                    selectedMemberNo = memberInfo.familyMemList?.get(selectedClient)?.famMemberNo.toString()
                    Log.e("####FamilyDependents",selectedMemberNo)
                }

            }

        }


        selectHospital.visibility = View.INVISIBLE

        changeHospital.visibility = View.INVISIBLE

        var changeHos = findViewById<TextView>(R.id.changeHospital)
        changeHos.setOnClickListener {
            val i = Intent(applicationContext, SelectHospital::class.java)
            i.putExtra("hospitalsRv", hospitals as Serializable)
            startActivityForResult(i, AppConstants.COUNTRY_REQUEST)
        }

        sharedpreferences = getSharedPreferences(MyPREFERENCES , Context.MODE_PRIVATE)

        member = sharedpreferences?.getString("member" , "null").toString()
            Log.e("###>>>memberNo",member)

        submitbtn = findViewById(R.id.Submit)
        select_hospital = findViewById(R.id.selectedhospital)
        apiC = APIClient.client?.create(HospitalsAPi::class.java)

        submitbtn?.setOnClickListener(View.OnClickListener {

            if (MerchantID == 0) {
                toast("Please select A Hospital to Continue")
                return@OnClickListener
            }

            if (General.mapsok(this)) {
                //getLocationPermissions();
                //openmaps();
                if (!General.isOnline(this)) {
                    println("Check your connection")
                    return@OnClickListener
                }
                popupshown = false
                if (alertDialog != null) {
                    alertDialog!!.dismiss()
                }

                    getuserlocation()
            Log.e("### GETTING USER LOCA","user location")

                //submit()
            }
        })

        select_hospital?.setOnTouchListener(OnTouchListener { v, event ->
//            Loader.toast(this, "touched " + hospitals.size)
            if (hospitals.size == 0) {
                return@OnTouchListener false
            }

            //if (event.getAction() == MotionEvent.ACTION_UP) {
            val i = Intent(applicationContext, SelectHospital::class.java)
            i.putExtra("hospitalsRv", hospitals as Serializable)
            startActivityForResult(i, AppConstants.COUNTRY_REQUEST)
            // }
            false
        })

        populate_hospitals()

    }


    private fun populate_hospitals() {

        l.showprogress(this@StartVisit,"please wait......")

        try {
            val call: Call<List<HospitalData>> = apiC!!.hospitals
            call.enqueue(object : Callback<List<HospitalData>> {
                override fun onResponse(
                    call: Call<List<HospitalData>>,
                    response: Response<List<HospitalData>>
                ) {
                    l.dismissprogress()
                    if (!response.isSuccessful) {
                        var errorBodyString = ""
                        try {
                            errorBodyString = response.errorBody()!!.string()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        Loader.toastError(applicationContext, errorBodyString)
                        l.dismissprogress()
                        return
                    }
                    hospitals = response.body()!!
                    selectHospital.visibility=View.INVISIBLE
                    select_hospital!!.isFocusable = true
                    select_hospital!!.isFocusableInTouchMode = true
                    changeHospital.visibility = View.VISIBLE
                }

                override fun onFailure(call: Call<List<HospitalData>>, t: Throwable) {
                    Loader.toastError(this@StartVisit, t.message!!)
                    l.dismissprogress()
                }
            })
        } catch (e: Exception) {
            Loader.toast(this@StartVisit, "Error getting hospitals")
            l.dismissprogress()
        }
    }

    private fun getuserlocation() {
        prepare_parameters()
        println("geoman get loc after perm")
        GpsUtils(this).turnGPSOn(object : GpsUtils.onGpsListener {
            override fun gpsStatus(isGPSEnable: Boolean) {
                // turn on GPS
                isGPS = isGPSEnable
            }
        })
        println("geoman passed perm after perm")
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                System.out.println(
                    "geoman called location callback with " + General.getDump(
                        locationResult
                    )
                )
                if (locationResult == null) {
                    Loader.toast(this@StartVisit, "Error getting location ")
                    return
                }
                for (location in locationResult.locations) {
                    if (location != null) {
                        wayLatitude = location.latitude
                        wayLongitude = location.longitude
                        if (!isContinue) {
                            println("geoman inside not continue")
                            storelocation(wayLatitude, wayLongitude)
                        } else {
                            println("geoman inside ambiguous")
                        }
                        if (!isContinue && mFusedLocationClient != null) {
                            println("geoman not continue, client not null")
                            //mFusedLocationClient.removeLocationUpdates(locationCallback);
                            stopLocationUpdates()
                        }
                    }
                }
            }
        }
        if (!isGPS) {
            Loader.toast(this, "Please turn on Location")
            return
        }
        fetchlocation()
    }

    private fun fetchlocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    AppConstants.LOCATION_REQUEST
                )
            } else {
                if (isContinue) {
                    startLocationUpdates()
                } else {
                    mFusedLocationClient!!.lastLocation.addOnSuccessListener(this,
                        OnSuccessListener { location: Location? ->
                            if (location != null) {
                                wayLatitude = location.latitude
                                wayLongitude = location.longitude
                                //txtLocation.setText(String.format(Locale.US, "%s - %s", wayLatitude, wayLongitude));
                                storelocation(wayLatitude, wayLongitude)
                            } else {
                                startLocationUpdates()
                            }
                        })
                }
            }
        } else {
            println("geoman inside lower version ")
            System.out.println("geoman locationRequest " + General.getDump(locationRequest))
            System.out.println("geoman locationCallback " + General.getDump(locationCallback))
            if (isContinue) {
                startLocationUpdates()
            } else {
                println("geoman inside not continue ")
                mFusedLocationClient!!.lastLocation.addOnSuccessListener(this,
                    OnSuccessListener { location: Location? ->
                        println("geoman inside last location ok ")
                        if (location != null) {
                            println("geoman wasnt null ")
                            wayLatitude = location.latitude
                            wayLongitude = location.longitude
                            //txtLocation.setText(String.format(Locale.US, "%s - %s", wayLatitude, wayLongitude));
                            storelocation(wayLatitude, wayLongitude)
                        } else {
                            println("geoman location was null....looking for new ")
                            startLocationUpdates()
                        }
                    })
            }
        }
    }

    //popup
    fun storelocation(lat_: Double, long_: Double) {
        Log.e("###here3","haha")
        validateLocation(lat_,long_)

    }


    fun start_visit() {


        if(!locationValidated){
            return
        }

        if (!popupshown) {
            val mAlertDialogBuilder = AlertDialog.Builder(this)
            mAlertDialogBuilder.setTitle("DO YOU WANT TO START A VISIT?")
            mAlertDialogBuilder.setIcon(R.drawable.lctpng)
            mAlertDialogBuilder.setCancelable(false)
            mAlertDialogBuilder.setPositiveButton("OK") { _, _ ->
                submit()
            }
            mAlertDialogBuilder.setNegativeButton("Cancel") { dialog, negativeButton_ ->
                dialog.dismiss()
            }
            val mAlertDialog = mAlertDialogBuilder.create()
            mAlertDialog.show()
            mAlertDialog.window?.setGravity(Gravity.BOTTOM)

            popupshown = true
        }
    }

    private fun prepare_parameters() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.create()
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest?.interval = 3000 // 10 seconds
        locationRequest?.fastestInterval = 1500 // 5 seconds
        //locationRequest.setInterval(10 * 1000); // 10 seconds
        //locationRequest.setFastestInterval(5 * 1000); // 5 seconds
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        prepare_parameters()
        try {
            println("requesting_ updates")
            mFusedLocationClient!!.requestLocationUpdates(locationRequest, locationCallback, null)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            println("requesting_ updates error $e")
        }
    }

    private fun stopLocationUpdates() {
        if (mFusedLocationClient != null) {
            mFusedLocationClient!!.removeLocationUpdates(locationCallback)
        }
        mFusedLocationClient = null
        locationRequest = null
        locationCallback = null
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            super.onActivityResult(requestCode, resultCode, data)
            select_hospital!!.clearFocus()
            if (resultCode == RESULT_OK) {
                if (requestCode == AppConstants.COUNTRY_REQUEST) {
                    val selectedpos = data!!.getIntExtra("pos", 0)
                    select_hospital!!.text = hospitals[selectedpos].merchantName
                        .toString() + ", " + hospitals[selectedpos].merchantId
                    MerchantID = hospitals[selectedpos].merchantId!!.toInt()
                } else if (requestCode == AppConstants.GPS_REQUEST) {
                    Loader.toast(this, "Location was activated")
                    isGPS = true // flag maintain before get location
                    fetchlocation();
                    stopLocationUpdates()
                    getuserlocation()
                } else {
                    Loader.toast(this, "Unhandled result code " + resultCode);
                }
            }
        } catch (e: java.lang.Exception) {
            // Loader.toast(SignupOne.this, "Error from opened activity " + e.getMessage());
        }
    }

    private fun validateLocation(lat_:Double,long_:Double){
        Log.e("###here","haha")

        if (MerchantID == 0) {
           toast("Please select A Hospital to Continue")
            return
        } else {
           // showSimpleProgressDialog(this@StartVisit , null , "Loading..." , false)
            validate_user_location(MerchantID,lat_,long_)

//            Log.e("###here2","haha")
//            val det =
//                "{\"merchantId\":$MerchantID,\"latitude\":$lat_,\"longitude\":$long_}"// the pay load
//            Log.e("###>>> URL",det)
//            val details = JSONObject(det)
//            println(details)
//
//            try {
//                Fuel.post(
//                    //VALIDATE_LOCATION_URL
//                NGROK_API_
//
//                ).jsonBody(details.toString()).responseJson { request , response , result ->
//                    Log.d("response: " , result.get().content)
//                    onValidation(result.get().content , deciding)
//                }
//            } catch (e: Exception) {
//
//            } finally {
//
//                return
//
//            }
//
//            toast("Your Visit has been Started Successfully.")
        }
    }


    private fun validate_user_location(merchantId:Int,latitude:Double,longitude:Double)
    {
        System.out.println("location_"+latitude)
        System.out.println("location_"+longitude)

        l.showprogress(this@StartVisit,"please wait......")

        try {
            val payload_details = UserLocationData(merchantId,latitude,longitude)
            val call: Call<GeneralResponse> = apiC!!.validate_user_location(payload_details)
            call.enqueue(object : Callback<GeneralResponse> {
                override fun onResponse(
                    call: Call<GeneralResponse>,
                    response: Response<GeneralResponse>
                ) {
                    l.dismissprogress()
                    if (!response.isSuccessful) {
                        var errorBodyString = ""
                        try {
                            errorBodyString = response.errorBody()!!.string()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        Loader.toastError(applicationContext, errorBodyString)
                        l.dismissprogress()
                        return
                    }
                    val resp: GeneralResponse? = response.body()
                    if(resp?.respCode == 200)
                    {
                        locationValidated = true

                        start_visit()
                    }
                    else
                    {
                        Loader.toast(this@StartVisit, "Your hospital visit was not validated. Kindly contact LCT if issue persists")
                    }

                }

                override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                    Loader.toastError(this@StartVisit, t.message!!)
                    l.dismissprogress()
                }
            })
        } catch (e: Exception) {
            Loader.toast(this@StartVisit, "Error validating your hospital visit")
            l.dismissprogress()
        }
    }


    private fun submit(){


       var selectedClient = sickClient.selectedItemPosition
        if (selectedClient == 0){
            selectedMemberNo = memberInfo.memberNo.toString()
        }else{
            selectedClient -=1
            selectedMemberNo = memberInfo.familyMemList?.get(selectedClient)?.famMemberNo.toString()
            Log.e("####FamilyDependents",selectedMemberNo)
        }

        if (MerchantID == 0) {
           toast("Please select A Hospital to Continue")
            return
        } else {
           // showSimpleProgressDialog(this@StartVisit , null , "Loading..." , false)

            start_hospital_visit(MerchantID,selectedMemberNo,AppConstants.START_VISIT_DEVICEID,member)

//            Log.e("###here2","haha")
//            val det =
//                "{\"merchantId\":$MerchantID,\"memberNo\":$selectedMemberNo,\"deviceID\":$mobileApp,\"guardian\":$member}"// the pay load
//            Log.e("###>>> URL",det)
//            val details = JSONObject(det)
//            println(details)
//
//            try {
//                Fuel.post(
//                    //START_VISIT_URL
//                START_VISIT_NGROK
//                ).jsonBody(details.toString()).responseJson { request , response , result ->
//                    Log.d("response: " , result.get().content)
//                    onVisitStarted(result.get().content , Submitting)
//                }
//            } catch (e: Exception) {
//
//            } finally {
//
//                return
//
//            }

            //toast("Your hospital visit has been started successfully.")
        }
    }


    private fun start_hospital_visit(merchantId:Int,memberNo:String,deviceID:String,guardian:String)
    {
        l.showprogress(this@StartVisit,"starting visit......")

        try {
            val payload_details = StartVisitData(merchantId,memberNo,deviceID,guardian)
            val call: Call<GeneralResponse> = apiC!!.start_visit(payload_details)
            call.enqueue(object : Callback<GeneralResponse> {
                override fun onResponse(
                    call: Call<GeneralResponse>,
                    response: Response<GeneralResponse>
                ) {
                    l.dismissprogress()
                    if (!response.isSuccessful) {
                        var errorBodyString = ""
                        try {
                            errorBodyString = response.errorBody()!!.string()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        Loader.toastError(applicationContext, errorBodyString)
                        l.dismissprogress()
                        return
                    }
                    val resp: GeneralResponse? = response.body()
                    if(resp?.respCode == 200)
                    {
                        toast("Your Visit has been Started Successfully.")
                    }
                    else
                    {
                        Loader.toast(this@StartVisit, "Error starting your hospital visit. Kindly contact LCT if issue persists")
                    }

                }

                override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                    Loader.toastError(this@StartVisit, t.message!!)
                    l.dismissprogress()
                }
            })
        } catch (e: Exception) {
            Loader.toast(this@StartVisit, "Error starting your hospital visit")
            l.dismissprogress()
        }
    }

    private fun onVisitStarted(response: String , task: Int) {
        //Loader.toast(context=this,"called")
        Log.d("onTaskCompleted_" , response)
        removeSimpleProgressDialog()  //will remove progress dialog
        when (task) {
            Submitting ->
                if (isSuccess(response)) {

                    JSONObject(response)

                toast("Your Visit has been Started Successfully.")
                    return

            } else if(notSuccesful(response)) {
                Toast.makeText(
                    this@StartVisit,
                    "Please try Again!" ,
                    Toast.LENGTH_LONG
                ).show()
                    return

            }else {
                Toast.makeText(
                    this@StartVisit ,
                    getErrorMessage(response) ,
                    Toast.LENGTH_SHORT
                ).show()
                    return
            }
        }
    }







    //VISIT CLOSED  RESPONSE FUNCTION

    private fun onValidation(response: String , task: Int) {
        //Loader.toast(context=this,"called")
        Log.d("onTaskCompleted_" , response)
        //removeSimpleProgressDialog()  //will remove progress dialog
        when (task) {
            deciding ->
                if (isSuccess(response)) {

                    locationValidated = true

                    start_visit()

                    JSONObject(response)

                    toast("Successful")
                    return

                } else if(notSuccesful(response)) {
                    Toast.makeText(
                        this@StartVisit,
                        "Please try Again!" ,
                        Toast.LENGTH_LONG
                    ).show()
                    return

                }else {
                    Toast.makeText(
                        this@StartVisit,
                        getErrorMessage(response) ,
                        Toast.LENGTH_SHORT

                    ).show()
                    return
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

        return true
    }

    private fun notSuccesful(response: String):Boolean{
        try {
            val jsonObject = JSONObject(response)
            return jsonObject.optString("respCode") == "201"

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return true
    }

    fun getErrorMessage(response: String): String {
        try {
            val jsonObject = JSONObject(response)
            return jsonObject.getString("message")
        } catch (e: JSONException) {
            e.printStackTrace()
            return "An unexpected error occurred."
        }finally {
            toast("please try again.")
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

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            AppConstants.LOCATION_REQUEST -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    //fetchlocation();
                    stopLocationUpdates()
                    getuserlocation()
                } else {
                    Loader.toast(this@StartVisit, "Permission denied, Please try again.")
                }
            }
        }
    }

}

