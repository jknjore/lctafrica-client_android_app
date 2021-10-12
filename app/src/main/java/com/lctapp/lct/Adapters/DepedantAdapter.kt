package com.lctapp.lct.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.lctapp.lct.Classes.Helpers.General
import com.lctapp.lct.Classes.Helpers.Loader
import com.lctapp.lct.Classes.Models.MemberClaims.MemberClaims
import com.lctapp.lct.R


class DepedantAdapter(context: Context, private val memberInfo: MemberClaims) :
    ArrayAdapter<String?>(context, R.layout.dependants_layout)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater:LayoutInflater= LayoutInflater.from(context)
        val rowView:View = layoutInflater.inflate(R.layout.dependants_layout,null,true)
        val dep_image:ImageView= rowView.findViewById(R.id.dependant_image)
        val dep_name:TextView= rowView.findViewById(R.id.dependant_name)
        val dep_age:TextView= rowView.findViewById(R.id.dependant_age)
        val dep_type:TextView= rowView.findViewById(R.id.dependant_type)

        dep_image.setImageResource(R.drawable.ic_depedant)
        dep_age.text=General.getAge(Integer.parseInt(memberInfo.getFamilyMemList().get(position).famDob.split("-")[0]),Integer.parseInt(memberInfo.getFamilyMemList().get(position).famDob.split("-")[1]),Integer.parseInt(memberInfo.getFamilyMemList().get(position).famDob.split("-")[2]))+" Years"
        dep_name.text=memberInfo.getFamilyMemList().get(position).famMemFullName
        dep_type.text=if(memberInfo.getFamilyMemList().get(position).relationId == 1)  "Spouse" else (if(memberInfo.getFamilyMemList().get(position).famGender == "M") "Son" else "Daughter")

        return rowView
    }


    override fun getCount(): Int {
        return memberInfo.getFamilyMemList().size
    }
}