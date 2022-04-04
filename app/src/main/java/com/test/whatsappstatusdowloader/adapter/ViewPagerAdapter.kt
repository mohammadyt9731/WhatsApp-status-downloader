package com.test.whatsappstatusdowloader.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.test.whatsappstatusdowloader.fragment.GuideFragment

class ViewPagerAdapter(fragmentManager:FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        when(position){

            0 -> return GuideFragment()
            1 -> return GuideFragment()
            2 -> return GuideFragment()
            3 -> return GuideFragment()
            else -> return GuideFragment()
        }
    }

}