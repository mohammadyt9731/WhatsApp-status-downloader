package com.test.whatsappstatusdowloader.dialog

import android.app.Activity
import android.app.Dialog
import android.widget.AbsListView
import com.test.whatsappstatusdowloader.R
import com.test.whatsappstatusdowloader.databinding.DialogExitBinding
import com.test.whatsappstatusdowloader.utils.Constants
import com.test.whatsappstatusdowloader.utils.UtilsMethod

class ExitDialog(activity: Activity) : Dialog(activity) {

    private var binding: DialogExitBinding = DialogExitBinding.inflate(layoutInflater)
    private var activity:Activity
    init {
        this.activity=activity
        setContentView(binding.root)
        config()
        showAdvertising()
        setOnClick()
    }


    private fun config() {
        //background transparent
        window!!.setBackgroundDrawableResource(android.R.color.transparent)

        //dialog animation
        window!!.attributes.windowAnimations = R.style.scale_anim_style

        //set width and height
        window!!.setLayout(
            UtilsMethod.getScreenWidth(context, Constants.DIALOG_WIDTH_PERCENTAGE),
            AbsListView.LayoutParams.WRAP_CONTENT
        )
    }

    private fun showAdvertising() {

//        Tapsell.showStandardBanner(
//            activity,
//            Constants.STANDARD_BANNER_EXIT_DIALOG,
//            binding.rlAdvertising,
//            TapsellPlusBannerType.BANNER_300x250
//        )

    }

    private fun setOnClick() {

        binding.apply {

            btnNo.setOnClickListener{
                cancel()
            }

            btnYes.setOnClickListener{
                activity.finish()
            }

        }


    }
}