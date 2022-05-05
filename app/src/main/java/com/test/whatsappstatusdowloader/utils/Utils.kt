package com.test.whatsappstatusdowloader.utils

import android.app.Activity

object Utils {

    fun getScreenWidth(activity: Activity):Int{
        return activity.resources.displayMetrics.widthPixels
    }

    fun isImageFile(fileName: String): Boolean {

        return (fileName.endsWith(".jpg")||fileName.endsWith(".png")||fileName.endsWith(".jpeg")||fileName.endsWith(".gif"))
    }

    fun isVideoFile(fileName: String): Boolean {

        return (fileName.endsWith(".mp4")||fileName.endsWith(".mkv"))
    }


}