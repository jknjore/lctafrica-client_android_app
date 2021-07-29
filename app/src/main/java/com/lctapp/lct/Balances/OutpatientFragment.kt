package com.lctapp.lct.Balances

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.lctapp.lct.Classes.Api.HospitalsAPi
import com.lctapp.lct.Classes.Constants.APIClient
import com.lctapp.lct.Classes.Helpers.Loader
import com.lctapp.lct.Classes.Models.Member.MemberDetails
import com.lctapp.lct.Classes.Models.Member.MemberProgramme
import com.lctapp.lct.Classes.Models.Payloads.Member
import com.lctapp.lct.R
import kotlinx.android.synthetic.main.fragment_inpatient.*
import kotlinx.android.synthetic.main.fragment_outpatient.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OutpatientFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OutpatientFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var l: Loader = Loader
    private lateinit var apiC: HospitalsAPi
    var member_data: MemberDetails? = null

    var fetchURL: String = //"http://34.123.154.111:8085/compas/rest/user/downloadMember"
        //"https://staging.lctafrica.io/compas/rest/user/downloadMember"
        "http://35.241.171.182:8085/compas/rest/user/downloadMember"
         //"https://lctafrica.io/compas/rest/user/downloadMember"

    private val FetchTask = 1

    private var mProgressDialog: ProgressDialog? = null
    val MyPREFERENCES = "MyPrefs"
    var sharedpreferences: SharedPreferences? = null
    var member : String= ""
    // Class Definitions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        // OnCreate
        sharedpreferences = requireActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE)
        apiC = APIClient.client!!.create(HospitalsAPi::class.java)
        Search()
    }

    override fun onResume() {
        super.onResume()
        sharedpreferences = requireActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE)
        apiC = APIClient.client!!.create(HospitalsAPi::class.java)

        //Search()
        if(member_data != null) {
            populate_parameters(member_data!!)
        }
    }

    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_outpatient, container, false)


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OutpatientFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OutpatientFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    // Functions

    private fun Search() {
        member = sharedpreferences?.getString("member","null").toString()
        //showSimpleProgressDialog(this, null, "fetching...", false)
        get_member_details(member)
//        val det =
//            "{\"memberNo\":" + member + "}"// the pay load
//
//        val details = JSONObject(det)
//
//        System.out.println("demo" + details)
//
//        try {
//
//            Fuel.post(
//                fetchURL
//            ).jsonBody(details.toString()).responseJson { request , response , result ->
//                Log.d("response: " , result.get().content)
//                onTaskCompleted(result.get().content , FetchTask)
//            }
//        } catch (e: Exception) {
//
//        } finally {
//        }

    }



    private fun get_member_details(memberNo:String)
    {
        l.showprogress(getActivity(),"please wait......")
        try {
            val payload_details = Member(memberNo)
            val call: Call<MemberDetails> = apiC!!.get_member_details(payload_details)
            call.enqueue(object : Callback<MemberDetails> {
                override fun onResponse(
                    call: Call<MemberDetails>,
                    response: Response<MemberDetails>
                ) {
                    l.dismissprogress()
                    if (!response.isSuccessful) {
                        var errorBodyString = ""
                        try {
                            errorBodyString = response.errorBody()!!.string()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        Loader.toastError(requireActivity(), errorBodyString)
                        l.dismissprogress()
                        return
                    }
                    val resp: MemberDetails? = response.body()
                    if(resp?.respCode == 200)
                    {
                        member_data=resp
                        populate_parameters(resp)
                    }
                    else
                    {
                        Loader.toast(requireActivity(), "An error occurred getting member details")
                    }

                }

                override fun onFailure(call: Call<MemberDetails>, t: Throwable) {
                    Loader.toastError(requireActivity(), t.message!!)
                    l.dismissprogress()
                }
            })
        } catch (e: Exception) {
            Loader.toast(requireActivity(), "Error getting member details")
            l.dismissprogress()
        }
    }


    private fun populate_parameters(resp: MemberDetails)
    {

//=============================================================================================================

        val limit:Double = resp.getMemberProgrammes().get(0).getVouchers().get(0).getUsed().toDouble()
        val vLimit:String = String.format("%,.2f",limit)

        txtLimit.text=vLimit

        val amount:Double = resp.getMemberProgrammes().get(0).getVouchers().get(0).getVoucherValue().toDouble()
        val vAmount:String = String.format("%,.2f",amount)

        txtAmount.text = vAmount

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        val patient:Double = resp.getMemberProgrammes().get(0).getVouchers().get(0).getServices().get(0).getServiceValue().toDouble()
        val vPatient:String = String.format("%,.2f",patient)

        txtoutPatient.text = vPatient

        val expediture:Double = resp.getMemberProgrammes().get(0).getVouchers().get(1).getVoucherValue().toDouble()
        val vExpediture:String = String.format("%,.2f",expediture)

        txtExpediture.text = vExpediture

        val expense:Double = resp.getMemberProgrammes().get(0).getVouchers().get(1).getUsed().toDouble()
        val vExpense:String = String.format("%,.2f",expense)

        txtExpense.text =vExpense


        val dental:Double = resp.getMemberProgrammes().get(0).getVouchers().get(1).getServices().get(0).getServiceBalance().toDouble()
        val vDental:String = String.format("%,.2f",dental)

        txtoutDental.text = vDental


        val voucObject3 =  resp.getMemberProgrammes().get(0).getVouchers().get(2)


        val balance:Double =  resp.getMemberProgrammes().get(0).getVouchers().get(2).getVoucherValue().toDouble()
        val vBalance:String = String.format("%,.2f",balance)

        txtBalance.text = vBalance

        val ibalance:Double =  resp.getMemberProgrammes().get(0).getVouchers().get(2).getVoucherBalance().toDouble()
        val vIbalance:String = String.format("%,.2f",ibalance)

        txttBalance.text = vIbalance


        val estimate:Double =  resp.getMemberProgrammes().get(0).getVouchers().get(2).getUsed().toDouble()
        val vEstimate:String = String.format("%,.2f",estimate)

        txtEstimate.text = vEstimate


        val optical:Double = resp.getMemberProgrammes().get(0).getVouchers().get(2).getServices().get(0).getServiceBalance().toDouble()
        val vOptical:String = String.format("%,.2f",optical)

        txtoutOptical.text = vOptical

        val iBalance:Double = resp.getMemberProgrammes().get(0).getVouchers().get(2).getVoucherValue().toDouble()
        val viBalance:String = String.format("%,.2f",iBalance)

        txtBalance.text = viBalance


        // this Where the Logic for OutPatient HealthCheckup Balance is.

        val tOut:Double = resp.getMemberProgrammes().get(0).getVouchers().get(0).getServices().get(1).getServiceValue().toDouble()
        Log.e("###HealthCheckup>>>",tOut.toString())

        val vTout:String = String.format("%,.2f",tOut)
        Log.e("###OutPatientHealth>>>",vTout)

        txttoutO.text = vTout
        Log.e("###HealthCheckUpBal>>>",vTout)

        val Estimate:Double = resp.getMemberProgrammes().get(0).getVouchers().get(0).getServices().get(1).getUsed().toDouble()
        Log.e("###CheckupUsed>>>",Estimate.toString())

        val ttEstimate:String = String.format("%,.2f",Estimate)
        Log.e("###CheckupUsed",ttEstimate.toString())

        txttEstimate.text = ttEstimate
        Log.e("###CheckupUsed",ttEstimate)


        val ttBalance:Double = resp.getMemberProgrammes().get(0).getVouchers().get(0).getServices().get(1).getServiceValue().toDouble()
        val vTtBalance:String = String.format("%,.2f",ttBalance)

        txttBalance.text = vTtBalance
    }




    private fun onTaskCompleted(response: String, task: Int) {
        Log.d("responsejson", response)
        removeSimpleProgressDialog()  //will remove progress dialog
        when (task) {
            FetchTask -> if (isSuccess(response)) {
                //saveInfo(response)

                val jsonObject = JSONObject(response)

                val arr: JSONArray = jsonObject.getJSONArray("memberProgrammes")
                val arrObj = arr.getJSONObject(0)

//=============================================================================================================
                val vouc: JSONArray = arrObj.getJSONArray("vouchers")
                val voucObject1 = vouc.getJSONObject(0)

                val limit:Double = voucObject1.optString("used").toDouble()
                val vLimit:String = String.format("%,.2f",limit)

                txtLimit.text=vLimit

                val serv: JSONArray = voucObject1.getJSONArray("services")
                val servObject1 = serv.getJSONObject(0)

                val amount:Double = voucObject1.optString("voucherValue").toDouble()
                val vAmount:String = String.format("%,.2f",amount)

                txtAmount.text = vAmount

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                val patient:Double = servObject1.optString("serviceBalance").toDouble()
                val vPatient:String = String.format("%,.2f",patient)

                txtoutPatient.text = vPatient

                val voucObject2 = vouc.getJSONObject(1)

                val expediture:Double = voucObject2.optString("voucherValue").toDouble()
                val vExpediture:String = String.format("%,.2f",expediture)

                txtExpediture.text = vExpediture

                val expense:Double = voucObject2.optString("used").toDouble()
                val vExpense:String = String.format("%,.2f",expense)

                txtExpense.text =vExpense

                val serv2: JSONArray = voucObject2.getJSONArray("services")
                val servObject2 = serv2.getJSONObject(0)

                val dental:Double = servObject2.optString("serviceBalance").toDouble()
                val vDental:String = String.format("%,.2f",dental)

                txtoutDental.text = vDental


                val voucObject3 = vouc.getJSONObject(2)


                val balance:Double =  voucObject3.optString("voucherValue").toDouble()
                val vBalance:String = String.format("%,.2f",balance)

                txtBalance.text = vBalance

                val ibalance:Double =  voucObject3.optString("voucherBalance").toDouble()
                val vIbalance:String = String.format("%,.2f",ibalance)

                txttBalance.text = vIbalance


                val estimate:Double =  voucObject3.optString("used").toDouble()
                val vEstimate:String = String.format("%,.2f",estimate)

                txtEstimate.text = vEstimate


                val serv3: JSONArray = voucObject3.getJSONArray("services")
                val servObject3 = serv3.getJSONObject(0)


                val optical:Double = servObject3.optString("serviceBalance").toDouble()
                val vOptical:String = String.format("%,.2f",optical)

                txtoutOptical.text = vOptical

                val iBalance:Double = voucObject3.optString("voucherValue").toDouble()
                val viBalance:String = String.format("%,.2f",iBalance)

                txtBalance.text = viBalance


                val voucObject4 =vouc.getJSONObject(0)

                // this Where the Logic for OutPatient HealthCheckup Balance is.
                val  servs: JSONArray = voucObject4.getJSONArray("services")
                val servobjectS = servs.getJSONObject(1)


                val tOut:Double = servobjectS.optString("serviceValue").toDouble()
                Log.e("###HealthCheckup>>>",tOut.toString())

                val vTout:String = String.format("%,.2f",tOut)
                Log.e("###OutPatientHealth>>>",vTout)

                txttoutO.text = vTout
                Log.e("###HealthCheckUpBal>>>",vTout)

                val Estimate:Double = servobjectS.optString("used").toDouble()
                Log.e("###CheckupUsed>>>",Estimate.toString())

                val ttEstimate:String = String.format("%,.2f",Estimate)
                Log.e("###CheckupUsed",ttEstimate.toString())

                txttEstimate.text = ttEstimate
                Log.e("###CheckupUsed",ttEstimate)


                val  serv4: JSONArray = voucObject4.getJSONArray("services")
                val servobject4 = serv4.getJSONObject(1)

                val ttBalance:Double = servobject4.optString("serviceValue").toDouble()
                val vTtBalance:String = String.format("%,.2f",ttBalance)

                txttBalance.text = vTtBalance

            } else {
                Toast.makeText(requireContext(), getErrorMessage(response), Toast.LENGTH_SHORT)
                    .show()
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

    fun getErrorMessage(response: String): String {
        try {
            val jsonObject = JSONObject(response)
            return jsonObject.getString("message")

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return "No data"
    }

    fun showSimpleProgressDialog(
        context: OutpatientFragment,
        title: String?,
        msg: String,
        isCancelable: Boolean
    ) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(requireContext(), title, msg)
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
