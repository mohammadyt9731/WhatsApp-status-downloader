package com.test.whatsappstatusdowloader.utility

import android.os.Environment

object Constants {

  //SplashActivity
  const val SPLASH_DURATION: Long = 500
  const val REQUEST_CODE = 1234
  const val MANAGE_EXTERNAL_STORAGE_PERMISSION = "android:manage_external_storage"


   val WHATSAPP_DIRECTORY=Environment.getExternalStorageDirectory().path + "/WhatsApp/Media/.Statuses"
  //val WHATSAPP_DIRECTORY=Environment.getExternalStorageDirectory().path + "/Download/Wahtsapp"
    val WHATSAPP_BUSINESS_DIRECTORY=Environment.getExternalStorageDirectory().path + "/WhatsApp/Media/.Statuses"
    val SAVED_DIRECTORY=Environment.getExternalStorageDirectory().path+"/saveDirectory"

    const val MEDIA_PATH_KEY="media_path_key"


    const val TAB_COLOR_CODE="#ece5dd"

}