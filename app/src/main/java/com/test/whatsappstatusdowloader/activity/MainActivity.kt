package com.test.whatsappstatusdowloader.activity


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.flarebit.flarebarlib.Flaretab
import com.test.whatsappstatusdowloader.R
import com.test.whatsappstatusdowloader.databinding.ActivityMainBinding
import com.test.whatsappstatusdowloader.fragment.GuideFragment
import com.test.whatsappstatusdowloader.fragment.StatusFragment
import com.test.whatsappstatusdowloader.utility.Constants
import com.test.whatsappstatusdowloader.utility.MyIntent


class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setUpBottomNavigationView()
    }



    private fun setUpBottomNavigationView() {

        configTabs()
        loadFragment(GuideFragment())
        setTabChangeListener()
        toolBarButtonClick()

    }

    private fun configTabs (){

        val tabs = ArrayList<Flaretab>()
        val colorCode=Constants.TAB_COLOR_CODE

        tabs.add(getFlareTab(R.drawable.ic_question,getString(R.string.guide),colorCode))
        tabs.add(getFlareTab(R.drawable.ic_whats_app,getString(R.string.new_status),colorCode))
        tabs.add(getFlareTab(R.drawable.ic_business_whatsapp,getString(R.string.new_business_status),colorCode))
        tabs.add(getFlareTab(R.drawable.ic_download,getString(R.string.downloads),colorCode))

        binding.bottomBar.tabList = tabs
        binding.bottomBar.attachTabs(this@MainActivity)

    }

    private fun getFlareTab(resourceId:Int,text:String,colorCode:String):Flaretab{
       return Flaretab(ResourcesCompat.getDrawable(resources,resourceId,null), text,colorCode)

    }

    private fun loadFragment(fragment: Fragment){

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_fragment_container,fragment).commit()
    }

    private fun setTabChangeListener(){
        binding.bottomBar.setTabChangedListener { selectedTab, selectedIndex, oldIndex ->

            when(selectedIndex){

                0-> loadFragment(GuideFragment())
                1-> loadFragment(StatusFragment(Constants.WHATSAPP_DIRECTORY))
                2-> loadFragment(StatusFragment(Constants.WHATSAPP_DIRECTORY))
                3-> loadFragment(StatusFragment(Constants.SAVED_DIRECTORY))
                else-> loadFragment(GuideFragment())
            }

        }
    }

    private fun toolBarButtonClick(){
        binding.ivWhatsapp.setOnClickListener{
            MyIntent.openWhatsApp(this)
        }
    }

}