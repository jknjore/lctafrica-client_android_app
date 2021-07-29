package com.lctapp.lct.Classes.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.lctapp.lct.Classes.Models.MyData
import com.lctapp.lct.R

class ListAdapter(val context:Context, private val list:ArrayList<MyData>): BaseAdapter() {


    @SuppressLint("ViewHolder")
    override fun getView(position: Int , convertView: View? , parent: ViewGroup?): View {
        //TODO("Not yet implemented")

        // creating a view inorder to bind the list view

        val view:View= LayoutInflater.from(context).inflate(R.layout.activity_row,parent, false)

        val txnDate=view.findViewById<TextView>(R.id.ptxnDate)
        val serviceName= view.findViewById<TextView>(R.id.pserviceName)
        val serviceAmount = view.findViewById<TextView>(R.id.pserviceAmount)
        val merchantName = view.findViewById<TextView>(R.id.pmerchantName)

        txnDate.text=list[position].txnDate.toString()
        serviceName.text=list[position].serviceName.toString()
        serviceAmount.text=list[position].serviceAmount.toString()
        merchantName.text=list[position].merchantName.toString()


        return view
    }

    override fun getItem(position: Int): Any {
        //TODO("Not yet implemented")

        return position
    }

    override fun getItemId(position: Int): Long {
        //TODO("Not yet implemented")

        return position.toLong()
    }

    override fun getCount(): Int {
        //TODO("Not yet implemented")

        return list.size
    }
}