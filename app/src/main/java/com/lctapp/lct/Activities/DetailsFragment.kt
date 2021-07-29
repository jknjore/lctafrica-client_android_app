package com.lctapp.lct.Activities

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
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsFragment : Fragment(), AdapterView.OnItemSelectedListener {
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

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val view = inflater.inflate(R.layout.fragment_details,container,false)
        var spinner = view.findViewById(R.id.gender) as Spinner
        val gender = arrayOf("Select gender", "Male", "Female")
        //val arrayAdpt = activity?.let { ArrayAdapter(it,android.R.layout.simple_spinner_dropdown_item,gender) }
        spinner.adapter = activity?.let {
            ArrayAdapter(
                it, R.layout.support_simple_spinner_dropdown_item,gender
            )
        }
        spinner.onItemSelectedListener = this
        Log.e("#gender", gender.toString())


        //val viw = inflater.inflate(R.layout.fragment_details,container,false)

        spinner = view.findViewById(R.id.memberType) as Spinner
        val type = arrayOf("select member type", "principal", "Dependent")
        //val arryAdp = activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, type) }
        spinner.adapter = activity?.let {
            ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item,type
            )
        }
        spinner.onItemSelectedListener = this
        Log.e("#MemberType", type.toString())

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //TODO("Not yet implemented")

        val gender = parent?.getItemAtPosition(position).toString()
        // Toast.makeText(activity,type, Toast.LENGTH_LONG).show()
        println(gender)

        val memberType = parent?.getItemAtPosition(position).toString()

        println(memberType)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}