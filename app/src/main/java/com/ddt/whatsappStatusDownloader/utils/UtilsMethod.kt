package com.ddt.whatsappStatusDownloader.utils

import android.content.Context
import android.os.Build
import android.widget.Toast
import com.ddt.whatsappStatusDownloader.R

object UtilsMethod {

    fun getScreenWidth(context: Context,percentage:Int=100):Int{
        return context.resources.displayMetrics.widthPixels * percentage/100
    }

    fun isImageFile(fileName: String): Boolean {

        return (fileName.endsWith(".jpg")||fileName.endsWith(".png")||fileName.endsWith(".jpeg")||fileName.endsWith(".gif"))
    }

    fun isVideoFile(fileName: String): Boolean {

        return (fileName.endsWith(".mp4")||fileName.endsWith(".mkv"))
    }

    fun isAndroid11orHigher()= (Build.VERSION.SDK_INT >= 30)

     fun showError(context: Context) {
        context.showToast( context.getString(R.string.unknown_error))
    }
}