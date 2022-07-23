package com.ddt.whatsappStatusDownloader.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.AppOpsManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.ddt.whatsappStatusDownloader.R
import com.ddt.whatsappStatusDownloader.utils.Constants
import java.util.*
import kotlin.concurrent.timerTask

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    //required permission list
    private val PERMISSION_LIST = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        checkStoragePermission()
    }

    private fun checkStoragePermission() {


        if (arePermissionsDenied())
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION_LIST[0]))
                showRequestPermissionDialog()
            else
                requestPermission()
        else
            goToMainActivity()

    }

    private fun arePermissionsDenied(): Boolean {


        //check for android > 11
        if (SDK_INT >= Build.VERSION_CODES.R)
            return checkStorageForApi30()
        else {
            for (permission in PERMISSION_LIST) {
                if (ActivityCompat.checkSelfPermission(this@SplashActivity, permission)
                    != PackageManager.PERMISSION_GRANTED
                )
                    return true
            }
            return false
        }

    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun checkStorageForApi30(): Boolean {

        val appOps = getSystemService(AppOpsManager::class.java)
        val mode = appOps.unsafeCheckOpNoThrow(
            Constants.MANAGE_EXTERNAL_STORAGE_PERMISSION,
            applicationContext.applicationInfo.uid,
            applicationContext.packageName
        )
        return mode != AppOpsManager.MODE_ALLOWED
    }

    private fun showRequestPermissionDialog() {

        AlertDialog.Builder(this).apply {
            setTitle(resources.getString(R.string.storage_permission_request))
            setMessage(resources.getString(R.string.need_access_storage))
            setPositiveButton(resources.getString(R.string.accept)) { _, _ -> requestPermission() }

            setNegativeButton(resources.getString(R.string.exit)) { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            create()
            show()
        }
    }

    private fun requestPermission() {

        if (arePermissionsDenied()) {

            // android >= 11
            if (SDK_INT >= Build.VERSION_CODES.R) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivityForResult(intent, Constants.REQUEST_CODE)
                return
            }
            //android < 11
            else
                requestPermissions(PERMISSION_LIST, Constants.REQUEST_CODE)
        }
    }

    private fun goToMainActivity() {

        Timer().schedule(timerTask {

            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, Constants.SPLASH_DELAY)
    }

    //for android >= 11
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST_CODE) {

            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    goToMainActivity()
                } else {
                    showRequestPermissionDialog()
                }
            }
        }
    }

    //for android <11
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.REQUEST_CODE && grantResults.isNotEmpty()) {

            if (arePermissionsDenied()) {
                showRequestPermissionDialog()
            } else {
                goToMainActivity()
            }
        }
    }


}