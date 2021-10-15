package com.lctapp.lct.Balances

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.lctapp.lct.Activities.ResetPassword
import com.lctapp.lct.Classes.Api.HospitalsAPi
import com.lctapp.lct.Classes.Constants.APIClient
import com.lctapp.lct.Classes.Helpers.Loader
import com.lctapp.lct.Classes.Models.Member.MemberDetails
import com.lctapp.lct.Classes.Models.Payloads.Member
import com.lctapp.lct.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_complementary.*
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
 * Use the [ComplementaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ComplementaryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var fetchURL: String =  //"http://34.123.154.111:8085/compas/rest/user/downloadMember"
        //"https://staging.lctafrica.io/compas/rest/user/downloadMember"
        "http://35.241.171.182:8085/compas/rest/user/downloadMember"
       // "https://lctafrica.io/compas/rest/user/downloadMember"

    private val FetchTask = 1

    private var mProgressDialog: ProgressDialog? = null
    val MyPREFERENCES = "MyPrefs"
    var sharedpreferences: SharedPreferences? = null
    var member : String= ""
    var l: Loader = Loader
    private var apiC: HospitalsAPi? = null
    var member_data: MemberDetails? = null
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
        return inflater.inflate(R.layout.fragment_complementary, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ComplementaryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ComplementaryFragment().apply {
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
//        println("demo$details")
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


    private fun populate_parameters(resp:MemberDetails)
    {

//=============================================================================================================
        val limit:Double = resp.getMemberProgrammes().get(0).getVouchers().get(3).getUsed()
        val vLimit:String = String.format("%,.2f",limit)

        txtCnLimit.text= vLimit

        val amount:Double = resp.getMemberProgrammes().get(0).getVouchers().get(3).getVoucherValue()
        val vAmount:String = String.format("%,.2f",amount)

        txtCnAmount.text = vAmount


        val patient:Double = resp.getMemberProgrammes().get(0).getVouchers().get(3).getVoucherBalance()
        val vPatient:String = String.format("%,.2f",patient)

        txtCnPatient.text = vPatient


////++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        val expense:Double =  resp.getMemberProgrammes().get(0).getVouchers().get(4).getUsed()
        val vExpense:String = String.format("%,.2f",expense)

        txtCnExpens.text =vExpense

        val expediture:Double = resp.getMemberProgrammes().get(0).getVouchers().get(4).getServices().get(8).getServiceValue()
        val vExpediture:String = String.format("%,.2f",expediture)

        txtCnExpediture.text = vExpediture

//this is where the ExternalAids/Appliances Balance Logic Starts here

        val hospitalization:Double = resp.getMemberProgrammes().get(0).getVouchers().get(4).getServices().get(8).getServiceValue() -   resp.getMemberProgrammes().get(0).getVouchers().get(4).getUsed().toDouble()

        //val vHospitalization:String = String.format("%,.2f",hospitalization)
        val vHospitalization:String = hospitalization.toString()

        txtCnhospitalization.text = vHospitalization

//this is where the ExternalAids/Appliances Balance Logic Ends

        val estimate:Double = resp.getMemberProgrammes().get(0).getVouchers().get(4).getUsed()
        val vEstimate:String = String.format("%,.2f",estimate)

        txtCnEstimate.text = vEstimate

//This is the begining of Last Expense Balance Logic.


        val preexisting:Double = resp.getMemberProgrammes().get(0).getVouchers().get(4).getServices().get(7).getServiceValue() - resp.getMemberProgrammes().get(0).getVouchers().get(4).getUsed()

        val vPreexisting:String = String.format("%,.2f",preexisting)

        txtCnPreexisting.text = vPreexisting
        //End Of Last Expense Logic FOr Balance.

        val balance:Double = resp.getMemberProgrammes().get(0).getVouchers().get(4).getServices().get(7).getServiceValue()
        val vBalance:String = String.format("%,.2f",balance)

        txtCnBalance.text = vBalance


        val nHospitalization:Double = resp.getMemberProgrammes().get(0).getVouchers().get(4).getVoucherBalance()
        val vnHospitalization:String = String.format("%,.2f",nHospitalization)

        txtCnhospitalization.text = vnHospitalization
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

//=============================================================================================================
                val vouc: JSONArray = arrObj.getJSONArray("vouchers")
                val voucObject1 = vouc.getJSONObject(3)


                val limit:Double = voucObject1.optString("used").toDouble()
                val vLimit:String = String.format("%,.2f",limit)

                txtCnLimit.text= vLimit

                val amount:Double = voucObject1.optString("voucherValue").toDouble()
                val vAmount:String = String.format("%,.2f",amount)

                txtCnAmount.text = vAmount


                val patient:Double = voucObject1.optString("voucherBalance").toDouble()
                val vPatient:String = String.format("%,.2f",patient)

                txtCnPatient.text = vPatient


////++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


                val voucObject2 = vouc.getJSONObject(4)

                val expense:Double = voucObject2.optString("used").toDouble()
                val vExpense:String = String.format("%,.2f",expense)

                txtCnExpens.text =vExpense

                val serv2: JSONArray = voucObject2.getJSONArray("services")
                val servObject2 = serv2.getJSONObject(8)
                Log.e("###ValuesAtIndex8",servObject2.toString())


                val expediture:Double = servObject2.optString("serviceValue").toDouble()
                val vExpediture:String = String.format("%,.2f",expediture)

                txtCnExpediture.text = vExpediture

//this is where the ExternalAids/Appliances Balance Logic Starts here

                val hospitalization:Double = servObject2.optString("serviceValue").toDouble() -  voucObject2.optString("used").toDouble()
                Log.e("###AppliancesLogic>>>", hospitalization.toString())
                Log.e("###servObject2",servObject2.toString())
                Log.e("###voucObject2",voucObject2.toString())

                //val vHospitalization:String = String.format("%,.2f",hospitalization)
                val vHospitalization:String = hospitalization.toString()

                Log.e("###AppliancesBalance>>>",vHospitalization)

                txtCnhospitalization.text = vHospitalization
                Log.e("###APPLIANCESBALANCE>>>",vHospitalization)

//this is where the ExternalAids/Appliances Balance Logic Ends

                val voucObject3 = vouc.getJSONObject(4)

                val estimate:Double = voucObject3.optString("used").toDouble()
                val vEstimate:String = String.format("%,.2f",estimate)

                txtCnEstimate.text = vEstimate

//This is the begining of Last Expense Balance Logic.

                val service: JSONArray = voucObject3.getJSONArray("services")
                val servObjects = service.getJSONObject(7)

                val preexisting:Double = servObjects.optString("serviceValue").toDouble() - voucObject3.optString("used").toDouble()
                Log.e("###ExpenseLogic",preexisting.toString())

                val vPreexisting:String = String.format("%,.2f",preexisting)
                Log.e("###FuneralExpense>>>",vPreexisting)

                txtCnPreexisting.text = vPreexisting
                Log.e("###FUneralExpenseBala>>",vPreexisting)
                //End Of Last Expense Logic FOr Balance.

                val serv3: JSONArray = voucObject3.getJSONArray("services")
                val servObject3 = serv3.getJSONObject(7)
                Log.e("###VALUES@INDEX7>>>",servObject3.toString())

                val balance:Double = servObject3.optString("serviceValue").toDouble()
                val vBalance:String = String.format("%,.2f",balance)

                txtCnBalance.text = vBalance


                val voucObject5 =vouc.getJSONObject(4)

                val nHospitalization:Double = voucObject5.optString("voucherBalance").toDouble()
                val vnHospitalization:String = String.format("%,.2f",nHospitalization)

                txtCnhospitalization.text = vnHospitalization

                Log.e("### voucherBalance ",voucObject5.optString("voucherBalance"))


//                val nExpense:Double = voucObject5.optString("used").toDouble()
//                val vnExpense:String = String.format("%,.2f",nExpense)
//
//               // txtCnExpense.text = vnExpense
//
//                Log.e("### used ",voucObject5.optString("used"))
//
//                val  serv5: JSONArray = voucObject5.getJSONArray("services")
//                val servobject5 = serv5.getJSONObject(8)
//
//
//                val nExpediture:Double = servobject5.optString("serviceValue").toDouble()
//                val vnExpediture:String = String.format("%,.2f",nExpediture)
//
//                txtCnExpediture.text =vnExpediture

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

    fun showSimpleProgressDialog(context: ComplementaryFragment, title: String?, msg: String, isCancelable: Boolean) {
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