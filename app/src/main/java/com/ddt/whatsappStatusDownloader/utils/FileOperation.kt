package com.ddt.whatsappStatusDownloader.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.FileProvider
import com.ddt.whatsappStatusDownloader.R
import java.io.File

object FileOperation {


    fun deleteFile(context: Context, file: File) {

        try {
            file.delete()
            context.showToast(context.getString(R.string.deleted_successfully))
        } catch (e: Exception) {
            UtilsMethod.showError(context)
        }
    }

    fun shareFile(context: Context, file: File){

        try {
            val sharePhotoIntent = Intent(Intent.ACTION_SEND).apply {
                type =if (UtilsMethod.isVideoFile(file.name)){
                    "video/*"
                }else
                    "image/*"


                val shareMessage = """
                ${context.getString(R.string.app_link_download)}${context.getString(R.string.app_name)}           
                ${context.getString(R.string.app_id_caffe_bazaar)}${context.packageName}                             
                """.trimIndent()

                putExtra(
                    Intent.EXTRA_TEXT,
                    shareMessage
                )
                val fileURI = FileProvider.getUriForFile(
                    context, context.packageName + ".provider",
                    File(file.absolutePath)
                )
                putExtra(Intent.EXTRA_STREAM, fileURI)
            }
            context.startActivity(sharePhotoIntent)

        } catch (e: Exception) {
            UtilsMethod.showError(context)
        }
    }

    fun saveFile(context: Context,file: File):Boolean {
        val sourceFile = File(file.path)
        val destinationFile =
            File(Constants.SAVED_DIRECTORY + "/" + file.name)

        if (destinationFile.exists()) {
            context.showToast(context.getString(R.string.already_saved))
        } else {
            try {
                sourceFile.copyTo(destinationFile)
                context.showToast(context.getString(R.string.saved_successfully))
                return true

            } catch (e: Exception) {
                UtilsMethod.showError(context)
            }
        }
        return false
    }
}