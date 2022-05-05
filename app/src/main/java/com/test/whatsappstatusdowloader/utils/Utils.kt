package com.test.whatsappstatusdowloader.utils

import android.content.Context

object Utils {

    fun getScreenWidth(context: Context,percentage:Int=100):Int{
        return context.resources.displayMetrics.widthPixels * percentage/100
    }

    fun isImageFile(fileName: String): Boolean {

        return (fileName.endsWith(".jpg")||fileName.endsWith(".png")||fileName.endsWith(".jpeg")||fileName.endsWith(".gif"))
    }

    fun isVideoFile(fileName: String): Boolean {

        return (fileName.endsWith(".mp4")||fileName.endsWith(".mkv"))
    }


}