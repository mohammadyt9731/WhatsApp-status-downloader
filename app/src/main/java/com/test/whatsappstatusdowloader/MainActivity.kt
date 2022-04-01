package com.test.whatsappstatusdowloader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.flarebit.flarebarlib.Flaretab
import com.test.whatsappstatusdowloader.databinding.ActivityMainBinding
import com.test.whatsappstatusdowloader.fragment.GuideFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViewPager();
        setUpBottomNavigationView()
    }

    private fun setUpViewPager(){


    }
    

    private fun setUpBottomNavigationView() {

        val tabs = ArrayList<Flaretab>()
        tabs.add(Flaretab(resources.getDrawable(R.drawable.ic_question), resources.getString(R.string.guide),"#ece5dd"))
        tabs.add(Flaretab(resources.getDrawable(R.drawable.ic_whats_app), resources.getString(R.string.new_status), "#ece5dd"))
        tabs.add(Flaretab(resources.getDrawable(R.drawable.ic_business_whatsapp), resources.getString(R.string.new_business_status), "#ece5dd"))
        tabs.add(Flaretab(resources.getDrawable(R.drawable.ic_download), resources.getString(R.string.downloads), "#ece5dd"))
        binding.bottomBar.tabList = tabs
        binding.bottomBar.attachTabs(this@MainActivity)




        binding.bottomBar.setTabChangedListener { selectedTab, selectedIndex, oldIndex -> //tabIndex starts from 0 (zero). Example : 4 tabs = last Index - 3


            loadFragment(GuideFragment())


        }
    }

    private fun loadFragment(fragment:Fragment){

        supportFragmentManager.beginTransaction().add(R.id.fl_fragment_container,fragment).commit()

    }
}