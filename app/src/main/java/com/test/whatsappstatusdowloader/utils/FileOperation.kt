package com.test.whatsappstatusdowloader.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.FileProvider
import com.test.whatsappstatusdowloader.R
import java.io.File

object FileOperation {


    fun deleteFile(context: Context,file: File){

        try {
           file.delete()
        } catch (e: Exception) {
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    fun shareFile(context:Context,file: File){
        if (UtilsMethod.isImageFile(file.name))
           sharePhoto(context,file.absolutePath)
        else if (UtilsMethod.isVideoFile(file.name))
           shareVideo(context,file.absolutePath)

    }

    private fun sharePhoto(context: Context, photoPath: String) {

        try {
            val sharePhotoIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/*"

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
                    File(photoPath)
                )
                putExtra(Intent.EXTRA_STREAM, fileURI)
            }
            context.startActivity(sharePhotoIntent)
        } catch (e: Exception) {
            Toast.makeText(context, context.getString(R.string.unknown_error), Toast.LENGTH_SHORT)
                .show()        }
    }

    private fun shareVideo(context: Context, videoPath: String) {

        try {
            val sharePhotoIntent = Intent(Intent.ACTION_SEND).apply {
                type = "video/*"

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
                    File(videoPath)
                )
                putExtra(Intent.EXTRA_STREAM, fileURI)
            }
            context.startActivity(sharePhotoIntent)

        } catch (e: Exception) {
            Toast.makeText(context, context.getString(R.string.unknown_error), Toast.LENGTH_SHORT)
                .show()
        }
    }
}