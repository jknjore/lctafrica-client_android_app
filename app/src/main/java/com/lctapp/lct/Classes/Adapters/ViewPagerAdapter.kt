package com.lctapp.lct.Classes.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lctapp.lct.Classes.Models.MemberClaims.MemberClaims
import com.lctapp.lct.R
import kotlinx.android.synthetic.main.item_view_pager.view.*

class ViewPagerAdapter (private val resp:MemberClaims):RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>()
{
    inner class ViewPagerViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {

        val view  = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager,parent,false)
        return  ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        //val cardView = totalDisplay[position]
        holder.itemView.cover_name.text = resp.getVouchers().get(position).voucherName
        holder.itemView.cover_value.text = resp.getVouchers().get(position).value
        holder.itemView.cover_balance.text = resp.getVouchers().get(position).amountRemaining
    }

    override fun getItemCount(): Int {
return resp.getVouchers().size
    }
}