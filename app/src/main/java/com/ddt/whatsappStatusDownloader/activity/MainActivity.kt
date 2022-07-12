package com.ddt.whatsappStatusDownloader.activity

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.ddt.whatsappStatusDownloader.R
import com.ddt.whatsappStatusDownloader.databinding.ActivityMainBinding
import com.ddt.whatsappStatusDownloader.dialog.AboutUsDialog
import com.ddt.whatsappStatusDownloader.dialog.CommentDialog
import com.ddt.whatsappStatusDownloader.dialog.ExitDialog
import com.ddt.whatsappStatusDownloader.utils.Constants
import com.ddt.whatsappStatusDownloader.utils.MyIntent
import com.ddt.whatsappStatusDownloader.utils.gone
import com.ddt.whatsappStatusDownloader.utils.visible
import com.flarebit.flarebarlib.Flaretab
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener





class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setUpNavController()
        setUpBottomNavigation()
        toolBarButtonClick()
        setUpNavigationView()
    }

    //navController
    private fun setUpNavController() {
        //init
        navController = findNavController(R.id.nav_controller)
        //destinationChange
        navController.addOnDestinationChangedListener { _, destination, _ ->

            binding.apply {

                if (destination.id == R.id.show_media_fragment) {
                    toolbar.gone()
                    bubbleNavigation.gone()
                } else {
                    toolbar.visible()
                    bubbleNavigation.visible()
                }
            }
        }

    }

    //bottomNavigation
    private fun setUpBottomNavigation() {


        binding.bubbleNavigation
            .setNavigationChangeListener(BubbleNavigationChangeListener { view, position ->

                val bundle = Bundle()
                when (position) {

                    0 -> navController.navigate(R.id.guide_fragment)
                    1 -> {
                        if (Build.VERSION.SDK_INT >= 30)
                            bundle.putString(Constants.DIRECTORY_KEY, Constants.NEW_WHATSAPP_DIRECTORY)
                        else
                            bundle.putString(Constants.DIRECTORY_KEY, Constants.WHATSAPP_DIRECTORY)


                        navController.navigate(R.id.whatsapp_status_fragment, bundle)
                    }
                    2 -> {
                        if (Build.VERSION.SDK_INT >= 30)
                            bundle.putString(
                                Constants.DIRECTORY_KEY,
                                Constants.NEW_WHATSAPP_BUSINESS_DIRECTORY
                            )
                        else
                            bundle.putString(
                                Constants.DIRECTORY_KEY,
                                Constants.WHATSAPP_BUSINESS_DIRECTORY
                            )
                        navController.navigate(R.id.wb_status_fragment, bundle)
                    }
                    3 -> {
                        bundle.putString(Constants.DIRECTORY_KEY, Constants.SAVED_DIRECTORY)
                        navController.navigate(R.id.saved_status_fragment, bundle)
                    }
                    else -> return@BubbleNavigationChangeListener
                }

        })
    }


    //toolbar
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

    //navigationView
    private fun setUpNavigationView() {

        binding.navigationView.setNavigationItemSelectedListener {

            when (it.itemId) {

                R.id.nav_item_guide -> {
                    navController.navigate(R.id.guide_fragment)
                    binding.bubbleNavigation.setCurrentActiveItem(0)
                }
                R.id.nav_item_share_app -> MyIntent.shareAppIntent(this@MainActivity)
                R.id.nav_item_other_app -> MyIntent.otherAppIntent(this@MainActivity)
                R.id.nav_item_comment -> CommentDialog(this@MainActivity).show()
                R.id.nav_item_about_us -> AboutUsDialog(this@MainActivity).show()
                R.id.nav_item_exit -> ExitDialog(this@MainActivity).show()

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