package com.test.whatsappstatusdowloader.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.flarebit.flarebarlib.Flaretab
import com.test.whatsappstatusdowloader.R
import com.test.whatsappstatusdowloader.databinding.ActivityMainBinding
import com.test.whatsappstatusdowloader.fragment.GuideFragment
import com.test.whatsappstatusdowloader.fragment.StatusFragment
import com.test.whatsappstatusdowloader.utils.Constants
import com.test.whatsappstatusdowloader.utils.MyIntent


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

        //start fragment
        loadFragment(GuideFragment())

        setTabChangeListener()
        toolBarButtonClick()

    }

    private fun configTabs (){

        val tabs = ArrayList<Flaretab>()

        tabs.add(createFlareTab(R.drawable.ic_question,getString(R.string.guide)))
        tabs.add(createFlareTab(R.drawable.ic_whats_app,getString(R.string.whatsapp_status)))
        tabs.add(createFlareTab(R.drawable.ic_business_whatsapp,getString(R.string.new_business_status)))
        tabs.add(createFlareTab(R.drawable.ic_download,getString(R.string.downloads)))

        binding.bottomBar.tabList = tabs
        binding.bottomBar.attachTabs(this@MainActivity)

    }

    private fun createFlareTab(resourceId:Int, text:String):Flaretab{
       return Flaretab(ResourcesCompat.getDrawable(resources,resourceId,null), text,Constants.TAB_COLOR_CODE)

    }

    private fun loadFragment(fragment: Fragment){

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_fragment_container,fragment).commit()
    }

    private fun setTabChangeListener(){
        binding.bottomBar.setTabChangedListener { _, selectedIndex, _ ->

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