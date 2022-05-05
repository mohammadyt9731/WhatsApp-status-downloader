package com.test.whatsappstatusdowloader.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import com.test.whatsappstatusdowloader.R
import java.io.File
import java.lang.Exception

object MyIntent {

    fun shareAppIntent(context: Context) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.setType("text/plain")
            val shareMessage = """
                ${context.getString(R.string.app_link_download)}${context.getString(R.string.app_name)}           
                ${context.getString(R.string.app_id_caffe_bazaar)}${context.packageName}
                
                
                """.trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            context.startActivity(
                Intent.createChooser(
                    shareIntent,
                    context.getString(R.string.share_app_with)
                )
            )
        } catch (e: Exception) {
            Toast.makeText(context, context.getString(R.string.unknown_error), Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun otherAppIntent(context: Context) {
        try {
            val otherAppIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(context.getString(R.string.developer_caffe_bazaar_link))
            )
            context.startActivity(otherAppIntent)
        } catch (e: Exception) {
            Toast.makeText(context, context.getString(R.string.unknown_error), Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun commentIntent(context: Context) {
        try {
            val commentUri =
                Uri.parse(context.getString(R.string.comment_caffe_bazaar_link) + context.packageName)
            val commentIntent = Intent(Intent.ACTION_EDIT, commentUri)
            context.startActivity(commentIntent)
        } catch (e: Exception) {
            Toast.makeText(
                context,
                context.getString(R.string.need_install_caffe_bazaar),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun emailIntent(context: Context) {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.setData(Uri.parse("mailto:"))
        val emailAddress = arrayOf(context.getString(R.string.email_address))
        emailIntent.putExtra(Intent.EXTRA_EMAIL, emailAddress)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name))
        try {
            context.startActivity(
                Intent.createChooser(
                    emailIntent,
                    context.getString(R.string.send_email)
                )
            )
        } catch (e: Exception) {
            Toast.makeText(context, context.getString(R.string.unknown_error), Toast.LENGTH_LONG)
                .show()
        }
    }

    fun sharePhoto(context: Context,photoPath:String){

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


    }

    fun shareVideo(context: Context, videoPath:String){

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


    }


    fun openWhatsApp(context: Context){

        try {

            val whatsAppIntent=context.packageManager.getLaunchIntentForPackage("com.whatsapp")
            context.startActivity(whatsAppIntent)

        }catch (e:Exception){

            Toast.makeText(context,context.getString(R.string.whatapp_is_not_installed),Toast.LENGTH_LONG).show()
        }

    }
}