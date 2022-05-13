package com.test.whatsappstatusdowloader.fragment

import android.Manifest
import android.app.AlertDialog
import android.app.AppOpsManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.test.whatsappstatusdowloader.R
import com.test.whatsappstatusdowloader.databinding.FragmentSplashScreenBinding
import com.test.whatsappstatusdowloader.utils.Constants
import java.util.*


class SplashScreenFragment : Fragment() {

    //required permission list
    private val PERMISSION_LIST = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private lateinit var binding: FragmentSplashScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentSplashScreenBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkStoragePermission()
    }

    private fun checkStoragePermission() {


        if (arePermissionsDenied())
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), PERMISSION_LIST[0]))
                showRequestPermissionDialog()
            else
                requestPermission()
        else
            goToMainActivity()

    }

    private fun arePermissionsDenied(): Boolean {


        //check for android > 11
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            return checkStorageForApi30()
        else {
            for (permission in PERMISSION_LIST) {
                if (ActivityCompat.checkSelfPermission(requireContext(), permission)
                    != PackageManager.PERMISSION_GRANTED
                )
                    return true
            }
            return false
        }

    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun checkStorageForApi30(): Boolean {

        val appOps = requireActivity().getSystemService(AppOpsManager::class.java)
        val mode = appOps.unsafeCheckOpNoThrow(
            Constants.MANAGE_EXTERNAL_STORAGE_PERMISSION,
            requireContext().applicationInfo.uid,
            requireContext().packageName
        )
        return mode != AppOpsManager.MODE_ALLOWED
    }

    private fun showRequestPermissionDialog() {

        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle(resources.getString(R.string.storage_permission_request))
            setMessage(resources.getString(R.string.need_access_storage))
            setPositiveButton(resources.getString(R.string.accept)) { _, _ -> requestPermission() }

            setNegativeButton(resources.getString(R.string.exit)) { dialog, _ ->
                dialog.dismiss()
                requireActivity().finish()
            }
            create()
            show()
        }
    }

    private fun requestPermission() {

        if (arePermissionsDenied()) {

            // android >= 11
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
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

        Handler().postDelayed(Runnable {
//
//            val navBuilder = NavOptions.Builder()
//            navBuilder.setEnterAnim(android.R.anim.slide_in_left).setExitAnim(android.R.anim.slide_out_right)
//                .setPopEnterAnim(android.R.anim.slide_in_left).setPopExitAnim(android.R.anim.slide_out_right)
          //  findNavController().navigate(R.id.guide_fragment,null,navBuilder.build())

            findNavController().navigate(R.id.action_splashScreenFragment_to_guide_fragment)
        },Constants.SPLASH_DELAY)


    }

    //for android >= 11
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST_CODE) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
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