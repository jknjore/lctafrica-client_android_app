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
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.lctapp.lct.Classes.Api.HospitalsAPi
import com.lctapp.lct.Classes.Constants.APIClient
import com.lctapp.lct.Classes.Helpers.Loader
import com.lctapp.lct.Classes.Models.Member.MemberDetails
import com.lctapp.lct.Classes.Models.Member.MemberProgramme
import com.lctapp.lct.Classes.Models.Payloads.Member
import com.lctapp.lct.R
import kotlinx.android.synthetic.main.fragment_complementary.*
import kotlinx.android.synthetic.main.fragment_inpatient.*
import kotlinx.android.synthetic.main.fragment_inpatient.txtiBalance
import kotlinx.android.synthetic.main.fragment_inpatient.txtiEstimate
import kotlinx.android.synthetic.main.fragment_inpatient.txtioutO
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
 * Use the [InpatientFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InpatientFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var l: Loader = Loader
    private var apiC: HospitalsAPi? = null
    var member_data: MemberDetails? = null

    var fetchURL: String =//"http://34.123.154.111:8085/compas/rest/user/downloadMember"
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
        apiC = APIClient.client?.create(HospitalsAPi::class.java)
        Search()
    }

    override fun onResume() {
        super.onResume()
        sharedpreferences = requireActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE)
        apiC = APIClient.client?.create(HospitalsAPi::class.java)
        //Search()
        if(member_data != null) {
            populate_parameters(member_data!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inpatient, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InpatientFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InpatientFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    // Functions

    private fun Search() {
        member = sharedpreferences?.getString("member","null").toString()
        //showSimpleProgressDialog(this,null, "fetching...", false)
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
        //l.showprogress(getActivity(),"please wait......")
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
        val arrObj:MemberProgramme =  resp.getMemberProgrammes().get(0)

//===============================================================================================================================================================
        val limit: Double = arrObj.getVouchers().get(4).getUsed().toDouble()
        var vLimit : String = String.format("%,.2f",limit)

        txtInLimit.text= vLimit

        // add comer to the value of balances
        var vtemps : Double = arrObj.getVouchers().get(4).getVoucherValue().toDouble()
        var vtemps2: String = String.format("%,.2f", vtemps)

        txtInAmount.text = vtemps2

        val patient:Double = arrObj.getVouchers().get(4).getServices().get(0).getServiceValue().toDouble()
        val vPatient:String = String.format("%,.2f",patient)

        txtInPatient.text = vPatient

        val voucObject2 = arrObj.getVouchers().get(4)

        val expense:Double = arrObj.getVouchers().get(4).getUsed().toDouble()
        val vExpense:String = String.format("%,.2f",expense)

        txtInExpense.text =vExpense

        //val hospitalization:Double = voucObject2.optString("voucherBalance").toDouble()

// here is where we have the balance For PostHospitalization being displayed
        val servObjects = arrObj.getVouchers().get(4).getServices().get(1)

        val hospitalization:Double =  arrObj.getVouchers().get(4).getServices().get(1).getServiceValue().toDouble()- arrObj.getVouchers().get(4).getUsed().toDouble()

        val vHospitalization:String = String.format("%,.2f",hospitalization)

        txtInhospitalization.text = vHospitalization
//this is where it ends , use the same logic amigo

        val servObject2 = arrObj.getVouchers().get(4).getServices().get(1)

        val expediture:Double =arrObj.getVouchers().get(4).getServices().get(1).getServiceValue().toDouble()
        var vExpediture:String = String.format("%,.2f",expediture)

        txtInExpediture.text = vExpediture


        val voucObject3 = arrObj.getVouchers().get(4)

        val estimate:Double = arrObj.getVouchers().get(4).getUsed().toDouble()
        val vEstimate:String = String.format("%,.2f",estimate)

        txtInEstimate.text = vEstimate

// this where we have Logic for Preexisting Balances.

        val servObjectS = arrObj.getVouchers().get(4).getServices().get(2)

        val preexisting:Double = arrObj.getVouchers().get(4).getServices().get(2).getServiceValue().toDouble() - arrObj.getVouchers().get(4).getUsed().toDouble()

        val vPreexisting:String = String.format("%,.2f",preexisting)

        txtInPreexisting.text = vPreexisting

// this is where the logic InPatient Preexisting balance comes to an end.

        val servObject3 = arrObj.getVouchers().get(4).getServices().get(2)

        val balance:Double = arrObj.getVouchers().get(4).getServices().get(2).getServiceValue().toDouble()
        val vBalance:String = String.format("%,.2f",balance)

        txtInBalance.text = vBalance


        val voucObject5 =arrObj.getVouchers().get(4)
// this where the InPatient Congenital/ChildBirth Balance Logic.

        val servObject = arrObj.getVouchers().get(4).getServices().get(3)

        val ioutO:Double = arrObj.getVouchers().get(4).getServices().get(3).getServiceValue().toDouble() - arrObj.getVouchers().get(4).getUsed().toDouble()

        val vIoutO:String = String.format("%,.2f",ioutO)

        txtioutO.text =vIoutO

//this is where the Logic for InPatient CongenitalChildBirth balance Ends.

        val iestimate:Double = arrObj.getVouchers().get(4).getUsed().toDouble()
        val vIestimate:String = String.format("%,.2f",iestimate)

        txtiEstimate.text = vIestimate

        val servobject5 = arrObj.getVouchers().get(4).getServices().get(3)

        val iBalance:Double = arrObj.getVouchers().get(4).getServices().get(3).getServiceValue().toDouble()
        val vIbalance:String = String.format("%,.2f",iBalance)

        txtiBalance.text = vIbalance
    }





    private fun onTaskCompleted(response: String, task: Int) {
        Log.d("responsejson", response)
        removeSimpleProgressDialog()  //will remove progress dialog
        when (task) {
            FetchTask -> if (isSuccess(response)) {
                //saveInfo(response)

                val jsonObject =  JSONObject(response)

                val arr: JSONArray = jsonObject.getJSONArray("memberProgrammes")
                val arrObj = arr.getJSONObject(0)

//===============================================================================================================================================================
                val vouc: JSONArray = arrObj.getJSONArray("vouchers")
                val voucObject1 = vouc.getJSONObject(4)

                val limit: Double = voucObject1.optString("used").toDouble()
                var vLimit : String = String.format("%,.2f",limit)

                txtInLimit.text= vLimit

                // add comer to the value of balances
                var vtemps : Double = voucObject1.optString("voucherValue").toDouble()
                Log.e("#### ", "vTemps varue >>>  $vtemps")
                var vtemps2: String = String.format("%,.2f", vtemps)

                txtInAmount.text = vtemps2

                val serv: JSONArray = voucObject1.getJSONArray("services")
                val servObject1 = serv.getJSONObject(0)

                val patient:Double = servObject1.optString("serviceBalance").toDouble()
                val vPatient:String = String.format("%,.2f",patient)

                txtInPatient.text = vPatient

                val voucObject2 = vouc.getJSONObject(4)

                val expense:Double = voucObject2.optString("used").toDouble()
                val vExpense:String = String.format("%,.2f",expense)

                txtInExpense.text =vExpense

                //val hospitalization:Double = voucObject2.optString("voucherBalance").toDouble()

// here is where we have the balance For PostHospitalization being displayed
                val servs: JSONArray = voucObject2.getJSONArray("services")
                val servObjects = servs.getJSONObject(1)

                val hospitalization:Double =  servObjects.optString("serviceValue").toDouble()- voucObject2.optString("used").toDouble()
                Log.e("###HospitalizationLogic",hospitalization.toString())
                val vHospitalization:String = String.format("%,.2f",hospitalization)
                Log.e("###PostHospitalization>",vHospitalization)

                txtInhospitalization.text = vHospitalization
                Log.e("###PostHosBalance>>", vHospitalization)
//this is where it ends , use the same logic amigo

                val serv2: JSONArray = voucObject2.getJSONArray("services")
                val servObject2 = serv2.getJSONObject(1)

                val expediture:Double =servObject2.optString("serviceValue").toDouble()
                var vExpediture:String = String.format("%,.2f",expediture)

                txtInExpediture.text = vExpediture


                val voucObject3 = vouc.getJSONObject(4)

                val estimate:Double = voucObject3.optString("used").toDouble()
                val vEstimate:String = String.format("%,.2f",estimate)

                txtInEstimate.text = vEstimate

// this where we have Logic for Preexisting Balances.

                val servS: JSONArray = voucObject3.getJSONArray("services")
                val servObjectS = servS.getJSONObject(2)

                val preexisting:Double = servObjectS.optString("serviceValue").toDouble() - voucObject3.optString("used").toDouble()
                Log.e("###PreexixtingLogic>>>",preexisting.toString())

                val vPreexisting:String = String.format("%,.2f",preexisting)
                Log.e("###PreexistingBalance>>",vPreexisting)

                txtInPreexisting.text = vPreexisting
                Log.e("###Preexisting>>>",vPreexisting)

// this is where the logic InPatient Preexisting balance comes to an end.

                val serv3: JSONArray = voucObject3.getJSONArray("services")
                val servObject3 = serv3.getJSONObject(2)

                val balance:Double = servObject3.optString("serviceValue").toDouble()
                val vBalance:String = String.format("%,.2f",balance)

                txtInBalance.text = vBalance


                val voucObject5 =vouc.getJSONObject(4)
// this where the InPatient Congenital/ChildBirth Balance Logic.

                val  servic: JSONArray = voucObject5.getJSONArray("services")
                val servObject = servic.getJSONObject(3)

                val ioutO:Double = servObject.optString("serviceValue").toDouble() - voucObject5.optString("used").toDouble()
                Log.e("###CongenitalLogic>>>",ioutO.toString())

                val vIoutO:String = String.format("%,.2f",ioutO)
                Log.e("###InpatientCongenital>",vIoutO)

                txtioutO.text =vIoutO
                Log.e("###CongenitalBalance>>>",vIoutO)

//this is where the Logic for InPatient CongenitalChildBirth balance Ends.

                val iestimate:Double = voucObject5.optString("used").toDouble()
                val vIestimate:String = String.format("%,.2f",iestimate)

                txtiEstimate.text = vIestimate

                val  serv5: JSONArray = voucObject5.getJSONArray("services")
                val servobject5 = serv5.getJSONObject(3)

                val iBalance:Double = servobject5.optString("serviceValue").toDouble()
                val vIbalance:String = String.format("%,.2f",iBalance)

                txtiBalance.text = vIbalance



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

    fun showSimpleProgressDialog(context:InpatientFragment,title: String?, msg: String, isCancelable: Boolean) {
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