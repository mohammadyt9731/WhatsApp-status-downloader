package com.test.whatsappstatusdowloader.activity

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer.OnPreparedListener
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.test.whatsappstatusdowloader.R
import com.test.whatsappstatusdowloader.databinding.ActivityShowMediaBinding
import com.test.whatsappstatusdowloader.utils.Constants
import com.test.whatsappstatusdowloader.utils.FileOperation
import com.test.whatsappstatusdowloader.utils.MyIntent
import com.test.whatsappstatusdowloader.utils.UtilsMethod
import java.io.File


class ShowMediaActivity : AppCompatActivity() {

    lateinit var binding:ActivityShowMediaBinding
    lateinit var statusFile: File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityShowMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        statusFile= intent.getSerializableExtra(Constants.MEDIA_PATH_KEY) as File




        Glide.with(this).load(statusFile)
            .placeholder(ColorDrawable(Color.WHITE))
            .transition(DrawableTransitionOptions.withCrossFade())
            . into(binding.ivPreview)

        checkFileType(statusFile)
        setOnclick()
    }

    private fun checkFileType(file: File){
        binding.apply {

            if(UtilsMethod.isImageFile(file.name))
            {
                ivPreview.visibility= View.VISIBLE
                videoView.visibility= View.GONE


            }else{

                ivPreview.visibility= View.GONE
                videoView.visibility= View.VISIBLE


                setUpVideoView(statusFile)
            }
        }
    }

    private fun setUpVideoView(file: File){

        binding.apply {

            val mediaController=MediaController(this@ShowMediaActivity)

            videoView.setOnPreparedListener(OnPreparedListener { mp ->
                mp.setOnVideoSizeChangedListener { _, _, _ ->
                    mediaController.setAnchorView(videoView)
                    mediaController.setMediaPlayer(videoView)
                }
            })

            videoView.setVideoPath(file.path)
            videoView.seekTo(1)
            videoView.setMediaController(mediaController)
            videoView.start()


        }

    }

    private fun setOnclick(){

        binding.apply {

            ivBack.setOnClickListener(){
                finish()
            }

            btnDelete.setOnClickListener(){
                showDeleteDialog()
            }

            btnSave.setOnClickListener(){
                saveFile()
            }

            btnShare.setOnClickListener(){
                FileOperation.shareFile(this@ShowMediaActivity,statusFile)
            }


        }

    }

    private fun showDeleteDialog(){

        val deleteDialog= AlertDialog.Builder(this)
            .setTitle("حذف پست")
            .setMessage("آیا میخواید این پست را حذف کنید؟")
            .setCancelable(true)
            .setPositiveButton("بله") { _, _ ->
                FileOperation.deleteFile(this,statusFile)
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