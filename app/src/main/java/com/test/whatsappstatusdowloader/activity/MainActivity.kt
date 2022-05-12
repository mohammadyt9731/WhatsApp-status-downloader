package com.test.whatsappstatusdowloader.activity

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.flarebit.flarebarlib.Flaretab
import com.test.whatsappstatusdowloader.R
import com.test.whatsappstatusdowloader.databinding.ActivityMainBinding
import com.test.whatsappstatusdowloader.dialog.AboutUsDialog
import com.test.whatsappstatusdowloader.dialog.CommentDialog
import com.test.whatsappstatusdowloader.fragment.GuideFragment
import com.test.whatsappstatusdowloader.fragment.StatusFragment
import com.test.whatsappstatusdowloader.utils.Constants
import com.test.whatsappstatusdowloader.utils.MyIntent


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController=findNavController(R.id.nav_controller)

        setUpBottomNavigationView()
        toolBarButtonClick()
        setUpNavigationView()
    }


    private fun setUpBottomNavigationView() {

        configTabs()

        //start fragment
     //   loadFragment(StatusFragment(Constants.WHATSAPP_DIRECTORY))


        setTabChangeListener()


    }

    private fun configTabs() {

        val tabs = ArrayList<Flaretab>()

        tabs.add(createFlareTab(R.drawable.ic_guide, getString(R.string.guide)))
        tabs.add(createFlareTab(R.drawable.ic_whats_app, getString(R.string.whatsapp_status)))
        tabs.add(
            createFlareTab(
                R.drawable.ic_business_whatsapp,
                getString(R.string.new_business_status)
            )
        )
        tabs.add(createFlareTab(R.drawable.ic_download, getString(R.string.downloads)))

        binding.bottomBar.apply {
            tabList = tabs
            attachTabs(this@MainActivity)

           // selectTab(1)

        }


    }

    private fun createFlareTab(resourceId: Int, text: String): Flaretab {
        return Flaretab(
            ResourcesCompat.getDrawable(resources, resourceId, null),
            text,
            Constants.TAB_COLOR_CODE
        )

    }

//    private fun loadFragment(fragment: Fragment) {
//
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fl_fragment_container, fragment).commit()
//    }

    private fun setTabChangeListener() {
//        binding.bottomBar.setTabChangedListener { _, selectedIndex, _ ->
//
//            when (selectedIndex) {
//
//                0 -> loadFragment(GuideFragment())
//                1 -> loadFragment(StatusFragment(Constants.WHATSAPP_DIRECTORY))
//                2 -> loadFragment(StatusFragment(Constants.WHATSAPP_DIRECTORY))
//                3 -> loadFragment(StatusFragment(Constants.SAVED_DIRECTORY))
//                else -> loadFragment(GuideFragment())
//            }
//
//        }

        binding.bottomBar.setTabChangedListener { _, selectedIndex, _ ->
            val bundle:Bundle= Bundle()

            when (selectedIndex) {

                0 -> navController.navigate(R.id.guide_fragment)
                1 ->{
                    if(Build.VERSION.SDK_INT>=30)
                    bundle.putString(Constants.DIRECTORY_KEY,Constants.WHATSAPP_DIRECTORY)
                   else
                        bundle.putString(Constants.DIRECTORY_KEY,Constants.WHATSAPP_DIRECTORY)


                    navController.navigate(R.id.whatsapp_status_fragment,bundle)
                }
                2 -> {
                    if(Build.VERSION.SDK_INT>=30)
                        bundle.putString(Constants.DIRECTORY_KEY,Constants.WHATSAPP_DIRECTORY)
                    else
                        bundle.putString(Constants.DIRECTORY_KEY,Constants.WHATSAPP_DIRECTORY)
                    navController.navigate(R.id.wb_status_fragment,bundle)
                }
                3 -> {
                    bundle.putString(Constants.DIRECTORY_KEY,Constants.SAVED_DIRECTORY)
                    navController.navigate(R.id.saved_status_fragment,bundle)
                }
                else -> navController.navigate(R.id.guide_fragment)
            }

        }

    }

    private fun toolBarButtonClick() {

        binding.apply {

            ivWhatsapp.setOnClickListener {
                MyIntent.openWhatsApp(this@MainActivity)
            }

            ivMenu.setOnClickListener() {

                drawerLayout.openDrawer(Gravity.RIGHT)
            }
        }


    }


    private fun setUpNavigationView() {

        binding.navigationView.setNavigationItemSelectedListener {

            when (it.itemId) {

                R.id.nav_item_guide ->
                {navController.navigate(R.id.guide_fragment)
                   binding.bottomBar.selectTab(0)
                }
                R.id.nav_item_share_app -> MyIntent.shareAppIntent(this@MainActivity)
                R.id.nav_item_other_app -> MyIntent.otherAppIntent(this@MainActivity)
                R.id.nav_item_comment -> CommentDialog(this@MainActivity).show()
                R.id.nav_item_about_us -> AboutUsDialog(this@MainActivity).show()
                R.id.nav_item_exit -> {
                    closeDrawer()
                    finish()
                }
            }

            closeDrawer()
            true
        }

    }

    override fun onBackPressed() {

        binding.apply {

            if (drawerLayout.isDrawerOpen(Gravity.RIGHT))
               closeDrawer()
            else
                super.onBackPressed()

        }
    }

    private fun closeDrawer(){
        binding.drawerLayout.closeDrawer(Gravity.RIGHT)

    }

}