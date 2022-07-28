package com.ddt.whatsappStatusDownloader.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.ddt.whatsappStatusDownloader.R

object MyIntent {

    fun shareAppIntent(context: Context) {
        try {

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                val shareMessage = """
                ${context.getString(R.string.app_link_download)}${context.getString(R.string.app_name)}           
                ${context.getString(R.string.market_app_id)}${context.packageName}                          
                """.trimIndent()
                putExtra(Intent.EXTRA_TEXT, shareMessage)

            }
            context.startActivity(
                Intent.createChooser(
                    shareIntent,
                    context.getString(R.string.share_app_with)
                )
            )

        } catch (e: Exception) {
            UtilsMethod.showError(context)
        }
    }

    fun otherAppIntent(context: Context) {
        try {
            val otherAppIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(context.getString(R.string.developer_applications_link))
            )
            context.startActivity(otherAppIntent)
        } catch (e: Exception) {
            UtilsMethod.showError(context)
        }
    }

    /*caffe bazzar
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
     */

    //caffe bazaar
    /*
    public static void commentIntent(Context context) {
        try {
            Uri commentUri = Uri.parse(context.getString(R.string.comment_market_link) + context.getPackageName());
            Intent commentIntent = new Intent(Intent.ACTION_EDIT, commentUri);
            context.startActivity(commentIntent);
        } catch (Exception e) {
            Toast.makeText(context, context.getString(R.string.need_install_market), Toast.LENGTH_LONG).show();
        }
    }
    */

    //myket
    fun commentIntent(context: Context) {
        try {
            val commentUri =
                Uri.parse(context.getString(R.string.comment_market_link) + context.packageName)
            val commentIntent = Intent(Intent.ACTION_VIEW, commentUri)
            context.startActivity(commentIntent)
        } catch (e: java.lang.Exception) {
            Toast.makeText(
                context,
                context.getString(R.string.need_install_market),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun emailIntent(context: Context) {

        try {

            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                val emailAddress = arrayOf(context.getString(R.string.email_address))
                putExtra(Intent.EXTRA_EMAIL, emailAddress)
                putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name))
            }

            context.startActivity(
                Intent.createChooser(
                    emailIntent,
                    context.getString(R.string.send_email)
                )
            )
        } catch (e: Exception) {
            UtilsMethod.showError(context)
        }
    }

    fun openWhatsApp(context: Context) {

        try {
            val whatsAppIntent = context.packageManager.getLaunchIntentForPackage(Constants.WHATSAPP_PACKAGE)
            context.startActivity(whatsAppIntent)

        } catch (e: Exception) {

            Toast.makeText(
                context,
                context.getString(R.string.whatsapp_is_not_installed),
                Toast.LENGTH_LONG
            ).show()
        }

    }

    fun openWhatsAppBusiness(context: Context) {

        try {
            val whatsAppIntent = context.packageManager.getLaunchIntentForPackage(Constants.WHATSAPP_BUSINESS_PACKAGE)
            context.startActivity(whatsAppIntent)
        } catch (e: Exception) {

            Toast.makeText(
                context,
                context.getString(R.string.whatsapp_business_is_not_installed),
                Toast.LENGTH_LONG
            ).show()
        }

    }


}