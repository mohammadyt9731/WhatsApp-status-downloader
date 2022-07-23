package com.ddt.whatsappStatusDownloader.utils

import android.content.Context
import com.adivery.sdk.Adivery
import com.adivery.sdk.AdiveryListener

object MyAdivery {

    fun showInterstitialAd(context: Context, adId: String) {

        Adivery.prepareInterstitialAd(context, adId);

        var isShowAd = false
        Adivery.addGlobalListener(object : AdiveryListener() {

            override fun onInterstitialAdLoaded(placementId: String) {

                if (isShowAd == false) {
                    Adivery.showAd(adId)
                    isShowAd = true
                }

            }

        })
    }
}