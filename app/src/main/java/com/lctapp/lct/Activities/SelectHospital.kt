package com.lctapp.lct.Activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lctapp.lct.Classes.Adapters.SelectHospitalAdapter
import com.lctapp.lct.Classes.Helpers.General
import com.lctapp.lct.Classes.Models.HospitalData
import com.lctapp.lct.Classes.Variables
import com.lctapp.lct.R
import java.util.*

class SelectHospital : AppCompatActivity() {
    private var hospitalsRv: RecyclerView? = null
    private var ct: List<HospitalData>? = null
    var ls: ArrayList<HospitalData>? = null
    var e: EditText? = null

    private lateinit var adapter:SelectHospitalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_hospital)



        val family = arrayOf(Variables.getDependantNumber(),)

        println("call inside ac")
        hospitalsRv = findViewById(R.id.hospitals_rv)
        // ArrayList<String> ct = (ArrayList<String>) getIntent().getSerializableExtra("mylist");
        // ArrayList<String> ct = (ArrayList<String>) getIntent().getSerializableExtra("mylist");
        ct = intent.extras!!.getSerializable("hospitalsRv") as List<HospitalData>?
        Log.e("####>>>ct",General.getDump(ct))
        adapter = SelectHospitalAdapter(ct, this)
        hospitalsRv?.setHasFixedSize(true)
        hospitalsRv?.setLayoutManager(LinearLayoutManager(this))
        hospitalsRv?.setAdapter(adapter)

        adapter!!.setOnItemClickListener(object : SelectHospitalAdapter.OnItemClickListener {
            override fun OnItemClick(position: Int) {
                var position = position
                if (ls != null) {
                    position = ct!!.indexOf(ls!![position])
                }
                val i = Intent()
                i.putExtra("pos", position)
                setResult(RESULT_OK, i)
                finish()
            }
        })

        e = findViewById(R.id.selection)
        e?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                updateSearch(s.toString())
            }
        })
    }


    fun updateSearch(s: String) {
        ls = ArrayList()
        for (item in ct!!) {
            if (item.merchantName?.toLowerCase(Locale.ROOT)?.contains(s.toLowerCase(Locale.ROOT).trim { it <= ' ' }) == true) {
                ls!!.add(item)
            }
        }
        adapter?.filterList(ls!!)
    }

}