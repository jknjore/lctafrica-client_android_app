
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
import com.lctapp.lct.Classes.Api.HospitalsAPi
import com.lctapp.lct.Classes.Constants.APIClient
import com.lctapp.lct.Classes.Helpers.Loader
import com.lctapp.lct.Classes.MemberData
import com.lctapp.lct.Classes.Models.MemberClaims.MemberClaims
import com.lctapp.lct.Classes.Models.Member.MemberDetails
import com.lctapp.lct.Classes.Models.Payloads.Member
import com.lctapp.lct.Classes.Variables
import com.lctapp.lct.Classes.utills.toast
import com.lctapp.lct.R
import kotlinx.android.synthetic.main.activity_member.*
import kotlinx.android.synthetic.main.activity_member_registration.*
import org.imaginativeworld.oopsnointernet.ConnectionCallback
import org.imaginativeworld.oopsnointernet.NoInternetDialog
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class MemberActivity : AppCompatActivity() {

    var member : String = ""
    var fetchURL: String = ""
    val MyPREFERENCES = "MyPrefs"
    var sharedpreferences: SharedPreferences? = null
    // No Internet Dialog
    private var noInternetDialog: NoInternetDialog? = null
    var l: Loader = Loader
    private var apiC: HospitalsAPi? = null

    //private var preferenceHelper: PreferenceHelper? = null
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member)
        getActionBar()?.setDisplayHomeAsUpEnabled(true);
        setTitle("Member Details")

        Variables.setDependantChecker(0)

        apiC = APIClient.client?.create(HospitalsAPi::class.java)


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



        private fun bounce(){

//        val card = findViewById<CardView>(R.id.card_view)
//        var animation= AnimationUtils.loadAnimation(this@MemberActivity, R.anim.top)
//        card.startAnimation(animation)
//
//        val car = findViewById<CardView>(R.id.cardView)
//         animation= AnimationUtils.loadAnimation(this@MemberActivity, R.anim.top)
//        car.startAnimation(animation)
//
//        val cad = findViewById<CardView>(R.id.cardview)
//        animation= AnimationUtils.loadAnimation(this@MemberActivity, R.anim.top)
//        cad.startAnimation(animation)
//
//        val view= findViewById<CardView>(R.id.cardviewfam)
//        animation= AnimationUtils.loadAnimation(this@MemberActivity, R.anim.top)
//        view.startAnimation(animation)
//
//        val viw = findViewById<CardView>(R.id.cardviewPayer)
//        animation= AnimationUtils.loadAnimation(this@MemberActivity, R.anim.top)
//        viw.startAnimation(animation)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun Search() {
        get_member_claims(member)
//        showSimpleProgressDialog(this@MemberActivity , null , "fetching..." , false)
//        try {
//
//            Fuel.get(fetchURL).responseJson { request , response , result ->
//                Log.e("response: " , result.get().content)
//                onTaskCompleted(result.get().content)
//            }
//        } catch (e: Exception) {
//            Log.e("Exception", e.toString())
//        }
    }


    private fun get_member_claims(memberNo:String)
    {
        l.showprogress(this@MemberActivity,"please wait......")
        try {
            val payload_details = Member(memberNo)
            val call: Call<MemberClaims> = apiC!!.get_member_claims(memberNo)
            call.enqueue(object : Callback<MemberClaims> {
                override fun onResponse(
                    call: Call<MemberClaims>,
                    response: Response<MemberClaims>
                ) {
                    l.dismissprogress()
                    if (!response.isSuccessful) {
                        var errorBodyString = ""
                        try {
                            errorBodyString = response.errorBody()!!.string()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        Loader.toastError(this@MemberActivity, errorBodyString)
                        l.dismissprogress()
                        return
                    }
                    val resp: MemberClaims? = response.body()
                    if(resp!!.getMemberId() != "0")
                    {
                        populate_parameters(resp)
                    }
                    else
                    {
                        Loader.toast(this@MemberActivity, "An error occurred getting member details")
                    }

                }

                override fun onFailure(call: Call<MemberClaims>, t: Throwable) {
                    Loader.toastError(this@MemberActivity, t.message!!)
                    l.dismissprogress()
                }
            })
        } catch (e: Exception) {
            Loader.toast(this@MemberActivity, "Error getting member details")
            l.dismissprogress()
        }
    }


    private fun populate_parameters(resp: MemberClaims)
    {


        txtName.text = resp.getFullName()

        txtDateofBirth.text = resp.getDateOfBirth()

        var gender = resp.getGender()

        if (gender == "M") {
            gender = "MALE"
        }

        if (gender == "F"){
            gender = "FEMALE"
        }

        txtGender.text = gender

        var type = resp.getMemberType()


        if (type== "P"){
            type = "Principal"
            if (type =="D"){
                type = "Dependant"
            }
        }
        txtType.text = type


        Scheme.text=resp.getScheme()

        txtStatus.text=resp.getOutpatientStatus()


        txtProg.text = resp.getProgrammes().get(0).getProgrammeDesc()

        txtVoucher.text = resp.getProgrammes().get(0).getMembervouchers().get(0).getVoucherDesc()


        txtValue.text = resp.getProgrammes().get(0).getMembervouchers().get(0).getServices().get(0).getPrice().toString()


        txtPayer.text = resp.getPayer()


        if(resp.getFamilyMemList().size > 0)
        {
            txtspouse.text = resp.getFamilyMemList().get(0).getFamMemFullName()
            spouse.text = resp.getFamilyMemList().get(0).getFamMemberNo()
            spouse.setOnClickListener {
                Variables.setDependantNumber(spouse.text.toString())
                Variables.setDependantChecker(1)
                // intent
                val intent = Intent(this, Profile::class.java)
                startActivity(intent)
            }
        }



        if(resp.getFamilyMemList().size > 1) {
            txtchild.text = resp.getFamilyMemList().get(1).getFamMemFullName()
            child.text = resp.getFamilyMemList().get(1).getFamMemberNo()
            child.setOnClickListener {
                Variables.setDependantNumber(child.text.toString())
                Variables.setDependantChecker(1)
                //intent
                val intent = Intent(this, Profile::class.java)
                startActivity(intent)
            }
        }

        if(resp.getFamilyMemList().size > 2) {
            Child.text = resp.getFamilyMemList().get(2).getFamMemFullName()
            baby.text = resp.getFamilyMemList().get(2).getFamMemberNo()
            baby.setOnClickListener {
                Variables.setDependantNumber(baby.text.toString())
                Variables.setDependantChecker(1)
                //intent
                val intent = Intent(this, Profile::class.java)
                startActivity(intent)

            }
        }

        if(resp.getFamilyMemList().size > 3) {
            aChild.text = resp.getFamilyMemList().get(3).getFamMemFullName()
            youth.text = resp.getFamilyMemList().get(3).getFamMemberNo()
            youth.setOnClickListener {
                Variables.setDependantNumber(youth.text.toString())
                Variables.setDependantChecker(1)
                //intent
                val intent = Intent(this, Profile::class.java)
                startActivity(intent)
            }
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