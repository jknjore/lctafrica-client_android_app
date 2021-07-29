package com.lctapp.lct.Activities

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.lctapp.lct.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InsurerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InsurerFragment : Fragment(), AdapterView.OnItemSelectedListener {
    var InsurerURL: String = "http://35.241.171.182:8085/compas/rest/organization/gtOrganizations"

    //"https://lctafrica.io/compas/rest/organization/gtOrganizations"
    private val InsurerTask = 1
    private var mProgressDialog: ProgressDialog? = null
    private var orgArray =
        Array(5) { i -> i <= 5 }


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //register()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insurer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val view = inflater.inflate(R.layout.fragment_insurer,container,false)

        val insurerSpinner = view.findViewById(R.id.insurerFragment) as Spinner

        val insurer = arrayOf(
            "Select Insurer",
            "LIAISON HEALTHCARE SERVICE",
            "LIAISON GROUP",
            "LIAISON HEALTHCARE",
            "BRITAM GROUP"
        )
        //val arrayAdpt = activity?.let { ArrayAdapter(it,android.R.layout.simple_spinner_dropdown_item,insurer)}
        insurerSpinner.adapter = activity?.let {
            ArrayAdapter(
                it, R.layout.support_simple_spinner_dropdown_item, insurer
            )
        }
        insurerSpinner.onItemSelectedListener = this
        Log.e("#Insurer", insurer.toString())


        val categorySpinner = view.findViewById(R.id.categoryFragment) as Spinner
        val cat = arrayListOf("Select Category", "CAT A", "CAT B", "CAT C", "CAT D")
        categorySpinner.adapter = activity?.let {
            ArrayAdapter(
                it, R.layout.support_simple_spinner_dropdown_item, cat
            )
        }
        categorySpinner.onItemSelectedListener = this
        Log.e("#cat", cat.toString())

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InsurerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InsurerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // TODO("Not yet implemented")
        val insurerSpinner = parent?.getItemAtPosition(position).toString()
        // Toast.makeText(activity,type, Toast.LENGTH_LONG).show()
        println(insurerSpinner)
        Log.e("#Insurer", position.toString())

        val categorySpinner = parent?.getItemAtPosition(position).toString()
        // Toast.makeText(activity,type, Toast.LENGTH_LONG).show()
        println(categorySpinner)
        Log.e("#cat", position.toString())
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}
