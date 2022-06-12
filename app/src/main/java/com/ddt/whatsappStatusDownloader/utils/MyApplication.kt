package com.ddt.whatsappStatusDownloader.utils

import android.app.Application
import ir.tapsell.plus.TapsellPlus
import ir.tapsell.plus.TapsellPlusInitListener
import ir.tapsell.plus.model.AdNetworkError
import ir.tapsell.plus.model.AdNetworks

class MyApplication : Application (){

    override fun onCreate() {
        super.onCreate()

        initTapsell()
    }


    private fun initTapsell() {
        TapsellPlus.initialize(this, Constants.TAPSELL_KEY,
            object : TapsellPlusInitListener {
                override fun onInitializeSuccess(adNetworks: AdNetworks) {}
                override fun onInitializeFailed(
                    adNetworks: AdNetworks,
                    adNetworkError: AdNetworkError
                ) {
                }
            })
        TapsellPlus.setGDPRConsent(this, true)
    }

}