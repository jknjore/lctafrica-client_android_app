package com.lctapp.lct.Classes

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.lctapp.lct.Balances.ComplementaryFragment
import com.lctapp.lct.Balances.InpatientFragment
import com.lctapp.lct.Balances.OutpatientFragment

class ViewerAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                //  val homeFragment: HomeFragment = HomeFragment()
                return OutpatientFragment()
            }
            1 -> {
                return InpatientFragment()
            }
            2 -> {
                // val movieFragment = MovieFragment()
                return ComplementaryFragment()
            }
            else -> OutpatientFragment()
        }

        return OutpatientFragment()
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}