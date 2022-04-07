package com.test.whatsappstatusdowloader.utility

import android.app.Activity
import android.util.DisplayMetrics

class Utility {

    companion object fun getScreenWidth(activity: Activity):Int{

        return activity.resources.displayMetrics.widthPixels
    }
}