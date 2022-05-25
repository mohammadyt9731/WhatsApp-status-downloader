package com.test.whatsappstatusdowloader.fragment

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.test.whatsappstatusdowloader.R
import com.test.whatsappstatusdowloader.databinding.FragmentShowMediaBinding
import com.test.whatsappstatusdowloader.utils.Constants
import com.test.whatsappstatusdowloader.utils.FileOperation
import com.test.whatsappstatusdowloader.utils.UtilsMethod
import java.io.File


class ShowMediaFragment : Fragment() {

    private lateinit var binding: FragmentShowMediaBinding
    private lateinit var statusFile: File
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentShowMediaBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        statusFile= arguments?.getSerializable(Constants.MEDIA_PATH_KEY) as File


        Glide.with(this).load(statusFile)
            .placeholder(ColorDrawable(Color.WHITE))
            .transition(DrawableTransitionOptions.withCrossFade())
            . into(binding.ivPreview)

        checkFileType(statusFile)
        setOnclick()


    }

    override fun onResume() {
        super.onResume()
        binding.apply {
            if(videoView.isPlaying)
                videoView.pause()

        }
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

            val mediaController= MediaController(context)

            videoView.setOnPreparedListener(MediaPlayer.OnPreparedListener { mp ->
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
               findNavController().navigateUp()
            }

            btnDelete.setOnClickListener(){
                showDeleteDialog()
            }

            btnSave.setOnClickListener(){
                saveFile()
            }

            btnShare.setOnClickListener(){
                FileOperation.shareFile(requireContext(),statusFile)
            }


        }

    }

    private fun showDeleteDialog(){

        val deleteDialog= AlertDialog.Builder(requireContext())
            .setTitle("حذف پست")
            .setMessage("آیا میخواید این پست را حذف کنید؟")
            .setCancelable(true)
            .setPositiveButton("بله") { _, _ ->
                FileOperation.deleteFile(requireContext(),statusFile)
                findNavController().navigateUp()
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

            Toast.makeText(requireContext(), "قبلا ذخیره شده است.", Toast.LENGTH_SHORT).show()
            return
        }


        try {
            sourceFile.copyTo(destinationFile)
            Toast.makeText(
                requireContext(),
                "در پوشه saveDirectory ذخیره شد",
                Toast.LENGTH_SHORT
            ).show()

        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                this.getString(R.string.unknown_error),
                Toast.LENGTH_SHORT
            ).show()
            Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_LONG).show()
        }


    }

}