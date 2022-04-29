package com.test.whatsappstatusdowloader.activity

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer.OnPreparedListener
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.test.whatsappstatusdowloader.R
import com.test.whatsappstatusdowloader.databinding.ActivityShowMediaBinding
import com.test.whatsappstatusdowloader.utility.Constants
import com.test.whatsappstatusdowloader.utility.MyIntent
import com.test.whatsappstatusdowloader.utility.Utility
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

            if(Utility.isImageFile(file.name))
            {
                ivPreview.visibility= View.VISIBLE
                videoView.visibility= View.GONE


            }else{

                ivPreview.visibility= View.GONE
                videoView.visibility= View.VISIBLE


//                val retriever = MediaMetadataRetriever()
//                retriever.setDataSource(file.path)
//                val bitmap = retriever.getFrameAtTime(1)
//
//                vvPreview.load(bitmap) {
//                    crossfade(true)
//                    placeholder(ColorDrawable(Color.WHITE))
//                }

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