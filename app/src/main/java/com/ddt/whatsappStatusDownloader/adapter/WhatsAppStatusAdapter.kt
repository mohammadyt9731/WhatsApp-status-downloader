package com.ddt.whatsappStatusDownloader.adapter

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ddt.whatsappStatusDownloader.R
import com.ddt.whatsappStatusDownloader.databinding.WhatsappStatusBinding
import com.ddt.whatsappStatusDownloader.utils.*
import java.io.File

private lateinit var binding: WhatsappStatusBinding

class WhatsAppStatusAdapter(var activity: Activity) :
    RecyclerView.Adapter<WhatsAppStatusAdapter.ViewHolder>() {

    var itemWidth: Int = UtilsMethod.getScreenWidth(activity, Constants.PERCENTAGE_OF_WIDTH)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = WhatsappStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setUpViews(position)
        holder.setStatusImage(position)
        holder.setOnClickListener(position)
    }


    override fun getItemCount(): Int {

        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root) {


        fun setUpViews(position: Int) {

            binding.apply {
                //root width
                clRoot.layoutParams.width = itemWidth

                //check video file
                if (UtilsMethod.isVideoFile(differ.currentList[position].path))
                    ivVideo.visible()
                else
                    ivVideo.gone()

                //check exists file
                if (File(Constants.SAVED_DIRECTORY + "/" + differ.currentList[position].name).exists()) {
                    lottieDownload.frame = Constants.LOTTIE_END_FRAME
                } else
                    lottieDownload.frame = Constants.LOTTIE_START_FRAME

            }
        }

        fun setStatusImage(position: Int) {

            //video
            if (UtilsMethod.isVideoFile(differ.currentList[position].path)) {
                try {
                    val retriever = MediaMetadataRetriever()
                    retriever.setDataSource(differ.currentList[position].path)
                    val bitmap = retriever.getFrameAtTime(1)

                    Glide.with(activity).load(bitmap)
                        .placeholder(ColorDrawable(Color.WHITE))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.ivStatus)
                } catch (e: Exception) {

                }

                //image
            } else {

                Glide.with(activity).load(differ.currentList[position])
                    .placeholder(ColorDrawable(Color.WHITE))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.ivStatus)


            }
        }

        fun setOnClickListener(position: Int) {
            binding.apply {

                ivShareStatus.setOnClickListener() {
                    FileOperation.shareFile(activity, differ.currentList[position])
                }

                ivStatus.setOnClickListener() {

                    val bundle = Bundle()
                    bundle.putSerializable(Constants.MEDIA_PATH_KEY, differ.currentList[position])
                    activity.findNavController(R.id.nav_host)
                        .navigate(R.id.show_media_fragment, bundle)
                }

                lottieDownload.setOnClickListener() {

                    if (FileOperation.saveFile(activity,differ.currentList[position]))
                        lottieDownload.playAnimation()
                }
            }

        }
    }


    private val differCallBack = object : DiffUtil.ItemCallback<File>() {
        override fun areItemsTheSame(oldItem: File, newItem: File): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: File, newItem: File): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
}