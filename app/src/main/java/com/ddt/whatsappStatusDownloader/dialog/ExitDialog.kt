package com.ddt.whatsappStatusDownloader.dialog

import android.app.Activity
import android.app.Dialog
import android.widget.AbsListView
import com.adivery.sdk.AdiveryAdListener
import com.adivery.sdk.AdiveryBannerAdView
import com.ddt.whatsappStatusDownloader.R
import com.ddt.whatsappStatusDownloader.databinding.DialogExitBinding
import com.ddt.whatsappStatusDownloader.utils.Constants
import com.ddt.whatsappStatusDownloader.utils.UtilsMethod


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

        val bannerAd: AdiveryBannerAdView = binding.bannerAd

        bannerAd.setBannerAdListener(object : AdiveryAdListener() {
            override fun onAdLoaded() {
                // تبلیغ به‌طور خودکار نمایش داده می‌شود، هر کار دیگری لازم است اینجا انجام دهید.
            }

            override fun onError(reason: String) {
                // خطا را چاپ کنید تا از دلیل آن مطلع شوید
            }

            override fun onAdClicked() {
                // کاربر روی بنر کلیک کرده
            }
        })

        bannerAd.loadAd()

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