package com.test.whatsappstatusdowloader.activity

import android.Manifest
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
import com.test.whatsappstatusdowloader.R
import java.util.*
import kotlin.concurrent.timerTask


class SplashActivity : AppCompatActivity() {

    private val DURATION: Long = 500
    private val REQUEST_CODE = 1234
    private val MANAGE_EXTERNAL_STORAGE_PERMISSION = "android:manage_external_storage"

    private var PERSMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        checkStoragePermission()

    }

    private fun checkStoragePermission() {

        if (arePermissionsDenied()) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(this, PERSMISSIONS[0])) {
                showRequestPermissionDialog()

            } else {
                requestPermission()
            }

        } else {
            goToMainActivity()
        }
    }


    private fun arePermissionsDenied(): Boolean {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //check for android > 11
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                return checkStorageApi30()
            else {
                for (permission in PERSMISSIONS) {
                    if (ActivityCompat.checkSelfPermission(this@SplashActivity, permission)
                        != PackageManager.PERMISSION_GRANTED
                    )

                        return true
                }

                return false
            }
        } else {
            return false
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun checkStorageApi30(): Boolean {

        val appOps = getSystemService(AppOpsManager::class.java)
        val mode = appOps.unsafeCheckOpNoThrow(
            MANAGE_EXTERNAL_STORAGE_PERMISSION,
            applicationContext.applicationInfo.uid,
            applicationContext.packageName
        )
        return mode != AppOpsManager.MODE_ALLOWED
    }


    private fun showRequestPermissionDialog() {

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.apply {
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionsDenied()) {

            // If Android 11 Request for Manage File Access Permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivityForResult(intent, REQUEST_CODE)
                return
            } else
                requestPermissions(PERSMISSIONS, REQUEST_CODE)
        }
    }

    private fun goToMainActivity() {

        Timer().schedule(timerTask {

            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, DURATION)
    }

    //for android > 11
    override fun onActivityResult(requestCode: Int, resultCode: Int,  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    goToMainActivity()
                } else {
                    showRequestPermissionDialog()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE && grantResults.isNotEmpty()) {

            if (arePermissionsDenied()) {
               showRequestPermissionDialog()
            } else {
                goToMainActivity()
            }
        }
    }


}