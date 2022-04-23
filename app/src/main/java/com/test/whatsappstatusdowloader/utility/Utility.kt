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

     fun isVideoFile(path:String):Boolean{

        val mimeType = URLConnection.guessContentTypeFromName(path)
        return  mimeType.startsWith("video")

    }


}