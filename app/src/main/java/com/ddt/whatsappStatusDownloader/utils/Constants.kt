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
    const val PERCENTAGE_OF_WIDTH: Int = 44
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


    //MyAdivery
    const val ADIVERY_APP_ID: String="b5322e41-d51e-4a96-8cdf-bc3adc2ce83d"
    const val INTERSTITIAL_MAIN_ID: String="0fe9b48f-2451-4798-8cc9-4959450a70f6"
    const val INTERSTITIAL_SECOND_ID: String="b92850dc-b111-463b-a2f2-90d8c68f0060"


    //MySharedPreferences
    const val SHARED_PREFERENCES_NAME="shared_preferences_name"
    const val IS_REGISTER_COMMENT="is_register_comment"
    const val NUMBER_OF_OPEN_APP="NUMBER_OF_OPEN_APP"

}