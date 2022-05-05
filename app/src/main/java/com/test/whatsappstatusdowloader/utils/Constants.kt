package com.test.whatsappstatusdowloader.utils

import android.os.Environment

object Constants {

    //SplashActivity
    const val SPLASH_DELAY: Long = 500
    const val REQUEST_CODE = 1234
    const val MANAGE_EXTERNAL_STORAGE_PERMISSION = "android:manage_external_storage"

    //MainActivity
    const val TAB_COLOR_CODE = "#ece5dd"
    val WHATSAPP_DIRECTORY =
        Environment.getExternalStorageDirectory().path + "/WhatsApp/Media/.Statuses"

    //val WHATSAPP_DIRECTORY=Environment.getExternalStorageDirectory().path + "/Download/Wahtsapp"
    val WHATSAPP_BUSINESS_DIRECTORY =
        Environment.getExternalStorageDirectory().path + "/WhatsApp/Media/.Statuses"
    val SAVED_DIRECTORY = Environment.getExternalStorageDirectory().path + "/saveDirectory"

    //WhatsappStatusAdepter
    const val LOTTIE_START_FRAME: Int = 22
    const val LOTTIE_END_FRAME: Int = 100
    const val MEDIA_PATH_KEY = "media_path_key"

    //Dialogs
    const val DIALOG_WIDTH_PERCENTAGE = 90

    //MyIntent
    const val WHATSAPP_PACKAGE = "com.whatsapp"


}