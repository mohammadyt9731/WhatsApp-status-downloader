package com.ddt.whatsappStatusDownloader.utils

import android.os.Environment

object Constants {




    //SplashActivity
    const val SPLASH_DELAY: Long = 500
    const val REQUEST_CODE = 1234
    const val MANAGE_EXTERNAL_STORAGE_PERMISSION = "android:manage_external_storage"

    //MainActivity
    const val DIRECTORY_KEY = "directory_key"
    const val MAX_NUMBER_OF_OPEN_APP = 2


    val WHATSAPP_DIRECTORY =
        Environment.getExternalStorageDirectory().path + "/WhatsApp/Media/.Statuses"

    val NEW_WHATSAPP_DIRECTORY =
        Environment.getExternalStorageDirectory().path + "/Android/media/com.whatsapp/WhatsApp/Media/.Statuses"

    val WHATSAPP_BUSINESS_DIRECTORY =
        Environment.getExternalStorageDirectory().path + "/WhatsAppBg/Media/.Statuses"

    val NEW_WHATSAPP_BUSINESS_DIRECTORY =
        Environment.getExternalStorageDirectory().path + "/Android/media/com.whatsapp/WhatsAppBg/Media/.Statuses"

    val SAVED_DIRECTORY = Environment.getExternalStorageDirectory().path + "/saveDirectory"

    //WhatsappStatusAdepter
    const val LOTTIE_START_FRAME: Int = 22
    const val LOTTIE_END_FRAME: Int = 100
    const val MEDIA_PATH_KEY = "media_path_key"

    //Dialogs
    const val DIALOG_WIDTH_PERCENTAGE = 90
    const val DISABLE_BUTTON_ALPHA = 0.4f
    const val ENABLE_BUTTON_ALPHA = 1f

    //MyIntent
    const val WHATSAPP_PACKAGE = "com.whatsapp"
    const val WHATSAPP_BUSINESS_PACKAGE = "com.com.whatsapp.w4b"

    //MyTapsell
    const val TAPSELL_KEY = "skmldklsmmsfjneespcsfmlrlimhtofdisdhagscgtctrknioitmdldehpajcseshrminp"
    const val STANDARD_BANNER_MAIN_ACTIVITY = "6297efb1305e375a5fb6a42e"
    const val STANDARD_BANNER_EXIT_DIALOG = "6297f016305e375a5fb6a42f"
    const val INTERSTITIAL_START_APP = "6297f06ce309116932dfa497"
    const val INTERSTITIAL_VIDEO = "6297f0d873843918417ab379"

    //MySharedPreferences
    const val SHARED_PREFERENCES_NAME="shared_preferences_name"
    const val IS_REGISTER_COMMENT="is_register_comment"
    const val NUMBER_OF_OPEN_APP="NUMBER_OF_OPEN_APP"



}