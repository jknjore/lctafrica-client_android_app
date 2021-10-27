package com.lctapp.lct.Activities

import android.app.ActionBar
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.lctapp.lct.Adapters.DepedantAdapter
import com.lctapp.lct.Classes.Adapters.BalancesAdapter
import com.lctapp.lct.Classes.Helpers.General
import com.lctapp.lct.Classes.Models.MemberBalances.MemberBalances
import com.lctapp.lct.Classes.Models.MemberClaims.MemberClaims
import com.lctapp.lct.R
import android.view.ViewGroup

import android.view.View.MeasureSpec
import android.widget.*


class BenefitsActivity : AppCompatActivity() {
    var balanceInfo:MemberBalances = MemberBalances()
    lateinit var member_number: TextView
    lateinit var insured_name: TextView
    lateinit var balances_listview: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_benefits_home)

        getActionBar()?.setDisplayHomeAsUpEnabled(true);
        setTitle("Benefits")

        member_number = findViewById(R.id.member_number)
        insured_name = findViewById(R.id.insured_name)
        balances_listview=findViewById(R.id.balances_listview)

        balanceInfo= (intent.getSerializableExtra("MemberData") as? MemberBalances)!!

        member_number.text = balanceInfo.memberNo
        insured_name.text = balanceInfo.fullName


        val depedantAdapter: BalancesAdapter = BalancesAdapter(this@BenefitsActivity,balanceInfo)
        balances_listview.adapter=depedantAdapter;

        balances_listview.onItemClickListener= AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@BenefitsActivity , VerificationActivity::class.java)
            intent.putExtra("voucher_name", balanceInfo.memberProgrammes!!.get(0).vouchers!!.get(position).voucherName)
            intent.putExtra("voucher_code", balanceInfo.memberProgrammes!!.get(0).vouchers!!.get(position).voucherCode)
            startActivity(intent)
            finish()
        }

        balances_listview .setVerticalScrollBarEnabled(false)
        //setListViewHeightBasedOnChildren(balances_listview);

    }


    fun setListViewHeightBasedOnChildren(listView: ListView) {
        val listAdapter = listView.adapter ?: return
        val desiredWidth = MeasureSpec.makeMeasureSpec(listView.width, MeasureSpec.UNSPECIFIED)
        var totalHeight = 0
        var view: View? = null
        for (i in 0 until listAdapter.count) {
            view = listAdapter.getView(i, view, listView)
            if (i == 0) view.layoutParams = ViewGroup.LayoutParams(
                desiredWidth,
                ActionBar.LayoutParams.MATCH_PARENT
            )
            view.measure(desiredWidth, MeasureSpec.UNSPECIFIED)
            totalHeight += view.measuredHeight
        }
        val params = listView.layoutParams
        params.height = totalHeight + listView.dividerHeight * listAdapter.count
        listView.layoutParams = params
        listView.requestLayout()
    }



}