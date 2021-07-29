
package com.lctapp.lct.Activities


import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.google.gson.Gson
import com.lctapp.lct.Classes.MemberData
import com.lctapp.lct.Classes.Variables
import com.lctapp.lct.Classes.utills.toast
import com.lctapp.lct.R
import kotlinx.android.synthetic.main.activity_member.*
import kotlinx.android.synthetic.main.activity_member_registration.*
import org.imaginativeworld.oopsnointernet.ConnectionCallback
import org.imaginativeworld.oopsnointernet.NoInternetDialog
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber


class MemberActivity : AppCompatActivity() {

    var member : String = ""
    var fetchURL: String = ""
    val MyPREFERENCES = "MyPrefs"
    var sharedpreferences: SharedPreferences? = null
    // No Internet Dialog
    private var noInternetDialog: NoInternetDialog? = null

    //private var preferenceHelper: PreferenceHelper? = null
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member)
        Variables.setDependantChecker(0)




        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE)
        member = sharedpreferences?.getString("member","null").toString()
        fetchURL =
            //"http://34.123.154.111:8085/compas/rest/member/gtEclaimMembers?memberNo=$member"
            //"https://staging.lctafrica.io/compas/rest/member/gtEclaimMembers?memberNo="+member
            "http://35.241.171.182:8085/compas/rest/member/gtEclaimMembers?memberNo=$member"
            //"https://lctafrica.io/compas/rest/member/gtEclaimMembers?memberNo="+member
        Log.e("URL>>", fetchURL)
        bounce()
        Search()
    }

    override fun onResume() {
        super.onResume()

        // No Internet Dialog"
        noInternetDialog = NoInternetDialog.Builder(this)
            .apply {
                connectionCallback = object : ConnectionCallback { // Optional
                    override fun hasActiveConnection(hasActiveConnection: Boolean) {
                        // ...
                    }
                }
                cancelable = false // Optional
                noInternetConnectionTitle = "No Internet" // Optional
                noInternetConnectionMessage =
                    "Check your Internet connection and try again." // Optional
                showInternetOnButtons = true // Optional
                pleaseTurnOnText = "Please turn on" // Optional
                wifiOnButtonText = "Wifi" // Optional
                mobileDataOnButtonText = "Mobile data" // Optional

                onAirplaneModeTitle = "No Internet" // Optional
                onAirplaneModeMessage = "You have turned on the airplane mode." // Optional
                pleaseTurnOffText = "Please turn off" // Optional
                airplaneModeOffButtonText = "Airplane mode" // Optional
                showAirplaneModeOffButtons = true // Optional
            }
            .build()
        return
    }

    override fun onPause() {
        super.onPause()

        // No Internet Dialog
        noInternetDialog?.destroy()

    }

        private fun bounce(){

        val card = findViewById<CardView>(R.id.card_view)
        var animation= AnimationUtils.loadAnimation(this@MemberActivity, R.anim.top)
        card.startAnimation(animation)

        val car = findViewById<CardView>(R.id.cardView)
         animation= AnimationUtils.loadAnimation(this@MemberActivity, R.anim.top)
        car.startAnimation(animation)

        val cad = findViewById<CardView>(R.id.cardview)
        animation= AnimationUtils.loadAnimation(this@MemberActivity, R.anim.top)
        cad.startAnimation(animation)

        val view= findViewById<CardView>(R.id.cardviewfam)
        animation= AnimationUtils.loadAnimation(this@MemberActivity, R.anim.top)
        view.startAnimation(animation)

        val viw = findViewById<CardView>(R.id.cardviewPayer)
        animation= AnimationUtils.loadAnimation(this@MemberActivity, R.anim.top)
        viw.startAnimation(animation)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun Search() {
        showSimpleProgressDialog(this@MemberActivity , null , "fetching..." , false)
        try {

            Fuel.get(fetchURL).responseJson { request , response , result ->
                Log.e("response: " , result.get().content)
                onTaskCompleted(result.get().content)
            }
        } catch (e: Exception) {
            Log.e("Exception", e.toString())
        }
    }
    private fun onTaskCompleted(response: String) {
        Log.e("responsejson" , response)
        removeSimpleProgressDialog()  //will remove progress dialog
        Log.e ("check>>: " , "one")
        try {
            Log.e("CHECK>>: " , "two")

            val jsonObject = JSONObject(response)

            Log.e("JSON>>: " , jsonObject.toString())

            txtName.text = jsonObject.getString("fullName")

            txtDateofBirth.text = jsonObject.getString("dateOfBirth")

            var gender = jsonObject.optString("gender")

            if (gender == "M") {
                gender = "MALE"
            }

                if (gender == "F"){
                    gender = "FEMALE"
                }
                Log.e("###SEX",gender.toString())

            txtGender.text = gender

            var type = jsonObject.optString("memberType")


            if (type== "P"){
                type = "Principal"
                if (type =="D"){
                    type = "Dependant"
                }
                Log.e("###type",type.toString())
            }
            txtType.text = type


            Scheme.text=jsonObject.optString("scheme")

            txtStatus.text=jsonObject.optString("outpatientStatus")


            val programmes: JSONArray = jsonObject.getJSONArray("programmes")
            val programmesObj = programmes.getJSONObject(0)
            txtProg.text = programmesObj.getString("programmeDesc")

            val membervouchers : JSONArray = programmesObj.getJSONArray("membervouchers")
            val membervouchersObj = membervouchers.getJSONObject(0)

            System.out.println("PROGRAMME >>>    " + jsonObject.optString("membervouchers"))
            txtVoucher.text = membervouchersObj.getString("voucherDesc")

            val services: JSONArray = membervouchersObj.getJSONArray("services")
            val servicesObj = services.getJSONObject(0)

            txtValue.text = servicesObj.getString("price")

            Log.e("Value: " , servicesObj.getString("price"))

            val jsonObject1 = JSONObject(response)

            Log.e("JSON>>: " , jsonObject1.toString())

            txtPayer.text = jsonObject1.getString("payer")

            val familyMemList: JSONArray =  jsonObject1 .getJSONArray("familyMemList")
            val familyMemListObj = familyMemList.getJSONObject(0)

            txtspouse.text = familyMemListObj.getString("famMemFullName")
            spouse.text = familyMemListObj.getString("famMemberNo")
            spouse.setOnClickListener {
                Variables.setDependantNumber(spouse.text.toString())
                Log.e("##### ", "Dependant Number : Variables : " +Variables.getDependantNumber())
                Variables.setDependantChecker(1)
                // intent
                val intent = Intent(this, Profile::class.java)
                startActivity(intent)


            }

            Log.e("spouse", familyMemListObj.getString("famMemFullName"))
            Log.e("###memberNo",familyMemListObj.getString("famMemberNo"))

            val familyMemList1: JSONArray =   jsonObject1.getJSONArray("familyMemList")
            val familyMemList1Obj = familyMemList1.getJSONObject(1)

            txtchild.text= familyMemList1Obj.getString("famMemFullName")
            child.text= familyMemList1Obj.getString("famMemberNo")
            child.setOnClickListener {
                Variables.setDependantNumber(child.text.toString())
                Log.e("##### ", "Dependant Number : Variables : " +Variables.getDependantNumber())
                Variables.setDependantChecker(1)
                //intent
                val intent = Intent(this, Profile::class.java)
                startActivity(intent)
            }

            Log.e("txtchild", familyMemList1Obj.getString("famMemFullName"))
            Log.e("###memberNo",familyMemList1Obj.getString("famMemberNo"))

            val familyMemList2: JSONArray =  jsonObject1.getJSONArray("familyMemList")
            val familyMemList2Obj = familyMemList2.getJSONObject(2)

            Child.text= familyMemList2Obj.getString("famMemFullName")

            baby.text= familyMemList2Obj.getString("famMemberNo")
            baby.setOnClickListener {
                Variables.setDependantNumber(baby.text.toString())
                Log.e("##### ", "Dependant Number : Variables : " +Variables.getDependantNumber())
                Variables.setDependantChecker(1)
                //intent
                val intent = Intent(this, Profile::class.java)
                startActivity(intent)

            }

            Log.e("familyMemList2", familyMemList2Obj.getString("famMemFullName"))
            Log.e("###memberNo",familyMemList2Obj.getString("famMemberNo"))

            val familyMemList3: JSONArray =  jsonObject1 .getJSONArray("familyMemList")
            val familyMemList3Obj = familyMemList3.getJSONObject(3)

            aChild.text= familyMemList3Obj.getString("famMemFullName")
            youth.text= familyMemList3Obj.getString("famMemberNo")
            youth.setOnClickListener {
                Variables.setDependantNumber(youth.text.toString())
                Log.e("##### ", "Dependant Number : Variables : " +Variables.getDependantNumber())
                Variables.setDependantChecker(1)
                //intent
                val intent = Intent(this, Profile::class.java)
                startActivity(intent)
            }

            Log.e("familyMemList3", familyMemList3Obj.getString("famMemFullName"))
            Log.e("###memberNo",familyMemList3Obj.getString("famMemberNo"))


        } catch (e : java.lang.Exception) {
            Log.e("##EXE >>" , e.toString())
        }

        Log.e("##CHECK >>" , "three")
    }

    fun showSimpleProgressDialog(
        context: Context ,
        title: String? ,
        msg: String ,
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

}