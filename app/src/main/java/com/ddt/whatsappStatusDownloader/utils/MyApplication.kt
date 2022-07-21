package com.ddt.whatsappStatusDownloader.utils

import android.app.Application
import com.adivery.sdk.Adivery


class MyApplication : Application (){

    override fun onCreate() {
        super.onCreate()

        initAdivery()
    }


    private fun initAdivery() {
        Adivery.configure(this, Constants.ADIVERY_APP_ID);
    }

}