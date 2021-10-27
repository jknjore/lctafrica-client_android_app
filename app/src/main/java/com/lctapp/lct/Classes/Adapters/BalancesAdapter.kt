package com.lctapp.lct.Classes.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.lctapp.lct.Classes.Helpers.General
import com.lctapp.lct.Classes.Models.MemberBalances.MemberBalances
import com.lctapp.lct.Classes.Models.MemberClaims.MemberClaims
import com.lctapp.lct.R
import java.text.DecimalFormat

class BalancesAdapter(context: Context, private val balanceInfo: MemberBalances) :
    ArrayAdapter<String?>(context, R.layout.balances_layout)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
    {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val rowView: View = layoutInflater.inflate(R.layout.balances_layout,null,true)
        val progress_circular: CircularProgressIndicator = rowView.findViewById(R.id.progress_circular)
        val balance_name: TextView = rowView.findViewById(R.id.balance_name)
        val balance_available: TextView = rowView.findViewById(R.id.balance_available)
        val balances_percentage: TextView = rowView.findViewById(R.id.balances_percentage)

        val percentage_used:Int = java.lang.Double.parseDouble(balanceInfo!!.memberProgrammes!!.get(0).vouchers!!.get(position).percentage).toInt()

        balances_percentage.setText(""+percentage_used+"%")
        progress_circular.setProgress(percentage_used);

        balance_name.text = balanceInfo!!.memberProgrammes!!.get(0).vouchers!!.get(position).voucherName
        balance_available.text ="Available for use: "+(100-percentage_used)+"%"


        return rowView
    }


    override fun getCount(): Int {
        return balanceInfo!!.memberProgrammes!!.get(0).vouchers!!.size
    }
}