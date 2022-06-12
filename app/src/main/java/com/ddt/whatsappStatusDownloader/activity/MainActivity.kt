package com.ddt.whatsappStatusDownloader.activity

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.flarebit.flarebarlib.Flaretab
import com.ddt.whatsappStatusDownloader.R
import com.ddt.whatsappStatusDownloader.databinding.ActivityMainBinding
import com.ddt.whatsappStatusDownloader.dialog.AboutUsDialog
import com.ddt.whatsappStatusDownloader.dialog.CommentDialog
import com.ddt.whatsappStatusDownloader.dialog.ExitDialog
import com.ddt.whatsappStatusDownloader.utils.Constants
import com.ddt.whatsappStatusDownloader.utils.MyIntent


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setUpNavController()
        setUpBottomNavigationView()
        toolBarButtonClick()
        setUpNavigationView()
    }

    private fun setUpNavController() {
        navController = findNavController(R.id.nav_controller)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            binding.apply {

                if (destination.id == R.id.show_media_fragment) {
                    toolbar.visibility = View.GONE
                    bottomBar.visibility = View.GONE
                } else {
                    toolbar.visibility = View.VISIBLE
                    bottomBar.visibility = View.VISIBLE
                }
            }
        }

    }


    private fun setUpBottomNavigationView() {

        configTabs()
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

        }

    }

    private fun createFlareTab(resourceId: Int, text: String): Flaretab {
        return Flaretab(
            ResourcesCompat.getDrawable(resources, resourceId, null),
            text,
            Constants.TAB_COLOR_CODE
        )

    }

    private fun setTabChangeListener() {

        binding.bottomBar.setTabChangedListener { _, selectedIndex, _ ->
            val bundle = Bundle()

            when (selectedIndex) {

                0 -> navController.navigate(R.id.guide_fragment)
                1 -> {
                    if (Build.VERSION.SDK_INT >= 40)
                        bundle.putString(Constants.DIRECTORY_KEY, Constants.NEW_WHATSAPP_DIRECTORY)
                    else
                        bundle.putString(Constants.DIRECTORY_KEY, Constants.WHATSAPP_DIRECTORY)


                    navController.navigate(R.id.whatsapp_status_fragment, bundle)
                }
                2 -> {
                    if (Build.VERSION.SDK_INT >= 30)
                        bundle.putString(Constants.DIRECTORY_KEY, Constants.NEW_WHATSAPP_BUSINESS_DIRECTORY)
                    else
                        bundle.putString(Constants.DIRECTORY_KEY, Constants.WHATSAPP_BUSINESS_DIRECTORY)
                    navController.navigate(R.id.wb_status_fragment, bundle)
                }
                3 -> {
                    bundle.putString(Constants.DIRECTORY_KEY, Constants.SAVED_DIRECTORY)
                    navController.navigate(R.id.saved_status_fragment, bundle)
                }
                else -> return@setTabChangedListener
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

                R.id.nav_item_guide -> {
                    navController.navigate(R.id.guide_fragment)
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
            else if (navController.currentDestination?.id == R.id.show_media_fragment) {
                navController.navigateUp()
            } else
                ExitDialog(this@MainActivity).show()


        }
    }

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawer(Gravity.RIGHT)

    }


}