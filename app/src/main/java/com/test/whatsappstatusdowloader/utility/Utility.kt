package com.test.whatsappstatusdowloader.utility

import android.app.Activity
import android.util.DisplayMetrics
import android.widget.Toast
import com.test.whatsappstatusdowloader.R
import java.io.File
import java.net.URLConnection

object Utility {

    fun getScreenWidth(activity: Activity):Int{
        return activity.resources.displayMetrics.widthPixels
    }

    fun isImageFile(name: String): Boolean {

        return (name.endsWith(".jpg")||name.endsWith(".png")||name.endsWith(".jpeg")||name.endsWith(".gif"))
    }

    fun isVideoFile(name: String): Boolean {

        return (name.endsWith(".mp4")||name.endsWith(".mvi")||name.endsWith(".mkv"))
    }


}