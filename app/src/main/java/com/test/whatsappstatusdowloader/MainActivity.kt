package com.test.whatsappstatusdowloader


import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.flarebit.flarebarlib.Flaretab
import com.test.whatsappstatusdowloader.databinding.ActivityMainBinding
import com.test.whatsappstatusdowloader.fragment.GuideFragment
import com.test.whatsappstatusdowloader.fragment.WhatsappStatusFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpBottomNavigationView()

    }




    private fun setUpBottomNavigationView() {


        val tabs = ArrayList<Flaretab>()
        val colorCode="#ece5dd"
        tabs.add(Flaretab(resources.getDrawable(R.drawable.ic_question), resources.getString(R.string.guide),colorCode))
        tabs.add(Flaretab(resources.getDrawable(R.drawable.ic_whats_app), resources.getString(R.string.new_status), colorCode))
        tabs.add(Flaretab(resources.getDrawable(R.drawable.ic_business_whatsapp), resources.getString(R.string.new_business_status), colorCode))
        tabs.add(Flaretab(resources.getDrawable(R.drawable.ic_download), resources.getString(R.string.downloads), colorCode))
        binding.bottomBar.tabList = tabs
        binding.bottomBar.attachTabs(this@MainActivity)



        loadFragment(GuideFragment())

        binding.bottomBar.setTabChangedListener { selectedTab, selectedIndex, oldIndex -> //tabIndex starts from 0 (zero). Example : 4 tabs = last Index - 3

            when(selectedIndex){

                0-> loadFragment(GuideFragment())
                1-> loadFragment(WhatsappStatusFragment())
                2-> loadFragment(WhatsappStatusFragment())
                3-> loadFragment(WhatsappStatusFragment())
                else-> loadFragment(GuideFragment())
            }

        }




    }

    private fun loadFragment(fragment: Fragment){

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_fragment_container,fragment).commit()

    }

}