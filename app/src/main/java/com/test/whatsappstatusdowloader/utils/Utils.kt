package com.test.whatsappstatusdowloader.utils

import android.app.Activity

object Utils {

    fun getScreenWidth(activity: Activity):Int{
        return activity.resources.displayMetrics.widthPixels
    }

    fun isImageFile(name: String): Boolean {

        return (name.endsWith(".jpg")||name.endsWith(".png")||name.endsWith(".jpeg")||name.endsWith(".gif"))
    }

    fun isVideoFile(name: String): Boolean {

        return (name.endsWith(".mp4")||name.endsWith(".mkv"))
    }


}