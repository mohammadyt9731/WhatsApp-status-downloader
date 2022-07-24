package com.ddt.whatsappStatusDownloader.fragment

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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ddt.whatsappStatusDownloader.R
import com.ddt.whatsappStatusDownloader.databinding.FragmentShowMediaBinding
import com.ddt.whatsappStatusDownloader.utils.*
import java.io.File


class ShowMediaFragment : Fragment() {

    private lateinit var binding: FragmentShowMediaBinding
    private lateinit var statusFile: File

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowMediaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showAd()
        init()
        setUpView(statusFile)
        setOnclick()
    }

    override fun onResume() {
        super.onResume()

        binding.apply {
            if (videoView.isPlaying)
                videoView.pause()
        }
    }

    private fun showAd() {

        MyAdivery.showInterstitialAd(requireContext(), Constants.INTERSTITIAL_SECOND_ID)
    }

    private fun init() {
        //get file
        statusFile = arguments?.getSerializable(Constants.MEDIA_PATH_KEY) as File
    }

    private fun setUpView(file: File) {
        binding.apply {

            //image
            if (UtilsMethod.isImageFile(file.name)) {
                //show image
                Glide.with(requireContext()).load(statusFile)
                    .placeholder(ColorDrawable(Color.WHITE))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.zoomageViewPreview)

                zoomageViewPreview.visible()
                videoView.gone()

                //video
            } else {

                setUpVideoView(statusFile)

                zoomageViewPreview.gone()
                videoView.visible()
            }
        }
    }

    private fun setUpVideoView(file: File) {

        binding.apply {

            val mediaController = MediaController(context)

            videoView.apply {
                setOnPreparedListener(MediaPlayer.OnPreparedListener { mp ->
                    mp.setOnVideoSizeChangedListener { _, _, _ ->
                        mediaController.setAnchorView(videoView)
                        mediaController.setMediaPlayer(videoView)
                    }
                })

                setVideoPath(file.path)
                seekTo(1)
                setMediaController(mediaController)
                start()
            }

        }

    }

    private fun setOnclick() {

        binding.apply {

            ivBack.setOnClickListener() {
                findNavController().navigateUp()
            }

            btnDelete.setOnClickListener() {
                showDeleteDialog()
            }

            btnSave.setOnClickListener() {

                FileOperation.saveFile(requireContext(),statusFile)
            }

            btnShare.setOnClickListener() {
                FileOperation.shareFile(requireContext(), statusFile)
            }


        }

    }

    private fun showDeleteDialog() {

        AlertDialog.Builder(requireContext())
            .setTitle(requireContext().getString(R.string.delete_status))
            .setMessage(requireContext().getString(R.string.do_you_want_to_delete))
            .setCancelable(true)
            .setPositiveButton(requireContext().getString(R.string.yes)) { _, _ ->
                FileOperation.deleteFile(requireContext(), statusFile)
                findNavController().navigateUp()
            }
            .setNegativeButton(requireContext().getString(R.string.no)) { dialog, _ -> dialog.cancel() }
            .create()
            .show()


    }



}