package com.ddt.whatsappStatusDownloader.dialog

import com.ddt.whatsappStatusDownloader.R
import android.app.Dialog
import android.content.Context
import android.widget.AbsListView
import com.ddt.whatsappStatusDownloader.databinding.DialogAboutUsBinding
import com.ddt.whatsappStatusDownloader.utils.Constants
import com.ddt.whatsappStatusDownloader.utils.UtilsMethod

class AboutUsDialog(context: Context) : Dialog(context) {

    private var binding: DialogAboutUsBinding = DialogAboutUsBinding.inflate(layoutInflater)

    init {
        setContentView(binding.root)
        config()
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

    private fun setOnClick() {

        binding.ivClose.setOnClickListener{
            cancel()
        }

    }
}