package com.ddt.whatsappStatusDownloader.utils

import androidx.lifecycle.ViewModel
import java.io.File

class StatusViewModel : ViewModel() {


    fun getStatusList(directoryAddress:String):MutableList<File>{
        val statusFileList: ArrayList<File> = ArrayList()
        val statusDirectory = File(directoryAddress)
        val fileList: Array<out File>? = statusDirectory.listFiles()

        for (file in fileList!!) {
            if (UtilsMethod.isImageFile(file.name) || UtilsMethod.isVideoFile(file.name))
                statusFileList.add(file)
        }

        return statusFileList
    }
}