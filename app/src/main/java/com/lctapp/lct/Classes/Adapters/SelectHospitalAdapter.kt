package com.lctapp.lct.Classes.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lctapp.lct.Activities.SelectHospital
import com.lctapp.lct.Classes.Models.HospitalData
import com.lctapp.lct.R
import java.util.*

class SelectHospitalAdapter(ct: List<HospitalData>?, selectHospital: SelectHospital) : RecyclerView.Adapter<SelectHospitalAdapter.ViewHolder>() {

    private var c_list: List<HospitalData>? = null
    private var c_list_full: List<HospitalData>? = null
    var context: Context? = null
    private var myListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun OnItemClick(position: Int)
    }

    fun setOnItemClickListener(l: OnItemClickListener?) {
        myListener = l
    }

    init {
        SelectHospitalAdapterfull(ct,selectHospital)
    }

    fun SelectHospitalAdapterfull(c_list: List<HospitalData>?, context: Context?) {
        this.c_list = c_list
        this.c_list_full = ArrayList(c_list)
        this.context = context
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        //TODO("Not yet implemented")
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.hospitalrvh, parent, false)
        return ViewHolder(view, myListener)
    }


    class ViewHolder(
        itemView: View,
        lisn: OnItemClickListener?
    ) :
        RecyclerView.ViewHolder(itemView) {
       // var ly: RelativeLayout = itemView.findViewById(R.id.hospitals_rv)
        var tv: TextView = itemView.findViewById(R.id.ct)

        init {
            itemView.setOnClickListener {
                if (lisn != null) {
                    val pos = adapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        lisn.OnItemClick(pos)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.tv.text = c_list!![position].merchantName
    }

    override fun getItemCount(): Int {
        return c_list!!.size
    }


    fun filterList(hospitalrvh: List<HospitalData>) {
        c_list = hospitalrvh
        notifyDataSetChanged()
    }

}
