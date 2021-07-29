package com.lctapp.lct.Classes

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.lctapp.lct.R

class ExpandableListViewAdapter(var context:Context, var title:MutableList<String>, var subTitle:MutableList<MutableList<String>>): BaseExpandableListAdapter() {
    override fun getGroup(groupPosition:Int):String{
        return title[groupPosition]
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
//        TODO("Not yet implemented")
        return true
    }

    override fun hasStableIds(): Boolean {
//        TODO("Not yet implemented")
        return false
    }

    @SuppressLint("InflateParams")
    override fun getGroupView(
        groupPosition: Int ,
        isExpanded: Boolean ,
        convertView: View? ,
        parent: ViewGroup?
    ): View {
        var convertView = convertView
        if (convertView==null){
            val inflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)as LayoutInflater
            convertView = inflater.inflate(R.layout.title_layout,null)
        }
        val mTitle=convertView!!.findViewById<TextView>(R.id.lv_title)
        mTitle.text = getGroup(groupPosition)
        return convertView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
//        TODO("Not yet implemented")
        return subTitle[groupPosition].size
    }

    override fun getChild(groupPosition: Int , childPosition: Int): String {
//        TODO("Not yet implemented")
        return subTitle[groupPosition][childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
//        TODO("Not yet implemented")
        return groupPosition.toLong()
    }

    @SuppressLint("InflateParams")
    override fun getChildView(
        groupPosition: Int ,
        childPosition: Int ,
        isExpanded: Boolean ,
        convertView: View? ,
        parent: ViewGroup?
    ): View {
//        TODO("Not yet implemented")
        var convertView= convertView
        if (convertView==null){
            val inflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView= inflater.inflate(R.layout.subtitle_layout,null)
        }
        val mSubTitle= convertView!!.findViewById<TextView>(R.id.lv_subtitle)
        mSubTitle.text=getChild(groupPosition,childPosition)
        return convertView
    }

    override fun getChildId(groupPosition: Int , childPosition: Int): Long {
//        TODO("Not yet implemented")
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
//        TODO("Not yet implemented")
        return title.size
    }

}