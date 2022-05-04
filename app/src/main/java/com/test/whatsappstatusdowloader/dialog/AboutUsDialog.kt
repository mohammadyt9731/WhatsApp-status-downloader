package com.test.whatsappstatusdowloader.dialog

import com.test.whatsappstatusdowloader.R
import android.app.Dialog
import android.content.Context
import android.widget.AbsListView
import com.test.whatsappstatusdowloader.databinding.DialogAboutUsBinding

class AboutUsDialog(context: Context) : Dialog(context) {

    private var binding: DialogAboutUsBinding = DialogAboutUsBinding.inflate(layoutInflater)

    init {
        setContentView(binding.root)
        config()
        setOnClick()
    }


    private fun config() {
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
        window!!.attributes.windowAnimations = R.style.scale_anim_style
        window!!.setLayout(
            AbsListView.LayoutParams.MATCH_PARENT,
            AbsListView.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setOnClick() {

        binding.ivClose.setOnClickListener{
            cancel()
        }


    }


}