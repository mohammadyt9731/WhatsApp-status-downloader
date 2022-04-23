package com.test.whatsappstatusdowloader.activity

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import coil.load
import com.test.whatsappstatusdowloader.R
import com.test.whatsappstatusdowloader.databinding.ActivityMainBinding
import com.test.whatsappstatusdowloader.databinding.ActivityShowMediaBinding
import com.test.whatsappstatusdowloader.utility.Constants
import com.test.whatsappstatusdowloader.utility.MyIntent
import java.io.File

class ShowMediaActivity : AppCompatActivity() {

    lateinit var binding:ActivityShowMediaBinding
    lateinit var statusFile: File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityShowMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        statusFile= intent.getSerializableExtra(Constants.MEDIA_PATH_KEY) as File


        binding.ivPreview.load(statusFile) {
            crossfade(true)
            placeholder(ColorDrawable(Color.WHITE))
        }

        setOnclick()
    }

    private fun setOnclick(){

        binding.apply {

            ivBack.setOnClickListener(){
                finish()
            }

            tvDelete.setOnClickListener(){
                showDeleteDialog()
            }

            tvSave.setOnClickListener(){
                saveFile()
            }

            tvShare.setOnClickListener(){
                MyIntent.sharePhoto(this@ShowMediaActivity, statusFile.path)
            }

        }

    }

    private fun showDeleteDialog(){

        val deleteDialog= AlertDialog.Builder(this)
            .setTitle("حذف پست")
            .setMessage("آیا میخواید این پست را حذف کنید؟")
            .setCancelable(true)
            .setPositiveButton("بله") { _, _ ->
                statusFile.delete()
                finish()
            }
            .setNegativeButton("خیر") { dialog,_  -> dialog.cancel() }
            .create()
            .show()

    }

    private fun saveFile() {

        val sourceFile = statusFile
        val destinationFile =
            File(Constants.SAVED_DIRECTORY + "/" + sourceFile.name)

        if (destinationFile.exists()) {
            Toast.makeText(this, "قبلا ذخیره شده است.", Toast.LENGTH_SHORT).show()
            return
        }


        try {
            sourceFile.copyTo(destinationFile)
            Toast.makeText(
                this,
                "در پوشه saveDirectory ذخیره شد",
                Toast.LENGTH_SHORT
            ).show()

        } catch (e: Exception) {
            Toast.makeText(
                this,
                this.getString(R.string.unknown_error),
                Toast.LENGTH_SHORT
            ).show()
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }


    }
}